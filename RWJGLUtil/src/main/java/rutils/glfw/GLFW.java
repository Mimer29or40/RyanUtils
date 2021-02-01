package rutils.glfw;

import org.jetbrains.annotations.Nullable;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWGamepadState;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Callback;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import rutils.IOUtil;
import rutils.Logger;
import rutils.TaskDelegator;
import rutils.glfw.event.EventJoystickConnected;
import rutils.glfw.event.EventJoystickDisconnected;
import rutils.glfw.event.EventMonitorConnected;
import rutils.glfw.event.EventMonitorDisconnected;
import rutils.group.Pair;
import rutils.group.Triple;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

public final class GLFW
{
    private static final Logger LOGGER = new Logger();
    
    private static final Map<Integer, String> ERROR_CODES = APIUtil.apiClassTokens((field, value) -> 0x10000 < value && value < 0x20000, null, org.lwjgl.glfw.GLFW.class);
    
    public static final TaskDelegator TASK_DELEGATOR = new TaskDelegator();
    
    public static final EventBus EVENT_BUS = new EventBus(true);
    
    static final Map<Long, Monitor> MONITORS        = new LinkedHashMap<>();
    static       Monitor            PRIMARY_MONITOR = null;
    
    static final Map<Long, Window> WINDOWS     = new LinkedHashMap<>();
    static       Window            MAIN_WINDOW = null;
    
    static Mouse    MOUSE;
    static Keyboard KEYBOARD;
    
    static final Map<Integer, Joystick> JOYSTICKS = new LinkedHashMap<>();
    
    // -------------------- Joystick Callback Emulation -------------------- //
    private static final float[][] JOYSTICK_AXIS_STATES   = new float[GLFW_JOYSTICK_LAST][];
    private static final byte[][]  JOYSTICK_BUTTON_STATES = new byte[GLFW_JOYSTICK_LAST][];
    private static final byte[][]  JOYSTICK_HAT_STATES    = new byte[GLFW_JOYSTICK_LAST][];
    // -------------------- Joystick Callback Emulation -------------------- //
    
    static boolean SUPPORT_RAW_MOUSE_MOTION;
    
    private GLFW() {}
    
    // -------------------- Global Methods -------------------- //
    
    /**
     * Initializes the GLFW library. Before most GLFW functions can be used,
     * GLFW must be initialized, and before an application terminates GLFW
     * should be terminated in order to free any resources allocated during or
     * after initialization.
     */
    public static void init()
    {
        try (MemoryStack stack = MemoryStack.stackPush())
        {
            IntBuffer major = stack.mallocInt(1);
            IntBuffer minor = stack.mallocInt(1);
            IntBuffer rev   = stack.mallocInt(1);
            
            glfwGetVersion(major, minor, rev);
            
            GLFW.LOGGER.fine("GLFW Initialization %s.%s.%s", major.get(), minor.get(), rev.get());
            GLFW.LOGGER.finer("RWJGLUtil Compiled to '%s'", glfwGetVersionString());
        }
        
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
        
        GLFW.TASK_DELEGATOR.setThread();
        
        GLFW.EVENT_BUS.start();
        
        glfwSetErrorCallback(GLFW::errorCallback);
        glfwSetMonitorCallback(GLFW::monitorCallback);
        glfwSetJoystickCallback(GLFW::joystickCallback);
        
        PointerBuffer monitors = Objects.requireNonNull(glfwGetMonitors(), "No monitors found.");
        long          handle;
        for (int i = 0, n = monitors.remaining(); i < n; i++) GLFW.MONITORS.put(handle = monitors.get(), new Monitor(handle, i));
        GLFW.PRIMARY_MONITOR = GLFW.MONITORS.get(glfwGetPrimaryMonitor());
        
        GLFW.MAIN_WINDOW = new WindowMain();
        
        GLFW.MOUSE    = new Mouse();
        GLFW.KEYBOARD = new Keyboard();
        
        for (int jid = GLFW_JOYSTICK_1; jid < GLFW_JOYSTICK_LAST; jid++)
        {
            // -------------------- Joystick Callback Emulation -------------------- //
            {
                if (glfwJoystickIsGamepad(jid))
                {
                    try (MemoryStack stack = MemoryStack.stackPush())
                    {
                        GLFWGamepadState state = GLFWGamepadState.mallocStack(stack);
                        
                        if (glfwGetGamepadState(jid, state))
                        {
                            FloatBuffer axes = state.axes();
                            GLFW.JOYSTICK_AXIS_STATES[jid] = new float[axes.remaining()];
                            
                            ByteBuffer buttons = state.buttons();
                            GLFW.JOYSTICK_BUTTON_STATES[jid] = new byte[buttons.remaining()];
                        }
                        else
                        {
                            GLFW.JOYSTICK_AXIS_STATES[jid]   = new float[0];
                            GLFW.JOYSTICK_BUTTON_STATES[jid] = new byte[0];
                        }
                    }
                }
                else
                {
                    FloatBuffer axes = glfwGetJoystickAxes(jid);
                    GLFW.JOYSTICK_AXIS_STATES[jid] = axes != null ? new float[axes.remaining()] : new float[0];
                    
                    ByteBuffer buttons = glfwGetJoystickButtons(jid);
                    GLFW.JOYSTICK_BUTTON_STATES[jid] = buttons != null ? new byte[buttons.remaining()] : new byte[0];
                }
                
                ByteBuffer hats = glfwGetJoystickHats(jid);
                GLFW.JOYSTICK_HAT_STATES[jid] = hats != null ? new byte[hats.remaining()] : new byte[0];
            }
            // -------------------- Joystick Callback Emulation -------------------- //
            
            if (glfwJoystickPresent(jid))
            {
                boolean isGamepad = glfwJoystickIsGamepad(jid);
                GLFW.JOYSTICKS.put(jid, isGamepad ? new Gamepad(jid) : new Joystick(jid, false));
            }
        }
        
        GLFW.SUPPORT_RAW_MOUSE_MOTION = glfwRawMouseMotionSupported();
    }
    
    /**
     * Processes all pending events.
     * <p>
     * This function processes only those events that are already in the event
     * queue and then returns immediately. Processing events will cause the
     * window and input callbacks associated with those events to be called.
     * <p>
     * On some platforms, a window move, resize or menu operation will cause
     * event processing to block. This is due to how event processing is
     * designed on those platforms. You can use the
     * <a target="_blank" href="http://www.glfw.org/docs/latest/window.html#window_refresh">window refresh callback</a>
     * to redraw the contents of your window when necessary during such operations.
     * <p>
     * On some platforms, certain events are sent directly to the application without going through the event queue, causing callbacks to be called outside of
     * a call to one of the event processing functions.
     *
     * <div style="margin-left: 26px; border-left: 1px solid gray; padding-left: 14px;">
     *     <h5>Note</h5>
     *     <ul>
     *     <li>This function must only be called from the main thread.</li>
     *     </ul>
     * </div>
     */
    public static void eventLoop()
    {
        while (GLFW.WINDOWS.size() > 1)
        {
            glfwPollEvents();
            
            // -------------------- Joystick Callback Emulation -------------------- //
            {
                for (int jid = GLFW_JOYSTICK_1; jid < GLFW_JOYSTICK_LAST; jid++)
                {
                    int n;
                    
                    if (glfwJoystickPresent(jid))
                    {
                        FloatBuffer axes    = null;
                        ByteBuffer  buttons = null;
                        ByteBuffer  hats    = glfwGetJoystickHats(jid);
                        
                        if (!glfwJoystickIsGamepad(jid))
                        {
                            axes    = glfwGetJoystickAxes(jid);
                            buttons = glfwGetJoystickButtons(jid);
                        }
                        else
                        {
                            try (MemoryStack stack = MemoryStack.stackPush())
                            {
                                GLFWGamepadState state = GLFWGamepadState.mallocStack(stack);
                                
                                if (glfwGetGamepadState(jid, state))
                                {
                                    axes    = state.axes();
                                    buttons = state.buttons();
                                }
                            }
                        }
                        
                        if (axes != null)
                        {
                            n = axes.remaining();
                            if (GLFW.JOYSTICK_AXIS_STATES[jid].length != n) GLFW.JOYSTICK_AXIS_STATES[jid] = new float[n];
                            for (int axis = 0; axis < n; axis++)
                            {
                                float newValue = axes.get(axis);
                                if (Float.compare(GLFW.JOYSTICK_AXIS_STATES[jid][axis], newValue) != 0)
                                {
                                    GLFW.JOYSTICK_AXIS_STATES[jid][axis] = newValue;
                                    if (!glfwJoystickPresent(jid)) break;
                                    joystickAxisCallback(jid, axis, GLFW.JOYSTICK_AXIS_STATES[jid][axis]);
                                }
                            }
                        }
                        if (buttons != null)
                        {
                            n = buttons.remaining();
                            if (GLFW.JOYSTICK_BUTTON_STATES[jid].length != n) GLFW.JOYSTICK_BUTTON_STATES[jid] = new byte[n];
                            for (int button = 0; button < n; button++)
                            {
                                byte newValue = buttons.get(button);
                                if (Float.compare(GLFW.JOYSTICK_BUTTON_STATES[jid][button], newValue) != 0)
                                {
                                    GLFW.JOYSTICK_BUTTON_STATES[jid][button] = newValue;
                                    if (!glfwJoystickPresent(jid)) break;
                                    joystickButtonCallback(jid, button, GLFW.JOYSTICK_BUTTON_STATES[jid][button]);
                                }
                            }
                        }
                        if (hats != null)
                        {
                            n = hats.remaining();
                            if (GLFW.JOYSTICK_HAT_STATES[jid].length != n) GLFW.JOYSTICK_HAT_STATES[jid] = new byte[n];
                            for (int hat = 0; hat < n; hat++)
                            {
                                byte newValue = hats.get(hat);
                                if (Float.compare(GLFW.JOYSTICK_HAT_STATES[jid][hat], newValue) != 0)
                                {
                                    GLFW.JOYSTICK_HAT_STATES[jid][hat] = newValue;
                                    if (!glfwJoystickPresent(jid)) break;
                                    joystickHatCallback(jid, hat, GLFW.JOYSTICK_HAT_STATES[jid][hat]);
                                }
                            }
                        }
                    }
                }
            }
            // -------------------- Joystick Callback Emulation -------------------- //
            
            GLFW.TASK_DELEGATOR.runTasks();
            
            GLFW.WINDOWS.values().removeIf(window -> !window.isOpen());
            
            Thread.yield();
        }
    }
    
    /**
     * Destroys all remaining windows and cursors, restores any modified gamma
     * ramps and frees any other allocated resources. Once this function is
     * called, you must again call {@link #init()} successfully before you will
     * be able to use most GLFW functions.
     * <p>
     * If GLFW has been successfully initialized, this function should be
     * called before the application exits. If initialization fails, there is
     * no need to call this function, as it is called by {@link #init()} before
     * it returns failure.
     */
    public static void destroy()
    {
        GLFW.LOGGER.fine("GLFW Destruction");
        
        GLFW.EVENT_BUS.shutdown();
        
        GLFW.WINDOWS.values().forEach(Window::destroy);
        
        GLFW.MOUSE.destroy();
        GLFW.KEYBOARD.destroy();
        
        GLFW.JOYSTICKS.values().forEach(InputDevice::destroy);
        
        Callback[] callbacks = new Callback[] {
                glfwSetErrorCallback(null),
                glfwSetMonitorCallback(null),
                glfwSetJoystickCallback(null)
        };
        for (Callback callback : callbacks) if (callback != null) callback.free();
        
        glfwTerminate();
    }
    
    // -------------------- Monitor -------------------- //
    
    public static Collection<Monitor> monitors()
    {
        return Collections.unmodifiableCollection(GLFW.MONITORS.values());
    }
    
    public static Monitor getMonitor(int index)
    {
        for (Monitor monitor : GLFW.MONITORS.values()) if (index-- <= 0) return monitor;
        return GLFW.PRIMARY_MONITOR;
    }
    
    public static Monitor primaryMonitor()
    {
        return GLFW.PRIMARY_MONITOR;
    }
    
    // -------------------- Window -------------------- //
    
    static void attachWindow(long handle, Window window)
    {
        GLFW.WINDOWS.put(handle, window);
        
        glfwSetWindowCloseCallback(handle, GLFW::windowCloseCallback);
        glfwSetWindowFocusCallback(handle, GLFW::windowFocusCallback);
        glfwSetWindowIconifyCallback(handle, GLFW::windowIconifyCallback);
        glfwSetWindowMaximizeCallback(handle, GLFW::windowMaximizeCallback);
        glfwSetWindowPosCallback(handle, GLFW::windowPosCallback);
        glfwSetWindowSizeCallback(handle, GLFW::windowSizeCallback);
        glfwSetWindowContentScaleCallback(handle, GLFW::windowContentScaleCallback);
        glfwSetFramebufferSizeCallback(handle, GLFW::framebufferSizeCallback);
        glfwSetWindowRefreshCallback(handle, GLFW::windowRefreshCallback);
        glfwSetDropCallback(handle, GLFW::dropCallback);
        
        glfwSetCursorEnterCallback(handle, GLFW::mouseEnteredCallback);
        glfwSetCursorPosCallback(handle, GLFW::mousePosCallback);
        glfwSetScrollCallback(handle, GLFW::scrollCallback);
        glfwSetMouseButtonCallback(handle, GLFW::mouseButtonCallback);
        
        glfwSetKeyCallback(handle, GLFW::keyCallback);
        glfwSetCharCallback(handle, GLFW::charCallback);
    }
    
    // -------------------- Input -------------------- //
    
    /**
     * Returns the value of the GLFW timer. Unless the timer has been set using
     * {@link #setTime(double)}, the timer measures time elapsed since GLFW was
     * initialized.
     * <p>
     * The resolution of the timer is system dependent, but is usually on
     * the order of a few micro- or nanoseconds. It uses the highest-resolution
     * monotonic time source on each supported platform.
     * <p>
     * This function may be called from any thread. Reading and writing of the
     * internal timer offset is not atomic, so it needs to be externally
     * synchronized with calls to {@link #setTime(double)}.
     *
     * @return the current value, in seconds, or zero if an error occurred
     */
    public static double getTime()
    {
        return glfwGetTime();
    }
    
    /**
     * Sets the value of the GLFW timer. It then continues to count up from
     * that value. The value must be a positive finite number less than or
     * equal to 18446744073.0, which is approximately 584.5 years.
     * <p>
     * The upper limit of the timer is calculated as
     * <code>floor((2<sup>64</sup> - 1) / 10<sup>9</sup>)</code> and is due to
     * implementations storing nanoseconds in 64 bits. The limit may be
     * increased in the future.
     * <p>
     * This function may be called from any thread. Reading and writing of the
     * internal timer offset is not atomic, so it needs to be externally
     * synchronized with calls to {@link #getTime()}.
     *
     * @param time the new value, in seconds
     */
    public static void setTime(double time)
    {
        glfwSetTime(time);
    }
    
    public static boolean supportRawMouseInput()
    {
        return GLFW.SUPPORT_RAW_MOUSE_MOTION;
    }
    
    /**
     * Adds the specified SDL_GameControllerDB gamepad mappings.
     * <p>
     * This function parses the specified ASCII encoded string and updates
     * the internal list with any gamepad mappings it finds. This string may
     * contain either a single gamepad mapping or many mappings separated by
     * newlines. The parser supports the full format of the
     * {@code game_controller_db.txt} source file including empty lines and
     * comments.
     * <p>
     * See
     * <a target="_blank" href="http://www.glfw.org/docs/latest/input.html#gamepad_mapping">gamepad_mapping</a>
     * for a description of the format.
     * <p>
     * If there is already a gamepad mapping for a given GUID in the internal
     * list, it will be replaced by the one passed to this function. If the
     * library is terminated and re-initialized the internal list will revert
     * to the built-in default.
     *
     * @param filePath the path to the file containing the gamepad mappings
     * @return {@code true}, or {@code false} if an error occurred
     */
    @SuppressWarnings("ConstantConditions")
    public static boolean loadControllerMapping(String filePath)
    {
        return GLFW.TASK_DELEGATOR.waitReturnTask(() -> glfwUpdateGamepadMappings(IOUtil.resourceToByteBuffer(filePath)));
    }
    
    /**
     * @return The mouse instance.
     */
    public static Mouse mouse()
    {
        return GLFW.MOUSE;
    }
    
    /**
     * @return The keyboard instance.
     */
    public static Keyboard keyboard()
    {
        return GLFW.KEYBOARD;
    }
    
    /**
     * Returns the joystick at the provided index if one is connected in that
     * index. If a joystick is not present then {@code null} will be returned.
     * <p>
     * If the connected joystick is a Gamepad then the instance return will be
     * of type {@link Gamepad}.
     *
     * @param index The slot of the joystick.
     * @return The joystick/gamepad at the index or {@code null}.
     */
    public static Joystick getJoystick(int index)
    {
        return GLFW.JOYSTICKS.get(index);
    }
    
    /**
     * Returns the contents of the system clipboard, if it contains or is
     * convertible to a UTF-8 encoded string. If the clipboard is empty or if
     * its contents cannot be converted, {@code NULL} is returned and a
     * {@link org.lwjgl.glfw.GLFW#GLFW_FORMAT_UNAVAILABLE FORMAT_UNAVAILABLE}
     * error is generated.
     *
     * @return the contents of the clipboard as a UTF-8 encoded string, or {@code null} if an error occurred
     */
    @Nullable
    public static String getClipboardString()
    {
        return GLFW.TASK_DELEGATOR.waitReturnTask(() -> glfwGetClipboardString(0L));
    }
    
    /**
     * Sets the system clipboard to the specified, UTF-8 encoded string.
     *
     * @param string a UTF-8 encoded string
     */
    public static void setClipboardString(CharSequence string)
    {
        GLFW.TASK_DELEGATOR.runTask(() -> glfwSetClipboardString(0L, string));
    }
    
    // -------------------- Callbacks -------------------- //
    
    private static void errorCallback(int error, long description)
    {
        String msg = GLFWErrorCallback.getDescription(description);
        
        StringBuilder message = new StringBuilder();
        message.append("[LWJGL] ").append(GLFW.ERROR_CODES.get(error)).append(" error\n");
        message.append("\tDescription : ").append(msg).append('\n');
        message.append("\tStacktrace  :\n");
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        for (int i = 4; i < stack.length; i++)
        {
            message.append("\t\t");
            message.append(stack[i].toString());
            message.append('\n');
        }
        
        GLFW.LOGGER.severe(message);
    }
    
    private static void monitorCallback(long handle, int event)
    {
        switch (event)
        {
            case GLFW_CONNECTED -> {
                Monitor monitor = new Monitor(handle, GLFW.MONITORS.size());
                GLFW.MONITORS.put(handle, monitor);
                GLFW.EVENT_BUS.post(EventMonitorConnected.create(monitor));
            }
            case GLFW_DISCONNECTED -> {
                Monitor monitor = GLFW.MONITORS.remove(handle);
                GLFW.EVENT_BUS.post(EventMonitorDisconnected.create(monitor));
            }
        }
        
        GLFW.PRIMARY_MONITOR = GLFW.MONITORS.get(glfwGetPrimaryMonitor());
    }
    
    private static void joystickCallback(int jid, int event)
    {
        switch (event)
        {
            case GLFW_CONNECTED -> {
                Joystick joystick = glfwJoystickIsGamepad(jid) ? new Gamepad(jid) : new Joystick(jid, false);
                GLFW.JOYSTICKS.put(jid, joystick);
                GLFW.EVENT_BUS.post(EventJoystickConnected.create(joystick));
            }
            case GLFW_DISCONNECTED -> {
                Joystick joystick = GLFW.JOYSTICKS.remove(jid);
                GLFW.EVENT_BUS.post(EventJoystickDisconnected.create(joystick));
                joystick.destroy();
            }
        }
    }
    
    private static void joystickAxisCallback(int jid, int axis, float value)
    {
        Joystick joystick = GLFW.JOYSTICKS.get(jid);
        
        joystick.axisStateChanges.offer(new Pair<>(axis, value));
    }
    
    private static void joystickButtonCallback(int jid, int button, int action)
    {
        Joystick joystick = GLFW.JOYSTICKS.get(jid);
        
        joystick.buttonStateChanges.offer(new Pair<>(button, action));
    }
    
    private static void joystickHatCallback(int jid, int hat, int action)
    {
        Joystick joystick = GLFW.JOYSTICKS.get(jid);
        
        joystick.hatStateChanges.offer(new Pair<>(hat, action));
    }
    
    private static void windowCloseCallback(long handle)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        window._close = true;
    }
    
    private static void windowFocusCallback(long handle, boolean focused)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        window._focused = focused;
    }
    
    private static void windowIconifyCallback(long handle, boolean iconified)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        window._iconified = iconified;
    }
    
    private static void windowMaximizeCallback(long handle, boolean maximized)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        window._maximized = maximized;
    }
    
    private static void windowRefreshCallback(long handle)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        window._refresh = true;
    }
    
    private static void windowPosCallback(long handle, int x, int y)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        window._pos.set(x, y);
    }
    
    private static void windowSizeCallback(long handle, int width, int height)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        window._size.set(width, height);
    }
    
    private static void windowContentScaleCallback(long handle, float xScale, float yScale)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        window._scale.set(xScale, yScale);
    }
    
    private static void framebufferSizeCallback(long handle, int width, int height)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        window._fbSize.set(width, height);
    }
    
    private static void dropCallback(long handle, int count, long names)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        window._dropped = new String[count];
        PointerBuffer charPointers = MemoryUtil.memPointerBuffer(names, count);
        for (int i = 0; i < count; i++) window._dropped[i] = MemoryUtil.memUTF8(charPointers.get(i));
    }
    
    private static void mouseEnteredCallback(long handle, boolean entered)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        GLFW.MOUSE._enteredChanges.offer(new Pair<>(window, entered));
    }
    
    private static void mousePosCallback(long handle, double x, double y)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        if (!Double.isFinite(x) || !Double.isFinite(y)) return;
        
        GLFW.MOUSE._pos.set(x, y);
        GLFW.MOUSE._posW = window;
    }
    
    private static void scrollCallback(long handle, double dx, double dy)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        if (!Double.isFinite(dx) || !Double.isFinite(dy)) return;
        
        GLFW.MOUSE._scroll.add(dx, dy);
        GLFW.MOUSE._scrollW = window;
    }
    
    private static void mouseButtonCallback(long handle, int button, int action, int mods)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        GLFW.MOUSE.buttonStateChanges.offer(new Triple<>(window, Mouse.Button.get(button), action));
        
        Modifier.updateMods(mods);
    }
    
    private static void keyCallback(long handle, int key, int scancode, int action, int mods)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        GLFW.KEYBOARD.keyStateChanges.offer(new Triple<>(window, Keyboard.Key.get(key, scancode), action));
        
        Modifier.updateMods(mods);
    }
    
    private static void charCallback(long handle, int codePoint)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        GLFW.KEYBOARD._charChanges.offer(new Pair<>(window, Character.toString(codePoint)));
    }
}

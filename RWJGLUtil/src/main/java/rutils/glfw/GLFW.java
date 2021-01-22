package rutils.glfw;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Callback;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import rutils.Logger;
import rutils.Pair;
import rutils.TaskDelegator;
import rutils.glfw.event.GLFWEventBus;
import rutils.glfw.event.GLFWEventMonitorConnected;
import rutils.glfw.event.GLFWEventMonitorDisconnected;

import java.nio.IntBuffer;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

public final class GLFW
{
    private static final Logger LOGGER = new Logger();
    
    private static final Map<Integer, String> ERROR_CODES = APIUtil.apiClassTokens((field, value) -> 0x10000 < value && value < 0x20000, null, org.lwjgl.glfw.GLFW.class);
    
    public static final TaskDelegator TASK_DELEGATOR = new TaskDelegator();
    
    public static final GLFWEventBus EVENT_BUS = new GLFWEventBus(true);
    
    static final Map<Long, Monitor> MONITORS        = new LinkedHashMap<>();
    static       Monitor            PRIMARY_MONITOR = null;
    
    static final Map<Long, Window> WINDOWS     = new LinkedHashMap<>();
    static       Window            MAIN_WINDOW = null;
    
    static Mouse    MOUSE;
    static Keyboard KEYBOARD;
    
    static boolean SUPPORT_RAW_MOUSE_MOTION;
    
    private GLFW() {}
    
    // -------------------- Global Methods -------------------- //
    
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
        
        // GLFW.EVENT_BUS.register(Modifier.class);
        
        glfwSetErrorCallback(GLFW::errorCallback);
        glfwSetMonitorCallback(GLFW::monitorCallback);
        glfwSetJoystickCallback(GLFW::joystickCallback);
        
        loadMonitors();
        
        GLFW.MAIN_WINDOW = new WindowMain();
        
        GLFW.MOUSE    = new Mouse();
        GLFW.KEYBOARD = new Keyboard();
        
        GLFW.SUPPORT_RAW_MOUSE_MOTION = glfwRawMouseMotionSupported();
    }
    
    public static void eventLoop()
    {
        while (GLFW.WINDOWS.size() > 1)
        {
            glfwPollEvents();
            
            GLFW.TASK_DELEGATOR.runTasks();
            
            ArrayList<Long> toRemove = new ArrayList<>();
            GLFW.WINDOWS.values().forEach(w -> {
                if (!w.isOpen()) toRemove.add(w.handle);
            });
            toRemove.forEach(GLFW.WINDOWS::remove);
            
            Thread.yield();
        }
    }
    
    public static void destroy()
    {
        GLFW.LOGGER.fine("GLFW Destruction");
        
        GLFW.EVENT_BUS.shutdown();
        
        GLFW.WINDOWS.values().forEach(Window::destroy);
        
        GLFW.MOUSE.running    = false;
        GLFW.KEYBOARD.running = false;
        
        Callback[] callbacks = new Callback[] {
                glfwSetErrorCallback(null),
                glfwSetMonitorCallback(null),
                glfwSetJoystickCallback(null)
        };
        for (Callback callback : callbacks) if (callback != null) callback.free();
        
        glfwTerminate();
    }
    
    // -------------------- Monitor -------------------- //
    
    private static void loadMonitors()
    {
        GLFW.LOGGER.fine("Loading Monitors");
        
        PointerBuffer monitors = Objects.requireNonNull(glfwGetMonitors(), "No monitors found.");
        GLFW.MONITORS.clear();
        long handle;
        for (int i = 0, n = monitors.limit(); i < n; i++) GLFW.MONITORS.put(handle = monitors.get(), new Monitor(handle, i));
        GLFW.PRIMARY_MONITOR = GLFW.MONITORS.get(glfwGetPrimaryMonitor());
    }
    
    public static Collection<Monitor> monitors()
    {
        return Collections.unmodifiableCollection(GLFW.MONITORS.values());
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
    
    public static boolean supportRawMouseInput()
    {
        return GLFW.SUPPORT_RAW_MOUSE_MOTION;
    }
    
    public static Mouse mouse()
    {
        return GLFW.MOUSE;
    }
    
    public static Keyboard keyboard()
    {
        return GLFW.KEYBOARD;
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
                GLFW.EVENT_BUS.post(new GLFWEventMonitorConnected(monitor));
            }
            case GLFW_DISCONNECTED -> {
                Monitor monitor = GLFW.MONITORS.remove(handle);
                GLFW.EVENT_BUS.post(new GLFWEventMonitorDisconnected(monitor));
            }
        }
        
        GLFW.PRIMARY_MONITOR = GLFW.MONITORS.get(glfwGetPrimaryMonitor());
    }
    
    private static void joystickCallback(int jid, int event)
    {
        // TODO - Add SubscribeGLFWEvent to this somehow.
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
        
        Mouse.Input input = GLFW.MOUSE.inputMap.get(Mouse.Button.get(button));
        
        input._window = window;
        input._action = action;
        
        Modifier.updateMods(mods);
    }
    
    private static void keyCallback(long handle, int key, int scancode, int action, int mods)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        Keyboard.Input input = GLFW.KEYBOARD.inputMap.get(Keyboard.Key.get(key));
        
        input._window = window;
        input._action = action;
        
        Modifier.updateMods(mods);
    }
    
    private static void charCallback(long handle, int codePoint)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        GLFW.KEYBOARD._charChanges.offer(new Pair<>(window, Character.toString(codePoint)));
    }
}

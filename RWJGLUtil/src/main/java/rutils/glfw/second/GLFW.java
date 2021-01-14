package rutils.glfw.second;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Callback;
import rutils.Logger;
import rutils.TaskDelegator;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;

public final class GLFW
{
    private static final Logger LOGGER = new Logger();
    
    private static final Map<Integer, String> ERROR_CODES = APIUtil.apiClassTokens((field, value) -> 0x10000 < value && value < 0x20000, null, org.lwjgl.glfw.GLFW.class);
    
    public static final TaskDelegator TASK_DELEGATOR = new TaskDelegator();
    
    private static final LinkedHashMap<Long, Monitor> MONITORS       = new LinkedHashMap<>();
    private static       Monitor                      primaryMonitor = null;
    
    private static final LinkedHashMap<Long, Window> WINDOWS    = new LinkedHashMap<>();
    private static       Window                      mainWindow = null;
    
    private GLFW() {}
    
    // -------------------- Global Methods -------------------- //
    
    public static void init()
    {
        GLFW.LOGGER.fine("GLFW Initialization");
    
        GLFW.TASK_DELEGATOR.setThread();
    
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
        
        glfwSetErrorCallback(GLFW::errorCallback);
        glfwSetMonitorCallback(GLFW::monitorCallback);
        glfwSetJoystickCallback(GLFW::joystickCallback);
    
        loadMonitors();
        GLFW.mainWindow = attachWindow(new Window.Builder().name("WindowMain"));
    }
    
    public static void eventLoop() throws InterruptedException
    {
        attachWindow(new Window.Builder().name("Second"));
        
        while (GLFW.mainWindow.isOpen())
        {
            glfwPollEvents();
            
            GLFW.TASK_DELEGATOR.runTasks();
            
            ArrayList<Long> toRemove = new ArrayList<>();
            GLFW.WINDOWS.values().forEach(w -> {
                if (!w.isOpen()) toRemove.add(w.handle());
            });
            toRemove.forEach(GLFW.WINDOWS::remove);
            
            Thread.yield();
        }
    }
    
    public static void destroy()
    {
        GLFW.LOGGER.fine("GLFW Destruction");
        
        GLFW.WINDOWS.values().forEach(Window::destroy);
    
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
        GLFW.LOGGER.finer("Loading Monitors");
        
        PointerBuffer monitors = Objects.requireNonNull(glfwGetMonitors(), "No monitors found.");
        GLFW.MONITORS.clear();
        long handle;
        for (int i = 0, n = monitors.limit(); i < n; i++) GLFW.MONITORS.put(handle = monitors.get(), new Monitor(handle, i));
        GLFW.primaryMonitor = GLFW.MONITORS.get(glfwGetPrimaryMonitor());
    }
    
    public static Monitor primaryMonitor()
    {
        return GLFW.primaryMonitor;
    }
    
    // -------------------- Window -------------------- //
    
    private static Window attachWindow(Window.Builder builder)
    {
        Window window = new Window(builder);
        
        GLFW.WINDOWS.put(window.handle(), window);
        
        glfwSetWindowCloseCallback(window.handle(), GLFW::windowCloseCallback);
        glfwSetWindowPosCallback(window.handle(), GLFW::windowPosCallback);
        glfwSetWindowSizeCallback(window.handle(), GLFW::windowSizeCallback);
        glfwSetWindowFocusCallback(window.handle(), GLFW::windowFocusCallback);
        glfwSetWindowContentScaleCallback(window.handle(), GLFW::windowContentScaleCallback);
        glfwSetWindowIconifyCallback(window.handle(), GLFW::windowIconifyCallback);
        glfwSetWindowMaximizeCallback(window.handle(), GLFW::windowMaximizeCallback);
        glfwSetWindowRefreshCallback(window.handle(), GLFW::windowRefreshCallback);
        
        glfwSetFramebufferSizeCallback(window.handle(), GLFW::framebufferSizeCallback);
    
        glfwSetCursorEnterCallback(window.handle(), GLFW::mouseEnteredCallback);
        glfwSetCursorPosCallback(window.handle(), GLFW::mousePosCallback);
        glfwSetScrollCallback(window.handle(), GLFW::scrollCallback);
        glfwSetMouseButtonCallback(window.handle(), GLFW::mouseButtonCallback);
    
        glfwSetDropCallback(window.handle(), GLFW::dropCallback);
    
        glfwSetKeyCallback(window.handle(), GLFW::keyCallback);
        glfwSetCharCallback(window.handle(), GLFW::charCallback);
        glfwSetCharModsCallback(window.handle(), GLFW::charModsCallback);
        
        return window;
    }
    
    public static Window mainWindow()
    {
        return GLFW.mainWindow;
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
    
    private static void monitorCallback(long monitor, int event)
    {
        GLFW.LOGGER.finest("GLFW Callback: monitorHandle=%s event=%s", monitor, event); // TODO - Add GLFWEvent to this somehow.
        
        loadMonitors();
    }
    
    private static void joystickCallback(int jid, int event)
    {
        // TODO
    }
    
    private static void windowCloseCallback(long handle)
    {
        Window window = GLFW.WINDOWS.get(handle);
    
        window._close = true;
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
    
    private static void windowFocusCallback(long handle, boolean focused)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        // window._focused = focused; // TODO
    }
    
    private static void windowContentScaleCallback(long handle, float xscale, float yscale)
    {
        Window window = GLFW.WINDOWS.get(handle); // TODO
    }
    
    private static void windowIconifyCallback(long handle, boolean iconified)
    {
        Window window = GLFW.WINDOWS.get(handle); // TODO
    }
    
    private static void windowMaximizeCallback(long handle, boolean maximized)
    {
        Window window = GLFW.WINDOWS.get(handle); // TODO
    }
    
    private static void windowRefreshCallback(long handle)
    {
        Window window = GLFW.WINDOWS.get(handle); // TODO
    }
    
    private static void framebufferSizeCallback(long handle, int width, int height)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        window._fbSize.set(width, height);
    }
    
    private static void mouseEnteredCallback(long handle, boolean entered)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        // GLFW.mouse.window   = entered ? window : null; // TODO
        // GLFW.mouse._entered = entered;
    }
    
    private static void mousePosCallback(long handle, double x, double y)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        if (!Double.isFinite(x) || !Double.isFinite(y)) return;
        
        // GLFW.mouse.window = window; // TODO
        // GLFW.mouse._pos.set(x, y);
    }
    
    private static void scrollCallback(long handle, double dx, double dy)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        if (!Double.isFinite(dx) || !Double.isFinite(dy)) return;
        
        // GLFW.mouse.window = window; // TODO
        // GLFW.mouse._scroll.add(dx, dy);
    }
    
    private static void mouseButtonCallback(long handle, int button, int action, int mods)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        // GLFW.mouse.window = window;
        // GLFW.mouse.stateCallback(button, action, mods); // TODO
    }
    
    private static void dropCallback(long handle, int count, long names)
    {
        Window window = GLFW.WINDOWS.get(handle); // TODO
    }
    
    private static void keyCallback(long handle, int key, int scancode, int action, int mods)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        // GLFW.keyboard.stateCallback(key, action, mods); // TODO
    }
    
    private static void charCallback(long handle, int codePoint)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        // GLFW.keyboard.charCallback(codePoint); // TODO
    }
    
    private static void charModsCallback(long handle, int codepoint, int mods)
    {
        Window window = GLFW.WINDOWS.get(handle);
        
        // GLFW.keyboard.charModsCallback(codePoint); // TODO
    }
}

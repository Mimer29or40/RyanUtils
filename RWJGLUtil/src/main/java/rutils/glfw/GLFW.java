package rutils.glfw;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.*;
import rutils.Logger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.concurrent.SynchronousQueue;

import static org.lwjgl.glfw.GLFW.*;

public class GLFW
{
    private static final Logger LOGGER = new Logger();
    
    private static final LinkedHashMap<Long, GLFWMonitor> MONITORS = new LinkedHashMap<>();
    private static final LinkedHashMap<Long, GLFWWindow>  WINDOWS  = new LinkedHashMap<>();
    
    private static GLFWMonitor defaultMonitor = null;
    private static GLFWWindow  mainWindow     = null;
    
    private static boolean initialized = false;
    
    public static final ArrayDeque<GLFWWindow.Builder> BUILDER_QUEUE = new ArrayDeque<>();
    public static final SynchronousQueue<GLFWWindow>   WINDOW_QUEUE  = new SynchronousQueue<>();
    
    public static void init()
    {
        if (!GLFW.initialized)
        {
            GLFW.LOGGER.finest("GLFWEnum Initialization");
            
            GLFWErrorCallback.createPrint(System.err).set();
            if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFWEnum");
            
            glfwDefaultWindowHints();
            glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
            
            loadMonitors();
            
            createMainWindow();
            
            glfwSetMonitorCallback(GLFW::monitorCallback);
            glfwSetJoystickCallback(GLFW::joystickCallback);
            
            GLFW.initialized = true;
        }
    }
    
    /**
     * Generates all GLFWEnum events for a single frame to be consumed by the application.
     */
    public static void generateEvents(long time, long delta)
    {
        GLFW.MONITORS.values().forEach(m -> m.generateEvents(time, delta));
        GLFW.WINDOWS.values().forEach(w -> w.generateEvents(time, delta));
    }
    
    /**
     * Processes all pending events on the main thread. This will call the callback methods.
     *
     * @return false if no windows remain.
     */
    public static boolean processEvents() throws InterruptedException
    {
        glfwPollEvents();
        
        if (GLFW.BUILDER_QUEUE.peek() != null) GLFW.WINDOW_QUEUE.put(bindWindow(GLFW.BUILDER_QUEUE.poll()));
        
        GLFW.MONITORS.values().forEach(GLFWMonitor::processEvents);
    
        ArrayList<Long> toRemove = new ArrayList<>();
        GLFW.WINDOWS.values().forEach(w -> {
            w.processEvents();
            if (w.shouldClose()) toRemove.add(w.handle);
        });
        toRemove.forEach(GLFW::windowCloseCallback);
        
        return GLFW.WINDOWS.size() > 0;
    }
    
    /**
     * Destroys the glfw resources.
     */
    public static void destroy()
    {
        glfwSetMonitorCallback(null);
        glfwSetJoystickCallback(null);
        
        GLFW.WINDOWS.values().forEach(GLFWWindow::destroy);
        
        GLFWErrorCallback errorCallback = glfwSetErrorCallback(null);
        if (errorCallback != null) errorCallback.free();
        
        glfwTerminate();
    }
    
    // ---------- Monitor Things ---------- //
    
    public static GLFWMonitor defaultMonitor()
    {
        return GLFW.defaultMonitor;
    }
    
    public static GLFWMonitor monitor(int index)
    {
        for (GLFWMonitor monitor : GLFW.MONITORS.values())
        {
            if (monitor.index() == index) return monitor;
        }
        return GLFW.defaultMonitor;
    }
    
    private static void loadMonitors()
    {
        GLFW.LOGGER.finer("Loading Monitors");
        
        PointerBuffer monitors = Objects.requireNonNull(glfwGetMonitors(), "No monitors found.");
        GLFW.MONITORS.clear();
        long handle;
        for (int i = 0, n = monitors.limit(); i < n; i++) GLFW.MONITORS.put(handle = monitors.get(), new GLFWMonitor(handle, i));
        GLFW.defaultMonitor = GLFW.MONITORS.get(glfwGetPrimaryMonitor());
        
        GLFW.LOGGER.finest(GLFW.MONITORS.values());
    }
    
    // ---------- Window Things ---------- //
    
    public static GLFWWindow mainWindow()
    {
        return GLFW.mainWindow;
    }
    
    private static void createMainWindow()
    {
        // TODO - Load from config here
        GLFWWindow.Builder builder = new GLFWWindow.Builder();
        builder.title("Main Window");
        GLFW.mainWindow = bindWindow(builder);
    }
    
    public static GLFWWindow createWindow(GLFWWindow.Builder builder)
    {
        GLFW.BUILDER_QUEUE.add(builder);
        
        try
        {
            return GLFW.WINDOW_QUEUE.take();
        }
        catch (InterruptedException e)
        {
            GLFW.LOGGER.severe("Window could not be created, returning main window.");
            GLFW.LOGGER.severe(e);
        }
        return GLFW.mainWindow;
    }
    
    private static GLFWWindow bindWindow(GLFWWindow.Builder builder)
    {
        GLFWWindow window = new GLFWWindow(builder);
        
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
    
    // ---------- Callbacks ---------- //
    
    private static void monitorCallback(long monitorHandle, int event)
    {
        // TODO - Add GLFWEvent to this somehow.
        GLFW.LOGGER.finest("GLFWMonitor Callback: monitorHandle=%s event=%s", monitorHandle, event);
        
        loadMonitors();
        
        for (GLFWWindow window : GLFW.WINDOWS.values())
        {
            double nextPercent, maxPercent = 0.0;
            for (GLFWMonitor monitor : GLFW.MONITORS.values())
            {
                if ((nextPercent = monitor.isWindowIn(window)) > maxPercent)
                {
                    maxPercent     = nextPercent;
                    window.monitor = monitor;
                }
            }
            window.newPos.x = (window.monitor.width() - window.size.x) >> 1;
            window.newPos.y = (window.monitor.height() - window.size.y) >> 1;
        }
    }
    
    private static void joystickCallback(int jid, int event)
    {
        // TODO
    }
    
    private static void windowCloseCallback(long handle)
    {
        GLFWWindow window = GLFW.WINDOWS.remove(handle);
        if (window == GLFW.mainWindow)
        {
            for (GLFWWindow w : GLFW.WINDOWS.values())
            {
                GLFW.mainWindow = w;
                break;
            }
        }
        window.destroy();
    }
    
    private static void windowPosCallback(long handle, int x, int y)
    {
        GLFWWindow window = GLFW.WINDOWS.get(handle);
        window.newPos.set(x, y);
    }
    
    private static void windowSizeCallback(long handle, int width, int height)
    {
        GLFWWindow window = GLFW.WINDOWS.get(handle);
        window.newSize.set((int) (width / window.monitor.scaleX()), (int) (height / window.monitor.scaleY()));
    }
    
    private static void windowFocusCallback(long handle, boolean focused)
    {
        GLFWWindow window = GLFW.WINDOWS.get(handle);
        window.newFocused = focused;
    }
    
    private static void windowContentScaleCallback(long handle, float xscale, float yscale)
    {
        GLFWWindow window = GLFW.WINDOWS.get(handle); // TODO
    }
    
    private static void windowIconifyCallback(long handle, boolean iconified)
    {
        GLFWWindow window = GLFW.WINDOWS.get(handle); // TODO
    }
    
    private static void windowMaximizeCallback(long handle, boolean maximized)
    {
        GLFWWindow window = GLFW.WINDOWS.get(handle); // TODO
    }
    
    private static void windowRefreshCallback(long handle)
    {
        GLFWWindow window = GLFW.WINDOWS.get(handle); // TODO
    }
    
    private static void framebufferSizeCallback(long handle, int width, int height)
    {
        GLFWWindow window = GLFW.WINDOWS.get(handle);
        window.newFramebufferSize.set(width, height);
    }
    
    private static void mouseEnteredCallback(long handle, boolean entered)
    {
        GLFWWindow window = GLFW.WINDOWS.get(handle);
        // Mouse.enteredCallback(entered); // TODO
    }
    
    private static void mousePosCallback(long handle, double x, double y)
    {
        GLFWWindow window = GLFW.WINDOWS.get(handle);
        // Mouse.positionCallback(x, y); // TODO
    }
    
    private static void scrollCallback(long handle, double dx, double dy)
    {
        GLFWWindow window = GLFW.WINDOWS.get(handle);
        // Mouse.scrollCallback(dx, dy); // TODO
    }
    
    private static void mouseButtonCallback(long handle, int key, int action, int mods)
    {
        GLFWWindow window = GLFW.WINDOWS.get(handle);
        // Mouse.stateCallback(button, action, mods); // TODO
    }
    
    private static void dropCallback(long handle, int count, long names)
    {
        GLFWWindow window = GLFW.WINDOWS.get(handle); // TODO
    }
    
    private static void keyCallback(long handle, int key, int scancode, int action, int mods)
    {
        GLFWWindow window = GLFW.WINDOWS.get(handle);
        // Keyboard.stateCallback(key, action, mods); // TODO
    }
    
    private static void charCallback(long handle, int codePoint)
    {
        GLFWWindow window = GLFW.WINDOWS.get(handle);
        // Keyboard.charCallback(codePoint); // TODO
    }
    
    private static void charModsCallback(long handle, int codepoint, int mods)
    {
        GLFWWindow window = GLFW.WINDOWS.get(handle);
        // Keyboard.charModsCallback(codePoint); // TODO
    }
    
    private GLFW() {}
}

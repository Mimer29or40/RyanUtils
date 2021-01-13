package rutils.glfw.second;

import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.system.APIUtil;
import org.lwjgl.system.Callback;
import rutils.Logger;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;

public final class GLFW
{
    private static final Logger LOGGER = new Logger();
    
    private static final Map<Integer, String> ERROR_CODES = APIUtil.apiClassTokens((field, value) -> 0x10000 < value && value < 0x20000, null, org.lwjgl.glfw.GLFW.class);
    
    private static final CountDownLatch RENDER_THREAD_LATCH = new CountDownLatch(1);
    
    private static final LinkedHashMap<Long, Monitor> MONITORS       = new LinkedHashMap<>();
    private static       Monitor                      defaultMonitor = null;
    
    private static boolean mainWindow;
    
    private GLFW() {}
    
    // -------------------- Global Methods -------------------- //
    
    public static void init()
    {
        GLFW.LOGGER.finest("GLFW Initialization");
        
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
        
        glfwSetErrorCallback(GLFW::errorCallback);
        glfwSetMonitorCallback(GLFW::monitorCallback);
        glfwSetJoystickCallback(GLFW::joystickCallback);
        
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
    }
    
    public static void renderThread()
    {
        try
        {
        }
        catch (Exception e)
        {
            GLFW.LOGGER.severe(e);
        }
        finally
        {
            GLFW.LOGGER.fine("OpenGL Thread Stopping");
            
            org.lwjgl.opengl.GL.destroy();
            org.lwjgl.opengl.GL.setCapabilities(null);
            
            
            GLFW.RENDER_THREAD_LATCH.countDown();
        }
    }
    
    public static void eventLoop() throws InterruptedException
    {
        // final CountDownLatch RENDER_THREAD_LATCH = new CountDownLatch(1);
        //
        // new Thread(() -> {
        //     GLFWTests.LOGGER.fine("OpenGL Thread Started");
        //     GLFWWindow window  = rutils.glfw.GLFW.window();
        //     GLFWWindow window1 = rutils.glfw.GLFW.window1;
        //     try
        //     {
        //         window.show();
        //         window1.show();
        //         window.makeCurrent();
        //
        //         org.lwjgl.opengl.GL.createCapabilities();
        //
        //         // TextRenderer.init();
        //         // ShapeRenderer.init();
        //         // DebugRenderer.init();
        //
        //         long t, dt;
        //         long lastEvent = 0;
        //         long lastFrame = 0;
        //         long lastTitle = 0;
        //
        //         long frameTime;
        //         long totalTime = 0;
        //
        //         long minTime = Long.MAX_VALUE;
        //         long maxTime = Long.MIN_VALUE;
        //
        //         double d_t, d_dt;
        //
        //         int totalFrames = 0;
        //
        //         while (!(window = rutils.glfw.GLFW.window()).shouldClose())
        //         {
        //             t   = nanoseconds();
        //             d_t = t / 1_000_000_000D;
        //
        //             // profiler.startFrame();
        //             {
        //                 dt = t - lastEvent;
        //                 // try (Section events = profiler.startSection("GLFWEvents"))
        //                 {
        //                     d_dt = dt / 1_000_000_000D;
        //
        //                     lastEvent = t;
        //
        //                     // EngineEvents.clear(); // TODO
        //
        //                     // try (Section glfwEvents = profiler.startSection("GLFW Events"))
        //                     {
        //                         rutils.glfw.GLFW.generateFrameEvents(t, dt);
        //                     }
        //                     if (GLFWKeyboardKey.SPACE.down())
        //                     {
        //                         LOGGER.info("PRESSED");
        //                         window.captureMouse();
        //                     }
        //                     if (GLFWKeyboardKey.ESCAPE.down())
        //                     {
        //                         LOGGER.info("PRESSED1");
        //                         window.releaseMouse();
        //                     }
        //                     if (GLFWKeyboardKey.Q.down())
        //                     {
        //                         LOGGER.info("PRESSED");
        //                         window1.captureMouse();
        //                     }
        //                     if (GLFWKeyboardKey.W.down())
        //                     {
        //                         LOGGER.info("PRESSED1");
        //                         window1.releaseMouse();
        //                     }
        //
        //                     // try (Section mouse = profiler.startSection("Mouse"))
        //                     // {
        //                     //     GLFW.mouse().generateEvents(window, t, dt);
        //                     // }
        //                     //
        //                     // try (Section keyboard = profiler.startSection("GLFWKeyboard"))
        //                     // {
        //                     //     GLFW.keyboard().generateEvents(t, dt);
        //                     // }
        //                     //
        //                     // try (Section glfwWindow = profiler.startSection("GLFWWindow"))
        //                     // {
        //                     //     GLFWWindow.generateEvents(t, dt);
        //                     // }
        //
        //                     // try (Section stateHandling = profiler.startSection("State Handling")) // TODO
        //                     // {
        //                     //     for (EngineEvent event : EngineEvents.get())
        //                     //     {
        //                     //         GLFWTests.LOGGER.finer("State Handing GLFWEvent:", event);
        //                     //
        //                     //         Application.state.handleEvent(d_t, d_dt, event);
        //                     //     }
        //                     // }
        //                 }
        //
        //                 dt = t - lastFrame;
        //                 if (dt > 0) // TODO - Frame limiting
        //                 {
        //                     d_dt = dt / 1_000_000_000D;
        //
        //                     lastFrame = t;
        //
        //                     // try (Section render = profiler.startSection("Render"))
        //                     {
        //                         window.updateViewport();
        //
        //                         // try (Section state = profiler.startSection("State"))
        //                         // {
        //                         //     Application.state.render(d_t, d_dt);
        //                         // }
        //
        //                         // try (Section swap = profiler.startSection("Swap"))
        //                         {
        //                             window.swap();
        //                         }
        //                     }
        //
        //                 }
        //             }
        //             // profiler.endFrame();
        //
        //             frameTime = nanoseconds() - t;
        //             minTime   = Math.min(minTime, frameTime);
        //             maxTime   = Math.max(maxTime, frameTime);
        //             totalTime += frameTime;
        //             totalFrames++;
        //
        //             dt = t - lastTitle;
        //             if (dt >= 1_000_000_000L)
        //             {
        //                 lastTitle = t;
        //
        //                 totalTime /= totalFrames;
        //
        //                 // GLFWTests.LOGGER.info("FPS(%s) SPF(Avg: %s us, Min: %s us, Max: %s us)", totalFrames, totalTime / 1000D, minTime / 1000D, maxTime / 1000D);
        //                 // window.title(String.format());
        //
        //                 totalTime = 0;
        //
        //                 minTime = Long.MAX_VALUE;
        //                 maxTime = Long.MIN_VALUE;
        //
        //                 totalFrames = 0;
        //             }
        //
        //             Thread.yield();
        //         }
        //     }
        //     catch (Exception e)
        //     {
        //         GLFWTests.LOGGER.severe(e);
        //     }
        //     finally
        //     {
        //         GLFWTests.LOGGER.fine("OpenGL Thread Stopping");
        //
        //         org.lwjgl.opengl.GL.destroy();
        //         org.lwjgl.opengl.GL.setCapabilities(null);
        //
        //         window.close();
        //         window.unmakeCurrent();
        //
        //         RENDER_THREAD_LATCH.countDown();
        //     }
        //
        // }, "OpenGL").start();
        
        loadMonitors();
        
        // while (GLFW.WINDOWS.size() > 0)
        {
            glfwPollEvents();
            
            // GLFW.focusedWindow = GLFW.WINDOWS.get(glfwFocusWindow(this.handle);)
            
            // while (!GLFW.mainThreadDeque.isEmpty()) GLFW.mainThreadDeque.poll().run();
            
            // ArrayList<Long> toRemove = new ArrayList<>();
            // GLFW.WINDOWS.values().forEach(w -> {
            //     if (w.shouldClose()) toRemove.add(w.handle);
            // });
            // toRemove.forEach(GLFW::windowCloseCallback);
            
            Thread.yield();
        }
        
        GLFW.RENDER_THREAD_LATCH.await();
    }
    
    public static void destroy()
    {
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
        GLFW.defaultMonitor = GLFW.MONITORS.get(glfwGetPrimaryMonitor());
        
        GLFW.LOGGER.finest(GLFW.MONITORS.values());
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
        // TODO
    }
    
    private static void joystickCallback(int jid, int event)
    {
        // TODO
    }
}

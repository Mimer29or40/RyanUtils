package rutils.glfw;

import rutils.Logger;
import rutils.profiler.Profiler;
import rutils.profiler.Section;

import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;

public class GLFWTests
{
    private static final Logger LOGGER = new Logger();
    
    // private static boolean running;
    
    private static long startTime;
    
    public static long nanoseconds()
    {
        return GLFWTests.startTime > 0 ? System.nanoTime() - GLFWTests.startTime : -1L;
    }
    
    public static void main(String[] args)
    {
        Logger.setLevel(Level.ALL);
        
        Profiler profiler = Profiler.get("GLFWTests");
        
        GLFWTests.LOGGER.info("Application Started");
        
        // GLFWTests.running   = true;
        GLFWTests.startTime = System.nanoTime();
        
        try
        {
            GLFWTests.LOGGER.fine("Application Initialization");
            
            GLFW.init();
            
            final CountDownLatch latch = new CountDownLatch(1);
            
            new Thread(() -> {
                GLFWTests.LOGGER.fine("OpenGL Thread Started");
                GLFWWindow window = GLFW.mainWindow();
                try
                {
                    window.show();
                    window.makeCurrent();
                    
                    org.lwjgl.opengl.GL.createCapabilities();
                    
                    // TextRenderer.init();
                    // ShapeRenderer.init();
                    // DebugRenderer.init();
                    
                    long t, dt;
                    long lastEvent = 0;
                    long lastFrame = 0;
                    long lastTitle = 0;
                    
                    long frameTime;
                    long totalTime = 0;
                    
                    long minTime = Long.MAX_VALUE;
                    long maxTime = Long.MIN_VALUE;
                    
                    double d_t, d_dt;
                    
                    int totalFrames = 0;
                    
                    while (!(window = GLFW.mainWindow()).shouldClose())
                    {
                        t   = nanoseconds();
                        d_t = t / 1_000_000_000D;
                        
                        profiler.startFrame();
                        {
                            dt = t - lastEvent;
                            try (Section events = profiler.startSection("GLFWEvents"))
                            {
                                d_dt = dt / 1_000_000_000D;

                                lastEvent = t;

                                EngineEvents.clear();

                                try (Section mouse = profiler.startSection("Mouse"))
                                {
                                    Mouse.generateEvents(t, dt);
                                }

                                try (Section keyboard = profiler.startSection("Keyboard"))
                                {
                                    Keyboard.generateEvents(t, dt);
                                }

                                try (Section glfwWindow = profiler.startSection("GLFWWindow"))
                                {
                                    GLFWWindow.generateEvents(t, dt);
                                }

                                try (Section stateHandling = profiler.startSection("State Handling"))
                                {
                                    for (EngineEvent event : EngineEvents.get())
                                    {
                                        GLFWTests.LOGGER.finer("State Handing GLFWEvent:", event);

                                        Application.state.handleEvent(d_t, d_dt, event);
                                    }
                                }
                            }
                            
                            dt = t - lastFrame;
                            if (dt > 0) // TODO - Frame limiting
                            {
                                d_dt = dt / 1_000_000_000D;
                                
                                lastFrame = t;
                                
                                try (Section render = profiler.startSection("Render"))
                                {
                                    window.updateViewport();
                                    //
                                    //             // try (Section state = profiler.startSection("State"))
                                    //             // {
                                    //             //     Application.state.render(d_t, d_dt);
                                    //             // }
                                    //
                                    try (Section swap = profiler.startSection("Swap"))
                                    {
                                        window.swap();
                                    }
                                }
                                
                            }
                        }
                        profiler.endFrame();
                        
                        frameTime = nanoseconds() - t;
                        minTime   = Math.min(minTime, frameTime);
                        maxTime   = Math.max(maxTime, frameTime);
                        totalTime += frameTime;
                        totalFrames++;
                        
                        dt = t - lastTitle;
                        if (dt >= 1_000_000_000L)
                        {
                            lastTitle = t;
                            
                            totalTime /= totalFrames;
                            
                            GLFWTests.LOGGER.info("FPS(%s) SPF(Avg: %s us, Min: %s us, Max: %s us)", totalFrames, totalTime / 1000D, minTime / 1000D, maxTime / 1000D);
                            // window.title(String.format());
                            
                            totalTime = 0;
                            
                            minTime = Long.MAX_VALUE;
                            maxTime = Long.MIN_VALUE;
                            
                            totalFrames = 0;
                        }
                        
                        Thread.yield();
                    }
                }
                catch (Exception e)
                {
                    GLFWTests.LOGGER.severe(e);
                }
                finally
                {
                    GLFWTests.LOGGER.fine("OpenGL Thread Stopping");
                    
                    org.lwjgl.opengl.GL.destroy();
                    org.lwjgl.opengl.GL.setCapabilities(null);
                    
                    window.close();
                    window.unmakeCurrent();
                    
                    latch.countDown();
                }
                
            }, "OpenGL").start();
            
            while (GLFW.processEvents()) Thread.yield();
            latch.await();
        }
        catch (Exception e)
        {
            GLFWTests.LOGGER.severe(e);
        }
        finally
        {
            GLFWTests.LOGGER.fine("Application Stopping");
            
            GLFW.destroy();
        }
        
        GLFWTests.LOGGER.info("Application Finished");
    }
}

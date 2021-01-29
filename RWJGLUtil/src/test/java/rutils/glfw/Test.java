package rutils.glfw;

import rutils.Logger;
import rutils.glfw.event.GLFWEvent;
import rutils.glfw.event.GLFWEventInput;
import rutils.glfw.event.GLFWEventKeyboardKey;
import rutils.glfw.event.SubscribeGLFWEvent;

import java.util.logging.Level;

public class Test
{
    private static final Logger LOGGER = new Logger();
    
    static Window window;
    static Window window1;
    
    @SubscribeGLFWEvent
    public static void allEvents(GLFWEvent event)
    {
        // LOGGER.info(event);
    }
    
    @SubscribeGLFWEvent
    public static void allInputEvents(GLFWEventInput event)
    {
        LOGGER.info(event);
    }
    
    @SubscribeGLFWEvent
    public static void keyEvents(GLFWEventKeyboardKey event)
    {
        // if (event instanceof GLFWEventKeyboardKeyDown)
        // {
        //     switch (event.key())
        //     {
        //         case S -> GLFW.mouse().show(window);
        //         case H -> GLFW.mouse().hide(window);
        //         case C -> GLFW.mouse().capture(window);
        //     }
        // }
    }
    
    public static void main(String[] args)
    {
        Logger.setLevel(Level.ALL);
        Logger.setLevel(Level.FINER);
        
        // TODO - Make an input manager to listen for input events and transform them into game inputs. This is out of scope for this library
        
        try
        {
            Test.LOGGER.fine("Application Starting");
            
            GLFW.init();
            
            GLFW.EVENT_BUS.register(Test.class);
            
            window  = new Window.Builder().name("First").build();
            // window1 = new Window.Builder().name("Second").build();
            
            GLFW.eventLoop();
        }
        catch (Throwable e)
        {
            Test.LOGGER.severe(e);
        }
        finally
        {
            Test.LOGGER.fine("Application Stopping");
            
            GLFW.destroy();
        }
    }
}

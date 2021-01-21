package rutils.glfw;

import rutils.Logger;
import rutils.glfw.second.GLFW;
import rutils.glfw.second.event.GLFWEvent;
import rutils.glfw.second.event.SubscribeGLFWEvent;

import java.util.logging.Level;

public class Test
{
    private static final Logger LOGGER = new Logger();
    
    @SubscribeGLFWEvent
    public static void allEvents(GLFWEvent event)
    {
        // LOGGER.info(event);
    }
    
    public static void main(String[] args)
    {
        Logger.setLevel(Level.FINEST);
        
        try
        {
            Test.LOGGER.fine("Application Starting");
        
            GLFW.init();
            
            GLFW.EVENT_BUS.register(Test.class);
            
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

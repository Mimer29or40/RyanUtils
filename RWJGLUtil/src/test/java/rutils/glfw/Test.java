package rutils.glfw;

import rutils.Logger;
import rutils.glfw.second.GLFW;

import java.util.logging.Level;

public class Test
{
    private static final Logger LOGGER = new Logger();
    
    public static void main(String[] args)
    {
        Logger.setLevel(Level.FINER);
        
        try
        {
            Test.LOGGER.fine("Application Starting");
        
            GLFW.init();
            
            GLFW.eventLoop();
        }
        catch (Exception e)
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

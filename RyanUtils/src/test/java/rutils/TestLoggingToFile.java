package rutils;

import java.util.logging.Level;

public class TestLoggingToFile
{
    private static final Logger LOGGER = new Logger();
    
    public static void main(String[] args)
    {
        Logger.addLogFile("testLoggingToFile.log");
        
        Logger.setLevel(Level.ALL);
        
        LOGGER.finest("LOG TEST");
        LOGGER.finer("LOG TEST");
        LOGGER.fine("LOG TEST");
        LOGGER.config("LOG TEST");
        LOGGER.info("LOG TEST");
        LOGGER.warning("LOG TEST");
        LOGGER.severe("LOG TEST");
    }
}

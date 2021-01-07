package rutils;

import java.util.logging.Level;
// import java.util.logging.Logger;

public class LoggingTest
{
    // static Logger LOGGER = Logger.getLogger(ClassUtil.getCallingClassName());
    static Logger LOGGER = new Logger();
    
    public static void main(String[] args)
    {
        // // Properties preferences = new Properties();
        // try
        // {
        //     FileInputStream configFile = new FileInputStream(IOUtil.getPath("logging.properties").toString());
        //     // preferences.load(configFile);
        //     LogManager.getLogManager().readConfiguration(configFile);
        // }
        // catch (IOException ex)
        // {
        //     System.out.println("WARNING: Could not open configuration file");
        //     System.out.println("WARNING: Logging not configured (console output only)");
        // }
        
        Logger.addLogFile("run/testLoggingToFile.log");

        Logger.setLevel(Level.ALL);
    
        LOGGER.finest("Message");
        LOGGER.finer("Message");
        LOGGER.fine("Message");
        LOGGER.config("Message");
        LOGGER.info("Message");
        LOGGER.warning("Message");
        LOGGER.severe("Message");
        System.out.println(LOGGER.getClass().getSimpleName());
    }
}

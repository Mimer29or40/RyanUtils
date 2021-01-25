package rutils;

import org.junit.jupiter.api.Test;

import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LoggerTest
{
    @Test
    void level()
    {
        Level expected, actual;
        
        expected = Level.INFO;
        Logger.setLevel(expected);
        actual   = Logger.getLevel();
        assertEquals(expected, actual);
        
        expected = Level.ALL;
        Logger.setLevel(expected);
        actual = Logger.getLevel();
        assertEquals(expected, actual);
        
        expected = Level.FINE;
        Logger.setLevel(expected);
        actual = Logger.getLevel();
        assertEquals(expected, actual);
        
        Logger.setLevel(Level.INFO);
    }
    
    @Test
    void filters()
    {
        Logger.addWhitelistFilter("Thing");
        Logger.removeWhitelistFilter("Thing");
        
        Logger.addBlacklistFilter("Thing");
        Logger.removeBlacklistFilter("Thing");
    }
    
    @Test
    void addLogFile()
    {
        Logger.addLogFile("out/testLoggingToFile.log");
    
        Logger.setLevel(Level.ALL);
    
        Logger logger = new Logger();
    
        logger.finest("LOG TEST");
        logger.finer("LOG TEST");
        logger.fine("LOG TEST");
        logger.config("LOG TEST");
        logger.info("LOG TEST");
        logger.warning("LOG TEST");
        logger.severe("LOG TEST");
    }
    
    @Test
    void log()
    {
        Logger.setLevel(Level.ALL);
        
        Logger logger = new Logger();
        
        logger.all("Object");
        logger.all("Object1", "Object2");
        logger.all("Format %s %s", "Object1", "Object2");
        
        logger.severe("Object");
        logger.severe("Object1", "Object2");
        logger.severe("Format %s %s", "Object1", "Object2");
        
        logger.warning("Object");
        logger.warning("Object1", "Object2");
        logger.warning("Format %s %s", "Object1", "Object2");
        
        logger.info("Object");
        logger.info("Object1", "Object2");
        logger.info("Format %s %s", "Object1", "Object2");
        
        logger.config("Object");
        logger.config("Object1", "Object2");
        logger.config("Format %s %s", "Object1", "Object2");
        
        logger.fine("Object");
        logger.fine("Object1", "Object2");
        logger.fine("Format %s %s", "Object1", "Object2");
        
        logger.finer("Object");
        logger.finer("Object1", "Object2");
        logger.finer("Format %s %s", "Object1", "Object2");
        
        logger.finest("Object");
        logger.finest("Object1", "Object2");
        logger.finest("Format %s %s", "Object1", "Object2");
    }
}

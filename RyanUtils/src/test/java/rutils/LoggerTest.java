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
        actual = Logger.getLevel();
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
    
    @Test
    void colors()
    {
        Logger logger = new Logger();
        
        String[] tags;
        
        tags = new String[] {
                Logger.BLACK,
                Logger.RED,
                Logger.GREEN,
                Logger.YELLOW,
                Logger.BLUE,
                Logger.PURPLE,
                Logger.CYAN,
                Logger.WHITE
        };
        for (String tag : tags) logger.info(tag + "Text Color");
        
        tags = new String[] {
                Logger.BLACK_BOLD,
                Logger.RED_BOLD,
                Logger.GREEN_BOLD,
                Logger.YELLOW_BOLD,
                Logger.BLUE_BOLD,
                Logger.PURPLE_BOLD,
                Logger.CYAN_BOLD,
                Logger.WHITE_BOLD
        };
        for (String tag : tags) logger.info(tag + "Bold Text Color");
        
        tags = new String[] {
                Logger.BLACK_UNDERLINED,
                Logger.RED_UNDERLINED,
                Logger.GREEN_UNDERLINED,
                Logger.YELLOW_UNDERLINED,
                Logger.BLUE_UNDERLINED,
                Logger.PURPLE_UNDERLINED,
                Logger.CYAN_UNDERLINED,
                Logger.WHITE_UNDERLINED
        };
        for (String tag : tags) logger.info(tag + "Underline Text Color");
        
        tags = new String[] {
                Logger.BLACK_BACKGROUND,
                Logger.RED_BACKGROUND,
                Logger.GREEN_BACKGROUND,
                Logger.YELLOW_BACKGROUND,
                Logger.BLUE_BACKGROUND,
                Logger.PURPLE_BACKGROUND,
                Logger.CYAN_BACKGROUND,
                Logger.WHITE_BACKGROUND
        };
        for (String tag : tags) logger.info(tag + "Background Text Color");
        
        tags = new String[] {
                Logger.BLACK_BRIGHT,
                Logger.RED_BRIGHT,
                Logger.GREEN_BRIGHT,
                Logger.YELLOW_BRIGHT,
                Logger.BLUE_BRIGHT,
                Logger.PURPLE_BRIGHT,
                Logger.CYAN_BRIGHT,
                Logger.WHITE_BRIGHT
        };
        for (String tag : tags) logger.info(tag + "Bright Text Color");
        
        tags = new String[] {
                Logger.BLACK_BOLD_BRIGHT,
                Logger.RED_BOLD_BRIGHT,
                Logger.GREEN_BOLD_BRIGHT,
                Logger.YELLOW_BOLD_BRIGHT,
                Logger.BLUE_BOLD_BRIGHT,
                Logger.PURPLE_BOLD_BRIGHT,
                Logger.CYAN_BOLD_BRIGHT,
                Logger.WHITE_BOLD_BRIGHT
        };
        for (String tag : tags) logger.info(tag + "Bright Bold Text Color");
        
        tags = new String[] {
                Logger.BLACK_BACKGROUND_BRIGHT,
                Logger.RED_BACKGROUND_BRIGHT,
                Logger.GREEN_BACKGROUND_BRIGHT,
                Logger.YELLOW_BACKGROUND_BRIGHT,
                Logger.BLUE_BACKGROUND_BRIGHT,
                Logger.PURPLE_BACKGROUND_BRIGHT,
                Logger.CYAN_BACKGROUND_BRIGHT,
                Logger.WHITE_BACKGROUND_BRIGHT
        };
        for (String tag : tags) logger.info(tag + "Bright Background Text Color");
    }
}

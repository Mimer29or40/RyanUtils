package rutils;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.Level;
import java.util.regex.Pattern;

import static rutils.StrUtil.getCurrentTimeString;

/**
 * A simple logging implementation for use in Engine classes. Only one logger should be used per file as the file's class path is in the message.
 * <p>
 * Use the global {@link #setLevel} to allow for log message to be displayed to the console.
 */
@SuppressWarnings("unused")
public class Logger
{
    private static final Logger LOGGER = new Logger();
    
    private static final Pattern FORMAT_PATTERN = Pattern.compile("%(\\d+\\$)?([-#+ 0,(<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])"); // Taken from java.lang.Formatter
    private static final Pattern SPLIT_PATTERN  = Pattern.compile("(\\n|\\n\\r|\\r\\n)");
    
    private static final ArrayList<OutputStream> OUTPUT_STREAMS = new ArrayList<>();
    
    private static final HashMap<String, Pattern> WHITELIST = new HashMap<>();
    private static final HashMap<String, Pattern> BLACKLIST = new HashMap<>();
    
    private static Level level = Level.INFO;
    
    static
    {
        Logger.OUTPUT_STREAMS.add(new BufferedOutputStream(System.out));
    }
    
    
    /**
     * @return The global logging level.
     */
    public static Level getLevel()
    {
        return Logger.level;
    }
    
    /**
     * Sets the global logging level.
     *
     * @param level The new level.
     */
    public static void setLevel(Level level)
    {
        Logger.LOGGER.finest("Setting Global Log Level", level);
        
        Logger.level = level;
    }
    
    /**
     * Adds a filter to the loggers. Only Loggers in this filter will be logged.
     *
     * @param regex The filter string.
     */
    public static void addWhitelistFilter(String regex)
    {
        Logger.LOGGER.finest("Adding filter the Whitelist", regex);
        
        Logger.WHITELIST.put(regex, Pattern.compile(regex));
    }
    
    /**
     * Removes the filter from the Whitelist.
     *
     * @param regex The filter String.
     */
    public static void removeWhitelistFilter(String regex)
    {
        Logger.LOGGER.finest("Removing filter from the Whitelist", regex);
        
        Logger.WHITELIST.remove(regex);
    }
    
    /**
     * Adds a filter to the loggers. Only Loggers not in this filter will be logged.
     *
     * @param regex The filter string.
     */
    public static void addBlacklistFilter(String regex)
    {
        Logger.LOGGER.finest("Adding filter to the Blacklist", regex);
        
        Logger.BLACKLIST.put(regex, Pattern.compile(regex));
    }
    
    /**
     * Removes the filter from the Blacklist.
     *
     * @param regex The filter String.
     */
    public static void removeBlacklistFilter(String regex)
    {
        Logger.LOGGER.finest("Removing filter from the Blacklist", regex);
        
        Logger.BLACKLIST.remove(regex);
    }
    
    private static boolean applyFilters(String name)
    {
        for (Pattern pattern : Logger.BLACKLIST.values())
        {
            if (pattern.matcher(name).find()) return true;
        }
        if (Logger.WHITELIST.size() > 0)
        {
            for (Pattern pattern : Logger.WHITELIST.values())
            {
                if (pattern.matcher(name).find()) return false;
            }
            return true;
        }
        return false;
    }
    
    public static void addLogFile(String file)
    {
        try
        {
            rotate(file, 0);
            
            Logger.OUTPUT_STREAMS.add(new FileOutputStream(file));
        }
        catch (IOException e)
        {
            Logger.LOGGER.warning(e);
        }
    }
    
    private static void rotate(String baseFilePath, int level) throws IOException
    {
        if (level >= 5) return;
        
        String currFile = baseFilePath + (level > 0 ? "." + level : "");
        String prevFile = baseFilePath + ((level - 1) > 0 ? "." + (level - 1) : "");
        
        Path currFilePath = Path.of(currFile);
        Path prevFilePath = Path.of(prevFile);
        
        if (Files.exists(currFilePath))
        {
            rotate(baseFilePath, level + 1);
        }
        else
        {
            Files.createFile(currFilePath);
        }
        if (level > 0) Files.copy(prevFilePath, new FileOutputStream(currFile));
    }
    
    private final String name;
    
    /**
     * Creates a new logger whose name is the class path to the file.
     */
    public Logger()
    {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        
        this.name = elements.length > 2 ? elements[2].getClassName() : "";
    }
    
    @Override
    public String toString()
    {
        return "Logger{" + this.name + '}';
    }
    
    private void logConsoleImpl(OutputStream outputStream, StringBuilder prefix, Level level, String message) throws IOException
    {
        String prefixString;
        if (level.intValue() >= Level.SEVERE.intValue())
        {
            prefixString = Logger.RED + prefix.toString();
        }
        else if (level.intValue() >= Level.WARNING.intValue())
        {
            prefixString = Logger.YELLOW + prefix.toString();
        }
        else
        {
            prefixString = prefix.toString();
        }
        
        StringBuilder fullMessage = new StringBuilder();
        for (String line : SPLIT_PATTERN.split(message)) fullMessage.append(prefixString).append(line).append(Logger.RESET).append(System.lineSeparator());
    
        outputStream.write(fullMessage.toString().getBytes());
        outputStream.flush();
    }
    
    private void logFileImpl(OutputStream outputStream, StringBuilder prefix, Level level, String message) throws IOException
    {
        StringBuilder fullMessage = new StringBuilder();
        for (String line : SPLIT_PATTERN.split(message)) fullMessage.append(prefix).append(line).append(System.lineSeparator());
    
        outputStream.write(fullMessage.toString().getBytes());
        outputStream.flush();
    }
    
    private void logImpl(Level level, String message)
    {
        try
        {
            StringBuilder prefix = new StringBuilder();
            prefix.append('[').append(getCurrentTimeString()).append("] [").append(Thread.currentThread().getName()).append('/').append(level).append(']');
            if (!this.name.equals("")) prefix.append(" [").append(this.name).append(']');
            prefix.append(": ");

            for (OutputStream outputStream : Logger.OUTPUT_STREAMS)
            {
                if (outputStream instanceof BufferedOutputStream)
                {
                    logConsoleImpl(outputStream, prefix, level, message);
                }
                else if (outputStream instanceof FileOutputStream)
                {
                    logFileImpl(outputStream, prefix, level, message);
                }
            }
        }
        catch (IOException ignored) { }
    }
    
    /**
     * Logs the objects separated by spaces at the level specified.
     *
     * @param level   The level to log at.
     * @param objects The objects to log.
     */
    public void log(Level level, Object... objects)
    {
        if (level.intValue() < Logger.level.intValue()) return;
        if (applyFilters(this.name)) return;
        int n = objects.length;
        if (n == 0) return;
        StringBuilder builder = new StringBuilder().append(printObject(objects[0]));
        for (int i = 1; i < n; i++) builder.append(' ').append(printObject(objects[i]));
        logImpl(level, builder.toString());
    }
    
    /**
     * Logs the objects separated by spaces at the level specified. If the format string has format characters in it, then it will be used to format the objects.
     *
     * @param level   The level to log at.
     * @param format  The optional format string.
     * @param objects The objects to log.
     */
    public void log(Level level, String format, Object... objects)
    {
        if (level.intValue() < Logger.level.intValue()) return;
        if (applyFilters(this.name)) return;
        if (Logger.FORMAT_PATTERN.matcher(format).find())
        {
            logImpl(level, String.format(format, objects));
        }
        else
        {
            StringBuilder builder = new StringBuilder(format);
            for (Object object : objects) builder.append(' ').append(printObject(object));
            logImpl(level, builder.toString());
        }
    }
    
    /**
     * Logs the objects separated by spaces at {@link Level#SEVERE}.
     *
     * @param objects The objects to log.
     */
    public void severe(Object... objects)
    {
        log(Level.SEVERE, objects);
    }
    
    /**
     * Logs the objects separated by spaces at {@link Level#SEVERE}. If the format string has format characters in it, then it will be used to format the objects.
     *
     * @param format  The optional format string.
     * @param objects The objects to log.
     */
    public void severe(String format, Object... objects)
    {
        log(Level.SEVERE, format, objects);
    }
    
    /**
     * Logs the objects separated by spaces at {@link Level#WARNING}.
     *
     * @param objects The objects to log.
     */
    public void warning(Object... objects)
    {
        log(Level.WARNING, objects);
    }
    
    /**
     * Logs the objects separated by spaces at {@link Level#WARNING}. If the format string has format characters in it, then it will be used to format the objects.
     *
     * @param format  The optional format string.
     * @param objects The objects to log.
     */
    public void warning(String format, Object... objects)
    {
        log(Level.WARNING, format, objects);
    }
    
    /**
     * Logs the objects separated by spaces at {@link Level#INFO}.
     *
     * @param objects The objects to log.
     */
    public void info(Object... objects)
    {
        log(Level.INFO, objects);
    }
    
    /**
     * Logs the objects separated by spaces at {@link Level#INFO}. If the format string has format characters in it, then it will be used to format the objects.
     *
     * @param format  The optional format string.
     * @param objects The objects to log.
     */
    public void info(String format, Object... objects)
    {
        log(Level.INFO, format, objects);
    }
    
    /**
     * Logs the objects separated by spaces at {@link Level#CONFIG}.
     *
     * @param objects The objects to log.
     */
    public void config(Object... objects)
    {
        log(Level.CONFIG, objects);
    }
    
    /**
     * Logs the objects separated by spaces at {@link Level#CONFIG}. If the format string has format characters in it, then it will be used to format the objects.
     *
     * @param format  The optional format string.
     * @param objects The objects to log.
     */
    public void config(String format, Object... objects)
    {
        log(Level.CONFIG, format, objects);
    }
    
    /**
     * Logs the objects separated by spaces at {@link Level#FINE}.
     *
     * @param objects The objects to log.
     */
    public void fine(Object... objects)
    {
        log(Level.FINE, objects);
    }
    
    /**
     * Logs the objects separated by spaces at {@link Level#FINE}. If the format string has format characters in it, then it will be used to format the objects.
     *
     * @param format  The optional format string.
     * @param objects The objects to log.
     */
    public void fine(String format, Object... objects)
    {
        log(Level.FINE, format, objects);
    }
    
    /**
     * Logs the objects separated by spaces at {@link Level#FINER}.
     *
     * @param objects The objects to log.
     */
    public void finer(Object... objects)
    {
        log(Level.FINER, objects);
    }
    
    /**
     * Logs the objects separated by spaces at {@link Level#FINER}. If the format string has format characters in it, then it will be used to format the objects.
     *
     * @param format  The optional format string.
     * @param objects The objects to log.
     */
    public void finer(String format, Object... objects)
    {
        log(Level.FINER, format, objects);
    }
    
    /**
     * Logs the objects separated by spaces at {@link Level#FINEST}.
     *
     * @param objects The objects to log.
     */
    public void finest(Object... objects)
    {
        log(Level.FINEST, objects);
    }
    
    /**
     * Logs the objects separated by spaces at {@link Level#FINEST}. If the format string has format characters in it, then it will be used to format the objects.
     *
     * @param format  The optional format string.
     * @param objects The objects to log.
     */
    public void finest(String format, Object... objects)
    {
        log(Level.FINEST, format, objects);
    }
    
    /**
     * Logs the object at {@link Level#ALL}.
     *
     * @param object The objects to log.
     */
    public void all(Object object)
    {
        log(Level.ALL, object);
    }
    
    /**
     * Logs the objects separated by spaces at {@link Level#ALL}.
     *
     * @param objects The objects to log.
     */
    public void all(Object... objects)
    {
        log(Level.ALL, objects);
    }
    
    /**
     * Logs the objects separated by spaces at {@link Level#ALL}. If the format string has format characters in it, then it will be used to format the objects.
     *
     * @param format  The optional format string.
     * @param objects The objects to log.
     */
    public void all(String format, Object... objects)
    {
        log(Level.ALL, format, objects);
    }
    
    private String printObject(Object o)
    {
        if (o instanceof Throwable)
        {
            return printThrowable((Throwable) o);
        }
        else if (o instanceof boolean[])
        {
            return Arrays.toString((boolean[]) o);
        }
        else if (o instanceof byte[])
        {
            return Arrays.toString((byte[]) o);
        }
        else if (o instanceof short[])
        {
            return Arrays.toString((short[]) o);
        }
        else if (o instanceof char[])
        {
            return Arrays.toString((char[]) o);
        }
        else if (o instanceof int[])
        {
            return Arrays.toString((int[]) o);
        }
        else if (o instanceof long[])
        {
            return Arrays.toString((long[]) o);
        }
        else if (o instanceof float[])
        {
            return Arrays.toString((float[]) o);
        }
        else if (o instanceof double[])
        {
            return Arrays.toString((double[]) o);
        }
        else if (o instanceof Object[])
        {
            return Arrays.toString((Object[]) o);
        }
        return String.valueOf(o);
    }
    
    private String printThrowable(Throwable throwable)
    {
        Set<Throwable> dejaVu = Collections.newSetFromMap(new IdentityHashMap<>());
        dejaVu.add(throwable);
        
        StringBuilder builder = new StringBuilder();
        
        // Print our stack trace
        builder.append(throwable.toString()).append(System.lineSeparator());
        StackTraceElement[] trace = throwable.getStackTrace();
        for (StackTraceElement traceElement : trace) builder.append("\tat ").append(traceElement).append(System.lineSeparator());
        
        // Print suppressed exceptions, if any
        for (Throwable se : throwable.getSuppressed()) enclosedStackTrace(builder, se, trace, "Suppressed: ", "\t", dejaVu);
        
        // Print cause, if any
        Throwable ourCause = throwable.getCause();
        if (ourCause != null) enclosedStackTrace(builder, ourCause, trace, "Caused by: ", "", dejaVu);
        
        return builder.toString();
    }
    
    private void enclosedStackTrace(StringBuilder builder, Throwable throwable, StackTraceElement[] enclosingTrace, String caption, String prefix, Set<Throwable> dejaVu)
    {
        if (dejaVu.contains(throwable))
        {
            builder.append("\t[CIRCULAR REFERENCE:").append(throwable).append("]").append(System.lineSeparator());
        }
        else
        {
            dejaVu.add(throwable);
            // Compute number of frames in common between this and enclosing trace
            StackTraceElement[] trace = throwable.getStackTrace();
            
            int m = trace.length - 1;
            int n = enclosingTrace.length - 1;
            while (m >= 0 && n >= 0 && trace[m].equals(enclosingTrace[n]))
            {
                m--;
                n--;
            }
            int framesInCommon = trace.length - 1 - m;
            
            // Print our stack trace
            builder.append(prefix).append(caption).append(throwable).append(System.lineSeparator());
            for (int i = 0; i <= m; i++) builder.append(prefix).append("\tat ").append(trace[i]).append(System.lineSeparator());
            if (framesInCommon != 0) builder.append(prefix).append("\t... ").append(framesInCommon).append(" more").append(System.lineSeparator());
            
            // Print suppressed exceptions, if any
            for (Throwable se : throwable.getSuppressed()) enclosedStackTrace(builder, throwable, trace, "Suppressed: ", prefix + "\t", dejaVu);
            
            // Print cause, if any
            Throwable ourCause = throwable.getCause();
            if (ourCause != null) enclosedStackTrace(builder, ourCause, trace, "Caused by: ", prefix, dejaVu);
        }
    }
    
    // Reset
    public static final String RESET = "\033[0m";
    
    // Regular Colors
    public static final String BLACK  = "\033[0;30m";
    public static final String RED    = "\033[0;31m";
    public static final String GREEN  = "\033[0;32m";
    public static final String YELLOW = "\033[0;33m";
    public static final String BLUE   = "\033[0;34m";
    public static final String PURPLE = "\033[0;35m";
    public static final String CYAN   = "\033[0;36m";
    public static final String WHITE  = "\033[0;37m";
    
    // Bold
    public static final String BLACK_BOLD  = "\033[1;30m";
    public static final String RED_BOLD    = "\033[1;31m";
    public static final String GREEN_BOLD  = "\033[1;32m";
    public static final String YELLOW_BOLD = "\033[1;33m";
    public static final String BLUE_BOLD   = "\033[1;34m";
    public static final String PURPLE_BOLD = "\033[1;35m";
    public static final String CYAN_BOLD   = "\033[1;36m";
    public static final String WHITE_BOLD  = "\033[1;37m";
    
    // Underline
    public static final String BLACK_UNDERLINED  = "\033[4;30m";
    public static final String RED_UNDERLINED    = "\033[4;31m";
    public static final String GREEN_UNDERLINED  = "\033[4;32m";
    public static final String YELLOW_UNDERLINED = "\033[4;33m";
    public static final String BLUE_UNDERLINED   = "\033[4;34m";
    public static final String PURPLE_UNDERLINED = "\033[4;35m";
    public static final String CYAN_UNDERLINED   = "\033[4;36m";
    public static final String WHITE_UNDERLINED  = "\033[4;37m";
    
    // Background
    public static final String BLACK_BACKGROUND  = "\033[40m";
    public static final String RED_BACKGROUND    = "\033[41m";
    public static final String GREEN_BACKGROUND  = "\033[42m";
    public static final String YELLOW_BACKGROUND = "\033[43m";
    public static final String BLUE_BACKGROUND   = "\033[44m";
    public static final String PURPLE_BACKGROUND = "\033[45m";
    public static final String CYAN_BACKGROUND   = "\033[46m";
    public static final String WHITE_BACKGROUND  = "\033[47m";
    
    // High Intensity
    public static final String BLACK_BRIGHT  = "\033[0;90m";
    public static final String RED_BRIGHT    = "\033[0;91m";
    public static final String GREEN_BRIGHT  = "\033[0;92m";
    public static final String YELLOW_BRIGHT = "\033[0;93m";
    public static final String BLUE_BRIGHT   = "\033[0;94m";
    public static final String PURPLE_BRIGHT = "\033[0;95m";
    public static final String CYAN_BRIGHT   = "\033[0;96m";
    public static final String WHITE_BRIGHT  = "\033[0;97m";
    
    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT  = "\033[1;90m";
    public static final String RED_BOLD_BRIGHT    = "\033[1;91m";
    public static final String GREEN_BOLD_BRIGHT  = "\033[1;92m";
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";
    public static final String BLUE_BOLD_BRIGHT   = "\033[1;94m";
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";
    public static final String CYAN_BOLD_BRIGHT   = "\033[1;96m";
    public static final String WHITE_BOLD_BRIGHT  = "\033[1;97m";
    
    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT  = "\033[0;100m";
    public static final String RED_BACKGROUND_BRIGHT    = "\033[0;101m";
    public static final String GREEN_BACKGROUND_BRIGHT  = "\033[0;102m";
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";
    public static final String BLUE_BACKGROUND_BRIGHT   = "\033[0;104m";
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m";
    public static final String CYAN_BACKGROUND_BRIGHT   = "\033[0;106m";
    public static final String WHITE_BACKGROUND_BRIGHT  = "\033[0;107m";
}

package rutils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.regex.Pattern;

public class StrUtil
{
    private StrUtil() { }
    
    private static final String dateFormatString = "uuuu-MMM-dd";
    private static final String timeFormatString = "HH:mm:ss.SSS";
    
    private static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(StrUtil.dateFormatString + ' ' + StrUtil.timeFormatString);
    private static final DateTimeFormatter timeFormat     = DateTimeFormatter.ofPattern(StrUtil.timeFormatString);
    private static final DateTimeFormatter dateFormat     = DateTimeFormatter.ofPattern(StrUtil.dateFormatString);
    
    /**
     * @return The current DateTime string.
     */
    public static String getCurrentDateTimeString()
    {
        return LocalDateTime.now().format(dateTimeFormat);
    }
    
    /**
     * @return The current Time string.
     */
    public static String getCurrentTimeString()
    {
        return LocalDateTime.now().toLocalTime().format(timeFormat);
    }
    
    /**
     * @return The current Date string.
     */
    public static String getCurrentDateString()
    {
        return LocalDateTime.now().toLocalDate().format(dateFormat);
    }
    
    private static final Pattern PATTERN = Pattern.compile("%(\\d+\\$)?([-#+ 0,(<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])"); // Taken from java.lang.Formatter
    
    /**
     * Prints an object to the console.
     *
     * @param object The object.
     */
    public static void print(Object object)
    {
        System.out.print(object);
    }
    
    /**
     * Prints the objects to the console separated by a space.
     *
     * @param objects The objects.
     */
    public static void print(Object... objects)
    {
        int n = objects.length;
        if (n == 0) return;
        StringBuilder builder = new StringBuilder(String.valueOf(objects[0]));
        for (int i = 1; i < n; i++) builder.append(' ').append(objects[i]);
        System.out.print(builder.toString());
    }
    
    /**
     * Prints the objects separated by a space to the console. If the format string contains format characters, then is will be used to format the objects.
     *
     * @param format  The optional format string.
     * @param objects The objects.
     */
    public static void print(String format, Object... objects)
    {
        if (StrUtil.PATTERN.matcher(format).find())
        {
            System.out.printf(format, objects);
        }
        else
        {
            StringBuilder builder = new StringBuilder(format);
            for (Object object : objects) builder.append(' ').append(object);
            System.out.print(builder.toString());
        }
    }
    
    /**
     * Prints an object, then a new line to the console.
     *
     * @param object The object.
     */
    public static void println(Object object)
    {
        System.out.println(object);
    }
    
    /**
     * Prints the objects separated by a space, then a new line to the console.
     *
     * @param objects The objects.
     */
    public static void println(Object... objects)
    {
        int n = objects.length;
        if (n == 0) return;
        StringBuilder builder = new StringBuilder(String.valueOf(objects[0]));
        for (int i = 1; i < n; i++) builder.append(' ').append(objects[i]);
        System.out.println(builder.toString());
    }
    
    /**
     * Prints the objects separated by a space, then a new line to the console. If the format string contains format characters, then is will be used to format the objects.
     *
     * @param format  The optional format string.
     * @param objects The objects.
     */
    public static void println(String format, Object... objects)
    {
        if (StrUtil.PATTERN.matcher(format).find())
        {
            System.out.printf((format) + "%n", objects);
        }
        else
        {
            StringBuilder builder = new StringBuilder(format);
            for (Object object : objects) builder.append(' ').append(object);
            System.out.println(builder.toString());
        }
    }
    
    /**
     * Joins an array of objects together into a string separated by a provided string.
     *
     * @param array   The array of objects.
     * @param between The string between each object.
     * @param prefix  The string before any objects.
     * @param suffix  The string after all the objects.
     * @return The string.
     */
    public static String join(Object[] array, String between, String prefix, String suffix)
    {
        int n = array.length;
        if (array.length == 0) return "";
        StringBuilder b = new StringBuilder(prefix).append(array[0]);
        for (int i = 1; i < n; i++) b.append(between).append(array[i]);
        return b.append(suffix).toString();
    }
    
    /**
     * Joins a collection of objects together into a string separated by a provided string.
     *
     * @param collection The collection of objects.
     * @param between    The string between each object.
     * @param prefix     The string before any objects.
     * @param suffix     The string after all the objects.
     * @return The string.
     */
    public static String join(Collection<?> collection, String between, String prefix, String suffix)
    {
        return join(collection.toArray(), between, prefix, suffix);
    }
    
    /**
     * Joins an array of objects together into a string separated by a provided string.
     *
     * @param array   The array of objects.
     * @param between The string between each object.
     * @return The string.
     */
    public static String join(Object[] array, String between)
    {
        return join(array, between, "", "");
    }
    
    /**
     * Joins a collection of objects together into a string separated by a provided string.
     *
     * @param collection The collection of objects.
     * @param between    The string between each object.
     * @return The string.
     */
    public static String join(Collection<?> collection, String between)
    {
        return join(collection.toArray(), between, "", "");
    }
    
    /**
     * Joins an array of objects together into a string separated by a space.
     *
     * @param array The collection of objects.
     * @return The string.
     */
    public static String join(Object... array)
    {
        return join(array, ", ", "", "");
    }
    
    /**
     * Joins a collection of objects together into a string separated by a space.
     *
     * @param collection The collection of objects.
     * @return The string.
     */
    public static String join(Collection<?> collection)
    {
        return join(collection.toArray(), ", ", "", "");
    }
}

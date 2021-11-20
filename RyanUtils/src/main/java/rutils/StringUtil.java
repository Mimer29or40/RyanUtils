package rutils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

public class StringUtil
{
    private static final String dateFormatString = "uuuu-MMM-dd";
    private static final String timeFormatString = "HH:mm:ss.SSS";
    
    private static final DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern(StringUtil.dateFormatString + ' ' + StringUtil.timeFormatString);
    private static final DateTimeFormatter timeFormat     = DateTimeFormatter.ofPattern(StringUtil.timeFormatString);
    private static final DateTimeFormatter dateFormat     = DateTimeFormatter.ofPattern(StringUtil.dateFormatString);
    
    /**
     * Extends the functionality of {@link String#valueOf(Object)} to include
     * pretty printing of {@link Throwable Throwable's} and arrays. This will
     * respect a custom Object's {@link Object#toString()} method.
     *
     * @param obj The object to print.
     * @return A {@link String} representation of the object.
     */
    public static @NotNull String toString(@Nullable Object obj)
    {
        if (obj == null) return "null";
        if (obj instanceof Throwable)
        {
            final StringWriter sw = new StringWriter();
            ((Throwable) obj).printStackTrace(new PrintWriter(sw));
            return sw.getBuffer().toString();
        }
        if (obj.getClass().isArray())
        {
            if (obj instanceof boolean[]) return Arrays.toString((boolean[]) obj);
            if (obj instanceof byte[]) return Arrays.toString((byte[]) obj);
            if (obj instanceof short[]) return Arrays.toString((short[]) obj);
            if (obj instanceof char[]) return Arrays.toString((char[]) obj);
            if (obj instanceof int[]) return Arrays.toString((int[]) obj);
            if (obj instanceof long[]) return Arrays.toString((long[]) obj);
            if (obj instanceof float[]) return Arrays.toString((float[]) obj);
            if (obj instanceof double[]) return Arrays.toString((double[]) obj);
            return Arrays.deepToString((Object[]) obj);
        }
        if (obj instanceof Boolean) return Boolean.toString((boolean) obj);
        if (obj instanceof Character) return Character.toString((char) obj);
        if (obj instanceof Number)
        {
            if (obj instanceof Byte) return Byte.toString((byte) obj);
            if (obj instanceof Short) return Short.toString((short) obj);
            if (obj instanceof Integer) return Integer.toString((int) obj);
            if (obj instanceof Long) return Long.toString((long) obj);
            if (obj instanceof Float) return Float.toString((float) obj);
            if (obj instanceof Double) return Double.toString((double) obj);
        }
        return String.valueOf(obj);
    }
    
    /**
     * @return The current DateTime string.
     */
    public static @NotNull String getCurrentDateTimeString()
    {
        return LocalDateTime.now().format(StringUtil.dateTimeFormat);
    }
    
    /**
     * @return The current Time string.
     */
    public static @NotNull String getCurrentTimeString()
    {
        return LocalDateTime.now().toLocalTime().format(StringUtil.timeFormat);
    }
    
    /**
     * @return The current Date string.
     */
    public static @NotNull String getCurrentDateString()
    {
        return LocalDateTime.now().toLocalDate().format(StringUtil.dateFormat);
    }
    
    private static final Pattern PATTERN = Pattern.compile("%(\\d+\\$)?([-#+ 0,(<]*)?(\\d+)?(\\.\\d+)?([tT])?([a-zA-Z%])"); // Taken from java.lang.Formatter
    
    /**
     * Checks if the {@link CharSequence} contains {@link java.util.Formatter}
     * codes.
     *
     * @param input The {@link CharSequence} to check.
     * @return If the {@link CharSequence} contains {@link java.util.Formatter} codes.
     */
    public static boolean isFormatterString(CharSequence input)
    {
        return StringUtil.PATTERN.matcher(input).find();
    }
    
    private static Object transformObject(Object obj)
    {
        if (obj instanceof Throwable) return toString(obj);
        if (obj != null && obj.getClass().isArray()) return toString(obj);
        return obj;
    }
    
    /**
     * Prints the object to the {@link java.io.PrintStream}.
     * <p>
     * The object will be printed with {@link StringUtil#toString(Object)} to
     * expand arrays and print the stacktrace for
     * {@link Throwable Throwable's}.
     * <p>
     * If the first object is a string that contains
     * {@link java.util.Formatter} codes, then it will be used to format the
     * object. Arrays will be converted to strings with
     * {@link StringUtil#toString(Object)} before being formatted.
     *
     * @param stream The {@link java.io.PrintStream} to print to.
     * @param object The object to print.
     */
    public static void printToStream(@NotNull PrintStream stream, @Nullable Object object)
    {
        stream.print(toString(object));
    }
    
    /**
     * Prints the objects to the {@link java.io.PrintStream} separated by a
     * space.
     * <p>
     * The objects will be printed with {@link StringUtil#toString(Object)} to
     * expand arrays and print the stacktrace for
     * {@link Throwable Throwable's}.
     * <p>
     * If the first object is a string that contains
     * {@link java.util.Formatter} codes, then it will be used to format the
     * objects. Arrays will be converted to strings with
     * {@link StringUtil#toString(Object)} before being formatted.
     *
     * @param stream  The {@link java.io.PrintStream} to print to.
     * @param objects The objects to print.
     */
    public static void printToStream(@NotNull PrintStream stream, @Nullable Object... objects)
    {
        int n = objects.length;
        if (n == 0) return;
        StringBuilder result = new StringBuilder();
        if (objects[0] instanceof String format)
        {
            if (isFormatterString(format))
            {
                Object[] transformed = new Object[n - 1];
                for (int i = 1; i < n; i++) transformed[i - 1] = transformObject(objects[i]);
                result.append(String.format(format, transformed));
            }
            else
            {
                result.append(toString(format));
                for (int i = 1; i < n; i++) result.append(' ').append(toString(objects[i]));
            }
        }
        else
        {
            result.append(toString(objects[0]));
            for (int i = 1; i < n; i++) result.append(' ').append(toString(objects[i]));
        }
        stream.print(result);
    }
    
    /**
     * Prints the object to the {@link java.io.PrintStream} followed by a new
     * line.
     * <p>
     * The object will be printed with {@link StringUtil#toString(Object)} to
     * expand arrays and print the stacktrace for
     * {@link Throwable Throwable's}.
     * <p>
     * If the first object is a string that contains
     * {@link java.util.Formatter} codes, then it will be used to format the
     * object. Arrays will be converted to strings with
     * {@link StringUtil#toString(Object)} before being formatted.
     *
     * @param stream The {@link java.io.PrintStream} to print to.
     * @param object The object to print.
     */
    public static void printlnToStream(@NotNull PrintStream stream, @Nullable Object object)
    {
        stream.println(toString(object));
    }
    
    /**
     * Prints the objects to the {@link java.io.PrintStream} separated by a
     * space followed by a new line.
     * <p>
     * The objects will be printed with {@link StringUtil#toString(Object)} to
     * expand arrays and print the stacktrace for
     * {@link Throwable Throwable's}.
     * <p>
     * If the first object is a string that contains
     * {@link java.util.Formatter} codes, then it will be used to format the
     * objects. Arrays will be converted to strings with
     * {@link StringUtil#toString(Object)} before being formatted.
     *
     * @param stream  The {@link java.io.PrintStream} to print to.
     * @param objects The objects to print.
     */
    public static void printlnToStream(@NotNull PrintStream stream, @Nullable Object... objects)
    {
        int n = objects.length;
        if (n == 0) return;
        StringBuilder result = new StringBuilder();
        if (objects[0] instanceof String format)
        {
            if (StringUtil.PATTERN.matcher(format).find())
            {
                Object[] transformed = new Object[n - 1];
                for (int i = 1; i < n; i++) transformed[i - 1] = transformObject(objects[i]);
                result.append(String.format(format, transformed));
            }
            else
            {
                result.append(toString(format));
                for (int i = 1; i < n; i++) result.append(' ').append(toString(objects[i]));
            }
        }
        else
        {
            result.append(toString(objects[0]));
            for (int i = 1; i < n; i++) result.append(' ').append(toString(objects[i]));
        }
        stream.println(result);
    }
    
    /**
     * Prints the object to {@link System#out}.
     * <p>
     * The object will be printed with {@link StringUtil#toString(Object)} to
     * expand arrays and print the stacktrace for
     * {@link Throwable Throwable's}.
     * <p>
     * If the first object is a string that contains
     * {@link java.util.Formatter} codes, then it will be used to format the
     * object. Arrays will be converted to strings with
     * {@link StringUtil#toString(Object)} before being formatted.
     *
     * @param object The object to print.
     */
    public static void print(@Nullable Object object)
    {
        printToStream(System.out, object);
    }
    
    /**
     * Prints the objects to {@link System#out} separated by a space.
     * <p>
     * The objects will be printed with {@link StringUtil#toString(Object)} to
     * expand arrays and print the stacktrace for
     * {@link Throwable Throwable's}.
     * <p>
     * If the first object is a string that contains
     * {@link java.util.Formatter} codes, then it will be used to format the
     * objects. Arrays will be converted to strings with
     * {@link StringUtil#toString(Object)} before being formatted.
     *
     * @param objects The objects to print.
     */
    public static void print(@Nullable Object... objects)
    {
        printToStream(System.out, objects);
    }
    
    /**
     * Prints the object to {@link System#out} followed by a new line.
     * <p>
     * The object will be printed with {@link StringUtil#toString(Object)} to
     * expand arrays and print the stacktrace for
     * {@link Throwable Throwable's}.
     * <p>
     * If the first object is a string that contains
     * {@link java.util.Formatter} codes, then it will be used to format the
     * object. Arrays will be converted to strings with
     * {@link StringUtil#toString(Object)} before being formatted.
     *
     * @param object The object to print.
     */
    public static void println(@Nullable Object object)
    {
        printlnToStream(System.out, object);
    }
    
    /**
     * Prints the objects to {@link System#out} separated by a space followed
     * by a new line.
     * <p>
     * The objects will be printed with {@link StringUtil#toString(Object)} to
     * expand arrays and print the stacktrace for
     * {@link Throwable Throwable's}.
     * <p>
     * If the first object is a string that contains
     * {@link java.util.Formatter} codes, then it will be used to format the
     * objects. Arrays will be converted to strings with
     * {@link StringUtil#toString(Object)} before being formatted.
     *
     * @param objects The objects to print.
     */
    public static void println(@Nullable Object... objects)
    {
        printlnToStream(System.out, objects);
    }
    
    /**
     * Joins an {@link Iterable} of objects together separated by a specified
     * string of characters, starting with a prefix string and ending with a
     * suffix string.
     * <p>
     * If the {@link Iterable} contains no values, then the resulting string
     * will be equal to:<pre>    prefix + suffix</pre>
     * <p>
     * The default values are the following:
     * <pre>
     *     separator = ", ";
     *     prefix    = "";
     *     suffix    = "";
     * </pre>
     *
     * @param iterable  The iterable to join in to a string.
     * @param separator The string that will be in between each value.
     * @param prefix    The string that will be before each value.
     * @param suffix    The string that will be after each value.
     * @return The joined String.
     */
    public static @NotNull String join(@NotNull Iterable<?> iterable, @NotNull CharSequence separator, @NotNull CharSequence prefix, @NotNull CharSequence suffix)
    {
        Iterator<?> iterator = iterable.iterator();
        if (!iterator.hasNext()) return prefix.toString() + suffix;
        StringBuilder builder = new StringBuilder(prefix).append(toString(iterator.next()));
        while (iterator.hasNext()) builder.append(separator).append(toString(iterator.next()));
        return builder.append(suffix).toString();
    }
    
    /**
     * Joins an {@link Iterable} of objects together separated by a specified
     * string of characters, starting with a prefix string and ending with a
     * suffix string.
     * <p>
     * If the {@link Iterable} contains no values, then the resulting string
     * will be equal to:<pre>    prefix + suffix</pre>
     * <p>
     * The default values are the following:
     * <pre>
     *     separator = ", ";
     *     prefix    = "";
     *     suffix    = "";
     * </pre>
     *
     * @param iterable  The iterable to join in to a string.
     * @param separator The string that will be in between each value.
     * @return The joined String.
     */
    public static @NotNull String join(@NotNull Iterable<?> iterable, @NotNull CharSequence separator)
    {
        return join(iterable, separator, "", "");
    }
    
    /**
     * Joins an {@link Iterable} of objects together separated by a specified
     * string of characters, starting with a prefix string and ending with a
     * suffix string.
     * <p>
     * If the {@link Iterable} contains no values, then the resulting string
     * will be equal to:<pre>    prefix + suffix</pre>
     * <p>
     * The default values are the following:
     * <pre>
     *     separator = ", ";
     *     prefix    = "";
     *     suffix    = "";
     * </pre>
     *
     * @param iterable The iterable to join in to a string.
     * @return The joined String.
     */
    public static @NotNull String join(@NotNull Iterable<?> iterable)
    {
        return join(iterable, ", ", "", "");
    }
    
    /**
     * Joins an array of objects together separated by a specified string of
     * characters, starting with a prefix string and ending with a suffix
     * string.
     * <p>
     * If the array contains no values, then the resulting string will be equal
     * to:<pre>    prefix + suffix</pre>
     * <p>
     * The default values are the following:
     * <pre>
     *     separator = ", ";
     *     prefix    = "";
     *     suffix    = "";
     * </pre>
     *
     * @param array     The iterable to join in to a string.
     * @param separator The string that will be in between each value.
     * @param prefix    The string that will be before each value.
     * @param suffix    The string that will be after each value.
     * @return The joined String.
     */
    public static @NotNull String join(@NotNull Object[] array, @NotNull CharSequence separator, @NotNull CharSequence prefix, @NotNull CharSequence suffix)
    {
        int n = array.length;
        if (array.length == 0) return prefix.toString() + suffix;
        StringBuilder b = new StringBuilder(prefix).append(toString(array[0]));
        for (int i = 1; i < n; i++) b.append(separator).append(toString(array[i]));
        return b.append(suffix).toString();
    }
    
    /**
     * Joins an array of objects together separated by a specified string of
     * characters, starting with a prefix string and ending with a suffix
     * string.
     * <p>
     * If the array contains no values, then the resulting string will be equal
     * to:<pre>    prefix + suffix</pre>
     * <p>
     * The default values are the following:
     * <pre>
     *     separator = ", ";
     *     prefix    = "";
     *     suffix    = "";
     * </pre>
     *
     * @param array     The iterable to join in to a string.
     * @param separator The string that will be in between each value.
     * @return The joined String.
     */
    public static @NotNull String join(@NotNull Object[] array, @NotNull CharSequence separator)
    {
        return join(array, separator, "", "");
    }
    
    /**
     * Joins an array of objects together separated by a specified string of
     * characters, starting with a prefix string and ending with a suffix
     * string.
     * <p>
     * If the array contains no values, then the resulting string will be equal
     * to:<pre>    prefix + suffix</pre>
     * <p>
     * The default values are the following:
     * <pre>
     *     separator = ", ";
     *     prefix    = "";
     *     suffix    = "";
     * </pre>
     *
     * @param array The iterable to join in to a string.
     * @return The joined String.
     */
    public static @NotNull String join(@NotNull Object[] array)
    {
        return join(array, ", ", "", "");
    }
    
    private StringUtil() { }
}

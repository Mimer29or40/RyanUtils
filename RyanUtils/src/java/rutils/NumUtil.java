package rutils;

import org.jetbrains.annotations.NotNull;
import rutils.group.IPair;
import rutils.group.IPairI;
import rutils.group.PairI;

import java.util.regex.Pattern;

public class NumUtil
{
    /**
     * Maps the value {@code x} linearly from between {@code x0} and {@code x1}
     * to a value between {@code y0} and {@code y1}.
     * <p>
     * Results are not clamped between endpoints.
     *
     * @param x  The value to map.
     * @param x0 The initial lower bound.
     * @param x1 The initial upper bound.
     * @param y0 The mapped lower bound.
     * @param y1 The mapped upper bound.
     * @return The mapped value.
     */
    public static double map(double x, double x0, double x1, double y0, double y1)
    {
        return (x - x0) * (y1 - y0) / (x1 - x0) + y0;
    }
    
    private static final Pattern FORMAT_NUMBERS_SPLIT = Pattern.compile("\\.");
    
    /**
     * This takes an array of decimals and determines the max length before and
     * after the decimal point to align the decimal points when printed in a
     * column.
     *
     * @param values The double values.
     * @return The pair of number.
     */
    public static @NotNull IPairI getFormatNumbers(double... values)
    {
        int numI = 1, numD = 0;
        for (double val : values)
        {
            String[] num = NumUtil.FORMAT_NUMBERS_SPLIT.split(Double.toString(val));
            numI = Math.max(numI, num[0].length());
            if (val != (int) val) numD = Math.max(numD, num[1].length());
        }
        return new PairI(numI, numD);
    }
    
    /**
     * Prints a number with a fixed number of character before and after the decimal point.
     *
     * @param x    The number.
     * @param numI The amount of characters before the decimal point.
     * @param numD The amount of characters after the decimal point.
     * @return The formatter number string.
     */
    public static @NotNull String format(double x, int numI, int numD)
    {
        String I = String.valueOf((int) x);
        String D = (numD > 0 ? String.valueOf((int) Math.round(Math.abs(getDecimal(x)) * Math.pow(10, numD))) : "");
        while (D.endsWith("0") && D.length() > 1) D = D.substring(0, D.length() - 1);
        String fI = numI > I.length() ? "%" + (numI - I.length()) + "s" : "%s";
        String fD = numD > D.length() ? "%" + (numD - D.length()) + "s" : "%s";
        return String.format(fI + "%s%s%s" + fD, "", I, numD > 0 ? "." : "", D, "");
    }
    
    /**
     * Prints a number with a fixed number of character before and after the decimal point.
     *
     * @param x       The number.
     * @param numbers The pair of number for before and after the decimal point.
     * @return The formatter number string.
     */
    public static @NotNull String format(double x, @NotNull IPair<Integer, Integer> numbers)
    {
        return format(x, numbers.getA() != null ? numbers.getA() : 1, numbers.getB() != null ? numbers.getB() : 0);
    }
    
    /**
     * Rounds a number to a specified number of decimal places.
     *
     * @param value  The number.
     * @param places The number of places.
     * @return The rounded number.
     */
    public static double round(double value, int places)
    {
        if (places <= 0) return Math.round(value);
        double pow = Math.pow(10, places);
        return Math.round(value * pow) / pow;
    }
    
    /**
     * Rounds a number to the nearest integer.
     *
     * @param value The number.
     * @return The rounded number.
     */
    public static int round(double value)
    {
        return (int) Math.round(value);
    }
    
    /**
     * Clamps a value between two bounds.
     *
     * @param x   The value to clamp.
     * @param min The lower bound.
     * @param max The upper bound.
     * @return The clamped value.
     */
    public static int clamp(int x, int min, int max)
    {
        return x <= min ? min : Math.min(x, max);
    }
    
    /**
     * Clamps a value between zero and an upper bound.
     *
     * @param x   The value to clamp.
     * @param max The upper bound.
     * @return The clamped value.
     */
    public static int clamp(int x, int max)
    {
        return clamp(x, 0, max);
    }
    
    /**
     * Clamps a value between two bounds.
     *
     * @param x   The value to clamp.
     * @param min The lower bound.
     * @param max The upper bound.
     * @return The clamped value.
     */
    public static long clamp(long x, long min, long max)
    {
        return x <= min ? min : Math.min(x, max);
    }
    
    /**
     * Clamps a value between zero and an upper bound.
     *
     * @param x   The value to clamp.
     * @param max The upper bound.
     * @return The clamped value.
     */
    public static long clamp(long x, long max)
    {
        return clamp(x, 0, max);
    }
    
    /**
     * Clamps a value between two bounds.
     *
     * @param x   The value to clamp.
     * @param min The lower bound.
     * @param max The upper bound.
     * @return The clamped value.
     */
    public static float clamp(float x, float min, float max)
    {
        return x <= min ? min : Math.min(x, max);
    }
    
    /**
     * Clamps a value between zero and an upper bound.
     *
     * @param x   The value to clamp.
     * @param max The upper bound.
     * @return The clamped value.
     */
    public static float clamp(float x, float max)
    {
        return clamp(x, 0, max);
    }
    
    /**
     * Clamps a value between two bounds.
     *
     * @param x   The value to clamp.
     * @param min The lower bound.
     * @param max The upper bound.
     * @return The clamped value.
     */
    public static double clamp(double x, double min, double max)
    {
        return x <= min ? min : Math.min(x, max);
    }
    
    /**
     * Clamps a value between zero and an upper bound.
     *
     * @param x   The value to clamp.
     * @param max The upper bound.
     * @return The clamped value.
     */
    public static double clamp(double x, double max)
    {
        return clamp(x, 0, max);
    }
    
    /**
     * Gets the decimal part of a number.
     *
     * @param value The number.
     * @return The decimal part.
     */
    public static double getDecimal(double value)
    {
        return value - (int) value;
    }
    
    /**
     * @param value The number.
     * @return If the number is even.
     */
    public static boolean isEven(long value)
    {
        return (value & 1) == 0;
    }
    
    /**
     * @param value The number.
     * @return If the number is odd.
     */
    public static boolean isOdd(long value)
    {
        return (value & 1) == 1;
    }
    
    /**
     * @param value The number.
     * @return If the number is even.
     */
    public static boolean isEven(double value)
    {
        return isEven((int) Math.floor(value));
    }
    
    /**
     * @param value The number.
     * @return If the number is odd.
     */
    public static boolean isOdd(double value)
    {
        return isOdd((int) Math.floor(value));
    }
    
    /**
     * Computes the minimum value of the given data set.
     *
     * @param array The data set.
     * @return The minimum value of the data set.
     */
    public static int min(int... array)
    {
        int min = Integer.MAX_VALUE;
        for (int x : array) min = Math.min(min, x);
        return min;
    }
    
    /**
     * Computes the minimum value of the given data set.
     *
     * @param array The data set.
     * @return The minimum value of the data set.
     */
    public static long min(long... array)
    {
        long min = Long.MAX_VALUE;
        for (long x : array) min = Math.min(min, x);
        return min;
    }
    
    /**
     * Computes the minimum value of the given data set.
     *
     * @param array The data set.
     * @return The minimum value of the data set.
     */
    public static float min(float... array)
    {
        float min = Float.MAX_VALUE;
        for (float x : array) min = Math.min(min, x);
        return min;
    }
    
    /**
     * Computes the minimum value of the given data set.
     *
     * @param array The data set.
     * @return The minimum value of the data set.
     */
    public static double min(double... array)
    {
        double min = Double.MAX_VALUE;
        for (double x : array) min = Math.min(min, x);
        return min;
    }
    
    /**
     * Computes the maximum value of the given data set.
     *
     * @param array The data set.
     * @return The maximum value of the data set.
     */
    public static int max(int... array)
    {
        int max = Integer.MIN_VALUE;
        for (int x : array) max = Math.max(max, x);
        return max;
    }
    
    /**
     * Computes the maximum value of the given data set.
     *
     * @param array The data set.
     * @return The maximum value of the data set.
     */
    public static long max(long... array)
    {
        long max = Long.MIN_VALUE;
        for (long x : array) max = Math.max(max, x);
        return max;
    }
    
    /**
     * Computes the maximum value of the given data set.
     *
     * @param array The data set.
     * @return The maximum value of the data set.
     */
    public static float max(float... array)
    {
        float max = Float.MIN_VALUE;
        for (float x : array) max = Math.max(max, x);
        return max;
    }
    
    /**
     * Computes the maximum value of the given data set.
     *
     * @param array The data set.
     * @return The maximum value of the data set.
     */
    public static double max(double... array)
    {
        double max = Double.MIN_VALUE;
        for (double x : array) max = Math.max(max, x);
        return max;
    }
    
    /**
     * Computes the summation of the given data set.
     *
     * @param array The data set.
     * @return The sum of the data set.
     */
    public static int sum(int... array)
    {
        int sum = 0;
        for (int x : array) sum += x;
        return sum;
    }
    
    /**
     * Computes the summation of the given data set.
     *
     * @param array The data set.
     * @return The sum of the data set.
     */
    public static long sum(long... array)
    {
        long sum = 0;
        for (long x : array) sum += x;
        return sum;
    }
    
    /**
     * Computes the summation of the given data set.
     *
     * @param array The data set.
     * @return The sum of the data set.
     */
    public static float sum(float... array)
    {
        float sum = 0;
        for (float x : array) sum += x;
        return sum;
    }
    
    /**
     * Computes the summation of the given data set.
     *
     * @param array The data set.
     * @return The sum of the data set.
     */
    public static double sum(double... array)
    {
        double sum = 0;
        for (double x : array) sum += x;
        return sum;
    }
    
    /**
     * Computes the mean value of the given data set.
     *
     * @param array The data set.
     * @return The mean value of the data set.
     */
    public static float mean(int... array)
    {
        float mean = 0;
        for (int x : array) mean += x;
        return mean / array.length;
    }
    
    /**
     * Computes the mean value of the given data set.
     *
     * @param array The data set.
     * @return The mean value of the data set.
     */
    public static double mean(long... array)
    {
        double mean = 0;
        for (long x : array) mean += x;
        return mean / array.length;
    }
    
    /**
     * Computes the mean value of the given data set.
     *
     * @param array The data set.
     * @return The mean value of the data set.
     */
    public static float mean(float... array)
    {
        float mean = 0;
        for (float x : array) mean += x;
        return mean / array.length;
    }
    
    /**
     * Computes the mean value of the given data set.
     *
     * @param array The data set.
     * @return The mean value of the data set.
     */
    public static double mean(double... array)
    {
        double mean = 0;
        for (double x : array) mean += x;
        return mean / array.length;
    }
    
    /**
     * Computes the standard deviation of the given data set.
     *
     * @param array The data set.
     * @return The standard deviation of the data set.
     */
    public static float stdDev(int... array)
    {
        float mean   = mean(array);
        float stdDev = 0;
        for (int x : array) stdDev += (x - mean) * (x - mean);
        return (float) Math.sqrt(stdDev / array.length);
    }
    
    /**
     * Computes the standard deviation of the given data set.
     *
     * @param array The data set.
     * @return The standard deviation of the data set.
     */
    public static double stdDev(long... array)
    {
        double mean   = mean(array);
        double stdDev = 0;
        for (long x : array) stdDev += (x - mean) * (x - mean);
        return Math.sqrt(stdDev / array.length);
    }
    
    /**
     * Computes the standard deviation of the given data set.
     *
     * @param array The data set.
     * @return The standard deviation of the data set.
     */
    public static float stdDev(float... array)
    {
        float mean   = mean(array);
        float stdDev = 0;
        for (float x : array) stdDev += (x - mean) * (x - mean);
        return (float) Math.sqrt(stdDev / array.length);
    }
    
    /**
     * Computes the standard deviation of the given data set.
     *
     * @param array The data set.
     * @return The standard deviation of the data set.
     */
    public static double stdDev(double... array)
    {
        double mean   = mean(array);
        double stdDev = 0;
        for (double x : array) stdDev += (x - mean) * (x - mean);
        return Math.sqrt(stdDev / array.length);
    }
    
    /**
     * Interpolates a value between two endpoints. Results are clamped between endpoints.
     *
     * @param a The first value.
     * @param b The second value.
     * @param x The amount to interpolate. [0-1]
     * @return The interpolated value.
     */
    public static int lerp(int a, int b, double x)
    {
        if (x <= 0) return a;
        if (x >= 1) return b;
        return a + (int) ((b - a) * x);
    }
    
    /**
     * Interpolates a value between two endpoints. Results are clamped between endpoints.
     *
     * @param a The first value.
     * @param b The second value.
     * @param x The amount to interpolate. [0-1]
     * @return The interpolated value.
     */
    public static long lerp(long a, long b, double x)
    {
        if (x <= 0) return a;
        if (x >= 1) return b;
        return a + (long) ((b - a) * x);
    }
    
    /**
     * Interpolates a value between two endpoints. Results are clamped between endpoints.
     *
     * @param a The first value.
     * @param b The second value.
     * @param x The amount to interpolate. [0-1]
     * @return The interpolated value.
     */
    public static float lerp(float a, float b, double x)
    {
        if (x <= 0) return a;
        if (x >= 1) return b;
        return a + (float) ((b - a) * x);
    }
    
    /**
     * Interpolates a value between two endpoints. Results are clamped between endpoints.
     *
     * @param a The first value.
     * @param b The second value.
     * @param x The amount to interpolate. [0-1]
     * @return The interpolated value.
     */
    public static double lerp(double a, double b, double x)
    {
        if (x <= 0) return a;
        if (x >= 1) return b;
        return a + (b - a) * x;
    }
    
    private static double smoothstep(double x)
    {
        return x * x * (3 - 2 * x);
    }
    
    /**
     * Interpolates between two values smoothly.
     *
     * @param a The first edge.
     * @param b The second edge.
     * @param x The amount to interpolate. [0-1]
     * @return The interpolated value.
     */
    public static int smoothstep(int a, int b, double x)
    {
        if (x <= 0) return a;
        if (x >= 1) return b;
        return a + (int) ((b - a) * smoothstep(x));
    }
    
    /**
     * Interpolates between two values smoothly.
     *
     * @param a The first edge.
     * @param b The second edge.
     * @param x The amount to interpolate. [0-1]
     * @return The interpolated value.
     */
    public static long smoothstep(long a, long b, double x)
    {
        if (x <= 0) return a;
        if (x >= 1) return b;
        return a + (long) ((b - a) * smoothstep(x));
    }
    
    /**
     * Interpolates between two values smoothly.
     *
     * @param a The first edge.
     * @param b The second edge.
     * @param x The amount to interpolate. [0-1]
     * @return The interpolated value.
     */
    public static float smoothstep(float a, float b, double x)
    {
        if (x <= 0) return a;
        if (x >= 1) return b;
        return a + (float) ((b - a) * smoothstep(x));
    }
    
    /**
     * Interpolates between two values smoothly.
     *
     * @param a The first edge.
     * @param b The second edge.
     * @param x The amount to interpolate. [0-1]
     * @return The interpolated value.
     */
    public static double smoothstep(double a, double b, double x)
    {
        if (x <= 0) return a;
        if (x >= 1) return b;
        return a + (b - a) * smoothstep(x);
    }
    
    private static double smootherstep(double x)
    {
        return x * x * x * (x * (x * 6 - 15) + 10);
    }
    
    /**
     * Interpolates between two values more smoothly.
     *
     * @param a The first edge.
     * @param b The second edge.
     * @param x The amount to interpolate. [0-1]
     * @return The interpolated value.
     */
    public static int smootherstep(int a, int b, double x)
    {
        if (x <= 0) return a;
        if (x >= 1) return b;
        return a + (int) ((b - a) * smootherstep(x));
    }
    
    /**
     * Interpolates between two values more smoothly.
     *
     * @param a The first edge.
     * @param b The second edge.
     * @param x The amount to interpolate. [0-1]
     * @return The interpolated value.
     */
    public static long smootherstep(long a, long b, double x)
    {
        if (x <= 0) return a;
        if (x >= 1) return b;
        return a + (long) ((b - a) * smootherstep(x));
    }
    
    /**
     * Interpolates between two values more smoothly.
     *
     * @param a The first edge.
     * @param b The second edge.
     * @param x The amount to interpolate. [0-1]
     * @return The interpolated value.
     */
    public static float smootherstep(float a, float b, double x)
    {
        if (x <= 0) return a;
        if (x >= 1) return b;
        return a + (float) ((b - a) * smootherstep(x));
    }
    
    /**
     * Interpolates between two values more smoothly.
     *
     * @param a The first edge.
     * @param b The second edge.
     * @param x The amount to interpolate. [0-1]
     * @return The interpolated value.
     */
    public static double smootherstep(double a, double b, double x)
    {
        if (x <= 0) return a;
        if (x >= 1) return b;
        return a + (b - a) * smootherstep(x);
    }
    
    /**
     * Calculated the floor of a double. Much faster than {@code Math.floor}
     *
     * @param x The value
     * @return The floored value.
     */
    public static int fastFloor(double x)
    {
        int xi = (int) x;
        return x < xi ? xi - 1 : xi;
    }
    
    /**
     * Calculated the ceiling of a double. Much faster than {@code Math.ceil}
     *
     * @param x The value
     * @return The ceiling value.
     */
    public static int fastCeil(double x)
    {
        int xi = (int) x;
        return x > xi ? xi + 1 : xi;
    }
    
    private NumUtil() { }
}

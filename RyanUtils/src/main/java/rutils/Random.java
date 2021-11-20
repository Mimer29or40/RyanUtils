package rutils;

import org.jetbrains.annotations.Nullable;

import java.util.Collection;

/**
 * An extension to Java's built-in Random class that adds some useful methods to random
 * numbers.
 * <p>
 * It also adds random functions for arrays, collections, JOML Vectors, and {@code Color}'s
 */
public class Random extends java.util.Random
{
    public Random()
    {
        super();
    }
    
    public Random(long seed)
    {
        super(seed);
    }
    
    /**
     * @return A random uniformly distributed {@code int} [{@code origin} - {@code bound}].
     */
    public int nextInt(int origin, int bound)
    {
        if (origin >= bound) throw new IllegalArgumentException("origin must be less than bound");
        return origin + nextInt(bound - origin);
    }
    
    /**
     * @return A random uniformly distributed {@code long} [{@code 0} - {@code bound}].
     */
    @SuppressWarnings("StatementWithEmptyBody")
    public long nextLong(long bound)
    {
        if (bound <= 0) throw new IllegalArgumentException("bound must be positive");
        long r = nextLong(), m = bound - 1;
        if ((bound & m) == 0L) { r &= m; }
        else { for (long u = r >>> 1; u + m - (r = u % bound) < 0L; u = nextLong()) ; }
        return r;
    }
    
    /**
     * @return A random uniformly distributed {@code long} [{@code origin} - {@code bound}].
     */
    public long nextLong(long origin, long bound)
    {
        if (origin >= bound) throw new IllegalArgumentException("origin must be less than bound");
        return origin + nextLong(bound - origin);
    }
    
    /**
     * @return A random uniformly distributed {@code float} [{@code 0} - {@code bound}].
     */
    public float nextFloat(float bound)
    {
        if (bound <= 0) throw new IllegalArgumentException("bound must be positive");
        return nextFloat() * bound;
    }
    
    /**
     * @return A random uniformly distributed {@code float} [{@code origin} - {@code bound}].
     */
    public float nextFloat(float origin, float bound)
    {
        if (origin >= bound) throw new IllegalArgumentException("origin must be less than bound");
        return origin + (bound - origin) * nextFloat();
    }
    
    /**
     * @return A random uniformly distributed {@code double} [{@code 0} - {@code bound}].
     */
    public double nextDouble(double bound)
    {
        if (bound <= 0) throw new IllegalArgumentException("bound must be positive");
        return nextDouble() * bound;
    }
    
    /**
     * @return A random uniformly distributed {@code double} [{@code origin} - {@code bound}].
     */
    public double nextDouble(double origin, double bound)
    {
        if (origin >= bound) throw new IllegalArgumentException("origin must be less than bound");
        return origin + (bound - origin) * nextDouble();
    }
    
    /**
     * @return An array with random uniformly distributed {@code int}.
     */
    public int[] nextInts(int[] ints)
    {
        for (int i = 0, n = ints.length; i < n; i++) ints[i] = nextInt();
        return ints;
    }
    
    /**
     * @return An array with random uniformly distributed {@code int} [{@code 0} - {@code bound}].
     */
    public int[] nextInts(int[] ints, int bound)
    {
        for (int i = 0, n = ints.length; i < n; i++) ints[i] = nextInt(bound);
        return ints;
    }
    
    /**
     * @return An array with random uniformly distributed {@code int} [{@code origin} - {@code bound}].
     */
    public int[] nextInts(int[] ints, int origin, int bound)
    {
        for (int i = 0, n = ints.length; i < n; i++) ints[i] = nextInt(origin, bound);
        return ints;
    }
    
    /**
     * @return An array with random uniformly distributed {@code long}.
     */
    public long[] nextLongs(long[] longs)
    {
        for (int i = 0, n = longs.length; i < n; i++) longs[i] = nextLong();
        return longs;
    }
    
    /**
     * @return An array with random uniformly distributed {@code long} [{@code 0} - {@code bound}].
     */
    public long[] nextLongs(long[] longs, long bound)
    {
        for (int i = 0, n = longs.length; i < n; i++) longs[i] = nextLong(bound);
        return longs;
    }
    
    /**
     * @return An array with random uniformly distributed {@code long} [{@code origin} - {@code bound}].
     */
    public long[] nextLongs(long[] longs, long origin, long bound)
    {
        for (int i = 0, n = longs.length; i < n; i++) longs[i] = nextLong(origin, bound);
        return longs;
    }
    
    /**
     * @return An array with random uniformly distributed {@code float} [{@code 0F} - {@code 1F}].
     */
    public float[] nextFloats(float[] floats)
    {
        for (int i = 0, n = floats.length; i < n; i++) floats[i] = nextFloat();
        return floats;
    }
    
    /**
     * @return An array with random uniformly distributed {@code float} [{@code 0F} - {@code bound}].
     */
    public float[] nextFloats(float[] floats, float bound)
    {
        for (int i = 0, n = floats.length; i < n; i++) floats[i] = nextFloat(bound);
        return floats;
    }
    
    /**
     * @return An array with random uniformly distributed {@code float} [{@code origin} - {@code bound}].
     */
    public float[] nextFloats(float[] floats, float origin, float bound)
    {
        for (int i = 0, n = floats.length; i < n; i++) floats[i] = nextFloat(origin, bound);
        return floats;
    }
    
    /**
     * @return An array with random uniformly distributed {@code double} [{@code 0.0} - {@code 1.0}].
     */
    public double[] nextDoubles(double[] doubles)
    {
        for (int i = 0, n = doubles.length; i < n; i++) doubles[i] = nextDouble();
        return doubles;
    }
    
    /**
     * @return An array with random uniformly distributed {@code double} [{@code 0.0} - {@code bound}].
     */
    public double[] nextDoubles(double[] doubles, double bound)
    {
        for (int i = 0, n = doubles.length; i < n; i++) doubles[i] = nextDouble(bound);
        return doubles;
    }
    
    /**
     * @return An array with random uniformly distributed {@code double} [{@code origin} - {@code bound}].
     */
    public double[] nextDoubles(double[] doubles, double origin, double bound)
    {
        for (int i = 0, n = doubles.length; i < n; i++) doubles[i] = nextDouble(origin, bound);
        return doubles;
    }
    
    /**
     * Returns the next pseudorandom, Gaussian ("normally") distributed
     * {@code double} value with mean {@code mean} and standard
     * deviation {@code stdDev} from this random number generator's sequence.
     *
     * @param mean   The mean value
     * @param stdDev The standard deviation value
     * @return The next pseudorandom, Gaussian ("normally") distributed
     * {@code double} value with mean {@code mean} and
     * standard deviation {@code stdDev} from this random number
     * generator's sequence
     */
    public double nextGaussian(double mean, double stdDev)
    {
        return mean + stdDev * nextGaussian();
    }
    
    /**
     * @param array The array
     * @return A random {@code int} from the array
     */
    public int nextFrom(int... array)
    {
        return array[nextInt(array.length)];
    }
    
    /**
     * @param array The array
     * @return A random {@code long} from the array
     */
    public long nextFrom(long... array)
    {
        return array[nextInt(array.length)];
    }
    
    /**
     * @param array The array
     * @return A random {@code float} from the array
     */
    public float nextFrom(float... array)
    {
        return array[nextInt(array.length)];
    }
    
    /**
     * @param array The array
     * @return A random {@code double} from the array
     */
    public double nextFrom(double... array)
    {
        return array[nextInt(array.length)];
    }
    
    /**
     * @param array The array
     * @return A random {@code T} from the array, or {@code null} if the object at the selected index is null.
     */
    @SafeVarargs
    public final <T> @Nullable T nextFrom(T... array)
    {
        return array[nextInt(array.length)];
    }
    
    /**
     * @param collection The collection
     * @return A random {@code T} from the collection, or {@code null} if the collection is null.
     */
    public <T> @Nullable T nextFrom(@Nullable Collection<T> collection)
    {
        if (collection == null) return null;
        int index = nextInt(collection.size());
        for (T value : collection)
        {
            if (index == 0) return value;
            index--;
        }
        return null;
    }
}

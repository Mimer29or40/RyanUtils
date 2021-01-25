package rutils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomTest
{
    static Random INSTANCE;
    
    @BeforeAll
    static void setUp()
    {
        RandomTest.INSTANCE = new Random();
    }
    
    @Test
    void nextInt()
    {
        RandomTest.INSTANCE.setSeed(1337);
        
        assertThrows(IllegalArgumentException.class, () -> RandomTest.INSTANCE.nextInt(1, 0));
        
        int origin = 0;
        int bound  = 100;
        int amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            int rand = RandomTest.INSTANCE.nextInt(origin, bound);
            assertTrue(rand >= origin);
            assertTrue(rand <= bound);
        }
    }
    
    @Test
    void nextLong()
    {
        RandomTest.INSTANCE.setSeed(1337);
        
        assertThrows(IllegalArgumentException.class, () -> RandomTest.INSTANCE.nextLong(-1));
        
        assertThrows(IllegalArgumentException.class, () -> RandomTest.INSTANCE.nextLong(1, 0));
        
        long origin = Integer.MAX_VALUE;
        long bound  = (long) Integer.MAX_VALUE << 16;
        int  amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            long rand = RandomTest.INSTANCE.nextLong(origin, bound);
            assertTrue(rand >= origin);
            assertTrue(rand <= bound);
        }
    }
    
    @Test
    void nextFloat()
    {
        RandomTest.INSTANCE.setSeed(1337);
        
        assertThrows(IllegalArgumentException.class, () -> RandomTest.INSTANCE.nextFloat(-1));
        
        assertThrows(IllegalArgumentException.class, () -> RandomTest.INSTANCE.nextFloat(1, 0));
        
        float origin = 10;
        float bound  = 20;
        int   amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            float rand = RandomTest.INSTANCE.nextFloat(origin, bound);
            assertTrue(rand >= origin);
            assertTrue(rand <= bound);
        }
    }
    
    @Test
    void nextFloatDir()
    {
        RandomTest.INSTANCE.setSeed(1337);
        
        float origin = -1F;
        float bound  = 1F;
        int   amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            float rand = RandomTest.INSTANCE.nextFloatDir();
            assertTrue(rand >= origin);
            assertTrue(rand <= bound);
        }
    }
    
    @Test
    void nextDouble()
    {
        RandomTest.INSTANCE.setSeed(1337);
        
        assertThrows(IllegalArgumentException.class, () -> RandomTest.INSTANCE.nextDouble(-1));
        
        assertThrows(IllegalArgumentException.class, () -> RandomTest.INSTANCE.nextDouble(1, 0));
        
        double origin = 10;
        double bound  = 20;
        int    amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            double rand = RandomTest.INSTANCE.nextDouble(origin, bound);
            assertTrue(rand >= origin);
            assertTrue(rand <= bound);
        }
    }
    
    @Test
    void nextDoubleDir()
    {
        RandomTest.INSTANCE.setSeed(1337);
        
        double origin = -1F;
        double bound  = 1F;
        int    amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            double rand = RandomTest.INSTANCE.nextDoubleDir();
            assertTrue(rand >= origin);
            assertTrue(rand <= bound);
        }
    }
    
    @Test
    void nextInts()
    {
        RandomTest.INSTANCE.setSeed(1337);
        
        int   length;
        int   origin, bound, amount;
        int[] rand;
        
        length = 10;
        rand   = RandomTest.INSTANCE.nextInts(new int[length]);
        assertEquals(length, rand.length);
        
        length = 10;
        origin = 0;
        bound  = 100;
        amount = 1_000_000;
        for (int i = 0; i < amount; i++)
        {
            rand = RandomTest.INSTANCE.nextInts(new int[length], bound);
            for (int r : rand)
            {
                assertTrue(r >= origin);
                assertTrue(r <= bound);
            }
        }
        
        length = 10;
        origin = 256;
        bound  = 758;
        amount = 1_000_000;
        for (int i = 0; i < amount; i++)
        {
            rand = RandomTest.INSTANCE.nextInts(new int[length], origin, bound);
            for (int r : rand)
            {
                assertTrue(r >= origin);
                assertTrue(r <= bound);
            }
        }
    }
    
    @Test
    void nextLongs()
    {
        RandomTest.INSTANCE.setSeed(1337);
        
        int    length;
        long   origin, bound, amount;
        long[] rand;
        
        length = 10;
        rand   = RandomTest.INSTANCE.nextLongs(new long[length]);
        assertEquals(length, rand.length);
        
        length = 10;
        origin = 0;
        bound  = 100;
        amount = 1_000_000;
        for (int i = 0; i < amount; i++)
        {
            rand = RandomTest.INSTANCE.nextLongs(new long[length], bound);
            for (long r : rand)
            {
                assertTrue(r >= origin);
                assertTrue(r <= bound);
            }
        }
        
        length = 10;
        origin = 256;
        bound  = 758;
        amount = 1_000_000;
        for (int i = 0; i < amount; i++)
        {
            rand = RandomTest.INSTANCE.nextLongs(new long[length], origin, bound);
            for (long r : rand)
            {
                assertTrue(r >= origin);
                assertTrue(r <= bound);
            }
        }
    }
    
    @Test
    void nextFloats()
    {
        RandomTest.INSTANCE.setSeed(1337);
        
        int     length;
        float   origin, bound, amount;
        float[] rand;
        
        length = 10;
        rand   = RandomTest.INSTANCE.nextFloats(new float[length]);
        assertEquals(length, rand.length);
        
        length = 10;
        origin = 0;
        bound  = 100;
        amount = 1_000_000;
        for (int i = 0; i < amount; i++)
        {
            rand = RandomTest.INSTANCE.nextFloats(new float[length], bound);
            for (float r : rand)
            {
                assertTrue(r >= origin);
                assertTrue(r <= bound);
            }
        }
        
        length = 10;
        origin = 256;
        bound  = 758;
        amount = 1_000_000;
        for (int i = 0; i < amount; i++)
        {
            rand = RandomTest.INSTANCE.nextFloats(new float[length], origin, bound);
            for (float r : rand)
            {
                assertTrue(r >= origin);
                assertTrue(r <= bound);
            }
        }
    }
    
    @Test
    void nextDoubles()
    {
        RandomTest.INSTANCE.setSeed(1337);
        
        int      length;
        double   origin, bound, amount;
        double[] rand;
        
        length = 10;
        rand   = RandomTest.INSTANCE.nextDoubles(new double[length]);
        assertEquals(length, rand.length);
        
        length = 10;
        origin = 0;
        bound  = 100;
        amount = 1_000_000;
        for (int i = 0; i < amount; i++)
        {
            rand = RandomTest.INSTANCE.nextDoubles(new double[length], bound);
            for (double r : rand)
            {
                assertTrue(r >= origin);
                assertTrue(r <= bound);
            }
        }
        
        length = 10;
        origin = 256;
        bound  = 758;
        amount = 1_000_000;
        for (int i = 0; i < amount; i++)
        {
            rand = RandomTest.INSTANCE.nextDoubles(new double[length], origin, bound);
            for (double r : rand)
            {
                assertTrue(r >= origin);
                assertTrue(r <= bound);
            }
        }
    }
    
    @Test
    void nextGaussian()
    {
        RandomTest.INSTANCE.setSeed(1337);
        
        double expectedMean   = 50.0;
        double expectedStdDev = 10.0;
        
        int arrayLength = 1_000_000;
        
        double[] array = new double[arrayLength];
        for (int i = 0, n = array.length; i < n; i++) array[i] = RandomTest.INSTANCE.nextGaussian(expectedMean, expectedStdDev);
        
        double mean = 0;
        for (double x : array) mean += x;
        mean = mean / array.length;
        
        double stdDev = 0.0;
        for (double v : array) stdDev += (v - mean) * (v - mean);
        stdDev = Math.sqrt(stdDev / array.length);
        
        double delta = 0.01;
        assertEquals(expectedMean, mean, delta);
        assertEquals(expectedStdDev, stdDev, delta);
    }
    
    @Test
    @SuppressWarnings("IfStatementMissingBreakInLoop")
    void nextFrom()
    {
        RandomTest.INSTANCE.setSeed(1337);
    
        boolean isIn;
    
        int[] iArray  = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int   iResult = RandomTest.INSTANCE.nextFrom(iArray);
        isIn = false;
        for (int i : iArray) if (i == iResult) isIn = true;
        assertTrue(isIn);
        
        long[] lArray  = new long[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        long   lResult = RandomTest.INSTANCE.nextFrom(lArray);
        isIn = false;
        for (long l : lArray) if (l == lResult) isIn = true;
        assertTrue(isIn);
        
        float[] fArray  = new float[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        float   fResult = RandomTest.INSTANCE.nextFrom(fArray);
        isIn = false;
        for (float f : fArray) if (f == fResult) isIn = true;
        assertTrue(isIn);
        
        double[] dArray  = new double[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        double   dResult = RandomTest.INSTANCE.nextFrom(dArray);
        isIn = false;
        for (double d : dArray) if (d == dResult) isIn = true;
        assertTrue(isIn);
        
        Class<?>[] cArray  = new Class<?>[] {Object.class, Class.class, Type.class, Constructor.class, Method.class, Field.class, Parameter.class};
        Class<?>   cResult = RandomTest.INSTANCE.nextFrom(cArray);
        isIn = false;
        for (Class<?> c : cArray) if (c == cResult) isIn = true;
        assertTrue(isIn);
    
        List<Class<?>> clArray = Arrays.asList(cArray);
        Class<?>   clResult = RandomTest.INSTANCE.nextFrom(clArray);
        isIn = false;
        for (Class<?> cl : clArray) if (cl == clResult) isIn = true;
        assertTrue(isIn);
    }
}

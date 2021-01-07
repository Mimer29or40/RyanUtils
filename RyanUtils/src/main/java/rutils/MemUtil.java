package rutils;

import java.nio.*;

@SuppressWarnings("unused")
public class MemUtil
{
    private MemUtil() { }
    
    /**
     * Copies a source array to a destination buffer.
     *
     * @param src  The source array.
     * @param dest The destination buffer.
     */
    public static void memCopy(byte[] src, ByteBuffer dest)
    {
        int size1 = src.length;
        int size2 = dest.remaining();
        
        if (size1 != size2) throw new RuntimeException("Array/Buffer size mismatch: " + size1 + " != " + size2);
        
        for (byte b : src) dest.put(b);
    }
    
    /**
     * Copies a source array to a destination buffer.
     *
     * @param src  The source array.
     * @param dest The destination buffer.
     */
    public static void memCopy(int[] src, ByteBuffer dest)
    {
        int size1 = src.length;
        int size2 = dest.remaining();
        
        if (size1 != size2) throw new RuntimeException("Array/Buffer size mismatch: " + size1 + " != " + size2);
        
        for (int b : src) dest.put((byte) (b & 0xFF));
    }
    
    /**
     * Copies a source array to a destination buffer.
     *
     * @param src  The source array.
     * @param dest The destination buffer.
     */
    public static void memCopy(short[] src, ShortBuffer dest)
    {
        int size1 = src.length;
        int size2 = dest.remaining();
        
        if (size1 != size2) throw new RuntimeException("Array/Buffer size mismatch: " + size1 + " != " + size2);
        
        for (short b : src) dest.put(b);
    }
    
    /**
     * Copies a source array to a destination buffer.
     *
     * @param src  The source array.
     * @param dest The destination buffer.
     */
    public static void memCopy(int[] src, IntBuffer dest)
    {
        int size1 = src.length;
        int size2 = dest.remaining();
        
        if (size1 != size2) throw new RuntimeException("Array/Buffer size mismatch: " + size1 + " != " + size2);
        
        for (int b : src) dest.put(b);
    }
    
    /**
     * Copies a source array to a destination buffer.
     *
     * @param src  The source array.
     * @param dest The destination buffer.
     */
    public static void memCopy(long[] src, LongBuffer dest)
    {
        int size1 = src.length;
        int size2 = dest.remaining();
        
        if (size1 != size2) throw new RuntimeException("Array/Buffer size mismatch: " + size1 + " != " + size2);
        
        for (long b : src) dest.put(b);
    }
    
    /**
     * Copies a source array to a destination buffer.
     *
     * @param src  The source array.
     * @param dest The destination buffer.
     */
    public static void memCopy(float[] src, FloatBuffer dest)
    {
        int size1 = src.length;
        int size2 = dest.remaining();
        
        if (size1 != size2) throw new RuntimeException("Array/Buffer size mismatch: " + size1 + " != " + size2);
        
        for (float b : src) dest.put(b);
    }
    
    /**
     * Copies a source array to a destination buffer.
     *
     * @param src  The source array.
     * @param dest The destination buffer.
     */
    public static void memCopy(double[] src, DoubleBuffer dest)
    {
        int size1 = src.length;
        int size2 = dest.remaining();
        
        if (size1 != size2) throw new RuntimeException("Array/Buffer size mismatch: " + size1 + " != " + size2);
        
        for (double b : src) dest.put(b);
    }
    
    /**
     * Copies a source buffer to a destination array.
     *
     * @param src  The source buffer.
     * @param dest The destination array.
     */
    public static void memCopy(ByteBuffer src, byte[] dest)
    {
        int size1 = src.remaining();
        int size2 = dest.length;
        
        if (size1 != size2) throw new RuntimeException("Array/Buffer size mismatch: " + size1 + " != " + size2);
        
        for (int i = 0; i < size1; i++) dest[i] = src.get();
    }
    
    /**
     * Copies a source buffer to a destination array.
     *
     * @param src  The source buffer.
     * @param dest The destination array.
     */
    public static void memCopy(ByteBuffer src, int[] dest)
    {
        int size1 = src.remaining();
        int size2 = dest.length;
        
        if (size1 != size2) throw new RuntimeException("Array/Buffer size mismatch: " + size1 + " != " + size2);
        
        for (int i = 0; i < size1; i++) dest[i] = src.get() & 0xFF;
    }
    
    /**
     * Copies a source buffer to a destination array.
     *
     * @param src  The source buffer.
     * @param dest The destination array.
     */
    public static void memCopy(ShortBuffer src, short[] dest)
    {
        int size1 = src.remaining();
        int size2 = dest.length;
        
        if (size1 != size2) throw new RuntimeException("Array/Buffer size mismatch: " + size1 + " != " + size2);
        
        for (int i = 0; i < size1; i++) dest[i] = src.get();
    }
    
    /**
     * Copies a source buffer to a destination array.
     *
     * @param src  The source buffer.
     * @param dest The destination array.
     */
    public static void memCopy(IntBuffer src, int[] dest)
    {
        int size1 = src.remaining();
        int size2 = dest.length;
        
        if (size1 != size2) throw new RuntimeException("Array/Buffer size mismatch: " + size1 + " != " + size2);
        
        for (int i = 0; i < size1; i++) dest[i] = src.get();
    }
    
    /**
     * Copies a source buffer to a destination array.
     *
     * @param src  The source buffer.
     * @param dest The destination array.
     */
    public static void memCopy(LongBuffer src, long[] dest)
    {
        int size1 = src.remaining();
        int size2 = dest.length;
        
        if (size1 != size2) throw new RuntimeException("Array/Buffer size mismatch: " + size1 + " != " + size2);
        
        for (int i = 0; i < size1; i++) dest[i] = src.get();
    }
    
    /**
     * Copies a source buffer to a destination array.
     *
     * @param src  The source buffer.
     * @param dest The destination array.
     */
    public static void memCopy(FloatBuffer src, float[] dest)
    {
        int size1 = src.remaining();
        int size2 = dest.length;
        
        if (size1 != size2) throw new RuntimeException("Array/Buffer size mismatch: " + size1 + " != " + size2);
        
        for (int i = 0; i < size1; i++) dest[i] = src.get();
    }
    
    /**
     * Copies a source buffer to a destination array.
     *
     * @param src  The source buffer.
     * @param dest The destination array.
     */
    public static void memCopy(DoubleBuffer src, double[] dest)
    {
        int size1 = src.remaining();
        int size2 = dest.length;
        
        if (size1 != size2) throw new RuntimeException("Array/Buffer size mismatch: " + size1 + " != " + size2);
        
        for (int i = 0; i < size1; i++) dest[i] = src.get();
    }
}

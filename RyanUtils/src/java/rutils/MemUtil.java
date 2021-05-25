package rutils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.*;

public class MemUtil
{
    /**
     * Gets the size in bytes of an element of a {@link java.nio.Buffer Buffer}
     * object.
     *
     * @param buffer The buffer.
     * @return The size in bytes of an element of the buffer.
     */
    public static int elementSize(@NotNull Buffer buffer)
    {
        if (buffer instanceof ByteBuffer) return Byte.BYTES;
        if (buffer instanceof IntBuffer) return Integer.BYTES;
        if (buffer instanceof FloatBuffer) return Float.BYTES;
        if (buffer instanceof CharBuffer) return Character.BYTES;
        if (buffer instanceof ShortBuffer) return Short.BYTES;
        if (buffer instanceof LongBuffer) return Long.BYTES;
        if (buffer instanceof DoubleBuffer) return Double.BYTES;
        return 0;
    }
    
    /**
     * Gets the size in bytes of an element of a {@link java.nio.Buffer Buffer}
     * object.
     *
     * @param buffer The buffer.
     * @return The size in bytes of an element of the buffer.
     */
    public static long memAddressSafe(@NotNull Buffer buffer)
    {
        if (buffer instanceof ByteBuffer) return Byte.BYTES;
        if (buffer instanceof IntBuffer) return Integer.BYTES;
        if (buffer instanceof FloatBuffer) return Float.BYTES;
        if (buffer instanceof CharBuffer) return Character.BYTES;
        if (buffer instanceof ShortBuffer) return Short.BYTES;
        if (buffer instanceof LongBuffer) return Long.BYTES;
        if (buffer instanceof DoubleBuffer) return Double.BYTES;
        return 0;
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src     the source array.
     * @param srcPos  starting position in the source array.
     * @param dest    the destination buffer.
     * @param destPos starting position in the destination buffer.
     * @param length  the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(byte[] src, int srcPos, @Nullable ByteBuffer dest, int destPos, int length)
    {
        if (srcPos < 0) throw new IndexOutOfBoundsException("srcPos cannot be negative");
        if (destPos < 0) throw new IndexOutOfBoundsException("destPos cannot be negative");
        if (length < 0) throw new IndexOutOfBoundsException("length cannot be negative");
        
        if (src == null) throw new NullPointerException("src cannot be null");
        if (dest == null) throw new NullPointerException("dest cannot be null");
        
        if (srcPos + length > src.length) throw new IndexOutOfBoundsException("index exceeded src size");
        if (destPos + length > dest.limit()) throw new IndexOutOfBoundsException("index exceeded dest size");
        
        for (int i = 0; i < length; i++) dest.put(destPos + i, src[srcPos + i]);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src    the source array.
     * @param dest   the destination buffer.
     * @param length the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(byte[] src, @NotNull ByteBuffer dest, int length)
    {
        memCopy(src, 0, dest, dest.position(), length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src  the source array.
     * @param dest the destination buffer.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(byte[] src, @NotNull ByteBuffer dest)
    {
        memCopy(src, 0, dest, dest.position(), src.length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src     the source array.
     * @param srcPos  starting position in the source array.
     * @param dest    the destination buffer.
     * @param destPos starting position in the destination buffer.
     * @param length  the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(int[] src, int srcPos, @NotNull ByteBuffer dest, int destPos, int length)
    {
        if (srcPos < 0) throw new IndexOutOfBoundsException("srcPos cannot be negative");
        if (destPos < 0) throw new IndexOutOfBoundsException("destPos cannot be negative");
        if (length < 0) throw new IndexOutOfBoundsException("length cannot be negative");
        
        if (src == null) throw new NullPointerException("src cannot be null");
        if (dest == null) throw new NullPointerException("dest cannot be null");
        
        if (srcPos + length > src.length) throw new IndexOutOfBoundsException("index exceeded src size");
        if (destPos + length > dest.limit()) throw new IndexOutOfBoundsException("index exceeded dest size");
        
        for (int i = 0; i < length; i++) dest.put(destPos + i, (byte) (src[srcPos + i] & 0xFF));
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src    the source array.
     * @param dest   the destination buffer.
     * @param length the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(int[] src, @NotNull ByteBuffer dest, int length)
    {
        memCopy(src, 0, dest, dest.position(), length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src  the source array.
     * @param dest the destination buffer.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(int[] src, @NotNull ByteBuffer dest)
    {
        memCopy(src, 0, dest, dest.position(), src.length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src     the source array.
     * @param srcPos  starting position in the source array.
     * @param dest    the destination buffer.
     * @param destPos starting position in the destination buffer.
     * @param length  the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(short[] src, int srcPos, @Nullable ShortBuffer dest, int destPos, int length)
    {
        if (srcPos < 0) throw new IndexOutOfBoundsException("srcPos cannot be negative");
        if (destPos < 0) throw new IndexOutOfBoundsException("destPos cannot be negative");
        if (length < 0) throw new IndexOutOfBoundsException("length cannot be negative");
        
        if (src == null) throw new NullPointerException("src cannot be null");
        if (dest == null) throw new NullPointerException("dest cannot be null");
        
        if (srcPos + length > src.length) throw new IndexOutOfBoundsException("index exceeded src size");
        if (destPos + length > dest.limit()) throw new IndexOutOfBoundsException("index exceeded dest size");
        
        for (int i = 0; i < length; i++) dest.put(destPos + i, src[srcPos + i]);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src    the source array.
     * @param dest   the destination buffer.
     * @param length the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(short[] src, @NotNull ShortBuffer dest, int length)
    {
        memCopy(src, 0, dest, dest.position(), length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src  the source array.
     * @param dest the destination buffer.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(short[] src, @NotNull ShortBuffer dest)
    {
        memCopy(src, 0, dest, dest.position(), src.length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src     the source array.
     * @param srcPos  starting position in the source array.
     * @param dest    the destination buffer.
     * @param destPos starting position in the destination buffer.
     * @param length  the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(int[] src, int srcPos, @NotNull IntBuffer dest, int destPos, int length)
    {
        if (srcPos < 0) throw new IndexOutOfBoundsException("srcPos cannot be negative");
        if (destPos < 0) throw new IndexOutOfBoundsException("destPos cannot be negative");
        if (length < 0) throw new IndexOutOfBoundsException("length cannot be negative");
        
        if (src == null) throw new NullPointerException("src cannot be null");
        if (dest == null) throw new NullPointerException("dest cannot be null");
        
        if (srcPos + length > src.length) throw new IndexOutOfBoundsException("index exceeded src size");
        if (destPos + length > dest.limit()) throw new IndexOutOfBoundsException("index exceeded dest size");
        
        for (int i = 0; i < length; i++) dest.put(destPos + i, src[srcPos + i]);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src    the source array.
     * @param dest   the destination buffer.
     * @param length the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(int[] src, @NotNull IntBuffer dest, int length)
    {
        memCopy(src, 0, dest, dest.position(), length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src  the source array.
     * @param dest the destination buffer.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(int[] src, @NotNull IntBuffer dest)
    {
        memCopy(src, 0, dest, dest.position(), src.length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src     the source array.
     * @param srcPos  starting position in the source array.
     * @param dest    the destination buffer.
     * @param destPos starting position in the destination buffer.
     * @param length  the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(long[] src, int srcPos, @Nullable LongBuffer dest, int destPos, int length)
    {
        if (srcPos < 0) throw new IndexOutOfBoundsException("srcPos cannot be negative");
        if (destPos < 0) throw new IndexOutOfBoundsException("destPos cannot be negative");
        if (length < 0) throw new IndexOutOfBoundsException("length cannot be negative");
        
        if (src == null) throw new NullPointerException("src cannot be null");
        if (dest == null) throw new NullPointerException("dest cannot be null");
        
        if (srcPos + length > src.length) throw new IndexOutOfBoundsException("index exceeded src size");
        if (destPos + length > dest.limit()) throw new IndexOutOfBoundsException("index exceeded dest size");
        
        for (int i = 0; i < length; i++) dest.put(destPos + i, src[srcPos + i]);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src    the source array.
     * @param dest   the destination buffer.
     * @param length the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(long[] src, @NotNull LongBuffer dest, int length)
    {
        memCopy(src, 0, dest, dest.position(), length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src  the source array.
     * @param dest the destination buffer.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(long[] src, @NotNull LongBuffer dest)
    {
        memCopy(src, 0, dest, dest.position(), src.length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src     the source array.
     * @param srcPos  starting position in the source array.
     * @param dest    the destination buffer.
     * @param destPos starting position in the destination buffer.
     * @param length  the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(float[] src, int srcPos, @NotNull FloatBuffer dest, int destPos, int length)
    {
        if (srcPos < 0) throw new IndexOutOfBoundsException("srcPos cannot be negative");
        if (destPos < 0) throw new IndexOutOfBoundsException("destPos cannot be negative");
        if (length < 0) throw new IndexOutOfBoundsException("length cannot be negative");
        
        if (src == null) throw new NullPointerException("src cannot be null");
        if (dest == null) throw new NullPointerException("dest cannot be null");
        
        if (srcPos + length > src.length) throw new IndexOutOfBoundsException("index exceeded src size");
        if (destPos + length > dest.limit()) throw new IndexOutOfBoundsException("index exceeded dest size");
        
        for (int i = 0; i < length; i++) dest.put(destPos + i, src[srcPos + i]);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src    the source array.
     * @param dest   the destination buffer.
     * @param length the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(float[] src, @NotNull FloatBuffer dest, int length)
    {
        memCopy(src, 0, dest, dest.position(), length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src  the source array.
     * @param dest the destination buffer.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(float[] src, @NotNull FloatBuffer dest)
    {
        memCopy(src, 0, dest, dest.position(), src.length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src     the source array.
     * @param srcPos  starting position in the source array.
     * @param dest    the destination buffer.
     * @param destPos starting position in the destination buffer.
     * @param length  the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(double[] src, int srcPos, @NotNull DoubleBuffer dest, int destPos, int length)
    {
        if (srcPos < 0) throw new IndexOutOfBoundsException("srcPos cannot be negative");
        if (destPos < 0) throw new IndexOutOfBoundsException("destPos cannot be negative");
        if (length < 0) throw new IndexOutOfBoundsException("length cannot be negative");
        
        if (src == null) throw new NullPointerException("src cannot be null");
        if (dest == null) throw new NullPointerException("dest cannot be null");
        
        if (srcPos + length > src.length) throw new IndexOutOfBoundsException("index exceeded src size");
        if (destPos + length > dest.limit()) throw new IndexOutOfBoundsException("index exceeded dest size");
        
        for (int i = 0; i < length; i++) dest.put(destPos + i, src[srcPos + i]);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src    the source array.
     * @param dest   the destination buffer.
     * @param length the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(double[] src, @NotNull DoubleBuffer dest, int length)
    {
        memCopy(src, 0, dest, dest.position(), length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src  the source array.
     * @param dest the destination buffer.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(double[] src, @NotNull DoubleBuffer dest)
    {
        memCopy(src, 0, dest, dest.position(), src.length);
    }
    
    /**
     * Copies a buffer from the specified source buffer, beginning at the
     * specified position, to the specified position of the destination array.
     * A subsequence of array components are copied from the source
     * buffer referenced by {@code src} to the destination array
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source buffer are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination array.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.limit}, the length of the source buffer.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.length}, the length of the destination array.
     * </ul>
     *
     * @param src     the source buffer.
     * @param srcPos  starting position in the source buffer.
     * @param dest    the destination array.
     * @param destPos starting position in the destination array.
     * @param length  the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@Nullable ByteBuffer src, int srcPos, byte[] dest, int destPos, int length)
    {
        if (srcPos < 0) throw new IndexOutOfBoundsException("srcPos cannot be negative");
        if (destPos < 0) throw new IndexOutOfBoundsException("destPos cannot be negative");
        if (length < 0) throw new IndexOutOfBoundsException("length cannot be negative");
        
        if (src == null) throw new NullPointerException("src cannot be null");
        if (dest == null) throw new NullPointerException("dest cannot be null");
        
        if (srcPos + length > src.limit()) throw new IndexOutOfBoundsException("index exceeded src size");
        if (destPos + length > dest.length) throw new IndexOutOfBoundsException("index exceeded dest size");
        
        for (int i = 0; i < length; i++) dest[destPos + i] = src.get(srcPos + i);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src    the source array.
     * @param dest   the destination buffer.
     * @param length the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@NotNull ByteBuffer src, byte[] dest, int length)
    {
        memCopy(src, src.position(), dest, 0, length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src  the source array.
     * @param dest the destination buffer.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@NotNull ByteBuffer src, byte[] dest)
    {
        memCopy(src, src.position(), dest, 0, src.limit());
    }
    
    /**
     * Copies a buffer from the specified source buffer, beginning at the
     * specified position, to the specified position of the destination array.
     * A subsequence of array components are copied from the source
     * buffer referenced by {@code src} to the destination array
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source buffer are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination array.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.limit}, the length of the source buffer.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.length}, the length of the destination array.
     * </ul>
     *
     * @param src     the source buffer.
     * @param srcPos  starting position in the source buffer.
     * @param dest    the destination array.
     * @param destPos starting position in the destination array.
     * @param length  the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@Nullable ByteBuffer src, int srcPos, int[] dest, int destPos, int length)
    {
        if (srcPos < 0) throw new IndexOutOfBoundsException("srcPos cannot be negative");
        if (destPos < 0) throw new IndexOutOfBoundsException("destPos cannot be negative");
        if (length < 0) throw new IndexOutOfBoundsException("length cannot be negative");
        
        if (src == null) throw new NullPointerException("src cannot be null");
        if (dest == null) throw new NullPointerException("dest cannot be null");
        
        if (srcPos + length > src.limit()) throw new IndexOutOfBoundsException("index exceeded src size");
        if (destPos + length > dest.length) throw new IndexOutOfBoundsException("index exceeded dest size");
        
        for (int i = 0; i < length; i++) dest[destPos + i] = src.get(srcPos + i) & 0xFF;
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src    the source array.
     * @param dest   the destination buffer.
     * @param length the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@NotNull ByteBuffer src, int[] dest, int length)
    {
        memCopy(src, src.position(), dest, 0, length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src  the source array.
     * @param dest the destination buffer.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@NotNull ByteBuffer src, int[] dest)
    {
        memCopy(src, src.position(), dest, 0, src.limit());
    }
    
    /**
     * Copies a buffer from the specified source buffer, beginning at the
     * specified position, to the specified position of the destination array.
     * A subsequence of array components are copied from the source
     * buffer referenced by {@code src} to the destination array
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source buffer are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination array.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.limit}, the length of the source buffer.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.length}, the length of the destination array.
     * </ul>
     *
     * @param src     the source buffer.
     * @param srcPos  starting position in the source buffer.
     * @param dest    the destination array.
     * @param destPos starting position in the destination array.
     * @param length  the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@Nullable ShortBuffer src, int srcPos, short[] dest, int destPos, int length)
    {
        if (srcPos < 0) throw new IndexOutOfBoundsException("srcPos cannot be negative");
        if (destPos < 0) throw new IndexOutOfBoundsException("destPos cannot be negative");
        if (length < 0) throw new IndexOutOfBoundsException("length cannot be negative");
        
        if (src == null) throw new NullPointerException("src cannot be null");
        if (dest == null) throw new NullPointerException("dest cannot be null");
        
        if (srcPos + length > src.limit()) throw new IndexOutOfBoundsException("index exceeded src size");
        if (destPos + length > dest.length) throw new IndexOutOfBoundsException("index exceeded dest size");
        
        for (int i = 0; i < length; i++) dest[destPos + i] = src.get(srcPos + i);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src    the source array.
     * @param dest   the destination buffer.
     * @param length the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@NotNull ShortBuffer src, short[] dest, int length)
    {
        memCopy(src, src.position(), dest, 0, length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src  the source array.
     * @param dest the destination buffer.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@NotNull ShortBuffer src, short[] dest)
    {
        memCopy(src, src.position(), dest, 0, src.limit());
    }
    
    /**
     * Copies a buffer from the specified source buffer, beginning at the
     * specified position, to the specified position of the destination array.
     * A subsequence of array components are copied from the source
     * buffer referenced by {@code src} to the destination array
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source buffer are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination array.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.limit}, the length of the source buffer.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.length}, the length of the destination array.
     * </ul>
     *
     * @param src     the source buffer.
     * @param srcPos  starting position in the source buffer.
     * @param dest    the destination array.
     * @param destPos starting position in the destination array.
     * @param length  the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@NotNull IntBuffer src, int srcPos, int[] dest, int destPos, int length)
    {
        if (srcPos < 0) throw new IndexOutOfBoundsException("srcPos cannot be negative");
        if (destPos < 0) throw new IndexOutOfBoundsException("destPos cannot be negative");
        if (length < 0) throw new IndexOutOfBoundsException("length cannot be negative");
        
        if (src == null) throw new NullPointerException("src cannot be null");
        if (dest == null) throw new NullPointerException("dest cannot be null");
        
        if (srcPos + length > src.limit()) throw new IndexOutOfBoundsException("index exceeded src size");
        if (destPos + length > dest.length) throw new IndexOutOfBoundsException("index exceeded dest size");
        
        for (int i = 0; i < length; i++) dest[destPos + i] = src.get(srcPos + i);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src    the source array.
     * @param dest   the destination buffer.
     * @param length the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@NotNull IntBuffer src, int[] dest, int length)
    {
        memCopy(src, src.position(), dest, 0, length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src  the source array.
     * @param dest the destination buffer.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@NotNull IntBuffer src, int[] dest)
    {
        memCopy(src, src.position(), dest, 0, src.limit());
    }
    
    /**
     * Copies a buffer from the specified source buffer, beginning at the
     * specified position, to the specified position of the destination array.
     * A subsequence of array components are copied from the source
     * buffer referenced by {@code src} to the destination array
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source buffer are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination array.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.limit}, the length of the source buffer.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.length}, the length of the destination array.
     * </ul>
     *
     * @param src     the source buffer.
     * @param srcPos  starting position in the source buffer.
     * @param dest    the destination array.
     * @param destPos starting position in the destination array.
     * @param length  the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@Nullable LongBuffer src, int srcPos, long[] dest, int destPos, int length)
    {
        if (srcPos < 0) throw new IndexOutOfBoundsException("srcPos cannot be negative");
        if (destPos < 0) throw new IndexOutOfBoundsException("destPos cannot be negative");
        if (length < 0) throw new IndexOutOfBoundsException("length cannot be negative");
        
        if (src == null) throw new NullPointerException("src cannot be null");
        if (dest == null) throw new NullPointerException("dest cannot be null");
        
        if (srcPos + length > src.limit()) throw new IndexOutOfBoundsException("index exceeded src size");
        if (destPos + length > dest.length) throw new IndexOutOfBoundsException("index exceeded dest size");
        
        for (int i = 0; i < length; i++) dest[destPos + i] = src.get(srcPos + i);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src    the source array.
     * @param dest   the destination buffer.
     * @param length the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@NotNull LongBuffer src, long[] dest, int length)
    {
        memCopy(src, src.position(), dest, 0, length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src  the source array.
     * @param dest the destination buffer.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@NotNull LongBuffer src, long[] dest)
    {
        memCopy(src, src.position(), dest, 0, src.limit());
    }
    
    /**
     * Copies a buffer from the specified source buffer, beginning at the
     * specified position, to the specified position of the destination array.
     * A subsequence of array components are copied from the source
     * buffer referenced by {@code src} to the destination array
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source buffer are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination array.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.limit}, the length of the source buffer.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.length}, the length of the destination array.
     * </ul>
     *
     * @param src     the source buffer.
     * @param srcPos  starting position in the source buffer.
     * @param dest    the destination array.
     * @param destPos starting position in the destination array.
     * @param length  the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@Nullable FloatBuffer src, int srcPos, float[] dest, int destPos, int length)
    {
        if (srcPos < 0) throw new IndexOutOfBoundsException("srcPos cannot be negative");
        if (destPos < 0) throw new IndexOutOfBoundsException("destPos cannot be negative");
        if (length < 0) throw new IndexOutOfBoundsException("length cannot be negative");
        
        if (src == null) throw new NullPointerException("src cannot be null");
        if (dest == null) throw new NullPointerException("dest cannot be null");
        
        if (srcPos + length > src.limit()) throw new IndexOutOfBoundsException("index exceeded src size");
        if (destPos + length > dest.length) throw new IndexOutOfBoundsException("index exceeded dest size");
        
        for (int i = 0; i < length; i++) dest[destPos + i] = src.get(srcPos + i);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src    the source array.
     * @param dest   the destination buffer.
     * @param length the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@NotNull FloatBuffer src, float[] dest, int length)
    {
        memCopy(src, src.position(), dest, 0, length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src  the source array.
     * @param dest the destination buffer.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@NotNull FloatBuffer src, float[] dest)
    {
        memCopy(src, src.position(), dest, 0, src.limit());
    }
    
    /**
     * Copies a buffer from the specified source buffer, beginning at the
     * specified position, to the specified position of the destination array.
     * A subsequence of array components are copied from the source
     * buffer referenced by {@code src} to the destination array
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source buffer are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination array.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.limit}, the length of the source buffer.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.length}, the length of the destination array.
     * </ul>
     *
     * @param src     the source buffer.
     * @param srcPos  starting position in the source buffer.
     * @param dest    the destination array.
     * @param destPos starting position in the destination array.
     * @param length  the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@Nullable DoubleBuffer src, int srcPos, double[] dest, int destPos, int length)
    {
        if (srcPos < 0) throw new IndexOutOfBoundsException("srcPos cannot be negative");
        if (destPos < 0) throw new IndexOutOfBoundsException("destPos cannot be negative");
        if (length < 0) throw new IndexOutOfBoundsException("length cannot be negative");
        
        if (src == null) throw new NullPointerException("src cannot be null");
        if (dest == null) throw new NullPointerException("dest cannot be null");
        
        if (srcPos + length > src.limit()) throw new IndexOutOfBoundsException("index exceeded src size");
        if (destPos + length > dest.length) throw new IndexOutOfBoundsException("index exceeded dest size");
        
        for (int i = 0; i < length; i++) dest[destPos + i] = src.get(srcPos + i);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src    the source array.
     * @param dest   the destination buffer.
     * @param length the number of array elements to be copied.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@NotNull DoubleBuffer src, double[] dest, int length)
    {
        memCopy(src, src.position(), dest, 0, length);
    }
    
    /**
     * Copies an array from the specified source array, beginning at the
     * specified position, to the specified position of the destination buffer.
     * A subsequence of array components are copied from the source
     * array referenced by {@code src} to the destination buffer
     * referenced by {@code dest}. The number of components copied is
     * equal to the {@code length} argument. The components at
     * positions {@code srcPos} through
     * {@code srcPos+length-1} in the source array are copied into
     * positions {@code destPos} through
     * {@code destPos+length-1}, respectively, of the destination
     * buffer.
     * <p>
     * Otherwise, if any of the following is true, an
     * {@code IndexOutOfBoundsException} is
     * thrown and the destination is not modified:
     * <ul>
     * <li>The {@code srcPos} argument is negative.
     * <li>The {@code destPos} argument is negative.
     * <li>The {@code length} argument is negative.
     * <li>{@code srcPos+length} is greater than
     *     {@code src.length}, the length of the source array.
     * <li>{@code destPos+length} is greater than
     *     {@code dest.limit}, the length of the destination array.
     * </ul>
     *
     * @param src  the source array.
     * @param dest the destination buffer.
     * @throws IndexOutOfBoundsException if copying would cause
     *                                   access of data outside array bounds.
     * @throws NullPointerException      if either {@code src} or
     *                                   {@code dest} is {@code null}.
     */
    public static void memCopy(@NotNull DoubleBuffer src, double[] dest)
    {
        memCopy(src, src.position(), dest, 0, src.limit());
    }
    
    private MemUtil() { }
}

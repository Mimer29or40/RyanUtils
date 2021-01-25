package rutils;

import org.junit.jupiter.api.Test;

import java.nio.*;

import static org.junit.jupiter.api.Assertions.*;

class MemUtilTest
{
    @Test
    void memCopy()
    {
        final Random rand = new Random(1337);
        
        int size   = 10;
        int length = 5;
        
        ByteBuffer   bBuffer = ByteBuffer.allocate(size);
        ShortBuffer  sBuffer = ShortBuffer.allocate(size);
        IntBuffer    iBuffer = IntBuffer.allocate(size);
        LongBuffer   lBuffer = LongBuffer.allocate(size);
        FloatBuffer  fBuffer = FloatBuffer.allocate(size);
        DoubleBuffer dBuffer = DoubleBuffer.allocate(size);
        
        byte[]   bArray = new byte[size];
        short[]  sArray = new short[size];
        int[]    iArray = new int[size];
        long[]   lArray = new long[size];
        float[]  fArray = new float[size];
        double[] dArray = new double[size];
        
        for (int i = 0; i < size; i++) bArray[i] = (byte) rand.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
        MemUtil.memCopy(bArray, bBuffer, length);
        for (int i = 0; i < length; i++) assertEquals(bArray[i], bBuffer.get(i));
        
        for (int i = 0; i < size; i++) bArray[i] = (byte) rand.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE);
        MemUtil.memCopy(bArray, bBuffer);
        for (int i = 0; i < size; i++) assertEquals(bArray[i], bBuffer.get(i));
        
        for (int i = 0; i < size; i++) iArray[i] = rand.nextInt(255);
        MemUtil.memCopy(iArray, bBuffer, length);
        for (int i = 0; i < length; i++) assertEquals((byte) iArray[i], bBuffer.get(i));
        
        for (int i = 0; i < size; i++) iArray[i] = rand.nextInt(255);
        MemUtil.memCopy(iArray, bBuffer);
        for (int i = 0; i < size; i++) assertEquals((byte) iArray[i], bBuffer.get(i));
        
        for (int i = 0; i < size; i++) sArray[i] = (short) rand.nextInt(Short.MIN_VALUE, Short.MAX_VALUE);
        MemUtil.memCopy(sArray, sBuffer, length);
        for (int i = 0; i < length; i++) assertEquals(sArray[i], sBuffer.get(i));
        
        for (int i = 0; i < size; i++) sArray[i] = (short) rand.nextInt(Short.MIN_VALUE, Short.MAX_VALUE);
        MemUtil.memCopy(sArray, sBuffer);
        for (int i = 0; i < size; i++) assertEquals(sArray[i], sBuffer.get(i));
        
        for (int i = 0; i < size; i++) iArray[i] = rand.nextInt();
        MemUtil.memCopy(iArray, iBuffer, length);
        for (int i = 0; i < length; i++) assertEquals(iArray[i], iBuffer.get(i));
        
        for (int i = 0; i < size; i++) iArray[i] = rand.nextInt();
        MemUtil.memCopy(iArray, iBuffer);
        for (int i = 0; i < size; i++) assertEquals(iArray[i], iBuffer.get(i));
        
        for (int i = 0; i < size; i++) lArray[i] = rand.nextLong();
        MemUtil.memCopy(lArray, lBuffer, length);
        for (int i = 0; i < length; i++) assertEquals(lArray[i], lBuffer.get(i));
        
        for (int i = 0; i < size; i++) lArray[i] = rand.nextLong();
        MemUtil.memCopy(lArray, lBuffer);
        for (int i = 0; i < size; i++) assertEquals(lArray[i], lBuffer.get(i));
        
        for (int i = 0; i < size; i++) fArray[i] = rand.nextFloat();
        MemUtil.memCopy(fArray, fBuffer, length);
        for (int i = 0; i < length; i++) assertEquals(fArray[i], fBuffer.get(i));
        
        for (int i = 0; i < size; i++) fArray[i] = rand.nextFloat();
        MemUtil.memCopy(fArray, fBuffer);
        for (int i = 0; i < size; i++) assertEquals(fArray[i], fBuffer.get(i));
        
        for (int i = 0; i < size; i++) dArray[i] = rand.nextDouble();
        MemUtil.memCopy(dArray, dBuffer, length);
        for (int i = 0; i < length; i++) assertEquals(dArray[i], dBuffer.get(i));
        
        for (int i = 0; i < size; i++) dArray[i] = rand.nextDouble();
        MemUtil.memCopy(dArray, dBuffer);
        for (int i = 0; i < size; i++) assertEquals(dArray[i], dBuffer.get(i));
        
        
        for (int i = 0; i < size; i++) bBuffer.put(i, (byte) rand.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE));
        MemUtil.memCopy(bBuffer, bArray, length);
        for (int i = 0; i < length; i++) assertEquals(bBuffer.get(i), bArray[i]);
        
        for (int i = 0; i < size; i++) bBuffer.put(i, (byte) rand.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE));
        MemUtil.memCopy(bBuffer, bArray);
        for (int i = 0; i < size; i++) assertEquals(bBuffer.get(i), bArray[i]);
        
        for (int i = 0; i < size; i++) iBuffer.put(i, rand.nextInt(255));
        MemUtil.memCopy(bBuffer, iArray, length);
        for (int i = 0; i < length; i++) assertEquals(bBuffer.get(i), (byte) iArray[i]);
        
        for (int i = 0; i < size; i++) iBuffer.put(i, rand.nextInt(255));
        MemUtil.memCopy(bBuffer, iArray);
        for (int i = 0; i < size; i++) assertEquals(bBuffer.get(i), (byte) iArray[i]);
        
        for (int i = 0; i < size; i++) sBuffer.put(i, (short) rand.nextInt(Short.MIN_VALUE, Short.MAX_VALUE));
        MemUtil.memCopy(sBuffer, sArray, length);
        for (int i = 0; i < length; i++) assertEquals(sBuffer.get(i), sArray[i]);
        
        for (int i = 0; i < size; i++) sBuffer.put(i, (short) rand.nextInt(Short.MIN_VALUE, Short.MAX_VALUE));
        MemUtil.memCopy(sBuffer, sArray);
        for (int i = 0; i < size; i++) assertEquals(sBuffer.get(i), sArray[i]);
        
        for (int i = 0; i < size; i++) iBuffer.put(i, rand.nextInt());
        MemUtil.memCopy(iBuffer, iArray, length);
        for (int i = 0; i < length; i++) assertEquals(iBuffer.get(i), iArray[i]);
        
        for (int i = 0; i < size; i++) iBuffer.put(i, rand.nextInt());
        MemUtil.memCopy(iBuffer, iArray);
        for (int i = 0; i < size; i++) assertEquals(iBuffer.get(i), iArray[i]);
        
        for (int i = 0; i < size; i++) lBuffer.put(i, rand.nextLong());
        MemUtil.memCopy(lBuffer, lArray, length);
        for (int i = 0; i < length; i++) assertEquals(lBuffer.get(i), lArray[i]);
        
        for (int i = 0; i < size; i++) lBuffer.put(i, rand.nextLong());
        MemUtil.memCopy(lBuffer, lArray);
        for (int i = 0; i < size; i++) assertEquals(lBuffer.get(i), lArray[i]);
        
        for (int i = 0; i < size; i++) fBuffer.put(i, rand.nextFloat());
        MemUtil.memCopy(fBuffer, fArray, length);
        for (int i = 0; i < length; i++) assertEquals(fBuffer.get(i), fArray[i]);
        
        for (int i = 0; i < size; i++) fBuffer.put(i, rand.nextFloat());
        MemUtil.memCopy(fBuffer, fArray);
        for (int i = 0; i < size; i++) assertEquals(fBuffer.get(i), fArray[i]);
        
        for (int i = 0; i < size; i++) dBuffer.put(i, rand.nextDouble());
        MemUtil.memCopy(dBuffer, dArray, length);
        for (int i = 0; i < length; i++) assertEquals(dBuffer.get(i), dArray[i]);
        
        for (int i = 0; i < size; i++) dBuffer.put(i, rand.nextDouble());
        MemUtil.memCopy(dBuffer, dArray);
        for (int i = 0; i < size; i++) assertEquals(dBuffer.get(i), dArray[i]);
    }
}

package rutils;

import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StringUtilTest
{
    private static final Logger LOGGER = new Logger();
    
    @Test
    void testToString()
    {
        String result;
        result = StringUtil.toString(new Exception("Test Exception Message"));
        LOGGER.info(result);
        
        result = StringUtil.toString(new Error("Test Error Message"));
        LOGGER.info(result);
        
        result = StringUtil.toString(null);
        assertEquals(result, "null");
        
        boolean[] booleanArray = new boolean[] {true, true, false, false};
        result = StringUtil.toString(booleanArray);
        assertEquals(result, "[true, true, false, false]");
        
        byte[] byteArray = new byte[] {0, -1, Byte.MAX_VALUE, Byte.MIN_VALUE};
        result = StringUtil.toString(byteArray);
        assertEquals(result, "[0, -1, " + Byte.MAX_VALUE + ", " + Byte.MIN_VALUE + "]");
        
        short[] shortArray = new short[] {0, -1, Short.MAX_VALUE, Short.MIN_VALUE};
        result = StringUtil.toString(shortArray);
        assertEquals(result, "[0, -1, " + Short.MAX_VALUE + ", " + Short.MIN_VALUE + "]");
        
        char[] charArray = new char[] {Character.MIN_VALUE, Character.MAX_VALUE};
        result = StringUtil.toString(charArray);
        assertEquals(result, "[" + Character.MIN_VALUE + ", " + Character.MAX_VALUE + "]");
        
        int[] intArray = new int[] {0, -1, Integer.MAX_VALUE, Integer.MIN_VALUE};
        result = StringUtil.toString(intArray);
        assertEquals(result, "[0, -1, " + Integer.MAX_VALUE + ", " + Integer.MIN_VALUE + "]");
        
        long[] longArray = new long[] {0L, -1L, Long.MAX_VALUE, Long.MIN_VALUE};
        result = StringUtil.toString(longArray);
        assertEquals(result, "[0, -1, " + Long.MAX_VALUE + ", " + Long.MIN_VALUE + "]");
        
        float[] floatArray = new float[] {0F, -1F, Float.MAX_VALUE, Float.MIN_VALUE};
        result = StringUtil.toString(floatArray);
        assertEquals(result, "[0.0, -1.0, " + Float.MAX_VALUE + ", " + Float.MIN_VALUE + "]");
        
        double[] doubleArray = new double[] {0D, -1D, Double.MAX_VALUE, Double.MIN_VALUE};
        result = StringUtil.toString(doubleArray);
        assertEquals(result, "[0.0, -1.0, " + Double.MAX_VALUE + ", " + Double.MIN_VALUE + "]");
        
        Object[] objectArray = new Object[] {true, 1.0, Object.class, null, new Object[] {1, 2, 3}};
        result = StringUtil.toString(objectArray);
        assertEquals(result, "[true, 1.0, " + Object.class.toString() + ", null, [1, 2, 3]]");
    }
    
    @Test
    void getCurrentDateTimeString()
    {
        LOGGER.info(StringUtil.getCurrentDateTimeString());
    }
    
    @Test
    void getCurrentTimeString()
    {
        LOGGER.info(StringUtil.getCurrentTimeString());
    }
    
    @Test
    void getCurrentDateString()
    {
        LOGGER.info(StringUtil.getCurrentDateString());
    }
    
    @Test
    void isFormatterString()
    {
        String  input;
        boolean actual;
        
        input  = "Contains no formatting";
        actual = StringUtil.isFormatterString(input);
        assertFalse(actual);
        
        input  = "Contains formatting %s %n";
        actual = StringUtil.isFormatterString(input);
        assertTrue(actual);
    }
    
    @Test
    void printStream()
    {
        StringUtil.print((Object) null);
        StringUtil.print(true);
        StringUtil.print('%');
        StringUtil.print((byte) 1);
        StringUtil.print((short) 1);
        StringUtil.print(1);
        StringUtil.print(1L);
        StringUtil.print(1F);
        StringUtil.print(1D);
        StringUtil.print("Thing");
        
        StringUtil.print(null, null);
        StringUtil.print(true, true);
        StringUtil.print('%', '%');
        StringUtil.print((byte) 1, (byte) 1);
        StringUtil.print((short) 1, (short) 1);
        StringUtil.print(1, 1);
        StringUtil.print(1L, 1L);
        StringUtil.print(1F, 1F);
        StringUtil.print(1D, 1D);
        StringUtil.print("Thing", "Thing");
        
        StringUtil.print("{%s-%s}", null, null);
        StringUtil.print("{%s-%s}", true, true);
        StringUtil.print("{%s-%s}", '%', '%');
        StringUtil.print("{%s-%s}", (byte) 1, (byte) 1);
        StringUtil.print("{%s-%s}", (short) 1, (short) 1);
        StringUtil.print("{%s-%s}", 1, 1);
        StringUtil.print("{%s-%s}", 1L, 1L);
        StringUtil.print("{%s-%s}", 1F, 1F);
        StringUtil.print("{%s-%s}", 1D, 1D);
        StringUtil.print("{%s-%s}", "Thing", "Thing");
    }
    
    @Test
    void printlnToStream()
    {
        StringUtil.println((Object) null);
        StringUtil.println(true);
        StringUtil.println('%');
        StringUtil.println((byte) 1);
        StringUtil.println((short) 1);
        StringUtil.println(1);
        StringUtil.println(1L);
        StringUtil.println(1F);
        StringUtil.println(1D);
        StringUtil.println("Thing");
        
        StringUtil.println(null, null);
        StringUtil.println(true, true);
        StringUtil.println('%', '%');
        StringUtil.println((byte) 1, (byte) 1);
        StringUtil.println((short) 1, (short) 1);
        StringUtil.println(1, 1);
        StringUtil.println(1L, 1L);
        StringUtil.println(1F, 1F);
        StringUtil.println(1D, 1D);
        StringUtil.println("Thing", "Thing");
        
        StringUtil.println("{%s-%s}", null, null);
        StringUtil.println("{%s-%s}", true, true);
        StringUtil.println("{%s-%s}", '%', '%');
        StringUtil.println("{%s-%s}", (byte) 1, (byte) 1);
        StringUtil.println("{%s-%s}", (short) 1, (short) 1);
        StringUtil.println("{%s-%s}", 1, 1);
        StringUtil.println("{%s-%s}", 1L, 1L);
        StringUtil.println("{%s-%s}", 1F, 1F);
        StringUtil.println("{%s-%s}", 1D, 1D);
        StringUtil.println("{%s-%s}", "Thing", "Thing");
    }
    
    @Test
    void join()
    {
        String expected, actual;
        
        List<Integer> iterable = Arrays.asList(1, 2, 3, 4, 5);
        Integer[]     array    = new Integer[] {1, 2, 3, 4, 5};
        
        expected = "[1-2-3-4-5]";
        actual   = StringUtil.join(iterable, "-", "[", "]");
        assertEquals(expected, actual);
        
        expected = "[1-2-3-4-5]";
        actual   = StringUtil.join(array, "-", "[", "]");
        assertEquals(expected, actual);
        
        expected = "1-2-3-4-5";
        actual   = StringUtil.join(iterable, "-");
        assertEquals(expected, actual);
        
        expected = "1-2-3-4-5";
        actual   = StringUtil.join(array, "-");
        assertEquals(expected, actual);
        
        expected = "1, 2, 3, 4, 5";
        actual   = StringUtil.join(iterable);
        assertEquals(expected, actual);
        
        expected = "1, 2, 3, 4, 5";
        actual   = StringUtil.join(array);
        assertEquals(expected, actual);
    }
}

package rutils;

import org.junit.jupiter.api.Test;
import rutils.group.IPairI;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ConstantConditions")
class MathTest
{
    @Test
    void math()
    {
        float  fTime = 0.8675309F;
        double dTime = 0.8675309D;
        
        float fActual, fExpected;
        double dActual, dExpected;
        
        fActual = Math.sin(fTime);
        fExpected = (float) java.lang.Math.sin(fTime);
        assertEquals(fExpected, fActual, 0.00000001);
        
        dActual = Math.sin(dTime);
        dExpected = java.lang.Math.sin(dTime);
        assertEquals(dExpected, dActual, 0.00000001);
        
        fActual = Math.cos(fTime);
        fExpected = (float) java.lang.Math.cos(fTime);
        assertEquals(fExpected, fActual, 0.00000001);
        
        dActual = Math.cos(dTime);
        dExpected = java.lang.Math.cos(dTime);
        assertEquals(dExpected, dActual, 0.00000001);
        
        fActual = Math.tan(fTime);
        fExpected = (float) java.lang.Math.tan(fTime);
        assertEquals(fExpected, fActual, 0.00000001);
        
        dActual = Math.tan(dTime);
        dExpected = java.lang.Math.tan(dTime);
        assertEquals(dExpected, dActual, 0.00000001);
        
        fActual = Math.asin(fTime);
        fExpected = (float) java.lang.Math.asin(fTime);
        assertEquals(fExpected, fActual, 0.00000001);
        
        dActual = Math.asin(dTime);
        dExpected = java.lang.Math.asin(dTime);
        assertEquals(dExpected, dActual, 0.00000001);
        
        fActual = Math.acos(fTime);
        fExpected = (float) java.lang.Math.acos(fTime);
        assertEquals(fExpected, fActual, 0.00000001);
        
        dActual = Math.acos(dTime);
        dExpected = java.lang.Math.acos(dTime);
        assertEquals(dExpected, dActual, 0.00000001);
        
        fActual = Math.atan(fTime);
        fExpected = (float) java.lang.Math.atan(fTime);
        assertEquals(fExpected, fActual, 0.00000001);
        
        dActual = Math.atan(dTime);
        dExpected = java.lang.Math.atan(dTime);
        assertEquals(dExpected, dActual, 0.00000001);
        
        fActual = Math.sinh(fTime);
        fExpected = (float) java.lang.Math.sinh(fTime);
        assertEquals(fExpected, fActual, 0.00000001);
        
        dActual = Math.sinh(dTime);
        dExpected = java.lang.Math.sinh(dTime);
        assertEquals(dExpected, dActual, 0.00000001);
        
        fActual = Math.cosh(fTime);
        fExpected = (float) java.lang.Math.cosh(fTime);
        assertEquals(fExpected, fActual, 0.00000001);
        
        dActual = Math.cosh(dTime);
        dExpected = java.lang.Math.cosh(dTime);
        assertEquals(dExpected, dActual, 0.00000001);
        
        fActual = Math.tanh(fTime);
        fExpected = (float) java.lang.Math.tanh(fTime);
        assertEquals(fExpected, fActual, 0.00000001);
        
        dActual = Math.tanh(dTime);
        dExpected = java.lang.Math.tanh(dTime);
        assertEquals(dExpected, dActual, 0.00000001);
        
        fActual = Math.hypot(fTime, fTime);
        fExpected = (float) java.lang.Math.hypot(fTime, fTime);
        assertEquals(fExpected, fActual, 0.00000001);
        
        dActual = Math.hypot(dTime, dTime);
        dExpected = java.lang.Math.hypot(dTime, dTime);
        assertEquals(dExpected, dActual, 0.00000001);
    
        fActual = Math.toRadians(180.0F);
        fExpected = Math.PIf;
        assertEquals(fExpected, fActual, 0.00000001);
    
        fActual = Math.toRadians(360.0F);
        fExpected = Math.PI2f;
        assertEquals(fExpected, fActual, 0.00000001);
    
        fActual = Math.toRadians(90.0F);
        fExpected = Math.PI_2f;
        assertEquals(fExpected, fActual, 0.00000001);
    
        fActual = Math.toRadians(60.0F);
        fExpected = Math.PI_3f;
        assertEquals(fExpected, fActual, 0.00000001);
    
        fActual = Math.toRadians(45.0F);
        fExpected = Math.PI_4f;
        assertEquals(fExpected, fActual, 0.00000001);
        
        fActual = Math.toRadians(30.0F);
        fExpected = Math.PI_6f;
        assertEquals(fExpected, fActual, 0.00000001);
    
        dActual = Math.toRadians(180.0);
        dExpected = Math.PI;
        assertEquals(dExpected, dActual, 0.00000001);
    
        dActual = Math.toRadians(360.0);
        dExpected = Math.PI2;
        assertEquals(dExpected, dActual, 0.00000001);
    
        dActual = Math.toRadians(90.0);
        dExpected = Math.PI_2;
        assertEquals(dExpected, dActual, 0.00000001);
    
        dActual = Math.toRadians(60.0);
        dExpected = Math.PI_3;
        assertEquals(dExpected, dActual, 0.00000001);
    
        dActual = Math.toRadians(45.0);
        dExpected = Math.PI_4;
        assertEquals(dExpected, dActual, 0.00000001);
    
        dActual = Math.toRadians(30.0);
        dExpected = Math.PI_6;
        assertEquals(dExpected, dActual, 0.00000001);
    
        fActual = Math.toDegrees(Math.PIf);
        fExpected = 180.0F;
        assertEquals(fExpected, fActual, 0.00000001);
    
        fActual = Math.toDegrees(Math.PI2f);
        fExpected = 360.0F;
        assertEquals(fExpected, fActual, 0.00000001);
    
        fActual = Math.toDegrees(Math.PI_2f);
        fExpected = 90.0F;
        assertEquals(fExpected, fActual, 0.00000001);
    
        fActual = Math.toDegrees(Math.PI_3f);
        fExpected = 60.0F;
        assertEquals(fExpected, fActual, 0.00000001);
    
        fActual = Math.toDegrees(Math.PI_4f);
        fExpected = 45.0F;
        assertEquals(fExpected, fActual, 0.00000001);
        
        fActual = Math.toDegrees(Math.PI_6f);
        fExpected = 30.0F;
        assertEquals(fExpected, fActual, 0.00000001);
    
        dActual = Math.toDegrees(Math.PI);
        dExpected = 180.0;
        assertEquals(dExpected, dActual, 0.00000001);
    
        dActual = Math.toDegrees(Math.PI2);
        dExpected = 360.0;
        assertEquals(dExpected, dActual, 0.00000001);
    
        dActual = Math.toDegrees(Math.PI_2);
        dExpected = 90.0;
        assertEquals(dExpected, dActual, 0.00000001);
    
        dActual = Math.toDegrees(Math.PI_3);
        dExpected = 60.0;
        assertEquals(dExpected, dActual, 0.00000001);
    
        dActual = Math.toDegrees(Math.PI_4);
        dExpected = 45.0;
        assertEquals(dExpected, dActual, 0.00000001);
    
        dActual = Math.toDegrees(Math.PI_6);
        dExpected = 30.0;
        assertEquals(dExpected, dActual, 0.00000001);
    }
    
    @Test
    void map()
    {
        double x, y, x0, x1, y0, y1;
        
        x  = 1.0;
        x0 = 0.0;
        x1 = 10.0;
        y0 = 0.0;
        y1 = 100.0;
        
        y = Math.map(x, x0, x1, y0, y1);
        assertEquals(y, 10.0, 0.001);
        
        x  = -1.0;
        x0 = 0.0;
        x1 = 10.0;
        y0 = 0.0;
        y1 = 100.0;
        
        y = Math.map(x, x0, x1, y0, y1);
        assertEquals(y, -10.0, 0.001);
        
        x  = 1.0;
        x0 = 0.0;
        x1 = 10.0;
        y0 = 0.0;
        y1 = 1.0;
        
        y = Math.map(x, x0, x1, y0, y1);
        assertEquals(y, 0.1, 0.001);
    }
    
    @Test
    void getFormatNumbers()
    {
        double[] numbers;
        IPairI   format;
        
        numbers = new double[] {0.0, 1.0, 0.5};
        format  = Math.getFormatNumbers(numbers);
        assertEquals(format.a(), 1);
        assertEquals(format.b(), 1);
        
        numbers = new double[] {0.0, 1.0, 0.5, 10, 100, 1000};
        format  = Math.getFormatNumbers(numbers);
        assertEquals(format.a(), 4);
        assertEquals(format.b(), 1);
        
        numbers = new double[] {0, 1, 0, 10, 100, 1000};
        format  = Math.getFormatNumbers(numbers);
        assertEquals(format.a(), 4);
        assertEquals(format.b(), 0);
        
        numbers = new double[] {0.0, 1.0, 0.5, 0.1234, 0, 1, 0, 10, 100, 1000};
        format  = Math.getFormatNumbers(numbers);
        assertEquals(format.a(), 4);
        assertEquals(format.b(), 4);
        
        numbers = new double[] {0.0, 1.0, 0.5, 0.1234};
        format  = Math.getFormatNumbers(numbers);
        assertEquals(format.a(), 1);
        assertEquals(format.b(), 4);
    }
    
    @Test
    void format()
    {
        double[] numbers;
        String[] results;
        IPairI   format;
        String   result;
        
        numbers = new double[] {0.0, 1.0, 0.5, 0.1234, 0.1, 1.21, 0.321, 10, 100, 1000};
        results = new String[] {"   0.0   ", "   1.0   ", "   0.5   ", "   0.1234", "   0.1   ", "   1.21  ", "   0.321 ", "  10.0   ", " 100.0   ", "1000.0   "};
        format  = Math.getFormatNumbers(numbers);
        
        for (int i = 0, n = numbers.length; i < n; i++)
        {
            result = Math.format(numbers[i], format);
            assertEquals(result, results[i]);
        }
        
        numbers = new double[] {0.0, 1.0, 0.5, 0.1234, 0.1, 1.21, 0.321, 10, 100, 1000};
        results = new String[] {"   0.0", "   1.0", "   0.5", "   0.1", "   0.1", "   1.2", "   0.3", "  10.0", " 100.0", "1000.0"};
        format  = Math.getFormatNumbers(numbers);
        
        for (int i = 0, n = numbers.length; i < n; i++)
        {
            result = Math.format(numbers[i], format.a(), 1);
            assertEquals(result, results[i]);
        }
        
        numbers = new double[] {0.123, 1.123, 0.123, 10.123, 100.123, 1000.123};
        results = new String[] {"    0", "    1", "    0", "   10", "  100", " 1000"};
        
        for (int i = 0, n = numbers.length; i < n; i++)
        {
            result = Math.format(numbers[i], 5, 0);
            assertEquals(result, results[i]);
        }
    }
    
    @Test
    void round()
    {
        long   iRounded;
        double dRounded;
        double number = 1.123456789;
        
        iRounded = Math.round(number);
        assertEquals(iRounded, 1);
        
        dRounded = Math.round(number, 0);
        assertEquals(dRounded, 1.0);
        
        dRounded = Math.round(number, 1);
        assertEquals(dRounded, 1.1);
        
        dRounded = Math.round(number, 2);
        assertEquals(dRounded, 1.12);
    }
    
    @Test
    void clamp()
    {
        int    iNum, iResult, iMin, iMax;
        long   lNum, lResult, lMin, lMax;
        float  fNum, fResult, fMin, fMax;
        double dNum, dResult, dMin, dMax;
        
        iNum    = 5;
        iMin    = 1;
        iMax    = 10;
        iResult = Math.clamp(iNum, iMin, iMax);
        assertEquals(iResult, iNum);
        assertTrue(iResult >= iMin);
        assertTrue(iResult <= iMax);
        
        iNum    = -1;
        iMin    = 1;
        iMax    = 10;
        iResult = Math.clamp(iNum, iMin, iMax);
        assertEquals(iResult, iMin);
        assertTrue(iResult >= iMin);
        assertTrue(iResult <= iMax);
        
        iNum    = 11;
        iMin    = 1;
        iMax    = 10;
        iResult = Math.clamp(iNum, iMin, iMax);
        assertEquals(iResult, iMax);
        assertTrue(iResult >= iMin);
        assertTrue(iResult <= iMax);
        
        iNum    = 5;
        iMin    = 0;
        iMax    = 10;
        iResult = Math.clamp(iNum, iMax);
        assertEquals(iResult, iNum);
        assertTrue(iResult >= iMin);
        assertTrue(iResult <= iMax);
        
        iNum    = -1;
        iMin    = 0;
        iMax    = 10;
        iResult = Math.clamp(iNum, iMax);
        assertEquals(iResult, iMin);
        assertTrue(iResult >= iMin);
        assertTrue(iResult <= iMax);
        
        iNum    = 11;
        iMin    = 0;
        iMax    = 10;
        iResult = Math.clamp(iNum, iMax);
        assertEquals(iResult, iMax);
        assertTrue(iResult >= iMin);
        assertTrue(iResult <= iMax);
        
        lNum    = 5;
        lMin    = 1;
        lMax    = 10;
        lResult = Math.clamp(lNum, lMin, lMax);
        assertEquals(lResult, lNum);
        assertTrue(lResult >= lMin);
        assertTrue(lResult <= lMax);
        
        lNum    = -1;
        lMin    = 1;
        lMax    = 10;
        lResult = Math.clamp(lNum, lMin, lMax);
        assertEquals(lResult, lMin);
        assertTrue(lResult >= lMin);
        assertTrue(lResult <= lMax);
        
        lNum    = 11;
        lMin    = 1;
        lMax    = 10;
        lResult = Math.clamp(lNum, lMin, lMax);
        assertEquals(lResult, lMax);
        assertTrue(lResult >= lMin);
        assertTrue(lResult <= lMax);
        
        lNum    = 5;
        lMin    = 0;
        lMax    = 10;
        lResult = Math.clamp(lNum, lMax);
        assertEquals(lResult, lNum);
        assertTrue(lResult >= lMin);
        assertTrue(lResult <= lMax);
        
        lNum    = -1;
        lMin    = 0;
        lMax    = 10;
        lResult = Math.clamp(lNum, lMax);
        assertEquals(lResult, lMin);
        assertTrue(lResult >= lMin);
        assertTrue(lResult <= lMax);
        
        lNum    = 11;
        lMin    = 0;
        lMax    = 10;
        lResult = Math.clamp(lNum, lMax);
        assertEquals(lResult, lMax);
        assertTrue(lResult >= lMin);
        assertTrue(lResult <= lMax);
        
        fNum    = 5;
        fMin    = 1;
        fMax    = 10;
        fResult = Math.clamp(fNum, fMin, fMax);
        assertEquals(fResult, fNum);
        assertTrue(fResult >= fMin);
        assertTrue(fResult <= fMax);
        
        fNum    = -1;
        fMin    = 1;
        fMax    = 10;
        fResult = Math.clamp(fNum, fMin, fMax);
        assertEquals(fResult, fMin);
        assertTrue(fResult >= fMin);
        assertTrue(fResult <= fMax);
        
        fNum    = 11;
        fMin    = 1;
        fMax    = 10;
        fResult = Math.clamp(fNum, fMin, fMax);
        assertEquals(fResult, fMax);
        assertTrue(fResult >= fMin);
        assertTrue(fResult <= fMax);
        
        fNum    = 5;
        fMin    = 0;
        fMax    = 10;
        fResult = Math.clamp(fNum, fMax);
        assertEquals(fResult, fNum);
        assertTrue(fResult >= fMin);
        assertTrue(fResult <= fMax);
        
        fNum    = -1;
        fMin    = 0;
        fMax    = 10;
        fResult = Math.clamp(fNum, fMax);
        assertEquals(fResult, fMin);
        assertTrue(fResult >= fMin);
        assertTrue(fResult <= fMax);
        
        fNum    = 11;
        fMin    = 0;
        fMax    = 10;
        fResult = Math.clamp(fNum, fMax);
        assertEquals(fResult, fMax);
        assertTrue(fResult >= fMin);
        assertTrue(fResult <= fMax);
        
        dNum    = 5;
        dMin    = 1;
        dMax    = 10;
        dResult = Math.clamp(dNum, dMin, dMax);
        assertEquals(dResult, dNum);
        assertTrue(dResult >= dMin);
        assertTrue(dResult <= dMax);
        
        dNum    = -1;
        dMin    = 1;
        dMax    = 10;
        dResult = Math.clamp(dNum, dMin, dMax);
        assertEquals(dResult, dMin);
        assertTrue(dResult >= dMin);
        assertTrue(dResult <= dMax);
        
        dNum    = 11;
        dMin    = 1;
        dMax    = 10;
        dResult = Math.clamp(dNum, dMin, dMax);
        assertEquals(dResult, dMax);
        assertTrue(dResult >= dMin);
        assertTrue(dResult <= dMax);
        
        dNum    = 5;
        dMin    = 0;
        dMax    = 10;
        dResult = Math.clamp(dNum, dMax);
        assertEquals(dResult, dNum);
        assertTrue(dResult >= dMin);
        assertTrue(dResult <= dMax);
        
        dNum    = -1;
        dMin    = 0;
        dMax    = 10;
        dResult = Math.clamp(dNum, dMax);
        assertEquals(dResult, dMin);
        assertTrue(dResult >= dMin);
        assertTrue(dResult <= dMax);
        
        dNum    = 11;
        dMin    = 0;
        dMax    = 10;
        dResult = Math.clamp(dNum, dMax);
        assertEquals(dResult, dMax);
        assertTrue(dResult >= dMin);
        assertTrue(dResult <= dMax);
    }
    
    @Test
    void getDecimal()
    {
        double number, decimal, actual;
        
        number  = 0.12345;
        actual  = 0.12345;
        decimal = Math.getDecimal(number);
        assertEquals(decimal, actual, 0.000001);
        
        number  = 123.456;
        actual  = 0.456;
        decimal = Math.getDecimal(number);
        assertEquals(decimal, actual, 0.000001);
    }
    
    @Test
    void isEven()
    {
        boolean result;
        int     iNum;
        double  dNum;
        
        iNum   = 23423;
        result = Math.isEven(iNum);
        assertFalse(result);
        
        iNum   = 4098;
        result = Math.isEven(iNum);
        assertTrue(result);
        
        dNum   = 23423;
        result = Math.isEven(dNum);
        assertFalse(result);
        
        dNum   = 4098;
        result = Math.isEven(dNum);
        assertTrue(result);
    }
    
    @Test
    void isOdd()
    {
        boolean result;
        int     iNum;
        double  dNum;
        
        iNum   = 23423;
        result = Math.isOdd(iNum);
        assertTrue(result);
        
        iNum   = 4098;
        result = Math.isOdd(iNum);
        assertFalse(result);
        
        dNum   = 23423;
        result = Math.isOdd(dNum);
        assertTrue(result);
        
        dNum   = 4098;
        result = Math.isOdd(dNum);
        assertFalse(result);
    }
    
    @Test
    void min()
    {
        int[]    iData;
        int      iMin, iExp;
        long[]   lData;
        long     lMin, lExp;
        float[]  fData;
        float    fMin, fExp;
        double[] dData;
        double   dMin, dExp;
        
        iData = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        iExp  = 0;
        iMin  = Math.min(iData);
        assertEquals(iExp, iMin);
        
        lData = new long[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        lExp  = 0;
        lMin  = Math.min(lData);
        assertEquals(lExp, lMin);
        
        fData = new float[] {234.4523F, 0.23423F, 0.34123F, 0.135453F, 0.12564323F};
        fExp  = 0.12564323F;
        fMin  = Math.min(fData);
        assertEquals(fExp, fMin);
        
        dData = new double[] {234.4523, 0.23423, 0.34123, 0.135453, 0.12564323};
        dExp  = 0.12564323;
        dMin  = Math.min(dData);
        assertEquals(dExp, dMin);
    }
    
    @Test
    void max()
    {
        int[] iData = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int   iExp  = 9;
        int   iMax  = Math.max(iData);
        assertEquals(iExp, iMax);
        
        long[] lData = new long[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        long   lExp  = 9;
        long   lMax  = Math.max(lData);
        assertEquals(lExp, lMax);
        
        float[] fData = new float[] {234.4523F, 0.23423F, 0.34123F, 0.135453F, 0.12564323F};
        float   fExp  = 234.4523F;
        float   fMax  = Math.max(fData);
        assertEquals(fExp, fMax);
        
        double[] dData = new double[] {234.4523, 0.23423, 0.34123, 0.135453, 0.12564323};
        double   dExp  = 234.4523;
        double   dMax  = Math.max(dData);
        assertEquals(dExp, dMax);
    }
    
    @Test
    void sum()
    {
        int[] iData = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int   iExp  = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9;
        int   iSum  = Math.sum(iData);
        assertEquals(iExp, iSum);
        
        long[] lData = new long[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        long   lExp  = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9;
        long   lSum  = Math.sum(lData);
        assertEquals(lExp, lSum);
        
        float[] fData = new float[] {234.4523F, 0.23423F, 0.34123F, 0.135453F, 0.12564323F};
        float   fExp  = 234.4523F + 0.23423F + 0.34123F + 0.135453F + 0.12564323F;
        float   fSum  = Math.sum(fData);
        assertEquals(fExp, fSum);
        
        double[] dData = new double[] {234.4523, 0.23423, 0.34123, 0.135453, 0.12564323};
        double   dExp  = 234.4523 + 0.23423 + 0.34123 + 0.135453 + 0.12564323;
        double   dSum  = Math.sum(dData);
        assertEquals(dExp, dSum);
    }
    
    @Test
    void mean()
    {
        int[] iData = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        float iExp  = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9;
        float iMean = Math.mean(iData);
        assertEquals(iExp / iData.length, iMean);
        
        long[] lData = new long[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        double lExp  = 1 + 2 + 3 + 4 + 5 + 6 + 7 + 8 + 9;
        double lMean = Math.mean(lData);
        assertEquals(lExp / lData.length, lMean);
        
        float[] fData = new float[] {234.4523F, 0.23423F, 0.34123F, 0.135453F, 0.12564323F};
        float   fExp  = 234.4523F + 0.23423F + 0.34123F + 0.135453F + 0.12564323F;
        float   fMean = Math.mean(fData);
        assertEquals(fExp / fData.length, fMean);
        
        double[] dData = new double[] {234.4523, 0.23423, 0.34123, 0.135453, 0.12564323};
        double   dExp  = 234.4523 + 0.23423 + 0.34123 + 0.135453 + 0.12564323;
        double   dMean = Math.mean(dData);
        assertEquals(dExp / dData.length, dMean);
    }
    
    @Test
    void stdDev()
    {
        double delta = 0.0000001;
        
        int[] iData   = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        float iExp    = 2.8722813F;
        float iStdDev = Math.stdDev(iData);
        assertEquals(iExp, iStdDev, delta);
        
        long[] lData   = new long[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        double lExp    = 2.872281323269;
        double lStdDev = Math.stdDev(lData);
        assertEquals(lExp, lStdDev, delta);
        
        float[] fData   = new float[] {234.4523F, 0.23423F, 0.34123F, 0.135453F, 0.12564323F};
        float   fExp    = 93.6972969F;
        float   fStdDev = Math.stdDev(fData);
        assertEquals(fExp, fStdDev, delta);
        
        double[] dData   = new double[] {234.4523, 0.23423, 0.34123, 0.135453, 0.12564323};
        double   dExp    = 93.697296905973;
        double   dStdDev = Math.stdDev(dData);
        assertEquals(dExp, dStdDev, delta);
    }
    
    @Test
    void lerp()
    {
        double x;
        
        int    iA, iB, iExp, iAct;
        long   lA, lB, lExp, lAct;
        float  fA, fB, fExp, fAct;
        double dA, dB, dExp, dAct;
        
        x    = -0.1;
        iA   = 0;
        iB   = 10;
        iExp = iA;
        iAct = Math.lerp(iA, iB, x);
        assertEquals(iExp, iAct);
        
        x    = 1.1;
        iA   = 0;
        iB   = 10;
        iExp = iB;
        iAct = Math.lerp(iA, iB, x);
        assertEquals(iExp, iAct);
        
        x    = 0.5;
        iA   = 0;
        iB   = 10;
        iExp = 5;
        iAct = Math.lerp(iA, iB, x);
        assertEquals(iExp, iAct);
        
        x    = -0.1;
        lA   = 100_000;
        lB   = 1_000_000;
        lExp = lA;
        lAct = Math.lerp(lA, lB, x);
        assertEquals(lExp, lAct);
        
        x    = 1.1;
        lA   = 100_000;
        lB   = 1_000_000;
        lExp = lB;
        lAct = Math.lerp(lA, lB, x);
        assertEquals(lExp, lAct);
        
        x    = 0.5;
        lA   = 100_000;
        lB   = 1_000_000;
        lExp = 550_000;
        lAct = Math.lerp(lA, lB, x);
        assertEquals(lExp, lAct);
        
        x    = -0.1;
        fA   = 32F;
        fB   = 64F;
        fExp = fA;
        fAct = Math.lerp(fA, fB, x);
        assertEquals(fExp, fAct);
        
        x    = 1.1;
        fA   = 32F;
        fB   = 64F;
        fExp = fB;
        fAct = Math.lerp(fA, fB, x);
        assertEquals(fExp, fAct);
        
        x    = 0.5;
        fA   = 32F;
        fB   = 64F;
        fExp = 48F;
        fAct = Math.lerp(fA, fB, x);
        assertEquals(fExp, fAct);
        
        x    = -0.1;
        dA   = 0.125;
        dB   = 0.875;
        dExp = dA;
        dAct = Math.lerp(dA, dB, x);
        assertEquals(dExp, dAct);
        
        x    = 1.1;
        dA   = 0.125;
        dB   = 0.875;
        dExp = dB;
        dAct = Math.lerp(dA, dB, x);
        assertEquals(dExp, dAct);
        
        x    = 0.5;
        dA   = 0.125;
        dB   = 0.875;
        dExp = 0.5;
        dAct = Math.lerp(dA, dB, x);
        assertEquals(dExp, dAct);
    }
    
    @Test
    void smoothstep()
    {
        double x;
        
        int    iA, iB, iExp, iAct;
        long   lA, lB, lExp, lAct;
        float  fA, fB, fExp, fAct;
        double dA, dB, dExp, dAct;
        
        x    = -0.1;
        iA   = 0;
        iB   = 10;
        iExp = iA;
        iAct = Math.smoothstep(iA, iB, x);
        assertEquals(iExp, iAct);
        
        x    = 1.1;
        iA   = 0;
        iB   = 10;
        iExp = iB;
        iAct = Math.smoothstep(iA, iB, x);
        assertEquals(iExp, iAct);
        
        x    = 0.75;
        iA   = 0;
        iB   = 10;
        iExp = 8;
        iAct = Math.smoothstep(iA, iB, x);
        assertEquals(iExp, iAct);
        
        x    = -0.1;
        lA   = 100_000;
        lB   = 1_000_000;
        lExp = lA;
        lAct = Math.smoothstep(lA, lB, x);
        assertEquals(lExp, lAct);
        
        x    = 1.1;
        lA   = 100_000;
        lB   = 1_000_000;
        lExp = lB;
        lAct = Math.smoothstep(lA, lB, x);
        assertEquals(lExp, lAct);
        
        x    = 0.75;
        lA   = 100_000;
        lB   = 1_000_000;
        lExp = 859_375;
        lAct = Math.smoothstep(lA, lB, x);
        assertEquals(lExp, lAct);
        
        x    = -0.1;
        fA   = 32F;
        fB   = 64F;
        fExp = fA;
        fAct = Math.smoothstep(fA, fB, x);
        assertEquals(fExp, fAct);
        
        x    = 1.1;
        fA   = 32F;
        fB   = 64F;
        fExp = fB;
        fAct = Math.smoothstep(fA, fB, x);
        assertEquals(fExp, fAct);
        
        x    = 0.75;
        fA   = 32F;
        fB   = 64F;
        fExp = 59F;
        fAct = Math.smoothstep(fA, fB, x);
        assertEquals(fExp, fAct);
        
        x    = -0.1;
        dA   = 0.125;
        dB   = 0.875;
        dExp = dA;
        dAct = Math.smoothstep(dA, dB, x);
        assertEquals(dExp, dAct);
        
        x    = 1.1;
        dA   = 0.125;
        dB   = 0.875;
        dExp = dB;
        dAct = Math.smoothstep(dA, dB, x);
        assertEquals(dExp, dAct);
        
        x    = 0.75;
        dA   = 0.125;
        dB   = 0.875;
        dExp = 0.7578125;
        dAct = Math.smoothstep(dA, dB, x);
        assertEquals(dExp, dAct);
    }
    
    @Test
    void smootherstep()
    {
        double x;
        
        int    iA, iB, iExp, iAct;
        long   lA, lB, lExp, lAct;
        float  fA, fB, fExp, fAct;
        double dA, dB, dExp, dAct;
        
        x    = -0.1;
        iA   = 0;
        iB   = 10;
        iExp = iA;
        iAct = Math.smootherstep(iA, iB, x);
        assertEquals(iExp, iAct);
        
        x    = 1.1;
        iA   = 0;
        iB   = 10;
        iExp = iB;
        iAct = Math.smootherstep(iA, iB, x);
        assertEquals(iExp, iAct);
        
        x    = 0.75;
        iA   = 0;
        iB   = 10;
        iExp = 8;
        iAct = Math.smootherstep(iA, iB, x);
        assertEquals(iExp, iAct);
        
        x    = -0.1;
        lA   = 100_000;
        lB   = 1_000_000;
        lExp = lA;
        lAct = Math.smootherstep(lA, lB, x);
        assertEquals(lExp, lAct);
        
        x    = 1.1;
        lA   = 100_000;
        lB   = 1_000_000;
        lExp = lB;
        lAct = Math.smootherstep(lA, lB, x);
        assertEquals(lExp, lAct);
        
        x    = 0.75;
        lA   = 100_000;
        lB   = 1_000_000;
        lExp = 906_835;
        lAct = Math.smootherstep(lA, lB, x);
        assertEquals(lExp, lAct);
        
        x    = -0.1;
        fA   = 32F;
        fB   = 64F;
        fExp = fA;
        fAct = Math.smootherstep(fA, fB, x);
        assertEquals(fExp, fAct);
        
        x    = 1.1;
        fA   = 32F;
        fB   = 64F;
        fExp = fB;
        fAct = Math.smootherstep(fA, fB, x);
        assertEquals(fExp, fAct);
        
        x    = 0.75;
        fA   = 32F;
        fB   = 64F;
        fExp = 60.6875F;
        fAct = Math.smootherstep(fA, fB, x);
        assertEquals(fExp, fAct);
        
        x    = -0.1;
        dA   = 0.125;
        dB   = 0.875;
        dExp = dA;
        dAct = Math.smootherstep(dA, dB, x);
        assertEquals(dExp, dAct);
        
        x    = 1.1;
        dA   = 0.125;
        dB   = 0.875;
        dExp = dB;
        dAct = Math.smootherstep(dA, dB, x);
        assertEquals(dExp, dAct);
        
        x    = 0.75;
        dA   = 0.125;
        dB   = 0.875;
        dExp = 0.79736328125;
        dAct = Math.smootherstep(dA, dB, x);
        assertEquals(dExp, dAct);
    }
    
    @Test
    void fastFloor()
    {
        double[] numbers = new double[] {10.234543, 0.3425, 6.42345, -4.34523, -3654.2342};
        int[]    floors  = new int[] {10, 0, 6, -5, -3655};
        
        for (int i = 0, n = numbers.length; i < n; i++)
        {
            assertEquals(floors[i], Math.fastFloor(numbers[i]));
        }
    }
    
    @Test
    void fastCeil()
    {
        double[] numbers = new double[] {10.234543, 0.3425, 6.42345, -4.34523, -3654.2342};
        int[]    floors  = new int[] {11, 1, 7, -4, -3654};
        
        for (int i = 0, n = numbers.length; i < n; i++)
        {
            assertEquals(floors[i], Math.fastCeil(numbers[i]));
        }
    }
}

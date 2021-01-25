package rutils.group;

import org.junit.jupiter.api.Test;
import rutils.StringUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupTest
{
    @SuppressWarnings("ConstantConditions")
    @Test
    void pair()
    {
        Pair<String, Boolean> sb = new Pair<>("Test", true);
        assertEquals(String.class, sb.getA().getClass());
        assertEquals("Test", sb.getA());
        assertEquals(Boolean.class, sb.getB().getClass());
        assertEquals(true, sb.getB());
        assertEquals("[Test, true]", StringUtil.toString(sb.toArray()));
        
        
        Pair.D dd = new Pair.D(0.0, 42.0);
        assertEquals(-1, dd.compareTo(new Pair<>(0.0, 43.0)));
        assertEquals(-1, dd.compareTo(new Pair<>(1.0, 42.0)));
        assertEquals(1, dd.compareTo(new Pair<>(0.0, 41.0)));
        assertEquals(1, dd.compareTo(new Pair<>(-1.0, 42.0)));
        assertEquals(0, dd.compareTo(new Pair<>(0.0, 42.0)));
        
        
        Pair.I pairI = new Pair.I(Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertEquals(Integer.MIN_VALUE, pairI.a());
        assertEquals(Integer.MAX_VALUE, pairI.b());
        assertEquals("Pair.I{" + Integer.MIN_VALUE + ", " + Integer.MAX_VALUE + "}", pairI.toString());
        
        Pair.L pairL = new Pair.L(Long.MIN_VALUE, Long.MAX_VALUE);
        assertEquals(Long.MIN_VALUE, pairL.a());
        assertEquals(Long.MAX_VALUE, pairL.b());
        assertEquals("Pair.L{" + Long.MIN_VALUE + ", " + Long.MAX_VALUE + "}", pairL.toString());
        
        Pair.F pairF = new Pair.F(Float.MIN_VALUE, Float.MAX_VALUE);
        assertEquals(Float.MIN_VALUE, pairF.a());
        assertEquals(Float.MAX_VALUE, pairF.b());
        assertEquals("Pair.F{" + Float.MIN_VALUE + ", " + Float.MAX_VALUE + "}", pairF.toString());
        
        Pair.D pairD = new Pair.D(Double.MIN_VALUE, Double.MAX_VALUE);
        assertEquals(Double.MIN_VALUE, pairD.a());
        assertEquals(Double.MAX_VALUE, pairD.b());
        assertEquals("Pair.D{" + Double.MIN_VALUE + ", " + Double.MAX_VALUE + "}", pairD.toString());
        
        Pair.S pairS = new Pair.S("String1", "String2");
        assertEquals("String1", pairS.a());
        assertEquals("String2", pairS.b());
        assertEquals("Pair.S{'String1', 'String2'}", pairS.toString());
    }
    
    @SuppressWarnings("ConstantConditions")
    @Test
    void triple()
    {
        Triple<String, Boolean, Double> sb = new Triple<>("Test", true, 1.0);
        assertEquals(String.class, sb.getA().getClass());
        assertEquals("Test", sb.getA());
        assertEquals(Boolean.class, sb.getB().getClass());
        assertEquals(true, sb.getB());
        assertEquals(Double.class, sb.getC().getClass());
        assertEquals(1.0, sb.getC());
        assertEquals("[Test, true, 1.0]", StringUtil.toString(sb.toArray()));
        
        
        Triple.D dd = new Triple.D(0.0, 42.0, 0.234);
        assertEquals(-1, dd.compareTo(new Triple<>(1.0, 42.0, 0.234)));
        assertEquals(-1, dd.compareTo(new Triple<>(0.0, 43.0, 0.234)));
        assertEquals(-1, dd.compareTo(new Triple<>(0.0, 42.0, 0.334)));
        assertEquals(1, dd.compareTo(new Triple<>(-1.0, 42.0, 0.234)));
        assertEquals(1, dd.compareTo(new Triple<>(0.0, 41.0, 0.234)));
        assertEquals(1, dd.compareTo(new Triple<>(0.0, 42.0, 0.134)));
        assertEquals(0, dd.compareTo(new Triple<>(0.0, 42.0, 0.234)));
        
        
        Triple.I pairI = new Triple.I(Integer.MIN_VALUE, 0, Integer.MAX_VALUE);
        assertEquals(Integer.MIN_VALUE, pairI.a());
        assertEquals(0, pairI.b());
        assertEquals(Integer.MAX_VALUE, pairI.c());
        assertEquals("Triple.I{" + Integer.MIN_VALUE + ", " + 0 + ", " + Integer.MAX_VALUE + "}", pairI.toString());
        
        Triple.L pairL = new Triple.L(Long.MIN_VALUE, 0, Long.MAX_VALUE);
        assertEquals(Long.MIN_VALUE, pairL.a());
        assertEquals(0, pairL.b());
        assertEquals(Long.MAX_VALUE, pairL.c());
        assertEquals("Triple.L{" + Long.MIN_VALUE + ", " + 0 + ", " + Long.MAX_VALUE + "}", pairL.toString());
        
        Triple.F pairF = new Triple.F(Float.MIN_VALUE, 0, Float.MAX_VALUE);
        assertEquals(Float.MIN_VALUE, pairF.a());
        assertEquals(0, pairF.b());
        assertEquals(Float.MAX_VALUE, pairF.c());
        assertEquals("Triple.F{" + Float.MIN_VALUE + ", " + 0.0 + ", " + Float.MAX_VALUE + "}", pairF.toString());
        
        Triple.D pairD = new Triple.D(Double.MIN_VALUE, 0, Double.MAX_VALUE);
        assertEquals(Double.MIN_VALUE, pairD.a());
        assertEquals(0, pairD.b());
        assertEquals(Double.MAX_VALUE, pairD.c());
        assertEquals("Triple.D{" + Double.MIN_VALUE + ", " + 0.0 + ", " + Double.MAX_VALUE + "}", pairD.toString());
        
        Triple.S pairS = new Triple.S("String1", "String2", "String3");
        assertEquals("String1", pairS.a());
        assertEquals("String2", pairS.b());
        assertEquals("String3", pairS.c());
        assertEquals("Triple.S{'String1', 'String2', 'String3'}", pairS.toString());
    }
}

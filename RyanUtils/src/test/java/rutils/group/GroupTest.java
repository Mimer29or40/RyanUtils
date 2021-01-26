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
        assertEquals(Boolean.class, sb.getB().getClass());
        sb.a = "New String";
        sb.b = false;
        assertEquals("New String", sb.getA());
        assertEquals(false, sb.getB());
        assertEquals("[Test, true]", StringUtil.toString(((IGroup) sb).toArray()));
        
        
        PairD dd = new PairD(0.0, 42.0);
        assertEquals(-1, dd.compareTo(new Pair<>(0.0, 43.0)));
        assertEquals(-1, dd.compareTo(new Pair<>(1.0, 42.0)));
        assertEquals(1, dd.compareTo(new Pair<>(0.0, 41.0)));
        assertEquals(1, dd.compareTo(new Pair<>(-1.0, 42.0)));
        assertEquals(0, dd.compareTo(new Pair<>(0.0, 42.0)));
        
        
        IPairI pairI = new PairI(Integer.MIN_VALUE, Integer.MAX_VALUE);
        assertEquals(Integer.MIN_VALUE, pairI.a());
        assertEquals(Integer.MAX_VALUE, pairI.b());
        assertEquals("PairI{" + Integer.MIN_VALUE + ", " + Integer.MAX_VALUE + "}", pairI.toString());
        
        IPairL pairL = new PairL(Long.MIN_VALUE, Long.MAX_VALUE);
        assertEquals(Long.MIN_VALUE, pairL.a());
        assertEquals(Long.MAX_VALUE, pairL.b());
        assertEquals("PairL{" + Long.MIN_VALUE + ", " + Long.MAX_VALUE + "}", pairL.toString());
        
        IPairF pairF = new PairF(Float.MIN_VALUE, Float.MAX_VALUE);
        assertEquals(Float.MIN_VALUE, pairF.a());
        assertEquals(Float.MAX_VALUE, pairF.b());
        assertEquals("PairF{" + Float.MIN_VALUE + ", " + Float.MAX_VALUE + "}", pairF.toString());
        
        IPairD pairD = new PairD(Double.MIN_VALUE, Double.MAX_VALUE);
        assertEquals(Double.MIN_VALUE, pairD.a());
        assertEquals(Double.MAX_VALUE, pairD.b());
        assertEquals("PairD{" + Double.MIN_VALUE + ", " + Double.MAX_VALUE + "}", pairD.toString());
        
        IPairS pairS = new PairS("String1", "String2");
        assertEquals("String1", pairS.a());
        assertEquals("String2", pairS.b());
        assertEquals("PairS{String1, String2}", pairS.toString());
    }
    
    @SuppressWarnings("ConstantConditions")
    @Test
    void triple()
    {
        Triple<String, Boolean, Double> sbd = new Triple<>("Test", true, 1.0);
        assertEquals(String.class, sbd.getA().getClass());
        assertEquals(Boolean.class, sbd.getB().getClass());
        assertEquals(Double.class, sbd.getC().getClass());
        sbd.a = "New String";
        sbd.b = false;
        sbd.c = 0.8675307;
        assertEquals("New String", sbd.getA());
        assertEquals(false, sbd.getB());
        assertEquals(0.8675307, sbd.getC());
        assertEquals("[Test, true, 1.0]", StringUtil.toString(((IGroup) sbd).toArray()));
        
        
        TripleD ddd = new TripleD(0.0, 42.0, 0.234);
        assertEquals(-1, ddd.compareTo(new Triple<>(1.0, 42.0, 0.234)));
        assertEquals(-1, ddd.compareTo(new Triple<>(0.0, 43.0, 0.234)));
        assertEquals(-1, ddd.compareTo(new Triple<>(0.0, 42.0, 0.334)));
        assertEquals(1, ddd.compareTo(new Triple<>(-1.0, 42.0, 0.234)));
        assertEquals(1, ddd.compareTo(new Triple<>(0.0, 41.0, 0.234)));
        assertEquals(1, ddd.compareTo(new Triple<>(0.0, 42.0, 0.134)));
        assertEquals(0, ddd.compareTo(new Triple<>(0.0, 42.0, 0.234)));
        
        
        ITripleI tripleI = new TripleI(Integer.MIN_VALUE, 0, Integer.MAX_VALUE);
        assertEquals(Integer.MIN_VALUE, tripleI.a());
        assertEquals(0, tripleI.b());
        assertEquals(Integer.MAX_VALUE, tripleI.c());
        assertEquals("TripleI{" + Integer.MIN_VALUE + ", " + 0 + ", " + Integer.MAX_VALUE + "}", tripleI.toString());
        
        ITripleL tripleL = new TripleL(Long.MIN_VALUE, 0, Long.MAX_VALUE);
        assertEquals(Long.MIN_VALUE, tripleL.a());
        assertEquals(0, tripleL.b());
        assertEquals(Long.MAX_VALUE, tripleL.c());
        assertEquals("TripleL{" + Long.MIN_VALUE + ", " + 0 + ", " + Long.MAX_VALUE + "}", tripleL.toString());
        
        ITripleF tripleF = new TripleF(Float.MIN_VALUE, 0, Float.MAX_VALUE);
        assertEquals(Float.MIN_VALUE, tripleF.a());
        assertEquals(0, tripleF.b());
        assertEquals(Float.MAX_VALUE, tripleF.c());
        assertEquals("TripleF{" + Float.MIN_VALUE + ", " + 0.0 + ", " + Float.MAX_VALUE + "}", tripleF.toString());
        
        ITripleD tripleD = new TripleD(Double.MIN_VALUE, 0, Double.MAX_VALUE);
        assertEquals(Double.MIN_VALUE, tripleD.a());
        assertEquals(0, tripleD.b());
        assertEquals(Double.MAX_VALUE, tripleD.c());
        assertEquals("TripleD{" + Double.MIN_VALUE + ", " + 0.0 + ", " + Double.MAX_VALUE + "}", tripleD.toString());
        
        ITripleS tripleS = new TripleS("String1", "String2", "String3");
        assertEquals("String1", tripleS.a());
        assertEquals("String2", tripleS.b());
        assertEquals("String3", tripleS.c());
        assertEquals("TripleS{String1, String2, String3}", tripleS.toString());
    }
    
    @SuppressWarnings({"ConstantConditions", "SpellCheckingInspection"})
    @Test
    void quad()
    {
        Quad<String, Boolean, Double, Integer> sbdi = new Quad<>("Test", true, 1.0, 69);
        assertEquals(String.class, sbdi.getA().getClass());
        assertEquals(Boolean.class, sbdi.getB().getClass());
        assertEquals(Double.class, sbdi.getC().getClass());
        assertEquals(Integer.class, sbdi.getD().getClass());
        sbdi.a = "New String";
        sbdi.b = false;
        sbdi.c = 0.8675307;
        sbdi.d = 42069;
        assertEquals("New String", sbdi.getA());
        assertEquals(false, sbdi.getB());
        assertEquals(0.8675307, sbdi.getC());
        assertEquals(42069, sbdi.getD());
        assertEquals("[Test, true, 1.0, 69]", StringUtil.toString(((IGroup) sbdi).toArray()));
        
        
        Quad<Double, Double, Double, Double> dddd = new Quad<>(1.0, 42.0, 0.234, -234.0);
        assertEquals(-1, dddd.compareTo(new Quad<>(2.0, 42.0, 0.234, -234.0)));
        assertEquals(-1, dddd.compareTo(new Quad<>(1.0, 43.0, 0.234, -234.0)));
        assertEquals(-1, dddd.compareTo(new Quad<>(1.0, 42.0, 0.334, -234.0)));
        assertEquals(-1, dddd.compareTo(new Quad<>(1.0, 42.0, 0.234, -233.0)));
        assertEquals(1, dddd.compareTo(new Quad<>(0.0, 42.0, 0.234, -234.0)));
        assertEquals(1, dddd.compareTo(new Quad<>(1.0, 41.0, 0.234, -234.0)));
        assertEquals(1, dddd.compareTo(new Quad<>(1.0, 42.0, 0.134, -234.0)));
        assertEquals(1, dddd.compareTo(new Quad<>(1.0, 42.0, 0.234, -235.0)));
        assertEquals(0, dddd.compareTo(new Quad<>(1.0, 42.0, 0.234, -234.0)));
        
        
        IQuadI quadI = new QuadI(Integer.MIN_VALUE, 0, -1, Integer.MAX_VALUE);
        assertEquals(Integer.MIN_VALUE, quadI.a());
        assertEquals(0, quadI.b());
        assertEquals(-1, quadI.c());
        assertEquals(Integer.MAX_VALUE, quadI.d());
        assertEquals("QuadI{" + Integer.MIN_VALUE + ", " + 0 + ", " + -1 + ", " + Integer.MAX_VALUE + "}", quadI.toString());
        
        IQuadL quadL = new QuadL(Long.MIN_VALUE, 0L, -1L, Long.MAX_VALUE);
        assertEquals(Long.MIN_VALUE, quadL.a());
        assertEquals(0, quadL.b());
        assertEquals(-1, quadL.c());
        assertEquals(Long.MAX_VALUE, quadL.d());
        assertEquals("QuadL{" + Long.MIN_VALUE + ", " + 0 + ", " + -1 + ", " + Long.MAX_VALUE + "}", quadL.toString());
        
        IQuadF quadF = new QuadF(Float.MIN_VALUE, 0F, -1F, Float.MAX_VALUE);
        assertEquals(Float.MIN_VALUE, quadF.a());
        assertEquals(0, quadF.b());
        assertEquals(-1, quadF.c());
        assertEquals(Float.MAX_VALUE, quadF.d());
        assertEquals("QuadF{" + Float.MIN_VALUE + ", " + 0.0 + ", " + -1.0 + ", " + Float.MAX_VALUE + "}", quadF.toString());
        
        IQuadD quadD = new QuadD(Double.MIN_VALUE, 0.0, -1.0, Double.MAX_VALUE);
        assertEquals(Double.MIN_VALUE, quadD.a());
        assertEquals(0, quadD.b());
        assertEquals(-1, quadD.c());
        assertEquals(Double.MAX_VALUE, quadD.d());
        assertEquals("QuadD{" + Double.MIN_VALUE + ", " + 0.0 + ", " + -1.0 + ", " + Double.MAX_VALUE + "}", quadD.toString());
        
        IQuadS quadS = new QuadS("String1", "String2", "String3", "String4");
        assertEquals("String1", quadS.a());
        assertEquals("String2", quadS.b());
        assertEquals("String3", quadS.c());
        assertEquals("String4", quadS.d());
        assertEquals("QuadS{String1, String2, String3, String4}", quadS.toString());
    }
}

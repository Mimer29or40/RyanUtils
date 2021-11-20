package rutils.group;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import rutils.StringUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupTest
{
    @Nested
    class Pair
    {
        @SuppressWarnings("ConstantConditions")
        @Test
        void Type()
        {
            rutils.group.Pair<String, Boolean> pair = new rutils.group.Pair<>("Test", true);
            assertEquals(String.class, pair.getA().getClass());
            assertEquals(Boolean.class, pair.getB().getClass());
            pair.a = "New String";
            pair.b = false;
            assertEquals("New String", pair.getA());
            assertEquals(false, pair.getB());
            assertEquals("[New String, false]", StringUtil.toString(((IGroup) pair).toArray()));
        }
        
        @Nested
        class PairI
        {
            @Test
            void value()
            {
                IPairI pair = new rutils.group.PairI(java.lang.Integer.MIN_VALUE, java.lang.Integer.MAX_VALUE);
                assertEquals(java.lang.Integer.MIN_VALUE, pair.a());
                assertEquals(java.lang.Integer.MAX_VALUE, pair.b());
                assertEquals("PairI{" + java.lang.Integer.MIN_VALUE + ", " + java.lang.Integer.MAX_VALUE + "}", pair.toString());
            }
            
            @Test
            void comparison()
            {
                int a = 0;
                int b = 42;
                
                rutils.group.PairI pair = new rutils.group.PairI(a, b);
                assertEquals(a, pair.a());
                assertEquals(b, pair.b());
                assertEquals(-1, pair.compareTo(new rutils.group.Pair<>(a, b + 1)));
                assertEquals(-1, pair.compareTo(new rutils.group.Pair<>(a + 1, b)));
                assertEquals(1, pair.compareTo(new rutils.group.Pair<>(a, b - 1)));
                assertEquals(1, pair.compareTo(new rutils.group.Pair<>(a - 1, b)));
                assertEquals(0, pair.compareTo(new rutils.group.Pair<>(a, b)));
            }
        }
        
        @Nested
        class PairL
        {
            @Test
            void value()
            {
                IPairL pair = new rutils.group.PairL(java.lang.Long.MIN_VALUE, java.lang.Long.MAX_VALUE);
                assertEquals(java.lang.Long.MIN_VALUE, pair.a());
                assertEquals(java.lang.Long.MAX_VALUE, pair.b());
                assertEquals("PairL{" + java.lang.Long.MIN_VALUE + ", " + java.lang.Long.MAX_VALUE + "}", pair.toString());
            }
            
            @Test
            void comparison()
            {
                long a = 0L;
                long b = 42L;
                
                rutils.group.PairL pair = new rutils.group.PairL(a, b);
                assertEquals(a, pair.a());
                assertEquals(b, pair.b());
                assertEquals(-1, pair.compareTo(new rutils.group.Pair<>(a, b + 1L)));
                assertEquals(-1, pair.compareTo(new rutils.group.Pair<>(a + 1L, b)));
                assertEquals(1, pair.compareTo(new rutils.group.Pair<>(a, b - 1L)));
                assertEquals(1, pair.compareTo(new rutils.group.Pair<>(a - 1L, b)));
                assertEquals(0, pair.compareTo(new rutils.group.Pair<>(a, b)));
            }
        }
        
        @Nested
        class PairF
        {
            @Test
            void value()
            {
                IPairF pair = new rutils.group.PairF(java.lang.Float.MIN_VALUE, java.lang.Float.MAX_VALUE);
                assertEquals(java.lang.Float.MIN_VALUE, pair.a());
                assertEquals(java.lang.Float.MAX_VALUE, pair.b());
                assertEquals("PairF{" + java.lang.Float.MIN_VALUE + ", " + java.lang.Float.MAX_VALUE + "}", pair.toString());
            }
            
            @Test
            void comparison()
            {
                float a = 0.0F;
                float b = 42.0F;
                
                rutils.group.PairF pair = new rutils.group.PairF(a, b);
                assertEquals(a, pair.a());
                assertEquals(b, pair.b());
                assertEquals(-1, pair.compareTo(new rutils.group.Pair<>(a, b + 1.0F)));
                assertEquals(-1, pair.compareTo(new rutils.group.Pair<>(a + 1.0F, b)));
                assertEquals(1, pair.compareTo(new rutils.group.Pair<>(a, b - 1.0F)));
                assertEquals(1, pair.compareTo(new rutils.group.Pair<>(a - 1.0F, b)));
                assertEquals(0, pair.compareTo(new rutils.group.Pair<>(a, b)));
            }
        }
        
        @Nested
        class PairD
        {
            @Test
            void value()
            {
                IPairD pair = new rutils.group.PairD(java.lang.Double.MIN_VALUE, java.lang.Double.MAX_VALUE);
                assertEquals(java.lang.Double.MIN_VALUE, pair.a());
                assertEquals(java.lang.Double.MAX_VALUE, pair.b());
                assertEquals("PairD{" + java.lang.Double.MIN_VALUE + ", " + java.lang.Double.MAX_VALUE + "}", pair.toString());
            }
            
            @Test
            void comparison()
            {
                double a = 0.0;
                double b = 42.0;
                
                rutils.group.PairD pair = new rutils.group.PairD(a, b);
                assertEquals(a, pair.a());
                assertEquals(b, pair.b());
                assertEquals(-1, pair.compareTo(new rutils.group.Pair<>(a, b + 1.0)));
                assertEquals(-1, pair.compareTo(new rutils.group.Pair<>(a + 1.0, b)));
                assertEquals(1, pair.compareTo(new rutils.group.Pair<>(a, b - 1.0)));
                assertEquals(1, pair.compareTo(new rutils.group.Pair<>(a - 1.0, b)));
                assertEquals(0, pair.compareTo(new rutils.group.Pair<>(a, b)));
            }
        }
        
        @Nested
        class PairS
        {
            @Test
            void value()
            {
                IPairS pair = new rutils.group.PairS("String1", "String2");
                assertEquals("String1", pair.a());
                assertEquals("String2", pair.b());
                assertEquals("PairS{String1, String2}", pair.toString());
            }
        }
    }
    
    @Nested
    class Triple
    {
        @SuppressWarnings("ConstantConditions")
        @Test
        void Type()
        {
            rutils.group.Triple<String, Boolean, Double> triple = new rutils.group.Triple<>("Test", true, 1.0);
            assertEquals(String.class, triple.getA().getClass());
            assertEquals(Boolean.class, triple.getB().getClass());
            assertEquals(Double.class, triple.getC().getClass());
            triple.a = "New String";
            triple.b = false;
            triple.c = 0.8675307;
            assertEquals("New String", triple.getA());
            assertEquals(false, triple.getB());
            assertEquals(0.8675307, triple.getC());
            assertEquals("[New String, false, 0.8675307]", StringUtil.toString(((IGroup) triple).toArray()));
        }
        
        @Nested
        class TripleI
        {
            @Test
            void value()
            {
                ITripleI triple = new rutils.group.TripleI(Integer.MIN_VALUE, 0, Integer.MAX_VALUE);
                assertEquals(Integer.MIN_VALUE, triple.a());
                assertEquals(0, triple.b());
                assertEquals(Integer.MAX_VALUE, triple.c());
                assertEquals("TripleI{" + Integer.MIN_VALUE + ", " + 0 + ", " + Integer.MAX_VALUE + "}", triple.toString());
            }
            
            @Test
            void comparison()
            {
                int a = 0;
                int b = 42;
                int c = 56342;
                
                rutils.group.TripleI triple = new rutils.group.TripleI(a, b, c);
                assertEquals(-1, triple.compareTo(new rutils.group.Triple<>(a + 1, b, c)));
                assertEquals(-1, triple.compareTo(new rutils.group.Triple<>(a, b + 1, c)));
                assertEquals(-1, triple.compareTo(new rutils.group.Triple<>(a, b, c + 1)));
                assertEquals(1, triple.compareTo(new rutils.group.Triple<>(a - 1, b, c)));
                assertEquals(1, triple.compareTo(new rutils.group.Triple<>(a, b - 1, c)));
                assertEquals(1, triple.compareTo(new rutils.group.Triple<>(a, b, c - 1)));
                assertEquals(0, triple.compareTo(new rutils.group.Triple<>(a, b, c)));
            }
        }
        
        @Nested
        class TripleL
        {
            @Test
            void value()
            {
                ITripleL triple = new rutils.group.TripleL(Long.MIN_VALUE, 0, Long.MAX_VALUE);
                assertEquals(Long.MIN_VALUE, triple.a());
                assertEquals(0, triple.b());
                assertEquals(Long.MAX_VALUE, triple.c());
                assertEquals("TripleL{" + Long.MIN_VALUE + ", " + 0 + ", " + Long.MAX_VALUE + "}", triple.toString());
            }
            
            @Test
            void comparison()
            {
                long a = 0L;
                long b = 42L;
                long c = 56342L;
                
                rutils.group.TripleL triple = new rutils.group.TripleL(a, b, c);
                assertEquals(-1, triple.compareTo(new rutils.group.Triple<>(a + 1L, b, c)));
                assertEquals(-1, triple.compareTo(new rutils.group.Triple<>(a, b + 1L, c)));
                assertEquals(-1, triple.compareTo(new rutils.group.Triple<>(a, b, c + 1L)));
                assertEquals(1, triple.compareTo(new rutils.group.Triple<>(a - 1L, b, c)));
                assertEquals(1, triple.compareTo(new rutils.group.Triple<>(a, b - 1L, c)));
                assertEquals(1, triple.compareTo(new rutils.group.Triple<>(a, b, c - 1L)));
                assertEquals(0, triple.compareTo(new rutils.group.Triple<>(a, b, c)));
            }
        }
        
        @Nested
        class TripleF
        {
            @Test
            void value()
            {
                ITripleF triple = new rutils.group.TripleF(Float.MIN_VALUE, 0, Float.MAX_VALUE);
                assertEquals(Float.MIN_VALUE, triple.a());
                assertEquals(0, triple.b());
                assertEquals(Float.MAX_VALUE, triple.c());
                assertEquals("TripleF{" + Float.MIN_VALUE + ", " + 0.0F + ", " + Float.MAX_VALUE + "}", triple.toString());
            }
            
            @Test
            void comparison()
            {
                float a = 0.0F;
                float b = 42.0F;
                float c = 0.234F;
                
                rutils.group.TripleF triple = new rutils.group.TripleF(a, b, c);
                assertEquals(-1, triple.compareTo(new rutils.group.Triple<>(a + 1.0F, b, c)));
                assertEquals(-1, triple.compareTo(new rutils.group.Triple<>(a, b + 1.0F, c)));
                assertEquals(-1, triple.compareTo(new rutils.group.Triple<>(a, b, c + 1.0F)));
                assertEquals(1, triple.compareTo(new rutils.group.Triple<>(a - 1.0F, b, c)));
                assertEquals(1, triple.compareTo(new rutils.group.Triple<>(a, b - 1.0F, c)));
                assertEquals(1, triple.compareTo(new rutils.group.Triple<>(a, b, c - 1.0F)));
                assertEquals(0, triple.compareTo(new rutils.group.Triple<>(a, b, c)));
            }
        }
        
        @Nested
        class TripleD
        {
            @Test
            void value()
            {
                ITripleD triple = new rutils.group.TripleD(Double.MIN_VALUE, 0, Double.MAX_VALUE);
                assertEquals(Double.MIN_VALUE, triple.a());
                assertEquals(0, triple.b());
                assertEquals(Double.MAX_VALUE, triple.c());
                assertEquals("TripleD{" + Double.MIN_VALUE + ", " + 0.0 + ", " + Double.MAX_VALUE + "}", triple.toString());
            }
            
            @Test
            void comparison()
            {
                double a = 0.0;
                double b = 42.0;
                double c = 0.234;
                
                rutils.group.TripleD triple = new rutils.group.TripleD(a, b, c);
                assertEquals(-1, triple.compareTo(new rutils.group.Triple<>(a + 1.0, b, c)));
                assertEquals(-1, triple.compareTo(new rutils.group.Triple<>(a, b + 1.0, c)));
                assertEquals(-1, triple.compareTo(new rutils.group.Triple<>(a, b, c + 1.0)));
                assertEquals(1, triple.compareTo(new rutils.group.Triple<>(a - 1.0, b, c)));
                assertEquals(1, triple.compareTo(new rutils.group.Triple<>(a, b - 1.0, c)));
                assertEquals(1, triple.compareTo(new rutils.group.Triple<>(a, b, c - 1.0)));
                assertEquals(0, triple.compareTo(new rutils.group.Triple<>(a, b, c)));
            }
        }
        
        @Nested
        class TripleS
        {
            @Test
            void value()
            {
                ITripleS triple = new rutils.group.TripleS("String1", "String2", "String3");
                assertEquals("String1", triple.a());
                assertEquals("String2", triple.b());
                assertEquals("String3", triple.c());
                assertEquals("TripleS{String1, String2, String3}", triple.toString());
            }
        }
    }
    
    @Nested
    class Quad
    {
        @SuppressWarnings("ConstantConditions")
        @Test
        void Type()
        {
            rutils.group.Quad<String, Boolean, Double, Integer> quad = new rutils.group.Quad<>("Test", true, 1.0, 69);
            assertEquals(String.class, quad.getA().getClass());
            assertEquals(Boolean.class, quad.getB().getClass());
            assertEquals(Double.class, quad.getC().getClass());
            assertEquals(Integer.class, quad.getD().getClass());
            quad.a = "New String";
            quad.b = false;
            quad.c = 0.8675307;
            quad.d = 42069;
            assertEquals("New String", quad.getA());
            assertEquals(false, quad.getB());
            assertEquals(0.8675307, quad.getC());
            assertEquals(42069, quad.getD());
            assertEquals("[New String, false, 0.8675307, 42069]", StringUtil.toString(((IGroup) quad).toArray()));
        }
        
        @Nested
        class TripleI
        {
            @Test
            void value()
            {
                IQuadI quad = new rutils.group.QuadI(Integer.MIN_VALUE, 0, -1, Integer.MAX_VALUE);
                assertEquals(Integer.MIN_VALUE, quad.a());
                assertEquals(0, quad.b());
                assertEquals(-1, quad.c());
                assertEquals(Integer.MAX_VALUE, quad.d());
                assertEquals("QuadI{" + Integer.MIN_VALUE + ", " + 0 + ", " + -1 + ", " + Integer.MAX_VALUE + "}", quad.toString());
            }
            
            @Test
            void comparison()
            {
                int a = 0;
                int b = 42;
                int c = 56342;
                int d = -234;
    
                QuadI quad = new rutils.group.QuadI(a, b, c, d);
                assertEquals(-1, quad.compareTo(new rutils.group.Quad<>(a + 1, b, c, d)));
                assertEquals(-1, quad.compareTo(new rutils.group.Quad<>(a, b + 1, c, d)));
                assertEquals(-1, quad.compareTo(new rutils.group.Quad<>(a, b, c + 1, d)));
                assertEquals(-1, quad.compareTo(new rutils.group.Quad<>(a, b, c, d + 1)));
                assertEquals(1, quad.compareTo(new rutils.group.Quad<>(a - 1, b, c, d)));
                assertEquals(1, quad.compareTo(new rutils.group.Quad<>(a, b - 1, c, d)));
                assertEquals(1, quad.compareTo(new rutils.group.Quad<>(a, b, c - 1, d)));
                assertEquals(1, quad.compareTo(new rutils.group.Quad<>(a, b, c, d - 1)));
                assertEquals(0, quad.compareTo(new rutils.group.Quad<>(a, b, c, d)));
            }
        }
        
        @Nested
        class TripleL
        {
            @Test
            void value()
            {
                IQuadL quad = new rutils.group.QuadL(Long.MIN_VALUE, 0L, -1L, Long.MAX_VALUE);
                assertEquals(Long.MIN_VALUE, quad.a());
                assertEquals(0, quad.b());
                assertEquals(-1, quad.c());
                assertEquals(Long.MAX_VALUE, quad.d());
                assertEquals("QuadL{" + Long.MIN_VALUE + ", " + 0 + ", " + -1 + ", " + Long.MAX_VALUE + "}", quad.toString());
            }
            
            @Test
            void comparison()
            {
                long a = 0L;
                long b = 42L;
                long c = 56342L;
                long d = -234L;
    
                QuadL quad = new rutils.group.QuadL(a, b, c, d);
                assertEquals(-1, quad.compareTo(new rutils.group.Quad<>(a + 1L, b, c, d)));
                assertEquals(-1, quad.compareTo(new rutils.group.Quad<>(a, b + 1L, c, d)));
                assertEquals(-1, quad.compareTo(new rutils.group.Quad<>(a, b, c + 1L, d)));
                assertEquals(-1, quad.compareTo(new rutils.group.Quad<>(a, b, c, d + 1L)));
                assertEquals(1, quad.compareTo(new rutils.group.Quad<>(a - 1L, b, c, d)));
                assertEquals(1, quad.compareTo(new rutils.group.Quad<>(a, b - 1L, c, d)));
                assertEquals(1, quad.compareTo(new rutils.group.Quad<>(a, b, c - 1L, d)));
                assertEquals(1, quad.compareTo(new rutils.group.Quad<>(a, b, c, d - 1L)));
                assertEquals(0, quad.compareTo(new rutils.group.Quad<>(a, b, c, d)));
            }
        }
        
        @Nested
        class TripleF
        {
            @Test
            void value()
            {
                IQuadF quad = new rutils.group.QuadF(Float.MIN_VALUE, 0F, -1F, Float.MAX_VALUE);
                assertEquals(Float.MIN_VALUE, quad.a());
                assertEquals(0, quad.b());
                assertEquals(-1, quad.c());
                assertEquals(Float.MAX_VALUE, quad.d());
                assertEquals("QuadF{" + Float.MIN_VALUE + ", " + 0.0 + ", " + -1.0 + ", " + Float.MAX_VALUE + "}", quad.toString());
            }
            
            @Test
            void comparison()
            {
                float a = 0.0F;
                float b = 42.0F;
                float c = 0.234F;
                float d = -234.0F;
    
                QuadF quad = new rutils.group.QuadF(a, b, c, d);
                assertEquals(-1, quad.compareTo(new rutils.group.Quad<>(a + 1.0F, b, c, d)));
                assertEquals(-1, quad.compareTo(new rutils.group.Quad<>(a, b + 1.0F, c, d)));
                assertEquals(-1, quad.compareTo(new rutils.group.Quad<>(a, b, c + 1.0F, d)));
                assertEquals(-1, quad.compareTo(new rutils.group.Quad<>(a, b, c, d + 1.0F)));
                assertEquals(1, quad.compareTo(new rutils.group.Quad<>(a - 1.0F, b, c, d)));
                assertEquals(1, quad.compareTo(new rutils.group.Quad<>(a, b - 1.0F, c, d)));
                assertEquals(1, quad.compareTo(new rutils.group.Quad<>(a, b, c - 1.0F, d)));
                assertEquals(1, quad.compareTo(new rutils.group.Quad<>(a, b, c, d - 1.0F)));
                assertEquals(0, quad.compareTo(new rutils.group.Quad<>(a, b, c, d)));
            }
        }
        
        @Nested
        class TripleD
        {
            @Test
            void value()
            {
                IQuadD quad = new rutils.group.QuadD(Double.MIN_VALUE, 0.0, -1.0, Double.MAX_VALUE);
                assertEquals(Double.MIN_VALUE, quad.a());
                assertEquals(0, quad.b());
                assertEquals(-1, quad.c());
                assertEquals(Double.MAX_VALUE, quad.d());
                assertEquals("QuadD{" + Double.MIN_VALUE + ", " + 0.0 + ", " + -1.0 + ", " + Double.MAX_VALUE + "}", quad.toString());
            }
            
            @Test
            void comparison()
            {
                double a = 0.0;
                double b = 42.0;
                double c = 0.234;
                double d = -234.0;
    
                QuadD quad = new rutils.group.QuadD(a, b, c, d);
                assertEquals(-1, quad.compareTo(new rutils.group.Quad<>(a + 1.0, b, c, d)));
                assertEquals(-1, quad.compareTo(new rutils.group.Quad<>(a, b + 1.0, c, d)));
                assertEquals(-1, quad.compareTo(new rutils.group.Quad<>(a, b, c + 1.0, d)));
                assertEquals(-1, quad.compareTo(new rutils.group.Quad<>(a, b, c, d + 1.0)));
                assertEquals(1, quad.compareTo(new rutils.group.Quad<>(a - 1.0, b, c, d)));
                assertEquals(1, quad.compareTo(new rutils.group.Quad<>(a, b - 1.0, c, d)));
                assertEquals(1, quad.compareTo(new rutils.group.Quad<>(a, b, c - 1.0, d)));
                assertEquals(1, quad.compareTo(new rutils.group.Quad<>(a, b, c, d - 1.0)));
                assertEquals(0, quad.compareTo(new rutils.group.Quad<>(a, b, c, d)));
            }
        }
        
        @Nested
        class TripleS
        {
            @Test
            void value()
            {
                IQuadS quad = new rutils.group.QuadS("String1", "String2", "String3", "String4");
                assertEquals("String1", quad.a());
                assertEquals("String2", quad.b());
                assertEquals("String3", quad.c());
                assertEquals("String4", quad.d());
                assertEquals("QuadS{String1, String2, String3, String4}", quad.toString());
            }
        }
    }
}

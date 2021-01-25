package rutils.group;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Generic group of three objects. The objects are not Read-Only.
 */
public class Triple<A, B, C> extends Group implements ITriple<A, B, C>, Comparable<ITriple<A, B, C>>, Serializable
{
    public A a;
    public B b;
    public C c;
    
    /**
     * Creates a new triple with three objects.
     *
     * @param a The first object.
     * @param b The second object.
     * @param c The third object.
     */
    public Triple(@Nullable A a, @Nullable B b, @Nullable C c)
    {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof ITriple)) return false;
        ITriple<?, ?, ?> triple = (ITriple<?, ?, ?>) o;
        return Objects.equals(this.a, triple.getA()) && Objects.equals(this.b, triple.getB()) && Objects.equals(this.c, triple.getC());
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(this.a, this.b, this.c);
    }
    
    @Override
    public String toString()
    {
        return getClass().getSimpleName() + '{' + this.a + ", " + this.b + ", " + this.c + '}';
    }
    
    @Override
    protected int size()
    {
        return 3;
    }
    
    @Override
    protected Object get(int index)
    {
        return switch (index)
                {
                    case 0 -> this.a;
                    case 1 -> this.b;
                    case 2 -> this.c;
                    default -> throw new IndexOutOfBoundsException();
                };
    }
    
    /**
     * @return The first object in the triple.
     */
    @Override
    public @Nullable A getA()
    {
        return this.a;
    }
    
    /**
     * @return The second object in the triple.
     */
    @Override
    public @Nullable B getB()
    {
        return this.b;
    }
    
    /**
     * @return The third object in the triple.
     */
    @Override
    public @Nullable C getC()
    {
        return this.c;
    }
    
    @Override
    public int compareTo(ITriple<A, B, C> o)
    {
        int comparison;
        
        if (getA() != o.getA())
        {
            if (getA() == null)
            {
                return -1;
            }
            if (o.getA() == null)
            {
                return 1;
            }
            @SuppressWarnings("unchecked") // assume this can be done; if not throw CCE as per Javadoc
            final Comparable<Object> comparable = (Comparable<Object>) getA();
            if ((comparison = comparable.compareTo(o.getA())) != 0) return comparison;
        }
        
        if (getB() != o.getB())
        {
            if (getB() == null)
            {
                return -1;
            }
            if (o.getB() == null)
            {
                return 1;
            }
            @SuppressWarnings("unchecked") // assume this can be done; if not throw CCE as per Javadoc
            final Comparable<Object> comparable = (Comparable<Object>) getB();
            if ((comparison = comparable.compareTo(o.getB())) != 0) return comparison;
        }
        
        if (getC() != o.getC())
        {
            if (getC() == null)
            {
                return -1;
            }
            if (o.getC() == null)
            {
                return 1;
            }
            @SuppressWarnings("unchecked") // assume this can be done; if not throw CCE as per Javadoc
            final Comparable<Object> comparable = (Comparable<Object>) getC();
            if ((comparison = comparable.compareTo(o.getC())) != 0) return comparison;
        }
        
        return 0;
    }
    
    /**
     * A simple {@code int} triple.
     */
    public static class I extends Triple<Integer, Integer, Integer> implements ITriple.I
    {
        /**
         * Creates a new triple with three ints.
         *
         * @param a The first int.
         * @param b The second int.
         * @param c The third int.
         */
        public I(int a, int b, int c)
        {
            super(a, b, c);
        }
    
        @Override
        public String toString()
        {
            return "Triple." + getClass().getSimpleName() + '{' + this.a + ", " + this.b + ", " + this.c + '}';
        }
        
        /**
         * @return The first int value.
         */
        @Override
        public int a()
        {
            return this.a;
        }
        
        /**
         * @return The second int value.
         */
        @Override
        public int b()
        {
            return this.b;
        }
        
        /**
         * @return The third int value.
         */
        @Override
        public int c()
        {
            return this.c;
        }
    }
    
    /**
     * A simple {@code long} triple.
     */
    public static class L extends Triple<Long, Long, Long> implements ITriple.L
    {
        /**
         * Creates a new triple with three longs.
         *
         * @param a The first long.
         * @param b The second long.
         * @param c The third long.
         */
        public L(long a, long b, long c)
        {
            super(a, b, c);
        }
    
        @Override
        public String toString()
        {
            return "Triple." + getClass().getSimpleName() + '{' + this.a + ", " + this.b + ", " + this.c + '}';
        }
        
        /**
         * @return The first long value.
         */
        @Override
        public long a()
        {
            return this.a;
        }
        
        /**
         * @return The second long value.
         */
        @Override
        public long b()
        {
            return this.b;
        }
        
        /**
         * @return The third long value.
         */
        @Override
        public long c()
        {
            return this.c;
        }
    }
    
    /**
     * A simple {@code float} triple.
     */
    public static class F extends Triple<Float, Float, Float> implements ITriple.F
    {
        /**
         * Creates a new triple with three floats.
         *
         * @param a The first float.
         * @param b The second float.
         * @param c The third float.
         */
        public F(float a, float b, float c)
        {
            super(a, b, c);
        }
    
        @Override
        public String toString()
        {
            return "Triple." + getClass().getSimpleName() + '{' + this.a + ", " + this.b + ", " + this.c + '}';
        }
        
        /**
         * @return The first float value.
         */
        @Override
        public float a()
        {
            return this.a;
        }
        
        /**
         * @return The second float value.
         */
        @Override
        public float b()
        {
            return this.b;
        }
        
        /**
         * @return The third float value.
         */
        @Override
        public float c()
        {
            return this.c;
        }
    }
    
    /**
     * A simple {@code double} triple.
     */
    public static class D extends Triple<Double, Double, Double> implements ITriple.D
    {
        /**
         * Creates a new triple with three doubles.
         *
         * @param a The first double.
         * @param b The second double.
         * @param c The third double.
         */
        public D(double a, double b, double c)
        {
            super(a, b, c);
        }
    
        @Override
        public String toString()
        {
            return "Triple." + getClass().getSimpleName() + '{' + this.a + ", " + this.b + ", " + this.c + '}';
        }
        
        /**
         * @return The first double value.
         */
        @Override
        public double a()
        {
            return this.a;
        }
        
        /**
         * @return The second double value.
         */
        @Override
        public double b()
        {
            return this.b;
        }
        
        /**
         * @return The third double value.
         */
        @Override
        public double c()
        {
            return this.c;
        }
    }
    
    /**
     * A simple {@code String} triple.
     */
    public static class S extends Triple<String, String, String> implements ITriple.S
    {
        /**
         * Creates a new triple with three Strings.
         *
         * @param a The first String.
         * @param b The second String.
         * @param c The third String.
         */
        public S(@Nullable String a, @Nullable String b, @Nullable String c)
        {
            super(a, b, c);
        }
    
        @Override
        public String toString()
        {
            return "Triple." + getClass().getSimpleName() + '{' + '\'' + this.a + '\'' + ", " + '\'' + this.b + '\'' + ", " + '\'' + this.c + '\'' + '}';
        }
        
        /**
         * @return The first String value.
         */
        @Override
        public @Nullable String a()
        {
            return this.a;
        }
        
        /**
         * @return The second String value.
         */
        @Override
        public @Nullable String b()
        {
            return this.b;
        }
        
        /**
         * @return The third String value.
         */
        @Override
        public @Nullable String c()
        {
            return this.c;
        }
    }
}

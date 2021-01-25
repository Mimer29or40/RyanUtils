package rutils.group;

import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Generic group of two objects. The objects are not Read-Only.
 */
public class Pair<A, B> extends Group implements IPair<A, B>, Comparable<IPair<A, B>>, Serializable
{
    public A a;
    public B b;
    
    /**
     * Creates a new pair with two objects.
     *
     * @param a The first object.
     * @param b The second object.
     */
    public Pair(@Nullable A a, @Nullable B b)
    {
        this.a = a;
        this.b = b;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof IPair)) return false;
        IPair<?, ?> other = (IPair<?, ?>) o;
        return Objects.equals(this.a, other.getA()) && Objects.equals(this.a, other.getB());
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(this.a, this.b);
    }
    
    @Override
    public String toString()
    {
        return getClass().getSimpleName() + '{' + this.a + ", " + this.b + '}';
    }
    
    @Override
    protected int size()
    {
        return 2;
    }
    
    @Override
    protected Object get(int index)
    {
        return switch (index)
                {
                    case 0 -> this.a;
                    case 1 -> this.b;
                    default -> throw new IndexOutOfBoundsException();
                };
    }
    
    /**
     * @return The first object in the pair.
     */
    @Override
    public @Nullable A getA()
    {
        return this.a;
    }
    
    /**
     * @return The second object in the pair.
     */
    @Override
    public @Nullable B getB()
    {
        return this.b;
    }
    
    @Override
    public int compareTo(IPair<A, B> o)
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
        
        return 0;
    }
    
    /**
     * A simple {@code int} pair.
     */
    public static class I extends Pair<Integer, Integer> implements IPair.I
    {
        /**
         * Creates a new pair with two ints.
         *
         * @param a The first int.
         * @param b The second int.
         */
        public I(int a, int b)
        {
            super(a, b);
        }
        
        @Override
        public String toString()
        {
            return "Pair." + getClass().getSimpleName() + '{' + this.a + ", " + this.b + '}';
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
    }
    
    /**
     * A simple {@code long} pair.
     */
    public static class L extends Pair<Long, Long> implements IPair.L
    {
        /**
         * Creates a new pair with two long.
         *
         * @param a The first long.
         * @param b The second long.
         */
        public L(long a, long b)
        {
            super(a, b);
        }
        
        @Override
        public String toString()
        {
            return "Pair." + getClass().getSimpleName() + '{' + this.a + ", " + this.b + '}';
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
    }
    
    /**
     * A simple {@code float} pair.
     */
    public static class F extends Pair<Float, Float> implements IPair.F
    {
        /**
         * Creates a new pair with two floats.
         *
         * @param a The first float.
         * @param b The second float.
         */
        public F(float a, float b)
        {
            super(a, b);
        }
        
        @Override
        public String toString()
        {
            return "Pair." + getClass().getSimpleName() + '{' + this.a + ", " + this.b + '}';
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
    }
    
    /**
     * A simple {@code double} pair.
     */
    public static class D extends Pair<Double, Double> implements IPair.D
    {
        /**
         * Creates a new pair with two doubles.
         *
         * @param a The first double.
         * @param b The second double.
         */
        public D(double a, double b)
        {
            super(a, b);
        }
        
        @Override
        public String toString()
        {
            return "Pair." + getClass().getSimpleName() + '{' + this.a + ", " + this.b + '}';
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
    }
    
    /**
     * A simple {@code String} pair.
     */
    public static class S extends Pair<String, String> implements IPair.S
    {
        /**
         * Creates a new pair with two Strings.
         *
         * @param a The first String.
         * @param b The second String.
         */
        public S(@Nullable String a, @Nullable String b)
        {
            super(a, b);
        }
        
        @Override
        public String toString()
        {
            return "Pair." + getClass().getSimpleName() + '{' + '\'' + this.a + '\'' + ", " + '\'' + this.b + '\'' + '}';
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
    }
}

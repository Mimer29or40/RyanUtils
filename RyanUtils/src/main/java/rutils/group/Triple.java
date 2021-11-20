package rutils.group;

import org.jetbrains.annotations.NotNull;
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
        if (!(o instanceof ITriple<?, ?, ?> triple)) return false;
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
    public int compareTo(@NotNull ITriple<A, B, C> o)
    {
        int comparison;
        
        if (this.a != o.getA())
        {
            if (this.a == null)
            {
                return -1;
            }
            if (o.getA() == null)
            {
                return 1;
            }
            @SuppressWarnings("unchecked") // assume this can be done; if not throw CCE as per Javadoc
            final Comparable<Object> comparable = (Comparable<Object>) this.a;
            if ((comparison = comparable.compareTo(o.getA())) != 0) return comparison;
        }
        
        if (this.b != o.getB())
        {
            if (this.b == null)
            {
                return -1;
            }
            if (o.getB() == null)
            {
                return 1;
            }
            @SuppressWarnings("unchecked") // assume this can be done; if not throw CCE as per Javadoc
            final Comparable<Object> comparable = (Comparable<Object>) this.b;
            if ((comparison = comparable.compareTo(o.getB())) != 0) return comparison;
        }
        
        if (this.c != o.getC())
        {
            if (this.c == null)
            {
                return -1;
            }
            if (o.getC() == null)
            {
                return 1;
            }
            @SuppressWarnings("unchecked") // assume this can be done; if not throw CCE as per Javadoc
            final Comparable<Object> comparable = (Comparable<Object>) this.c;
            if ((comparison = comparable.compareTo(o.getC())) != 0) return comparison;
        }
        
        return 0;
    }
    
}

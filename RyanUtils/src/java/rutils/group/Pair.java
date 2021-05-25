package rutils.group;

import org.jetbrains.annotations.NotNull;
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
    public int compareTo(@NotNull IPair<A, B> o)
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
        
        return 0;
    }
    
}

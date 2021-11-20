package rutils.group;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Generic group of four objects. The objects are not Read-Only.
 */
public class Quad<A, B, C, D> extends Group implements IQuad<A, B, C, D>, Comparable<IQuad<A, B, C, D>>, Serializable
{
    public A a;
    public B b;
    public C c;
    public D d;
    
    /**
     * Creates a new quad with four objects.
     *
     * @param a The first object.
     * @param b The second object.
     * @param c The third object.
     * @param d The fourth object.
     */
    public Quad(@Nullable A a, @Nullable B b, @Nullable C c, @Nullable D d)
    {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof IQuad<?, ?, ?, ?> quad)) return false;
        return Objects.equals(this.a, quad.getA()) && Objects.equals(this.b, quad.getB()) && Objects.equals(this.c, quad.getC()) && Objects.equals(this.d, quad.getD());
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(this.a, this.b, this.c, this.d);
    }
    
    @Override
    public String toString()
    {
        return getClass().getSimpleName() + '{' + this.a + ", " + this.b + ", " + this.c + ", " + this.d + '}';
    }
    
    @Override
    protected int size()
    {
        return 4;
    }
    
    @Override
    protected Object get(int index)
    {
        return switch (index)
                {
                    case 0 -> this.a;
                    case 1 -> this.b;
                    case 2 -> this.c;
                    case 3 -> this.d;
                    default -> throw new IndexOutOfBoundsException();
                };
    }
    
    /**
     * @return The first object in the quad.
     */
    @Override
    public @Nullable A getA()
    {
        return this.a;
    }
    
    /**
     * @return The second object in the quad.
     */
    @Override
    public @Nullable B getB()
    {
        return this.b;
    }
    
    /**
     * @return The third object in the quad.
     */
    @Override
    public @Nullable C getC()
    {
        return this.c;
    }
    
    /**
     * @return The fourth object in the quad.
     */
    @Override
    public @Nullable D getD()
    {
        return this.d;
    }
    
    @Override
    public int compareTo(@NotNull rutils.group.IQuad<A, B, C, D> o)
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
        
        if (this.d != o.getD())
        {
            if (this.d == null)
            {
                return -1;
            }
            if (o.getD() == null)
            {
                return 1;
            }
            @SuppressWarnings("unchecked") // assume this can be done; if not throw CCE as per Javadoc
            final Comparable<Object> comparable = (Comparable<Object>) this.d;
            if ((comparison = comparable.compareTo(o.getD())) != 0) return comparison;
        }
        
        return 0;
    }
    
}

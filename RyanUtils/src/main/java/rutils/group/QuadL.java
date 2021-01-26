package rutils.group;

/**
 * A simple {@code long} quad.
 */
public class QuadL extends Quad<Long, Long, Long, Long> implements IQuadL
{
    /**
     * Creates a new quad with four longs.
     *
     * @param a The first long.
     * @param b The second long.
     * @param c The third long.
     * @param d The fourth long.
     */
    public QuadL(long a, long b, long c, long d)
    {
        super(a, b, c, d);
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
    
    /**
     * @return The fourth long value.
     */
    @Override
    public long d()
    {
        return this.d;
    }
}

package rutils.group;

/**
 * A simple {@code long} triple.
 */
public class TripleL extends Triple<Long, Long, Long> implements ITripleL
{
    /**
     * Creates a new triple with three longs.
     *
     * @param a The first long.
     * @param b The second long.
     * @param c The third long.
     */
    public TripleL(long a, long b, long c)
    {
        super(a, b, c);
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

package rutils.group;

/**
 * A simple {@code long} pair.
 */
public class PairL extends Pair<Long, Long> implements IPairL
{
    /**
     * Creates a new pair with two long.
     *
     * @param a The first long.
     * @param b The second long.
     */
    public PairL(long a, long b)
    {
        super(a, b);
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

package rutils.group;

/**
 * A simple {@code int} pair.
 */
public class PairI extends Pair<Integer, Integer> implements IPairI
{
    /**
     * Creates a new pair with two ints.
     *
     * @param a The first int.
     * @param b The second int.
     */
    public PairI(int a, int b)
    {
        super(a, b);
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

package rutils.group;

/**
 * A simple {@code int} triple.
 */
public class TripleI extends Triple<Integer, Integer, Integer> implements ITripleI
{
    /**
     * Creates a new triple with three ints.
     *
     * @param a The first int.
     * @param b The second int.
     * @param c The third int.
     */
    public TripleI(int a, int b, int c)
    {
        super(a, b, c);
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

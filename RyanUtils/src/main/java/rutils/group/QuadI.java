package rutils.group;

/**
 * A simple {@code int} quad.
 */
public class QuadI extends Quad<Integer, Integer, Integer, Integer> implements IQuadI
{
    /**
     * Creates a new quad with four ints.
     *
     * @param a The first int.
     * @param b The second int.
     * @param c The third int.
     * @param d The fourth int.
     */
    public QuadI(int a, int b, int c, int d)
    {
        super(a, b, c, d);
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
    
    /**
     * @return The fourth int value.
     */
    @Override
    public int d()
    {
        return this.d;
    }
}

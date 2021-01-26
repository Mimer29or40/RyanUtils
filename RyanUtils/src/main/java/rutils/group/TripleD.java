package rutils.group;

/**
 * A simple {@code double} triple.
 */
public class TripleD extends Triple<Double, Double, Double> implements ITripleD
{
    /**
     * Creates a new triple with three doubles.
     *
     * @param a The first double.
     * @param b The second double.
     * @param c The third double.
     */
    public TripleD(double a, double b, double c)
    {
        super(a, b, c);
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
    
    /**
     * @return The third double value.
     */
    @Override
    public double c()
    {
        return this.c;
    }
}

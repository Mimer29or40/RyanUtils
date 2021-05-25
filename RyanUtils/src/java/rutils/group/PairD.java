package rutils.group;

/**
 * A simple {@code double} pair.
 */
public class PairD extends Pair<Double, Double> implements IPairD
{
    /**
     * Creates a new pair with two doubles.
     *
     * @param a The first double.
     * @param b The second double.
     */
    public PairD(double a, double b)
    {
        super(a, b);
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
}

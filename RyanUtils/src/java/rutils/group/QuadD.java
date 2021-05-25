package rutils.group;

/**
 * A simple {@code double} quad.
 */
public class QuadD extends Quad<Double, Double, Double, Double> implements IQuadD
{
    /**
     * Creates a new quad with four doubles.
     *
     * @param a The first double.
     * @param b The second double.
     * @param c The third double.
     * @param d The fourth double.
     */
    public QuadD(double a, double b, double c, double d)
    {
        super(a, b, c, d);
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
    
    /**
     * @return The fourth double value.
     */
    @Override
    public double d()
    {
        return this.d;
    }
}

package rutils.group;

/**
 * A simple {@code float} quad.
 */
public class QuadF extends Quad<Float, Float, Float, Float> implements IQuadF
{
    /**
     * Creates a new quad with four floats.
     *
     * @param a The first float.
     * @param b The second float.
     * @param c The third float.
     * @param d The fourth float.
     */
    public QuadF(float a, float b, float c, float d)
    {
        super(a, b, c, d);
    }
    
    /**
     * @return The first float value.
     */
    @Override
    public float a()
    {
        return this.a;
    }
    
    /**
     * @return The second float value.
     */
    @Override
    public float b()
    {
        return this.b;
    }
    
    /**
     * @return The third float value.
     */
    @Override
    public float c()
    {
        return this.c;
    }
    
    /**
     * @return The fourth float value.
     */
    @Override
    public float d()
    {
        return this.d;
    }
}

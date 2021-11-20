package rutils.group;

/**
 * A simple {@code float} triple.
 */
public class TripleF extends Triple<Float, Float, Float> implements ITripleF
{
    /**
     * Creates a new triple with three floats.
     *
     * @param a The first float.
     * @param b The second float.
     * @param c The third float.
     */
    public TripleF(float a, float b, float c)
    {
        super(a, b, c);
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
}

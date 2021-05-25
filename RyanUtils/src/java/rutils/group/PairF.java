package rutils.group;

/**
 * A simple {@code float} pair.
 */
public class PairF extends Pair<Float, Float> implements IPairF
{
    /**
     * Creates a new pair with two floats.
     *
     * @param a The first float.
     * @param b The second float.
     */
    public PairF(float a, float b)
    {
        super(a, b);
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
}

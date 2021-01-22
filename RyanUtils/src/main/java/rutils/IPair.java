package rutils;


import java.util.Map;

/**
 * Interface to a read-only view of a Pair.
 */
@SuppressWarnings("unused")
public interface IPair<A, B> extends Map.Entry<A, B>
{
    /**
     * @return The first object in the pair.
     */
    A getA();
    
    /**
     * @return The second object in the pair.
     */
    B getB();
    
    /**
     * <p>Gets the key from this pair.</p>
     *
     * <p>This method implements the {@code Map.Entry} interface returning the
     * a element as the key.</p>
     *
     * @return the a element as the key, may be null
     */
    @Override
    default A getKey()
    {
        return getA();
    }
    
    /**
     * <p>Gets the value from this pair.</p>
     *
     * <p>This method implements the {@code Map.Entry} interface returning the
     * b element as the value.</p>
     *
     * @return the b element as the value, may be null
     */
    @Override
    default B getValue()
    {
        return getB();
    }
}

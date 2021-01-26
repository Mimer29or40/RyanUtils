package rutils.group;

/**
 * A simple {@code double} tuple.
 */
public interface ITripleD extends ITriple<Double, Double, Double>
{
    /**
     * @return The first double value.
     */
    double a();
    
    /**
     * @return The second double value.
     */
    double b();
    
    /**
     * @return The third double value.
     */
    double c();
}

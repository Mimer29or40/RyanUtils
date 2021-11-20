package rutils.group;

/**
 * A simple {@code double} tuple.
 */
public interface IQuadD extends IQuad<Double, Double, Double, Double>
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
    
    /**
     * @return The fourth double value.
     */
    double d();
}

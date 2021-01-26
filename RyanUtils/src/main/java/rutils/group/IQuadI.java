package rutils.group;

/**
 * A simple {@code int} tuple.
 */
public interface IQuadI extends IQuad<Integer, Integer, Integer, Integer>
{
    /**
     * @return The first int value.
     */
    int a();
    
    /**
     * @return The second int value.
     */
    int b();
    
    /**
     * @return The third int value.
     */
    int c();
    
    /**
     * @return The fourth int value.
     */
    int d();
}

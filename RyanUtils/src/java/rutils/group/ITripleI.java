package rutils.group;

/**
 * A simple {@code int} tuple.
 */
public interface ITripleI extends ITriple<Integer, Integer, Integer>
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
}

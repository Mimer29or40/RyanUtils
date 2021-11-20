package rutils.group;

/**
 * A simple {@code long} tuple.
 */
public interface ITripleL extends ITriple<Long, Long, Long>
{
    /**
     * @return The first long value.
     */
    long a();
    
    /**
     * @return The second long value.
     */
    long b();
    
    /**
     * @return The third long value.
     */
    long c();
}

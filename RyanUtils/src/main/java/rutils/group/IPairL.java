package rutils.group;

/**
 * A simple {@code long} pair.
 */
public interface IPairL extends IPair<Long, Long>
{
    /**
     * @return The first long value.
     */
    long a();
    
    /**
     * @return The second long value.
     */
    long b();
}

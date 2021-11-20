package rutils.group;


import org.jetbrains.annotations.Nullable;

/**
 * Interface to a read-only view of a Pair.
 */
public interface IPair<A, B> extends IGroup
{
    /**
     * @return The first object in the pair.
     */
    @Nullable A getA();
    
    /**
     * @return The second object in the pair.
     */
    @Nullable B getB();
    
}

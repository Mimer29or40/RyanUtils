package rutils.group;

import org.jetbrains.annotations.Nullable;

/**
 * Interface to a read-only view of a Triple.
 */
public interface ITriple<A, B, C> extends IGroup
{
    /**
     * @return The first object in the triple.
     */
    @Nullable A getA();
    
    /**
     * @return The second object in the triple.
     */
    @Nullable B getB();
    
    /**
     * @return The third object in the triple.
     */
    @Nullable C getC();
    
}

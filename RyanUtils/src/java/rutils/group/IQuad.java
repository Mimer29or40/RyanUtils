package rutils.group;

import org.jetbrains.annotations.Nullable;

/**
 * Interface to a read-only view of a Quad.
 */
public interface IQuad<A, B, C, D> extends IGroup
{
    /**
     * @return The first object in the quad.
     */
    @Nullable A getA();
    
    /**
     * @return The second object in the quad.
     */
    @Nullable B getB();
    
    /**
     * @return The third object in the quad.
     */
    @Nullable C getC();
    
    /**
     * @return The fourth object in the quad.
     */
    @Nullable D getD();
    
}

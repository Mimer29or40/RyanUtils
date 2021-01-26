package rutils.group;

import org.jetbrains.annotations.NotNull;

interface IGroup extends Iterable<Object>
{
    /**
     * Creates an array from the objects in the group.
     *
     * @return The array of objects in this group.
     */
    @NotNull
    Object[] toArray();
}

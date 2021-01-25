package rutils.group;

interface IGroup extends Iterable<Object>
{
    /**
     * Creates an array from the objects in the group.
     *
     * @return The array of objects in this group.
     */
    Object[] toArray();
}

package rutils.group;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A group is a fixed size array of elements.
 */
abstract class Group implements IGroup
{
    /**
     * @return The number of elements in this group.
     */
    protected abstract int size();
    
    /**
     * Gets the object at the specified index.
     * <p>
     * If the {@code index < 0 || index >= }{@link Group#size()}, then it will
     * throw an {@link IndexOutOfBoundsException}.
     *
     * @param index The index.
     * @return The object at the index.
     * @throws IndexOutOfBoundsException if this {@code index < 0 || index >= }{@link Group#size()}.
     */
    @Nullable
    protected abstract Object get(int index);
    
    /**
     * Creates an array from the objects in the group.
     *
     * @return The array of objects in this group.
     */
    @NotNull
    @Override
    public Object[] toArray()
    {
        int      n   = size();
        Object[] arr = new Object[n];
        for (int i = 0; i < n; i++) arr[i] = get(i);
        return arr;
    }
    
    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @NotNull
    @Override
    public Iterator<Object> iterator()
    {
        return new Itr();
    }
    
    private class Itr implements Iterator<Object>
    {
        /**
         * Index of element to be returned by subsequent call to next.
         */
        int cursor = 0;
        
        /**
         * Returns {@code true} if the iteration has more elements.
         * (In other words, returns {@code true} if {@link #next} would
         * return an element rather than throwing an exception.)
         *
         * @return {@code true} if the iteration has more elements
         */
        @Override
        public boolean hasNext()
        {
            return this.cursor != Group.this.size();
        }
        
        /**
         * Returns the next element in the iteration.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the iteration has no more elements
         */
        @Nullable
        @Override
        public Object next()
        {
            try
            {
                int    i    = this.cursor;
                Object next = Group.this.get(i);
                this.cursor = i + 1;
                return next;
            }
            catch (IndexOutOfBoundsException e)
            {
                throw new NoSuchElementException();
            }
        }
    }
}

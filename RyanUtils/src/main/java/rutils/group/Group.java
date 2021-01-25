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
    protected abstract int size();
    
    protected abstract Object get(int index);
    
    /**
     * Creates an array from the objects in the group.
     *
     * @return The array of objects in this group.
     */
    @Override
    public Object[] toArray()
    {
        int n = size();
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

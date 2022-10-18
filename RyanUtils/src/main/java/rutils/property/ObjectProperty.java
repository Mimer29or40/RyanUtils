package rutils.property;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ObjectProperty<T> extends AbstractProperty<T>
{
    protected T value;
    
    protected boolean changed;
    
    public ObjectProperty(T initial)
    {
        this.value   = initial;
        this.changed = false;
    }
    
    @Override
    @Nullable
    public T get()
    {
        return this.value;
    }
    
    @Override
    public boolean changed()
    {
        boolean changed = this.changed;
        this.changed = false;
        return changed;
    }
    
    @Override
    public void set(@Nullable T newValue)
    {
        if (checkObj(newValue))
        {
            this.changed = true;
            this.value = setObj(newValue);
        }
    }
    
    protected boolean checkObj(T newValue)
    {
        return !Objects.deepEquals(this.value, newValue);
    }
    
    protected T setObj(T newValue)
    {
        return this.value = newValue;
    }
}

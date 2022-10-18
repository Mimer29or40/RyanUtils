package rutils.property;

import org.jetbrains.annotations.Nullable;

public class NullableObjectProperty<T> extends ObjectProperty<T>
{
    public NullableObjectProperty(@Nullable T initial)
    {
        super(initial);
    }
    
    @Override
    @Nullable
    public T get()
    {
        return super.get();
    }
    
    
    @Override
    public void set(@Nullable T newValue)
    {
        super.set(newValue);
    }
}

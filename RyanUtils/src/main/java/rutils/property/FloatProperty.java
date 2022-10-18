package rutils.property;

import org.jetbrains.annotations.NotNull;

public class FloatProperty extends AbstractProperty<Float>
{
    protected float value;
    
    protected boolean changed;
    
    public FloatProperty(float initial)
    {
        this.value   = initial;
        this.changed = false;
    }
    
    @Override
    @NotNull
    public Float get()
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
    public void set(@NotNull Float newValue)
    {
        if (Float.compare(this.value, newValue) != 0)
        {
            this.changed = true;
            this.value   = newValue;
        }
    }
}

package rutils.property;

import org.jetbrains.annotations.NotNull;

public class IntProperty extends AbstractProperty<Integer>
{
    protected int value;
    
    protected boolean changed;
    
    public IntProperty(int initial)
    {
        this.value   = initial;
        this.changed = false;
    }
    
    @Override
    @NotNull
    public Integer get()
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
    public void set(@NotNull Integer newValue)
    {
        if (this.value != newValue)
        {
            this.changed = true;
            this.value   = newValue;
        }
    }
}

package rutils.property;

import org.jetbrains.annotations.NotNull;

public class LongProperty extends AbstractProperty<Long>
{
    protected long value;
    
    protected boolean changed;
    
    public LongProperty(long initial)
    {
        this.value   = initial;
        this.changed = false;
    }
    
    @Override
    @NotNull
    public Long get()
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
    public void set(@NotNull Long newValue)
    {
        if (this.value != newValue)
        {
            this.changed = true;
            this.value   = newValue;
        }
    }
}

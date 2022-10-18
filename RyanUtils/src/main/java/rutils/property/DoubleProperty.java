package rutils.property;

import org.jetbrains.annotations.NotNull;

public class DoubleProperty extends AbstractProperty<Double>
{
    protected double value;
    
    protected boolean changed;
    
    public DoubleProperty(double initial)
    {
        this.value   = initial;
        this.changed = false;
    }
    
    @Override
    @NotNull
    public Double get()
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
    public void set(@NotNull Double newValue)
    {
        if (Double.compare(this.value, newValue) != 0)
        {
            this.changed = true;
            this.value   = newValue;
        }
    }
}

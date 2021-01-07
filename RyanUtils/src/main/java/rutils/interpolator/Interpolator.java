package rutils.interpolator;

/**
 * Abstract class used to interpolate a data class between arbitrary two values.
 * <p>
 * To use this class, change the value of <code>target</code> and the current will be interpolated to that value
 *
 * @param <D> The data class for storing the value.
 */
public abstract class Interpolator<D>
{
    private boolean enabled = true;
    
    public D current;
    public D target;
    public D minValue;
    public D maxValue;
    
    protected D _current;
    protected D _target;
    
    protected D velocity;
    
    public double maxAcceleration = 100.0;
    public double maxVelocity     = 100.0;
    
    protected boolean updated = true;
    
    public Interpolator()
    {
        this.current  = newInstance();
        this.target   = newInstance();
        this._current = newInstance();
        this._target  = newInstance();
        this.velocity = newInstance();
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (!(obj instanceof Interpolator<?>)) return false;
        Interpolator<?> other = (Interpolator<?>) obj;
        return equals(this.current, other.current) && equals(this.target, other.target);
    }
    
    @Override
    public String toString()
    {
        return String.format("%s{%s, target=%s}", getClass().getSimpleName(), this.current, this.target);
    }
    
    /**
     * @return <code>true</code> if interpolation should happen
     */
    public boolean enabled()
    {
        return this.enabled;
    }
    
    /**
     * Sets if the interpolator should interpolated from the current value to the target
     *
     * @param enabled The new enabled state
     * @return This instance for call chaining
     */
    public Interpolator<D> enabled(boolean enabled)
    {
        this.enabled = enabled;
        return this;
    }
    
    /**
     * Toggles the enabled state
     *
     * @return This instance for call chaining
     */
    public Interpolator<D> toggleEnabled()
    {
        return enabled(!this.enabled);
    }
    
    /**
     * Enables interpolating
     *
     * @return This instance for call chaining
     */
    public Interpolator<D> enable()
    {
        return enabled(true);
    }
    
    /**
     * Disables interpolating
     *
     * @return This instance for call chaining
     */
    public Interpolator<D> disable()
    {
        return enabled(false);
    }
    
    /**
     * @return <code>true</code>> if the interpolator was updated during the last update.
     */
    public boolean updated()
    {
        return this.updated;
    }
    
    /**
     * @return A new instance of the data class
     */
    protected abstract D newInstance();
    
    /**
     * Sets the value of assigner to the object assignee.
     *
     * @param object The object to assign the values to.
     * @param value  The object to copy the values from.
     * @return object
     */
    protected abstract D assign(D object, D value);
    
    /**
     * Internal method to check if a value of type T is equal to the other object.
     *
     * @param value The value to check. This should be either get() or getTarget().
     * @param other The other value in the equality.
     * @return If the value is equal to the other.
     */
    protected abstract boolean equals(D value, Object other);
    
    /**
     * Internal methods to compare the two specified {@code D} values.
     *
     * @param a the first {@code D} value to compare
     * @param b the second {@code D} value to compare
     * @return the value {@code 0} if {@code a} is
     * numerically equal to {@code b}; a value less than
     * {@code 0} if {@code a} is numerically less than
     * {@code b}; and a value greater than {@code 0}
     * if {@code a} is numerically greater than
     * {@code b}.
     */
    protected abstract int compare(D a, D b);
    
    /**
     * @return The difference between the current value and the target difference.
     */
    public abstract D difference();
    
    /**
     * @return The current velocity that the mover it traveling at.
     */
    public abstract D velocity();
    
    /**
     * Sets the velocity of the mover to zero.
     */
    protected abstract void resetVelocity();
    
    /**
     * @return If the mover's value is at the target.
     */
    public boolean atTarget()
    {
        return equals(this.current, this.target);
    }
    
    /**
     * This method is called if the current value does not match the target value
     *
     * @param t  The total time since the engine has started in seconds.
     * @param dt The time since last frame in seconds.
     */
    protected abstract void updateCurrent(double t, double dt);
    
    /**
     * Method called once per frame to update the values depending on how much time has passed.
     *
     * @param t  The total time since the engine has started in seconds.
     * @param dt The time since last frame in seconds.
     */
    public void update(double t, double dt)
    {
        this.updated = false;
        
        if (!equals(this._current, this.current))
        {
            this.target = assign(this.target, this.current);
            
            this.updated = true;
        }
        
        if (!this.enabled)
        {
            if (!equals(this._target, this.target))
            {
                if (this.minValue != null && compare(this.minValue, this.target) > 0) this.target = assign(this.target, this.minValue);
                if (this.maxValue != null && compare(this.maxValue, this.target) < 0) this.target = assign(this.target, this.maxValue);
                
                this.current = assign(this.current, this.target);
                
                this.updated = true;
            }
            
            resetVelocity();
        }
        else if (!atTarget())
        {
            if (!equals(this._target, this.target))
            {
                if (this.minValue != null && compare(this.minValue, this.target) > 0) this.target = assign(this.target, this.minValue);
                if (this.maxValue != null && compare(this.maxValue, this.target) < 0) this.target = assign(this.target, this.maxValue);
            }
            
            updateCurrent(t, dt);
            
            this.updated = true;
        }
        
        this._current = assign(this._current, this.current);
        this._target  = assign(this._target, this.target);
    }
}

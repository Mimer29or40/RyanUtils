package rutils.interpolator;

/**
 * Class to allow for interpolation between its current scalar value to an arbitrary target scalar value.
 */
@SuppressWarnings("unused")
public class ScalarInterpolator extends Interpolator<Double>
{
    /**
     * @return A new instance of the data class
     */
    @Override
    protected Double newInstance()
    {
        return 0.0;
    }
    
    /**
     * Sets the value of assigner to the object assignee.
     *
     * @param object The object to assign the values to.
     * @param value  The object to copy the values from.
     * @return object
     */
    @Override
    protected Double assign(Double object, Double value)
    {
        return value;
    }
    
    /**
     * Internal method to check if a value of type T is equal to the other object.
     *
     * @param value The value to check. This should be either get() or getTarget().
     * @param other The other value in the equality.
     * @return If the value is equal to the other.
     */
    @Override
    protected boolean equals(Double value, Object other)
    {
        return (other instanceof Double) && Double.compare(value, (Double) other) == 0;
    }
    
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
    @Override
    protected int compare(Double a, Double b)
    {
        return Double.compare(a, b);
    }
    
    /**
     * @return The difference between the current value and the target difference.
     */
    @Override
    public Double difference()
    {
        return this.target - this.current;
    }
    
    /**
     * @return The current velocity that the mover it traveling at.
     */
    @Override
    public Double velocity()
    {
        return this.velocity;
    }
    
    /**
     * Sets the velocity of the mover to zero.
     */
    @Override
    protected void resetVelocity()
    {
        this.velocity = 0.0;
    }
    
    /**
     * This method is called if the current value does not match the target value
     *
     * @param t  The total time since the engine has started in seconds.
     * @param dt The time since last frame in seconds.
     */
    @Override
    protected void updateCurrent(double t, double dt)
    {
        double distanceToTarget    = difference();
        double maxStoppingDistance = this.velocity * this.velocity / (2.0 * this.maxAcceleration);
        
        double acceleration = this.velocity * distanceToTarget > 0.0 && maxStoppingDistance >= Math.abs(distanceToTarget) ? -this.maxAcceleration : this.maxAcceleration;
        
        if (distanceToTarget < 0.0) acceleration *= -1;
        
        this.velocity = Math.fma(dt, acceleration, this.velocity);
        if (Math.abs(this.velocity) > this.maxVelocity) this.velocity = this.velocity > 0 ? this.maxVelocity : -this.maxVelocity;
        
        double dCurrent = this.velocity * dt;
        if (this.velocity * distanceToTarget > 0.0 && Math.abs(dCurrent) > Math.abs(distanceToTarget))
        {
            resetVelocity();
            this.current = this.target;
        }
        else
        {
            this.current += dCurrent;
        }
    }
}

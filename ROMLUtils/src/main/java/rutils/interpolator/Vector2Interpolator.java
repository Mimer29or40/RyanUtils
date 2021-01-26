package rutils.interpolator;

import org.joml.Math;
import org.joml.Vector2d;

public class Vector2Interpolator extends Interpolator<Vector2d>
{
    private static final double DELTA = 1e-6;
    
    private final Vector2d _differenceAxial    = new Vector2d();
    private final Vector2d _differenceUnit     = new Vector2d();
    private final Vector2d _velocity           = new Vector2d();
    private final Vector2d _velocityNormal     = new Vector2d();
    private final Vector2d _acceleration       = new Vector2d();
    private final Vector2d _accelerationNormal = new Vector2d();
    private final Vector2d _dCurrent           = new Vector2d();
    
    
    /**
     * @return A new instance of the data class
     */
    @Override
    protected Vector2d newInstance()
    {
        return new Vector2d();
    }
    
    /**
     * Sets the value of assigner to the object assignee.
     *
     * @param object The object to assign the values to.
     * @param value  The object to copy the values from.
     * @return object
     */
    @Override
    protected Vector2d assign(Vector2d object, Vector2d value)
    {
        return object.set(value);
    }
    
    /**
     * Internal method to check if a value of type T is equal to the other object.
     *
     * @param value The value to check. This should be either get() or getTarget().
     * @param other The other value in the equality.
     * @return If the value is equal to the other.
     */
    @Override
    protected boolean equals(Vector2d value, Object other)
    {
        return other instanceof Vector2d && value.equals((Vector2d) other, Vector2Interpolator.DELTA);
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
    protected int compare(Vector2d a, Vector2d b)
    {
        return Double.compare(a.lengthSquared(), b.lengthSquared());
    }
    
    /**
     * @return The difference between the current value and the target difference.
     */
    @Override
    public Vector2d difference()
    {
        return this._differenceAxial.set(this.target).sub(this.current);
    }
    
    /**
     * @return The current velocity that the mover it traveling at.
     */
    @Override
    public Vector2d velocity()
    {
        return this.velocity;
    }
    
    /**
     * Sets the velocity of the mover to zero.
     */
    @Override
    protected void resetVelocity()
    {
        this.velocity.zero();
    }
    
    /**
     * This method is called if the current value does not match the target value
     *
     * @param dt The time since last frame in seconds.
     */
    @Override
    protected void updateCurrent(double dt)
    {
        this._differenceAxial.set(this.target).sub(this.current).normalize(this._differenceUnit);
        
        double dot = this._differenceUnit.dot(this.velocity);
        
        this._velocity.set(this._differenceUnit).mul(Math.abs(dot));
        this._velocityNormal.set(this._differenceUnit).mul(dot).sub(this.velocity);
        
        double timeToStopAxial  = this._velocity.length() / this.maxAcceleration;
        double timeToStopNormal = this._velocityNormal.length() / this.maxAcceleration;
        
        double maxStopDistanceAxial = this._velocity.lengthSquared() / (2.0 * this.maxAcceleration);
        
        if (dot >= Vector2Interpolator.DELTA && (maxStopDistanceAxial >= this._differenceAxial.length() || timeToStopNormal > timeToStopAxial))
        {
            this._acceleration.set(this._differenceUnit).mul(-this.maxAcceleration);
        }
        else
        {
            this._acceleration.set(this._differenceAxial).div(dt);
        }
        
        if (this._velocityNormal.lengthSquared() > Vector2Interpolator.DELTA * Vector2Interpolator.DELTA)
        {
            this._accelerationNormal.set(this._velocityNormal).div(dt);
            this._acceleration.add(this._accelerationNormal);
        }
        
        if (this._acceleration.lengthSquared() > this.maxAcceleration * this.maxAcceleration) this._acceleration.normalize(this.maxAcceleration);
        
        this.velocity.fma(dt, this._acceleration);
        if (this.velocity.lengthSquared() > this.maxVelocity * this.maxVelocity) this.velocity.normalize(this.maxVelocity);
        
        this._dCurrent.set(this.velocity).mul(dt);
        if (this._dCurrent.lengthSquared() > this._differenceAxial.lengthSquared())
        {
            resetVelocity();
            this.current.set(this.target);
        }
        else
        {
            this.current.add(this._dCurrent);
        }
    }
}

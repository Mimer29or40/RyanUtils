package rutils.interpolator;

import org.joml.Math;
import org.joml.Quaterniond;
import org.joml.Quaterniondc;
import org.joml.Vector3d;
import rutils.joml.JOMLUtil;

public class QuaternionInterpolator extends Interpolator<Quaterniond>
{
    private static final double DELTA = 1e-6;
    
    private static final Vector3d TEMP_V3D = new Vector3d();
    
    private double _velocity = 0.0;
    
    private final Quaterniond _difference = new Quaterniond();
    
    /**
     * @return A new instance of the data class
     */
    @Override
    protected Quaterniond newInstance()
    {
        return new Quaterniond();
    }
    
    /**
     * Sets the value of assigner to the object assignee.
     *
     * @param object The object to assign the values to.
     * @param value  The object to copy the values from.
     * @return object
     */
    @Override
    protected Quaterniond assign(Quaterniond object, Quaterniond value)
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
    protected boolean equals(Quaterniond value, Object other)
    {
        return other instanceof Quaterniond && JOMLUtil.equals(value, (Quaterniond) other, QuaternionInterpolator.DELTA);
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
    protected int compare(Quaterniond a, Quaterniond b)
    {
        return 0;
    }
    
    /**
     * @return The difference between the current value and the target difference.
     */
    @Override
    public Quaterniond difference()
    {
        return this._difference.set(this.target).difference(this.current);
    }
    
    /**
     * @return The current velocity that the mover it traveling at.
     */
    @Override
    public Quaterniond velocity()
    {
        QuaternionInterpolator.TEMP_V3D.set(this._velocity, this._velocity, this._velocity);
        difference().transform(QuaternionInterpolator.TEMP_V3D);
        return this.velocity.set(QuaternionInterpolator.TEMP_V3D.x(), QuaternionInterpolator.TEMP_V3D.y(), QuaternionInterpolator.TEMP_V3D.z(), 0);
    }
    
    /**
     * Sets the velocity of the mover to zero.
     */
    @Override
    protected void resetVelocity()
    {
        this._velocity = 0;
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
        double distanceToTarget    = quaternionDistance(this.current, this.target);
        double maxStoppingDistance = this._velocity * this._velocity / (2.0 * this.maxAcceleration);
        
        double acceleration = this._velocity * distanceToTarget > 0.0 && maxStoppingDistance >= Math.abs(distanceToTarget) ? -this.maxAcceleration : this.maxAcceleration;
        
        if (distanceToTarget < 0.0) acceleration *= -1;
        
        this._velocity = Math.fma(dt, acceleration, this._velocity);
        if (Math.abs(this._velocity) > this.maxVelocity) this._velocity = this._velocity > 0 ? this.maxVelocity : -this.maxVelocity;
        
        double dCurrent = this._velocity * dt;
        if (this._velocity * distanceToTarget > 0.0 && Math.abs(dCurrent) > Math.abs(distanceToTarget))
        {
            resetVelocity();
            this.current.set(this.target);
        }
        else
        {
            this.current.nlerp(this.target, dCurrent / distanceToTarget).normalize();
        }
    }
    
    /**
     * From: https://math.stackexchange.com/questions/90081/quaternion-distance
     */
    private double quaternionDistance(Quaterniondc a, Quaterniondc b)
    {
        double dot = a.x() * b.x() + a.y() * b.y() + a.z() * b.z() + a.w() * b.w();
        
        return Math.acos(2.0 * dot * dot - 1.0);
    }
}

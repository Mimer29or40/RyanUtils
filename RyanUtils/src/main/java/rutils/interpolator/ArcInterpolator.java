package rutils.interpolator;

public class ArcInterpolator extends ScalarInterpolator
{
    private static final double PI2 = Math.PI * 2.0;
    
    /**
     * @return The difference between the current value and the target difference.
     */
    @Override
    public Double difference()
    {
        double difference = Math.PI - Math.abs((Math.abs(this.current - this.target) % ArcInterpolator.PI2) - Math.PI);
        
        if (clamp(this.current - this.target) < Math.PI) difference *= -1.0f;
        
        return difference;
    }
    
    /**
     * Method called once per frame to update the values depending on how much time has passed.
     *
     * @param dt The time since last frame in seconds.
     */
    @Override
    public void update(double dt)
    {
        this._current = clamp(this._current);
        
        super.update(dt);
    }
    
    /**
     * This method is called if the current value does not match the target value
     *
     * @param dt The time since last frame in seconds.
     */
    @Override
    protected void updateCurrent(double dt)
    {
        super.updateCurrent(dt);
        
        this.current = clamp(this.current);
    }
    
    private static double clamp(double angle)
    {
        if (angle < 0) return angle + ArcInterpolator.PI2;
        if (angle >= ArcInterpolator.PI2) return angle - ArcInterpolator.PI2;
        return angle;
    }
}

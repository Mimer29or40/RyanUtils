package rutils.interpolator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScalarInterpolatorTest
{
    @Test
    void newInstance()
    {
        ScalarInterpolator interpolator = new ScalarInterpolator();
        
        assertEquals(0.0, interpolator.newInstance());
    }
    
    @Test
    void assign()
    {
        ScalarInterpolator interpolator = new ScalarInterpolator();
        
        double assigner, assignee;
        
        assignee = 0.0;
        assigner = 10.0;
        assignee = interpolator.assign(assignee, assigner);
        
        assertEquals(assigner, assignee);
    }
    
    @Test
    void equals()
    {
        ScalarInterpolator interpolator = new ScalarInterpolator();
        
        double d1, d2;
        
        d1 = 69.0;
        d2 = 69.0;
        assertTrue(interpolator.equals(d1, d2));
        
        d1 = 69.0;
        d2 = 420.0;
        assertFalse(interpolator.equals(d1, d2));
    }
    
    @Test
    void compare()
    {
        ScalarInterpolator interpolator = new ScalarInterpolator();
        
        double d1, d2;
        
        d1 = 69.0;
        d2 = 69.0;
        assertEquals(interpolator.compare(d1, d2), 0);
        
        d1 = 69.0;
        d2 = 420.0;
        assertEquals(interpolator.compare(d1, d2), -1);
        
        d1 = 420.0;
        d2 = 69.0;
        assertEquals(interpolator.compare(d1, d2), 1);
    }
    
    @Test
    void difference()
    {
        ScalarInterpolator interpolator = new ScalarInterpolator();
        
        interpolator.target  = 69.0;
        interpolator.current = 69.0;
        assertEquals(interpolator.difference(), 0.0);
        
        interpolator.target  = 69.0;
        interpolator.current = 79.0;
        assertEquals(interpolator.difference(), -10.0);
        
        interpolator.target  = 79.0;
        interpolator.current = 69.0;
        assertEquals(interpolator.difference(), 10.0);
    }
    
    @Test
    void velocity()
    {
        ScalarInterpolator interpolator = new ScalarInterpolator();
        
        double dt = 0.1;
        
        interpolator.target = 10.0;
        interpolator.update(dt);
        
        assertEquals(Math.fma(dt, interpolator.maxAcceleration, 0.0), ((Interpolator<Double>) interpolator).velocity(), 0.000001);
    }
    
    @Test
    void resetVelocity()
    {
        ScalarInterpolator interpolator = new ScalarInterpolator();
        
        double dt = 0.1;
        
        interpolator.target = 10.0;
        interpolator.update(dt);
        interpolator.resetVelocity();
        
        assertEquals(0.0, interpolator.velocity(), 0.000001);
    }
}

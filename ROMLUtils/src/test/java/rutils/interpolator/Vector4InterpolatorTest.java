package rutils.interpolator;

import org.joml.Vector4d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector4InterpolatorTest
{
    @Test
    void newInstance()
    {
        Vector4Interpolator interpolator = new Vector4Interpolator();
        
        assertEquals(new Vector4d(), interpolator.newInstance());
    }
    
    @Test
    void assign()
    {
        Vector4Interpolator interpolator = new Vector4Interpolator();
        
        Vector4d assigner, assignee;
        
        assignee = new Vector4d();
        assigner = new Vector4d(10);
        assignee = interpolator.assign(assignee, assigner);
        
        assertEquals(assigner, assignee);
    }
    
    @Test
    void equals()
    {
        Vector4Interpolator interpolator = new Vector4Interpolator();
        
        Vector4d d1, d2;
        
        d1 = new Vector4d(69.0);
        d2 = new Vector4d(69.0);
        assertTrue(interpolator.equals(d1, d2));
        
        d1 = new Vector4d(69.0);
        d2 = new Vector4d(420.0);
        assertFalse(interpolator.equals(d1, d2));
    }
    
    @Test
    void compare()
    {
        Vector4Interpolator interpolator = new Vector4Interpolator();
        
        Vector4d d1, d2;
        
        d1 = new Vector4d(69.0);
        d2 = new Vector4d(69.0);
        assertEquals(interpolator.compare(d1, d2), 0);
        
        d1 = new Vector4d(69.0);
        d2 = new Vector4d(420.0);
        assertEquals(interpolator.compare(d1, d2), -1);
        
        d1 = new Vector4d(420.0);
        d2 = new Vector4d(69.0);
        assertEquals(interpolator.compare(d1, d2), 1);
    }
    
    @Test
    void difference()
    {
        Vector4Interpolator interpolator = new Vector4Interpolator();
        
        interpolator.target  = new Vector4d(69.0);
        interpolator.current = new Vector4d(69.0);
        assertEquals(interpolator.difference(), new Vector4d(0.0));
        
        interpolator.target  = new Vector4d(69.0);
        interpolator.current = new Vector4d(79.0);
        assertEquals(interpolator.difference(), new Vector4d(-10.0));
        
        interpolator.target  = new Vector4d(79.0);
        interpolator.current = new Vector4d(69.0);
        assertEquals(interpolator.difference(), new Vector4d(10.0));
    }
    
    @Test
    void velocity()
    {
        Vector4Interpolator interpolator = new Vector4Interpolator();
        
        double dt = 0.1;
        
        interpolator.target = new Vector4d(10.0);
        interpolator.update(dt);
        
        assertEquals(new Vector4d(-4.868E+0, -4.868E+0, -4.868E+0, -4.377E+0).length(), interpolator.velocity().length(), 0.001);
    }
    
    @Test
    void resetVelocity()
    {
        Vector4Interpolator interpolator = new Vector4Interpolator();
        
        double dt = 0.1;
        
        interpolator.target = new Vector4d(10.0);
        interpolator.update(dt);
        interpolator.resetVelocity();
        
        assertEquals(new Vector4d(0.0), interpolator.velocity());
    }
}

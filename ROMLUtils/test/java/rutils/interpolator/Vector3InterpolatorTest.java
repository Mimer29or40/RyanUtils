package rutils.interpolator;

import org.joml.Vector3d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector3InterpolatorTest
{
    @Test
    void newInstance()
    {
        Vector3Interpolator interpolator = new Vector3Interpolator();
        
        assertEquals(new Vector3d(), interpolator.newInstance());
    }
    
    @Test
    void assign()
    {
        Vector3Interpolator interpolator = new Vector3Interpolator();
        
        Vector3d assigner, assignee;
        
        assignee = new Vector3d();
        assigner = new Vector3d(10);
        assignee = interpolator.assign(assignee, assigner);
        
        assertEquals(assigner, assignee);
    }
    
    @Test
    void equals()
    {
        Vector3Interpolator interpolator = new Vector3Interpolator();
        
        Vector3d d1, d2;
        
        d1 = new Vector3d(69.0);
        d2 = new Vector3d(69.0);
        assertTrue(interpolator.equals(d1, d2));
        
        d1 = new Vector3d(69.0);
        d2 = new Vector3d(420.0);
        assertFalse(interpolator.equals(d1, d2));
    }
    
    @Test
    void compare()
    {
        Vector3Interpolator interpolator = new Vector3Interpolator();
        
        Vector3d d1, d2;
        
        d1 = new Vector3d(69.0);
        d2 = new Vector3d(69.0);
        assertEquals(interpolator.compare(d1, d2), 0);
        
        d1 = new Vector3d(69.0);
        d2 = new Vector3d(420.0);
        assertEquals(interpolator.compare(d1, d2), -1);
        
        d1 = new Vector3d(420.0);
        d2 = new Vector3d(69.0);
        assertEquals(interpolator.compare(d1, d2), 1);
    }
    
    @Test
    void difference()
    {
        Vector3Interpolator interpolator = new Vector3Interpolator();
        
        interpolator.target  = new Vector3d(69.0);
        interpolator.current = new Vector3d(69.0);
        assertEquals(interpolator.difference(), new Vector3d(0.0));
        
        interpolator.target  = new Vector3d(69.0);
        interpolator.current = new Vector3d(79.0);
        assertEquals(interpolator.difference(), new Vector3d(-10.0));
        
        interpolator.target  = new Vector3d(79.0);
        interpolator.current = new Vector3d(69.0);
        assertEquals(interpolator.difference(), new Vector3d(10.0));
    }
    
    @Test
    void velocity()
    {
        Vector3Interpolator interpolator = new Vector3Interpolator();
        
        double dt = 0.1;
        
        interpolator.target = new Vector3d(10.0);
        interpolator.update(dt);
        
        assertEquals(new Vector3d(5.774E+0, 5.774E+0, 5.774E+0).length(), interpolator.velocity().length(), 0.001);
    }
    
    @Test
    void resetVelocity()
    {
        Vector3Interpolator interpolator = new Vector3Interpolator();
        
        double dt = 0.1;
        
        interpolator.target = new Vector3d(10.0);
        interpolator.update(dt);
        interpolator.resetVelocity();
        
        assertEquals(new Vector3d(0.0), interpolator.velocity());
    }
}

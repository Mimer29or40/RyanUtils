package rutils.interpolator;

import org.joml.Vector2d;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Vector2InterpolatorTest
{
    @Test
    void newInstance()
    {
        Vector2Interpolator interpolator = new Vector2Interpolator();
        
        assertEquals(new Vector2d(), interpolator.newInstance());
    }
    
    @Test
    void assign()
    {
        Vector2Interpolator interpolator = new Vector2Interpolator();
        
        Vector2d assigner, assignee;
        
        assignee = new Vector2d();
        assigner = new Vector2d(10, 10);
        assignee = interpolator.assign(assignee, assigner);
        
        assertEquals(assigner, assignee);
    }
    
    @Test
    void equals()
    {
        Vector2Interpolator interpolator = new Vector2Interpolator();
        
        Vector2d d1, d2;
        
        d1 = new Vector2d(69.0);
        d2 = new Vector2d(69.0);
        assertTrue(interpolator.equals(d1, d2));
        
        d1 = new Vector2d(69.0);
        d2 = new Vector2d(420.0);
        assertFalse(interpolator.equals(d1, d2));
    }
    
    @Test
    void compare()
    {
        Vector2Interpolator interpolator = new Vector2Interpolator();
        
        Vector2d d1, d2;
        
        d1 = new Vector2d(69.0);
        d2 = new Vector2d(69.0);
        assertEquals(interpolator.compare(d1, d2), 0);
        
        d1 = new Vector2d(69.0);
        d2 = new Vector2d(420.0);
        assertEquals(interpolator.compare(d1, d2), -1);
        
        d1 = new Vector2d(420.0);
        d2 = new Vector2d(69.0);
        assertEquals(interpolator.compare(d1, d2), 1);
    }
    
    @Test
    void difference()
    {
        Vector2Interpolator interpolator = new Vector2Interpolator();
        
        interpolator.target  = new Vector2d(69.0);
        interpolator.current = new Vector2d(69.0);
        assertEquals(interpolator.difference(), new Vector2d(0.0));
        
        interpolator.target  = new Vector2d(69.0);
        interpolator.current = new Vector2d(79.0);
        assertEquals(interpolator.difference(), new Vector2d(-10.0));
        
        interpolator.target  = new Vector2d(79.0);
        interpolator.current = new Vector2d(69.0);
        assertEquals(interpolator.difference(), new Vector2d(10.0));
    }
    
    @Test
    void velocity()
    {
        Vector2Interpolator interpolator = new Vector2Interpolator();
        
        double dt = 0.1;
        
        interpolator.target = new Vector2d(10.0);
        interpolator.update(dt);
        
        assertEquals(new Vector2d(7.071E+0, 7.071E+0).length(), interpolator.velocity().length(), 0.001);
    }
    
    @Test
    void resetVelocity()
    {
        Vector2Interpolator interpolator = new Vector2Interpolator();
        
        double dt = 0.1;
        
        interpolator.target = new Vector2d(10.0);
        interpolator.update(dt);
        interpolator.resetVelocity();
        
        assertEquals(new Vector2d(0.0), interpolator.velocity());
    }
}

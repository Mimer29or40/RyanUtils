package rutils.interpolator;

import org.joml.Quaterniond;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuaternionInterpolatorTest
{
    @Test
    void newInstance()
    {
        QuaternionInterpolator interpolator = new QuaternionInterpolator();
        
        assertEquals(new Quaterniond(), interpolator.newInstance());
    }
    
    @Test
    void assign()
    {
        QuaternionInterpolator interpolator = new QuaternionInterpolator();
        
        Quaterniond assigner, assignee;
        
        assignee = new Quaterniond();
        assigner = new Quaterniond().lookAlong(0, 0, 1, 0, 1, 0);
        assignee = interpolator.assign(assignee, assigner);
        
        assertEquals(assigner, assignee);
    }
    
    @Test
    void equals()
    {
        QuaternionInterpolator interpolator = new QuaternionInterpolator();
        
        Quaterniond d1, d2;
        
        d1 = new Quaterniond().lookAlong(0, 0, 1, 0, 1, 0);
        d2 = new Quaterniond().lookAlong(0, 0, 1, 0, 1, 0);
        assertTrue(interpolator.equals(d1, d2));
        
        d1 = new Quaterniond().lookAlong(0, 0, 1, 0, 1, 0);
        d2 = new Quaterniond().lookAlong(0, 0, -1, 0, 1, 0);
        assertFalse(interpolator.equals(d1, d2));
    }
    
    @Test
    void compare()
    {
        QuaternionInterpolator interpolator = new QuaternionInterpolator();
        
        Quaterniond d1, d2;
        
        d1 = new Quaterniond();
        d2 = new Quaterniond();
        assertEquals(interpolator.compare(d1, d2), 0);
        
        d1 = new Quaterniond().lookAlong(0, 0, 1, 0, 1, 0);
        d2 = new Quaterniond().lookAlong(0, 0, -1, 0, 1, 0);
        assertEquals(interpolator.compare(d1, d2), 0);
        
        d1 = new Quaterniond().lookAlong(0, 0, -1, 0, 1, 0);
        d2 = new Quaterniond().lookAlong(0, 0, 1, 0, 1, 0);
        assertEquals(interpolator.compare(d1, d2), 0);
    }
}

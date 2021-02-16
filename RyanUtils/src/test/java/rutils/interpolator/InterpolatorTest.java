package rutils.interpolator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InterpolatorTest
{
    @Test
    void enabled()
    {
        Interpolator<?> interpolator = new ScalarInterpolator();
        
        assertTrue(interpolator.enabled());
        
        interpolator.enabled(false);
        assertFalse(interpolator.enabled());
        
        interpolator.toggleEnabled();
        assertTrue(interpolator.enabled());
        
        interpolator.disable();
        assertFalse(interpolator.enabled());
        
        interpolator.enable();
        assertTrue(interpolator.enabled());
    }
    
    @Test
    void updated()
    {
        Interpolator<Double> interpolator = new ScalarInterpolator();
        
        assertTrue(interpolator.updated());
        
        interpolator.target  = 1.0;
        interpolator.current = 1.0;
        for (int i = 0; i < 10; i++)
        {
            interpolator.update(0.1);
        }
        assertTrue(interpolator.atTarget());
        assertFalse(interpolator.updated());
        
        interpolator.target          = 1000.0;
        interpolator.current         = 1.0;
        interpolator.maxAcceleration = 50;
        interpolator.maxVelocity     = 200;
        while (!interpolator.atTarget())
        {
            interpolator.update(0.1);
            assertTrue(interpolator.updated());
        }
        interpolator.update(0.1);
        assertFalse(interpolator.updated());
    }
    
    @Test
    void extrema()
    {
        Interpolator<Double> interpolator = new ScalarInterpolator();
        
        assertTrue(interpolator.updated());
        
        interpolator.target  = 1.0;
        interpolator.current = 1.0;
        for (int i = 0; i < 10; i++)
        {
            interpolator.update(0.1);
        }
        assertTrue(interpolator.atTarget());
        assertFalse(interpolator.updated());
        
        interpolator.maxValue = 9.0;
        interpolator.target   = 10.0;
        interpolator.current  = 1.0;
        while (!interpolator.atTarget())
        {
            interpolator.update(0.1);
            assertTrue(interpolator.updated());
        }
        interpolator.update(0.1);
        assertFalse(interpolator.updated());
        
        interpolator.minValue = 2.0;
        interpolator.target   = 1.0;
        interpolator.current  = 10.0;
        while (!interpolator.atTarget())
        {
            interpolator.update(0.1);
            assertTrue(interpolator.updated());
        }
        interpolator.update(0.1);
        assertFalse(interpolator.updated());
    }
}

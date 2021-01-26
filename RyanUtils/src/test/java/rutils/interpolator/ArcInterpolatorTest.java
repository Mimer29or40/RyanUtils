package rutils.interpolator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArcInterpolatorTest
{
    @Test
    void difference()
    {
        ArcInterpolator interpolator = new ArcInterpolator();
        
        interpolator.target  = 0.0;
        interpolator.current = Math.PI;
        assertEquals(interpolator.difference(), Math.PI);
    
        interpolator.target  = 0.0;
        interpolator.current = Math.PI - 1;
        assertEquals(interpolator.difference(), -Math.PI + 1);
    
        interpolator.target  = 0.0;
        interpolator.current = Math.PI + 1;
        assertEquals(interpolator.difference(), Math.PI - 1);
    }
}

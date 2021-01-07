package rutils.interpolator;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ScalarInterpolatorTest
{
    private ScalarInterpolator a;
    private ScalarInterpolator b;
    
    @BeforeEach
    void setUp()
    {
        this.a = new ScalarInterpolator();
        this.b = new ScalarInterpolator();
    }
    
    @Test
    void newInstance()
    {
        assertEquals(0.0, this.a.newInstance());
    }
    
    @Test
    void assign()
    {
        this.a.current = this.a.assign(this.a.current, 52.125);
    
        assertEquals(52.125, this.a.current);
    }
    
    @Test
    void testEquals()
    {
    }
    
    @Test
    void compare()
    {
    }
    
    @Test
    void difference()
    {
    }
    
    @Test
    void velocity()
    {
    }
    
    @Test
    void resetVelocity()
    {
    }
    
    @Test
    void updateCurrent()
    {
    }
}

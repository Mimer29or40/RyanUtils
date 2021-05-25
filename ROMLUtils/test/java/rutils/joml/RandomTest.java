package rutils.joml;

import org.joml.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import rutils.Logger;

import static org.junit.jupiter.api.Assertions.*;

class RandomTest
{
    private static final Logger LOGGER = new Logger();
    
    static Random INSTANCE;
    
    @BeforeAll
    static void setUp()
    {
        RandomTest.INSTANCE = new Random();
        RandomTest.INSTANCE = new Random(1337);
    }
    
    @Test
    void nextVector2i()
    {
        assertThrows(IllegalArgumentException.class, () -> LOGGER.info(INSTANCE.nextVector2i(-1)));
        
        assertThrows(IllegalArgumentException.class, () -> LOGGER.info(INSTANCE.nextVector2i(1, 0)));
        
        int origin, bound, amount;
        
        Vector2i vector = RandomTest.INSTANCE.nextVector2i();
        
        origin = 0;
        bound  = 267;
        amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            INSTANCE.nextVector2i(vector, bound);
            assertTrue(vector.x >= origin);
            assertTrue(vector.x <= bound);
            assertTrue(vector.y >= origin);
            assertTrue(vector.y <= bound);
        }
        
        origin = 50;
        bound  = 100;
        amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            INSTANCE.nextVector2i(vector, origin, bound);
            assertTrue(vector.x >= origin);
            assertTrue(vector.x <= bound);
            assertTrue(vector.y >= origin);
            assertTrue(vector.y <= bound);
        }
    }
    
    @Test
    void nextVector3i()
    {
        assertThrows(IllegalArgumentException.class, () -> LOGGER.info(INSTANCE.nextVector3i(-1)));
        
        assertThrows(IllegalArgumentException.class, () -> LOGGER.info(INSTANCE.nextVector3i(1, 0)));
        
        int origin, bound, amount;
        
        Vector3i vector = RandomTest.INSTANCE.nextVector3i();
        
        origin = 0;
        bound  = 267;
        amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            INSTANCE.nextVector3i(vector, bound);
            assertTrue(vector.x >= origin);
            assertTrue(vector.x <= bound);
            assertTrue(vector.y >= origin);
            assertTrue(vector.y <= bound);
            assertTrue(vector.z >= origin);
            assertTrue(vector.z <= bound);
        }
        
        origin = 50;
        bound  = 100;
        amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            INSTANCE.nextVector3i(vector, origin, bound);
            assertTrue(vector.x >= origin);
            assertTrue(vector.x <= bound);
            assertTrue(vector.y >= origin);
            assertTrue(vector.y <= bound);
            assertTrue(vector.z >= origin);
            assertTrue(vector.z <= bound);
        }
    }
    
    @Test
    void nextVector4i()
    {
        assertThrows(IllegalArgumentException.class, () -> LOGGER.info(INSTANCE.nextVector4i(-1)));
        
        assertThrows(IllegalArgumentException.class, () -> LOGGER.info(INSTANCE.nextVector4i(1, 0)));
        
        int origin, bound, amount;
        
        Vector4i vector = RandomTest.INSTANCE.nextVector4i();
        
        origin = 0;
        bound  = 267;
        amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            INSTANCE.nextVector4i(vector, bound);
            assertTrue(vector.x >= origin);
            assertTrue(vector.x <= bound);
            assertTrue(vector.y >= origin);
            assertTrue(vector.y <= bound);
            assertTrue(vector.z >= origin);
            assertTrue(vector.z <= bound);
            assertTrue(vector.w >= origin);
            assertTrue(vector.w <= bound);
        }
        
        origin = 50;
        bound  = 100;
        amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            INSTANCE.nextVector4i(vector, origin, bound);
            assertTrue(vector.x >= origin);
            assertTrue(vector.x <= bound);
            assertTrue(vector.y >= origin);
            assertTrue(vector.y <= bound);
            assertTrue(vector.z >= origin);
            assertTrue(vector.z <= bound);
            assertTrue(vector.w >= origin);
            assertTrue(vector.w <= bound);
        }
    }
    
    @Test
    void nextVector2f()
    {
        assertThrows(IllegalArgumentException.class, () -> LOGGER.info(INSTANCE.nextVector2f(-1)));
        
        assertThrows(IllegalArgumentException.class, () -> LOGGER.info(INSTANCE.nextVector2f(1, 0)));
        
        float origin, bound, amount;
        
        Vector2f vector = RandomTest.INSTANCE.nextVector2f();
        
        origin = 0;
        bound  = 267;
        amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            INSTANCE.nextVector2f(vector, bound);
            assertTrue(vector.x >= origin);
            assertTrue(vector.x <= bound);
            assertTrue(vector.y >= origin);
            assertTrue(vector.y <= bound);
        }
        
        origin = 50;
        bound  = 100;
        amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            INSTANCE.nextVector2f(vector, origin, bound);
            assertTrue(vector.x >= origin);
            assertTrue(vector.x <= bound);
            assertTrue(vector.y >= origin);
            assertTrue(vector.y <= bound);
        }
    }
    
    @Test
    void nextVector3f()
    {
        assertThrows(IllegalArgumentException.class, () -> LOGGER.info(INSTANCE.nextVector3f(-1)));
        
        assertThrows(IllegalArgumentException.class, () -> LOGGER.info(INSTANCE.nextVector3f(1, 0)));
        
        float origin, bound, amount;
        
        Vector3f vector = RandomTest.INSTANCE.nextVector3f();
        
        origin = 0;
        bound  = 267;
        amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            INSTANCE.nextVector3f(vector, bound);
            assertTrue(vector.x >= origin);
            assertTrue(vector.x <= bound);
            assertTrue(vector.y >= origin);
            assertTrue(vector.y <= bound);
            assertTrue(vector.z >= origin);
            assertTrue(vector.z <= bound);
        }
        
        origin = 50;
        bound  = 100;
        amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            INSTANCE.nextVector3f(vector, origin, bound);
            assertTrue(vector.x >= origin);
            assertTrue(vector.x <= bound);
            assertTrue(vector.y >= origin);
            assertTrue(vector.y <= bound);
            assertTrue(vector.z >= origin);
            assertTrue(vector.z <= bound);
        }
    }
    
    @Test
    void nextVector4f()
    {
        assertThrows(IllegalArgumentException.class, () -> LOGGER.info(INSTANCE.nextVector4f(-1)));
        
        assertThrows(IllegalArgumentException.class, () -> LOGGER.info(INSTANCE.nextVector4f(1, 0)));
        
        float origin, bound, amount;
        
        Vector4f vector = RandomTest.INSTANCE.nextVector4f();
        
        origin = 0;
        bound  = 267;
        amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            INSTANCE.nextVector4f(vector, bound);
            assertTrue(vector.x >= origin);
            assertTrue(vector.x <= bound);
            assertTrue(vector.y >= origin);
            assertTrue(vector.y <= bound);
            assertTrue(vector.z >= origin);
            assertTrue(vector.z <= bound);
            assertTrue(vector.w >= origin);
            assertTrue(vector.w <= bound);
        }
        
        origin = 50;
        bound  = 100;
        amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            INSTANCE.nextVector4f(vector, origin, bound);
            assertTrue(vector.x >= origin);
            assertTrue(vector.x <= bound);
            assertTrue(vector.y >= origin);
            assertTrue(vector.y <= bound);
            assertTrue(vector.z >= origin);
            assertTrue(vector.z <= bound);
            assertTrue(vector.w >= origin);
            assertTrue(vector.w <= bound);
        }
    }
    
    @Test
    void nextVector2d()
    {
        assertThrows(IllegalArgumentException.class, () -> LOGGER.info(INSTANCE.nextVector2d(-1)));
        
        assertThrows(IllegalArgumentException.class, () -> LOGGER.info(INSTANCE.nextVector2d(1, 0)));
        
        float origin, bound, amount;
        
        Vector2d vector = RandomTest.INSTANCE.nextVector2d();
        
        origin = 0;
        bound  = 267;
        amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            INSTANCE.nextVector2d(vector, bound);
            assertTrue(vector.x >= origin);
            assertTrue(vector.x <= bound);
            assertTrue(vector.y >= origin);
            assertTrue(vector.y <= bound);
        }
        
        origin = 50;
        bound  = 100;
        amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            INSTANCE.nextVector2d(vector, origin, bound);
            assertTrue(vector.x >= origin);
            assertTrue(vector.x <= bound);
            assertTrue(vector.y >= origin);
            assertTrue(vector.y <= bound);
        }
    }
    
    @Test
    void nextVector3d()
    {
        assertThrows(IllegalArgumentException.class, () -> LOGGER.info(INSTANCE.nextVector3d(-1)));
        
        assertThrows(IllegalArgumentException.class, () -> LOGGER.info(INSTANCE.nextVector3d(1, 0)));
        
        float origin, bound, amount;
        
        Vector3d vector = RandomTest.INSTANCE.nextVector3d();
        
        origin = 0;
        bound  = 267;
        amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            INSTANCE.nextVector3d(vector, bound);
            assertTrue(vector.x >= origin);
            assertTrue(vector.x <= bound);
            assertTrue(vector.y >= origin);
            assertTrue(vector.y <= bound);
            assertTrue(vector.z >= origin);
            assertTrue(vector.z <= bound);
        }
        
        origin = 50;
        bound  = 100;
        amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            INSTANCE.nextVector3d(vector, origin, bound);
            assertTrue(vector.x >= origin);
            assertTrue(vector.x <= bound);
            assertTrue(vector.y >= origin);
            assertTrue(vector.y <= bound);
            assertTrue(vector.z >= origin);
            assertTrue(vector.z <= bound);
        }
    }
    
    @Test
    void nextVector4d()
    {
        assertThrows(IllegalArgumentException.class, () -> LOGGER.info(INSTANCE.nextVector4d(-1)));
        
        assertThrows(IllegalArgumentException.class, () -> LOGGER.info(INSTANCE.nextVector4d(1, 0)));
        
        float origin, bound, amount;
        
        Vector4d vector = RandomTest.INSTANCE.nextVector4d();
        
        origin = 0;
        bound  = 267;
        amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            INSTANCE.nextVector4d(vector, bound);
            assertTrue(vector.x >= origin);
            assertTrue(vector.x <= bound);
            assertTrue(vector.y >= origin);
            assertTrue(vector.y <= bound);
            assertTrue(vector.z >= origin);
            assertTrue(vector.z <= bound);
            assertTrue(vector.w >= origin);
            assertTrue(vector.w <= bound);
        }
        
        origin = 50;
        bound  = 100;
        amount = 100_000_000;
        for (int i = 0; i < amount; i++)
        {
            INSTANCE.nextVector4d(vector, origin, bound);
            assertTrue(vector.x >= origin);
            assertTrue(vector.x <= bound);
            assertTrue(vector.y >= origin);
            assertTrue(vector.y <= bound);
            assertTrue(vector.z >= origin);
            assertTrue(vector.z <= bound);
            assertTrue(vector.w >= origin);
            assertTrue(vector.w <= bound);
        }
    }
    
    @Test
    void nextUnitF()
    {
        Vector2f vector2 = INSTANCE.nextUnit2f();
        assertEquals(1.0, vector2.length(), 0.000001);
        assertEquals(1.0, INSTANCE.nextUnit2f(vector2).length(), 0.000001);
        
        Vector3f vector3 = INSTANCE.nextUnit3f();
        assertEquals(1.0, vector3.length(), 0.000001);
        assertEquals(1.0, INSTANCE.nextUnit3f(vector3).length(), 0.000001);
        
        Vector4f vector4 = INSTANCE.nextUnit4f();
        assertEquals(1.0, vector4.length(), 0.000001);
        assertEquals(1.0, INSTANCE.nextUnit4f(vector4).length(), 0.000001);
    }
    
    @Test
    void nextUnitD()
    {
        Vector2d vector2 = INSTANCE.nextUnit2d();
        assertEquals(1.0, vector2.length(), 0.000001);
        assertEquals(1.0, INSTANCE.nextUnit2d(vector2).length(), 0.000001);
        
        Vector3d vector3 = INSTANCE.nextUnit3d();
        assertEquals(1.0, vector3.length(), 0.000001);
        assertEquals(1.0, INSTANCE.nextUnit3d(vector3).length(), 0.000001);
        
        Vector4d vector4 = INSTANCE.nextUnit4d();
        assertEquals(1.0, vector4.length(), 0.000001);
        assertEquals(1.0, INSTANCE.nextUnit4d(vector4).length(), 0.000001);
    }
}

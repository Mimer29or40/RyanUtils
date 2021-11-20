package rutils.joml;

import org.joml.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JOMLUtilTest
{
    @Test
    void projectToPlane()
    {
        Vector3f vectorF = new Vector3f(1, 1, 1);
        Vector3f normalF = new Vector3f(0, 0, 1);
        
        Vector3fc resultF = JOMLUtil.projectToPlane(vectorF, normalF);
        assertEquals(new Vector3f(1, 1, 0).normalize(), resultF);
        
        Vector3d vectorD = new Vector3d(1, 1, 1);
        Vector3d normalD = new Vector3d(0, 0, 1);
        
        Vector3dc resultD = JOMLUtil.projectToPlane(vectorD, normalD);
        assertEquals(new Vector3d(1, 1, 0).normalize(), resultD);
    }
    
    @Test
    void testEquals()
    {
        assertTrue(JOMLUtil.equals(new Vector3f(3, 4, 5), 3, 4, 5, 0.000001F));
        assertTrue(JOMLUtil.equals(new Vector3d(3, 4, 5), 3, 4, 5, 0.000001));
        assertFalse(JOMLUtil.equals(new Vector3f(1), 3, 4, 5, 0.000001F));
        assertFalse(JOMLUtil.equals(new Vector3d(1), 3, 4, 5, 0.000001));
        
        assertTrue(JOMLUtil.equals(new Quaternionf().lookAlong(0, 0, 1, 0, 1, 0), new Quaternionf().lookAlong(0, 0, 1, 0, 1, 0), 0.000001F));
        assertTrue(JOMLUtil.equals(new Quaterniond().lookAlong(0, 0, 1, 0, 1, 0), new Quaterniond().lookAlong(0, 0, 1, 0, 1, 0), 0.000001));
        assertFalse(JOMLUtil.equals(new Quaternionf().lookAlong(0, 0, -1, 0, 1, 0), new Quaternionf().lookAlong(0, 0, 1, 0, 1, 0), 0.000001F));
        assertFalse(JOMLUtil.equals(new Quaterniond().lookAlong(0, 0, -1, 0, 1, 0), new Quaterniond().lookAlong(0, 0, 1, 0, 1, 0), 0.000001));
    }
}

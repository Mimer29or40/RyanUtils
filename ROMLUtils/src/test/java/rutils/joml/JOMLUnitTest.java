package rutils.joml;

import org.joml.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JOMLUnitTest
{
    @Test
    void units()
    {
        assertEquals(new Vector2i(0, 0), JOMLUnit.ZERO2i);
        assertEquals(new Vector2i(1, 1), JOMLUnit.ONE2i);
        assertEquals(new Vector2i(1, 0), JOMLUnit.X2i);
        assertEquals(new Vector2i(0, 1), JOMLUnit.Y2i);
        assertEquals(new Vector2f(0, 0), JOMLUnit.ZERO2f);
        assertEquals(new Vector2f(1, 1), JOMLUnit.ONE2f);
        assertEquals(new Vector2f(1, 0), JOMLUnit.X2f);
        assertEquals(new Vector2f(0, 1), JOMLUnit.Y2f);
        assertEquals(new Vector2d(0, 0), JOMLUnit.ZERO2d);
        assertEquals(new Vector2d(1, 1), JOMLUnit.ONE2d);
        assertEquals(new Vector2d(1, 0), JOMLUnit.X2d);
        assertEquals(new Vector2d(0, 1), JOMLUnit.Y2d);
        assertEquals(new Vector3i(0, 0, 0), JOMLUnit.ZERO3i);
        assertEquals(new Vector3i(1, 1, 1), JOMLUnit.ONE3i);
        assertEquals(new Vector3i(1, 0, 0), JOMLUnit.X3i);
        assertEquals(new Vector3i(0, 1, 0), JOMLUnit.Y3i);
        assertEquals(new Vector3i(0, 0, 1), JOMLUnit.Z3i);
        assertEquals(new Vector3f(0, 0, 0), JOMLUnit.ZERO3f);
        assertEquals(new Vector3f(1, 1, 1), JOMLUnit.ONE3f);
        assertEquals(new Vector3f(1, 0, 0), JOMLUnit.X3f);
        assertEquals(new Vector3f(0, 1, 0), JOMLUnit.Y3f);
        assertEquals(new Vector3f(0, 0, 1), JOMLUnit.Z3f);
        assertEquals(new Vector3d(0, 0, 0), JOMLUnit.ZERO3d);
        assertEquals(new Vector3d(1, 1, 1), JOMLUnit.ONE3d);
        assertEquals(new Vector3d(1, 0, 0), JOMLUnit.X3d);
        assertEquals(new Vector3d(0, 1, 0), JOMLUnit.Y3d);
        assertEquals(new Vector3d(0, 0, 1), JOMLUnit.Z3d);
        assertEquals(new Vector4i(0, 0, 0, 0), JOMLUnit.ZERO4i);
        assertEquals(new Vector4i(1, 1, 1, 1), JOMLUnit.ONE4i);
        assertEquals(new Vector4i(1, 0, 0, 0), JOMLUnit.X4i);
        assertEquals(new Vector4i(0, 1, 0, 0), JOMLUnit.Y4i);
        assertEquals(new Vector4i(0, 0, 1, 0), JOMLUnit.Z4i);
        assertEquals(new Vector4i(0, 0, 0, 1), JOMLUnit.W4i);
        assertEquals(new Vector4f(0, 0, 0, 0), JOMLUnit.ZERO4f);
        assertEquals(new Vector4f(1, 1, 1, 1), JOMLUnit.ONE4f);
        assertEquals(new Vector4f(1, 0, 0, 0), JOMLUnit.X4f);
        assertEquals(new Vector4f(0, 1, 0, 0), JOMLUnit.Y4f);
        assertEquals(new Vector4f(0, 0, 1, 0), JOMLUnit.Z4f);
        assertEquals(new Vector4f(0, 0, 0, 1), JOMLUnit.W4f);
        assertEquals(new Vector4d(0, 0, 0, 0), JOMLUnit.ZERO4d);
        assertEquals(new Vector4d(1, 1, 1, 1), JOMLUnit.ONE4d);
        assertEquals(new Vector4d(1, 0, 0, 0), JOMLUnit.X4d);
        assertEquals(new Vector4d(0, 1, 0, 0), JOMLUnit.Y4d);
        assertEquals(new Vector4d(0, 0, 1, 0), JOMLUnit.Z4d);
        assertEquals(new Vector4d(0, 0, 0, 1), JOMLUnit.W4d);
    }
}

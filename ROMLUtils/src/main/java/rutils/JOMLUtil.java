package rutils;

import org.joml.Math;
import org.joml.*;

@SuppressWarnings("unused")
public class JOMLUtil
{
    private static final Vector3f WORKING3f = new Vector3f();
    private static final Vector3d WORKING3d = new Vector3d();
    
    /**
     * Gets the vector that is parallel to a plane
     *
     * @param vector      The vector to project
     * @param planeNormal The plane
     * @return Read-Only projected vector
     */
    public static Vector3fc projectToPlane(Vector3fc vector, Vector3fc planeNormal)
    {
        return projectToPlane(vector, planeNormal, JOMLUtil.WORKING3f).normalize();
    }
    
    /**
     * Gets the vector that is parallel to a plane
     *
     * @param vector      The vector to project
     * @param planeNormal The plane
     * @param dest        The projected vector
     * @return dest
     */
    public static Vector3f projectToPlane(Vector3fc vector, Vector3fc planeNormal, Vector3f dest)
    {
        float scalar = vector.dot(planeNormal) / planeNormal.lengthSquared();
        
        dest.x = vector.x() - scalar * planeNormal.x();
        dest.y = vector.y() - scalar * planeNormal.y();
        dest.z = vector.z() - scalar * planeNormal.z();
        
        return dest;
    }
    
    /**
     * Gets the vector that is parallel to a plane
     *
     * @param vector      The vector to project
     * @param planeNormal The plane
     * @return Read-Only projected vector
     */
    public static Vector3dc projectToPlane(Vector3dc vector, Vector3dc planeNormal)
    {
        return projectToPlane(vector, planeNormal, JOMLUtil.WORKING3d).normalize();
    }
    
    /**
     * Gets the vector that is parallel to a plane
     *
     * @param vector      The vector to project
     * @param planeNormal The plane
     * @param dest        The projected vector
     * @return dest
     */
    public static Vector3d projectToPlane(Vector3dc vector, Vector3dc planeNormal, Vector3d dest)
    {
        double scalar = vector.dot(planeNormal) / planeNormal.lengthSquared();
        
        dest.x = vector.x() - scalar * planeNormal.x();
        dest.y = vector.y() - scalar * planeNormal.y();
        dest.z = vector.z() - scalar * planeNormal.z();
        
        return dest;
    }
    
    /**
     * Compares a vector to a scalar vector with a delta
     *
     * @param v     The first vector
     * @param x     The second vectors x component
     * @param y     The second vectors y component
     * @param z     The second vectors z component
     * @param delta The comparison amount
     * @return <code>true</code> if vector v equals vector (x, y, z)
     */
    public static boolean equals(Vector3fc v, float x, float y, float z, float delta)
    {
        return equals(x, v.x(), delta) &&
               equals(y, v.y(), delta) &&
               equals(z, v.z(), delta);
    }
    
    /**
     * Compares a vector to a scalar vector with a delta
     *
     * @param v     The first vector
     * @param x     The second vectors x component
     * @param y     The second vectors y component
     * @param z     The second vectors z component
     * @param delta The comparison amount
     * @return <code>true</code> if vector v equals vector (x, y, z)
     */
    public static boolean equals(Vector3dc v, double x, double y, double z, double delta)
    {
        return equals(x, v.x(), delta) &&
               equals(y, v.y(), delta) &&
               equals(z, v.z(), delta);
    }
    
    /**
     * Compares two quaternions by a delta amount
     *
     * @param a     The first quaternion
     * @param b     The second quaternion
     * @param delta The amount to compare by
     * @return <code>true</code> if quaternion <code>a</code> equals quaternion <code>b</code> by amount <code>delta</code>
     */
    public static boolean equals(Quaternionfc a, Quaternionfc b, float delta)
    {
        return a.equals(b) ||
               (equals(a.x(), b.x(), delta) &&
                equals(a.y(), b.y(), delta) &&
                equals(a.z(), b.z(), delta) &&
                equals(a.w(), b.w(), delta));
    }
    
    /**
     * Compares two quaternions by a delta amount
     *
     * @param a     The first quaternion
     * @param b     The second quaternion
     * @param delta The amount to compare by
     * @return <code>true</code> if quaternion <code>a</code> equals quaternion <code>b</code> by amount <code>delta</code>
     */
    public static boolean equals(Quaterniondc a, Quaterniondc b, double delta)
    {
        return a.equals(b) ||
               (equals(a.x(), b.x(), delta) &&
                equals(a.y(), b.y(), delta) &&
                equals(a.z(), b.z(), delta) &&
                equals(a.w(), b.w(), delta));
    }
    
    private static boolean equals(float a, float b, float delta)
    {
        return Float.compare(a, b) == 0 || Math.abs(a - b) <= delta;
    }
    
    private static boolean equals(double a, double b, double delta)
    {
        return Double.compare(a, b) == 0 || Math.abs(a - b) <= delta;
    }
}

package rutils.joml;

import org.joml.*;

@SuppressWarnings("unused")
public class Random extends rutils.Random
{
    public static final rutils.Random INSTANCE = new rutils.Random();
    
    public Random()
    {
        super();
    }
    
    public Random(long seed)
    {
        super(seed);
    }
    
    /**
     * @return A random {@link Vector2i} with {@code int}'s [{@code Integer.MIN_VALUE} - {@code Integer.MAX_VALUE}].
     */
    public Vector2i nextVector2i(Vector2i vector)
    {
        return vector.set(nextInt(), nextInt());
    }
    
    /**
     * @return A random {@link Vector2i} with {@code int}'s [{@code Integer.MIN_VALUE} - {@code Integer.MAX_VALUE}].
     */
    public Vector2i nextVector2i()
    {
        return nextVector2i(new Vector2i());
    }
    
    /**
     * @return A random {@link Vector2i} with {@code int}'s [{@code 0} - {@code bound}].
     */
    public Vector2i nextVector2i(Vector2i vector, int bound)
    {
        return vector.set(nextInt(bound), nextInt(bound));
    }
    
    /**
     * @return A random {@link Vector2i} with {@code int}'s [{@code 0} - {@code bound}].
     */
    public Vector2i nextVector2i(int bound)
    {
        return nextVector2i(new Vector2i(), bound);
    }
    
    /**
     * @return A random {@link Vector2i} with {@code int}'s [{@code origin} - {@code bound}].
     */
    public Vector2i nextVector2i(Vector2i vector, int origin, int bound)
    {
        return vector.set(nextInt(origin, bound), nextInt(origin, bound));
    }
    
    /**
     * @return A random {@link Vector2i} with {@code int}'s [{@code origin} - {@code bound}].
     */
    public Vector2i nextVector2i(int origin, int bound)
    {
        return nextVector2i(new Vector2i(), origin, bound);
    }
    
    /**
     * @return A random {@link Vector3i} with {@code int}'s [{@code Integer.MIN_VALUE} - {@code Integer.MAX_VALUE}].
     */
    public Vector3i nextVector3i(Vector3i vector)
    {
        return vector.set(nextInt(), nextInt(), nextInt());
    }
    
    /**
     * @return A random {@link Vector3i} with {@code int}'s [{@code Integer.MIN_VALUE} - {@code Integer.MAX_VALUE}].
     */
    public Vector3i nextVector3i()
    {
        return nextVector3i(new Vector3i());
    }
    
    /**
     * @return A random {@link Vector3i} with {@code int}'s [{@code 0} - {@code bound}].
     */
    public Vector3i nextVector3i(Vector3i vector, int bound)
    {
        return vector.set(nextInt(bound), nextInt(bound), nextInt(bound));
    }
    
    /**
     * @return A random {@link Vector3i} with {@code int}'s [{@code 0} - {@code bound}].
     */
    public Vector3i nextVector3i(int bound)
    {
        return nextVector3i(new Vector3i(), bound);
    }
    
    /**
     * @return A random {@link Vector3i} with {@code int}'s [{@code origin} - {@code bound}].
     */
    public Vector3i nextVector3i(Vector3i vector, int origin, int bound)
    {
        return vector.set(nextInt(origin, bound), nextInt(origin, bound), nextInt(origin, bound));
    }
    
    /**
     * @return A random {@link Vector3i} with {@code int}'s [{@code origin} - {@code bound}].
     */
    public Vector3i nextVector3i(int origin, int bound)
    {
        return nextVector3i(new Vector3i(), origin, bound);
    }
    
    /**
     * @return A random {@link Vector4i} with {@code int}'s [{@code Integer.MIN_VALUE} - {@code Integer.MAX_VALUE}].
     */
    public Vector4i nextVector4i(Vector4i vector)
    {
        return vector.set(nextInt(), nextInt(), nextInt(), nextInt());
    }
    
    /**
     * @return A random {@link Vector4i} with {@code int}'s [{@code Integer.MIN_VALUE} - {@code Integer.MAX_VALUE}].
     */
    public Vector4i nextVector4i()
    {
        return nextVector4i(new Vector4i());
    }
    
    /**
     * @return A random {@link Vector4i} with {@code int}'s [{@code 0} - {@code bound}].
     */
    public Vector4i nextVector4i(Vector4i vector, int bound)
    {
        return vector.set(nextInt(bound), nextInt(bound), nextInt(bound), nextInt(bound));
    }
    
    /**
     * @return A random {@link Vector4i} with {@code int}'s [{@code 0} - {@code bound}].
     */
    public Vector4i nextVector4i(int bound)
    {
        return nextVector4i(new Vector4i(), bound);
    }
    
    /**
     * @return A random {@link Vector4i} with {@code int}'s [{@code origin} - {@code bound}].
     */
    public Vector4i nextVector4i(Vector4i vector, int origin, int bound)
    {
        return vector.set(nextInt(origin, bound), nextInt(origin, bound), nextInt(origin, bound), nextInt(origin, bound));
    }
    
    /**
     * @return A random {@link Vector4i} with {@code int}'s [{@code origin} - {@code bound}].
     */
    public Vector4i nextVector4i(int origin, int bound)
    {
        return nextVector4i(new Vector4i(), origin, bound);
    }
    
    /**
     * @return A random {@link Vector2f} with {@code float}'s [{@code 0} - {@code 1}].
     */
    public Vector2f nextVector2f(Vector2f vector)
    {
        return vector.set(nextFloat(), nextFloat());
    }
    
    /**
     * @return A random {@link Vector2f} with {@code float}'s [{@code 0} - {@code 1}].
     */
    public Vector2f nextVector2f()
    {
        return nextVector2f(new Vector2f());
    }
    
    /**
     * @return A random {@link Vector2f} with {@code float}'s [{@code 0} - {@code bound}].
     */
    public Vector2f nextVector2f(Vector2f vector, float bound)
    {
        return vector.set(nextFloat(bound), nextFloat(bound));
    }
    
    /**
     * @return A random {@link Vector2f} with {@code float}'s [{@code 0} - {@code bound}].
     */
    public Vector2f nextVector2f(float bound)
    {
        return nextVector2f(new Vector2f(), bound);
    }
    
    /**
     * @return A random {@link Vector2f} with {@code float}'s [{@code origin} - {@code bound}].
     */
    public Vector2f nextVector2f(Vector2f vector, float origin, float bound)
    {
        return vector.set(nextFloat(origin, bound), nextFloat(origin, bound));
    }
    
    /**
     * @return A random {@link Vector2f} with {@code float}'s [{@code origin} - {@code bound}].
     */
    public Vector2f nextVector2f(float origin, float bound)
    {
        return nextVector2f(new Vector2f(), origin, bound);
    }
    
    /**
     * @return A random {@link Vector3f} with {@code float}'s [{@code 0} - {@code 1}].
     */
    public Vector3f nextVector3f(Vector3f vector)
    {
        return vector.set(nextFloat(), nextFloat(), nextFloat());
    }
    
    /**
     * @return A random {@link Vector3f} with {@code float}'s [{@code 0} - {@code 1}].
     */
    public Vector3f nextVector3f()
    {
        return nextVector3f(new Vector3f());
    }
    
    /**
     * @return A random {@link Vector3f} with {@code float}'s [{@code 0} - {@code bound}].
     */
    public Vector3f nextVector3f(Vector3f vector, float bound)
    {
        return vector.set(nextFloat(bound), nextFloat(bound), nextFloat(bound));
    }
    
    /**
     * @return A random {@link Vector3f} with {@code float}'s [{@code 0} - {@code bound}].
     */
    public Vector3f nextVector3f(float bound)
    {
        return nextVector3f(new Vector3f(), bound);
    }
    
    /**
     * @return A random {@link Vector3f} with {@code float}'s [{@code origin} - {@code bound}].
     */
    public Vector3f nextVector3f(Vector3f vector, float origin, float bound)
    {
        return vector.set(nextFloat(origin, bound), nextFloat(origin, bound), nextFloat(origin, bound));
    }
    
    /**
     * @return A random {@link Vector3f} with {@code float}'s [{@code origin} - {@code bound}].
     */
    public Vector3f nextVector3f(float origin, float bound)
    {
        return nextVector3f(new Vector3f(), origin, bound);
    }
    
    /**
     * @return A random {@link Vector4f} with {@code float}'s [{@code 0} - {@code 1}].
     */
    public Vector4f nextVector4f(Vector4f vector)
    {
        return vector.set(nextFloat(), nextFloat(), nextFloat(), nextFloat());
    }
    
    /**
     * @return A random {@link Vector4f} with {@code float}'s [{@code 0} - {@code 1}].
     */
    public Vector4f nextVector4f()
    {
        return nextVector4f(new Vector4f());
    }
    
    /**
     * @return A random {@link Vector4f} with {@code float}'s [{@code 0} - {@code bound}].
     */
    public Vector4f nextVector4f(Vector4f vector, float bound)
    {
        return vector.set(nextFloat(bound), nextFloat(bound), nextFloat(bound), nextFloat(bound));
    }
    
    /**
     * @return A random {@link Vector4f} with {@code float}'s [{@code 0} - {@code bound}].
     */
    public Vector4f nextVector4f(float bound)
    {
        return nextVector4f(new Vector4f(), bound);
    }
    
    /**
     * @return A random {@link Vector4f} with {@code float}'s [{@code origin} - {@code bound}].
     */
    public Vector4f nextVector4f(Vector4f vector, float origin, float bound)
    {
        return vector.set(nextFloat(origin, bound), nextFloat(origin, bound), nextFloat(origin, bound), nextFloat(origin, bound));
    }
    
    /**
     * @return A random {@link Vector4f} with {@code float}'s [{@code origin} - {@code bound}].
     */
    public Vector4f nextVector4f(float origin, float bound)
    {
        return nextVector4f(new Vector4f(), origin, bound);
    }
    
    /**
     * @return A random {@link Vector2d} with {@code double}'s [{@code 0} - {@code 1}].
     */
    public Vector2d nextVector2d(Vector2d vector)
    {
        return vector.set(nextDouble(), nextDouble());
    }
    
    /**
     * @return A random {@link Vector2d} with {@code double}'s [{@code 0} - {@code 1}].
     */
    public Vector2d nextVector2d()
    {
        return nextVector2d(new Vector2d());
    }
    
    /**
     * @return A random {@link Vector2d} with {@code double}'s [{@code 0} - {@code bound}].
     */
    public Vector2d nextVector2d(Vector2d vector, float bound)
    {
        return vector.set(nextDouble(bound), nextDouble(bound));
    }
    
    /**
     * @return A random {@link Vector2d} with {@code double}'s [{@code 0} - {@code bound}].
     */
    public Vector2d nextVector2d(float bound)
    {
        return nextVector2d(new Vector2d(), bound);
    }
    
    /**
     * @return A random {@link Vector2d} with {@code double}'s [{@code origin} - {@code bound}].
     */
    public Vector2d nextVector2d(Vector2d vector, float origin, float bound)
    {
        return vector.set(nextDouble(origin, bound), nextDouble(origin, bound));
    }
    
    /**
     * @return A random {@link Vector2d} with {@code double}'s [{@code origin} - {@code bound}].
     */
    public Vector2d nextVector2d(float origin, float bound)
    {
        return nextVector2d(new Vector2d() ,origin, bound);
    }
    
    /**
     * @return A random {@link Vector3d} with {@code double}'s [{@code 0} - {@code 1}].
     */
    public Vector3d nextVector3d(Vector3d vector)
    {
        return vector.set(nextDouble(), nextDouble(), nextDouble());
    }
    
    /**
     * @return A random {@link Vector3d} with {@code double}'s [{@code 0} - {@code 1}].
     */
    public Vector3d nextVector3d()
    {
        return nextVector3d(new Vector3d());
    }
    
    /**
     * @return A random {@link Vector3d} with {@code double}'s [{@code 0} - {@code bound}].
     */
    public Vector3d nextVector3d(Vector3d vector, float bound)
    {
        return vector.set(nextDouble(bound), nextDouble(bound), nextDouble(bound));
    }
    
    /**
     * @return A random {@link Vector3d} with {@code double}'s [{@code 0} - {@code bound}].
     */
    public Vector3d nextVector3d(float bound)
    {
        return nextVector3d(new Vector3d(), bound);
    }
    
    /**
     * @return A random {@link Vector3d} with {@code double}'s [{@code origin} - {@code bound}].
     */
    public Vector3d nextVector3d(Vector3d vector, float origin, float bound)
    {
        return vector.set(nextDouble(origin, bound), nextDouble(origin, bound), nextDouble(origin, bound));
    }
    
    /**
     * @return A random {@link Vector3d} with {@code double}'s [{@code origin} - {@code bound}].
     */
    public Vector3d nextVector3d(float origin, float bound)
    {
        return nextVector3d(new Vector3d(), origin, bound);
    }
    
    /**
     * @return A random {@link Vector4d} with {@code double}'s [{@code 0} - {@code 1}].
     */
    public Vector4d nextVector4d(Vector4d vector)
    {
        return vector.set(nextDouble(), nextDouble(), nextDouble(), nextDouble());
    }
    
    /**
     * @return A random {@link Vector4d} with {@code double}'s [{@code 0} - {@code 1}].
     */
    public Vector4d nextVector4d()
    {
        return nextVector4d(new Vector4d());
    }
    
    /**
     * @return A random {@link Vector4d} with {@code double}'s [{@code 0} - {@code bound}].
     */
    public Vector4d nextVector4d(Vector4d vector, double bound)
    {
        return vector.set(nextDouble(bound), nextDouble(bound), nextDouble(bound), nextDouble(bound));
    }
    
    /**
     * @return A random {@link Vector4d} with {@code double}'s [{@code 0} - {@code bound}].
     */
    public Vector4d nextVector4d(double bound)
    {
        return nextVector4d(new Vector4d(), bound);
    }
    
    /**
     * @return A random {@link Vector4d} with {@code double}'s [{@code origin} - {@code bound}].
     */
    public Vector4d nextVector4d(Vector4d vector, double origin, double bound)
    {
        return vector.set(nextDouble(origin, bound), nextDouble(origin, bound), nextDouble(origin, bound), nextDouble(origin, bound));
    }
    
    /**
     * @return A random {@link Vector4d} with {@code double}'s [{@code origin} - {@code bound}].
     */
    public Vector4d nextVector4d(double origin, double bound)
    {
        return nextVector4d(new Vector4d(), origin, bound);
    }
    
    /**
     * @return A random unit {@link Vector2f}
     */
    public Vector2f nextUnit2f(Vector2f vector)
    {
        return vector.set(nextFloatDir(), nextFloatDir()).normalize();
    }
    
    /**
     * @return A random unit {@link Vector2f}
     */
    public Vector2f nextUnit2f()
    {
        return nextUnit2f(new Vector2f());
    }
    
    /**
     * @return A random unit {@link Vector3f}
     */
    public Vector3f nextUnit3f(Vector3f vector)
    {
        return vector.set(nextFloatDir(), nextFloatDir(), nextFloatDir()).normalize();
    }
    
    /**
     * @return A random unit {@link Vector3f}
     */
    public Vector3f nextUnit3f()
    {
        return nextUnit3f(new Vector3f());
    }
    
    /**
     * @return A random unit {@link Vector4f}
     */
    public Vector4f nextUnit4f(Vector4f vector)
    {
        return vector.set(nextFloatDir(), nextFloatDir(), nextFloatDir(), nextFloatDir()).normalize();
    }
    
    /**
     * @return A random unit {@link Vector4f}
     */
    public Vector4f nextUnit4f()
    {
        return nextUnit4f(new Vector4f());
    }
    
    /**
     * @return A random unit {@link Vector2d}
     */
    public Vector2d nextUnit2d(Vector2d vector)
    {
        return vector.set(nextDoubleDir(), nextDoubleDir()).normalize();
    }
    
    /**
     * @return A random unit {@link Vector2d}
     */
    public Vector2d nextUnit2d()
    {
        return nextUnit2d(new Vector2d());
    }
    
    /**
     * @return A random unit {@link Vector3d}
     */
    public Vector3d nextUnit3d(Vector3d vector)
    {
        return vector.set(nextDoubleDir(), nextDoubleDir(), nextDoubleDir()).normalize();
    }
    
    /**
     * @return A random unit {@link Vector3d}
     */
    public Vector3d nextUnit3d()
    {
        return nextUnit3d(new Vector3d());
    }
    
    /**
     * @return A random unit {@link Vector4d}
     */
    public Vector4d nextUnit4d(Vector4d vector)
    {
        return vector.set(nextDoubleDir(), nextDoubleDir(), nextDoubleDir(), nextDoubleDir()).normalize();
    }
    
    /**
     * @return A random unit {@link Vector4d}
     */
    public Vector4d nextUnit4d()
    {
        return nextUnit4d(new Vector4d());
    }
}

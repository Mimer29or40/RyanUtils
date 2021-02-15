package rutils.noise;

import java.util.concurrent.atomic.AtomicLong;

public class Noise
{
    public enum Type
    {
        OPEN_SIMPLEX_2,
        OPEN_SIMPLEX_2S,
        CELLULAR,
        PERLIN,
        VALUE_CUBIC,
        VALUE
    }
    
    public enum Rotation
    {
        NONE,
        IMPROVE_XY,
        IMPROVE_XZ
    }
    
    public enum Fractal
    {
        NONE,
        FBM,
        RIDGED,
        PING_PONG,
        DOMAIN_WARP_PROGRESSIVE,
        DOMAIN_WARP_INDEPENDENT
    }
    
    public enum DistanceFunction
    {
        EUCLIDEAN,
        EUCLIDEAN_SQ,
        MANHATTAN,
        HYBRID
    }
    
    public enum Return
    {
        CELL_VALUE,
        DISTANCE,
        DISTANCE_SQ,
        DISTANCE_SQ_ADD,
        DISTANCE_SQ_SUB,
        DISTANCE_SQ_MUL,
        DISTANCE_SQ_DIV
    }
    
    public enum DomainWarp
    {
        OPEN_SIMPLEX_2,
        OPEN_SIMPLEX_2_REDUCED,
        BASIC_GRID
    }
    
    private enum Transform
    {
        NONE,
        IMPROVE_XY,
        IMPROVE_XZ,
        DEFAULT_OPEN_SIMPLEX_2
    }
    
    // -------------------- Properties (All NoiseTypes) -------------------- //
    
    private int   seed;
    private double frequency = 0.01;
    private Type   type      = Type.OPEN_SIMPLEX_2;
    // private Rotation rotation  = Rotation.NONE;
    //
    // private Transform transform = Transform.DEFAULT_OPEN_SIMPLEX_2;
    
    // -------------------- Properties (Fractal) -------------------- //
    
    // private Fractal fractal          = Fractal.NONE;
    // private int     octaves          = 3;
    // private double  lacunarity       = 2.0;
    // private double  gain             = 0.5;
    // private double  weightedStrength = 0.0;
    // private double  pingPongStrength = 2.0;
    //
    // private double fractalBounding = 1.0 / 1.75;
    
    // -------------------- Properties (Cellular) -------------------- //
    
    // private DistanceFunction distanceFunction = DistanceFunction.EUCLIDEAN_SQ;
    // private Return           returnType       = Return.DISTANCE;
    // private double           jitterModifier   = 1.0;
    
    // -------------------- Properties (Domain Warp) -------------------- //
    
    // private DomainWarp warpType      = DomainWarp.OPEN_SIMPLEX_2;
    // private double     warpAmplitude = 1.0;
    //
    // private Transform warpTransform = Transform.DEFAULT_OPEN_SIMPLEX_2;
    
    public Noise(int seed)
    {
        this.seed = seed;
    }
    
    public Noise()
    {
        long seed = seedUniquifier() ^ System.nanoTime();
        this.seed = (int) (seed >>> 32) ^ (int) seed;
    }
    
    // -------------------- Properties (All NoiseTypes) -------------------- //
    
    /**
     * Sets the seed used for all noise types.
     *
     * @param seed The new seed.
     */
    public void seed(int seed)
    {
        this.seed = seed;
    }
    
    /**
     * Sets the frequency used for all noise types.
     *
     * @param frequency The new frequency. Default: {@code 0.01}
     */
    public void frequency(double frequency)
    {
        this.frequency = frequency;
    }
    
    /**
     * Sets the current noise algorithm for {@link #get}
     *
     * @param type The new noise type. Default: {@link Type#OPEN_SIMPLEX_2 OPEN_SIMPLEX_2}
     */
    public void type(Type type)
    {
        this.type = type;
    }
    
    public double get(double x)
    {
        x *= this.frequency;
    
        return get(this.seed, x);
    }
    
    public double get(double x, double y)
    {
        x *= this.frequency;
        y *= this.frequency;
    
        return get(this.seed, x, y);
    }
    
    public double get(double x, double y, double z)
    {
        x *= this.frequency;
        y *= this.frequency;
        z *= this.frequency;
    
        return get(this.seed, x, y, z);
    }
    
    public double get(double x, double y, double z, double w)
    {
        x *= this.frequency;
        y *= this.frequency;
        z *= this.frequency;
        w *= this.frequency;
    
        return get(this.seed, x, y, z, w);
    }
    
    private double get(int seed, double x)
    {
        return switch (this.type)
                {
                    // case OPEN_SIMPLEX_2 -> singleSimplex(seed, x);
                    // case OPEN_SIMPLEX_2S -> singleOpenSimplex2S(seed, x);
                    // case CELLULAR -> singleCellular(seed, x);
                    // case PERLIN -> singlePerlin(seed, x);
                    case VALUE_CUBIC -> singleValueCubic(seed, x);
                    case VALUE -> getValue(seed, x);
                    default -> 0.0;
                };
    }
    
    private double get(int seed, double x, double y)
    {
        return switch (this.type)
                {
                    // case OPEN_SIMPLEX_2 -> singleSimplex(seed, x, y);
                    // case OPEN_SIMPLEX_2S -> singleOpenSimplex2S(seed, x, y);
                    // case CELLULAR -> singleCellular(seed, x, y);
                    // case PERLIN -> singlePerlin(seed, x, y);
                    case VALUE_CUBIC -> singleValueCubic(seed, x, y);
                    case VALUE -> getValue(seed, x, y);
                    default -> 0.0;
                };
    }
    
    private double get(int seed, double x, double y, double z)
    {
        return switch (this.type)
                {
                    // case OPEN_SIMPLEX_2 -> singleOpenSimplex2(seed, x, y, z);
                    // case OPEN_SIMPLEX_2S -> singleOpenSimplex2S(seed, x, y, z);
                    // case CELLULAR -> singleCellular(seed, x, y, z);
                    // case PERLIN -> singlePerlin(seed, x, y, z);
                    case VALUE_CUBIC -> singleValueCubic(seed, x, y, z);
                    case VALUE -> getValue(seed, x, y, z);
                    default -> 0.0;
                };
    }
    
    private double get(int seed, double x, double y, double z, double w)
    {
        return switch (this.type)
                {
                    // case OPEN_SIMPLEX_2 -> singleOpenSimplex2(seed, x, y, z, w);
                    // case OPEN_SIMPLEX_2S -> singleOpenSimplex2S(seed, x, y, z, w);
                    // case CELLULAR -> singleCellular(seed, x, y, z, w);
                    // case PERLIN -> singlePerlin(seed, x, y, z, w);
                    case VALUE_CUBIC -> singleValueCubic(seed, x, y, z, w);
                    case VALUE -> getValue(seed, x, y, z, w);
                    default -> 0.0;
                };
    }
    
    // -------------------- Value Cubic Functions -------------------- //
    
    private double singleValueCubic(int seed, double x)
    {
        int x1 = floor(x);
        
        double xs = x - x1;
        
        int x0 = (x1 *= PRIME_X) - PRIME_X;
        int x2 = x1 + PRIME_X;
        int x3 = x1 + (PRIME_X << 1);
        
        return lerpCubic(valueCoord(seed, x0), valueCoord(seed, x1), valueCoord(seed, x2), valueCoord(seed, x3), xs) * (1.0 / (1.5));
    }
    
    private double singleValueCubic(int seed, double x, double y)
    {
        int x1 = floor(x);
        int y1 = floor(y);
        
        double xs = x - x1;
        double ys = y - y1;
        
        int x0 = (x1 *= PRIME_X) - PRIME_X;
        int y0 = (y1 *= PRIME_Y) - PRIME_Y;
        int x2 = x1 + PRIME_X;
        int y2 = y1 + PRIME_Y;
        int x3 = x1 + (PRIME_X << 1);
        int y3 = y1 + (PRIME_Y << 1);
    
        double lx_y0 = lerpCubic(valueCoord(seed, x0, y0), valueCoord(seed, x1, y0), valueCoord(seed, x2, y0), valueCoord(seed, x3, y0), xs);
        double lx_y1 = lerpCubic(valueCoord(seed, x0, y1), valueCoord(seed, x1, y1), valueCoord(seed, x2, y1), valueCoord(seed, x3, y1), xs);
        double lx_y2 = lerpCubic(valueCoord(seed, x0, y2), valueCoord(seed, x1, y2), valueCoord(seed, x2, y2), valueCoord(seed, x3, y2), xs);
        double lx_y3 = lerpCubic(valueCoord(seed, x0, y3), valueCoord(seed, x1, y3), valueCoord(seed, x2, y3), valueCoord(seed, x3, y3), xs);
        
        return lerpCubic(lx_y0, lx_y1, lx_y2, lx_y3, ys) * (1.0 / (1.5 * 1.5));
    }
    
    private double singleValueCubic(int seed, double x, double y, double z)
    {
        int x1 = floor(x);
        int y1 = floor(y);
        int z1 = floor(z);
        
        double xs = x - x1;
        double ys = y - y1;
        double zs = z - z1;
        
        int x0 = (x1 *= PRIME_X) - PRIME_X;
        int y0 = (y1 *= PRIME_Y) - PRIME_Y;
        int z0 = (z1 *= PRIME_Z) - PRIME_Z;
        int x2 = x1 + PRIME_X;
        int y2 = y1 + PRIME_Y;
        int z2 = z1 + PRIME_Z;
        int x3 = x1 + (PRIME_X << 1);
        int y3 = y1 + (PRIME_Y << 1);
        int z3 = z1 + (PRIME_Z << 1);
    
        double lx_y0_z0 = lerpCubic(valueCoord(seed, x0, y0, z0), valueCoord(seed, x1, y0, z0), valueCoord(seed, x2, y0, z0), valueCoord(seed, x3, y0, z0), xs);
        double lx_y1_z0 = lerpCubic(valueCoord(seed, x0, y1, z0), valueCoord(seed, x1, y1, z0), valueCoord(seed, x2, y1, z0), valueCoord(seed, x3, y1, z0), xs);
        double lx_y2_z0 = lerpCubic(valueCoord(seed, x0, y2, z0), valueCoord(seed, x1, y2, z0), valueCoord(seed, x2, y2, z0), valueCoord(seed, x3, y2, z0), xs);
        double lx_y3_z0 = lerpCubic(valueCoord(seed, x0, y3, z0), valueCoord(seed, x1, y3, z0), valueCoord(seed, x2, y3, z0), valueCoord(seed, x3, y3, z0), xs);
        double lx_y0_z1 = lerpCubic(valueCoord(seed, x0, y0, z1), valueCoord(seed, x1, y0, z1), valueCoord(seed, x2, y0, z1), valueCoord(seed, x3, y0, z1), xs);
        double lx_y1_z1 = lerpCubic(valueCoord(seed, x0, y1, z1), valueCoord(seed, x1, y1, z1), valueCoord(seed, x2, y1, z1), valueCoord(seed, x3, y1, z1), xs);
        double lx_y2_z1 = lerpCubic(valueCoord(seed, x0, y2, z1), valueCoord(seed, x1, y2, z1), valueCoord(seed, x2, y2, z1), valueCoord(seed, x3, y2, z1), xs);
        double lx_y3_z1 = lerpCubic(valueCoord(seed, x0, y3, z1), valueCoord(seed, x1, y3, z1), valueCoord(seed, x2, y3, z1), valueCoord(seed, x3, y3, z1), xs);
        double lx_y0_z2 = lerpCubic(valueCoord(seed, x0, y0, z2), valueCoord(seed, x1, y0, z2), valueCoord(seed, x2, y0, z2), valueCoord(seed, x3, y0, z2), xs);
        double lx_y1_z2 = lerpCubic(valueCoord(seed, x0, y1, z2), valueCoord(seed, x1, y1, z2), valueCoord(seed, x2, y1, z2), valueCoord(seed, x3, y1, z2), xs);
        double lx_y2_z2 = lerpCubic(valueCoord(seed, x0, y2, z2), valueCoord(seed, x1, y2, z2), valueCoord(seed, x2, y2, z2), valueCoord(seed, x3, y2, z2), xs);
        double lx_y3_z2 = lerpCubic(valueCoord(seed, x0, y3, z2), valueCoord(seed, x1, y3, z2), valueCoord(seed, x2, y3, z2), valueCoord(seed, x3, y3, z2), xs);
        double lx_y0_z3 = lerpCubic(valueCoord(seed, x0, y0, z3), valueCoord(seed, x1, y0, z3), valueCoord(seed, x2, y0, z3), valueCoord(seed, x3, y0, z3), xs);
        double lx_y1_z3 = lerpCubic(valueCoord(seed, x0, y1, z3), valueCoord(seed, x1, y1, z3), valueCoord(seed, x2, y1, z3), valueCoord(seed, x3, y1, z3), xs);
        double lx_y2_z3 = lerpCubic(valueCoord(seed, x0, y2, z3), valueCoord(seed, x1, y2, z3), valueCoord(seed, x2, y2, z3), valueCoord(seed, x3, y2, z3), xs);
        double lx_y3_z3 = lerpCubic(valueCoord(seed, x0, y3, z3), valueCoord(seed, x1, y3, z3), valueCoord(seed, x2, y3, z3), valueCoord(seed, x3, y3, z3), xs);
    
        double ly_z0 = lerpCubic(lx_y0_z0, lx_y1_z0, lx_y2_z0, lx_y3_z0, ys);
        double ly_z1 = lerpCubic(lx_y0_z1, lx_y1_z1, lx_y2_z1, lx_y3_z1, ys);
        double ly_z2 = lerpCubic(lx_y0_z2, lx_y1_z2, lx_y2_z2, lx_y3_z2, ys);
        double ly_z3 = lerpCubic(lx_y0_z3, lx_y1_z3, lx_y2_z3, lx_y3_z3, ys);
    
        return lerpCubic(ly_z0, ly_z1, ly_z2, ly_z3, zs) * (1.0 / (1.5 * 1.5 * 1.5));
    }
    
    private double singleValueCubic(int seed, double x, double y, double z, double w)
    {
        int x1 = floor(x);
        int y1 = floor(y);
        int z1 = floor(z);
        int w1 = floor(w);
        
        double xs = x - x1;
        double ys = y - y1;
        double zs = z - z1;
        double ws = w - w1;
        
        int x0 = (x1 *= PRIME_X) - PRIME_X;
        int y0 = (y1 *= PRIME_Y) - PRIME_Y;
        int z0 = (z1 *= PRIME_Z) - PRIME_Z;
        int w0 = (w1 *= PRIME_W) - PRIME_W;
        int x2 = x1 + PRIME_X;
        int y2 = y1 + PRIME_Y;
        int z2 = z1 + PRIME_Z;
        int w2 = w1 + PRIME_W;
        int x3 = x1 + (PRIME_X << 1);
        int y3 = y1 + (PRIME_Y << 1);
        int z3 = z1 + (PRIME_Z << 1);
        int w3 = w1 + (PRIME_W << 1);
    
        double lx_y0_z0_w0 = lerpCubic(valueCoord(seed, x0, y0, z0, w0), valueCoord(seed, x1, y0, z0, w0), valueCoord(seed, x2, y0, z0, w0), valueCoord(seed, x3, y0, z0, w0), xs);
        double lx_y1_z0_w0 = lerpCubic(valueCoord(seed, x0, y1, z0, w0), valueCoord(seed, x1, y1, z0, w0), valueCoord(seed, x2, y1, z0, w0), valueCoord(seed, x3, y1, z0, w0), xs);
        double lx_y2_z0_w0 = lerpCubic(valueCoord(seed, x0, y2, z0, w0), valueCoord(seed, x1, y2, z0, w0), valueCoord(seed, x2, y2, z0, w0), valueCoord(seed, x3, y2, z0, w0), xs);
        double lx_y3_z0_w0 = lerpCubic(valueCoord(seed, x0, y3, z0, w0), valueCoord(seed, x1, y3, z0, w0), valueCoord(seed, x2, y3, z0, w0), valueCoord(seed, x3, y3, z0, w0), xs);
        double lx_y0_z1_w0 = lerpCubic(valueCoord(seed, x0, y0, z1, w0), valueCoord(seed, x1, y0, z1, w0), valueCoord(seed, x2, y0, z1, w0), valueCoord(seed, x3, y0, z1, w0), xs);
        double lx_y1_z1_w0 = lerpCubic(valueCoord(seed, x0, y1, z1, w0), valueCoord(seed, x1, y1, z1, w0), valueCoord(seed, x2, y1, z1, w0), valueCoord(seed, x3, y1, z1, w0), xs);
        double lx_y2_z1_w0 = lerpCubic(valueCoord(seed, x0, y2, z1, w0), valueCoord(seed, x1, y2, z1, w0), valueCoord(seed, x2, y2, z1, w0), valueCoord(seed, x3, y2, z1, w0), xs);
        double lx_y3_z1_w0 = lerpCubic(valueCoord(seed, x0, y3, z1, w0), valueCoord(seed, x1, y3, z1, w0), valueCoord(seed, x2, y3, z1, w0), valueCoord(seed, x3, y3, z1, w0), xs);
        double lx_y0_z2_w0 = lerpCubic(valueCoord(seed, x0, y0, z2, w0), valueCoord(seed, x1, y0, z2, w0), valueCoord(seed, x2, y0, z2, w0), valueCoord(seed, x3, y0, z2, w0), xs);
        double lx_y1_z2_w0 = lerpCubic(valueCoord(seed, x0, y1, z2, w0), valueCoord(seed, x1, y1, z2, w0), valueCoord(seed, x2, y1, z2, w0), valueCoord(seed, x3, y1, z2, w0), xs);
        double lx_y2_z2_w0 = lerpCubic(valueCoord(seed, x0, y2, z2, w0), valueCoord(seed, x1, y2, z2, w0), valueCoord(seed, x2, y2, z2, w0), valueCoord(seed, x3, y2, z2, w0), xs);
        double lx_y3_z2_w0 = lerpCubic(valueCoord(seed, x0, y3, z2, w0), valueCoord(seed, x1, y3, z2, w0), valueCoord(seed, x2, y3, z2, w0), valueCoord(seed, x3, y3, z2, w0), xs);
        double lx_y0_z3_w0 = lerpCubic(valueCoord(seed, x0, y0, z3, w0), valueCoord(seed, x1, y0, z3, w0), valueCoord(seed, x2, y0, z3, w0), valueCoord(seed, x3, y0, z3, w0), xs);
        double lx_y1_z3_w0 = lerpCubic(valueCoord(seed, x0, y1, z3, w0), valueCoord(seed, x1, y1, z3, w0), valueCoord(seed, x2, y1, z3, w0), valueCoord(seed, x3, y1, z3, w0), xs);
        double lx_y2_z3_w0 = lerpCubic(valueCoord(seed, x0, y2, z3, w0), valueCoord(seed, x1, y2, z3, w0), valueCoord(seed, x2, y2, z3, w0), valueCoord(seed, x3, y2, z3, w0), xs);
        double lx_y3_z3_w0 = lerpCubic(valueCoord(seed, x0, y3, z3, w0), valueCoord(seed, x1, y3, z3, w0), valueCoord(seed, x2, y3, z3, w0), valueCoord(seed, x3, y3, z3, w0), xs);
        double lx_y0_z0_w1 = lerpCubic(valueCoord(seed, x0, y0, z0, w1), valueCoord(seed, x1, y0, z0, w1), valueCoord(seed, x2, y0, z0, w1), valueCoord(seed, x3, y0, z0, w1), xs);
        double lx_y1_z0_w1 = lerpCubic(valueCoord(seed, x0, y1, z0, w1), valueCoord(seed, x1, y1, z0, w1), valueCoord(seed, x2, y1, z0, w1), valueCoord(seed, x3, y1, z0, w1), xs);
        double lx_y2_z0_w1 = lerpCubic(valueCoord(seed, x0, y2, z0, w1), valueCoord(seed, x1, y2, z0, w1), valueCoord(seed, x2, y2, z0, w1), valueCoord(seed, x3, y2, z0, w1), xs);
        double lx_y3_z0_w1 = lerpCubic(valueCoord(seed, x0, y3, z0, w1), valueCoord(seed, x1, y3, z0, w1), valueCoord(seed, x2, y3, z0, w1), valueCoord(seed, x3, y3, z0, w1), xs);
        double lx_y0_z1_w1 = lerpCubic(valueCoord(seed, x0, y0, z1, w1), valueCoord(seed, x1, y0, z1, w1), valueCoord(seed, x2, y0, z1, w1), valueCoord(seed, x3, y0, z1, w1), xs);
        double lx_y1_z1_w1 = lerpCubic(valueCoord(seed, x0, y1, z1, w1), valueCoord(seed, x1, y1, z1, w1), valueCoord(seed, x2, y1, z1, w1), valueCoord(seed, x3, y1, z1, w1), xs);
        double lx_y2_z1_w1 = lerpCubic(valueCoord(seed, x0, y2, z1, w1), valueCoord(seed, x1, y2, z1, w1), valueCoord(seed, x2, y2, z1, w1), valueCoord(seed, x3, y2, z1, w1), xs);
        double lx_y3_z1_w1 = lerpCubic(valueCoord(seed, x0, y3, z1, w1), valueCoord(seed, x1, y3, z1, w1), valueCoord(seed, x2, y3, z1, w1), valueCoord(seed, x3, y3, z1, w1), xs);
        double lx_y0_z2_w1 = lerpCubic(valueCoord(seed, x0, y0, z2, w1), valueCoord(seed, x1, y0, z2, w1), valueCoord(seed, x2, y0, z2, w1), valueCoord(seed, x3, y0, z2, w1), xs);
        double lx_y1_z2_w1 = lerpCubic(valueCoord(seed, x0, y1, z2, w1), valueCoord(seed, x1, y1, z2, w1), valueCoord(seed, x2, y1, z2, w1), valueCoord(seed, x3, y1, z2, w1), xs);
        double lx_y2_z2_w1 = lerpCubic(valueCoord(seed, x0, y2, z2, w1), valueCoord(seed, x1, y2, z2, w1), valueCoord(seed, x2, y2, z2, w1), valueCoord(seed, x3, y2, z2, w1), xs);
        double lx_y3_z2_w1 = lerpCubic(valueCoord(seed, x0, y3, z2, w1), valueCoord(seed, x1, y3, z2, w1), valueCoord(seed, x2, y3, z2, w1), valueCoord(seed, x3, y3, z2, w1), xs);
        double lx_y0_z3_w1 = lerpCubic(valueCoord(seed, x0, y0, z3, w1), valueCoord(seed, x1, y0, z3, w1), valueCoord(seed, x2, y0, z3, w1), valueCoord(seed, x3, y0, z3, w1), xs);
        double lx_y1_z3_w1 = lerpCubic(valueCoord(seed, x0, y1, z3, w1), valueCoord(seed, x1, y1, z3, w1), valueCoord(seed, x2, y1, z3, w1), valueCoord(seed, x3, y1, z3, w1), xs);
        double lx_y2_z3_w1 = lerpCubic(valueCoord(seed, x0, y2, z3, w1), valueCoord(seed, x1, y2, z3, w1), valueCoord(seed, x2, y2, z3, w1), valueCoord(seed, x3, y2, z3, w1), xs);
        double lx_y3_z3_w1 = lerpCubic(valueCoord(seed, x0, y3, z3, w1), valueCoord(seed, x1, y3, z3, w1), valueCoord(seed, x2, y3, z3, w1), valueCoord(seed, x3, y3, z3, w1), xs);
        double lx_y0_z0_w2 = lerpCubic(valueCoord(seed, x0, y0, z0, w2), valueCoord(seed, x1, y0, z0, w2), valueCoord(seed, x2, y0, z0, w2), valueCoord(seed, x3, y0, z0, w2), xs);
        double lx_y1_z0_w2 = lerpCubic(valueCoord(seed, x0, y1, z0, w2), valueCoord(seed, x1, y1, z0, w2), valueCoord(seed, x2, y1, z0, w2), valueCoord(seed, x3, y1, z0, w2), xs);
        double lx_y2_z0_w2 = lerpCubic(valueCoord(seed, x0, y2, z0, w2), valueCoord(seed, x1, y2, z0, w2), valueCoord(seed, x2, y2, z0, w2), valueCoord(seed, x3, y2, z0, w2), xs);
        double lx_y3_z0_w2 = lerpCubic(valueCoord(seed, x0, y3, z0, w2), valueCoord(seed, x1, y3, z0, w2), valueCoord(seed, x2, y3, z0, w2), valueCoord(seed, x3, y3, z0, w2), xs);
        double lx_y0_z1_w2 = lerpCubic(valueCoord(seed, x0, y0, z1, w2), valueCoord(seed, x1, y0, z1, w2), valueCoord(seed, x2, y0, z1, w2), valueCoord(seed, x3, y0, z1, w2), xs);
        double lx_y1_z1_w2 = lerpCubic(valueCoord(seed, x0, y1, z1, w2), valueCoord(seed, x1, y1, z1, w2), valueCoord(seed, x2, y1, z1, w2), valueCoord(seed, x3, y1, z1, w2), xs);
        double lx_y2_z1_w2 = lerpCubic(valueCoord(seed, x0, y2, z1, w2), valueCoord(seed, x1, y2, z1, w2), valueCoord(seed, x2, y2, z1, w2), valueCoord(seed, x3, y2, z1, w2), xs);
        double lx_y3_z1_w2 = lerpCubic(valueCoord(seed, x0, y3, z1, w2), valueCoord(seed, x1, y3, z1, w2), valueCoord(seed, x2, y3, z1, w2), valueCoord(seed, x3, y3, z1, w2), xs);
        double lx_y0_z2_w2 = lerpCubic(valueCoord(seed, x0, y0, z2, w2), valueCoord(seed, x1, y0, z2, w2), valueCoord(seed, x2, y0, z2, w2), valueCoord(seed, x3, y0, z2, w2), xs);
        double lx_y1_z2_w2 = lerpCubic(valueCoord(seed, x0, y1, z2, w2), valueCoord(seed, x1, y1, z2, w2), valueCoord(seed, x2, y1, z2, w2), valueCoord(seed, x3, y1, z2, w2), xs);
        double lx_y2_z2_w2 = lerpCubic(valueCoord(seed, x0, y2, z2, w2), valueCoord(seed, x1, y2, z2, w2), valueCoord(seed, x2, y2, z2, w2), valueCoord(seed, x3, y2, z2, w2), xs);
        double lx_y3_z2_w2 = lerpCubic(valueCoord(seed, x0, y3, z2, w2), valueCoord(seed, x1, y3, z2, w2), valueCoord(seed, x2, y3, z2, w2), valueCoord(seed, x3, y3, z2, w2), xs);
        double lx_y0_z3_w2 = lerpCubic(valueCoord(seed, x0, y0, z3, w2), valueCoord(seed, x1, y0, z3, w2), valueCoord(seed, x2, y0, z3, w2), valueCoord(seed, x3, y0, z3, w2), xs);
        double lx_y1_z3_w2 = lerpCubic(valueCoord(seed, x0, y1, z3, w2), valueCoord(seed, x1, y1, z3, w2), valueCoord(seed, x2, y1, z3, w2), valueCoord(seed, x3, y1, z3, w2), xs);
        double lx_y2_z3_w2 = lerpCubic(valueCoord(seed, x0, y2, z3, w2), valueCoord(seed, x1, y2, z3, w2), valueCoord(seed, x2, y2, z3, w2), valueCoord(seed, x3, y2, z3, w2), xs);
        double lx_y3_z3_w2 = lerpCubic(valueCoord(seed, x0, y3, z3, w2), valueCoord(seed, x1, y3, z3, w2), valueCoord(seed, x2, y3, z3, w2), valueCoord(seed, x3, y3, z3, w2), xs);
        double lx_y0_z0_w3 = lerpCubic(valueCoord(seed, x0, y0, z0, w3), valueCoord(seed, x1, y0, z0, w3), valueCoord(seed, x2, y0, z0, w3), valueCoord(seed, x3, y0, z0, w3), xs);
        double lx_y1_z0_w3 = lerpCubic(valueCoord(seed, x0, y1, z0, w3), valueCoord(seed, x1, y1, z0, w3), valueCoord(seed, x2, y1, z0, w3), valueCoord(seed, x3, y1, z0, w3), xs);
        double lx_y2_z0_w3 = lerpCubic(valueCoord(seed, x0, y2, z0, w3), valueCoord(seed, x1, y2, z0, w3), valueCoord(seed, x2, y2, z0, w3), valueCoord(seed, x3, y2, z0, w3), xs);
        double lx_y3_z0_w3 = lerpCubic(valueCoord(seed, x0, y3, z0, w3), valueCoord(seed, x1, y3, z0, w3), valueCoord(seed, x2, y3, z0, w3), valueCoord(seed, x3, y3, z0, w3), xs);
        double lx_y0_z1_w3 = lerpCubic(valueCoord(seed, x0, y0, z1, w3), valueCoord(seed, x1, y0, z1, w3), valueCoord(seed, x2, y0, z1, w3), valueCoord(seed, x3, y0, z1, w3), xs);
        double lx_y1_z1_w3 = lerpCubic(valueCoord(seed, x0, y1, z1, w3), valueCoord(seed, x1, y1, z1, w3), valueCoord(seed, x2, y1, z1, w3), valueCoord(seed, x3, y1, z1, w3), xs);
        double lx_y2_z1_w3 = lerpCubic(valueCoord(seed, x0, y2, z1, w3), valueCoord(seed, x1, y2, z1, w3), valueCoord(seed, x2, y2, z1, w3), valueCoord(seed, x3, y2, z1, w3), xs);
        double lx_y3_z1_w3 = lerpCubic(valueCoord(seed, x0, y3, z1, w3), valueCoord(seed, x1, y3, z1, w3), valueCoord(seed, x2, y3, z1, w3), valueCoord(seed, x3, y3, z1, w3), xs);
        double lx_y0_z2_w3 = lerpCubic(valueCoord(seed, x0, y0, z2, w3), valueCoord(seed, x1, y0, z2, w3), valueCoord(seed, x2, y0, z2, w3), valueCoord(seed, x3, y0, z2, w3), xs);
        double lx_y1_z2_w3 = lerpCubic(valueCoord(seed, x0, y1, z2, w3), valueCoord(seed, x1, y1, z2, w3), valueCoord(seed, x2, y1, z2, w3), valueCoord(seed, x3, y1, z2, w3), xs);
        double lx_y2_z2_w3 = lerpCubic(valueCoord(seed, x0, y2, z2, w3), valueCoord(seed, x1, y2, z2, w3), valueCoord(seed, x2, y2, z2, w3), valueCoord(seed, x3, y2, z2, w3), xs);
        double lx_y3_z2_w3 = lerpCubic(valueCoord(seed, x0, y3, z2, w3), valueCoord(seed, x1, y3, z2, w3), valueCoord(seed, x2, y3, z2, w3), valueCoord(seed, x3, y3, z2, w3), xs);
        double lx_y0_z3_w3 = lerpCubic(valueCoord(seed, x0, y0, z3, w3), valueCoord(seed, x1, y0, z3, w3), valueCoord(seed, x2, y0, z3, w3), valueCoord(seed, x3, y0, z3, w3), xs);
        double lx_y1_z3_w3 = lerpCubic(valueCoord(seed, x0, y1, z3, w3), valueCoord(seed, x1, y1, z3, w3), valueCoord(seed, x2, y1, z3, w3), valueCoord(seed, x3, y1, z3, w3), xs);
        double lx_y2_z3_w3 = lerpCubic(valueCoord(seed, x0, y2, z3, w3), valueCoord(seed, x1, y2, z3, w3), valueCoord(seed, x2, y2, z3, w3), valueCoord(seed, x3, y2, z3, w3), xs);
        double lx_y3_z3_w3 = lerpCubic(valueCoord(seed, x0, y3, z3, w3), valueCoord(seed, x1, y3, z3, w3), valueCoord(seed, x2, y3, z3, w3), valueCoord(seed, x3, y3, z3, w3), xs);
    
        double ly_z0_w0 = lerpCubic(lx_y0_z0_w0, lx_y1_z0_w0, lx_y2_z0_w0, lx_y3_z0_w0, ys);
        double ly_z1_w0 = lerpCubic(lx_y0_z1_w0, lx_y1_z1_w0, lx_y2_z1_w0, lx_y3_z1_w0, ys);
        double ly_z2_w0 = lerpCubic(lx_y0_z2_w0, lx_y1_z2_w0, lx_y2_z2_w0, lx_y3_z2_w0, ys);
        double ly_z3_w0 = lerpCubic(lx_y0_z3_w0, lx_y1_z3_w0, lx_y2_z3_w0, lx_y3_z3_w0, ys);
        double ly_z0_w1 = lerpCubic(lx_y0_z0_w1, lx_y1_z0_w1, lx_y2_z0_w1, lx_y3_z0_w1, ys);
        double ly_z1_w1 = lerpCubic(lx_y0_z1_w1, lx_y1_z1_w1, lx_y2_z1_w1, lx_y3_z1_w1, ys);
        double ly_z2_w1 = lerpCubic(lx_y0_z2_w1, lx_y1_z2_w1, lx_y2_z2_w1, lx_y3_z2_w1, ys);
        double ly_z3_w1 = lerpCubic(lx_y0_z3_w1, lx_y1_z3_w1, lx_y2_z3_w1, lx_y3_z3_w1, ys);
        double ly_z0_w2 = lerpCubic(lx_y0_z0_w2, lx_y1_z0_w2, lx_y2_z0_w2, lx_y3_z0_w2, ys);
        double ly_z1_w2 = lerpCubic(lx_y0_z1_w2, lx_y1_z1_w2, lx_y2_z1_w2, lx_y3_z1_w2, ys);
        double ly_z2_w2 = lerpCubic(lx_y0_z2_w2, lx_y1_z2_w2, lx_y2_z2_w2, lx_y3_z2_w2, ys);
        double ly_z3_w2 = lerpCubic(lx_y0_z3_w2, lx_y1_z3_w2, lx_y2_z3_w2, lx_y3_z3_w2, ys);
        double ly_z0_w3 = lerpCubic(lx_y0_z0_w3, lx_y1_z0_w3, lx_y2_z0_w3, lx_y3_z0_w3, ys);
        double ly_z1_w3 = lerpCubic(lx_y0_z1_w3, lx_y1_z1_w3, lx_y2_z1_w3, lx_y3_z1_w3, ys);
        double ly_z2_w3 = lerpCubic(lx_y0_z2_w3, lx_y1_z2_w3, lx_y2_z2_w3, lx_y3_z2_w3, ys);
        double ly_z3_w3 = lerpCubic(lx_y0_z3_w3, lx_y1_z3_w3, lx_y2_z3_w3, lx_y3_z3_w3, ys);
    
        double lz_w0 = lerpCubic(ly_z0_w0, ly_z1_w0, ly_z2_w0, ly_z3_w0, zs);
        double lz_w1 = lerpCubic(ly_z0_w1, ly_z1_w1, ly_z2_w1, ly_z3_w1, zs);
        double lz_w2 = lerpCubic(ly_z0_w2, ly_z1_w2, ly_z2_w2, ly_z3_w2, zs);
        double lz_w3 = lerpCubic(ly_z0_w3, ly_z1_w3, ly_z2_w3, ly_z3_w3, zs);
    
        return lerpCubic(lz_w0, lz_w1, lz_w2, lz_w3, ws) * (1.0 / (1.5 * 1.5 * 1.5 * 1.5));
    }
    
    // -------------------- Value Functions -------------------- //
    
    private double getValue(int seed, double x)
    {
        int x0 = floor(x);
        
        double xs = hermite(x - x0);
        
        int x1 = (x0 *= PRIME_X) + PRIME_X;
        
        return lerp(valueCoord(seed, x0), valueCoord(seed, x1), xs);
    }
    
    private double getValue(int seed, double x, double y)
    {
        int x0 = floor(x);
        int y0 = floor(y);
        
        double xs = hermite(x - x0);
        double ys = hermite(y - y0);
        
        int x1 = (x0 *= PRIME_X) + PRIME_X;
        int y1 = (y0 *= PRIME_Y) + PRIME_Y;
    
        double lx_y0 = lerp(valueCoord(seed, x0, y0), valueCoord(seed, x1, y0), xs);
        double lx_y1 = lerp(valueCoord(seed, x0, y1), valueCoord(seed, x1, y1), xs);
    
        return lerp(lx_y0, lx_y1, ys);
    }
    
    private double getValue(int seed, double x, double y, double z)
    {
        int x0 = floor(x);
        int y0 = floor(y);
        int z0 = floor(z);
        
        double xs = hermite(x - x0);
        double ys = hermite(y - y0);
        double zs = hermite(z - z0);
        
        int x1 = (x0 *= PRIME_X) + PRIME_X;
        int y1 = (y0 *= PRIME_Y) + PRIME_Y;
        int z1 = (z0 *= PRIME_Z) + PRIME_Z;
    
        double lx_y0_z0 = lerp(valueCoord(seed, x0, y0, z0), valueCoord(seed, x1, y0, z0), xs);
        double lx_y1_z0 = lerp(valueCoord(seed, x0, y1, z0), valueCoord(seed, x1, y1, z0), xs);
        double lx_y0_z1 = lerp(valueCoord(seed, x0, y0, z1), valueCoord(seed, x1, y0, z1), xs);
        double lx_y1_z1 = lerp(valueCoord(seed, x0, y1, z1), valueCoord(seed, x1, y1, z1), xs);
    
        double ly_z0 = lerp(lx_y0_z0, lx_y1_z0, ys);
        double ly_z1 = lerp(lx_y0_z1, lx_y1_z1, ys);
    
        return lerp(ly_z0, ly_z1, zs);
    }
    
    private double getValue(int seed, double x, double y, double z, double w)
    {
        int x0 = floor(x);
        int y0 = floor(y);
        int z0 = floor(z);
        int w0 = floor(w);
        
        double xs = hermite(x - x0);
        double ys = hermite(y - y0);
        double zs = hermite(z - z0);
        double ws = hermite(w - w0);
        
        int x1 = (x0 *= PRIME_X) + PRIME_X;
        int y1 = (y0 *= PRIME_Y) + PRIME_Y;
        int z1 = (z0 *= PRIME_Z) + PRIME_Z;
        int w1 = (w0 *= PRIME_W) + PRIME_W;
        
        double lx_y0_z0_w0 = lerp(valueCoord(seed, x0, y0, z0, w0), valueCoord(seed, x1, y0, z0, w0), xs);
        double lx_y1_z0_w0 = lerp(valueCoord(seed, x0, y1, z0, w0), valueCoord(seed, x1, y1, z0, w0), xs);
        double lx_y0_z1_w0 = lerp(valueCoord(seed, x0, y0, z1, w0), valueCoord(seed, x1, y0, z1, w0), xs);
        double lx_y1_z1_w0 = lerp(valueCoord(seed, x0, y1, z1, w0), valueCoord(seed, x1, y1, z1, w0), xs);
        double lx_y0_z0_w1 = lerp(valueCoord(seed, x0, y0, z0, w1), valueCoord(seed, x1, y0, z0, w1), xs);
        double lx_y1_z0_w1 = lerp(valueCoord(seed, x0, y1, z0, w1), valueCoord(seed, x1, y1, z0, w1), xs);
        double lx_y0_z1_w1 = lerp(valueCoord(seed, x0, y0, z1, w1), valueCoord(seed, x1, y0, z1, w1), xs);
        double lx_y1_z1_w1 = lerp(valueCoord(seed, x0, y1, z1, w1), valueCoord(seed, x1, y1, z1, w1), xs);
        
        double ly_z0_w0 = lerp(lx_y0_z0_w0, lx_y1_z0_w0, ys);
        double ly_z1_w0 = lerp(lx_y0_z1_w0, lx_y1_z1_w0, ys);
        double ly_z0_w1 = lerp(lx_y0_z0_w1, lx_y1_z0_w1, ys);
        double ly_z1_w1 = lerp(lx_y0_z1_w1, lx_y1_z1_w1, ys);
        
        double lz_w0 = lerp(ly_z0_w0, ly_z1_w0, zs);
        double lz_w1 = lerp(ly_z0_w1, ly_z1_w1, zs);
        
        return lerp(lz_w0, lz_w1, ws);
    }
    
    // private void calculateFractalBounding()
    // {
    //     double amp        = this.gain;
    //     double ampFractal = 1;
    //     for (int i = 1; i < this.octaves; i++)
    //     {
    //         ampFractal += amp;
    //         amp *= this.gain;
    //     }
    //     this.fractalBounding = 1.0 / ampFractal;
    // }
    
    // private void updateTransform()
    // {
    //     this.transform = switch (this.rotation)
    //     {
    //         case IMPROVE_XY -> Transform.IMPROVE_XY;
    //         case IMPROVE_XZ -> Transform.IMPROVE_XZ;
    //         default -> switch (this.type)
    //                 {
    //                     case OPEN_SIMPLEX_2, OPEN_SIMPLEX_2S -> Transform.DEFAULT_OPEN_SIMPLEX_2;
    //                     default -> Transform.NONE;
    //                 };
    //     };
    // }
    
    // private void updateWarpTransform()
    // {
    //     this.warpTransform = switch (this.rotation)
    //     {
    //         case IMPROVE_XY -> Transform.IMPROVE_XY;
    //         case IMPROVE_XZ -> Transform.IMPROVE_XZ;
    //         default -> switch (this.warpType)
    //                 {
    //                     case OPEN_SIMPLEX_2, OPEN_SIMPLEX_2_REDUCED -> Transform.DEFAULT_OPEN_SIMPLEX_2;
    //                     default -> Transform.NONE;
    //                 };
    //     };
    // }
    
    // -------------------- Lookups -------------------- //
    
    private static final double[] GRAD2 = {
            +0.1305261922, +0.9914448613, +0.3826834323, +0.9238795325, +0.6087614290, +0.7933533402, +0.7933533402, +0.6087614290,
            +0.9238795325, +0.3826834323, +0.9914448613, +0.1305261922, +0.9914448613, -0.1305261922, +0.9238795325, -0.3826834323,
            +0.7933533402, -0.6087614290, +0.6087614290, -0.7933533402, +0.3826834323, -0.9238795325, +0.1305261922, -0.9914448613,
            -0.1305261922, -0.9914448613, -0.3826834323, -0.9238795325, -0.6087614290, -0.7933533402, -0.7933533402, -0.6087614290,
            -0.9238795325, -0.3826834323, -0.9914448613, -0.1305261922, -0.9914448613, +0.1305261922, -0.9238795325, +0.3826834323,
            -0.7933533402, +0.6087614290, -0.6087614290, +0.7933533402, -0.3826834323, +0.9238795325, -0.1305261922, +0.9914448613,
            +0.1305261922, +0.9914448613, +0.3826834323, +0.9238795325, +0.6087614290, +0.7933533402, +0.7933533402, +0.6087614290,
            +0.9238795325, +0.3826834323, +0.9914448613, +0.1305261922, +0.9914448613, -0.1305261922, +0.9238795325, -0.3826834323,
            +0.7933533402, -0.6087614290, +0.6087614290, -0.7933533402, +0.3826834323, -0.9238795325, +0.1305261922, -0.9914448613,
            -0.1305261922, -0.9914448613, -0.3826834323, -0.9238795325, -0.6087614290, -0.7933533402, -0.7933533402, -0.6087614290,
            -0.9238795325, -0.3826834323, -0.9914448613, -0.1305261922, -0.9914448613, +0.1305261922, -0.9238795325, +0.3826834323,
            -0.7933533402, +0.6087614290, -0.6087614290, +0.7933533402, -0.3826834323, +0.9238795325, -0.1305261922, +0.9914448613,
            +0.1305261922, +0.9914448613, +0.3826834323, +0.9238795325, +0.6087614290, +0.7933533402, +0.7933533402, +0.6087614290,
            +0.9238795325, +0.3826834323, +0.9914448613, +0.1305261922, +0.9914448613, -0.1305261922, +0.9238795325, -0.3826834323,
            +0.7933533402, -0.6087614290, +0.6087614290, -0.7933533402, +0.3826834323, -0.9238795325, +0.1305261922, -0.9914448613,
            -0.1305261922, -0.9914448613, -0.3826834323, -0.9238795325, -0.6087614290, -0.7933533402, -0.7933533402, -0.6087614290,
            -0.9238795325, -0.3826834323, -0.9914448613, -0.1305261922, -0.9914448613, +0.1305261922, -0.9238795325, +0.3826834323,
            -0.7933533402, +0.6087614290, -0.6087614290, +0.7933533402, -0.3826834323, +0.9238795325, -0.1305261922, +0.9914448613,
            +0.1305261922, +0.9914448613, +0.3826834323, +0.9238795325, +0.6087614290, +0.7933533402, +0.7933533402, +0.6087614290,
            +0.9238795325, +0.3826834323, +0.9914448613, +0.1305261922, +0.9914448613, -0.1305261922, +0.9238795325, -0.3826834323,
            +0.7933533402, -0.6087614290, +0.6087614290, -0.7933533402, +0.3826834323, -0.9238795325, +0.1305261922, -0.9914448613,
            -0.1305261922, -0.9914448613, -0.3826834323, -0.9238795325, -0.6087614290, -0.7933533402, -0.7933533402, -0.6087614290,
            -0.9238795325, -0.3826834323, -0.9914448613, -0.1305261922, -0.9914448613, +0.1305261922, -0.9238795325, +0.3826834323,
            -0.7933533402, +0.6087614290, -0.6087614290, +0.7933533402, -0.3826834323, +0.9238795325, -0.1305261922, +0.9914448613,
            +0.1305261922, +0.9914448613, +0.3826834323, +0.9238795325, +0.6087614290, +0.7933533402, +0.7933533402, +0.6087614290,
            +0.9238795325, +0.3826834323, +0.9914448613, +0.1305261922, +0.9914448613, -0.1305261922, +0.9238795325, -0.3826834323,
            +0.7933533402, -0.6087614290, +0.6087614290, -0.7933533402, +0.3826834323, -0.9238795325, +0.1305261922, -0.9914448613,
            -0.1305261922, -0.9914448613, -0.3826834323, -0.9238795325, -0.6087614290, -0.7933533402, -0.7933533402, -0.6087614290,
            -0.9238795325, -0.3826834323, -0.9914448613, -0.1305261922, -0.9914448613, +0.1305261922, -0.9238795325, +0.3826834323,
            -0.7933533402, +0.6087614290, -0.6087614290, +0.7933533402, -0.3826834323, +0.9238795325, -0.1305261922, +0.9914448613,
            +0.3826834323, +0.9238795325, +0.9238795325, +0.3826834323, +0.9238795325, -0.3826834323, +0.3826834323, -0.9238795325,
            -0.3826834323, -0.9238795325, -0.9238795325, -0.3826834323, -0.9238795325, +0.3826834323, -0.3826834323, +0.9238795325
    };
    
    private static final double[] VEC2 = {
            -0.2700222198, -0.9628540911, +0.3863092627, -0.9223693152, +0.0444485900, -0.9990116730, -0.5992523158, -0.8005602176, -0.7819280288, +0.6233687174, +0.9464672271, +0.3227999196, -0.6514146797, -0.7587218957, +0.9378472289, +0.3470483760,
            -0.8497875957, -0.5271252623, -0.8790425920, +0.4767432447, -0.8923002880, -0.4514423508, -0.3798444340, -0.9250503802, -0.9951650832, +0.0982163789, +0.7724397808, -0.6350880136, +0.7573283322, -0.6530343002, -0.9928004525, -0.1197800550,
            -0.0532665713, +0.9985803285, +0.9754253726, -0.2203300762, -0.7665018163, +0.6422421394, +0.9916367060, +0.1290606184, -0.9946968380, +0.1028503788, -0.5379205513, -0.8429955400, +0.5022815471, -0.8647041387, +0.4559821461, -0.8899889226,
            -0.8659131224, -0.5001944266, +0.0879458407, -0.9961252577, -0.5051684983, +0.8630207346, +0.7753185226, -0.6315704146, -0.6921944612, +0.7217110418, -0.5191659449, -0.8546734591, +0.8978622882, -0.4402764035, -0.1706774107, +0.9853269617,
            -0.9353430106, -0.3537420705, -0.9992404798, +0.0389674679, -0.2882064021, -0.9575683108, -0.9663811329, +0.2571137995, -0.8759714238, -0.4823630009, -0.8303123018, -0.5572983775, +0.0511013375, -0.9986934731, -0.8558373281, -0.5172450752,
            +0.0988702528, +0.9951003332, +0.9189016087, +0.3944867976, -0.2439375892, -0.9697909324, -0.8121409387, -0.5834613061, -0.9910431363, +0.1335421355, +0.8492423985, -0.5280031709, -0.9717838994, -0.2358729591, +0.9949457207, +0.1004142068,
            +0.6241065508, -0.7813392434, +0.6629103070, +0.7486988212, -0.7197418176, +0.6942418282, -0.8143370775, -0.5803922158, +0.1045210540, -0.9945226741, -0.1065926113, -0.9943027784, +0.4457996840, -0.8951327509, +0.1055474060, +0.9944142724,
            -0.9927902670, +0.1198644477, -0.8334366408, +0.5526150250, +0.9115561563, -0.4111755999, +0.8285544909, -0.5599084351, +0.7217097654, -0.6921957921, +0.4940492677, -0.8694339084, -0.3652321272, -0.9309164803, -0.9696606758, +0.2444548501,
            +0.0892550973, -0.9960087990, +0.5354071276, -0.8445941083, -0.1053576186, +0.9944343981, -0.9890284586, +0.1477251101, +0.0048561049, +0.9999882091, +0.9885598478, +0.1508291331, +0.9286129562, -0.3710498316, -0.5832393863, -0.8123003252,
            +0.3015207509, +0.9534596146, -0.9575110528, +0.2883965738, +0.9715802154, -0.2367105511, +0.2299817920, +0.9731949318, +0.9557638160, -0.2941352207, +0.7409561160, +0.6715534485, -0.9971513787, -0.0754263076, +0.6905710663, -0.7232645452,
            -0.2907137030, -0.9568100872, +0.5912777791, -0.8064679708, -0.9454592212, -0.3257404810, +0.6664455681, +0.7455536900, +0.6236134912, +0.7817328275, +0.9126993851, -0.4086316587, -0.8191762011, +0.5735419353, -0.8812745759, -0.4726046147,
            +0.9953313627, +0.0965167265, +0.9855650846, -0.1692969699, -0.8495980887, +0.5274306472, +0.6174853946, -0.7865823463, +0.8508156371, +0.5254643200, +0.9985032451, -0.0546924992, +0.1971371563, -0.9803759185, +0.6607855748, -0.7505747292,
            -0.0309749406, +0.9995201614, -0.6731660801, +0.7394913310, -0.7195018362, -0.6944905383, +0.9727511689, +0.2318515979, +0.9997059088, -0.0242506907, +0.4421787429, -0.8969269532, +0.9981350961, -0.0610436730, -0.9173660799, -0.3980445648,
            -0.8150056635, -0.5794529907, -0.8789331304, +0.4769450202, +0.0158605829, +0.9998742130, -0.8095464474, +0.5870558317, -0.9165898907, -0.3998286786, -0.8023542565, +0.5968480938, -0.5176737917, +0.8555780767, -0.8154407307, -0.5788405779,
            +0.4022010347, -0.9155513791, -0.9052556868, -0.4248672045, +0.7317445619, +0.6815789728, -0.5647632201, -0.8252529947, -0.8403276335, -0.5420788397, -0.9314281527, +0.3639252620, +0.5238198472, +0.8518290719, +0.7432803869, -0.6689800195,
            -0.9853715610, -0.1704197369, +0.4601468731, +0.8878428100, +0.8258554040, +0.5638819483, +0.6182366099, +0.7859920446, +0.8331502863, -0.5530466530, +0.1500307506, +0.9886813308, -0.6623303690, -0.7492119075, -0.6685986640, +0.7436234440,
            +0.7025606278, +0.7116238924, -0.5419389763, -0.8404178401, -0.3388616456, +0.9408362159, +0.8331530315, +0.5530425174, -0.2989720662, -0.9542618632, +0.2638522993, +0.9645630949, +0.1241087390, -0.9922686234, -0.7282649308, -0.6852956957,
            +0.6962500149, +0.7177993569, -0.9183535368, +0.3957610156, -0.6326102274, -0.7744703352, -0.9331891859, -0.3593855080, -0.1153779357, -0.9933216659, +0.9514974788, -0.3076565421, -0.0898797744, -0.9959526224, +0.6678496916, +0.7442961705,
            +0.7952400393, -0.6062947138, -0.6462007402, -0.7631674805, -0.2733598753, +0.9619118351, +0.9669590226, -0.2549318510, -0.9792894595, +0.2024651934, -0.5369502995, -0.8436138784, -0.2700364710, -0.9628500944, -0.6400277131, +0.7683518247,
            -0.7854537493, -0.6189203566, +0.0600590538, -0.9981948257, -0.0245577037, +0.9996984141, -0.6598362300, +0.7514094420, -0.6253894466, -0.7803127835, -0.6210408851, -0.7837781695, +0.8348888491, +0.5504185768, -0.1592275245, +0.9872419133,
            +0.8367622488, +0.5475663786, -0.8675753916, -0.4973056806, -0.2022662628, -0.9793305667, +0.9399189937, +0.3413975472, +0.9877404807, -0.1561049093, -0.9034455656, +0.4287028224, +0.1269804218, -0.9919052235, -0.3819600854, +0.9241788210,
            +0.9754625894, +0.2201652486, -0.3204015856, -0.9472818081, -0.9874760884, +0.1577687387, +0.0253534847, -0.9996785487, +0.4835130794, -0.8753371362, -0.2850799925, -0.9585037287, -0.0680551600, -0.9976815600, -0.7885244045, -0.6150034663,
            +0.3185392127, -0.9479096845, +0.8880043089, +0.4598351306, +0.6476921488, -0.7619021462, +0.9820241299, +0.1887554194, +0.9357275128, -0.3527237187, -0.8894895414, +0.4569555293, +0.7922791302, +0.6101588153, +0.7483818261, +0.6632681526,
            -0.7288929755, -0.6846276581, +0.8729032783, -0.4878932944, +0.8288345784, +0.5594937369, +0.0807456707, +0.9967347374, +0.9799148216, -0.1994165048, -0.5807306730, -0.8140957471, -0.4700049791, -0.8826637636, +0.2409492979, +0.9705377045,
            +0.9437816757, -0.3305694308, -0.8927998638, -0.4504535528, -0.8069622304, +0.5906030467, +0.0625897316, +0.9980393407, -0.9312597469, +0.3643559849, +0.5777449785, +0.8162173362, -0.3360095855, -0.9418585660, +0.6979320750, -0.7161639607,
            -0.0020081572, -0.9999979837, -0.1827294312, -0.9831632392, -0.6523911722, +0.7578824173, -0.4302626911, -0.9027037258, -0.9985126289, -0.0545209125, -0.0102810217, -0.9999471489, -0.4946071129, +0.8691166802, -0.2999350194, +0.9539596344,
            +0.8165471961, +0.5772786819, +0.2697460475, +0.9629314980, -0.7306287391, -0.6827749597, -0.7590952064, -0.6509796216, -0.9070538530, +0.4210146171, -0.5104861064, -0.8598860013, +0.8613350597, +0.5080373165, +0.5007881595, -0.8655698812,
            -0.6541581520, +0.7563577938, -0.8382755311, -0.5452468560, +0.6940070834, +0.7199681717, +0.0695093603, +0.9975812994, +0.1702942185, -0.9853932612, +0.2695973274, +0.9629731466, +0.5519612192, -0.8338697815, +0.2256574870, -0.9742067022,
            +0.4215262855, -0.9068161835, +0.4881873305, -0.8727388672, -0.3683854996, -0.9296731273, -0.9825390578, +0.1860564427, +0.8125647100, +0.5828709909, +0.3196460933, -0.9475370046, +0.9570913859, +0.2897862643, -0.6876655497, -0.7260276109,
            -0.9988770922, -0.0473767310, -0.1250179027, +0.9921544860, -0.8280133617, +0.5607083670, +0.9324863769, -0.3612051451, +0.6394653183, +0.7688199442, -0.0162384706, -0.9998681473, -0.9955014666, -0.0947461345, -0.8145331500, +0.5801170120,
            +0.4037327978, -0.9148769469, +0.9944263371, +0.1054336766, -0.1624711654, +0.9867132919, -0.9949487814, -0.1003838750, -0.6995302564, +0.7146029809, +0.5263414922, -0.8502732700, -0.5395221479, +0.8419714080, +0.6579370318, +0.7530729462,
            +0.0142675884, -0.9998982128, -0.6734383991, +0.7392433447, +0.6394120980, -0.7688642071, +0.9211571421, +0.3891908523, -0.1466372140, -0.9891903394, -0.7823180980, +0.6228791163, -0.5039610839, -0.8637263605, -0.7743120191, -0.6328039957
    };
    
    private static final double[] GRAD3 = {
            +0, +1, +1, +0, +0, -1, +1, +0, +0, +1, -1, +0, +0, -1, -1, +0,
            +1, +0, +1, +0, -1, +0, +1, +0, +1, +0, -1, +0, -1, +0, -1, +0,
            +1, +1, +0, +0, -1, +1, +0, +0, +1, -1, +0, +0, -1, -1, +0, +0,
            +0, +1, +1, +0, +0, -1, +1, +0, +0, +1, -1, +0, +0, -1, -1, +0,
            +1, +0, +1, +0, -1, +0, +1, +0, +1, +0, -1, +0, -1, +0, -1, +0,
            +1, +1, +0, +0, -1, +1, +0, +0, +1, -1, +0, +0, -1, -1, +0, +0,
            +0, +1, +1, +0, +0, -1, +1, +0, +0, +1, -1, +0, +0, -1, -1, +0,
            +1, +0, +1, +0, -1, +0, +1, +0, +1, +0, -1, +0, -1, +0, -1, +0,
            +1, +1, +0, +0, -1, +1, +0, +0, +1, -1, +0, +0, -1, -1, +0, +0,
            +0, +1, +1, +0, +0, -1, +1, +0, +0, +1, -1, +0, +0, -1, -1, +0,
            +1, +0, +1, +0, -1, +0, +1, +0, +1, +0, -1, +0, -1, +0, -1, +0,
            +1, +1, +0, +0, -1, +1, +0, +0, +1, -1, +0, +0, -1, -1, +0, +0,
            +0, +1, +1, +0, +0, -1, +1, +0, +0, +1, -1, +0, +0, -1, -1, +0,
            +1, +0, +1, +0, -1, +0, +1, +0, +1, +0, -1, +0, -1, +0, -1, +0,
            +1, +1, +0, +0, -1, +1, +0, +0, +1, -1, +0, +0, -1, -1, +0, +0,
            +1, +1, +0, +0, +0, -1, +1, +0, -1, +1, +0, +0, +0, -1, -1, +0
    };
    
    private static final double[] VEC3 = {
            -0.7292736885, -0.6618439697, +0.1735581948, +0, +0.7902920810, -0.5480887466, -0.2739291014, +0, +0.7217578935, +0.6226212466, -0.3023380997, +0, +0.5656831370, -0.8208298145, -0.0790000257, +0,
            +0.7600490340, -0.5555979497, -0.3370999617, +0, +0.3713945616, +0.5011264475, +0.7816254623, +0, -0.1277062463, -0.4254438999, -0.8959289049, +0, -0.2881560924, -0.5815838982, +0.7607405838, +0,
            +0.5849561111, -0.6628202390, -0.4674352136, +0, +0.3307171170, +0.0391653737, +0.9429168900, +0, +0.8712121778, -0.4113374369, -0.2679381538, +0, +0.5809810150, +0.7021915846, +0.4115677815, +0,
            +0.5037568730, +0.6330056931, -0.5878203852, +0, +0.4493712205, +0.6013901950, +0.6606022552, +0, -0.6878403724, +0.0901889080, -0.7202371714, +0, -0.5958956522, -0.6469350577, +0.4757976490, +0,
            -0.5127052122, +0.1946921978, -0.8361987284, +0, -0.9911507140, -0.0541027646, -0.1212153153, +0, -0.2149721042, +0.9720882117, -0.0939760774, +0, -0.7518650936, -0.5428057603, +0.3742469607, +0,
            +0.5237068895, +0.8516377189, -0.0210781783, +0, +0.6333504779, +0.1926167129, -0.7495104896, +0, -0.0678824160, +0.3998305789, +0.9140719259, +0, -0.5538628599, -0.4729896695, -0.6852128902, +0,
            -0.7261455366, -0.5911990757, +0.3509933228, +0, -0.9229274730, -0.1782808786, +0.3412049336, +0, -0.6968815002, +0.6511274338, +0.3006480328, +0, +0.9608044783, -0.2098363234, -0.1811724921, +0,
            +0.0681714606, -0.9743405129, +0.2145069156, +0, -0.3577285196, -0.6697087264, -0.6507845481, +0, -0.1868621131, +0.7648617052, -0.6164974636, +0, -0.6541697588, +0.3967914832, +0.6439087246, +0,
            +0.6993340405, -0.6164538506, +0.3618239211, +0, -0.1546665730, +0.6291283928, +0.7617583057, +0, -0.6841612949, -0.2580482182, -0.6821542638, +0, +0.5383980957, +0.4258654885, +0.7271630328, +0,
            -0.5026987823, -0.7939832935, -0.3418836993, +0, +0.3202971715, +0.2834415347, +0.9039195862, +0, +0.8683227101, -0.0003762656, -0.4959995258, +0, +0.7911200310, -0.0851104574, +0.6057105799, +0,
            -0.0401101605, -0.4397248749, +0.8972364289, +0, +0.9145119870, +0.3579346169, -0.1885487608, +0, -0.9612039066, -0.2756484276, +0.0102466692, +0, +0.6510361721, -0.2877799159, -0.7023778346, +0,
            -0.2041786351, +0.7365237271, +0.6448595850, +0, -0.7718263711, +0.3790626912, +0.5104855816, +0, -0.3060082741, -0.7692987727, +0.5608371729, +0, +0.4540073410, -0.5024843065, +0.7357899537, +0,
            +0.4816795475, +0.6021208291, -0.6367380315, +0, +0.6961980360, -0.3222197429, +0.6414691970, +0, -0.6532160499, -0.6781148932, +0.3368515753, +0, +0.5089301236, -0.6154662304, -0.6018234363, +0,
            -0.1635919754, -0.9133604627, -0.3728408920, +0, +0.5240801900, -0.8437664109, +0.1157505864, +0, +0.5902587356, +0.4983817807, -0.6349883666, +0, +0.5863227872, +0.4947647450, +0.6414307729, +0,
            +0.6779335087, +0.2341345225, +0.6968408593, +0, +0.7177054540, -0.6858979348, +0.1201786310, +0, -0.5328819713, -0.5205125012, +0.6671608058, +0, -0.8654874251, -0.0700727088, -0.4960053754, +0,
            -0.2861810166, +0.7952089234, +0.5345495242, +0, -0.0484952963, +0.9810836427, -0.1874115585, +0, -0.6358521667, +0.6058348682, +0.4781800233, +0, +0.6254794696, -0.2861619734, +0.7258696564, +0,
            -0.2585259868, +0.5061949264, -0.8227581726, +0, +0.0213630670, +0.5064016808, -0.8620330371, +0, +0.2001117730, +0.8599263484, +0.4695550591, +0, +0.4743561372, +0.6014985084, -0.6427953014, +0,
            +0.6622993731, -0.5202474575, -0.5391679918, +0, +0.0808497281, -0.6532720452, +0.7527940996, +0, -0.6893687501, +0.0592860349, +0.7219805347, +0, -0.1121887082, -0.9673185067, +0.2273952515, +0,
            +0.7344116094, +0.5979668656, -0.3210532909, +0, +0.5789393460, -0.2488849713, +0.7764570201, +0, +0.6988182827, +0.3557169806, -0.6205791146, +0, -0.8636845529, -0.2748771249, -0.4224826141, +0,
            -0.4247027957, -0.4640880967, +0.7773350460, +0, +0.5257722489, -0.8427017621, +0.1158329937, +0, +0.9343830603, +0.3163024720, -0.1639543925, +0, -0.1016836419, -0.8057303073, -0.5834887393, +0,
            -0.6529238969, +0.5060212600, -0.5635892736, +0, -0.2465286160, -0.9668205684, -0.0669449749, +0, -0.9776897119, -0.2099250524, -0.0073688253, +0, +0.7736893337, +0.5734244712, +0.2694238123, +0,
            -0.6095087895, +0.4995678998, +0.6155736747, +0, +0.5794535482, +0.7434546771, +0.3339292269, +0, -0.8226211154, +0.0814258185, +0.5627293636, +0, -0.5103854830, +0.4703667658, +0.7199039967, +0,
            -0.5764971849, -0.0723165627, -0.8138926898, +0, +0.7250628870, +0.3949971505, -0.5641463116, +0, -0.1525424005, +0.4860840828, -0.8604958341, +0, -0.5550976208, -0.4957820792, +0.6678822960, +0,
            -0.1883614327, +0.9145869398, +0.3578417250, +0, +0.7625556724, -0.5414408243, -0.3540489801, +0, -0.5870231946, -0.3226498013, -0.7424963803, +0, +0.3051124198, +0.2262544068, -0.9250488391, +0,
            +0.6379576059, +0.5772424240, -0.5097070502, +0, -0.5966775790, +0.1454852398, -0.7891830656, +0, -0.6583305730, +0.6555487542, -0.3699414651, +0, +0.7434892426, +0.2351084581, +0.6260573129, +0,
            +0.5562114096, +0.8264360377, -0.0873632843, +0, -0.3028940016, -0.8251527185, +0.4768419182, +0, +0.1129343818, -0.9858884390, -0.1235710781, +0, +0.5937652891, -0.5896813806, +0.5474656618, +0,
            +0.6757964092, -0.5835758614, -0.4502648413, +0, +0.7242302600, -0.1152719764, +0.6798550586, +0, -0.9511914166, +0.0753623979, -0.2992580792, +0, +0.2539470961, -0.1886339355, +0.9486454084, +0,
            +0.5714336210, -0.1679450851, -0.8032795685, +0, -0.0677823497, +0.3978269256, +0.9149531629, +0, +0.6074972649, +0.7330600240, -0.3058922593, +0, -0.5435478392, +0.1675822484, +0.8224791405, +0,
            -0.5876678086, -0.3380045064, -0.7351186982, +0, -0.7967562400, +0.0409782270, -0.6029098428, +0, -0.1996350917, +0.8706294745, +0.4496111079, +0, -0.0278766033, -0.9106232682, -0.4122962022, +0,
            -0.7797625996, -0.6257634692, +0.0197577558, +0, -0.5211232846, +0.7401644346, -0.4249554471, +0, +0.8575424857, +0.4053272873, -0.3167501783, +0, +0.1045223322, +0.8390195772, -0.5339674439, +0,
            +0.3501822831, +0.9242524096, -0.1520850155, +0, +0.1987849850, +0.0764761326, +0.9770547224, +0, +0.7845996363, +0.6066256811, -0.1280964233, +0, +0.0900673743, -0.9750989929, -0.2026569073, +0,
            -0.8274343547, -0.5422995590, +0.1458203587, +0, -0.3485797732, -0.4158022770, +0.8400003620, +0, -0.2471778936, -0.7304819962, -0.6366310879, +0, -0.3700154943, +0.8577948156, +0.3567584454, +0,
            +0.5913394901, -0.5483119670, -0.5913303597, +0, +0.1204873510, -0.7626472379, -0.6354935001, +0, +0.6169592650, +0.0307964792, +0.7863922953, +0, +0.1258156836, -0.6640829889, -0.7369967419, +0,
            -0.6477565124, -0.1740147258, -0.7417077429, +0, +0.6217889313, -0.7804430448, -0.0654765507, +0, +0.6589943422, -0.6096987708, +0.4404473475, +0, -0.2689837504, -0.6732403169, -0.6887635427, +0,
            -0.3849775103, +0.5676542638, +0.7277093879, +0, +0.5754444400, +0.8110471154, -0.1051963504, +0, +0.9141593684, +0.3832947817, +0.1319005670, +0, -0.1079253190, +0.9245493968, +0.3654593525, +0,
            +0.3779770890, +0.3043148782, +0.8743716458, +0, -0.2142885215, -0.8259286236, +0.5214617324, +0, +0.5802544474, +0.4148098596, -0.7008834116, +0, -0.1982660881, +0.8567161266, -0.4761596756, +0,
            -0.0338155370, +0.3773180787, -0.9254661404, +0, -0.6867922840, -0.6656597827, +0.2919133642, +0, +0.7731742607, -0.2875793547, -0.5652430251, +0, -0.0965594192, +0.9193708367, -0.3813575004, +0,
            +0.2715702457, -0.9577909544, -0.0942660558, +0, +0.2451015704, -0.6917998565, -0.6792188003, +0, +0.9777007820, -0.1753855374, +0.1155036542, +0, -0.5224739938, +0.8521606816, +0.0290361594, +0,
            -0.7734880599, -0.5261292347, +0.3534179531, +0, -0.7134492440, -0.2695472430, +0.6467878011, +0, +0.1644037271, +0.5105846203, -0.8439637196, +0, +0.6494635788, +0.0558561129, +0.7583384168, +0,
            -0.4711970882, +0.5017280509, -0.7254255765, +0, -0.6335764307, -0.2381686273, -0.7361091029, +0, -0.9021533097, -0.2709478030, -0.3357181763, +0, -0.3793711033, +0.8722581170, +0.3086152025, +0,
            -0.6855598966, -0.3250143309, +0.6514394162, +0, +0.2900942210, -0.7799057743, -0.5546100667, +0, -0.2098319339, +0.8503707300, +0.4825351604, +0, -0.4592603758, +0.6598504336, -0.5947077538, +0,
            +0.8715945488, +0.0961636540, -0.4807031248, +0, -0.6776666319, +0.7118504878, -0.1844907016, +0, +0.7044377633, +0.3124275970, +0.6373040360, +0, -0.7052318886, -0.2401093292, -0.6670798253, +0,
            +0.0819210070, -0.7207336136, -0.6883545647, +0, -0.6993680900, -0.5875763221, -0.4069869034, +0, -0.1281454481, +0.6419895885, +0.7559286424, +0, -0.6337388239, -0.6785471501, -0.3714146849, +0,
            +0.5565051903, -0.2168887573, -0.8020356851, +0, -0.5791554484, +0.7244372011, -0.3738578718, +0, +0.1175779076, -0.7096451073, +0.6946792478, +0, -0.6134619607, +0.1323631078, +0.7785527795, +0,
            +0.6984635305, -0.0298051623, -0.7150247190, +0, +0.8318082960, -0.3930171956, +0.3919597455, +0, +0.1469576422, +0.0554165171, -0.9875892167, +0, +0.7088685750, -0.2690503865, +0.6520101478, +0,
            +0.2726053183, +0.6736976600, -0.6868899500, +0, -0.6591295371, +0.3035458599, -0.6880466294, +0, +0.4815131379, -0.7528270071, +0.4487723203, +0, +0.9430009463, +0.1675647412, -0.2875261255, +0,
            +0.4348029570, +0.7695304522, -0.4677277752, +0, +0.3931996180, +0.5944736250, +0.7014236729, +0, +0.7254336655, -0.6039256540, +0.3301814672, +0, +0.7590235227, -0.6506083235, +0.0243331320, +0,
            -0.8552768592, -0.3430042733, +0.3883935666, +0, -0.6139746835, +0.6981725247, +0.3682257648, +0, -0.7465905486, -0.5752009504, +0.3342849376, +0, +0.5730065677, +0.8105555370, -0.1210916791, +0,
            -0.9225877367, -0.3475211012, -0.1675140360, +0, -0.7105816780, -0.4719692027, -0.5218416899, +0, -0.0856460971, +0.3583001386, +0.9296697030, +0, -0.8279697606, -0.2043157126, +0.5222271202, +0,
            +0.4279440230, +0.2781659940, +0.8599346446, +0, +0.5399079671, -0.7857120652, -0.3019204161, +0, +0.5678404253, -0.5495413974, -0.6128307303, +0, -0.9896071041, +0.1365639107, -0.0450341842, +0,
            -0.6154342638, -0.6440875597, +0.4543037336, +0, +0.1074204360, -0.7946340692, +0.5975094525, +0, -0.3595449969, -0.8885529948, +0.2849578400, +0, -0.2180405296, +0.1529888965, +0.9638738118, +0,
            -0.7277432317, -0.6164050508, -0.3007234646, +0, +0.7249729114, -0.0066971948, +0.6887448187, +0, -0.5553659455, -0.5336586252, +0.6377908264, +0, +0.5137558015, +0.7976208196, -0.3160000073, +0,
            -0.3794024848, +0.9245608561, -0.0352275149, +0, +0.8229248650, +0.2745365933, -0.4974176556, +0, -0.5404114394, +0.6091141441, +0.5804613989, +0, +0.8036581901, -0.2703029469, +0.5301601931, +0,
            +0.6044318879, +0.6832968393, +0.4095943388, +0, +0.0638998881, +0.9658208605, -0.2512108074, +0, +0.1087113286, +0.7402471173, -0.6634877936, +0, -0.7134277120, -0.6926784018, +0.1059128479, +0,
            +0.6458897819, -0.5724548511, -0.5050958653, +0, -0.6553931410, +0.7381471625, +0.1599956150, +0, +0.3910961323, +0.9188871375, -0.0518675599, +0, -0.4879022471, -0.5904376907, +0.6429111375, +0,
            +0.6014790094, +0.7707441366, -0.2101820095, +0, -0.5677173047, +0.7511360995, +0.3368851762, +0, +0.7858573506, +0.2266746650, +0.5753666838, +0, -0.4520345543, -0.6042226860, -0.6561857263, +0,
            +0.0022721163, +0.4132844051, -0.9105991643, +0, -0.5815751410, -0.5162925989, +0.6286591339, +0, -0.0370370478, +0.8273785755, +0.5604221175, +0, -0.5119692504, +0.7953543429, -0.3244980058, +0,
            -0.2682417366, -0.9572290247, -0.1084387619, +0, -0.2322482736, -0.9679131102, -0.0959424332, +0, +0.3554328906, -0.8881505545, +0.2913006227, +0, +0.7346520519, -0.4371373164, +0.5188422971, +0,
            +0.9985120116, +0.0465901116, -0.0283394457, +0, -0.3727687490, -0.9082481361, +0.1900757285, +0, +0.9173737700, -0.3483642108, +0.1925298489, +0, +0.2714911074, +0.4147529736, -0.8684886582, +0,
            +0.5131763485, -0.7116334161, +0.4798207128, +0, -0.8737353606, +0.1888699200, -0.4482350644, +0, +0.8460043821, -0.3725217914, +0.3814499973, +0, +0.8978727456, -0.1780209141, -0.4026575304, +0,
            +0.2178065647, -0.9698322841, -0.1094789531, +0, -0.1518031300, -0.7788918132, -0.6085091231, +0, -0.2600384876, -0.4755398075, -0.8403819825, +0, +0.5723135090, -0.7474340931, -0.3373418503, +0,
            -0.7174141009, +0.1699017182, -0.6756111411, +0, -0.6841807840, +0.0214570759, -0.7289967412, +0, -0.2007447902, +0.0655560578, -0.9774476623, +0, -0.1148803697, -0.8044887315, +0.5827524187, +0,
            -0.7870349638, +0.0344748923, +0.6159443543, +0, -0.2015596420, +0.6859872284, +0.6991389226, +0, -0.0858108251, -0.1092083600, -0.9903080513, +0, +0.5532693395, +0.7325250401, -0.3966107710, +0,
            -0.1842489331, -0.9777375055, -0.1004076743, +0, +0.0775473789, -0.9111505856, +0.4047110257, +0, +0.1399838409, +0.7601631212, -0.6344734459, +0, +0.4484419361, -0.8452892480, +0.2904925424, +0
    };
    
    // -------------------- Hashing Methods -------------------- //
    
    private static final int PRIME_X = 501125321;
    private static final int PRIME_Y = 1136930381;
    private static final int PRIME_Z = 1720413743;
    private static final int PRIME_W = 2147483647;
    
    private static final int PRIME_MUL = 0x27D4EB2D;
    
    private static final double VALUE_MUL = 1.0 / ((double) Integer.MAX_VALUE + 1.0) ;
    
    private static int hash(int seed, int x)
    {
        return (seed ^ x) * Noise.PRIME_MUL;
    }
    
    private static int hash(int seed, int x, int y)
    {
        return (seed ^ x ^ y) * Noise.PRIME_MUL;
    }
    
    private static int hash(int seed, int x, int y, int z)
    {
        return (seed ^ x ^ y ^ z) * Noise.PRIME_MUL;
    }
    
    private static int hash(int seed, int x, int y, int z, int w)
    {
        return (seed ^ x ^ y ^ z ^ w) * Noise.PRIME_MUL;
    }
    
    private static double valueCoord(int seed, int x)
    {
        int hash = hash(seed, x);
        hash *= hash;
        return (hash ^ (hash << 19)) * Noise.VALUE_MUL;
    }
    
    private static double valueCoord(int seed, int x, int y)
    {
        int hash = hash(seed, x, y);
        hash *= hash;
        return (hash ^ (hash << 19)) * Noise.VALUE_MUL;
    }
    
    private static double valueCoord(int seed, int x, int y, int z)
    {
        int hash = hash(seed, x, y, z);
        hash *= hash;
        return (hash ^ (hash << 19)) * Noise.VALUE_MUL;
    }
    
    private static double valueCoord(int seed, int x, int y, int z, int w)
    {
        int hash = hash(seed, x, y, z, w);
        hash *= hash;
        return (hash ^ (hash << 19)) * Noise.VALUE_MUL;
    }
    
    private static double gradCoord(int seed, int x, double xd)
    {
        int hash = hash(seed, x);
        hash ^= hash >>> 32;
        hash &= 0xFE;
        
        // double xg = Noise.GRAD1[(int) hash];
        double xg = 1.0;
        
        return xd * xg;
    }
    
    private static double gradCoord(int seed, int x, int y, double xd, double yd)
    {
        int hash = hash(seed, x, y);
        hash ^= hash >>> 32;
        hash &= 0xFE;
        
        double xg = Noise.GRAD2[(int) hash];
        double yg = Noise.GRAD2[(int) hash | 1];
        
        return xd * xg + yd * yg;
    }
    
    private static double GradCoord(int seed, int x, int y, int z, double xd, double yd, double zd)
    {
        int hash = hash(seed, x, y, z);
        hash ^= hash >>> 32;
        hash &= 0xFC;
        
        double xg = Noise.GRAD3[(int) hash];
        double yg = Noise.GRAD3[(int) hash | 1];
        double zg = Noise.GRAD3[(int) hash | 2];
        
        return xd * xg + yd * yg + zd * zg;
    }
    
    private static double gradCoord(int seed, int x, int y, int z, int w, double xd, double yd, double zd, double wd)
    {
        int hash = seed;
        hash ^= Noise.PRIME_X * x;
        hash ^= Noise.PRIME_Y * y;
        hash ^= Noise.PRIME_Z * z;
        hash ^= Noise.PRIME_W * w;
        
        hash = hash * hash * hash * 60493;
        hash = (hash >> 13) ^ hash;
        
        hash &= 31;
        double a, b, c;    // X,Y,Z
        switch ((int) hash >> 3) // OR, DEPENDING ON HIGH ORDER 2 BITS:
        {
            // W,X,Y
            case 1 -> {
                a = wd;
                b = xd;
                c = yd;
            }
            // Z,W,X
            case 2 -> {
                a = zd;
                b = wd;
                c = xd;
            }
            // Y,Z,W
            default -> {
                a = yd;
                b = zd;
                c = wd;
            }
        }
        return ((hash & 4) == 0 ? -a : a) + ((hash & 2) == 0 ? -b : b) + ((hash & 1) == 0 ? -c : c);
    }
    
    // -------------------- Utility Methods -------------------- //
    
    private static double min(double a, double b)
    {
        return Math.min(a, b);
    }
    
    private static double max(double a, double b)
    {
        return Math.max(a, b);
    }
    
    private static double FastAbs(double f)
    {
        return f < 0 ? -f : f;
    }
    
    private static double FastSqrt(double f)
    {
        return Math.sqrt(f);
    }
    
    private static int floor(double f)
    {
        return (f >= 0 ? (int) f : (int) f - 1);
    }
    
    private static int round(double f)
    {
        return (f >= 0) ? (int) (f + 0.5) : (int) (f - 0.5);
    }
    
    private static double lerp(double a, double b, double t)
    {
        return a + t * (b - a);
    }
    
    private static double hermite(double t)
    {
        return t * t * (3 - 2 * t);
    }
    
    private static double quintic(double t)
    {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }
    
    private static double lerpCubic(double a, double b, double c, double d, double t)
    {
        double p = (d - c) - (a - b);
        return t * t * t * p + t * t * ((a - b) - p) + t * (c - a) + b;
    }
    
    private static double pingPong(double t)
    {
        t -= (int) (t * 0.5f) * 2;
        return t < 1 ? t : 2 - t;
    }
    
    private static final AtomicLong seedUniquifier = new AtomicLong(8682522807148012L);
    
    private static long seedUniquifier()
    {
        for (; ; )
        {
            long current = Noise.seedUniquifier.get();
            long next    = current * 1181783497276652981L;
            if (Noise.seedUniquifier.compareAndSet(current, next)) return next;
        }
    }
}

package rutils.group;


import org.jetbrains.annotations.Nullable;

/**
 * Interface to a read-only view of a Pair.
 */
public interface IPair<A, B> extends IGroup
{
    /**
     * @return The first object in the pair.
     */
    @Nullable A getA();
    
    /**
     * @return The second object in the pair.
     */
    @Nullable B getB();
    
    /**
     * A simple {@code int} pair.
     */
    interface I extends IPair<Integer, Integer>
    {
        /**
         * @return The first int value.
         */
        int a();
        
        /**
         * @return The second int value.
         */
        int b();
    }
    
    /**
     * A simple {@code long} pair.
     */
    interface L extends IPair<Long, Long>
    {
        /**
         * @return The first long value.
         */
        long a();
        
        /**
         * @return The second long value.
         */
        long b();
    }
    
    /**
     * A simple {@code float} pair.
     */
    interface F extends IPair<Float, Float>
    {
        /**
         * @return The first float value.
         */
        float a();
        
        /**
         * @return The second float value.
         */
        float b();
    }
    
    /**
     * A simple {@code double} pair.
     */
    interface D extends IPair<Double, Double>
    {
        /**
         * @return The first double value.
         */
        double a();
        
        /**
         * @return The second double value.
         */
        double b();
    }
    
    /**
     * A simple {@code String} pair.
     */
    interface S extends IPair<String, String>
    {
        /**
         * @return The first String value.
         */
        @Nullable String a();
        
        /**
         * @return The second String value.
         */
        @Nullable String b();
    }
}

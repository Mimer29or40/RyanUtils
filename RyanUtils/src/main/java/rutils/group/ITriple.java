package rutils.group;

import org.jetbrains.annotations.Nullable;

/**
 * Interface to a read-only view of a Triple.
 */
public interface ITriple<A, B, C> extends IGroup
{
    /**
     * @return The first object in the triple.
     */
    @Nullable A getA();
    
    /**
     * @return The second object in the triple.
     */
    @Nullable B getB();
    
    /**
     * @return The third object in the triple.
     */
    @Nullable C getC();
    
    /**
     * A simple {@code int} tuple.
     */
    interface I extends ITriple<Integer, Integer, Integer>
    {
        /**
         * @return The first int value.
         */
        int a();
        
        /**
         * @return The second int value.
         */
        int b();
        
        /**
         * @return The third int value.
         */
        int c();
    }
    
    /**
     * A simple {@code long} tuple.
     */
    interface L extends ITriple<Long, Long, Long>
    {
        /**
         * @return The first long value.
         */
        long a();
        
        /**
         * @return The second long value.
         */
        long b();
        
        /**
         * @return The third long value.
         */
        long c();
    }
    
    /**
     * A simple {@code double} float.
     */
    interface F extends ITriple<Float, Float, Float>
    {
        /**
         * @return The first float value.
         */
        float a();
        
        /**
         * @return The second float value.
         */
        float b();
        
        /**
         * @return The third float value.
         */
        float c();
    }
    
    /**
     * A simple {@code double} tuple.
     */
    interface D extends ITriple<Double, Double, Double>
    {
        /**
         * @return The first double value.
         */
        double a();
        
        /**
         * @return The second double value.
         */
        double b();
        
        /**
         * @return The third double value.
         */
        double c();
    }
    
    /**
     * A simple {@code String} tuple.
     */
    interface S extends ITriple<String, String, String>
    {
        /**
         * @return The first String value.
         */
        @Nullable String a();
        
        /**
         * @return The second String value.
         */
        @Nullable String b();
        
        /**
         * @return The third String value.
         */
        @Nullable String c();
    }
}

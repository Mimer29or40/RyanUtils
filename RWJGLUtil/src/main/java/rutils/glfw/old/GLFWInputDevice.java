package rutils.glfw.old;

import rutils.Logger;

public abstract class GLFWInputDevice extends GLFWDevice
{
    private static final Logger LOGGER = new Logger();
    
    protected static long holdDelay   = 500_000_000;
    protected static long repeatDelay = 30_000_000;
    protected static long doubleDelay = 100_000_000;
    
    /**
     * @return The delay in seconds before an Input is "held".
     */
    public static double holdDelay()
    {
        return GLFWInputDevice.holdDelay / 1_000_000_000D;
    }
    
    /**
     * Sets the delay in seconds before an Input is "held".
     *
     * @param holdDelay The new delay in seconds.
     */
    public static void holdDelay(double holdDelay)
    {
        GLFWInputDevice.LOGGER.finest("Setting GLFWInputDevice Hold Delay:", holdDelay);
        
        GLFWInputDevice.holdDelay = (long) (holdDelay * 1_000_000_000L);
    }
    
    /**
     * @return The delay in seconds before an Input is "repeated".
     */
    public static double repeatDelay()
    {
        return GLFWInputDevice.repeatDelay / 1_000_000_000D;
    }
    
    /**
     * Sets the delay in seconds before an Input is "repeated".
     *
     * @param repeatDelay The new delay in seconds.
     */
    public static void repeatDelay(double repeatDelay)
    {
        GLFWInputDevice.LOGGER.finest("Setting GLFWInputDevice Repeat Delay:", holdDelay);
        
        GLFWInputDevice.repeatDelay = (long) (repeatDelay * 1_000_000_000L);
    }
    
    /**
     * @return The delay in seconds before an Input is pressed/clicked twice to be a double pressed/clicked.
     */
    public static double doubleDelay()
    {
        return GLFWInputDevice.doubleDelay / 1_000_000_000D;
    }
    
    /**
     * Sets the delay in seconds before an Input is pressed/clicked twice to be a double pressed/clicked.
     *
     * @param doubleDelay The new delay in seconds.
     */
    public static void doubleDelay(double doubleDelay)
    {
        GLFWInputDevice.LOGGER.finest("Setting GLFWInputDevice Double Delay:", holdDelay);
        
        GLFWInputDevice.doubleDelay = (long) (doubleDelay * 1_000_000_000L);
    }
}

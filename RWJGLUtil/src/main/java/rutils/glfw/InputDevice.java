package rutils.glfw;

import org.jetbrains.annotations.NotNull;
import rutils.Logger;

import java.util.Map;
import java.util.function.Predicate;

import static org.lwjgl.glfw.GLFW.*;

public abstract class InputDevice<E extends Enum<E>, I extends InputDevice.Input>
{
    private static final Logger LOGGER = new Logger();
    
    protected static long holdDelay   = 500_000_000L;
    protected static long repeatDelay = 30_000_000L;
    protected static long doubleDelay = 100_000_000L;
    
    /**
     * @return The delay in seconds before an Input is "held".
     */
    public static double holdDelay()
    {
        return InputDevice.holdDelay / 1_000_000_000D;
    }
    
    /**
     * Sets the delay in seconds before an Input is "held".
     *
     * @param holdDelay The new delay in seconds.
     */
    public static void holdDelay(double holdDelay)
    {
        InputDevice.LOGGER.finest("Setting InputDevice Hold Delay:", holdDelay);
        
        InputDevice.holdDelay = (long) (holdDelay * 1_000_000_000L);
    }
    
    /**
     * @return The delay in seconds before an Input is "repeated".
     */
    public static double repeatDelay()
    {
        return InputDevice.repeatDelay / 1_000_000_000D;
    }
    
    /**
     * Sets the delay in seconds before an Input is "repeated".
     *
     * @param repeatDelay The new delay in seconds.
     */
    public static void repeatDelay(double repeatDelay)
    {
        InputDevice.LOGGER.finest("Setting InputDevice Repeat Delay:", holdDelay);
        
        InputDevice.repeatDelay = (long) (repeatDelay * 1_000_000_000L);
    }
    
    /**
     * @return The delay in seconds before an Input is pressed/clicked twice to be a double pressed/clicked.
     */
    public static double doubleDelay()
    {
        return InputDevice.doubleDelay / 1_000_000_000D;
    }
    
    /**
     * Sets the delay in seconds before an Input is pressed/clicked twice to be a double pressed/clicked.
     *
     * @param doubleDelay The new delay in seconds.
     */
    public static void doubleDelay(double doubleDelay)
    {
        InputDevice.LOGGER.finest("Setting InputDevice Double Delay:", holdDelay);
        
        InputDevice.doubleDelay = (long) (doubleDelay * 1_000_000_000L);
    }
    
    protected final Map<E, I> inputMap;
    
    public InputDevice()
    {
        this.inputMap = generateMap();
    }
    
    protected abstract @NotNull Map<E, I> generateMap();
    
    /**
     * This method is called by the window it is attached to. This is where
     * events should be posted to when something has changed.
     *
     * @param time  The system time in nanoseconds.
     * @param delta The time in nanoseconds since the last time this method was called.
     */
    protected void postEvents(long time, long delta)
    {
        for (I input : this.inputMap.values())
        {
            input.down   = false;
            input.up     = false;
            input.repeat = false;
            input.mods   = 0;
            
            if (input._action != input.action)
            {
                if (input._action == GLFW_PRESS)
                {
                    input.down     = true;
                    input.held     = true;
                    input.repeat   = true;
                    input.downTime = time;
                }
                else if (input._action == GLFW_RELEASE)
                {
                    input.up       = true;
                    input.held     = false;
                    input.downTime = Long.MAX_VALUE;
                }
                input.action = input._action;
                input.mods   = input._mods;
            }
            if (input.action == GLFW_REPEAT && input.held && time - input.downTime > InputDevice.holdDelay)
            {
                input.downTime += InputDevice.repeatDelay;
                input.repeat = true;
                input.mods   = input._mods;
            }
            
            postInputEvents(input, time, delta);
        }
    }
    
    /**
     * Post events to the event bus
     *
     * @param input The input object to generate the events for.
     * @param time  The system time in nanoseconds.
     * @param delta The time in nanoseconds since the last time this method was called.
     */
    protected abstract void postInputEvents(I input, long time, long delta);
    
    public static class Input
    {
        protected boolean down, up, held, repeat;
        
        protected int _action, action;
        
        protected int _mods, mods;
        
        protected long downTime;
        
        /**
         * @return If the Input is in the down state with optional modifiers. This will only be true for one frame.
         */
        public boolean down(Modifier... modifiers)
        {
            return this.down && checkModifiers(modifiers);
        }
        
        /**
         * @return If the Input was released with optional modifiers. This will only be true for one frame.
         */
        public boolean up(Modifier... modifiers)
        {
            return this.up && checkModifiers(modifiers);
        }
        
        /**
         * @return If the Input is being held down with optional modifiers.
         */
        public boolean held(Modifier... modifiers)
        {
            return this.held && checkModifiers(modifiers);
        }
        
        /**
         * @return If the Input is being repeated with optional modifiers. This will be true for one frame at a time.
         */
        public boolean repeat(Modifier... modifiers)
        {
            return this.repeat && checkModifiers(modifiers);
        }
        
        /**
         * Checks if the supplied modifiers match which modifiers are pressed.
         *
         * @param modifiers The array of modifiers.
         * @return True if the supplied modifiers matches the actual modifiers.
         */
        protected boolean checkModifiers(Modifier[] modifiers)
        {
            if (modifiers.length == 0) return true;
            Predicate<Integer> predicate = modifiers[0];
            for (int i = 1; i < modifiers.length; i++) predicate = predicate.and(modifiers[i]);
            return predicate.test(this.mods);
        }
    }
}

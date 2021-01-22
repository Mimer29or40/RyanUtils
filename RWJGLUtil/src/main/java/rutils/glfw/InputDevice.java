package rutils.glfw;

import org.jetbrains.annotations.NotNull;
import rutils.Logger;

import java.util.Map;
import java.util.function.Predicate;

import static org.lwjgl.glfw.GLFW.*;

public abstract class InputDevice<E extends Enum<E>, I extends InputDevice.Input<E>>
{
    private static final Logger LOGGER = new Logger();
    
    protected static long repeatDelay        = 500_000_000L;
    protected static long repeatFrequency    = 30_000_000L;
    protected static long doublePressedDelay = 200_000_000L;
    
    /**
     * @return The delay, in seconds, before an Input is "held" before it starts to be "repeated".
     */
    public static double repeatDelay()
    {
        return InputDevice.repeatDelay / 1_000_000_000D;
    }
    
    /**
     * Sets the delay, in seconds, before an Input is "held" before it starts to be "repeated".
     *
     * @param repeatDelay The delay, in seconds, before an Input is "held" before it starts to be "repeated".
     */
    public static void repeatDelay(double repeatDelay)
    {
        InputDevice.LOGGER.finest("Setting InputDevice Hold Delay:", repeatDelay);
        
        InputDevice.repeatDelay = (long) (repeatDelay * 1_000_000_000L);
    }
    
    /**
     * @return The frequency, in seconds, that an Input is "repeated" after the initial delay has passed.
     */
    public static double repeatFrequency()
    {
        return InputDevice.repeatFrequency / 1_000_000_000D;
    }
    
    /**
     * Sets the frequency, in seconds, that an Input is "repeated" after the initial delay has passed.
     *
     * @param repeatFrequency The frequency, in seconds, that an Input is "repeated" after the initial delay has passed.
     */
    public static void repeatFrequency(double repeatFrequency)
    {
        InputDevice.LOGGER.finest("Setting InputDevice Repeat Delay:", repeatDelay);
        
        InputDevice.repeatFrequency = (long) (repeatFrequency * 1_000_000_000L);
    }
    
    /**
     * @return The delay, in seconds, before an Input is pressed twice to be a double pressed.
     */
    public static double doublePressedDelay()
    {
        return InputDevice.doublePressedDelay / 1_000_000_000D;
    }
    
    /**
     * Sets the delay, in seconds, before an Input is pressed twice to be a double pressed.
     *
     * @param doublePressedDelay The delay, in seconds, before an Input is pressed twice to be a double pressed.
     */
    public static void doublePressedDelay(double doublePressedDelay)
    {
        InputDevice.LOGGER.finest("Setting InputDevice Double Delay:", repeatDelay);
        
        InputDevice.doublePressedDelay = (long) (doublePressedDelay * 1_000_000_000L);
    }
    
    protected final Map<E, I> inputMap;
    
    protected       boolean running;
    protected final Thread  thread;
    
    public InputDevice()
    {
        this.inputMap = generateMap();
        
        this.running = true;
        this.thread  = new Thread(this::runInThread, toString());
        this.thread.start();
    }
    
    protected abstract @NotNull Map<E, I> generateMap();
    
    protected void runInThread()
    {
        long t, dt, last = System.nanoTime();
        
        while (this.running)
        {
            t  = System.nanoTime();
            dt = t - last;
            
            if (dt >= 1_000_000)
            {
                last = t;
                postEvents(t, dt);
            }
            
            Thread.yield();
        }
    }
    
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
            
            if (input._action != input.action)
            {
                if (input._action == GLFW_PRESS)
                {
                    input.down     = true;
                    input.held     = true;
                    input.downTime = time + InputDevice.repeatDelay;
                }
                else if (input._action == GLFW_RELEASE)
                {
                    input.up       = true;
                    input.held     = false;
                    input.downTime = Long.MAX_VALUE;
                }
                input.action = input._action;
            }
            if (input.action == GLFW_REPEAT || time - input.downTime > InputDevice.repeatFrequency)
            {
                input.downTime += InputDevice.repeatFrequency;
                input.repeat = true;
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
    
    public static class Input<E extends Enum<E>>
    {
        protected final E input;
        
        protected Window _window;
        
        protected boolean down, up, held, repeat;
        
        protected int _action, action;
        
        protected long pressTime = 0;
        protected long downTime  = Long.MAX_VALUE;
        
        public Input(E input)
        {
            this.input = input;
        }
    
        @Override
        public String toString()
        {
            return "Input{" + this.input.getClass().getSimpleName() + '.' + this.input + '}';
        }
    
        /**
         * @return The enum value associated with this input.
         */
        public E input()
        {
            return this.input;
        }
    
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
            return predicate.test(Modifier.activeMods());
        }
    }
}

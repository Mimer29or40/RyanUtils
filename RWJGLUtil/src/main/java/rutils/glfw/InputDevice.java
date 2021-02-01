package rutils.glfw;

import rutils.Logger;

import java.util.concurrent.CountDownLatch;

public abstract class InputDevice
{
    private static final Logger LOGGER = new Logger();
    
    protected static long holdFrequency      = 1_000_000L;
    protected static long doublePressedDelay = 200_000_000L;
    
    /**
     * @return The frequency, in seconds, that a "Held" event will be generated while an ButtonInput is down.
     */
    public static double holdFrequency()
    {
        return InputDevice.holdFrequency / 1_000_000_000D;
    }
    
    /**
     * Sets the frequency, in seconds, that a "Held" event will be generated while an ButtonInput is down.
     *
     * @param holdFrequency The frequency, in seconds, that a "Held" event will be generated while an ButtonInput is down.
     */
    public static void holdFrequency(double holdFrequency)
    {
        InputDevice.LOGGER.finest("Setting InputDevice Hold Frequency:", holdFrequency);
        
        InputDevice.holdFrequency = (long) (holdFrequency * 1_000_000_000L);
    }
    
    /**
     * @return The delay, in seconds, before an ButtonInput is pressed twice to be a double pressed.
     */
    public static double doublePressedDelay()
    {
        return InputDevice.doublePressedDelay / 1_000_000_000D;
    }
    
    /**
     * Sets the delay, in seconds, before an ButtonInput is pressed twice to be a double pressed.
     *
     * @param doublePressedDelay The delay, in seconds, before an ButtonInput is pressed twice to be a double pressed.
     */
    public static void doublePressedDelay(double doublePressedDelay)
    {
        InputDevice.LOGGER.finest("Setting InputDevice Double Delay:", doublePressedDelay);
        
        InputDevice.doublePressedDelay = (long) (doublePressedDelay * 1_000_000_000L);
    }
    
    private boolean running;
    
    protected final CountDownLatch threadStart;
    private final   Thread         thread;
    
    InputDevice(String threadName)
    {
        this.running = true;
        
        this.threadStart = new CountDownLatch(1);
        this.thread      = new Thread(this::runInThread, threadName);
        this.thread.start();
    }
    
    protected void runInThread()
    {
        try
        {
            this.threadStart.await();
        }
        catch (InterruptedException e)
        {
            return;
        }
        
        long t, dt, last = System.nanoTime();
        
        while (this.running)
        {
            t  = System.nanoTime();
            dt = t - last;
            
            try
            {
                if (dt >= 100_000) postEvents(last = t, dt);
            }
            catch (Throwable throwable)
            {
                InputDevice.LOGGER.severe(throwable);
            }
            
            Thread.yield();
        }
    }
    
    /**
     * This method is called by the window it is attached to. This is where
     * events should be posted to when something has changed.
     *
     * @param time      The system time in nanoseconds.
     * @param deltaTime The time in nanoseconds since the last time this method was called.
     */
    protected abstract void postEvents(long time, long deltaTime);
    
    public void destroy()
    {
        this.running = false;
        try
        {
            this.thread.interrupt();
            this.thread.join();
        }
        catch (InterruptedException ignored) { }
    }
    
    protected static class Input
    {
        protected int state = -1, _state = -1;
        
        protected boolean held;
        
        protected long holdTime = Long.MAX_VALUE, pressTime;
        
        protected Window _window;
    }
}

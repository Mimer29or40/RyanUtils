package rutils;

import java.util.ArrayDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.function.Function;

/**
 * This class can be used to block one thread until another thread has finished creating an object.
 * <p>
 * I used this pattern to ask the main thread to create a GLFW window and wait for it to finish.
 *
 * // TODO - Clean this up.
 */
public class WorkBlocker<B, R> implements Runnable
{
    private static final Logger LOGGER = new Logger();
    
    public boolean running = false;
    
    private final ArrayDeque<B>       bQueue = new ArrayDeque<>();
    private final SynchronousQueue<R> rQueue = new SynchronousQueue<>();
    
    public Function<B, R> transformFunc;
    
    /**
     * This function should be called on the thread that will do the work.
     */
    public void doWork() throws InterruptedException, NoTransformationException
    {
        B builder = this.bQueue.poll();
        
        if (builder != null)
        {
            if (this.transformFunc == null) throw new NoTransformationException();
            
            this.rQueue.put(this.transformFunc.apply(builder));
        }
    }
    
    /**
     * Asks the WorkBlocker to create an object.
     *
     * @param builder The builder object to construct the new object.
     * @return The newly constructed object.
     */
    public R askForObject(B builder)
    {
        this.bQueue.add(builder);
        
        try
        {
            return this.rQueue.take();
        }
        catch (InterruptedException e)
        {
            WorkBlocker.LOGGER.severe("Object could not be created.");
            WorkBlocker.LOGGER.severe(e);
        }
        return null;
    }
    
    /**
     * When an object implementing interface {@code Runnable} is used
     * to create a thread, starting the thread causes the object's
     * {@code run} method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method {@code run} is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run()
    {
        this.running = true;
        
        try
        {
            while (this.running)
            {
                try
                {
                    doWork();
                }
                catch (InterruptedException e)
                {
                    WorkBlocker.LOGGER.severe(e);
                }
                Thread.yield();
            }
        }
        catch (NoTransformationException e)
        {
            WorkBlocker.LOGGER.severe(e);
        }
    }
    
    /**
     * Signals that the WorkBlocker was not given a transformation function.
     */
    public static final class NoTransformationException extends Exception
    {
        
        /**
         * Constructs an {@code EOFException} with {@code null}
         * as its error detail message.
         */
        public NoTransformationException()
        {
            super();
        }
        
        /**
         * Constructs an {@code EOFException} with the specified detail
         * message. The string {@code s} may later be retrieved by the
         * <code>{@link java.lang.Throwable#getMessage}</code> method of class
         * {@code java.lang.Throwable}.
         *
         * @param s the detail message.
         */
        public NoTransformationException(String s)
        {
            super(s);
        }
    }
}

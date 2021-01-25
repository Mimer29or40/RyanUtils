package rutils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rutils.group.Pair;

import java.util.ArrayDeque;
import java.util.concurrent.SynchronousQueue;
import java.util.function.Supplier;

/**
 * This class is used to make another thread preform a task instead of the one calling the function.
 * <p>
 * The calling thread can either wait for the worker thread to finish or not block.
 * <p>
 * // TODO - Clean this up.
 */
public class TaskDelegator
{
    private static final Logger LOGGER = new Logger();
    
    private final ArrayDeque<Pair<Runnable, Boolean>> runTasks = new ArrayDeque<>();
    
    private final ArrayDeque<Runnable>                       waitRunTasks   = new ArrayDeque<>();
    private final SynchronousQueue<Pair<Integer, Exception>> waitRunResults = new SynchronousQueue<>();
    
    private final ArrayDeque<Supplier<Object>>              waitReturnTasks   = new ArrayDeque<>();
    private final SynchronousQueue<Pair<Object, Exception>> waitReturnResults = new SynchronousQueue<>();
    
    private Thread thread = null;
    
    public void setThread()
    {
        if (this.thread != null) TaskDelegator.LOGGER.warning("Cannot reset thread.");
        if (this.thread == null) this.thread = Thread.currentThread();
    }
    
    /**
     * Runs a task on the TaskDelegator's thread. Non-blocking.
     *
     * @param task          The task to complete.
     * @param passException If the TaskDelegator should pass the exception to the calling thread. If false the exception if effectively ignored.
     */
    public void runTask(@NotNull Runnable task, boolean passException)
    {
        if (Thread.currentThread() == this.thread)
        {
            task.run();
            return;
        }
        
        this.runTasks.offer(new Pair<>(task, passException));
    }
    
    /**
     * Runs a task on the TaskDelegator's thread. Non-blocking.
     *
     * @param task The task to complete.
     */
    public void runTask(@NotNull Runnable task)
    {
        runTask(task, false);
    }
    
    /**
     * Runs a task on the TaskDelegator's thread. Blocks until the task is completed.
     *
     * @param task The task to complete.
     */
    public void waitRunTask(@NotNull Runnable task)
    {
        if (Thread.currentThread() == this.thread)
        {
            task.run();
            return;
        }
        
        this.waitRunTasks.offer(task);
        
        try
        {
            Pair<Integer, Exception> result = this.waitRunResults.take();
            if (result.a != 0) throw new RuntimeException(result.b);
        }
        catch (InterruptedException e)
        {
            TaskDelegator.LOGGER.warning("Run task was interrupted.");
        }
    }
    
    /**
     * Runs a supplier on the TaskDelegator's thread. Blocks until the task is completed.
     *
     * @param task The supplier to run.
     * @param <T>  The type of the supplier.
     * @return The supplied object or null if the thread was interrupted
     */
    @SuppressWarnings("unchecked")
    public <T> @Nullable T waitReturnTask(@NotNull Supplier<T> task)
    {
        if (Thread.currentThread() == this.thread) return task.get();
        
        this.waitReturnTasks.offer((Supplier<Object>) task);
        
        try
        {
            Pair<Object, Exception> result = this.waitReturnResults.take();
            if (result.b != null) throw new RuntimeException(result.b);
            return (T) result.a;
        }
        catch (InterruptedException e)
        {
            TaskDelegator.LOGGER.warning("Return task was interrupted.");
        }
        return null;
    }
    
    /**
     * This method is called once per "frame" on the desired thread.
     */
    public void runTasks()
    {
        this.thread = Thread.currentThread();
        
        while (!this.runTasks.isEmpty())
        {
            Pair<Runnable, Boolean> task = this.runTasks.poll();
            try
            {
                task.a.run();
            }
            catch (Exception e)
            {
                if (task.b)
                {
                    throw new RuntimeException(e);
                }
                else
                {
                    TaskDelegator.LOGGER.severe(e);
                }
            }
        }
        
        while (!this.waitRunTasks.isEmpty())
        {
            Runnable task = this.waitRunTasks.poll();
            
            int       result = 0;
            Exception except = null;
            try
            {
                task.run();
            }
            catch (Exception e)
            {
                result = 1;
                except = e;
            }
            this.waitRunResults.offer(new Pair<>(result, except));
        }
        
        while (!this.waitReturnTasks.isEmpty())
        {
            Supplier<Object> task = waitReturnTasks.poll();
            
            Object    result = null;
            Exception except = null;
            try
            {
                result = task.get();
            }
            catch (Exception e)
            {
                except = e;
            }
            this.waitReturnResults.offer(new Pair<>(result, except));
        }
    }
}

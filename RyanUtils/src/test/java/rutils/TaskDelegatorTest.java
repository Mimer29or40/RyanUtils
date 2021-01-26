package rutils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskDelegatorTest
{
    private static final Logger LOGGER = new Logger();
    
    @Test
    void setThread()
    {
        TaskDelegator taskDelegator = new TaskDelegator();
        
        taskDelegator.setThread();
        
        assertEquals(taskDelegator.thread, Thread.currentThread());
    }
    
    @Test
    void runTask() throws InterruptedException
    {
        Thread other;
        
        TaskDelegator taskDelegator1 = new TaskDelegator();
        
        taskDelegator1.runTask(() -> LOGGER.info("Running Task 1 from main Thread"));
        
        other = new Thread(() -> {
            taskDelegator1.runTask(() -> LOGGER.info("Running Task 2 from other Thread"));
            taskDelegator1.setThread();
            taskDelegator1.runTasks();
        });
        other.start();
        other.join();
        
        TaskDelegator taskDelegator2 = new TaskDelegator();
        taskDelegator2.runTask(() -> {
            throw new RuntimeException("Task 1 Exception");
        }, false);
        
        taskDelegator2.runTask(() -> {
            throw new RuntimeException("Task 2 Exception");
        }, true);
        
        other = new Thread(() -> {
            taskDelegator2.setThread();
            assertThrows(RuntimeException.class, taskDelegator2::runTasks);
        });
        other.start();
        other.join();
    }
    
    @Test
    void waitRunTask() throws InterruptedException
    {
        TaskDelegator taskDelegator = new TaskDelegator();
        
        Thread other = new Thread(() -> taskDelegator.waitRunTask(() -> LOGGER.info("Waiting for Task to be completed.")));
        other.start();
        
        taskDelegator.setThread();
        for (int i = 0; i < 10; i++)
        {
            taskDelegator.runTasks();
            Thread.sleep(1);
        }
        
        other.join();
    }
    
    @Test
    void waitReturnTask() throws InterruptedException
    {
        TaskDelegator taskDelegator = new TaskDelegator();
        
        Thread other = new Thread(() -> {
            Boolean value = taskDelegator.waitReturnTask(() -> true);
            LOGGER.info("Returned Value: " + value);
        });
        other.start();
        
        taskDelegator.setThread();
        for (int i = 0; i < 10; i++)
        {
            taskDelegator.runTasks();
            Thread.sleep(1);
        }
        
        other.join();
    }
}

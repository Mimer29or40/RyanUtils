package rutils.glfw;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

public class WaitUntilThreadIsDone
{
    public static final Object lock = new Object();
    
    public static Queue<String>            queue1            = new ArrayDeque<>();
    public static SynchronousQueue<Thing>  synchronousQueue2 = new SynchronousQueue<>();
    
    public static void main(String[] args) throws InterruptedException
    {
        // SynchronousQueue
        
        new Thread(() -> {
            try
            {
                for (int i = 0; i < 10; i++)
                {
                    String name = "Name+" + i;
                    
                    Thing thing = createThing(name);
                    
                    System.out.println(thing.name);
                    
                    Thread.yield();
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }, "Thread").start();
        
        int time = 0;
        while (time < 1_000_000_000)
        {
            processThings();
            
            time++;
            
            Thread.yield();
        }
    }
    
    private static Thing createThing(String name) throws InterruptedException
    {
        queue1.add(name);
        
        return synchronousQueue2.take();
    }
    
    private static void processThings() throws InterruptedException
    {
        String name = queue1.poll();
        if (name != null)
        {
            Thread.sleep(1000);
            synchronousQueue2.put(new Thing(name));
        }
    }
    
    public static class Thing
    {
        public final String name;
        
        public Thing(String name) {this.name = name;}
    }
}

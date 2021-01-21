package rutils.glfw;

import rutils.Logger;

class WindowMain extends Window
{
    private static final Logger LOGGER = new Logger();
    
    private static final Builder BUILDER = new Builder().name("Main")
                                                        .visible(false)
                                                        .focused(false)
                                                        .focusOnShow(false);
    
    WindowMain()
    {
        super(WindowMain.BUILDER);
    }
    
    @Override
    protected void runInThread()
    {
        try
        {
            WindowMain.LOGGER.fine("Starting");
            
            this.taskDelegator.setThread();
            
            while (!this.close && this.open)
            {
                this.taskDelegator.runTasks();
                
                Thread.yield();
            }
        }
        catch (Throwable cause)
        {
            WindowMain.LOGGER.severe(cause);
        }
        finally
        {
            WindowMain.LOGGER.fine("Stopping");
            
            destroy();
        }
    }
}

package rutils.glfw;

import rutils.Logger;

public abstract class GLFWApplicationTest
{
    private static final Logger LOGGER = new Logger();
    
    protected Window window;
    
    protected abstract Window createWindow();
    
    protected abstract void init();
    
    protected abstract void draw(double time, double deltaT);
    
    protected abstract void beforeEventLoop();
    
    public void run()
    {
        try
        {
            GLFWApplicationTest.LOGGER.info("Test Starting");
            
            GLFW.init();
            
            GLFW.EVENT_BUS.register(this);
            
            this.window = createWindow();
            this.window.onWindowInit(this::init);
            this.window.onWindowDraw(this::draw);
            this.window.open();
            
            beforeEventLoop();
            
            GLFW.eventLoop();
        }
        finally
        {
            GLFWApplicationTest.LOGGER.info("Test Stopping");
            
            GLFW.EVENT_BUS.unregister(this);
            
            GLFW.destroy();
        }
    }
}

package rutils.glfw.event.events;

import rutils.glfw.Window;

public class GLFWEventMouseEntered extends GLFWEventMouse
{
    private final boolean entered;
    
    public GLFWEventMouseEntered(Window window, boolean entered)
    {
        super(window);
        
        this.entered = entered;
    }
    
    @Property
    public boolean entered()
    {
        return this.entered;
    }
}

package rutils.glfw.second.event;

import rutils.glfw.second.Mouse;

public class GLFWEventMouseEntered extends GLFWEventMouse
{
    private final boolean entered;
    
    public GLFWEventMouseEntered(Mouse mouse, boolean entered)
    {
        super(mouse);
        
        this.entered = entered;
    }
    
    @Property
    public boolean entered()
    {
        return this.entered;
    }
}

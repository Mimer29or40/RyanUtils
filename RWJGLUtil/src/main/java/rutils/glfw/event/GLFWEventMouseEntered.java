package rutils.glfw.event;

import rutils.glfw.Mouse;

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

package rutils.glfw.event;

import rutils.glfw.Window;

public class GLFWEventMouseEntered extends GLFWEventMouse
{
    private final boolean entered;
    
    public GLFWEventMouseEntered(Window window, int mods, boolean entered)
    {
        super(window, mods);
        
        this.entered = entered;
    }
    
    @Property
    public boolean entered()
    {
        return this.entered;
    }
}

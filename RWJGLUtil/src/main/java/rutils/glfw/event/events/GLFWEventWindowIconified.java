package rutils.glfw.event.events;

import rutils.glfw.Window;

public class GLFWEventWindowIconified extends GLFWEventWindow
{
    private final boolean iconified;
    
    public GLFWEventWindowIconified(Window window, boolean iconified)
    {
        super(window);
    
        this.iconified = iconified;
    }
    
    @Property
    public boolean iconified()
    {
        return this.iconified;
    }
}

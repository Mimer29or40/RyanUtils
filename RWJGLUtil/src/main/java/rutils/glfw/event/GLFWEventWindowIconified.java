package rutils.glfw.event;

import rutils.glfw.Window;

@SuppressWarnings("unused")
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

package rutils.glfw.second.event;

import rutils.glfw.second.Window;

public class GLFWEventWindow extends GLFWEvent
{
    private final Window window;
    
    public GLFWEventWindow(Window window)
    {
        this.window = window;
    }
    
    @Property
    public Window window()
    {
        return this.window;
    }
}

package rutils.glfw.event;

import rutils.glfw.Window;

@SuppressWarnings("unused")
public class GLFWEventWindow extends GLFWEvent
{
    private final Window window;
    
    public GLFWEventWindow(Window window)
    {
        this.window = window;
    }
    
    @Property(printName = false)
    public Window window()
    {
        return this.window;
    }
}

package rutils.glfw.event;

import rutils.glfw.Window;

@SuppressWarnings("unused")
public class GLFWEventMouse extends GLFWEvent
{
    private final Window window;
    
    public GLFWEventMouse(Window window)
    {
        this.window = window;
    }
    
    @Property(printName = false)
    public Window window()
    {
        return this.window;
    }
}

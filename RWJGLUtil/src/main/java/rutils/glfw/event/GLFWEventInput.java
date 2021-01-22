package rutils.glfw.event;

import rutils.glfw.Window;

public class GLFWEventInput extends GLFWEvent
{
    private final Window window;
    
    public GLFWEventInput(Window window)
    {
        this.window = window;
    }
    
    @Property(printName = false)
    public Window window()
    {
        return this.window;
    }
}

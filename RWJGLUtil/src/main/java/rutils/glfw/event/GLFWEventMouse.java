package rutils.glfw.event;

import rutils.glfw.Mouse;

@SuppressWarnings("unused")
public class GLFWEventMouse extends GLFWEvent
{
    private final Mouse mouse;
    
    public GLFWEventMouse(Mouse mouse)
    {
        this.mouse = mouse;
    }
    
    @Property(printName = false)
    public Mouse mouse()
    {
        return this.mouse;
    }
}

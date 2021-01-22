package rutils.glfw.event;

import rutils.glfw.Window;

@SuppressWarnings("unused")
public class GLFWEventInput extends GLFWEvent
{
    private final Window window;
    private final int    mods;
    
    public GLFWEventInput(Window window, int mods)
    {
        this.window = window;
        this.mods   = mods;
    }
    
    @Property(printName = false)
    public Window window()
    {
        return this.window;
    }
    
    @Property
    public int mods()
    {
        return this.mods;
    }
}

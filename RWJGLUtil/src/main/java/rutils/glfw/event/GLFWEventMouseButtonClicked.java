package rutils.glfw.event;

import org.joml.Vector2d;
import rutils.glfw.Mouse;

@SuppressWarnings("unused")
public class GLFWEventMouseButtonClicked extends GLFWEventMouseButton
{
    private final boolean doubleClicked;
    
    public GLFWEventMouseButtonClicked(Mouse mouse, Mouse.Button button, int mods, Vector2d pos, boolean doubleClicked)
    {
        super(mouse, button, mods, pos);
        
        this.doubleClicked = doubleClicked;
    }
    
    @Property
    public boolean doubleClicked()
    {
        return this.doubleClicked;
    }
}

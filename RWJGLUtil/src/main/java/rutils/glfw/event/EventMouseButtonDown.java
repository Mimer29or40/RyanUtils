package rutils.glfw.event;

import org.joml.Vector2dc;
import rutils.glfw.Mouse;
import rutils.glfw.Window;

public interface EventMouseButtonDown extends EventMouseButton
{
    final class _EventMouseButtonDown extends AbstractEventMouseButton implements EventMouseButtonDown
    {
        private _EventMouseButtonDown(Window window, Mouse.Button button, Vector2dc pos)
        {
            super(window, button, pos);
        }
    }
    
    static EventMouseButtonDown create(Window window, Mouse.Button button, Vector2dc pos)
    {
        return new _EventMouseButtonDown(window, button, pos);
    }
}

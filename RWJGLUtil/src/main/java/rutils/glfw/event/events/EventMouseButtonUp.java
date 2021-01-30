package rutils.glfw.event.events;

import org.joml.Vector2dc;
import rutils.glfw.Mouse;
import rutils.glfw.Window;

public interface EventMouseButtonUp extends EventMouseButton
{
    final class _EventMouseButtonUp extends AbstractEventMouseButton implements EventMouseButtonUp
    {
        private _EventMouseButtonUp(Window window, Mouse.Button button, Vector2dc pos)
        {
            super(window, button, pos);
        }
    }
    
    static EventMouseButtonUp create(Window window, Mouse.Button button, Vector2dc pos)
    {
        return new _EventMouseButtonUp(window, button, pos);
    }
}

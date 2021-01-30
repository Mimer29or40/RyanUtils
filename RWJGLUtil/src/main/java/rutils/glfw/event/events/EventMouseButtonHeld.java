package rutils.glfw.event.events;

import org.joml.Vector2dc;
import rutils.glfw.Mouse;
import rutils.glfw.Window;

public interface EventMouseButtonHeld extends EventMouseButton
{
    final class _EventMouseButtonHeld extends AbstractEventMouseButton implements EventMouseButtonHeld
    {
        private _EventMouseButtonHeld(Window window, Mouse.Button button, Vector2dc pos)
        {
            super(window, button, pos);
        }
    }
    
    static EventMouseButtonHeld create(Window window, Mouse.Button button, Vector2dc pos)
    {
        return new _EventMouseButtonHeld(window, button, pos);
    }
}

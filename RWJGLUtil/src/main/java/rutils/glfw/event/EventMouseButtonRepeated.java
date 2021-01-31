package rutils.glfw.event;

import org.joml.Vector2dc;
import rutils.glfw.Mouse;
import rutils.glfw.Window;

public interface EventMouseButtonRepeated extends EventInputDeviceInputRepeated, EventMouseButton
{
    final class _EventMouseButtonRepeated extends AbstractEventMouseButton implements EventMouseButtonRepeated
    {
        private _EventMouseButtonRepeated(Window window, Mouse.Button button, Vector2dc pos)
        {
            super(window, button, pos);
        }
    }
    
    static EventMouseButtonRepeated create(Window window, Mouse.Button button, Vector2dc pos)
    {
        return new _EventMouseButtonRepeated(window, button, pos);
    }
}

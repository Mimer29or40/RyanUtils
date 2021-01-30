package rutils.glfw.event.events;

import org.joml.Vector2dc;
import rutils.glfw.Mouse;
import rutils.glfw.Window;
import rutils.glfw.event.EventProperty;

public interface EventMouseButton extends EventMouse
{
    @EventProperty(printName = false)
    Mouse.Button button();
    
    @EventProperty
    Vector2dc pos();
    
    default double x()
    {
        return pos().x();
    }
    
    default double y()
    {
        return pos().y();
    }
    
    final class _EventMouseButton extends AbstractEventMouseButton implements EventMouseButton
    {
        private _EventMouseButton(Window window, Mouse.Button button, Vector2dc pos)
        {
            super(window, button, pos);
        }
    }
    
    static EventMouseButton create(Window window, Mouse.Button button, Vector2dc pos)
    {
        return new _EventMouseButton(window, button, pos);
    }
}

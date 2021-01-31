package rutils.glfw.event;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import rutils.glfw.Window;
import rutils.glfw.EventProperty;

public interface EventMouseScrolled extends EventMouse
{
    @EventProperty
    Vector2dc scroll();
    
    default double dx()
    {
        return scroll().x();
    }
    
    default double dy()
    {
        return scroll().y();
    }
    
    final class _EventMouseScrolled extends AbstractEventInputDevice implements EventMouseScrolled
    {
        private final Vector2d scroll;
        
        private _EventMouseScrolled(Window window, Vector2dc scroll)
        {
            super(window);
            
            this.scroll = new Vector2d(scroll);
        }
        
        @Override
        public Vector2dc scroll()
        {
            return this.scroll;
        }
    }
    
    static EventMouseScrolled create(Window window, Vector2dc pos)
    {
        return new EventMouseScrolled._EventMouseScrolled(window, pos);
    }
}

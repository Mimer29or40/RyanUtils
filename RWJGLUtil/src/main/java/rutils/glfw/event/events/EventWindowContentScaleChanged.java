package rutils.glfw.event.events;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import rutils.glfw.Window;
import rutils.glfw.event.EventProperty;

public interface EventWindowContentScaleChanged extends EventWindow
{
    @EventProperty
    Vector2dc contentScale();
    
    default double contentScaleX()
    {
        return contentScale().x();
    }
    
    default double contentScaleY()
    {
        return contentScale().y();
    }
    
    @EventProperty
    Vector2dc rel();
    
    default double dx()
    {
        return rel().x();
    }
    
    default double dy()
    {
        return rel().y();
    }
    
    final class _EventWindowContentScaleChanged extends AbstractEventWindow implements EventWindowContentScaleChanged
    {
        private final Vector2d scale;
        private final Vector2d rel;
    
        private _EventWindowContentScaleChanged(Window window, Vector2d scale, Vector2d rel)
        {
            super(window);
        
            this.scale = new Vector2d(scale);
            this.rel   = new Vector2d(rel);
        }
    
        @Override
        public Vector2dc contentScale()
        {
            return this.scale;
        }
    
        @Override
        public Vector2dc rel()
        {
            return this.rel;
        }
    }
    
    static EventWindowContentScaleChanged create(Window window, Vector2d scale, Vector2d rel)
    {
        return new _EventWindowContentScaleChanged(window, scale, rel);
    }
}

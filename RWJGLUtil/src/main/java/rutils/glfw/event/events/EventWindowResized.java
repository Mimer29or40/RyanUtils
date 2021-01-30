package rutils.glfw.event.events;

import org.joml.Vector2i;
import org.joml.Vector2ic;
import rutils.glfw.Window;
import rutils.glfw.event.EventProperty;

public interface EventWindowResized extends EventWindow
{
    @EventProperty
    Vector2ic size();
    
    default int width()
    {
        return size().x();
    }
    
    default int height()
    {
        return size().y();
    }
    
    @EventProperty
    Vector2ic rel();
    
    default int dWidth()
    {
        return rel().x();
    }
    
    default int dHeight()
    {
        return rel().y();
    }
    
    final class _EventWindowResized extends AbstractEventWindow implements EventWindowResized
    {
        private final Vector2i size;
        private final Vector2i rel;
        
        private _EventWindowResized(Window window, Vector2ic size, Vector2ic rel)
        {
            super(window);
            
            this.size = new Vector2i(size);
            this.rel  = new Vector2i(rel);
        }
        
        @Override
        public Vector2ic size()
        {
            return this.size;
        }
        
        @Override
        public Vector2ic rel()
        {
            return this.rel;
        }
    }
    
    static EventWindowResized create(Window window, Vector2ic pos, Vector2ic rel)
    {
        return new _EventWindowResized(window, pos, rel);
    }
}

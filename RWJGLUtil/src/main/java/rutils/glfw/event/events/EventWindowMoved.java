package rutils.glfw.event.events;

import org.joml.Vector2i;
import org.joml.Vector2ic;
import rutils.glfw.Window;
import rutils.glfw.event.EventProperty;

public interface EventWindowMoved extends EventWindow
{
    @EventProperty
    Vector2ic pos();
    
    default int x()
    {
        return pos().x();
    }
    
    default int y()
    {
        return pos().y();
    }
    
    @EventProperty
    Vector2ic rel();
    
    default int dx()
    {
        return rel().x();
    }
    
    default int dy()
    {
        return rel().y();
    }
    
    final class _EventWindowMoved extends AbstractEventWindow implements EventWindowMoved
    {
        private final Vector2i pos;
        private final Vector2i rel;
        
        private _EventWindowMoved(Window window, Vector2ic pos, Vector2ic rel)
        {
            super(window);
            
            this.pos = new Vector2i(pos);
            this.rel = new Vector2i(rel);
        }
        
        @Override
        public Vector2ic pos()
        {
            return this.pos;
        }
        
        @Override
        public Vector2ic rel()
        {
            return this.rel;
        }
    }
    
    static EventWindowMoved create(Window window, Vector2ic pos, Vector2ic rel)
    {
        return new _EventWindowMoved(window, pos, rel);
    }
}

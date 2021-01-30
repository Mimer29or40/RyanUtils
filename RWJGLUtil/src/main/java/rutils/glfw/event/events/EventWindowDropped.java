package rutils.glfw.event.events;

import rutils.glfw.Window;
import rutils.glfw.event.EventProperty;

import java.nio.file.Path;

public interface EventWindowDropped extends EventWindow
{
    @EventProperty
    Path[] paths();
    
    final class _EventWindowDropped extends AbstractEventWindow implements EventWindowDropped
    {
        private final Path[] paths;
    
        private _EventWindowDropped(Window window, Path[] paths)
        {
            super(window);
        
            this.paths = paths;
        }
    
        @Override
        public Path[] paths()
        {
            return this.paths;
        }
    }
    
    static EventWindowDropped create(Window window, Path[] paths)
    {
        return new _EventWindowDropped(window, paths);
    }
}

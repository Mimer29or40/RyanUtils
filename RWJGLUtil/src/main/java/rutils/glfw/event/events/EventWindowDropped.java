package rutils.glfw.event.events;

import rutils.glfw.Window;

import java.nio.file.Path;

public class EventWindowDropped extends EventWindow
{
    private final Path[] paths;
    
    public EventWindowDropped(Window window, Path[] paths)
    {
        super(window);
        
        this.paths = paths;
    }
    
    @Property
    public Path[] paths()
    {
        return this.paths;
    }
}

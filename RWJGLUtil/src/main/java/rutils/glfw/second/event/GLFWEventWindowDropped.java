package rutils.glfw.second.event;

import rutils.glfw.second.Window;

import java.nio.file.Path;

@SuppressWarnings("unused")
public class GLFWEventWindowDropped extends GLFWEventWindow
{
    private final Path[] paths;
    
    public GLFWEventWindowDropped(Window window, Path[] paths)
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

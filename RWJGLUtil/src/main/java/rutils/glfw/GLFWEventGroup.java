package rutils.glfw;

import java.util.ArrayList;

public class GLFWEventGroup
{
    private final ArrayList<String>         events = new ArrayList<>();
    private final ArrayList<GLFWEventGroup> groups = new ArrayList<>();
    
    private final ArrayList<String> cache   = new ArrayList<>();
    private       boolean           rebuild = true;
    
    @Override
    public String toString()
    {
        return "GLFWEventGroup{" + this.events + '}';
    }
    
    public GLFWEventGroup(Object... eventTypes)
    {
        for (Object type : eventTypes)
        {
            if (type instanceof String)
            {
                this.events.add((String) type);
            }
            else if (type instanceof GLFWEventGroup)
            {
                this.groups.add((GLFWEventGroup) type);
            }
        }
    }
    
    public Iterable<String> events()
    {
        if (this.rebuild)
        {
            this.cache.clear();
            
            this.cache.addAll(this.events);
            
            for (GLFWEventGroup group : this.groups)
            {
                for (String event : group.events())
                {
                    this.cache.add(event);
                }
            }
            
            this.rebuild = false;
        }
        return this.cache;
    }
    
    public void add(String event)
    {
        this.rebuild = true;
        
        this.events.add(event);
    }
    
    public void add(GLFWEventGroup eventGroup)
    {
        this.rebuild = true;
        
        this.groups.add(eventGroup);
    }
    
    public static final GLFWEventGroup WINDOW       = new GLFWEventGroup();
    public static final GLFWEventGroup MOUSE_BUTTON = new GLFWEventGroup();
    public static final GLFWEventGroup MOUSE        = new GLFWEventGroup();
    public static final GLFWEventGroup KEYBOARD_KEY = new GLFWEventGroup();
    public static final GLFWEventGroup KEYBOARD     = new GLFWEventGroup();
    public static final GLFWEventGroup INPUT        = new GLFWEventGroup();
}

package rutils.glfw.event.events;

import rutils.glfw.Window;

public class EventKeyboardTyped extends EventKeyboard
{
    private final String typed;
    
    public EventKeyboardTyped(Window window, String typed)
    {
        super(window);
    
        this.typed = typed;
    }
    
    @Property
    public String typed()
    {
        return this.typed;
    }
}

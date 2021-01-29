package rutils.glfw.event.events;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public class EventKeyboardKey extends EventKeyboard
{
    private final Keyboard.Key key;
    
    public EventKeyboardKey(Window window, Keyboard.Key key)
    {
        super(window);
        
        this.key = key;
    }
    
    @Property(printName = false)
    public Keyboard.Key key()
    {
        return this.key;
    }
}

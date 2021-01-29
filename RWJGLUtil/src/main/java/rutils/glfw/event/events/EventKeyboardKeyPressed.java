package rutils.glfw.event.events;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public class EventKeyboardKeyPressed extends EventKeyboardKey
{
    private final boolean doublePressed;
    
    public EventKeyboardKeyPressed(Window window, Keyboard.Key key, boolean doublePressed)
    {
        super(window, key);
    
        this.doublePressed = doublePressed;
    }
    
    @Property
    public boolean doublePressed()
    {
        return this.doublePressed;
    }
}

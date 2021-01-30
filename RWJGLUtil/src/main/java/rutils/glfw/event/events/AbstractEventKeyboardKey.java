package rutils.glfw.event.events;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

public abstract class AbstractEventKeyboardKey extends AbstractEventInput implements EventKeyboardKey
{
    private final Keyboard.Key key;
    
    public AbstractEventKeyboardKey(Window window, Keyboard.Key key)
    {
        super(window);
        
        this.key = key;
    }
    
    @Override
    public Keyboard.Key key()
    {
        return this.key;
    }
}

package rutils.glfw.event;

import rutils.glfw.Keyboard;
import rutils.glfw.Window;

abstract class AbstractEventKeyboardKey extends AbstractEventInput implements EventKeyboardKey
{
    private final Keyboard.Key key;
    
    AbstractEventKeyboardKey(Window window, Keyboard.Key key)
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

package rutils.glfw.event;

import org.joml.Vector2dc;
import rutils.glfw.Mouse;
import rutils.glfw.Window;
import rutils.glfw.EventProperty;

public interface EventMouseButtonPressed extends EventMouseButton
{
    @EventProperty
    boolean doublePressed();
    
    final class _EventMouseButtonPressed extends AbstractEventMouseButton implements EventMouseButtonPressed
    {
        private final boolean doublePressed;
        
        private _EventMouseButtonPressed(Window window, Mouse.Button button, Vector2dc pos, boolean doublePressed)
        {
            super(window, button, pos);
            
            this.doublePressed = doublePressed;
        }
        
        @Override
        public boolean doublePressed()
        {
            return this.doublePressed;
        }
    }
    
    static EventMouseButtonPressed create(Window window, Mouse.Button button, Vector2dc pos, boolean doublePressed)
    {
        return new _EventMouseButtonPressed(window, button, pos, doublePressed);
    }
}

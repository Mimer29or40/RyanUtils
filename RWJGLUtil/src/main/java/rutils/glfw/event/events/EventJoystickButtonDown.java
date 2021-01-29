package rutils.glfw.event.events;

public interface EventJoystickButtonDown extends EventJoystickButton
{
    static EventJoystickButtonDown create()
    {
        return new EventJoystickButtonDown()
        {
        
        };
    }
}

package rutils.glfw.event;

import rutils.glfw.Gamepad;

public abstract class AbstractEventGamepadButton extends AbstractEventJoystickButton implements EventGamepadButton
{
    private final Gamepad.Button button;
    
    public AbstractEventGamepadButton(Gamepad gamepad, Gamepad.Button button)
    {
        super(gamepad, button.id());
    
        this.button = button;
    }
    
    public Gamepad gamepad()
    {
        return (Gamepad) this.joystick;
    }
    
    public Gamepad.Button gamepadButton()
    {
        return this.button;
    }
}

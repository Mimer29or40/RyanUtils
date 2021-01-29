package rutils.glfw;

import org.jetbrains.annotations.NotNull;
import rutils.Logger;
import rutils.glfw.event.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;

public class Joystick extends InputDevice
{
    private static final Logger LOGGER = new Logger();
    
    private final int jid;
    
    private final String name;
    
    private final boolean gamepad;
    
    Map<Integer, Axis>   axisMap;
    Map<Integer, Button> buttonMap;
    
    Joystick(int jid, boolean gamepad)
    {
        this.jid = jid;
        
        this.name = glfwGetJoystickName(this.jid);
        
        this.gamepad = gamepad;
        
    }
    
    @Override
    public String toString()
    {
        return "Joystick{" + this.jid + ", name='" + this.name + '\'' + '}';
    }
    
    @Override
    protected @NotNull String threadName()
    {
        return getClass().getSimpleName() + "-" + this.jid;
    }
    
    @Override
    protected void fillInputMaps()
    {
        this.axisMap = new LinkedHashMap<>();
        FloatBuffer axes = Objects.requireNonNull(glfwGetJoystickAxes(this.jid), "Joystick is not connected.");
        for (int i = 0, n = axes.remaining(); i < n; i++) this.axisMap.put(i, new Axis(axes.get(i)));
        
        this.buttonMap = new LinkedHashMap<>();
        ByteBuffer buttons = Objects.requireNonNull(glfwGetJoystickButtons(this.jid), "Joystick is not connected.");
        for (int i = 0, n = buttons.remaining(); i < n; i++) this.buttonMap.put(i, new Button(buttons.get(i)));
    }
    
    public String name()
    {
        return this.name;
    }
    
    public boolean isGamepad()
    {
        return this.gamepad;
    }
    
    /**
     * This method is called by the window it is attached to. This is where
     * events should be posted to when something has changed.
     *
     * @param time  The system time in nanoseconds.
     * @param delta The time in nanoseconds since the last time this method was called.
     */
    @Override
    protected void postEvents(long time, long delta)
    {
        FloatBuffer axes = Objects.requireNonNull(glfwGetJoystickAxes(this.jid), "Joystick is not connected.");
        for (int axis : this.axisMap.keySet())
        {
            Axis axisObj = this.axisMap.get(axis);
            
            axisObj._value = axes.get(axis);
            if (Float.compare(axisObj.value, axisObj._value) != 0)
            {
                float difference = axisObj._value - axisObj.value;
                axisObj.value = axisObj._value;
                GLFW.EVENT_BUS.post(new GLFWEventJoystickAxis(this, axis, axisObj.value, difference));
            }
        }
        
        ByteBuffer buttons = Objects.requireNonNull(glfwGetJoystickButtons(this.jid), "Joystick is not connected.");
        for (int button : this.buttonMap.keySet())
        {
            Button buttonObj = this.buttonMap.get(button);
            
            buttonObj._state = buttons.get();
            if (buttonObj.state != buttonObj._state)
            {
                buttonObj.state = buttonObj._state;
                if (buttonObj.state == GLFW_PRESS)
                {
                    buttonObj.held       = true;
                    buttonObj.holdTime   = time + InputDevice.holdFrequency;
                    buttonObj.repeatTime = time + InputDevice.repeatDelay;
                    GLFW.EVENT_BUS.post(new GLFWEventJoystickButtonDown(this, button));
                }
                else if (buttonObj.state == GLFW_RELEASE)
                {
                    buttonObj.held       = false;
                    buttonObj.holdTime   = Long.MAX_VALUE;
                    buttonObj.repeatTime = Long.MAX_VALUE;
                    GLFW.EVENT_BUS.post(new GLFWEventJoystickButtonUp(this, button));
                    
                    if (time - buttonObj.pressTime < InputDevice.doublePressedDelay)
                    {
                        buttonObj.pressTime = 0;
                        GLFW.EVENT_BUS.post(new GLFWEventJoystickButtonPressed(this, button, true));
                    }
                    else
                    {
                        buttonObj.pressTime = time;
                        GLFW.EVENT_BUS.post(new GLFWEventJoystickButtonPressed(this, button, false));
                    }
                }
            }
            if (buttonObj.held && time - buttonObj.holdTime >= InputDevice.holdFrequency)
            {
                buttonObj.holdTime += InputDevice.holdFrequency;
                GLFW.EVENT_BUS.post(new GLFWEventJoystickButtonHeld(this, button));
            }
            if (buttonObj.state == GLFW_REPEAT || time - buttonObj.repeatTime >= InputDevice.repeatFrequency)
            {
                buttonObj.repeatTime += InputDevice.repeatFrequency;
                GLFW.EVENT_BUS.post(new GLFWEventJoystickButtonRepeated(this, button));
            }
        }
    }
    
    static final class Axis
    {
        protected float _value, value;
        
        Axis(float initial)
        {
            this._value = initial;
        }
    }
    
    static final class Button extends InputDevice.Input
    {
        public Button(int initial)
        {
            super(initial);
        }
    }
}

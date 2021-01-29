package rutils.glfw;

import rutils.Logger;
import rutils.glfw.event.events.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;

public class Joystick extends InputDevice
{
    private static final Logger LOGGER = new Logger();
    
    protected final int jid;
    
    private final String name;
    
    private final boolean gamepad;
    
    final Map<Integer, AxisInput>   axisMap;
    final Map<Integer, ButtonInput> buttonMap;
    final Map<Integer, HatInput>    hatMap;
    
    Joystick(int jid, boolean gamepad)
    {
        super("Joystick-" + jid);
        
        this.jid = jid;
        
        this.gamepad = gamepad;
        
        this.name = glfwGetJoystickName(this.jid);
        
        synchronized (this.axisMap = new LinkedHashMap<>())
        {
            FloatBuffer axes = Objects.requireNonNull(glfwGetJoystickAxes(this.jid), "Joystick is not connected.");
            for (int i = 0, n = axes.remaining(); i < n; i++) this.axisMap.put(i, new AxisInput(axes.get(i)));
        }
        
        synchronized (this.buttonMap = new LinkedHashMap<>())
        {
            ByteBuffer buttons = Objects.requireNonNull(glfwGetJoystickButtons(this.jid), "Joystick is not connected.");
            for (int i = 0, n = buttons.remaining(); i < n; i++) this.buttonMap.put(i, new ButtonInput(buttons.get(i)));
        }
        
        synchronized (this.hatMap = new LinkedHashMap<>())
        {
            ByteBuffer hats = Objects.requireNonNull(glfwGetJoystickHats(this.jid), "Joystick is not connected.");
            for (int i = 0, n = hats.remaining(); i < n; i++) this.hatMap.put(i, new HatInput(hats.get(i)));
        }
    }
    
    @Override
    public String toString()
    {
        return "Joystick{" + this.jid + ", name='" + this.name + '\'' + '}';
    }
    
    public boolean isGamepad()
    {
        return this.gamepad;
    }
    
    public String name()
    {
        return this.name;
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
        if (this.axisMap != null)
        {
            synchronized (this.axisMap)
            {
                for (int axis : this.axisMap.keySet())
                {
                    AxisInput axisObj = this.axisMap.get(axis);
                    if (Float.compare(axisObj.value, axisObj._value) != 0)
                    {
                        float difference = axisObj._value - axisObj.value;
                        axisObj.value = axisObj._value;
                        GLFW.EVENT_BUS.post(new GLFWEventJoystickAxis(this, axis, axisObj.value, difference));
                    }
                }
            }
        }
        
        if (this.buttonMap != null)
        {
            synchronized (this.buttonMap)
            {
                for (int button : this.buttonMap.keySet())
                {
                    ButtonInput buttonObj = this.buttonMap.get(button);
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
        }
        
        if (this.hatMap != null)
        {
            synchronized (this.hatMap)
            {
                for (int hat : this.hatMap.keySet())
                {
                    HatInput hatObj = this.hatMap.get(hat);
                    
                    if (hatObj.state != hatObj._state)
                    {
                        hatObj.state = hatObj._state;
                        GLFW.EVENT_BUS.post(new GLFWEventJoystickHat(this, hat, Hat.get(hatObj.state)));
                    }
                }
            }
        }
    }
    
    static final class AxisInput
    {
        protected float _value, value;
        
        AxisInput(float initial)
        {
            this._value = initial;
        }
    }
    
    static final class ButtonInput extends Input
    {
        public ButtonInput(int initial)
        {
            super(initial);
        }
    }
    
    static final class HatInput extends Input
    {
        public HatInput(int initial)
        {
            super(initial);
        }
    }
    
    public enum Hat
    {
        CENTERED(GLFW_HAT_CENTERED),
        
        UP(GLFW_HAT_UP),
        RIGHT(GLFW_HAT_RIGHT),
        DOWN(GLFW_HAT_DOWN),
        LEFT(GLFW_HAT_LEFT),
        
        RIGHT_UP(GLFW_HAT_RIGHT_UP),
        RIGHT_DOWN(GLFW_HAT_RIGHT_DOWN),
        LEFT_UP(GLFW_HAT_LEFT_UP),
        LEFT_DOWN(GLFW_HAT_LEFT_DOWN),
        
        UP_DOWN(GLFW_HAT_UP | GLFW_HAT_DOWN),
        RIGHT_LEFT(GLFW_HAT_RIGHT | GLFW_HAT_LEFT),
        
        RIGHT_UP_LEFT(GLFW_HAT_RIGHT | GLFW_HAT_UP | GLFW_HAT_LEFT),
        RIGHT_DOWN_LEFT(GLFW_HAT_RIGHT | GLFW_HAT_DOWN | GLFW_HAT_LEFT),
        UP_RIGHT_DOWN(GLFW_HAT_UP | GLFW_HAT_RIGHT | GLFW_HAT_DOWN),
        UP_LEFT_DOWN(GLFW_HAT_UP | GLFW_HAT_LEFT | GLFW_HAT_DOWN),
        
        ALL(GLFW_HAT_UP | GLFW_HAT_RIGHT | GLFW_HAT_DOWN | GLFW_HAT_LEFT),
        
        ;
        
        private final int ref;
        
        Hat(int ref)
        {
            this.ref = ref;
        }
        
        public static Hat get(int value)
        {
            for (Hat hat : Hat.values()) if ((value & hat.ref) == value) return hat;
            return Hat.CENTERED;
        }
    }
}

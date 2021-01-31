package rutils.glfw;

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
        
        this.axisMap = new LinkedHashMap<>();
        FloatBuffer axes = Objects.requireNonNull(glfwGetJoystickAxes(this.jid), "Joystick is not connected.");
        for (int i = 0, n = axes.remaining(); i < n; i++) this.axisMap.put(i, new AxisInput(axes.get(i)));
        
        this.buttonMap = new LinkedHashMap<>();
        ByteBuffer buttons = Objects.requireNonNull(glfwGetJoystickButtons(this.jid), "Joystick is not connected.");
        for (int i = 0, n = buttons.remaining(); i < n; i++) this.buttonMap.put(i, new ButtonInput(buttons.get(i)));
        
        this.hatMap = new LinkedHashMap<>();
        ByteBuffer hats = Objects.requireNonNull(glfwGetJoystickHats(this.jid), "Joystick is not connected.");
        for (int i = 0, n = hats.remaining(); i < n; i++) this.hatMap.put(i, new HatInput(hats.get(i)));
        
        this.threadStart.countDown();
    }
    
    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "{" + this.jid + ", name='" + this.name + '\'' + '}';
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
     * @param time      The system time in nanoseconds.
     * @param deltaTime The time in nanoseconds since the last time this method was called.
     */
    @Override
    protected void postEvents(long time, long deltaTime)
    {
        for (int axis : this.axisMap.keySet())
        {
            AxisInput axisObj = this.axisMap.get(axis);
            if (Double.compare(axisObj.value, axisObj._value) != 0)
            {
                double delta = axisObj._value - axisObj.value;
                axisObj.value = axisObj._value;
                postAxisEvent(axis, axisObj.value, delta);
            }
        }
        
        for (int button : this.buttonMap.keySet())
        {
            ButtonInput input = this.buttonMap.get(button);
            
            input.state  = input._state;
            input._state = -1;
            switch (input.state)
            {
                case GLFW_PRESS -> {
                    input.held     = true;
                    input.holdTime = time + InputDevice.holdFrequency;
                    postButtonDownEvent(button);
                }
                case GLFW_RELEASE -> {
                    input.held     = false;
                    input.holdTime = Long.MAX_VALUE;
                    postButtonUpEvent(button);
                    
                    if (time - input.pressTime < InputDevice.doublePressedDelay)
                    {
                        input.pressTime = 0;
                        postButtonPressedEvent(button, true);
                    }
                    else
                    {
                        input.pressTime = time;
                        postButtonPressedEvent(button, false);
                    }
                }
                case GLFW_REPEAT -> postButtonRepeatedEvent(button);
            }
            if (input.held && time - input.holdTime >= InputDevice.holdFrequency)
            {
                input.holdTime += InputDevice.holdFrequency;
                postButtonHeldEvent(button);
            }
        }
        
        for (int hat : this.hatMap.keySet())
        {
            HatInput hatObj = this.hatMap.get(hat);
            
            if (hatObj.state != hatObj._state)
            {
                hatObj.state = hatObj._state;
                postHatEvent(hat, hatObj.state);
            }
        }
    }
    
    protected void postAxisEvent(int axis, double value, double delta)
    {
        GLFW.EVENT_BUS.post(EventJoystickAxis.create(this, axis, value, delta));
    }
    
    protected void postButtonDownEvent(int button)
    {
        GLFW.EVENT_BUS.post(EventJoystickButtonDown.create(this, button));
    }
    
    protected void postButtonUpEvent(int button)
    {
        GLFW.EVENT_BUS.post(EventJoystickButtonUp.create(this, button));
    }
    
    protected void postButtonPressedEvent(int button, boolean doublePressed)
    {
        GLFW.EVENT_BUS.post(EventJoystickButtonPressed.create(this, button, doublePressed));
    }
    
    protected void postButtonHeldEvent(int button)
    {
        GLFW.EVENT_BUS.post(EventJoystickButtonHeld.create(this, button));
    }
    
    protected void postButtonRepeatedEvent(int button)
    {
        GLFW.EVENT_BUS.post(EventJoystickButtonRepeated.create(this, button));
    }
    
    protected void postHatEvent(int hat, int state)
    {
        GLFW.EVENT_BUS.post(EventJoystickHat.create(this, hat, Hat.get(state)));
    }
    
    static final class AxisInput
    {
        protected double _value, value;
        
        AxisInput(double initial)
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

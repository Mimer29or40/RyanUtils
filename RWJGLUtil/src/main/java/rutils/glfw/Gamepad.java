package rutils.glfw;

import org.lwjgl.glfw.GLFWGamepadState;
import org.lwjgl.system.MemoryStack;
import rutils.glfw.event.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;

public class Gamepad extends Joystick
{
    private final String name;
    
    final Map<Axis, AxisInput>     axisMap;
    final Map<Button, ButtonInput> buttonMap;
    final Map<Integer, HatInput>   hatMap;
    
    Gamepad(int jid, boolean gamepad)
    {
        super(jid, gamepad);
        
        this.name = glfwGetGamepadName(this.jid);
        
        try (MemoryStack stack = MemoryStack.stackPush())
        {
            GLFWGamepadState state = GLFWGamepadState.mallocStack(stack);
            
            glfwGetGamepadState(this.jid, state);
            
            synchronized (this.axisMap = new LinkedHashMap<>())
            {
                FloatBuffer axes = state.axes();
                for (int i = 0, n = axes.remaining(); i < n; i++) this.axisMap.put(Axis.get(i), new AxisInput(axes.get(i)));
            }
            
            synchronized (this.buttonMap = new LinkedHashMap<>())
            {
                ByteBuffer buttons = state.buttons();
                for (int i = 0, n = buttons.remaining(); i < n; i++) this.buttonMap.put(Button.get(i), new ButtonInput(buttons.get(i)));
            }
            
            synchronized (this.hatMap = new LinkedHashMap<>())
            {
                ByteBuffer hats = Objects.requireNonNull(glfwGetJoystickHats(this.jid), "Joystick is not connected.");
                for (int i = 0, n = hats.remaining(); i < n; i++) this.hatMap.put(i, new HatInput(hats.get(i)));
            }
        }
    }
    
    @Override
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
                for (Axis axis : this.axisMap.keySet())
                {
                    AxisInput axisObj = this.axisMap.get(axis);
                    if (Float.compare(axisObj.value, axisObj._value) != 0)
                    {
                        float difference = axisObj._value - axisObj.value;
                        axisObj.value = axisObj._value;
                        GLFW.EVENT_BUS.post(EventGamepadAxis.create(this, axis, axisObj.value, difference));
                    }
                }
            }
        }
        
        if (this.buttonMap != null)
        {
            synchronized (this.buttonMap)
            {
                for (Button button : this.buttonMap.keySet())
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
                            GLFW.EVENT_BUS.post(EventGamepadButtonDown.create(this, button));
                        }
                        else if (buttonObj.state == GLFW_RELEASE)
                        {
                            buttonObj.held       = false;
                            buttonObj.holdTime   = Long.MAX_VALUE;
                            buttonObj.repeatTime = Long.MAX_VALUE;
                            GLFW.EVENT_BUS.post(EventGamepadButtonUp.create(this, button));
                            
                            if (time - buttonObj.pressTime < InputDevice.doublePressedDelay)
                            {
                                buttonObj.pressTime = 0;
                                GLFW.EVENT_BUS.post(EventGamepadButtonPressed.create(this, button, true));
                            }
                            else
                            {
                                buttonObj.pressTime = time;
                                GLFW.EVENT_BUS.post(EventGamepadButtonPressed.create(this, button, false));
                            }
                        }
                    }
                    if (buttonObj.held && time - buttonObj.holdTime >= InputDevice.holdFrequency)
                    {
                        buttonObj.holdTime += InputDevice.holdFrequency;
                        GLFW.EVENT_BUS.post(EventGamepadButtonHeld.create(this, button));
                    }
                    if (buttonObj.state == GLFW_REPEAT || time - buttonObj.repeatTime >= InputDevice.repeatFrequency)
                    {
                        buttonObj.repeatTime += InputDevice.repeatFrequency;
                        GLFW.EVENT_BUS.post(EventGamepadButtonRepeated.create(this, button));
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
                        GLFW.EVENT_BUS.post(EventGamepadHat.create(this, hat, Hat.get(hatObj.state)));
                    }
                }
            }
        }
    }
    
    public enum Axis
    {
        NONE(-1),
        
        LEFT_X(GLFW_GAMEPAD_AXIS_LEFT_X),
        LEFT_Y(GLFW_GAMEPAD_AXIS_LEFT_Y),
        
        RIGHT_X(GLFW_GAMEPAD_AXIS_RIGHT_X),
        RIGHT_Y(GLFW_GAMEPAD_AXIS_RIGHT_Y),
        
        LEFT_TRIGGER(GLFW_GAMEPAD_AXIS_LEFT_TRIGGER),
        RIGHT_TRIGGER(GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER),
        ;
        
        private final int id;
        
        Axis(int id)
        {
            this.id = id;
        }
    
        public int id()
        {
            return this.id;
        }
        
        public static Axis get(int value)
        {
            for (Axis hat : Axis.values()) if (hat.id == value) return hat;
            return Axis.NONE;
        }
    }
    
    public enum Button
    {
        NONE(-1),
        
        A(GLFW_GAMEPAD_BUTTON_A),
        B(GLFW_GAMEPAD_BUTTON_B),
        X(GLFW_GAMEPAD_BUTTON_X),
        Y(GLFW_GAMEPAD_BUTTON_Y),
        
        LEFT_BUMPER(GLFW_GAMEPAD_BUTTON_LEFT_BUMPER),
        RIGHT_BUMPER(GLFW_GAMEPAD_BUTTON_RIGHT_BUMPER),
        
        BACK(GLFW_GAMEPAD_BUTTON_BACK),
        START(GLFW_GAMEPAD_BUTTON_START),
        GUIDE(GLFW_GAMEPAD_BUTTON_GUIDE),
        
        LEFT_THUMB(GLFW_GAMEPAD_BUTTON_LEFT_THUMB),
        RIGHT_THUMB(GLFW_GAMEPAD_BUTTON_RIGHT_THUMB),
        
        DPAD_UP(GLFW_GAMEPAD_BUTTON_DPAD_UP),
        DPAD_RIGHT(GLFW_GAMEPAD_BUTTON_DPAD_RIGHT),
        DPAD_DOWN(GLFW_GAMEPAD_BUTTON_DPAD_DOWN),
        DPAD_LEFT(GLFW_GAMEPAD_BUTTON_DPAD_LEFT),
        
        CROSS(GLFW_GAMEPAD_BUTTON_CROSS),
        CIRCLE(GLFW_GAMEPAD_BUTTON_CIRCLE),
        SQUARE(GLFW_GAMEPAD_BUTTON_SQUARE),
        TRIANGLE(GLFW_GAMEPAD_BUTTON_TRIANGLE),
        ;
        
        private final int id;
        
        Button(int id)
        {
            this.id = id;
        }
        
        public int id()
        {
            return this.id;
        }
        
        public static Button get(int value)
        {
            for (Button button : Button.values()) if (button.id == value) return button;
            return Button.NONE;
        }
    }
}

package rutils.glfw;

import org.lwjgl.glfw.GLFWGamepadState;
import org.lwjgl.system.MemoryStack;
import rutils.glfw.event.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Objects;

import static org.lwjgl.glfw.GLFW.*;

public class Gamepad extends Joystick
{
    private final String name;
    
    Gamepad(int jid, boolean gamepad)
    {
        super(jid, gamepad);
        
        this.name = glfwGetGamepadName(this.jid);
        
        // try (MemoryStack stack = MemoryStack.stackPush())
        // {
        //     GLFWGamepadState state = GLFWGamepadState.mallocStack(stack);
        //
        //     glfwGetGamepadState(this.jid, state);
        //
        //     synchronized (this.axisMap)
        //     {
        //         FloatBuffer axes = state.axes();
        //         for (int i = 0, n = axes.remaining(); i < n; i++) this.axisMap.put(i, new AxisInput(axes.get(i)));
        //     }
        //
        //     synchronized (this.buttonMap)
        //     {
        //         ByteBuffer buttons = state.buttons();
        //         for (int i = 0, n = buttons.remaining(); i < n; i++) this.buttonMap.put(i, new ButtonInput(buttons.get(i)));
        //     }
        //
        //     synchronized (this.hatMap)
        //     {
        //         ByteBuffer hats = Objects.requireNonNull(glfwGetJoystickHats(this.jid), "Joystick is not connected.");
        //         for (int i = 0, n = hats.remaining(); i < n; i++) this.hatMap.put(i, new HatInput(hats.get(i)));
        //     }
        // }
    }
    
    @Override
    public String name()
    {
        return this.name;
    }
    
    @Override
    protected void postAxisEvent(int axis, double value, double delta)
    {
        GLFW.EVENT_BUS.post(EventGamepadAxis.create(this, Axis.get(axis), value, delta));
    }
    
    @Override
    protected void postButtonDownEvent(int button)
    {
        GLFW.EVENT_BUS.post(EventGamepadButtonDown.create(this, Button.get(button)));
    }
    
    @Override
    protected void postButtonUpEvent(int button)
    {
        GLFW.EVENT_BUS.post(EventGamepadButtonUp.create(this, Button.get(button)));
    }
    
    @Override
    protected void postButtonPressedEvent(int button, boolean doublePressed)
    {
        GLFW.EVENT_BUS.post(EventGamepadButtonPressed.create(this, Button.get(button), doublePressed));
    }
    
    @Override
    protected void postButtonHeldEvent(int button)
    {
        GLFW.EVENT_BUS.post(EventGamepadButtonHeld.create(this, Button.get(button)));
    }
    
    @Override
    protected void postButtonRepeatedEvent(int button)
    {
        GLFW.EVENT_BUS.post(EventGamepadButtonRepeated.create(this, Button.get(button)));
    }
    
    @Override
    protected void postHatEvent(int hat, int state)
    {
        GLFW.EVENT_BUS.post(EventGamepadHat.create(this, hat, Hat.get(state)));
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

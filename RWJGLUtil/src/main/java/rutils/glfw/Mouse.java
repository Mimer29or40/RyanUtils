package rutils.glfw;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2d;
import org.joml.Vector2i;
import org.lwjgl.system.Platform;
import rutils.Logger;
import rutils.glfw.event.*;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse extends InputDevice<Mouse.Button, Mouse.Input>
{
    private static final Logger LOGGER = new Logger();
    
    private final Window window;
    
    // -------------------- Callback Objects -------------------- //
    
    protected boolean entered;
    protected boolean _entered;
    
    protected final Vector2d pos  = new Vector2d();
    protected final Vector2d _pos = new Vector2d();
    
    protected final Vector2d rel = new Vector2d(0);
    
    protected final Vector2d scroll  = new Vector2d();
    protected final Vector2d _scroll = new Vector2d();
    
    // -------------------- Internal Objects -------------------- //
    
    private final Vector2i deltaI = new Vector2i();
    
    // TODO - Only have one mouse instance.
    Mouse(Window window)
    {
        this.window = window;
    }
    
    @Override
    public String toString()
    {
        return "Mouse{" + "window=" + this.window.name() + '}';
    }
    
    /**
     * @return The window that this mouse is attached to.
     */
    public Window window()
    {
        return this.window;
    }
    
    // -------------------- Properties -------------------- //
    
    /**
     * Makes the cursor visible and behaving normally.
     */
    public void show()
    {
        GLFW.TASK_DELEGATOR.runTask(() -> glfwSetInputMode(this.window.handle, GLFW_CURSOR, GLFW_CURSOR_NORMAL));
    }
    
    /**
     * @return Retrieves if the mouse is visible and behaving normally in its window.
     */
    public boolean isShown()
    {
        return GLFW.TASK_DELEGATOR.waitReturnTask(() -> glfwGetInputMode(this.window.handle, GLFW_CURSOR) == GLFW_CURSOR_NORMAL);
    }
    
    /**
     * Makes the cursor invisible when it is over the content area of the
     * window but does not restrict the cursor from leaving.
     */
    public void hide()
    {
        GLFW.TASK_DELEGATOR.runTask(() -> glfwSetInputMode(this.window.handle, GLFW_CURSOR, GLFW_CURSOR_HIDDEN));
    }
    
    /**
     * @return Retrieves if the mouse is hidden over its window.
     */
    public boolean isHidden()
    {
        return GLFW.TASK_DELEGATOR.waitReturnTask(() -> glfwGetInputMode(this.window.handle, GLFW_CURSOR) == GLFW_CURSOR_HIDDEN);
    }
    
    /**
     * Hides and grabs the cursor, providing virtual and unlimited cursor
     * movement. This is useful for implementing for example 3D camera
     * controls.
     */
    public void capture()
    {
        GLFW.TASK_DELEGATOR.runTask(() -> glfwSetInputMode(this.window.handle, GLFW_CURSOR, GLFW_CURSOR_DISABLED));
    }
    
    /**
     * @return Retrieves if the mouse is captured by its window.
     */
    public boolean isCaptured()
    {
        return GLFW.TASK_DELEGATOR.waitReturnTask(() -> glfwGetInputMode(this.window.handle, GLFW_CURSOR) == GLFW_CURSOR_DISABLED);
    }
    
    /**
     * Sets the raw mouse motion flag. Set {@code true} to enable raw (unscaled
     * and unaccelerated) mouse motion when the cursor is disabled, or
     * {@code false} to disable it. If raw motion is not supported, attempting
     * to set this will log a warning.
     *
     * @param rawInput {@code true} to enable raw mouse motion mode, otherwise {@code false}.
     */
    public void rawInput(boolean rawInput)
    {
        if (!GLFW.supportRawMouseInput)
        {
            Mouse.LOGGER.warning("Raw Mouse Motion is not support on", Platform.get());
            return;
        }
        GLFW.TASK_DELEGATOR.runTask(() -> glfwSetInputMode(this.window.handle, GLFW_RAW_MOUSE_MOTION, rawInput ? GLFW_TRUE : GLFW_FALSE));
    }
    
    /**
     * @return Retrieves the raw mouse motion flag.
     */
    public boolean rawInputEnabled()
    {
        if (!GLFW.supportRawMouseInput)
        {
            Mouse.LOGGER.warning("Raw Mouse Motion is not support on", Platform.get());
            return false;
        }
        return GLFW.TASK_DELEGATOR.waitReturnTask(() -> glfwGetInputMode(this.window.handle, GLFW_RAW_MOUSE_MOTION) == GLFW_TRUE);
    }
    
    /**
     * Sets the sticky mouse buttons flag. If sticky mouse buttons are enabled,
     * a mouse button press will ensure that {@link GLFWEventMouseButtonClicked}
     * is posted even if the mouse button had been released before the call.
     * This is useful when you are only interested in whether mouse buttons
     * have been pressed but not when or in which order.
     *
     * @param sticky {@code true} to enable sticky mode, otherwise {@code false}.
     */
    public void sticky(boolean sticky)
    {
        GLFW.TASK_DELEGATOR.runTask(() -> glfwSetInputMode(this.window.handle, GLFW_STICKY_MOUSE_BUTTONS, sticky ? GLFW_TRUE : GLFW_FALSE));
    }
    
    /**
     * @return Retrieves the sticky mouse buttons flag.
     */
    public boolean stickyEnabled()
    {
        return GLFW.TASK_DELEGATOR.waitReturnTask(() -> glfwGetInputMode(this.window.handle, GLFW_STICKY_MOUSE_BUTTONS) == GLFW_TRUE);
    }
    
    // -------------------- Callback Related Things -------------------- //
    
    @Override
    protected @NotNull Map<Button, Input> generateMap()
    {
        Map<Button, Input> map = new HashMap<>();
        for (Button button : Button.values()) map.put(button, new Input(button));
        return map;
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
        super.postEvents(time, delta);
        
        boolean entered = false;
        
        if (this.entered != this._entered)
        {
            this.entered = this._entered;
            GLFW.EVENT_BUS.post(new GLFWEventMouseEntered(this, this.entered));
            if (this.entered)
            {
                entered = true;
                this.pos.set(this._pos);
            }
        }
        
        this.rel.set(0);
        if (Double.compare(this.pos.x, this._pos.x) != 0 || Double.compare(this.pos.y, this._pos.y) != 0 || entered)
        {
            this._pos.sub(this.pos, this.rel);
            this.pos.set(this._pos);
            GLFW.EVENT_BUS.post(new GLFWEventMouseMoved(this, this.pos, this.rel));
        }
        
        this.scroll.set(0);
        if (Double.compare(this.scroll.x, this._scroll.x) != 0 || Double.compare(this.scroll.y, this._scroll.y) != 0)
        {
            this.scroll.set(this._scroll);
            this._scroll.set(0);
            GLFW.EVENT_BUS.post(new GLFWEventMouseScrolled(this, this.scroll));
        }
    }
    
    /**
     * Post events to the event bus
     *
     * @param input The input object to generate the events for.
     * @param time  The system time in nanoseconds.
     * @param delta The time in nanoseconds since the last time this method was called.
     */
    @Override
    protected void postInputEvents(Input input, long time, long delta)
    {
        if (input.down)
        {
            GLFW.EVENT_BUS.post(new GLFWEventMouseButtonDown(this, input.button, input.mods, this.pos));
            
            input.click.set(this.pos);
        }
        if (input.up)
        {
            GLFW.EVENT_BUS.post(new GLFWEventMouseButtonUp(this, input.button, input.mods, this.pos));
            
            boolean inClickRange  = Math.abs(this.pos.x - input.click.x) < 2 && Math.abs(this.pos.y - input.click.y) < 2;
            boolean inDClickRange = Math.abs(this.pos.x - input.dClick.x) < 2 && Math.abs(this.pos.y - input.dClick.y) < 2;
            
            if (inDClickRange && time - input.pressTime < 500_000_000)
            {
                GLFW.EVENT_BUS.post(new GLFWEventMouseButtonClicked(this, input.button, input.mods, this.pos, true));
            }
            else if (inClickRange)
            {
                GLFW.EVENT_BUS.post(new GLFWEventMouseButtonClicked(this, input.button, input.mods, this.pos, false));
                input.dClick.set(this.pos);
                input.pressTime = time;
            }
        }
        input.dragged = false;
        if (input.held)
        {
            GLFW.EVENT_BUS.post(new GLFWEventMouseButtonHeld(this, input.button, input.mods, this.pos));
            
            if (this.rel.x != 0 || this.rel.y != 0)
            {
                input.dragged = true;
                
                GLFW.EVENT_BUS.post(new GLFWEventMouseButtonDragged(this, input.button, input.mods, this.pos, this.rel, input.click));
            }
        }
        if (input.repeat) GLFW.EVENT_BUS.post(new GLFWEventMouseButtonRepeat(this, input.button, input.mods, this.pos));
    }
    
    protected void stateCallback(int button, int action, int mods)
    {
        InputDevice.Input input = this.inputMap.get(Button.get(button));
        input._action = action;
        input._mods   = mods;
    }
    
    public static class Input extends InputDevice.Input
    {
        private final Button button;
        
        protected long pressTime;
        
        protected boolean dragged;
        
        final Vector2d click  = new Vector2d();
        final Vector2d dClick = new Vector2d();
        
        public Input(Button button)
        {
            this.button = button;
        }
        
        /**
         * @return If the GLFWMouseButton is being dragged.
         */
        public boolean dragged(Modifier... modifiers)
        {
            return this.dragged && checkModifiers(modifiers);
        }
    }
    
    public enum Button
    {
        NONE(-1),
        
        LEFT(GLFW_MOUSE_BUTTON_LEFT),
        RIGHT(GLFW_MOUSE_BUTTON_RIGHT),
        MIDDLE(GLFW_MOUSE_BUTTON_MIDDLE),
        
        FOUR(GLFW_MOUSE_BUTTON_4),
        FIVE(GLFW_MOUSE_BUTTON_5),
        SIX(GLFW_MOUSE_BUTTON_6),
        SEVEN(GLFW_MOUSE_BUTTON_7),
        EIGHT(GLFW_MOUSE_BUTTON_8),
        
        ;
        
        private static final HashMap<Integer, Button> BUTTON_MAP = new HashMap<>();
        
        final int ref;
        
        Button(int ref)
        {
            this.ref = ref;
        }
        
        /**
         * @return Gets the Input that corresponds to the GLFW constant.
         */
        public static Button get(int ref)
        {
            return Button.BUTTON_MAP.getOrDefault(ref, Button.NONE);
        }
        
        static
        {
            for (Button button : values())
            {
                Button.BUTTON_MAP.put(button.ref, button);
            }
        }
    }
}

package rutils.glfw;

import org.jetbrains.annotations.NotNull;
import org.joml.Vector2d;
import org.lwjgl.system.Platform;
import rutils.IPair;
import rutils.Logger;
import rutils.glfw.event.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse extends InputDevice<Mouse.Button, Mouse.Input>
{
    private static final Logger LOGGER = new Logger();
    
    // -------------------- State Objects -------------------- //
    
    protected Window captureWindow = null;
    
    // -------------------- Callback Objects -------------------- //
    
    protected Queue<IPair<Window, Boolean>> _enteredChanges = new ConcurrentLinkedQueue<>();
    protected Window                        _enteredW       = null;
    
    protected final Vector2d pos   = new Vector2d();
    protected final Vector2d _pos  = new Vector2d();
    protected       Window   _posW = null;
    
    protected final Vector2d rel = new Vector2d(0);
    
    protected final Vector2d scroll   = new Vector2d();
    protected final Vector2d _scroll  = new Vector2d();
    protected       Window   _scrollW = null;
    
    // -------------------- Internal Objects -------------------- //
    
    @Override
    public String toString()
    {
        return "Mouse{" + '}';
    }
    
    // -------------------- Properties -------------------- //
    
    public boolean isOver(Window window)
    {
        return this._enteredW == window;
    }
    
    // TODO - Query GLFW.WINDOWS to check if any/all window are shown/hidden/captured
    
    /**
     * Makes the cursor visible and behaving normally.
     */
    public void show(Window window)
    {
        GLFW.TASK_DELEGATOR.runTask(() -> {
            if (glfwGetInputMode(window.handle, GLFW_CURSOR) == GLFW_CURSOR_DISABLED) this._pos.set(this.pos.set(window.width() * 0.5, window.height() * 0.5));
            glfwSetInputMode(window.handle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
            if (this.captureWindow == window) this.captureWindow = null;
        });
    }
    
    /**
     * @return Retrieves if the mouse is visible and behaving normally in its window.
     */
    public boolean isShown(Window window)
    {
        return GLFW.TASK_DELEGATOR.waitReturnTask(() -> glfwGetInputMode(window.handle, GLFW_CURSOR) == GLFW_CURSOR_NORMAL);
    }
    
    /**
     * Makes the cursor invisible when it is over the content area of the
     * window but does not restrict the cursor from leaving.
     */
    public void hide(Window window)
    {
        GLFW.TASK_DELEGATOR.runTask(() -> {
            glfwSetInputMode(window.handle, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
            if (this.captureWindow == window) this.captureWindow = null;
        });
    }
    
    /**
     * @return Retrieves if the mouse is hidden over its window.
     */
    public boolean isHidden(Window window)
    {
        return GLFW.TASK_DELEGATOR.waitReturnTask(() -> glfwGetInputMode(window.handle, GLFW_CURSOR) == GLFW_CURSOR_HIDDEN);
    }
    
    /**
     * Hides and grabs the cursor, providing virtual and unlimited cursor
     * movement. This is useful for implementing for example 3D camera
     * controls.
     */
    public void capture(Window window)
    {
        this._pos.set(this.pos.set(window.width() * 0.5, window.height() * 0.5));
        GLFW.TASK_DELEGATOR.runTask(() -> {
            glfwSetCursorPos(window.handle, this._pos.x, this._pos.y);
            glfwSetInputMode(window.handle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
            this.captureWindow = window;
        });
    }
    
    /**
     * @return Retrieves if the mouse is captured by its window.
     */
    public boolean isCaptured(Window window)
    {
        return GLFW.TASK_DELEGATOR.waitReturnTask(() -> glfwGetInputMode(window.handle, GLFW_CURSOR) == GLFW_CURSOR_DISABLED);
    }
    
    /**
     * @return Retrieves if the mouse is captured by any window.
     */
    public boolean isCaptured()
    {
        return this.captureWindow == null;
    }
    
    // TODO - The following can possible be global flags
    
    /**
     * Sets the raw mouse motion flag. Set {@code true} to enable raw (unscaled
     * and unaccelerated) mouse motion when the cursor is disabled, or
     * {@code false} to disable it. If raw motion is not supported, attempting
     * to set this will log a warning.
     *
     * @param rawInput {@code true} to enable raw mouse motion mode, otherwise {@code false}.
     */
    public void rawInput(Window window, boolean rawInput)
    {
        if (!GLFW.SUPPORT_RAW_MOUSE_MOTION)
        {
            Mouse.LOGGER.warning("Raw Mouse Motion is not support on", Platform.get());
            return;
        }
        GLFW.TASK_DELEGATOR.runTask(() -> glfwSetInputMode(window.handle, GLFW_RAW_MOUSE_MOTION, rawInput ? GLFW_TRUE : GLFW_FALSE));
    }
    
    /**
     * @return Retrieves the raw mouse motion flag.
     */
    public boolean rawInputEnabled(Window window)
    {
        if (!GLFW.SUPPORT_RAW_MOUSE_MOTION)
        {
            Mouse.LOGGER.warning("Raw Mouse Motion is not support on", Platform.get());
            return false;
        }
        return GLFW.TASK_DELEGATOR.waitReturnTask(() -> glfwGetInputMode(window.handle, GLFW_RAW_MOUSE_MOTION) == GLFW_TRUE);
    }
    
    /**
     * Sets the sticky mouse buttons flag. If sticky mouse buttons are enabled,
     * a mouse button press will ensure that {@link GLFWEventMouseButtonPressed}
     * is posted even if the mouse button had been released before the call.
     * This is useful when you are only interested in whether mouse buttons
     * have been pressed but not when or in which order.
     *
     * @param sticky {@code true} to enable sticky mode, otherwise {@code false}.
     */
    public void sticky(Window window, boolean sticky)
    {
        GLFW.TASK_DELEGATOR.runTask(() -> glfwSetInputMode(window.handle, GLFW_STICKY_MOUSE_BUTTONS, sticky ? GLFW_TRUE : GLFW_FALSE));
    }
    
    /**
     * @return Retrieves the sticky mouse buttons flag.
     */
    public boolean stickyEnabled(Window window)
    {
        return GLFW.TASK_DELEGATOR.waitReturnTask(() -> glfwGetInputMode(window.handle, GLFW_STICKY_MOUSE_BUTTONS) == GLFW_TRUE);
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
        
        IPair<Window, Boolean> enteredChange;
        while ((enteredChange = this._enteredChanges.poll()) != null)
        {
            GLFW.EVENT_BUS.post(new GLFWEventMouseEntered(enteredChange.getA(), enteredChange.getB()));
            if (enteredChange.getB())
            {
                entered = true;
                
                this._enteredW = enteredChange.getA();
                this.pos.set(this._pos);
            }
        }
        
        this.rel.set(0);
        if (Double.compare(this.pos.x, this._pos.x) != 0 || Double.compare(this.pos.y, this._pos.y) != 0 || entered)
        {
            this._pos.sub(this.pos, this.rel);
            this.pos.set(this._pos);
            GLFW.EVENT_BUS.post(new GLFWEventMouseMoved(this._posW, this.pos, this.rel));
        }
        
        this.scroll.set(0);
        if (Double.compare(this.scroll.x, this._scroll.x) != 0 || Double.compare(this.scroll.y, this._scroll.y) != 0)
        {
            this.scroll.set(this._scroll);
            this._scroll.set(0);
            GLFW.EVENT_BUS.post(new GLFWEventMouseScrolled(this._scrollW, this.scroll));
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
            GLFW.EVENT_BUS.post(new GLFWEventMouseButtonDown(input._window, input, this.pos));
            
            input.click.set(this.pos);
        }
        if (input.up)
        {
            GLFW.EVENT_BUS.post(new GLFWEventMouseButtonUp(input._window, input, this.pos));
            
            boolean inClickRange  = Math.abs(this.pos.x - input.click.x) < 2 && Math.abs(this.pos.y - input.click.y) < 2;
            boolean inDClickRange = Math.abs(this.pos.x - input.dClick.x) < 2 && Math.abs(this.pos.y - input.dClick.y) < 2;
            
            if (inDClickRange && time - input.pressTime < InputDevice.doublePressedDelay)
            {
                GLFW.EVENT_BUS.post(new GLFWEventMouseButtonPressed(input._window, input, this.pos, true));
                input.pressTime = 0;
            }
            else if (inClickRange)
            {
                GLFW.EVENT_BUS.post(new GLFWEventMouseButtonPressed(input._window, input, this.pos, false));
                input.dClick.set(this.pos);
                input.pressTime = time;
            }
        }
        input.dragged = false;
        if (input.held)
        {
            GLFW.EVENT_BUS.post(new GLFWEventMouseButtonHeld(input._window, input, this.pos));
            
            if (this.rel.x != 0 || this.rel.y != 0)
            {
                input.dragged = true;
                
                GLFW.EVENT_BUS.post(new GLFWEventMouseButtonDragged(input._window, input, this.pos, this.rel, input.click));
            }
        }
        if (input.repeat) GLFW.EVENT_BUS.post(new GLFWEventMouseButtonRepeated(input._window, input, this.pos));
    }
    
    public static class Input extends InputDevice.Input<Button>
    {
        protected boolean dragged;
        
        final Vector2d click  = new Vector2d();
        final Vector2d dClick = new Vector2d();
        
        private Input(Button input)
        {
            super(input);
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

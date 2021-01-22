package rutils.glfw.old;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import rutils.Logger;

import static org.lwjgl.glfw.GLFW.*;

public class GLFWMouse extends GLFWInputDevice
{
    private static final Logger LOGGER = new Logger();
    
    GLFWWindow window;
    
    GLFWWindow captureWindow = null, _captureWindow = null;
    boolean entered, _entered;
    
    final Vector2d pos  = new Vector2d(0);
    final Vector2d _pos = new Vector2d(0);
    
    final Vector2d rel = new Vector2d(0);
    
    final Vector2d scroll  = new Vector2d(0);
    final Vector2d _scroll = new Vector2d(0);
    
    final Vector2d dragPos = new Vector2d();
    GLFWMouseButton drag;
    
    GLFWMouse() {}
    
    public GLFWWindow window()
    {
        return this.window;
    }
    
    /**
     * @return If the mouse captured by the window.
     */
    public boolean captured()
    {
        return this.captureWindow != null;
    }
    
    /**
     * @return The window that captured the mouse or null if its not captured.
     */
    public GLFWWindow captureWindow()
    {
        return this.captureWindow;
    }
    
    /**
     * Sets if the mouse is captured or not.
     *
     * @param captureWindow The window to capture the mouse or null to release the mouse.
     */
    public void capture(GLFWWindow captureWindow)
    {
        this._captureWindow = captureWindow;
    }
    
    /**
     * Released the mouse from the window.
     */
    public void release()
    {
        this._captureWindow = null;
    }
    
    /**
     * @return If the mouse is in the window.
     */
    public boolean entered()
    {
        return this.entered;
    }
    
    /**
     * @return The position vector of the mouse.
     */
    public Vector2dc pos()
    {
        return this.pos;
    }
    
    /**
     * @return The x position of the mouse.
     */
    public double x()
    {
        return this.pos.x;
    }
    
    /**
     * @return The y position of the mouse.
     */
    public double y()
    {
        return this.pos.y;
    }
    
    /**
     * Sets the y position of the window.
     *
     * @param y The new y position.
     */
    public void y(double y)
    {
        this._pos.y = y;
    }
    
    /**
     * @return The relative position vector of the mouse since last frame.
     */
    public Vector2dc rel()
    {
        return this.rel;
    }
    
    /**
     * @return The relative x position of the mouse since last frame.
     */
    public double relX()
    {
        return this.rel.x;
    }
    
    /**
     * @return The relative y position of the mouse since last frame.
     */
    public double relY()
    {
        return this.rel.y;
    }
    
    /**
     * @return The direction vector of the mouse scroll wheel.
     */
    public Vector2dc scroll()
    {
        return this.scroll;
    }
    
    /**
     * @return The x direction of the mouse scroll wheel.
     */
    public double scrollX()
    {
        return this.scroll.x;
    }
    
    /**
     * @return The y direction of the mouse scroll wheel.
     */
    public double scrollY()
    {
        return this.scroll.y;
    }
    
    /**
     * This is called by the Engine to generate the events and state changes for the GLFWMouse.
     *
     * @param time  The time in nano seconds that it happened.
     * @param delta The time in nano seconds since the last frame.
     */
    @Override
    void generateGLFWEvents(long time, long delta)
    {
        GLFWMouse.LOGGER.finest("Handling GLFWMouse EngineEvents");
        
        // try (Section captured = profiler.startSection("Captured"))
        {
            if (this.captureWindow != this._captureWindow)
            {
                final GLFWWindow prev = this.captureWindow;
                final GLFWWindow curr = this._captureWindow;
                GLFW.run(() -> {
                    if (prev != null)
                    {
                        glfwSetInputMode(prev.handle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
                        glfwSetCursorPos(prev.handle, prev.size.x * 0.5, prev.size.y * 0.5);
                    }
                    if (curr != null)
                    {
                        glfwSetInputMode(curr.handle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
                        glfwSetCursorPos(curr.handle, curr.size.x * 0.5, curr.size.y * 0.5);
                    }
                });
                this.captureWindow = this._captureWindow;
                // EngineEvents.post(EngineEvent.MOUSE_CAPTURED, this.captureWindow); // TODO
            }
        }
        
        boolean justEntered = false;
        // try (Section entered = profiler.startSection("Entered"))
        {
            if (this.entered != this._entered)
            {
                this.entered = this._entered;
                if (this.entered)
                {
                    justEntered = true;
                    this.pos.set(this._pos);
                }
                // EngineEvents.post(EngineEvent.MOUSE_ENTERED, this.entered); // TODO
            }
        }
        
        // try (Section position = profiler.startSection("Position"))
        {
            this.rel.set(0);
            if (Double.compare(this.pos.x, this._pos.x) != 0 || Double.compare(this.pos.y, this._pos.y) != 0 || justEntered)
            {
                this._pos.sub(this.pos, this.rel);
                this.pos.set(this._pos);
                LOGGER.info(this.rel);
                // EngineEvents.post(EngineEvent.MOUSE_MOVED, this.captured ? JOMLUnit.ZERO2d : this.pos, this.rel); // TODO
            }
        }
        
        // try (Section scroll = profiler.startSection("Scroll"))
        {
            this.scroll.set(0);
            if (Double.compare(this.scroll.x, this._scroll.x) != 0 || Double.compare(this.scroll.y, this._scroll.y) != 0)
            {
                this.scroll.set(this._scroll);
                this._scroll.set(0);
                // EngineEvents.post(EngineEvent.MOUSE_SCROLLED, this.scroll); // TODO
            }
        }
        
        // try (Section buttons = profiler.startSection("Buttons"))
        {
            for (GLFWMouseButton button : GLFWMouseButton.values())
            {
                button.down   = false;
                button.up     = false;
                button.repeat = false;
                button.mods   = 0;
                
                if (button.newState != button.state)
                {
                    if (button.newState == GLFW_PRESS)
                    {
                        button.down     = true;
                        button.held     = true;
                        button.repeat   = true;
                        button.downTime = time;
                    }
                    else if (button.newState == GLFW_RELEASE)
                    {
                        button.up       = true;
                        button.held     = false;
                        button.downTime = Long.MAX_VALUE;
                    }
                    button.state = button.newState;
                    button.mods  = button.newMods;
                }
                if (button.state == GLFW_REPEAT && button.held && time - button.downTime > GLFWInputDevice.holdDelay)
                {
                    button.downTime += GLFWInputDevice.repeatDelay;
                    button.repeat = true;
                    button.mods   = button.newMods;
                }
                
                postEvents(button, time, delta);
            }
        }
    }
    
    /**
     * This is called by the GLFWMouse to post any events that it may have generated this frame.
     *
     * @param button The button
     * @param time   The time in nano seconds that it happened.
     * @param delta  The time in nano seconds since the last frame.
     */
    protected void postEvents(GLFWMouseButton button, long time, long delta)
    {
        if (button.down)
        {
            // EngineEvents.post(EngineEvent.MOUSE_BUTTON_DOWN, button, this.pos); // TODO
            
            button.click.set(this.pos);
            if (this.drag == null)
            {
                this.drag = button;
                this.dragPos.set(this.pos);
            }
        }
        if (button.up)
        {
            // EngineEvents.post(EngineEvent.MOUSE_BUTTON_UP, button, this.pos); // TODO
            
            boolean inClickRange  = Math.abs(this.pos.x - button.click.x) < 2 && Math.abs(this.pos.y - button.click.y) < 2;
            boolean inDClickRange = Math.abs(this.pos.x - button.dClick.x) < 2 && Math.abs(this.pos.y - button.dClick.y) < 2;
            
            if (inDClickRange && time - button.pressTime < 500_000_000)
            {
                // EngineEvents.post(EngineEvent.MOUSE_BUTTON_CLICKED, button, this.pos, true); // TODO
            }
            else if (inClickRange)
            {
                // EngineEvents.post(EngineEvent.MOUSE_BUTTON_CLICKED, button, this.pos, false); // TODO
                button.dClick.set(this.pos);
                button.pressTime = time;
            }
            if (this.drag == button) this.drag = null;
        }
        button.dragged = false;
        if (button.held)
        {
            // EngineEvents.post(EngineEvent.MOUSE_BUTTON_HELD, button, this.pos); // TODO
            
            if (this.drag == button && (this.rel.x != 0 || this.rel.y != 0))
            {
                button.dragged = true;
                
                // EngineEvents.post(EngineEvent.MOUSE_BUTTON_DRAGGED, button, this.dragPos, this.pos, this.rel); // TODO
            }
        }
        // if (button.repeat) EngineEvents.post(EngineEvent.MOUSE_BUTTON_REPEAT, button, this.pos); // TODO
    }
    
    /**
     * This is the callback used by the window whenever an input is pressed, released, or repeated
     *
     * @param reference The Input
     * @param state     The action that took place
     * @param mods      The modifier info
     */
    public void stateCallback(int reference, int state, int mods)
    {
        GLFWMouse.LOGGER.finest("GLFWMouse State Callback: %s %s", reference, state, mods);
        
        GLFWMouseButton button = GLFWMouseButton.get(reference);
        button.newState = state;
        button.newMods  = mods;
    }
}

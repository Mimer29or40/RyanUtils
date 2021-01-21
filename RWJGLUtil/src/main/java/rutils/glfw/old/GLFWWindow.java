package rutils.glfw.old;

import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import rutils.Logger;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GLFWWindow extends GLFWDevice
{
    private static final Logger LOGGER = new Logger();
    
    final long handle;
    
    // ---------- Settable ---------- //
    
    final Vector2i pos  = new Vector2i(0);
    final Vector2i _pos = new Vector2i(0);
    
    final Vector2i size  = new Vector2i(0);
    final Vector2i _size = new Vector2i(0);
    
    final Vector2i minSize  = new Vector2i(GLFW_DONT_CARE);
    final Vector2i _minSize = new Vector2i(GLFW_DONT_CARE);
    
    final Vector2i maxSize  = new Vector2i(GLFW_DONT_CARE);
    final Vector2i _maxSize = new Vector2i(GLFW_DONT_CARE);
    
    boolean focused, _focused;
    boolean windowed, _windowed;
    boolean vsync, _vsync;
    int frameRate, _frameRate;
    
    String title, _title;
    
    // ---------- Derived ---------- //
    
    GLFWMonitor monitor;
    
    final Vector2i framebufferSize  = new Vector2i(0);
    final Vector2i _framebufferSize = new Vector2i(0);
    
    double aspectRatio, fbAspectRatio;
    boolean aspectChanged, fbAspectChanged;
    
    final Matrix4d viewMatrix   = new Matrix4d();
    final Matrix4d fbViewMatrix = new Matrix4d();
    
    // boolean mouseCaptured, _mouseCaptured;
    boolean lockModsState;
    
    GLFWWindow(Builder b)
    {
        GLFWWindow.LOGGER.finer("Creating GLFWWindow");
        
        this.monitor = b.monitor != null ? this.monitor : GLFW.defaultMonitor();
        
        this.handle = glfwCreateWindow(b.width, b.height, b.title, b.windowed ? NULL : this.monitor.handle(), b.parent != null ? b.parent.handle() : NULL);
        if (this.handle == NULL) throw new RuntimeException("Failed to create the GLFW window");
        
        if (b.x != null) this.pos.x = this._pos.x = b.x;
        if (b.y != null) this.pos.y = this._pos.y = b.y;
        if (b.x != null || b.y != null) glfwSetWindowPos(this.handle, this.pos.x, this.pos.y);
        
        this.size.set(this._size.set(b.width, b.height));
        
        this.minSize.set(this._minSize.set(b.minWidth, b.minHeight));
        this.maxSize.set(this._maxSize.set(b.maxWidth, b.maxHeight));
        glfwSetWindowSizeLimits(this.handle, this.minSize.x, this.minSize.y, this.maxSize.x, this.maxSize.y);
        
        this.focused   = this._focused = b.focused;
        this.windowed  = this._windowed = b.windowed;
        this.vsync     = this._vsync = b.vsync;
        this.frameRate = this._frameRate = b.frameRate;
        
        this.title = this._title = b.title;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GLFWWindow window = (GLFWWindow) o;
        return this.handle == window.handle;
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(this.handle);
    }
    
    @Override
    public String toString()
    {
        return "GLFWWindow{" + "handle=" + this.handle + '}';
    }
    
    /**
     * @return The glfw reference id.
     */
    public long handle()
    {
        return this.handle;
    }
    
    /**
     * @return The current monitor that the window is on and will go fullscreen on.
     */
    public GLFWMonitor monitor()
    {
        return this.monitor;
    }
    
    /**
     * @return The position of the window in monitor coordinates.
     */
    public Vector2ic pos()
    {
        return this.pos;
    }
    
    /**
     * Sets the position of the window.
     *
     * @param x The new x value.
     * @param y The new y value.
     */
    public void pos(int x, int y)
    {
        this._pos.set(x, y);
    }
    
    /**
     * Sets the position of the window.
     *
     * @param pos The new pos value.
     */
    public void pos(Vector2ic pos)
    {
        this._pos.set(pos);
    }
    
    /**
     * @return The x position of the window in monitor coordinates.
     */
    public int x()
    {
        return this.pos.x;
    }
    
    /**
     * Sets the x position of the window.
     *
     * @param x The new x position.
     */
    public void x(int x)
    {
        this._pos.x = x;
    }
    
    /**
     * @return The y position of the window in monitor coordinates.
     */
    public int y()
    {
        return this.pos.y;
    }
    
    /**
     * Sets the y position of the window.
     *
     * @param y The new y position.
     */
    public void y(int y)
    {
        this._pos.y = y;
    }
    
    /**
     * @return The size of the window in monitor space.
     */
    public Vector2ic size()
    {
        return this.size;
    }
    
    /**
     * Sets the size of the window.
     *
     * @param width  The new width value.
     * @param height The new height value.
     */
    public void size(int width, int height)
    {
        this._size.set(width, height);
    }
    
    /**
     * Sets the size of the window.
     *
     * @param size The new pos value.
     */
    public void size(Vector2ic size)
    {
        this._size.set(size);
    }
    
    /**
     * @return The width of the window in monitor space.
     */
    public int width()
    {
        return this.size.x;
    }
    
    /**
     * Sets the width of the window.
     *
     * @param width The new width.
     */
    public void width(int width)
    {
        this._size.x = width;
    }
    
    /**
     * @return The height of the window in monitor space.
     */
    public int height()
    {
        return this.size.y;
    }
    
    /**
     * Sets the height of the window.
     *
     * @param height The new height.
     */
    public void height(int height)
    {
        this._size.y = height;
    }
    
    /**
     * @return The aspect ratio of the window.
     */
    public double aspectRatio()
    {
        return this.aspectRatio;
    }
    
    /**
     * @return true if the aspect ratio has changed.
     */
    public boolean aspectChanged()
    {
        return this.aspectChanged;
    }
    
    /**
     * @return The aspect ratio of the framebuffer.
     */
    public double framebufferAspectRatio()
    {
        return this.fbAspectRatio;
    }
    
    /**
     * @return true if the framebuffer aspect ratio has changed.
     */
    public boolean framebufferAspectChanged()
    {
        return this.fbAspectChanged;
    }
    
    /**
     * @return The view matrix for screen space transformations.
     */
    public Matrix4dc viewMatrix()
    {
        return this.viewMatrix;
    }
    
    /**
     * @return The view matrix for framebuffer space transformations.
     */
    public Matrix4dc framebufferViewMatrix()
    {
        return this.fbViewMatrix;
    }
    
    /**
     * @return The size of the framebuffer in pixels.
     */
    public Vector2ic frameBufferSize()
    {
        return this.framebufferSize;
    }
    
    /**
     * @return The width of the framebuffer in pixels.
     */
    public int frameBufferWidth()
    {
        return this.framebufferSize.x;
    }
    
    /**
     * @return The height of the framebuffer in pixels.
     */
    public int frameBufferHeight()
    {
        return this.framebufferSize.y;
    }
    
    /**
     * @return If the window has input focus.
     */
    public boolean focused()
    {
        return this.focused;
    }
    
    /**
     * Focuses the window.
     */
    public void focus()
    {
        this._focused = true;
    }
    
    /**
     * @return If the window is windowed in fullscreen mode.
     */
    public boolean windowed()
    {
        return this.windowed;
    }
    
    /**
     * Sets whether or not the window is windowed in fullscreen mode.
     *
     * @param windowed The new windowed state.
     */
    public void windowed(boolean windowed)
    {
        this._windowed = windowed;
    }
    
    /**
     * Toggles the windowed state.
     */
    public void toggleWindowed()
    {
        this._windowed = !this.windowed;
    }
    
    /**
     * @return If the window is locking the frame rate to the refresh rate of the current monitor.
     */
    public boolean vsync()
    {
        return this.vsync;
    }
    
    /**
     * Sets whether or not the window should lock the frame rate to the refresh rate of the current monitor.
     *
     * @param vsync The new vsync state.
     */
    public void vsync(boolean vsync)
    {
        this._vsync = vsync;
    }
    
    /**
     * Toggles the vsync state.
     */
    public void toggleVsync()
    {
        this._vsync = !this.vsync;
    }
    
    /**
     * @return The targetFrameRate of the window.
     */
    public int targetFrameRate()
    {
        return this.frameRate;
    }
    
    /**
     * Sets the target frame rate of the window.
     *
     * @param targetFrameRate The new targetFrameRate;
     */
    public void targetFrameRate(int targetFrameRate)
    {
        this._frameRate = this.frameRate;
    }
    
    /**
     * Sets the title of the window.
     *
     * @param title The new title.
     */
    public void title(String title)
    {
        this._title = title;
    }
    
    /**
     * @return If the window has captured the mouse.
     */
    public boolean mouseCaptured()
    {
        return GLFW.mouse.captureWindow == this;
    }
    
    /**
     * Captures the mouse in this window. This will release the mouse from any other window.
     */
    public void captureMouse()
    {
        GLFW.mouse.capture(this);
    }
    
    /**
     * Releases the mouse if it was captured by this window.
     */
    public void releaseMouse()
    {
        if (mouseCaptured()) GLFW.mouse.release();
    }
    
    /**
     * Called by the Engine once per frame to generate any GLFWEvents for any state changes.
     *
     * @param time  The time since the engine began in nanoseconds.
     * @param delta The time since the last frame in nanoseconds.
     */
    @Override
    public void generateGLFWEvents(long time, long delta)
    {
        boolean updateMonitor     = false;
        boolean updateMonitorData = false;
        
        if (this.pos.x != this._pos.x || this.pos.y != this._pos.y)
        {
            this.pos.set(this._pos);
            // GLFWEvents.post(SubscribeGLFWEvent.WINDOW_MOVED, this.pos); // TODO
            
            GLFW.run(() -> glfwSetWindowPos(this.handle, this.pos.x, this.pos.y));
            
            updateMonitor = true;
        }
        
        this.aspectChanged = false;
        
        if (this.size.x != this._size.x || this.size.y != this._size.y)
        {
            this.size.set(this._size);
            // GLFWEvents.post(SubscribeGLFWEvent.WINDOW_RESIZED, this.size); // TODO
            
            GLFW.run(() -> glfwSetWindowSize(this.handle, this.size.x, this.size.y));
            
            this.aspectRatio   = (double) this.size.x / (double) this.size.y;
            this.aspectChanged = true;
            
            this.viewMatrix.setOrtho(0, this.size.x, this.size.y, 0, -1F, 1F);
            
            updateMonitor = true;
        }
        
        if (this.focused != this._focused)
        {
            this.focused = this._focused;
            // GLFWEvents.post(SubscribeGLFWEvent.WINDOW_FOCUSED, this.focused); // TODO
            
            GLFW.run(() -> glfwFocusWindow(this.handle));
        }
        
        if (this.windowed != this._windowed)
        {
            this.windowed = this._windowed;
            // GLFWEvents.post(SubscribeGLFWEvent.WINDOW_WINDOWED, this.windowed); // TODO
            
            updateMonitorData = true;
        }
        
        if (this.vsync != this._vsync)
        {
            this.vsync = this._vsync;
            // GLFWEvents.post(SubscribeGLFWEvent.WINDOW_VSYNC, this.vsync); // TODO
            
            glfwSwapInterval(this.vsync ? 1 : 0);
            
            updateMonitorData = true;
        }
        
        if (this.frameRate != this._frameRate)
        {
            this.frameRate = this._frameRate;
            // GLFWEvents.post(SubscribeGLFWEvent.WINDOW_FRAME_RATE, this.frameRate); // TODO
            
            updateMonitorData = true;
        }
        
        if (!this.title.equals(this._title))
        {
            this.title = this._title;
            // GLFWEvents.post(SubscribeGLFWEvent.WINDOW_TITLE_CHANGED, this.title); // TODO
            
            GLFW.run(() -> glfwSetWindowTitle(this.handle, this.title));
        }
        
        this.fbAspectChanged = false;
        
        if (this.framebufferSize.x != this._framebufferSize.x || this.framebufferSize.y != this._framebufferSize.y)
        {
            this.framebufferSize.set(this._framebufferSize);
            // GLFWEvents.post(SubscribeGLFWEvent.WINDOW_FRAMEBUFFER_RESIZED, this.frameBufferSize); // TODO
            
            this.fbAspectRatio   = (double) this.framebufferSize.x / (double) this.framebufferSize.y;
            this.fbAspectChanged = true;
            
            this.fbViewMatrix.setOrtho(0, this.framebufferSize.x, this.framebufferSize.y, 0, -1F, 1F);
        }
        
        if (updateMonitor)
        {
            // double next, max = 0.0;
            // for (GLFWMonitor monitor : this.monitors) // TODO
            // {
            //     if ((next = monitor.isWindowIn(this)) > max)
            //     {
            //         max          = next;
            //         this.monitor = monitor;
            //     }
            // }
            // GLFWEvents.post(SubscribeGLFWEvent.WINDOW_MONITOR_CHANGED, this.monitor); // TODO
            
            updateMonitorData = true;
        }
        
        if (updateMonitorData)
        {
            GLFW.run(() -> {
                // TODO - Fullscreen
                // this.monitor.refreshRate()
                long window      = this.windowed ? NULL : this.monitor.handle();
                int  refreshRate = this.vsync ? this.monitor.refreshRate() : this.frameRate;
                glfwSetWindowMonitor(this.handle, window, this.pos.x, this.pos.y, this.size.x, this.size.y, refreshRate);
            });
        }
        
        // boolean newCapturedState = false;//GLFWMouse.captured(); // TODO
        boolean newLockModsState = GLFWModifier.lockMods();
        
        if (newLockModsState != this.lockModsState)
        {
            this.lockModsState = newLockModsState;
            
            GLFW.run(() -> glfwSetInputMode(this.handle, GLFW_LOCK_KEY_MODS, this.lockModsState ? GLFW_TRUE : GLFW_FALSE));
        }
    }
    
    /**
     * Called to update the viewport.
     */
    public void updateViewport()
    {
        if (this.aspectChanged) glViewport(0, 0, this.size.x, this.size.y);
    }
    
    /**
     * Shows the window. Starts events being process for the window.
     */
    public void show()
    {
        glfwShowWindow(this.handle);
    }
    
    /**
     * Shows the window. GLFWEvents will not longer be generated for the window.
     */
    public void hide()
    {
        glfwHideWindow(this.handle);
    }
    
    public boolean shouldClose()
    {
        return glfwWindowShouldClose(this.handle);
    }
    
    public void close()
    {
        glfwSetWindowShouldClose(this.handle, true);
    }
    
    /**
     * Makes the current thread current to allow OpenGL rendering.
     */
    public void makeCurrent()
    {
        glfwMakeContextCurrent(this.handle);
    }
    
    /**
     * Makes the current thread not current to allow for context switching.
     */
    public void unmakeCurrent()
    {
        glfwMakeContextCurrent(NULL);
    }
    
    /**
     * @return If the window is the current context.
     */
    public boolean isCurrent()
    {
        return this.handle == glfwGetCurrentContext();
    }
    
    /**
     * Swaps buffers to show what was rendered on the screen.
     */
    public void swap()
    {
        glfwSwapBuffers(this.handle);
    }
    
    /**
     * Destroys the window.
     */
    public void destroy()
    {
        if (this.handle != NULL)
        {
            glfwFreeCallbacks(this.handle);
            glfwDestroyWindow(this.handle);
        }
    }
    
    @SuppressWarnings("unused")
    static class Builder
    {
        private GLFWWindow parent = null;
        
        private Integer x = null;
        private Integer y = null;
        
        private int width  = 800;
        private int height = 600;
        
        private int minWidth  = GLFW_DONT_CARE;
        private int minHeight = GLFW_DONT_CARE;
        
        private int maxWidth  = GLFW_DONT_CARE;
        private int maxHeight = GLFW_DONT_CARE;
        
        private boolean focused   = true;
        private boolean windowed  = true;
        private boolean vsync     = false;
        private int     frameRate = 60;
        
        private String title = "";
        
        private GLFWMonitor monitor = null;
        
        public Builder parent(GLFWWindow parent)
        {
            this.parent = parent;
            
            return this;
        }
        
        public Builder monitor(GLFWMonitor monitor)
        {
            this.monitor = monitor;
            
            return this;
        }
        
        public Builder position(int x, int y)
        {
            this.x = x;
            this.y = y;
            
            return this;
        }
        
        public Builder x(int x)
        {
            this.x = x;
            
            return this;
        }
        
        public Builder y(int y)
        {
            this.y = y;
            
            return this;
        }
        
        public Builder size(int width, int height)
        {
            this.width  = width;
            this.height = height;
            
            return this;
        }
        
        public Builder width(int width)
        {
            this.width = width;
            
            return this;
        }
        
        public Builder height(int height)
        {
            this.height = height;
            
            return this;
        }
        
        public Builder minSize(int width, int height)
        {
            this.minWidth  = width;
            this.minHeight = height;
            
            return this;
        }
        
        public Builder minWidth(int width)
        {
            this.minWidth = width;
            
            return this;
        }
        
        public Builder minHeight(int height)
        {
            this.minHeight = height;
            
            return this;
        }
        
        public Builder maxSize(int width, int height)
        {
            this.maxWidth  = width;
            this.maxHeight = height;
            
            return this;
        }
        
        public Builder maxWidth(int width)
        {
            this.maxWidth = width;
            
            return this;
        }
        
        public Builder maxHeight(int height)
        {
            this.maxHeight = height;
            
            return this;
        }
        
        public Builder focused(boolean focused)
        {
            this.focused = focused;
            
            return this;
        }
        
        public Builder windowed(boolean windowed)
        {
            this.windowed = windowed;
            
            return this;
        }
        
        public Builder vsync(boolean vsync)
        {
            this.vsync = vsync;
            
            return this;
        }
        
        public Builder frameRate(int frameRate)
        {
            this.frameRate = frameRate;
            
            return this;
        }
        
        public Builder title(String title)
        {
            this.title = title;
            
            return this;
        }
    }
}

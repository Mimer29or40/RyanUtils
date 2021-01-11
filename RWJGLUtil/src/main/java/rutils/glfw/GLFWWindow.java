package rutils.glfw;

import org.joml.Matrix4d;
import org.joml.Matrix4dc;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.lwjgl.glfw.GLFWErrorCallback;
import rutils.Logger;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

public class GLFWWindow
{
    private static final Logger LOGGER = new Logger();
    
    final long handle;
    
    GLFWMonitor monitor;
    GLFWWindow  parent;
    
    final Vector2i pos     = new Vector2i(0);
    final Vector2i newPos  = new Vector2i(0);
    final Vector2i fullPos = new Vector2i(0);
    boolean posChanged;
    
    final Vector2i size     = new Vector2i(0);
    final Vector2i newSize  = new Vector2i(0);
    final Vector2i fullSize = new Vector2i(0);
    boolean sizeChanged;
    
    final Vector2i minSize = new Vector2i(GLFW_DONT_CARE);
    final Vector2i maxSize = new Vector2i(GLFW_DONT_CARE);
    
    double  aspectRatio;
    boolean aspectChanged;
    double  framebufferAspectRatio;
    boolean framebufferAspectChanged;
    
    final Matrix4d viewMatrix            = new Matrix4d();
    final Matrix4d framebufferViewMatrix = new Matrix4d();
    
    final Vector2i framebufferSize    = new Vector2i(0);
    final Vector2i newFramebufferSize = new Vector2i(0);
    
    boolean focused, newFocused;
    
    boolean fullscreen, newFullscreen;
    boolean windowed = true, newWindowed;
    boolean windowedChanged;
    boolean vsync = false, newVsync;
    int targetFrameRate = 60, newTargetFrameRate;
    
    String title = "", newTitle;
    boolean capturedState, lockModsState;
    
    GLFWWindow(Builder builder)
    {
        GLFWWindow.LOGGER.finer("Creating GLFWWindow");
        
        this.size.set(builder.width, builder.height);
        this.minSize.set(builder.minWidth, builder.minHeight);
        this.maxSize.set(builder.maxWidth, builder.maxHeight);
        
        this.newTitle   = builder.title;
        this.fullscreen = builder.fullscreen;
        
        this.monitor = builder.monitor != null ? this.monitor : GLFW.defaultMonitor();
        this.parent  = builder.parent;
        
        this.handle = glfwCreateWindow(this.size.x, this.size.y, this.title, this.fullscreen && !this.windowed ? this.monitor.handle() : NULL, this.parent != null ? this.parent.handle() : NULL);
        if (this.handle == NULL) throw new RuntimeException("Failed to create the GLFW window");
        
        GLFWWindow.LOGGER.finest("GLFWEnum: Checking GLFWWindow Size");
        
        if (this.fullscreen) this.size.set(this.monitor.size()); // TODO
        GLFWWindow.LOGGER.finest("GLFWWindow Size: %s", this.size);
        
        glfwSetWindowSizeLimits(this.handle, this.minSize.x, this.minSize.y, this.maxSize.x, this.maxSize.y);
        
        this.pos.x = builder.x == null ? (this.monitor.width() - this.size.x) >> 1 : builder.x;
        this.pos.y = builder.y == null ? (this.monitor.height() - this.size.y) >> 1 : builder.y;
        glfwSetWindowPos(this.handle, this.pos.x, this.pos.y);
        
        GLFWWindow.LOGGER.fine("GLFWWindow Created");
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
        this.newPos.set(x, y);
    }
    
    /**
     * Sets the position of the window.
     *
     * @param pos The new pos value.
     */
    public void pos(Vector2ic pos)
    {
        this.newPos.set(pos);
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
        this.newPos.x = x;
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
        this.newPos.y = y;
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
        this.newSize.set(width, height);
    }
    
    /**
     * Sets the size of the window.
     *
     * @param size The new pos value.
     */
    public void size(Vector2ic size)
    {
        this.newSize.set(size);
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
        this.newSize.x = width;
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
        this.newSize.y = height;
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
        return this.framebufferAspectRatio;
    }
    
    /**
     * @return true if the framebuffer aspect ratio has changed.
     */
    public boolean framebufferAspectChanged()
    {
        return this.framebufferAspectChanged;
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
        return this.framebufferViewMatrix;
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
     * @return If the window is in fullscreen mode.
     */
    public boolean fullscreen()
    {
        return this.fullscreen;
    }
    
    /**
     * Sets whether or not the window is in fullscreen mode.
     *
     * @param fullscreen The new fullscreen state.
     */
    public void fullscreen(boolean fullscreen)
    {
        this.newFullscreen = fullscreen;
    }
    
    /**
     * Toggles the fullscreen state.
     */
    public void toggleFullscreen()
    {
        this.newFullscreen = !this.fullscreen;
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
        this.newWindowed = windowed;
    }
    
    /**
     * Toggles the windowed state.
     */
    public void toggleWindowed()
    {
        this.newWindowed = !this.windowed;
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
        this.newVsync = vsync;
    }
    
    /**
     * @return The targetFrameRate of the window.
     */
    public int targetFrameRate()
    {
        return this.targetFrameRate;
    }
    
    /**
     * Sets the target frame rate of the window.
     *
     * @param targetFrameRate The new targetFrameRate;
     */
    public void targetFrameRate(int targetFrameRate)
    {
        this.newTargetFrameRate = this.targetFrameRate;
    }
    
    /**
     * Toggles the vsync state.
     */
    public void toggleVsync()
    {
        this.newVsync = !this.vsync;
    }
    
    /**
     * Sets the title of the window.
     *
     * @param title The new title.
     */
    public void title(String title)
    {
        this.newTitle = title;
    }
    
    /**
     * Called by the Engine once per frame to generate any GLFWEvents for any state changes.
     *
     * @param time  The time since the engine began in nanoseconds.
     * @param delta The time since the last frame in nanoseconds.
     */
    public void generateEvents(long time, long delta)
    {
        if (this.focused != this.newFocused)
        {
            this.focused = this.newFocused;
            // GLFWEvents.post(GLFWEvent.WINDOW_FOCUSED, this.focused); // TODO
        }
        
        if (this.fullscreen != this.newFullscreen)
        {
            this.fullscreen = this.newFullscreen;
            // GLFWEvents.post(GLFWEvent.WINDOW_FULLSCREEN, this.fullscreen); // TODO
            
            if (this.fullscreen)
            {
                this.fullPos.set(this.pos);
                this.fullSize.set(this.size);
                this.newPos.set(this.monitor.pos());
                this.newSize.set(this.monitor.size());
            }
            else
            {
                this.newPos.set(this.fullPos);
                this.newSize.set(this.fullSize);
            }
        }
        
        if (this.windowed != this.newWindowed)
        {
            this.windowed = this.newWindowed;
            // GLFWEvents.post(GLFWEvent.WINDOW_WINDOWED, this.windowed); // TODO
            
            this.windowedChanged = true;
        }
        
        if (this.vsync != this.newVsync)
        {
            this.vsync = this.newVsync;
            // GLFWEvents.post(GLFWEvent.WINDOW_VSYNC, this.vsync); // TODO
            
            glfwSwapInterval(this.vsync ? 1 : 0);
        }
        
        if (this.pos.x != this.newPos.x || this.pos.y != this.newPos.y)
        {
            this.pos.set(this.newPos);
            // GLFWEvents.post(GLFWEvent.WINDOW_MOVED, this.pos); // TODO
            
            this.posChanged = true;
            
            // double next, max = 0.0;
            // for (GLFWMonitor monitor : this.monitors) // TODO
            // {
            //     if ((next = monitor.isWindowIn(this)) > max)
            //     {
            //         max          = next;
            //         this.monitor = monitor;
            //     }
            // }
        }
        
        this.aspectChanged = false;
        
        if (this.size.x != this.newSize.x || this.size.y != this.newSize.y)
        {
            this.size.set(this.newSize);
            // GLFWEvents.post(GLFWEvent.WINDOW_RESIZED, this.size); // TODO
            
            this.aspectRatio   = (double) this.size.x / (double) this.size.y;
            this.aspectChanged = true;
            
            this.viewMatrix.setOrtho(0, this.size.x, this.size.y, 0, -1F, 1F);
            
            this.sizeChanged = true;
        }
        
        this.framebufferAspectChanged = false;
        
        if (this.framebufferSize.x != this.newFramebufferSize.x || this.framebufferSize.y != this.newFramebufferSize.y)
        {
            this.framebufferSize.set(this.newFramebufferSize);
            // GLFWEvents.post(GLFWEvent.WINDOW_FRAMEBUFFER_RESIZED, this.frameBufferSize); // TODO
            
            this.framebufferAspectRatio   = (double) this.framebufferSize.x / (double) this.framebufferSize.y;
            this.framebufferAspectChanged = true;
            
            this.framebufferViewMatrix.setOrtho(0, this.framebufferSize.x, this.framebufferSize.y, 0, -1F, 1F);
        }
    }
    
    /**
     * Processes all pending events. This will call the callback methods.
     */
    void processEvents()
    {
        // glfwPollEvents();
        
        boolean newCapturedState = false;//Mouse.captured(); // TODO
        boolean newLockModsState = false;//Modifier.lockMods(); // TODO
        
        if (newCapturedState != this.capturedState)
        {
            glfwSetCursorPos(this.handle, this.size.x * 0.5, this.size.y * 0.5);
            this.capturedState = newCapturedState;
            glfwSetInputMode(this.handle, GLFW_CURSOR, this.capturedState ? GLFW_CURSOR_DISABLED : GLFW_CURSOR_NORMAL);
        }
        
        if (newLockModsState != this.lockModsState)
        {
            this.lockModsState = newLockModsState;
            glfwSetInputMode(this.handle, GLFW_LOCK_KEY_MODS, this.lockModsState ? GLFW_TRUE : GLFW_FALSE);
        }
        
        if (this.posChanged)
        {
            glfwSetWindowPos(this.handle, this.pos.x, this.pos.y);
            this.posChanged = false;
        }
        
        if (this.sizeChanged)
        {
            glfwSetWindowSize(this.handle, this.size.x, this.size.y);
            this.sizeChanged = false;
        }
        
        if (!this.title.equals(this.newTitle))
        {
            this.title = this.newTitle;
            glfwSetWindowTitle(this.handle, this.title);
        }
        
        if (this.windowedChanged)
        {
            // TODO - glfwSetWindowMonitor(long window, long monitor, int xpos, int ypos, int width, int height, int refreshRate)
            // this.monitor.refreshRate()
            long window      = this.windowed ? NULL : this.monitor.handle();
            int  refreshRate = this.vsync ? this.monitor.refreshRate() : this.targetFrameRate;
            glfwSetWindowMonitor(this.handle, window, 0, 0, this.size.x, this.size.y, refreshRate);
            
            this.windowedChanged = false;
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
        
        GLFWErrorCallback errorCallback = glfwSetErrorCallback(null);
        if (errorCallback != null) errorCallback.free();
        
        glfwTerminate();
    }
    
    @SuppressWarnings("unused")
    public static class Builder
    {
        private GLFWMonitor monitor = null;
        private GLFWWindow  parent  = null;
        
        private Integer x = null;
        private Integer y = null;
        
        private int width  = 800;
        private int height = 600;
        
        private int minWidth  = GLFW_DONT_CARE;
        private int minHeight = GLFW_DONT_CARE;
        
        private int maxWidth  = GLFW_DONT_CARE;
        private int maxHeight = GLFW_DONT_CARE;
        
        private boolean fullscreen = false;
        
        private String title = "";
        
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
        
        public Builder fullscreen(boolean fullscreen)
        {
            this.fullscreen = fullscreen;
            
            return this;
        }
        
        public Builder title(String title)
        {
            this.title = title;
            
            return this;
        }
    }
}

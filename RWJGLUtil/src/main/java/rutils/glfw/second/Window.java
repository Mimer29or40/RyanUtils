package rutils.glfw.second;

import org.joml.Vector2i;
import org.joml.Vector2ic;
import rutils.Logger;
import rutils.TaskDelegator;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window
{
    private static final Logger LOGGER = new Logger();
    
    public final TaskDelegator taskDelegator = new TaskDelegator();
    
    private final String name;
    private final long   handle;
    private final Thread thread;
    
    private final Monitor monitor;
    
    private boolean open;
    
    // -------------------- Events -------------------- //
    protected boolean close;
    boolean _close;
    
    protected boolean focused;
    boolean _focused;
    
    protected final Vector2i pos  = new Vector2i();
    final           Vector2i _pos = new Vector2i();
    
    protected final Vector2i size  = new Vector2i();
    final           Vector2i _size = new Vector2i();
    
    protected final Vector2i fbSize  = new Vector2i();
    final           Vector2i _fbSize = new Vector2i();
    
    Window(Builder builder)
    {
        this.name = builder.name;
        
        if (builder.setPos) builder.visible(false);
        builder.applyHints();
        
        this.monitor = builder.monitor != null ? builder.monitor : GLFW.primaryMonitor();
        
        Window main = GLFW.mainWindow();
        if (main != null) main.unmakeCurrent();
        
        this.handle = glfwCreateWindow(builder.width, builder.height, builder.title, builder.windowed ? NULL : this.monitor.handle(), main != null ? main.handle : 0L);
        if (this.handle == NULL) throw new RuntimeException("Failed to create the GLFW window");
        
        if (main != null) main.makeCurrent();
        
        this.open = true;
        
        if (builder.setPos)
        {
            pos(builder.x, builder.y);
            show();
        }
        
        glfwSetWindowSizeLimits(this.handle, builder.minWidth, builder.minHeight, builder.maxWidth, builder.maxHeight); // TODO - Move this to setter.
        
        // GLFW.waitRunOnMain(() -> {
        //     if (isCurrent()) glfwSwapInterval(builder.vsync ? 1 : 0);
        //     Window context = GLFW.currentContext();
        //     GLFW.currentContext(this);
        //     glfwSwapInterval(builder.vsync ? 1 : 0);
        //     GLFW.currentContext(context); // TODO - Move this to setter.
        // });
        
        // glfwSetWindowIcon(); // TODO - Handle these
        // glfwSetWindowAspectRatio(window, width, height); // TODO
        
        // TODO - Handle these
        // GLFW_FOCUSED;
        // GLFW_ICONIFIED;
        // GLFW_RESIZABLE;
        // GLFW_VISIBLE;
        // GLFW_DECORATED;
        // GLFW_FLOATING;
        // GLFW_MAXIMIZED;
        // GLFW_CENTER_CURSOR;
        // GLFW_TRANSPARENT_FRAMEBUFFER;
        // GLFW_HOVERED;
        // GLFW_FOCUS_ON_SHOW;
        // GLFW_CLIENT_API;
        // GLFW_CONTEXT_VERSION_MAJOR;
        // GLFW_CONTEXT_VERSION_MINOR;
        // GLFW_CONTEXT_REVISION;
        // GLFW_CONTEXT_ROBUSTNESS;
        // GLFW_OPENGL_FORWARD_COMPAT;
        // GLFW_OPENGL_DEBUG_CONTEXT;
        // GLFW_OPENGL_PROFILE;
        // GLFW_CONTEXT_RELEASE_BEHAVIOR;
        // GLFW_CONTEXT_NO_ERROR;
        // GLFW_CONTEXT_CREATION_API;
        // GLFW_SCALE_TO_MONITOR;
        
        this.thread = new Thread(this::runInThread, this.name != null ? this.name : "Window" + this.handle);
        this.thread.start();
    }
    
    // -------------------- Properties -------------------- //
    
    /**
     * @return The GLFW address of the monitor.
     */
    public long handle()
    {
        return this.handle;
    }
    
    /**
     * Retrieves if the window has input focus.
     *
     * @return if the window has input focus
     */
    public boolean focused()
    {
        return this.focused;
    }
    
    /**
     * Brings the specified window to front and sets input focus. The window
     * should already be visible and not iconified.
     *
     * <p>By default, both windowed and full screen mode windows are focused
     * when initially created. Set the
     * {@link Builder#focused(Boolean)} FOCUSED} flag to disable this
     * behavior.</p>
     *
     * <p>Also by default, windowed mode windows are focused when shown with
     * {@link #show}. Set the {@link Builder#focusOnShow(Boolean)} window hint
     * to disable this behavior.</p>
     *
     * <p><b>Do not use this function</b> to steal focus from other
     * applications unless you are certain that is what the user wants. Focus
     * stealing can be extremely disruptive.</p>
     *
     * <p>For a less disruptive way of getting the user's attention, see
     * {@link #requestFocus}.</p>
     */
    public void focus()
    {
        GLFW.TASK_DELEGATOR.runTask(() -> glfwFocusWindow(this.handle), false);
    }
    
    /**
     * Requests user attention to the specified window.
     *
     * <p>This function requests user attention to the specified window. On
     * platforms where this is not supported, attention is requested to the
     * application as a whole.</p>
     *
     * <p>Once the user has given attention, usually by focusing the window or
     * application, the system will end the request automatically.</p>
     */
    public void requestFocus()
    {
        GLFW.TASK_DELEGATOR.runTask(() -> glfwRequestWindowAttention(this.handle), false);
    }
    
    /**
     * Retrieves the position, in screen coordinates, of the upper-left corner
     * of the content area of the window.
     *
     * @return The position of the upper-left corner of the content area
     */
    public Vector2ic pos()
    {
        return this.pos;
    }
    
    /**
     * Retrieves the x-coordinate of the position, in screen coordinates, of
     * the upper-left corner of the content area of the window.
     *
     * @return The x-coordinate of the upper-left corner of the content area
     */
    public int x()
    {
        return this.pos.x;
    }
    
    /**
     * Retrieves the y-coordinate of the position, in screen coordinates, of
     * the upper-left corner of the content area of the window.
     *
     * @return The y-coordinate of the upper-left corner of the content area
     */
    public int y()
    {
        return this.pos.y;
    }
    
    /**
     * Sets the position, in screen coordinates, of the upper-left corner of
     * the content area of the windowed mode window. If the window is a full
     * screen window, this function does nothing.
     *
     * <p><b>Do not use this function</b> to move an already visible window
     * unless you have very good reasons for doing so, as it will confuse and
     * annoy the user.</p>
     *
     * <p>The window manager may put limits on what positions are allowed. GLFW
     * cannot and should not override these limits.</p>
     *
     * @param x The x-coordinate of the upper-left corner of the content area.
     * @param y The y-coordinate of the upper-left corner of the content area.
     */
    public void pos(int x, int y)
    {
        GLFW.TASK_DELEGATOR.waitRunTask(() -> glfwSetWindowPos(this.handle, x, y));
    }
    
    /**
     * Sets the position, in screen coordinates, of the upper-left corner of
     * the content area of the windowed mode window. If the window is a full
     * screen window, this function does nothing.
     *
     * <p><b>Do not use this function</b> to move an already visible window
     * unless you have very good reasons for doing so, as it will confuse and
     * annoy the user.</p>
     *
     * <p>The window manager may put limits on what positions are allowed. GLFW
     * cannot and should not override these limits.</p>
     *
     * @param pos The position of the upper-left corner of the content area.
     */
    public void pos(Vector2ic pos)
    {
        pos(pos.x(), pos.y());
    }
    
    /**
     * Retrieves the size, in screen coordinates, of the content area of the
     * window. If you wish to retrieve the size of the framebuffer of the
     * window in pixels, see {@link #framebufferSize framebufferSize}.
     *
     * @return The size, in screen coordinates, of the content area.
     */
    public Vector2ic size()
    {
        return this.size;
    }
    
    /**
     * Retrieves the width, in screen coordinates, of the content area of the
     * window. If you wish to retrieve the size of the framebuffer of the
     * window in pixels, see {@link #framebufferSize framebufferSize}.
     *
     * @return The width, in screen coordinates, of the content area.
     */
    public int width()
    {
        return this.size.x;
    }
    
    /**
     * Retrieves the height, in screen coordinates, of the content area of the
     * window. If you wish to retrieve the size of the framebuffer of the
     * window in pixels, see {@link #framebufferSize framebufferSize}.
     *
     * @return The height, in screen coordinates, of the content area.
     */
    public int height()
    {
        return this.size.y;
    }
    
    /**
     * Sets the size, in pixels, of the content area of the specified window.
     *
     * <p>For full screen windows, this function updates the resolution of its
     * desired video mode and switches to the video mode closest to it, without
     * affecting the window's context. As the context is unaffected, the bit
     * depths of the framebuffer remain unchanged.</p>
     *
     * <p>The window manager may put limits on what sizes are allowed. GLFW
     * cannot and should not override these limits.</p>
     *
     * @param width  The desired width, in screen coordinates, of the window content area
     * @param height The desired height, in screen coordinates, of the window content area
     */
    public void size(int width, int height)
    {
        GLFW.TASK_DELEGATOR.waitRunTask(() -> glfwSetWindowSize(this.handle, width, height));
    }
    
    /**
     * Sets the size, in pixels, of the content area of the specified window.
     *
     * <p>For full screen windows, this function updates the resolution of its
     * desired video mode and switches to the video mode closest to it, without
     * affecting the window's context. As the context is unaffected, the bit
     * depths of the framebuffer remain unchanged.</p>
     *
     * <p>The window manager may put limits on what sizes are allowed. GLFW
     * cannot and should not override these limits.</p>
     *
     * @param size The desired size, in screen coordinates, of the window content area
     */
    public void size(Vector2ic size)
    {
        size(size.x(), size.y());
    }
    
    /**
     * Retrieves the size, in pixels, of the framebuffer of the specified
     * window. If you wish to retrieve the size of the window in screen
     * coordinates, see {@link #size}.
     *
     * @return The size, in pixels, of the framebuffer
     */
    public Vector2ic framebufferSize()
    {
        return this.fbSize;
    }
    
    /**
     * Retrieves the width, in pixels, of the framebuffer of the specified
     * window. If you wish to retrieve the size of the window in screen
     * coordinates, see {@link #size}.
     *
     * @return The width, in pixels, of the framebuffer
     */
    public int framebufferWidth()
    {
        return this.fbSize.x;
    }
    
    /**
     * Retrieves the height, in pixels, of the framebuffer of the specified
     * window. If you wish to retrieve the size of the window in screen
     * coordinates, see {@link #size}.
     *
     * @return The height, in pixels, of the framebuffer
     */
    public int framebufferHeight()
    {
        return this.fbSize.y;
    }
    
    public boolean isOpen()
    {
        return this.open;
    }
    
    public boolean isCurrent()
    {
        return this.taskDelegator.waitReturnTask(() -> this.handle == glfwGetCurrentContext());
    }
    
    // -------------------- Methods -------------------- //
    
    public void makeCurrent()
    {
        this.taskDelegator.waitRunTask(() -> glfwMakeContextCurrent(this.handle));
    }
    
    public void unmakeCurrent()
    {
        this.taskDelegator.waitRunTask(() -> glfwMakeContextCurrent(0L));
    }
    
    public void show()
    {
        this.taskDelegator.runTask(() -> glfwShowWindow(this.handle), false);
    }
    
    public void hide()
    {
        this.taskDelegator.runTask(() -> glfwHideWindow(this.handle), false);
    }
    
    public void destroy()
    {
        if (this.open)
        {
            GLFW.TASK_DELEGATOR.runTask(() -> {
                glfwFreeCallbacks(this.handle);
                glfwDestroyWindow(this.handle);
            }, false);
        }
        
        this.open = false;
    }
    
    private void runInThread()
    {
        try
        {
            Window.LOGGER.fine("Starting");
            
            this.taskDelegator.setThread();
            
            makeCurrent();
            
            org.lwjgl.opengl.GL.createCapabilities();
            
            while (!this.close && this.open)
            {
                this.taskDelegator.runTasks();
                
                if (this.close != this._close)
                {
                    this.close = this._close;
                }
                
                if (this.pos.x != this._pos.x || this.pos.y != this._pos.y)
                {
                    this.pos.set(this._pos);
                }
                
                if (this.size.x != this._size.x || this.size.y != this._size.y)
                {
                    this.size.set(this._size);
                }
                
                if (this.fbSize.x != this._fbSize.x || this.fbSize.y != this._fbSize.y)
                {
                    this.fbSize.set(this._fbSize);
                }
                
                Thread.yield();
            }
        }
        catch (Exception e)
        {
            Window.LOGGER.severe(e);
        }
        finally
        {
            Window.LOGGER.fine("Stopping");
            
            org.lwjgl.opengl.GL.destroy();
            
            destroy();
        }
    }
    
    @SuppressWarnings({"unused", "UnusedReturnValue"})
    public static final class Builder
    {
        private String name = null;
        
        private Monitor monitor = null;
        
        private boolean setPos = false;
        private int     x      = 0;
        private int     y      = 0;
        
        private int width  = 800;
        private int height = 600;
        
        private int minWidth  = GLFW_DONT_CARE;
        private int minHeight = GLFW_DONT_CARE;
        
        private int maxWidth  = GLFW_DONT_CARE;
        private int maxHeight = GLFW_DONT_CARE;
        
        private boolean windowed = true;
        private boolean vsync    = false;
        
        private String title = "";
        
        private Boolean resizable              = null; // RESIZABLE TRUE TRUE or FALSE
        private Boolean visible                = null; // VISIBLE TRUE TRUE or FALSE
        private Boolean decorated              = null; // DECORATED TRUE TRUE or FALSE
        private Boolean focused                = null; // FOCUSED TRUE TRUE or FALSE
        private Boolean autoIconify            = null; // AUTO_ICONIFY TRUE TRUE or FALSE
        private Boolean floating               = null; // FLOATING FALSE TRUE or FALSE
        private Boolean maximized              = null; // MAXIMIZED FALSE TRUE or FALSE
        private Boolean centerCursor           = null; // CENTER_CURSOR TRUE TRUE or FALSE
        private Boolean transparentFramebuffer = null; // TRANSPARENT_FRAMEBUFFER FALSE TRUE or FALSE
        private Boolean focusOnShow            = null; // FOCUS_ON_SHOW TRUE TRUE or FALSE
        private Boolean scaleToMonitor         = null; // SCALE_TO_MONITOR FALSE TRUE or FALSE
        
        private Integer redBits        = null; // RED_BITS 8 0 to Integer.MAX_VALUE or DONT_CARE
        private Integer greenBits      = null; // GREEN_BITS 8 0 to Integer.MAX_VALUE or DONT_CARE
        private Integer blueBits       = null; // BLUE_BITS 8 0 to Integer.MAX_VALUE or DONT_CARE
        private Integer alphaBits      = null; // ALPHA_BITS 8 0 to Integer.MAX_VALUE or DONT_CARE
        private Integer depthBits      = null; // DEPTH_BITS 24 0 to Integer.MAX_VALUE or DONT_CARE
        private Integer stencilBits    = null; // STENCIL_BITS 8 0 to Integer.MAX_VALUE or DONT_CARE
        private Integer accumRedBits   = null; // ACCUM_RED_BITS 0 0 to Integer.MAX_VALUE or DONT_CARE
        private Integer accumGreenBits = null; // ACCUM_GREEN_BITS 0 0 to Integer.MAX_VALUE or DONT_CARE
        private Integer accumBlueBits  = null; // ACCUM_BLUE_BITS 0 0 to Integer.MAX_VALUE or DONT_CARE
        private Integer accumAlphaBits = null; // ACCUM_ALPHA_BITS 0 0 to Integer.MAX_VALUE or DONT_CARE
        private Integer auxBuffers     = null; // AUX_BUFFERS 0 0 to Integer.MAX_VALUE or DONT_CARE
        private Boolean stereo         = null; // STEREO FALSE TRUE or FALSE
        private Integer samples        = null; // SAMPLES 0 0 to Integer.MAX_VALUE or DONT_CARE
        private Boolean srgbCapable    = null; // SRGB_CAPABLE FALSE TRUE or FALSE
        private Boolean doublebuffer   = null; // DOUBLEBUFFER TRUE TRUE or FALSE
        
        private Integer refreshRate = null; // REFRESH_RATE DONT_CARE 0 to Integer.MAX_VALUE or DONT_CARE
        
        private Integer clientApi              = null; // CLIENT_API OPENGL_API NO_API OPENGL_API OPENGL_ES_API
        private Integer contextCreationApi     = null; // CONTEXT_CREATION_API NATIVE_CONTEXT_API NATIVE_CONTEXT_API EGL_CONTEXT_API OSMESA_CONTEXT_API
        private Integer contextVersionMajor    = null; // CONTEXT_VERSION_MAJOR 1 Any valid major version number of the chosen client API
        private Integer contextVersionMinor    = null; // CONTEXT_VERSION_MINOR 0 Any valid minor version number of the chosen client API
        private Boolean openglForwardCompat    = null; // OPENGL_FORWARD_COMPAT FALSE TRUE or FALSE
        private Boolean openglDebugContext     = null; // OPENGL_DEBUG_CONTEXT FALSE TRUE or FALSE
        private Integer openglProfile          = null; // OPENGL_PROFILE OPENGL_ANY_PROFILE OPENGL_ANY_PROFILE OPENGL_CORE_PROFILE OPENGL_COMPAT_PROFILE
        private Integer contextRobustness      = null; // CONTEXT_ROBUSTNESS NO_ROBUSTNESS NO_ROBUSTNESS NO_RESET_NOTIFICATION LOSE_CONTEXT_ON_RESET
        private Integer contextReleaseBehavior = null; // CONTEXT_RELEASE_BEHAVIOR ANY_RELEASE_BEHAVIOR ANY_RELEASE_BEHAVIOR RELEASE_BEHAVIOR_FLUSH RELEASE_BEHAVIOR_NONE
        private Boolean contextNoError         = null; // CONTEXT_NO_ERROR FALSE TRUE or FALSE
        
        private Boolean cocoaRetinaFramebuffer = null; // COCOA_RETINA_FRAMEBUFFER TRUE TRUE or FALSE
        private String  cocoaFrameName         = null; // COCOA_FRAME_NAME "" A UTF-8 encoded frame auto save name
        private Boolean cocoaGraphicsSwitching = null; // COCOA_GRAPHICS_SWITCHING FALSE TRUE or FALSE
        
        private String x11ClassName    = null; // X11_CLASS_NAME "" An ASCII encoded WM_CLASS class name
        private String x11InstanceName = null; // X11_INSTANCE_NAME "" An ASCII encoded WM_CLASS instance name
        
        /**
         * Sets the name of the window. This is currently only used to name the
         * thread.
         *
         * @param name The name of the window.
         * @return This instance for call chaining.
         */
        public Builder name(String name)
        {
            this.name = name;
            return this;
        }
        
        /**
         * Sets the initial monitor that the window will be placed in, or the
         * primary monitor if {@code null} supplied.
         *
         * @param monitor The initial monitor that the window will be placed in.
         * @return This instance for call chaining.
         */
        public Builder monitor(Monitor monitor)
        {
            this.monitor = monitor;
            return this;
        }
        
        /**
         * Sets the initial position, in screen coordinates, of the upper-left
         * corner of the content area of the specified windowed mode window.
         * Setting this will override the
         * {@link org.lwjgl.glfw.GLFW#GLFW_VISIBLE VISIBLE} flag.
         *
         * @param x The initial x coordinate of the window.
         * @param y The initial y coordinate of the window.
         * @return This instance for call chaining.
         */
        public Builder position(int x, int y)
        {
            this.x      = x;
            this.y      = y;
            this.setPos = true;
            return this;
        }
        
        /**
         * This function sets the initial size, in screen coordinates, of the
         * content area of the specified window.
         * <p>
         * For full screen windows, this function updates the resolution of its
         * desired video mode and switches to the video mode closest to it,
         * without affecting the window's context. As the context is
         * unaffected, the bit depths of the framebuffer remain unchanged.
         * <p>
         * The window manager may put limits on what sizes are allowed. GLFW cannot and should not override these limits.
         *
         * @param width  The initial width of the window.
         * @param height The initial height of the window.
         * @return This instance for call chaining.
         */
        public Builder size(int width, int height)
        {
            this.width  = width;
            this.height = height;
            return this;
        }
        
        /**
         * This function sets the minimum size of the content area of the
         * specified window. If the window is full screen, the size limits only
         * take effect once it is made windowed. If the window is not
         * resizable, this function does nothing.
         * <p>
         * The size limits are applied immediately to a windowed mode window and
         * may cause it to be resized.
         * <p>
         * The maximum dimensions must be greater than or equal to the minimum
         * dimensions and all must be greater than or equal to zero.
         *
         * @param minWidth  The minimum width, in screen coordinates, of the content area, or GLFW_DONT_CARE.
         * @param minHeight The minimum height, in screen coordinates, of the content area, or GLFW_DONT_CARE.
         * @return This instance for call chaining.
         */
        public Builder minSize(int minWidth, int minHeight)
        {
            this.minWidth  = minWidth;
            this.minHeight = minHeight;
            return this;
        }
        
        /**
         * This function sets the maximum size of the content area of the
         * specified window. If the window is full screen, the size limits only
         * take effect once it is made windowed. If the window is not
         * resizable, this function does nothing.
         * <p>
         * The size limits are applied immediately to a windowed mode window and
         * may cause it to be resized.
         * <p>
         * The maximum dimensions must be greater than or equal to the minimum
         * dimensions and all must be greater than or equal to zero.
         *
         * @param maxWidth  The maximum width, in screen coordinates, of the content area, or GLFW_DONT_CARE.
         * @param maxHeight The maximum height, in screen coordinates, of the content area, or GLFW_DONT_CARE.
         * @return This instance for call chaining.
         */
        public Builder maxSize(int maxWidth, int maxHeight)
        {
            this.maxWidth  = maxWidth;
            this.maxHeight = maxHeight;
            return this;
        }
        
        /**
         * This function sets if the window will be windowed or not
         *
         * @param windowed if the window will be windowed.
         * @return This instance for call chaining.
         */
        public Builder windowed(boolean windowed)
        {
            this.windowed = windowed;
            return this;
        }
        
        /**
         * This function sets if the window's frame rate should be locked to its monitor's refresh rate.
         *
         * @param vsync if the window will be locked the its monitor's refresh rate..
         * @return This instance for call chaining.
         */
        public Builder vsync(boolean vsync)
        {
            this.vsync = vsync;
            return this;
        }
        
        /**
         * This function sets the initial title of the window.
         *
         * @param title the initial title of the window.
         * @return This instance for call chaining.
         */
        public Builder title(String title)
        {
            this.title = title;
            return this;
        }
        
        /**
         * Specifies whether the windowed mode window will be resizable by the
         * user. The window will still be resizable using the glfwSetWindowSize
         * function. Possible values are {@code true}, {@code false} and
         * {@code null} for system default. This hint is ignored for full
         * screen and undecorated windows.
         *
         * @param resizable if the windowed mode window will be resizable by the user. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder resizable(Boolean resizable)
        {
            this.resizable = resizable;
            return this;
        }
        
        /**
         * Specifies whether the windowed mode window will be initially
         * visible. Possible values are {@code true}, {@code false} and
         * {@code null} for system default. This hint is ignored for full
         * screen and undecorated windows.
         *
         * @param visible if the windowed mode window will be initially visible. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder visible(Boolean visible)
        {
            this.visible = visible;
            return this;
        }
        
        /**
         * Specifies whether the windowed mode window will have window
         * decorations such as a border, a close widget, etc. An undecorated
         * window will not be resizable by the user but will still allow the
         * user to generate close events on some platforms. Possible values are
         * {@code true}, {@code false} and {@code null} for system default.
         * This hint is ignored for full screen and undecorated windows.
         *
         * @param decorated if the windowed mode window will have window decorations. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder decorated(Boolean decorated)
        {
            this.decorated = decorated;
            return this;
        }
        
        /**
         * Specifies whether the windowed mode window will be given input focus
         * when created. Possible values are {@code true}, {@code false} and
         * {@code null} for system default.
         * This hint is ignored for full screen and initially hidden windows.
         *
         * @param focused if the windowed mode window will be given input focus when created. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder focused(Boolean focused)
        {
            this.focused = focused;
            return this;
        }
        
        /**
         * Specifies whether the full screen window will automatically iconify
         * and restore the previous video mode on input focus loss. Possible
         * values are {@code true}, {@code false} and {@code null} for system
         * default. This hint is ignored for windowed mode windows.
         *
         * @param autoIconify if the windowed mode window will automatically iconify. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder autoIconify(Boolean autoIconify)
        {
            this.autoIconify = autoIconify;
            return this;
        }
        
        /**
         * Specifies whether the windowed mode window will be floating above
         * other regular windows, also called topmost or always-on-top. This is
         * intended primarily for debugging purposes and cannot be used to
         * implement proper full screen windows. Possible values are
         * {@code true}, {@code false} and {@code null} for system default.
         * This hint is ignored for full screen windows.
         *
         * @param floating if the windowed mode window will be floating above other regular windows. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder Floating(Boolean floating)
        {
            this.floating = floating;
            return this;
        }
        
        /**
         * Specifies whether the windowed mode window will be maximized when
         * created. Possible values are {@code true}, {@code false} and
         * {@code null} for system default. This hint is ignored for full
         * screen windows.
         *
         * @param maximized if the windowed mode window will be maximized when created. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder maximized(Boolean maximized)
        {
            this.maximized = maximized;
            return this;
        }
        
        /**
         * Specifies whether the cursor should be centered over newly created
         * full screen windows. Possible values are {@code true}, {@code false}
         * and {@code null} for system default. This hint is ignored for
         * windowed mode windows.
         *
         * @param centerCursor if the cursor should be centered over newly created full screen windows. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder centerCursor(Boolean centerCursor)
        {
            this.centerCursor = centerCursor;
            return this;
        }
        
        /**
         * Specifies whether the window framebuffer will be transparent. If
         * enabled and supported by the system, the window framebuffer alpha
         * channel will be used to combine the framebuffer with the background.
         * This does not affect window decorations. Possible values are
         * {@code true}, {@code false} and {@code null} for system default.
         *
         * <ul><b>Windows:</b> GLFW sets a color key for the window to work
         * around repainting issues with a transparent framebuffer. The chosen
         * color value is RGB 255,0,255 (magenta). This will make pixels with
         * that exact color fully transparent regardless of their alpha values.
         * If this is a problem, make these pixels any other color before
         * buffer swap.</ul>
         *
         * @param transparentFramebuffer if the window framebuffer will be transparent. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder transparentFramebuffer(Boolean transparentFramebuffer)
        {
            this.transparentFramebuffer = transparentFramebuffer;
            return this;
        }
        
        /**
         * Specifies whether the window will be given input focus when
         * glfwShowWindow is called. Possible values are {@code true},
         * {@code false} and {@code null} for system default.
         *
         * @param focusOnShow if the window will be given input focus when glfwShowWindow is called. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder focusOnShow(Boolean focusOnShow)
        {
            this.focusOnShow = focusOnShow;
            return this;
        }
        
        /**
         * Specifies whether the window content area should be resized based on
         * the monitor content scale of any monitor it is placed on. This
         * includes the initial placement when the window is created. Possible
         * values are {@code true}, {@code false} and {@code null} for system
         * default.
         * <p>
         * This hint only has an effect on platforms where screen coordinates
         * and pixels always map 1:1 such as Windows and X11. On platforms like
         * macOS the resolution of the framebuffer is changed independently of
         * the window size.
         *
         * @param scaleToMonitor if the window content area should be resized based on the monitor content scale. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder scaleToMonitor(Boolean scaleToMonitor)
        {
            this.scaleToMonitor = scaleToMonitor;
            return this;
        }
        
        /**
         * Specifies the desired bit depth of the red component of the default
         * framebuffer. A value of
         * {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE} means the
         * application has no preference.
         *
         * @param redBits the desired bit depth of the red component. In the range:<br>0 to {@link Integer#MAX_VALUE} or {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE}
         * @return This instance for call chaining.
         */
        public Builder redBits(Integer redBits)
        {
            this.redBits = redBits;
            return this;
        }
        
        /**
         * Specifies the desired bit depth of the green component of the
         * default framebuffer. A value of
         * {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE} means the
         * application has no preference.
         *
         * @param greenBits the desired bit depth of the green component. In the range:<br>0 to {@link Integer#MAX_VALUE} or {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE}
         * @return This instance for call chaining.
         */
        public Builder greenBits(Integer greenBits)
        {
            this.greenBits = greenBits;
            return this;
        }
        
        /**
         * Specifies the desired bit depth of the blue component of the default
         * framebuffer. A value of
         * {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE} means the
         * application has no preference.
         *
         * @param blueBits the desired bit depth of the blue component. In the range:<br>0 to {@link Integer#MAX_VALUE} or {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE}
         * @return This instance for call chaining.
         */
        public Builder blueBits(Integer blueBits)
        {
            this.blueBits = blueBits;
            return this;
        }
        
        /**
         * Specifies the desired bit depth of the alpha component of the
         * default framebuffer. A value of
         * {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE} means the
         * application has no preference.
         *
         * @param alphaBits the desired bit depth of the alpha component. In the range:<br>0 to {@link Integer#MAX_VALUE} or {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE}
         * @return This instance for call chaining.
         */
        public Builder alphaBits(Integer alphaBits)
        {
            this.alphaBits = alphaBits;
            return this;
        }
        
        /**
         * Specifies the desired bit depth of the depth component of the
         * default framebuffer. A value of
         * {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE} means the
         * application has no preference.
         *
         * @param depthBits the desired bit depth of the depth component. In the range:<br>0 to {@link Integer#MAX_VALUE} or {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE}
         * @return This instance for call chaining.
         */
        public Builder depthBits(Integer depthBits)
        {
            this.depthBits = depthBits;
            return this;
        }
        
        /**
         * Specifies the desired bit depth of the stencil component of the
         * default framebuffer. A value of
         * {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE} means the
         * application has no preference.
         *
         * @param stencilBits the desired bit depth of the stencil component. In the range:<br>0 to {@link Integer#MAX_VALUE} or {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE}
         * @return This instance for call chaining.
         */
        public Builder stencilBits(Integer stencilBits)
        {
            this.stencilBits = stencilBits;
            return this;
        }
        
        /**
         * Specifies the desired bit depth of the red components of the
         * accumulation buffer. A value of
         * {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE} means the
         * application has no preference.
         * <ul>Accumulation buffers are a legacy OpenGL feature and should not
         * be used in new code.</ul>
         *
         * @param accumRedBits the desired bit depth of the red component. In the range:<br>0 to {@link Integer#MAX_VALUE} or {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE}
         * @return This instance for call chaining.
         */
        public Builder accumRedBits(Integer accumRedBits)
        {
            this.accumRedBits = accumRedBits;
            return this;
        }
        
        /**
         * Specifies the desired bit depth of the green components of the
         * accumulation buffer. A value of
         * {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE} means the
         * application has no preference.
         * <ul>Accumulation buffers are a legacy OpenGL feature and should not
         * be used in new code.</ul>
         *
         * @param accumGreenBits the desired bit depth of the green component. In the range:<br>0 to {@link Integer#MAX_VALUE} or {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE}
         * @return This instance for call chaining.
         */
        public Builder accumGreenBits(Integer accumGreenBits)
        {
            this.accumGreenBits = accumGreenBits;
            return this;
        }
        
        /**
         * Specifies the desired bit depth of the blue components of the
         * accumulation buffer. A value of
         * {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE} means the
         * application has no preference.
         * <ul>Accumulation buffers are a legacy OpenGL feature and should not
         * be used in new code.</ul>
         *
         * @param accumBlueBits the desired bit depth of the blue component. In the range:<br>0 to {@link Integer#MAX_VALUE} or {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE}
         * @return This instance for call chaining.
         */
        public Builder accumBlueBits(Integer accumBlueBits)
        {
            this.accumBlueBits = accumBlueBits;
            return this;
        }
        
        /**
         * Specifies the desired bit depth of the alpha components of the
         * accumulation buffer. A value of
         * {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE} means the
         * application has no preference.
         * <ul>Accumulation buffers are a legacy OpenGL feature and should not
         * be used in new code.</ul>
         *
         * @param accumAlphaBits the desired bit depth of the alpha component. In the range:<br>0 to {@link Integer#MAX_VALUE} or {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE}
         * @return This instance for call chaining.
         */
        public Builder accumAlphaBits(Integer accumAlphaBits)
        {
            this.accumAlphaBits = accumAlphaBits;
            return this;
        }
        
        /**
         * Specifies the desired number of auxiliary buffers. A value of
         * {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE} means the
         * application has no preference.
         *
         * <ul>Auxiliary buffers are a legacy OpenGL feature and should not be
         * used in new code.</ul>
         *
         * @param auxBuffers the desired number of auxiliary buffers. In the range:<br>0 to {@link Integer#MAX_VALUE} or {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE}
         * @return This instance for call chaining.
         */
        public Builder auxBuffers(Integer auxBuffers)
        {
            this.auxBuffers = auxBuffers;
            return this;
        }
        
        /**
         * Specifies whether to use OpenGL stereoscopic rendering. Possible
         * values are {@code true}, {@code false} and {@code null} for system
         * default. This is a hard constraint.
         *
         * @param stereo if to use OpenGL stereoscopic rendering. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder stereo(Boolean stereo)
        {
            this.stereo = stereo;
            return this;
        }
        
        /**
         * Specifies the desired number of samples to use for multisampling.
         * Zero disables multisampling. A value of
         * {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE} means the
         * application has no preference.
         *
         * @param samples the desired number of samples. In the range:<br>0 to {@link Integer#MAX_VALUE} or {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE}
         * @return This instance for call chaining.
         */
        public Builder samples(Integer samples)
        {
            this.samples = samples;
            return this;
        }
        
        /**
         * Specifies whether the framebuffer should be sRGB capable. Possible
         * values are {@code true}, {@code false} and {@code null} for system
         * default. This is a hard constraint.
         *
         * <ul><b>OpenGL:</b> If enabled and supported by the system, the
         * {@code GL_FRAMEBUFFER_SRGB} enable will control sRGB rendering. By
         * default, sRGB rendering will be disabled.
         * <p>
         * <b>OpenGL ES:</b> If enabled and supported by the system, the
         * context will always have sRGB rendering enabled.</ul>
         *
         * @param srgbCapable if the framebuffer should be sRGB capable. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder srgbCapable(Boolean srgbCapable)
        {
            this.srgbCapable = srgbCapable;
            return this;
        }
        
        /**
         * Specifies whether the framebuffer should be double buffered. You
         * nearly always want to use double buffering. Possible values are
         * {@code true}, {@code false} and {@code null} for system default.
         * This is a hard constraint.
         *
         * @param doublebuffer if the framebuffer should be double buffered. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder doublebuffer(Boolean doublebuffer)
        {
            this.doublebuffer = doublebuffer;
            return this;
        }
        
        /**
         * Specifies the desired refresh rate for full screen windows. A value
         * of {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE} means the
         * highest available refresh rate will be used. This hint is ignored
         * for windowed mode windows.
         *
         * @param refreshRate the desired refresh rate for full screen windows. In the range:<br>0 to {@link Integer#MAX_VALUE} or {@link org.lwjgl.glfw.GLFW#GLFW_DONT_CARE DONT_CARE}
         * @return This instance for call chaining.
         */
        public Builder refreshRate(Integer refreshRate)
        {
            this.refreshRate = refreshRate;
            return this;
        }
        
        /**
         * Specifies which client API to create the context for. Possible
         * values are {@link org.lwjgl.glfw.GLFW#GLFW_OPENGL_API OPENGL_API},
         * {@link org.lwjgl.glfw.GLFW#GLFW_OPENGL_ES_API OPENGL_ES_API} and
         * {@link org.lwjgl.glfw.GLFW#GLFW_NO_API NO_API}. This is a hard
         * constraint.
         *
         * @param clientApi the client API to create the context for. One of:<br><table><tr><td>{@link org.lwjgl.glfw.GLFW#GLFW_OPENGL_API OPENGL_API}</td><td>{@link org.lwjgl.glfw.GLFW#GLFW_OPENGL_ES_API OPENGL_ES_API}</td><td>{@link org.lwjgl.glfw.GLFW#GLFW_NO_API NO_API}</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder clientApi(Integer clientApi)
        {
            this.clientApi = clientApi;
            return this;
        }
        
        /**
         * Specifies which context creation API to use to create the context.
         * Possible values are
         * {@link org.lwjgl.glfw.GLFW#GLFW_NATIVE_CONTEXT_API NATIVE_CONTEXT_API},
         * {@link org.lwjgl.glfw.GLFW#GLFW_EGL_CONTEXT_API EGL_CONTEXT_API} and
         * {@link org.lwjgl.glfw.GLFW#GLFW_OSMESA_CONTEXT_API OSMESA_CONTEXT_API}.
         * This is a hard constraint. If no client API is requested, this hint
         * is ignored.
         *
         * <ul><b>macOS:</b> The EGL API is not available on this platform and
         * requests to use it will fail.
         * <p>
         * <b>Wayland:</b> The EGL API is the native context creation API,
         * so this hint will have no effect.
         * <p>
         * <b>OSMesa:</b> As its name implies, an OpenGL context created
         * with OSMesa does not update the window contents when its buffers are
         * swapped. Use OpenGL functions or the OSMesa native access functions
         * glfwGetOSMesaColorBuffer and glfwGetOSMesaDepthBuffer to retrieve
         * the framebuffer contents.</ul>
         *
         * @param contextCreationApi the context creation API to use to create the context. One of:<br><table><tr><td>{@link org.lwjgl.glfw.GLFW#GLFW_NATIVE_CONTEXT_API NATIVE_CONTEXT_API}</td><td>{@link org.lwjgl.glfw.GLFW#GLFW_EGL_CONTEXT_API EGL_CONTEXT_API}</td><td>{@link org.lwjgl.glfw.GLFW#GLFW_OSMESA_CONTEXT_API OSMESA_CONTEXT_API}</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder contextCreationApi(Integer contextCreationApi)
        {
            this.contextCreationApi = contextCreationApi;
            return this;
        }
        
        /**
         * Specify the client API major version that the created context must
         * be compatible with. The exact behavior of these hints depend on the
         * requested client API.
         *
         * <ul><b>OpenGL:</b> These hints are not hard constraints, but
         * creation will fail if the OpenGL version of the created context is
         * less than the one requested. It is therefore perfectly safe to use
         * the default of version 1.0 for legacy code and you will still get
         * backwards-compatible contexts of version 3.0 and above when
         * available.
         * <p>
         * While there is no way to ask the driver for a context of the
         * highest supported version, GLFW will attempt to provide this when
         * you ask for a version 1.0 context, which is the default for these
         * hints.
         * <p>
         * <b>OpenGL ES:</b> These hints are not hard constraints, but
         * creation will fail if the OpenGL ES version of the created context
         * is less than the one requested. Additionally, OpenGL ES 1.x cannot
         * be returned if 2.0 or later was requested, and vice versa. This is
         * because OpenGL ES 3.x is backward compatible with 2.0, but OpenGL ES
         * 2.0 is not backward compatible with 1.x.</ul>
         *
         * @param contextVersionMajor the client API major version that the created context must be compatible with.
         * @return This instance for call chaining.
         */
        public Builder contextVersionMajor(Integer contextVersionMajor)
        {
            this.contextVersionMajor = contextVersionMajor;
            return this;
        }
        
        /**
         * Specify the client API minor version that the created context must
         * be compatible with. The exact behavior of these hints depend on the
         * requested client API.
         *
         * <ul><b>OpenGL:</b> These hints are not hard constraints, but
         * creation will fail if the OpenGL version of the created context is
         * less than the one requested. It is therefore perfectly safe to use
         * the default of version 1.0 for legacy code and you will still get
         * backwards-compatible contexts of version 3.0 and above when
         * available.
         * <p>
         * While there is no way to ask the driver for a context of the
         * highest supported version, GLFW will attempt to provide this when
         * you ask for a version 1.0 context, which is the default for these
         * hints.
         * <p>
         * <b>OpenGL ES:</b> These hints are not hard constraints, but
         * creation will fail if the OpenGL ES version of the created context
         * is less than the one requested. Additionally, OpenGL ES 1.x cannot
         * be returned if 2.0 or later was requested, and vice versa. This is
         * because OpenGL ES 3.x is backward compatible with 2.0, but OpenGL ES
         * 2.0 is not backward compatible with 1.x.</ul>
         *
         * @param contextVersionMinor the client API minor version that the created context must be compatible with.
         * @return This instance for call chaining.
         */
        public Builder contextVersionMinor(Integer contextVersionMinor)
        {
            this.contextVersionMinor = contextVersionMinor;
            return this;
        }
        
        /**
         * Specifies whether the OpenGL context should be forward-compatible
         * i.e. one where all functionality deprecated in the requested version
         * of OpenGL is removed. This must only be used if the requested OpenGL
         * version is 3.0 or above. If OpenGL ES is requested, this hint is
         * ignored. Possible values are {@code true}, {@code false} and
         * {@code null} for system default.
         *
         * <ul>Forward-compatibility is described in detail in the
         * <a href="https://www.opengl.org/registry/">OpenGL Reference Manual.
         * </a></ul>
         *
         * @param openglForwardCompat if the OpenGL context should be forward-compatible. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder openglForwardCompat(Boolean openglForwardCompat)
        {
            this.openglForwardCompat = openglForwardCompat;
            return this;
        }
        
        /**
         * Specifies whether the context should be created in debug mode,
         * which may provide additional error and diagnostic reporting
         * functionality. Possible values are {@code true}, {@code false} and
         * {@code null} for system default.
         *
         * <ul>Debug contexts for OpenGL and OpenGL ES are described in detail
         * by the <a href="https://www.opengl.org/registry/specs/KHR/context_flush_control.txt">GL_KHR_debug
         * extension.</a></ul>
         *
         * @param openglDebugContext if the context should be created in debug mode. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder openglDebugContext(Boolean openglDebugContext)
        {
            this.openglDebugContext = openglDebugContext;
            return this;
        }
        
        /**
         * Specifies which OpenGL profile to create the context for. Possible
         * values are
         * {@link org.lwjgl.glfw.GLFW#GLFW_OPENGL_CORE_PROFILE OPENGL_CORE_PROFILE},
         * {@link org.lwjgl.glfw.GLFW#GLFW_OPENGL_COMPAT_PROFILE OPENGL_COMPAT_PROFILE} and
         * {@link org.lwjgl.glfw.GLFW#GLFW_OPENGL_ANY_PROFILE OPENGL_ANY_PROFILE}
         * to not request a specific profile. If requesting an OpenGL version
         * below 3.2, GLFW_OPENGL_ANY_PROFILE must be used. If OpenGL ES is
         * requested, this hint is ignored.
         *
         * <ul>Forward-compatibility is described in detail in the
         * <a href="https://www.opengl.org/registry/">OpenGL Reference Manual.
         * </a></ul>
         *
         * @param openglProfile the OpenGL profile to create the context for. One of:<br><table><tr><td>{@link org.lwjgl.glfw.GLFW#GLFW_OPENGL_CORE_PROFILE OPENGL_CORE_PROFILE}</td><td>{@link org.lwjgl.glfw.GLFW#GLFW_OPENGL_COMPAT_PROFILE OPENGL_COMPAT_PROFILE}</td><td>{@link org.lwjgl.glfw.GLFW#GLFW_OPENGL_ANY_PROFILE OPENGL_ANY_PROFILE}</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder openglProfile(Integer openglProfile)
        {
            this.openglProfile = openglProfile;
            return this;
        }
        
        /**
         * Specifies the robustness strategy to be used by the context. This can be one of
         * {@link org.lwjgl.glfw.GLFW#GLFW_NO_RESET_NOTIFICATION NO_RESET_NOTIFICATION},
         * {@link org.lwjgl.glfw.GLFW#GLFW_LOSE_CONTEXT_ON_RESET LOSE_CONTEXT_ON_RESET} or
         * {@link org.lwjgl.glfw.GLFW#GLFW_NO_ROBUSTNESS NO_ROBUSTNESS}
         * to not request a robustness strategy.
         *
         * @param contextRobustness the robustness strategy to be used by the context. One of:<br><table><tr><td>{@link org.lwjgl.glfw.GLFW#GLFW_NO_RESET_NOTIFICATION NO_RESET_NOTIFICATION}</td><td>{@link org.lwjgl.glfw.GLFW#GLFW_LOSE_CONTEXT_ON_RESET LOSE_CONTEXT_ON_RESET}</td><td>{@link org.lwjgl.glfw.GLFW#GLFW_NO_ROBUSTNESS NO_ROBUSTNESS}</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder contextRobustness(Integer contextRobustness)
        {
            this.contextRobustness = contextRobustness;
            return this;
        }
        
        /**
         * Specifies the release behavior to be used by the context. Possible
         * values are one of
         * {@link org.lwjgl.glfw.GLFW#GLFW_ANY_RELEASE_BEHAVIOR ANY_RELEASE_BEHAVIOR},
         * {@link org.lwjgl.glfw.GLFW#GLFW_RELEASE_BEHAVIOR_FLUSH RELEASE_BEHAVIOR_FLUSH} or
         * {@link org.lwjgl.glfw.GLFW#GLFW_RELEASE_BEHAVIOR_NONE RELEASE_BEHAVIOR_NONE}.
         * If the behavior is
         * {@link org.lwjgl.glfw.GLFW#GLFW_ANY_RELEASE_BEHAVIOR ANY_RELEASE_BEHAVIOR},
         * the default behavior of the context creation API will be used. If
         * the behavior is
         * {@link org.lwjgl.glfw.GLFW#GLFW_RELEASE_BEHAVIOR_FLUSH RELEASE_BEHAVIOR_FLUSH},
         * the pipeline will be flushed whenever the context is released from
         * being the current one. If the behavior is
         * {@link org.lwjgl.glfw.GLFW#GLFW_RELEASE_BEHAVIOR_NONE RELEASE_BEHAVIOR_NONE},
         * the pipeline will not be flushed on release.
         *
         * <ul>Context release behaviors are described in detail by the
         * <a href="https://www.opengl.org/registry/specs/KHR/no_error.txt">GL_KHR_context_flush_control</a>
         * extension.</ul>
         *
         * @param contextReleaseBehavior the release behavior to be used by the context. One of:<br><table><tr><td>{@link org.lwjgl.glfw.GLFW#GLFW_ANY_RELEASE_BEHAVIOR ANY_RELEASE_BEHAVIOR}</td><td>{@link org.lwjgl.glfw.GLFW#GLFW_RELEASE_BEHAVIOR_FLUSH RELEASE_BEHAVIOR_FLUSH}</td><td>{@link org.lwjgl.glfw.GLFW#GLFW_RELEASE_BEHAVIOR_NONE RELEASE_BEHAVIOR_NONE}</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder contextReleaseBehavior(Integer contextReleaseBehavior)
        {
            this.contextReleaseBehavior = contextReleaseBehavior;
            return this;
        }
        
        /**
         * Specifies whether errors should be generated by the context.
         * Possible values are {@code true}, {@code false} and {@code null} for
         * system default. If enabled, situations that would have generated
         * errors instead cause undefined behavior.
         *
         * <ul>The no error mode for OpenGL and OpenGL ES is described in
         * detail by the <a href="https://www.opengl.org/registry/specs/KHR/no_error.txt">GL_KHR_no_error</a>
         * extension</ul>
         *
         * @param contextNoError if the context should be created in debug mode. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder contextNoError(Boolean contextNoError)
        {
            this.contextNoError = contextNoError;
            return this;
        }
        
        /**
         * Specifies whether to use full resolution framebuffers on Retina
         * displays. Possible values are {@code true}, {@code false} and
         * {@code null} for system default.
         *
         * @param cocoaRetinaFramebuffer if to use full resolution framebuffers on Retina displays. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder cocoaRetinaFramebuffer(Boolean cocoaRetinaFramebuffer)
        {
            this.cocoaRetinaFramebuffer = cocoaRetinaFramebuffer;
            return this;
        }
        
        /**
         * Specifies whether to use Automatic Graphics Switching, i.e. to allow
         * the system to choose the integrated GPU for the OpenGL context and
         * move it between GPUs if necessary or whether to force it to always
         * run on the discrete GPU. This only affects systems with both
         * integrated and discrete GPUs. Possible values are {@code true},
         * {@code false} and {@code null} for system default. This is ignored
         * on other platforms.
         *
         * @param cocoaGraphicsSwitching if to use Automatic Graphics Switching. One of:<br><table><tr><td>{@code true }</td><td>{@code false }</td><td>{@code null }</td></tr></table>
         * @return This instance for call chaining.
         */
        public Builder cocoaGraphicsSwitching(Boolean cocoaGraphicsSwitching)
        {
            this.cocoaGraphicsSwitching = cocoaGraphicsSwitching;
            return this;
        }
        
        /**
         * Specifies the UTF-8 encoded name to use for autosaving the window
         * frame, or if empty disables frame autosaving for the window. This is
         * ignored on other platforms.
         *
         * @param cocoaFrameName the UTF-8 encoded name to use for autosaving the window frame
         * @return This instance for call chaining.
         */
        public Builder cocoaFrameName(String cocoaFrameName)
        {
            this.cocoaFrameName = cocoaFrameName;
            return this;
        }
        
        /**
         * Specifies the desired ASCII encoded class name of the ICCCM {@code WM_CLASS} window property.
         *
         * @param x11ClassName the desired ASCII encoded class name
         * @return This instance for call chaining.
         */
        public Builder x11ClassName(String x11ClassName)
        {
            this.x11ClassName = x11ClassName;
            return this;
        }
        
        /**
         * Specifies the desired ASCII encoded instance name of the ICCCM {@code WM_CLASS} window property.
         *
         * @param x11InstanceName the desired ASCII encoded instance name
         * @return This instance for call chaining.
         */
        public Builder x11InstanceName(String x11InstanceName)
        {
            this.x11InstanceName = x11InstanceName;
            return this;
        }
        
        private void applyHints()
        {
            glfwDefaultWindowHints();
            
            applyBoolean(GLFW_RESIZABLE, this.resizable);
            applyBoolean(GLFW_VISIBLE, this.visible);
            applyBoolean(GLFW_DECORATED, this.decorated);
            applyBoolean(GLFW_FOCUSED, this.focused);
            applyBoolean(GLFW_AUTO_ICONIFY, this.autoIconify);
            applyBoolean(GLFW_FLOATING, this.floating);
            applyBoolean(GLFW_MAXIMIZED, this.maximized);
            applyBoolean(GLFW_CENTER_CURSOR, this.centerCursor);
            applyBoolean(GLFW_TRANSPARENT_FRAMEBUFFER, this.transparentFramebuffer);
            applyBoolean(GLFW_FOCUS_ON_SHOW, this.focusOnShow);
            applyBoolean(GLFW_SCALE_TO_MONITOR, this.scaleToMonitor);
            
            applyInteger(GLFW_RED_BITS, this.redBits, true);
            applyInteger(GLFW_GREEN_BITS, this.greenBits, true);
            applyInteger(GLFW_BLUE_BITS, this.blueBits, true);
            applyInteger(GLFW_ALPHA_BITS, this.alphaBits, true);
            applyInteger(GLFW_DEPTH_BITS, this.depthBits, true);
            applyInteger(GLFW_STENCIL_BITS, this.stencilBits, true);
            applyInteger(GLFW_ACCUM_RED_BITS, this.accumRedBits, true);
            applyInteger(GLFW_ACCUM_GREEN_BITS, this.accumGreenBits, true);
            applyInteger(GLFW_ACCUM_BLUE_BITS, this.accumBlueBits, true);
            applyInteger(GLFW_ACCUM_ALPHA_BITS, this.accumAlphaBits, true);
            applyInteger(GLFW_AUX_BUFFERS, this.auxBuffers, true);
            applyInteger(GLFW_SAMPLES, this.samples, true);
            applyBoolean(GLFW_STEREO, this.stereo);
            applyBoolean(GLFW_SRGB_CAPABLE, this.srgbCapable);
            applyBoolean(GLFW_DOUBLEBUFFER, this.doublebuffer);
            
            applyInteger(GLFW_REFRESH_RATE, this.refreshRate, true);
            
            applyInteger(GLFW_CLIENT_API, this.clientApi, false);
            applyInteger(GLFW_CONTEXT_CREATION_API, this.contextCreationApi, false);
            applyInteger(GLFW_CONTEXT_VERSION_MAJOR, this.contextVersionMajor, false);
            applyInteger(GLFW_CONTEXT_VERSION_MINOR, this.contextVersionMinor, false);
            applyBoolean(GLFW_OPENGL_FORWARD_COMPAT, this.openglForwardCompat);
            applyBoolean(GLFW_OPENGL_DEBUG_CONTEXT, this.openglDebugContext);
            applyInteger(GLFW_OPENGL_PROFILE, this.openglProfile, false);
            applyInteger(GLFW_CONTEXT_ROBUSTNESS, this.contextRobustness, false);
            applyInteger(GLFW_CONTEXT_RELEASE_BEHAVIOR, this.contextReleaseBehavior, false);
            applyBoolean(GLFW_CONTEXT_NO_ERROR, this.contextNoError);
            
            applyBoolean(GLFW_COCOA_RETINA_FRAMEBUFFER, this.cocoaRetinaFramebuffer);
            applyBoolean(GLFW_COCOA_GRAPHICS_SWITCHING, this.cocoaGraphicsSwitching);
            
            applyString(GLFW_COCOA_FRAME_NAME, this.cocoaFrameName);
            applyString(GLFW_X11_CLASS_NAME, this.x11ClassName);
            applyString(GLFW_X11_INSTANCE_NAME, this.x11InstanceName);
        }
        
        private void applyInteger(int glfw, Integer value, boolean dontCare)
        {
            if (value == null) return;
            
            glfwWindowHint(glfw, value >= 0 ? value : dontCare ? GLFW_DONT_CARE : 0);
        }
        
        private void applyBoolean(int glfw, Boolean value)
        {
            if (value == null) return;
            
            glfwWindowHint(glfw, value ? GLFW_TRUE : GLFW_FALSE);
        }
        
        private void applyString(int glfw, String value)
        {
            if (value == null) return;
            
            glfwWindowHintString(glfw, value);
        }
    }
}

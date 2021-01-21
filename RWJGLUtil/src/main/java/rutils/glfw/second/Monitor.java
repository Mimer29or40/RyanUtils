package rutils.glfw.second;

import org.joml.Vector2f;
import org.joml.Vector2fc;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.lwjgl.PointerBuffer;
import org.lwjgl.glfw.GLFWGammaRamp;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.NativeType;
import rutils.Logger;
import rutils.MemUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;

import static org.lwjgl.glfw.GLFW.*;

public class Monitor
{
    private static final Logger LOGGER = new Logger();
    
    private final long   handle;
    private final int    index;
    private final String name;
    
    private final ArrayList<VideoMode> videoModes = new ArrayList<>();
    
    private final Vector2i actualSize = new Vector2i();
    
    private final Vector2f scale = new Vector2f();
    
    private final Vector2i pos = new Vector2i();
    
    private final Vector2i workAreaPos  = new Vector2i();
    private final Vector2i workAreaSize = new Vector2i();
    
    Monitor(long handle, int index)
    {
        this.handle = handle;
        this.index  = index;
        this.name   = glfwGetMonitorName(this.handle);
        
        GLFWVidMode.Buffer videoModes = glfwGetVideoModes(this.handle);
        if (videoModes != null) for (int i = 0, n = videoModes.limit(); i < n; i++) this.videoModes.add(VideoMode.get(videoModes.get()));
        
        try (MemoryStack stack = MemoryStack.stackPush())
        {
            IntBuffer x = stack.mallocInt(1);
            IntBuffer y = stack.mallocInt(1);
            
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            
            FloatBuffer sx = stack.mallocFloat(1);
            FloatBuffer sy = stack.mallocFloat(1);
            
            glfwGetMonitorPhysicalSize(this.handle, w, h);
            this.actualSize.set(w.get(0), h.get(0));
            
            glfwGetMonitorContentScale(this.handle, sx, sy);
            this.scale.set(sx.get(0), sy.get(0));
            
            glfwGetMonitorPos(this.handle, x, y);
            this.pos.set(x.get(0), y.get(0));
            
            glfwGetMonitorWorkarea(this.handle, x, y, w, h);
            this.workAreaPos.set(x.get(0), y.get(0));
            this.workAreaSize.set(w.get(0), h.get(0));
        }
        
        glfwSetGamma(this.handle, 1.0F);
        
        Monitor.LOGGER.finer("Created", this);
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monitor monitor = (Monitor) o;
        return this.handle == monitor.handle;
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(this.handle);
    }
    
    @Override
    public String toString()
    {
        return "Monitor{" + "handle=" + this.handle + ", index=" + this.index + ", name='" + this.name + '\'' + '}';
    }
    
    /**
     * @return The GLFW address of the monitor.
     */
    public long handle()
    {
        return this.handle;
    }
    
    /**
     * @return The monitor index.
     */
    public int index()
    {
        return this.index;
    }
    
    /**
     * The human-readable, UTF-8 encoded name of a monitor.
     * <p>
     * Monitor names are not guaranteed to be unique. Two monitors of the same model and make may have the same name
     *
     * @return The human-readable, UTF-8 encoded name of a monitor.
     */
    public String name()
    {
        return this.name;
    }
    
    /**
     * @return The current video the monitor is in.
     */
    public VideoMode videoMode()
    {
        GLFWVidMode vidMode = glfwGetVideoMode(this.handle);
        if (vidMode != null) return VideoMode.get(vidMode);
        if (this.videoModes.size() > 0) return this.videoModes.get(0);
        return null;
    }
    
    /**
     * @return Every video mode associated with this monitor.
     */
    public List<VideoMode> videoModes()
    {
        return Collections.unmodifiableList(this.videoModes);
    }
    
    /**
     * The physical size of a monitor in millimetres, or an estimation of it.
     * This has no relation to its current resolution, i.e. the width and
     * height of its current video mode.
     * <p>
     * While this can be used to calculate the raw DPI of a monitor, this is
     * often not useful. Instead use the monitor content scale and window
     * content scale to scale your content.
     *
     * @return The physical size of a monitor in millimetres
     */
    public Vector2ic actualSize()
    {
        return this.actualSize;
    }
    
    /**
     * The physical width of a monitor in millimetres, or an estimation of it.
     * This has no relation to its current resolution, i.e. the width of its
     * current video mode.
     * <p>
     * While this can be used to calculate the raw DPI of a monitor, this is
     * often not useful. Instead use the monitor content scale and window
     * content scale to scale your content.
     *
     * @return The physical width of a monitor in millimetres
     */
    public int actualWidth()
    {
        return this.actualSize.x;
    }
    
    /**
     * The physical height of a monitor in millimetres, or an estimation of it.
     * This has no relation to its current resolution, i.e. the height of its
     * current video mode.
     * <p>
     * While this can be used to calculate the raw DPI of a monitor, this is
     * often not useful. Instead use the monitor content scale and window
     * content scale to scale your content.
     *
     * @return The physical height of a monitor in millimetres
     */
    public int actualHeight()
    {
        return this.actualSize.y;
    }
    
    /**
     * The content scale for a monitor. The content scale is the ratio between
     * the current DPI and the platform's default DPI. This is especially
     * important for text and any UI elements. If the pixel dimensions of your
     * UI scaled by this look appropriate on your machine then it should appear
     * at a reasonable size on other machines regardless of their DPI and
     * scaling settings. This relies on the system DPI and scaling settings
     * being somewhat correct.
     * <p>
     * The content scale may depend on both the monitor resolution and pixel
     * density and on user settings. It may be very different from the raw DPI
     * calculated from the physical size and current resolution.
     *
     * @return The content scale for a monitor.
     */
    public Vector2fc contentScale()
    {
        return this.scale;
    }
    
    /**
     * The horizontal content scale for a monitor. The content scale is the
     * ratio between the current DPI and the platform's default DPI. This is
     * especially important for text and any UI elements. If the pixel
     * dimensions of your UI scaled by this look appropriate on your machine
     * then it should appear at a reasonable size on other machines regardless
     * of their DPI and scaling settings. This relies on the system DPI and
     * scaling settings being somewhat correct.
     * <p>
     * The content scale may depend on both the monitor resolution and pixel
     * density and on user settings. It may be very different from the raw DPI
     * calculated from the physical size and current resolution.
     *
     * @return The horizontal content scale for a monitor.
     */
    public float contentScaleX()
    {
        return this.scale.x;
    }
    
    /**
     * The vertical content scale for a monitor. The content scale is the ratio
     * between the current DPI and the platform's default DPI. This is
     * especially important for text and any UI elements. If the pixel
     * dimensions of your UI scaled by this look appropriate on your machine
     * then it should appear at a reasonable size on other machines regardless
     * of their DPI and scaling settings. This relies on the system DPI and
     * scaling settings being somewhat correct.
     * <p>
     * The content scale may depend on both the monitor resolution and pixel
     * density and on user settings. It may be very different from the raw DPI
     * calculated from the physical size and current resolution.
     *
     * @return The horizontal content scale for a monitor.
     */
    public float contentScaleY()
    {
        return this.scale.y;
    }
    
    /**
     * @return The position of the monitor on the virtual desktop, in screen coordinates.
     */
    public Vector2ic pos()
    {
        return this.pos;
    }
    
    /**
     * @return The x position of the monitor on the virtual desktop, in screen coordinates.
     */
    public int x()
    {
        return this.pos.x;
    }
    
    /**
     * @return The y position of the monitor on the virtual desktop, in screen coordinates.
     */
    public int y()
    {
        return this.pos.y;
    }
    
    /**
     * The area of a monitor not occupied by global task bars or menu bars is the work area. This is specified in screen coordinates.
     *
     * @return The position of the work area.
     */
    public Vector2ic workAreaPos()
    {
        return this.workAreaPos;
    }
    
    /**
     * The area of a monitor not occupied by global task bars or menu bars is the work area. This is specified in screen coordinates.
     *
     * @return The x position of the work area.
     */
    public int workAreaX()
    {
        return this.workAreaPos.x;
    }
    
    /**
     * The area of a monitor not occupied by global task bars or menu bars is the work area. This is specified in screen coordinates.
     *
     * @return The y position of the work area.
     */
    public int workAreaY()
    {
        return this.workAreaPos.y;
    }
    
    /**
     * The area of a monitor not occupied by global task bars or menu bars is the work area. This is specified in screen coordinates.
     *
     * @return The size of the work area.
     */
    public Vector2ic workAreaSize()
    {
        return this.workAreaSize;
    }
    
    /**
     * The area of a monitor not occupied by global task bars or menu bars is the work area. This is specified in screen coordinates.
     *
     * @return The width of the work area.
     */
    public int workAreaWidth()
    {
        return this.workAreaSize.x;
    }
    
    /**
     * The area of a monitor not occupied by global task bars or menu bars is the work area. This is specified in screen coordinates.
     *
     * @return The height of the work area.
     */
    public int workAreaHeight()
    {
        return this.workAreaSize.y;
    }
    
    public GammaRamp gammaRamp()
    {
        GLFWGammaRamp ramp = GLFW.TASK_DELEGATOR.waitReturnTask(() -> glfwGetGammaRamp(this.handle));
        return ramp != null ? new GammaRamp(ramp) : null;
    }
    
    public void gammaRamp(GammaRamp gammaRamp)
    {
        GLFW.TASK_DELEGATOR.waitRunTask(() -> {
            try (MemoryStack stack = MemoryStack.stackPush())
            {
                GLFWGammaRamp ramp = GLFWGammaRamp.callocStack(stack);
                
                ramp.size(gammaRamp.size);
                MemUtil.memCopy(gammaRamp.red, ramp.red());
                MemUtil.memCopy(gammaRamp.green, ramp.green());
                MemUtil.memCopy(gammaRamp.blue, ramp.blue());
                
                glfwSetGammaRamp(this.handle, ramp);
            }
        });
    }
    
    public void gammaRamp(float ramp)
    {
        GLFW.TASK_DELEGATOR.waitRunTask(() -> glfwSetGamma(this.handle, ramp));
    }
    
    public int windowOverlap(Window window)
    {
        VideoMode current = videoMode();
        
        int mx = x();
        int my = y();
        int mw = current.width;
        int mh = current.height;
        
        int wx = window.x();
        int wy = window.y();
        int ww = window.width();
        int wh = window.height();
        
        return Math.max(0, Math.min(wx + ww, mx + mw) - Math.max(wx, mx)) *
               Math.max(0, Math.min(wy + wh, my + mh) - Math.max(wy, my));
    }
}

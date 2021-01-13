package rutils.glfw.second;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;
import rutils.Logger;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Collection;

import static org.lwjgl.glfw.GLFW.*;

public class Monitor
{
    private static final Logger LOGGER = new Logger();
    
    private final long   handle;
    private final int    index;
    private final String name;
    
    private final ArrayList<VideoMode> videoModes = new ArrayList<>();
    
    private final Vector2i pos = new Vector2i();
    
    private final Vector2i workSpacePos  = new Vector2i();
    private final Vector2i workSpaceSize = new Vector2i();
    
    private final Vector2i actualSize = new Vector2i();
    
    private final Vector2f scale = new Vector2f();
    
    private final float gamma;
    private final float _gamma;
    
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
            
            glfwGetMonitorPos(this.handle, x, y);
            this.pos.set(x.get(0), y.get(0));
            
            glfwGetMonitorWorkarea(this.handle, x, y, w, h);
            this.workSpacePos.set(x.get(0), y.get(0));
            this.workSpaceSize.set(w.get(0), h.get(0));
            
            glfwGetMonitorPhysicalSize(this.handle, w, h);
            this.actualSize.set(w.get(0), h.get(0));
            
            glfwGetMonitorContentScale(this.handle, sx, sy);
            this.scale.set(sx.get(0), sy.get(0));
        }
        
        this.gamma = this._gamma = 1.0F;
        glfwSetGamma(this.handle, this.gamma);
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
     *
     * @return The current video the monitor is in.
     */
    public VideoMode videoMode()
    {
        GLFWVidMode vidMode = glfwGetVideoMode(this.handle);
        if (vidMode != null) return VideoMode.get(vidMode);
        if (this.videoModes.size() > 0) return this.videoModes.get(0);
        return null;
    }
    
    public Collection<VideoMode> videoModes()
    {
        return this.videoModes;
    }
    
    public Vector2ic pos()
    {
        return this.pos;
    }
}

package rutils.glfw.second;

import org.lwjgl.glfw.GLFWVidMode;

import java.util.HashMap;
import java.util.Objects;

public class VideoMode
{
    private static final HashMap<Integer, VideoMode> CACHE = new HashMap<>();
    
    public static VideoMode get(GLFWVidMode vidMode)
    {
        int hash = Objects.hash(vidMode.width(), vidMode.height(), vidMode.redBits(), vidMode.greenBits(), vidMode.blueBits(), vidMode.refreshRate());
        return VideoMode.CACHE.computeIfAbsent(hash, h -> new VideoMode(vidMode));
    }
    
    /**
     * The resolution of a video mode is specified in screen coordinates, not pixels.
     */
    public final int width, height;
    
    /**
     * The bit depth of the video mode.
     */
    public final int redBits, greenBits, blueBits;
    
    /**
     * The refresh rate, in Hz, of the video mode.
     */
    public final int refreshRate;
    
    private final int    hash;
    private final String string;
    
    private VideoMode(GLFWVidMode vidMode)
    {
        this.width  = vidMode.width();
        this.height = vidMode.height();
        
        this.redBits   = vidMode.redBits();
        this.greenBits = vidMode.greenBits();
        this.blueBits  = vidMode.blueBits();
        
        this.refreshRate = vidMode.refreshRate();
        
        this.hash   = Objects.hash(this.width, this.height, this.redBits, this.greenBits, this.blueBits, this.refreshRate);
        this.string = String.format("VideoMode{size=(%s, %s)" + ", bits=(%s, %s, %s), refreshRate=%s}",
                                    this.width, this.height, this.redBits, this.greenBits, this.blueBits, this.refreshRate);
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VideoMode videoMode = (VideoMode) o;
        return this.hash == videoMode.hash;
    }
    
    @Override
    public int hashCode()
    {
        return this.hash;
    }
    
    @Override
    public String toString()
    {
        return this.string;
    }
}

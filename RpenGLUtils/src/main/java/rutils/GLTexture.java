package rutils;

import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_TEXTURE0;
import static org.lwjgl.opengl.GL30.glActiveTexture;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.stb.STBImageWrite.stbi_write_png;

@SuppressWarnings("unused")
public class GLTexture
{
    private static final Logger LOGGER = new Logger();
    
    protected final int id;
    protected final int width;
    protected final int height;
    protected final int channels;
    
    protected final GL format;
    
    protected GL wrapS     = GL.CLAMP_TO_EDGE;
    protected GL wrapT     = GL.CLAMP_TO_EDGE;
    protected GL minFilter = GL.NEAREST;
    protected GL magFilter = GL.NEAREST;
    
    /**
     * Creates an OpenGL texture.
     *
     * @param width    The width of the texture.
     * @param height   The height of the texture.
     * @param channels The number of channels in the texture.
     */
    public GLTexture(int width, int height, int channels)
    {
        if (channels < 1 || 4 < channels) throw new RuntimeException("Sprites can only have 1-4 channels");
        
        this.id       = glGenTextures();
        this.width    = width;
        this.height   = height;
        this.channels = channels;
        
        this.format = getFormat(channels);
        
        GLTexture.LOGGER.fine("Generated:", this);
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GLTexture texture = (GLTexture) o;
        return this.id == texture.id;
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(this.id);
    }
    
    @Override
    public String toString()
    {
        return "GLTexture{" + "id=" + this.id + ", width=" + this.width + ", height=" + this.height + ", format=" + this.format + '}';
    }
    
    /**
     * @return The OpenGL texture id.
     */
    public int id()
    {
        return this.id;
    }
    
    /**
     * @return The width in pixels.
     */
    public int width()
    {
        return this.width;
    }
    
    /**
     * @return The height in pixels.
     */
    public int height()
    {
        return this.height;
    }
    
    /**
     * @return The number of color channels
     */
    public int channels()
    {
        return this.channels;
    }
    
    /**
     * Sets the wrap mode for the texture.
     *
     * @param wrapS The new wrapS mode.
     * @param wrapT The new wrapT mode.
     * @return This instance for call chaining.
     */
    public GLTexture wrapMode(GL wrapS, GL wrapT)
    {
        this.wrapS = wrapS;
        this.wrapT = wrapT;
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, this.wrapS.ref());
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, this.wrapT.ref());
        
        return this;
    }
    
    /**
     * Sets the filter mode for the texture.
     *
     * @param minFilter The new minFilter mode.
     * @param magFilter The new magFilter mode.
     * @return This instance for call chaining.
     */
    public GLTexture filterMode(GL minFilter, GL magFilter)
    {
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, this.minFilter.ref());
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, this.magFilter.ref());
        
        return this;
    }
    
    /**
     * Binds the texture for OpenGL rendering to a texture index.
     *
     * @param textureNumber The texture index.
     * @return This instance for call chaining.
     */
    public GLTexture bind(int textureNumber)
    {
        glActiveTexture(GL_TEXTURE0 + textureNumber);
        glBindTexture(GL_TEXTURE_2D, this.id);
        
        return this;
    }
    
    /**
     * Binds the texture for OpenGL rendering to the 0 texture.
     *
     * @return This instance for call chaining.
     */
    public GLTexture bind()
    {
        return bind(0);
    }
    
    /**
     * Unbinds the texture from OpenGL rendering.
     *
     * @return This instance for call chaining.
     */
    public GLTexture unbind()
    {
        glBindTexture(GL_TEXTURE_2D, 0);
        
        return this;
    }
    
    /**
     * Destroys the texture and free's it memory.
     *
     * @return This instance for call chaining.
     */
    public GLTexture destroy()
    {
        glDeleteTextures(this.id);
        
        return this;
    }
    
    // TODO - Copying textures with glBlitNamedFramebuffer(this.id, other.id, 0, 0, this.width, this.height, 0, 0, other.width, other.height, GL_COLOR_BUFFER_BIT, GL_NEAREST);
    
    /**
     * Uploads the data in the byte buffer to the GPU
     *
     * @param data The texture data.
     * @return This instance for call chaining
     */
    public GLTexture upload(ByteBuffer data)
    {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, this.wrapS.ref());
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, this.wrapT.ref());
        
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, this.minFilter.ref());
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, this.magFilter.ref());
        
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        
        glTexImage2D(GL_TEXTURE_2D, 0, this.format.ref(), this.width, this.height, 0, this.format.ref(), GL_UNSIGNED_BYTE, data);
        
        return this;
    }
    
    /**
     * Downloads the texture from the GPU and stores it into the buffer.
     * <p>
     * Make sure to bind the texture first.
     *
     * @return This buffer containing the data.
     */
    public ByteBuffer download(ByteBuffer data)
    {
        glGetTexImage(GL_TEXTURE_2D, 0, this.format.ref(), GL_UNSIGNED_BYTE, data);
        
        return data;
    }
    
    /**
     * Downloads the texture from the GPU and stores it into the array.
     * <p>
     * Make sure to bind the texture first.
     *
     * @return This array containing the data.
     */
    public int[] download()
    {
        int[] data = new int[this.width * this.height * this.channels];
        
        glGetTexImage(GL_TEXTURE_2D, 0, this.format.ref(), GL_UNSIGNED_BYTE, data);
        
        return data;
    }
    
    /**
     * Saves the texture as a png to disk.
     *
     * @param filePath The path to the file.
     * @return This instance for call chaining.
     */
    public GLTexture saveImage(String filePath)
    {
        if (!filePath.endsWith(".png")) filePath += ".png";
        
        ByteBuffer data = download(MemoryUtil.memAlloc(this.width * this.height * this.channels));
        
        if (!stbi_write_png(filePath, this.width, this.height, this.channels, data, this.width * this.channels))
        {
            GLTexture.LOGGER.severe("Image could not be saved:", filePath);
        }
        MemoryUtil.memFree(data);
        
        return this;
    }
    
    /**
     * Loads a png file from disk.
     *
     * @param filePath The path to the file.
     * @param flip     If the image should be flipped vertically.
     * @return The new texture.
     */
    public static GLTexture loadImage(String filePath, boolean flip)
    {
        String actualPath = IOUtil.getPath(filePath).toString();
        
        stbi_set_flip_vertically_on_load(flip);
        
        int[] width    = new int[1];
        int[] height   = new int[1];
        int[] channels = new int[1];
        
        GLTexture texture = new GLTexture(width[0], height[0], channels[0]);
        
        if (stbi_info(actualPath, width, height, channels))
        {
            ByteBuffer data = stbi_load(actualPath, width, height, channels, 0);
            
            return texture.bind().upload(data).unbind();
        }
        else
        {
            GLTexture.LOGGER.severe("Failed to load Texture:", filePath);
        }
        
        stbi_set_flip_vertically_on_load(false);
        
        return texture;
    }
    
    /**
     * Loads a png file from disk.
     *
     * @param filePath The path to the file.
     * @return The new texture.
     */
    public static GLTexture loadImage(String filePath)
    {
        return loadImage(filePath, false);
    }
    
    private static GL getFormat(int channels)
    {
        return switch (channels)
                {
                    case 1 -> GL.RED;
                    case 2 -> GL.RG;
                    case 3 -> GL.RGB;
                    default -> GL.RGBA;
                };
    }
}

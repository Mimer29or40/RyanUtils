package rutils.gl;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import rutils.IOUtil;
import rutils.Logger;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL30.GL_TEXTURE0;
import static org.lwjgl.opengl.GL30.glActiveTexture;
import static org.lwjgl.opengl.GL43.glCopyImageSubData;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.stb.STBImageWrite.stbi_write_png;

public class GLTexture
{
    private static final Logger LOGGER = new Logger();
    
    protected final int id;
    
    protected final int width;
    protected final int height;
    protected final GL  format;
    
    protected final int channels;
    
    protected GL wrapS     = GL.CLAMP_TO_EDGE;
    protected GL wrapT     = GL.CLAMP_TO_EDGE;
    protected GL minFilter = GL.NEAREST;
    protected GL magFilter = GL.NEAREST;
    
    /**
     * Creates an OpenGL texture.
     *
     * @param width  The width of the texture.
     * @param height The height of the texture.
     * @param format The GL format of the texture.
     */
    public GLTexture(int width, int height, GL format)
    {
        this.id = glGenTextures();
        
        this.width  = width;
        this.height = height;
        this.format = format;
        
        this.channels = getChannels(this.format);
        
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
     * <p>
     * Make sure to bind the texture first.
     *
     * @param wrapS The new wrapS mode.
     * @param wrapT The new wrapT mode.
     * @return This instance for call chaining.
     */
    public GLTexture wrapMode(GL wrapS, GL wrapT)
    {
        this.wrapS = wrapS;
        this.wrapT = wrapT;
        
        return applyWrapMode();
    }
    
    /**
     * Sets the filter mode for the texture.
     * <p>
     * Make sure to bind the texture first.
     *
     * @param minFilter The new minFilter mode.
     * @param magFilter The new magFilter mode.
     * @return This instance for call chaining.
     */
    public GLTexture filterMode(GL minFilter, GL magFilter)
    {
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        
        return applyFilterMode();
    }
    
    /**
     * Applies the wrap mode to the texture.
     * <p>
     * Make sure to bind the texture first.
     *
     * @return This instance for call chaining.
     */
    public GLTexture applyWrapMode()
    {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, this.wrapS.ref());
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, this.wrapT.ref());
        
        return this;
    }
    
    /**
     * Applies the filter mode to the texture.
     * <p>
     * Make sure to bind the texture first.
     *
     * @return This instance for call chaining.
     */
    public GLTexture applyFilterMode()
    {
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, this.minFilter.ref());
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, this.magFilter.ref());
        
        return this;
    }
    
    /**
     * Applies the pixel alignment to the texture.
     * <p>
     * Make sure to bind the texture first.
     *
     * @return This instance for call chaining.
     */
    public GLTexture applyPixelAlignment()
    {
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        
        return this;
    }
    
    /**
     * Applies the following texture settings: applyWrapModem applyFilterMode, applyPixelAlignment
     * <p>
     * Make sure to bind the texture first.
     *
     * @return This instance for call chaining.
     */
    public GLTexture applyTextureSettings()
    {
        return applyWrapMode().applyFilterMode().applyPixelAlignment();
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
    
    /**
     * @return A new copy of this texture.
     */
    public GLTexture copy()
    {
        GLTexture other = new GLTexture(this.width, this.height, this.format);
        
        other.wrapS = this.wrapS;
        other.wrapT = this.wrapT;
        
        other.minFilter = this.minFilter;
        other.magFilter = this.magFilter;
        
        return copy(other.bind().applyTextureSettings().upload((ByteBuffer) null).unbind());
    }
    
    /**
     * Copies this texture into the other texture.
     *
     * @param other The other texture.
     * @return The other texture.
     */
    public GLTexture copy(GLTexture other)
    {
        if (this.width != other.width || this.height != other.height) throw new RuntimeException(String.format("Textures are not same size. (%s, %s) != (%s, %s)", this.width, this.height, other.width, other.height));
        if (this.format != other.format) throw new RuntimeException(String.format("Textures are not same format. %s != %s", this.format, other.format));
        
        glCopyImageSubData(this.id, GL_TEXTURE_2D, 0, 0, 0, 0,
                           other.id, GL_TEXTURE_2D, 0, 0, 0, 0,
                           this.width, this.height, this.channels);
        
        return other;
    }
    
    /**
     * Uploads the data in the byte buffer to the GPU
     * <p>
     * Make sure to bind the texture first.
     *
     * @param data The texture data.
     * @return This instance for call chaining
     */
    public GLTexture upload(ByteBuffer data)
    {
        glTexImage2D(GL_TEXTURE_2D, 0, this.format.ref(), this.width, this.height, 0, this.format.ref(), GL_UNSIGNED_BYTE, data);
        
        return this;
    }
    
    /**
     * Uploads the data in the byte buffer to the GPU
     * <p>
     * Make sure to bind the texture first.
     *
     * @param data The texture data.
     * @return This instance for call chaining
     */
    public GLTexture upload(int[] data)
    {
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
     * Downloads the texture from the GPU and stores it into the buffer.
     * <p>
     * Make sure to bind the texture first.
     *
     * @return This buffer containing the data.
     */
    public int[] download(int[] data)
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
        return download(new int[this.width * this.height * this.channels]);
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
        
        try (MemoryStack stack = MemoryStack.stackPush())
        {
            IntBuffer width    = stack.mallocInt(1);
            IntBuffer height   = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);
            
            if (stbi_info(actualPath, width, height, channels))
            {
                ByteBuffer data = stbi_load(actualPath, width, height, channels, 0);
                
                return new GLTexture(width.get(), height.get(), getFormat(channels.get())).bind().upload(data).unbind();
            }
            else
            {
                GLTexture.LOGGER.severe("Failed to load Texture:", filePath);
            }
        }
        
        stbi_set_flip_vertically_on_load(false);
        
        return new GLTexture(0, 0, GL.RED);
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
    
    private static int getChannels(GL gl)
    {
        return switch (gl)
                {
                    case RED, ALPHA, RED_INTEGER, STENCIL_INDEX, DEPTH_COMPONENT -> 1;
                    case RG, RG_INTEGER, DEPTH_STENCIL -> 2;
                    case RGB, BGR, RGB_INTEGER, BGR_INTEGER -> 3;
                    case RGBA, BGRA, RGBA_INTEGER, BGRA_INTEGER -> 4;
                    default -> throw new RuntimeException("Invalid Texture Format: " + gl);
                };
    }
}

package rutils.gl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CustomBuffer;
import org.lwjgl.system.MemoryUtil;
import rutils.Logger;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Objects;

import static rutils.MemUtil.elementSize;

public class GLBuffer
{
    private static final Logger LOGGER = new Logger();
    
    private int id;
    
    private final GL   type;
    private       long size;
    private final GL   usage;
    
    private ByteBuffer mapped = null;
    
    public GLBuffer(GL type, long size, GL usage)
    {
        this.id = GL.genBuffers();
        
        this.type  = type;
        this.size  = size;
        this.usage = usage;
        
        GL.bufferData(this.type, this.size, MemoryUtil.NULL, this.usage);
        
        GLBuffer.LOGGER.fine("Generated: %s", this);
    }
    
    public GLBuffer(GL type, @NotNull Buffer data, GL usage)
    {
        this.id = GL.genBuffers();
        
        this.type  = type;
        this.size  = Integer.toUnsignedLong(data.remaining()) * elementSize(data);
        this.usage = usage;
        
        GL.bufferData(this.type, this.size, MemoryUtil.memAddress(data), this.usage);
        
        GLBuffer.LOGGER.fine("Generated: %s", this);
    }
    
    public GLBuffer(GL type, @NotNull CustomBuffer<?> data, GL usage)
    {
        this.id = GL.genBuffers();
        
        this.type  = type;
        this.size  = Integer.toUnsignedLong(data.remaining()) * data.sizeof();
        this.usage = usage;
        
        GL.bufferData(this.type, this.size, data.address(), this.usage);
        
        GLBuffer.LOGGER.fine("Generated: %s", this);
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GLBuffer that = (GLBuffer) o;
        return this.id == that.id;
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(this.id);
    }
    
    @Override
    public String toString()
    {
        return "GLBuffer{" + "id=" + this.id + ", type=" + this.type + ", size=" + this.size + ", usage=" + this.usage + '}';
    }
    
    //-----------------------
    // ----- Properties -----
    //-----------------------
    
    /**
     * @return The buffer type.
     */
    public GL type()
    {
        return this.type;
    }
    
    /**
     * @return The size in bytes of the buffer.
     */
    public long size()
    {
        return this.size;
    }
    
    /**
     * @return The buffer usage.
     */
    public GL usage()
    {
        return this.usage;
    }
    
    //-------------------------------
    // ----- Instance Functions -----
    //-------------------------------
    
    /**
     * Binds the buffer for reading/writing.
     * <p>
     * Make sure to bind the buffer.
     *
     * @return This instance for call chaining.
     */
    public GLBuffer base(int index)
    {
        if (this.type != GL.ATOMIC_COUNTER_BUFFER && this.type != GL.TRANSFORM_FEEDBACK_BUFFER && this.type != GL.UNIFORM_BUFFER && this.type != GL.SHADER_STORAGE_BUFFER)
        {
            GLBuffer.LOGGER.warning("%s: Base is not supported for this buffer type '%s'", this, this.type);
            return this;
        }
        GLBuffer.LOGGER.fine("%s: Binding to Base: %s", this, index);
        
        GL.bindBufferBase(this.type, index, this.id);
        
        return this;
    }
    
    /**
     * Binds the buffer for reading/writing.
     *
     * @return This instance for call chaining.
     */
    public GLBuffer bind()
    {
        GLBuffer.LOGGER.finer("%s: Binding", this);
        
        GL.bindBuffer(this.type, this.id);
        
        return this;
    }
    
    /**
     * Unbinds the buffer from reading/writing.
     *
     * @return This instance for call chaining.
     */
    public GLBuffer unbind()
    {
        GLBuffer.LOGGER.finer("%s: Unbinding", this);
        
        GL.bindBuffer(this.type, 0);
        
        return this;
    }
    
    /**
     * Deletes the contents of the buffer and free's its memory.
     *
     * @return This instance for call chaining.
     */
    public GLBuffer delete()
    {
        GLBuffer.LOGGER.fine("%s: Deleting", this);
        
        GL.deleteBuffers(this.id);
        
        this.id   = 0;
        this.size = 0;
        
        this.mapped = null;
        
        return this;
    }
    
    /**
     * Maps a ByteBuffer to the data in this buffer.
     *
     * @param access the access policy
     * @return The ByteBuffer pointing to this buffer's data.
     */
    public @Nullable ByteBuffer map(@NotNull GL access)
    {
        return this.mapped = GL.mapBuffer(this.type, access, this.size, this.mapped);
    }
    
    /**
     * Unmaps the ByteBuffer from this buffer's data.
     *
     * @return This instance for call chaining.
     */
    public GLBuffer unmap()
    {
        if (!GL.unmapBuffer(this.type)) GLBuffer.LOGGER.warning("Mapped Buffer for %s was corrupted and is no longer valid.", this);
        
        return this;
    }
    
    /**
     * Gets the data in the buffer starting at an offset.
     * <p>
     * Make sure to bind the buffer first.
     *
     * @param offset The offset into the data.
     * @param buffer The destination buffer.
     * @return The data in the buffer.
     */
    public <B extends Buffer> B get(long offset, B buffer)
    {
        GLBuffer.LOGGER.finer("%s: Getting to Buffer", this);
        
        GL.getBufferSubData(this.type, offset, buffer);
        return buffer;
    }
    
    /**
     * Gets the data in the buffer.
     * <p>
     * Make sure to bind the buffer first.
     *
     * @param buffer The destination buffer.
     * @return The data in the buffer.
     */
    public <B extends Buffer> B get(B buffer)
    {
        return get(0, buffer);
    }
    
    /**
     * Gets the data in the buffer starting at an offset.
     * <p>
     * Make sure to bind the buffer first.
     *
     * @param offset The offset into the data.
     * @param buffer The destination buffer.
     * @return The data in the buffer.
     */
    public <B extends CustomBuffer<?>> B get(long offset, B buffer)
    {
        GLBuffer.LOGGER.finer("%s: Getting to CustomBuffer", this);
        
        GL.getBufferSubData(this.type, offset, buffer);
        return buffer;
    }
    
    /**
     * Gets the data in the buffer.
     * <p>
     * Make sure to bind the buffer first.
     *
     * @param buffer The destination buffer.
     * @return The data in the buffer.
     */
    public <B extends CustomBuffer<?>> B get(B buffer)
    {
        return get(0, buffer);
    }
    
    /**
     * Sets the contents of the buffer starting at the offset.
     * <p>
     * Make sure to bind the buffer first.
     *
     * @param offset The offset into the buffer to put the data.
     * @param data   The data.
     * @return This instance for call chaining.
     */
    public GLBuffer set(long offset, @NotNull Buffer data)
    {
        GLBuffer.LOGGER.finer("%s: Setting from Buffer", this);
        
        GL.bufferSubData(this.type, offset, data);
        
        return this;
    }
    
    /**
     * Sets the contents of the buffer.
     * <p>
     * Make sure to bind the buffer first.
     *
     * @param data The data.
     * @return This instance for call chaining.
     */
    public GLBuffer set(@NotNull Buffer data)
    {
        return set(0, data);
    }
    
    /**
     * Sets the contents of the buffer starting at the offset.
     * <p>
     * Make sure to bind the buffer first.
     *
     * @param offset The offset into the buffer to put the data.
     * @param data   The data.
     * @return This instance for call chaining.
     */
    public GLBuffer set(long offset, @NotNull CustomBuffer<?> data)
    {
        GLBuffer.LOGGER.finer("%s: Setting from CustomBuffer", this);
        
        GL.bufferSubData(this.type, offset, data);
        
        return this;
    }
    
    /**
     * Sets the contents of the buffer.
     * <p>
     * Make sure to bind the buffer first.
     *
     * @param data The data.
     * @return This instance for call chaining.
     */
    public GLBuffer set(@NotNull CustomBuffer<?> data)
    {
        return set(0, data);
    }
}

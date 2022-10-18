package rutils.gl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.system.CustomBuffer;
import rutils.Logger;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * A wrapper class for OpenGL's vertex arrays. This class adds helper functions that make it easy to send data to the buffers.
 */
public class GLVertexArray
{
    // TODO - Pair with GLShader
    
    private static final Logger LOGGER = new Logger();
    
    private int id;
    
    private final ArrayList<GLBuffer> buffers = new ArrayList<>();
    
    private GLBuffer indexBuffer = null;
    
    private final List<List<Attribute>> attributes = new ArrayList<>();
    
    private int vertexCount;
    
    /**
     * Creates a new GLVertexArray.
     */
    public GLVertexArray()
    {
        this.id = GL.genVertexArrays();
        
        GLVertexArray.LOGGER.fine("Generated: GLVertexArray{id=%s}", this.id);
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GLVertexArray that = (GLVertexArray) o;
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
        return "GLVertexArray{" + "id=" + this.id + ", vertex=" + printVertex() + ", vertexCount=" + this.vertexCount + (indexCount() > 0 ? ", indexCount=" + indexCount() : "") + '}';
    }
    
    public String printArrays()
    {
        return "[VBOs=" + this.buffers + ", EBO=" + this.indexBuffer + ']';
    }
    
    public String printVertex()
    {
        List<Attribute> vertex = new ArrayList<>();
        for (List<Attribute> bufferAttributes : this.attributes) vertex.addAll(bufferAttributes);
        return vertex.toString();
    }
    
    //-----------------------
    // ----- Properties -----
    //-----------------------
    
    /**
     * @return The number of attributes in the vertex array.
     */
    public int attributeAmount()
    {
        int count = 0;
        for (List<Attribute> bufferAttributes : this.attributes)
        {
            count += bufferAttributes.size();
        }
        return count;
    }
    
    /**
     * @return The count of elements in a vertex.
     */
    public int attributeCount()
    {
        int count = 0;
        for (List<Attribute> bufferAttributes : this.attributes)
        {
            for (Attribute attribute : bufferAttributes)
            {
                count += attribute.count;
            }
        }
        return count;
    }
    
    /**
     * @return The size in bytes of a vertex.
     */
    public int attributeSize()
    {
        int size = 0;
        for (List<Attribute> bufferAttributes : this.attributes)
        {
            for (Attribute attribute : bufferAttributes)
            {
                size += attribute.size;
            }
        }
        return size;
    }
    
    /**
     * @return The number of vertices in the vertex array.
     */
    public int vertexCount()
    {
        return this.vertexCount;
    }
    
    /**
     * Sets the number of vertices to draw.
     *
     * @return This instance for call chaining.
     */
    public @NotNull GLVertexArray vertexCount(int vertexCount)
    {
        this.vertexCount = vertexCount;
        
        return this;
    }
    
    /**
     * @return The number of indices in the vertex array.
     */
    public int indexCount()
    {
        return this.indexBuffer != null ? (int) (this.indexBuffer.size()) / Integer.BYTES : 0;
    }
    
    //-------------------------------
    // ----- Instance Functions -----
    //-------------------------------
    
    /**
     * Bind the GLVertexArray for use.
     *
     * @return This instance for call chaining.
     */
    public @NotNull GLVertexArray bind()
    {
        GLVertexArray.LOGGER.finer("%s: Binding", this);
        
        GL.bindVertexArray(this.id);
        
        return this;
    }
    
    /**
     * Unbind the GLVertexArray from use.
     *
     * @return This instance for call chaining.
     */
    public @NotNull GLVertexArray unbind()
    {
        GLVertexArray.LOGGER.finer("%s: Unbinding", this);
        
        GL.bindVertexArray(0);
        
        return this;
    }
    
    /**
     * Deletes the GLVertexArray and Buffers.
     *
     * @return This instance for call chaining.
     */
    public @NotNull GLVertexArray delete()
    {
        GLVertexArray.LOGGER.fine("%s: Deleting", this);
        
        for (GLBuffer vbo : this.buffers) vbo.delete();
        this.buffers.clear();
        
        if (this.indexBuffer != null) this.indexBuffer.delete();
        this.indexBuffer = null;
        
        int i = 0;
        for (List<Attribute> bufferAttributes : this.attributes)
        {
            for (Attribute ignored : bufferAttributes)
            {
                GL.disableVertexAttribArray(i++);
            }
            bufferAttributes.clear();
        }
        this.attributes.clear();
        
        GL.deleteVertexArrays(this.id);
        
        this.id          = 0;
        this.vertexCount = 0;
        
        return this;
    }
    
    //-----------------------------
    // ----- Buffer Functions -----
    //-----------------------------
    
    /**
     * Gets the GLBuffer that holds the indices bound to the GLVertexArray. Can be null.
     *
     * @return The index buffer
     */
    public @Nullable GLBuffer indexBuffer()
    {
        return this.indexBuffer;
    }
    
    /**
     * Adds an element array buffer to the Vertex Array, if one is already present then is deletes and replaces it. The buffer object will be managed by the GLVertexArray object.
     * <p>
     * Make sure to bind the vertex array first.
     *
     * @param buffer The index array buffer
     * @return This instance for call chaining.
     */
    public @NotNull GLVertexArray indexBuffer(GLBuffer buffer)
    {
        GLVertexArray.LOGGER.fine("%s: Adding EBO %s", this, buffer);
        
        if (this.indexBuffer != null) this.indexBuffer.delete();
        
        this.indexBuffer = buffer.unbind();
        
        return this;
    }
    
    /**
     * Adds an index array to the Vertex Array
     * <p>
     * Make sure to bind the vertex array first.
     *
     * @param size  The size of the buffer.
     * @param usage How the data should be used.
     * @return This instance for call chaining.
     */
    public @NotNull GLVertexArray indexBuffer(long size, GL usage)
    {
        return indexBuffer(new GLBuffer(GL.ELEMENT_ARRAY_BUFFER, size, usage));
    }
    
    /**
     * Adds an index array to the Vertex Array
     * <p>
     * Make sure to bind the vertex array first.
     *
     * @param buffer The data.
     * @param usage  How the data should be used.
     * @return This instance for call chaining.
     */
    public @NotNull GLVertexArray indexBuffer(@NotNull Buffer buffer, GL usage)
    {
        return indexBuffer(new GLBuffer(GL.ELEMENT_ARRAY_BUFFER, buffer, usage));
    }
    
    /**
     * Adds an index array to the Vertex Array
     * <p>
     * Make sure to bind the vertex array first.
     *
     * @param buffer The data.
     * @param usage  How the data should be used.
     * @return This instance for call chaining.
     */
    public @NotNull GLVertexArray indexBuffer(@NotNull CustomBuffer<?> buffer, GL usage)
    {
        return indexBuffer(new GLBuffer(GL.ELEMENT_ARRAY_BUFFER, buffer, usage));
    }
    
    /**
     * Gets a GLBuffer that has been bound to the GLVertexArray.
     *
     * @param index The index.
     * @return The GLBuffer
     */
    public @NotNull GLBuffer buffer(int index)
    {
        return this.buffers.get(index);
    }
    
    /**
     * Adds a buffer object with any number of attributes to the Vertex Array. The GLVertexArray object will manage the buffer.
     * <p>
     * Make sure to bind the vertex array first.
     *
     * @param buffer  The buffer object.
     * @param formats The type, size, normalized values for how the buffer is organized.
     * @return This instance for call chaining.
     */
    public @NotNull GLVertexArray buffer(GLBuffer buffer, Object... formats)
    {
        int n = formats.length;
        
        ArrayList<Attribute> bufferAttributes = new ArrayList<>();
        
        int stride = 0;
        
        GL      format     = null;
        Integer count      = null;
        Boolean normalized = null;
        for (int i = 0; i < n; i++)
        {
            Object currVal = formats[i];
            if (currVal instanceof GL) format = (GL) currVal;
            if (currVal instanceof Integer) count = (int) currVal;
            if (currVal instanceof Boolean) normalized = (boolean) currVal;
            
            if (i + 1 >= n || formats[i + 1] instanceof GL)
            {
                Attribute attribute = getAttribute(format, count, normalized);
                bufferAttributes.add(attribute);
                stride += attribute.size;
                
                format     = null;
                count      = null;
                normalized = null;
            }
        }
        
        GLVertexArray.LOGGER.fine("%s: Adding VBO %s of structure %s", this, buffer, bufferAttributes);
        
        buffer.bind();
        for (int i = 0, size = bufferAttributes.size(), attributeCount = attributeAmount(), offset = 0; i < size; i++)
        {
            Attribute attribute = bufferAttributes.get(i);
            GL.vertexAttribPointer(attributeCount, attribute.count, attribute.type, attribute.normalize, stride, offset);
            GL.enableVertexAttribArray(attributeCount++);
            offset += attribute.size;
        }
        this.buffers.add(buffer.unbind());
        this.attributes.add(bufferAttributes);
        
        return this;
    }
    
    /**
     * Allocates a buffer with a certain size with any number of attributes to the Vertex Array.
     * <p>
     * Make sure to bind the vertex array first.
     *
     * @param size    The size of the data in bytes
     * @param usage   How the data should be used.
     * @param formats The type, size, normalized values for how the buffer is organized.
     * @return This instance for call chaining.
     */
    public @NotNull GLVertexArray buffer(long size, GL usage, Object... formats)
    {
        return buffer(new GLBuffer(GL.ARRAY_BUFFER, size, usage), formats);
    }
    
    /**
     * Allocates a buffer with a certain size with any number of attributes to the Vertex Array.
     * <p>
     * Make sure to bind the vertex array first.
     *
     * @param buffer  The data
     * @param usage   How the data should be used.
     * @param formats The type, size, normalized values for how the buffer is organized.
     * @return This instance for call chaining.
     */
    public @NotNull GLVertexArray buffer(@NotNull Buffer buffer, GL usage, Object... formats)
    {
        return buffer(new GLBuffer(GL.ARRAY_BUFFER, buffer, usage), formats);
    }
    
    /**
     * Allocates a buffer with a certain size with any number of attributes to the Vertex Array.
     * <p>
     * Make sure to bind the vertex array first.
     *
     * @param buffer  The data
     * @param usage   How the data should be used.
     * @param formats The type, size, normalized values for how the buffer is organized.
     * @return This instance for call chaining.
     */
    public @NotNull GLVertexArray buffer(@NotNull CustomBuffer<?> buffer, GL usage, Object... formats)
    {
        return buffer(new GLBuffer(GL.ARRAY_BUFFER, buffer, usage), formats);
    }
    
    /**
     * Draws the array in the specified mode. If an element buffer is available, it used it.
     * <p>
     * Make sure to bind the vertex array first.
     *
     * @param mode The primitive type.
     * @param size The size of the buffer to draw.
     * @return This instance for call chaining.
     */
    public GLVertexArray draw(GL mode, int size)
    {
        if (this.indexBuffer != null)
        {
            GLVertexArray.LOGGER.finer("%s: Drawing Elements size=%s", this, size);
            
            GL.drawElements(mode, size, GL.UNSIGNED_INT, 0);
        }
        else
        {
            GLVertexArray.LOGGER.finer("%s: Drawing Array size=%s", this, size);
            
            GL.drawArrays(mode, 0, size);
        }
        return this;
    }
    
    /**
     * Draws the array in the specified mode. If an element buffer is available, it used it.
     * <p>
     * Make sure to bind the vertex array first.
     *
     * @param mode The primitive type.
     * @return This instance for call chaining.
     */
    public GLVertexArray draw(GL mode)
    {
        return draw(mode, this.indexBuffer != null ? indexCount() : this.vertexCount);
    }
    
    private static Object[] getFormatArray(int[] sizes, GL type)
    {
        int      n      = sizes.length;
        Object[] format = new Object[n << 1];
        for (int i = 0, index = 0; i < n; i++)
        {
            format[index++] = type;
            format[index++] = sizes[i];
        }
        return format;
    }
    
    private static int getBytes(GL type)
    {
        return switch (type)
                {
                    default -> Byte.BYTES;
                    case UNSIGNED_SHORT, SHORT -> Short.BYTES;
                    case UNSIGNED_INT, INT -> Integer.BYTES;
                    case FLOAT -> Float.BYTES;
                    case DOUBLE -> Double.BYTES;
                };
    }
    
    private static final HashMap<Integer, Attribute> ATTRIBUTE_CACHE = new HashMap<>();
    
    private static Attribute getAttribute(GL format, Integer c, Boolean n)
    {
        if (format == null) throw new RuntimeException("Invalid vertex format: GL type not provided");
        int     count     = c != null ? c : 1;
        boolean normalize = n != null ? n : false;
        
        int hash = Objects.hash(format, count, normalize);
        
        if (!GLVertexArray.ATTRIBUTE_CACHE.containsKey(hash))
        {
            Attribute attribute = new Attribute(format, count, normalize);
            
            GLVertexArray.ATTRIBUTE_CACHE.put(hash, attribute);
        }
        
        return GLVertexArray.ATTRIBUTE_CACHE.get(hash);
    }
    
    public static class Attribute
    {
        public final GL      type;
        public final int     count;
        public final boolean normalize;
        public final int     bytes;
        public final int     size;
        
        private Attribute(GL type, int count, boolean normalize)
        {
            this.type      = type;
            this.count     = count;
            this.bytes     = getBytes(type);
            this.normalize = normalize;
            this.size      = this.count * this.bytes;
        }
        
        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (!(o instanceof Attribute attribute)) return false;
            return this.type == attribute.type && this.count == attribute.count;
        }
        
        @Override
        public int hashCode()
        {
            return Objects.hash(this.type, this.count);
        }
        
        @Override
        public String toString()
        {
            return this.type + "x" + this.count;
        }
    }
}

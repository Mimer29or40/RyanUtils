package rutils.gl;

import rutils.Logger;

import java.util.Objects;

import static org.lwjgl.opengl.GL30.*;

public class GLFrameBuffer
{
    private static final Logger LOGGER = new Logger();
    
    protected final int id;
    
    public GLFrameBuffer()
    {
        this.id = glGenFramebuffers();
        
        GLFrameBuffer.LOGGER.fine("Generated:", this);
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GLFrameBuffer texture = (GLFrameBuffer) o;
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
        return "GLFrameBuffer{" + "id=" + this.id + '}';
    }
    
    /**
     * @return The OpenGL framebuffer id.
     */
    public int id()
    {
        return this.id;
    }
    
    /**
     * @return The status of the Framebuffer
     */
    public GL checkStatus()
    {
        return GL.get(glCheckFramebufferStatus(GL_FRAMEBUFFER));
    }
    
    /**
     * Binds the framebuffer for OpenGL rendering.
     *
     * @return This instance for call chaining.
     */
    public GLFrameBuffer bind()
    {
        GLFrameBuffer.LOGGER.finest("Binding:", this);
        
        glBindFramebuffer(GL_FRAMEBUFFER, this.id);
        
        return this;
    }
    
    /**
     * Unbinds the framebuffer from OpenGL rendering.
     *
     * @return This instance for call chaining.
     */
    public GLFrameBuffer unbind()
    {
        GLFrameBuffer.LOGGER.finest("Unbinding:", this);
        
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
        
        return this;
    }
    
    /**
     * Destroys the texture and free's it memory.
     *
     * @return This instance for call chaining.
     */
    public GLFrameBuffer destroy()
    {
        GLFrameBuffer.LOGGER.fine("Destroying:", this);
        
        glDeleteFramebuffers(this.id);
        
        return this;
    }
    
    /**
     * Binds a texture to this framebuffer.
     *
     * @param attachment The attachment point of the framebuffer.
     * @param texture    The texture to bind.
     * @param level      The mipmap level of the texture.
     * @return This instance for call chaining.
     */
    public GLFrameBuffer bindTexture(GL attachment, GLTexture texture, int level)
    {
        GLFrameBuffer.LOGGER.fine("Binding %s to %s", texture, this);
        
        glFramebufferTexture2D(GL_FRAMEBUFFER, attachment.ref(), GL_TEXTURE_2D, texture.id(), level);
        
        return this;
    }
    
    /**
     * Binds a texture to this framebuffer.
     *
     * @param attachment The attachment point of the framebuffer.
     * @param texture    The texture to bind.
     * @return This instance for call chaining.
     */
    public GLFrameBuffer bindTexture(GL attachment, GLTexture texture)
    {
        return bindTexture(attachment, texture, 0);
    }
}

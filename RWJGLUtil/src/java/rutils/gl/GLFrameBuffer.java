package rutils.gl;

import rutils.Logger;

import java.util.Objects;

import static org.lwjgl.opengl.GL30.*;

public class GLFrameBuffer
{
    private static final Logger LOGGER = new Logger();
    
    protected final int id;
    
    protected final int width, height;
    
    protected final GLTexture color;
    protected final GLTexture depthStencil;
    
    public GLFrameBuffer(int width, int height)
    {
        this.id = glGenFramebuffers();
        
        this.width  = width;
        this.height = height;
        
        this.color        = new GLTexture(width, height, GL.RGB).bind().allocate().wrapMode(GL.CLAMP_TO_EDGE, GL.CLAMP_TO_EDGE).filterMode(GL.NEAREST, GL.NEAREST).unbind();
        this.depthStencil = new GLTexture(width, height, GL.DEPTH24_STENCIL8).bind().allocate().wrapMode(GL.CLAMP_TO_EDGE, GL.CLAMP_TO_EDGE).filterMode(GL.NEAREST, GL.NEAREST).unbind();
        
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
    
    public int width()
    {
        return this.width;
    }
    
    public int height()
    {
        return this.height;
    }
    
    public GLTexture color()
    {
        return this.color;
    }
    
    public GLTexture depthStencil()
    {
        return this.depthStencil;
    }
    
    public GLFrameBuffer attach()
    {
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, this.color.id(), 0);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_STENCIL_ATTACHMENT, GL_TEXTURE_2D, this.depthStencil.id(), 0);
        
        return this;
    }
    
    public GLFrameBuffer validate()
    {
        GL status = checkStatus();
        if (status != GL.FRAMEBUFFER_COMPLETE) throw new RuntimeException(this + " not complete:\n! " + status);
        
        return this;
    }
    
    /**
     * @return The status of the Framebuffer
     */
    public GL checkStatus()
    {
        return GL.checkFramebufferStatus(GL.FRAMEBUFFER);
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
}

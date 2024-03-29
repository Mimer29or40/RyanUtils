package rutils;

import rutils.gl.GL;
import rutils.gl.GLShader;
import rutils.gl.GLVertexArray;
import rutils.glfw.GLFWApplicationTest;
import rutils.glfw.Window;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.opengl.GL11.*;

public class LearnOpenGL102 extends GLFWApplicationTest
{
    GLShader shader;
    
    GLVertexArray vao;
    
    @Override
    protected Window createWindow()
    {
        return new Window.Builder()
                .name("First")
                .contextVersionMajor(3)
                .contextVersionMinor(3)
                .openglProfile(GLFW_OPENGL_CORE_PROFILE)
                .openglForwardCompat(true)
                .size(800, 600)
                .build();
    }
    
    @Override
    protected void init()
    {
        glClearColor(0.1F, 0.1F, 0.1F, 1F);
        
        glEnable(GL_DEPTH_TEST);
        
        shader = new GLShader().bind()
                               .load(GL.VERTEX_SHADER, """
                                                       #version 330 core
                                                       layout (location = 0) in vec3 aPos;
                                                       void main()
                                                       {
                                                           gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);
                                                       }
                                                       """)
                               .load(GL.FRAGMENT_SHADER, """
                                                         #version 330 core
                                                         out vec4 FragColor;
                                                         void main()
                                                         {
                                                             FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);
                                                         }
                                                         """)
                               .validate()
                               .unbind();
        
        FloatBuffer buffer = FloatBuffer.wrap(new float[] {
                -0.5f, -0.5f, 0.0f, // left
                0.5f, -0.5f, 0.0f, // right
                0.0f, 0.5f, 0.0f  // top
        });
        
        vao = new GLVertexArray().bind().buffer(buffer, GL.STATIC_DRAW, GL.FLOAT, 3, false).unbind();
    }
    
    @Override
    protected void draw(double time, double deltaT)
    {
        glViewport(0, 0, window.framebufferWidth(), window.framebufferHeight());
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        shader.bind();
        
        vao.bind().draw(GL.TRIANGLES).unbind();
        
        shader.unbind();
        
        window.swap();
    }
    
    @Override
    protected void beforeEventLoop()
    {
        System.out.println(GLFW_OPENGL_CORE_PROFILE + " " + window.getAttribute(GLFW_OPENGL_PROFILE));
        
    }
    
    public static void main(String[] args)
    {
        new LearnOpenGL102().run();
    }
}

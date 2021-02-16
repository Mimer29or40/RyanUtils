package rutils;

import rutils.gl.GL;
import rutils.gl.GLShader;
import rutils.gl.GLVertexArray;
import rutils.glfw.GLFWApplicationTest;
import rutils.glfw.Window;

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
                               .load(GL.VERTEX_SHADER, "#version 330 core\n" +
                                                       "layout (location = 0) in vec3 aPos;\n" +
                                                       "void main()\n" +
                                                       "{\n" +
                                                       "    gl_Position = vec4(aPos.x, aPos.y, aPos.z, 1.0);\n" +
                                                       "}\n")
                               .load(GL.FRAGMENT_SHADER, "#version 330 core\n" +
                                                         "out vec4 FragColor;\n" +
                                                         "void main()\n" +
                                                         "{\n" +
                                                         "    FragColor = vec4(1.0f, 0.5f, 0.2f, 1.0f);\n" +
                                                         "}\n")
                               .validate()
                               .unbind();
        
        vao = new GLVertexArray().bind().add(new float[] {
                -0.5f, -0.5f, 0.0f, // left
                0.5f, -0.5f, 0.0f, // right
                0.0f, 0.5f, 0.0f  // top
        }, GL.STATIC_DRAW, 3).unbind();
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

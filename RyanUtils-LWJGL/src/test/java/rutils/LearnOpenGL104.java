package rutils;

import rutils.gl.GL;
import rutils.gl.GLShader;
import rutils.gl.GLTexture;
import rutils.gl.GLVertexArray;
import rutils.glfw.GLFW;
import rutils.glfw.Window;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.opengl.GL11.*;

public class LearnOpenGL104
{
    static Window window;
    
    static GLShader shader;
    
    static GLVertexArray vao;
    
    static GLTexture texture1;
    static GLTexture texture2;
    
    static void init()
    {
        glClearColor(0.1F, 0.1F, 0.1F, 1F);
        
        glEnable(GL_DEPTH_TEST);
        
        shader = new GLShader().bind()
                               .load(GL.VERTEX_SHADER, """
                                                       #version 330 core
                                                       layout (location = 0) in vec3 aPos;
                                                       layout (location = 1) in vec3 aColor;
                                                       layout (location = 2) in vec2 aTexCoord;

                                                       out vec3 ourColor;
                                                       out vec2 TexCoord;

                                                       void main()
                                                       {
                                                       	gl_Position = vec4(aPos, 1.0);
                                                       	ourColor = aColor;
                                                       	TexCoord = vec2(aTexCoord.x, aTexCoord.y);
                                                       }""")
                               .load(GL.FRAGMENT_SHADER, """
                                                         #version 330 core
                                                         out vec4 FragColor;

                                                         in vec3 ourColor;
                                                         in vec2 TexCoord;

                                                         // texture samplers
                                                         uniform sampler2D texture1;
                                                         uniform sampler2D texture2;

                                                         void main()
                                                         {
                                                             // linearly interpolate between both textures (80% container, 20% awesomeface)
                                                             FragColor = mix(texture(texture1, TexCoord), texture(texture2, TexCoord), 0.2);
                                                         }
                                                         """)
                               .validate()
                               .unbind();
        
        FloatBuffer buffer = FloatBuffer.wrap(new float[] {
                // positions         // colors         // texture coords (note that we changed them to 2.0f!)
                +0.5f, +0.5f, +0.0f, 1.0f, 0.0f, 0.0f, 2.0f, 2.0f, // top right
                +0.5f, -0.5f, +0.0f, 0.0f, 1.0f, 0.0f, 2.0f, 0.0f, // bottom right
                -0.5f, -0.5f, +0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, // bottom left
                -0.5f, +0.5f, +0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 2.0f  // top left
        });
        IntBuffer indices = IntBuffer.wrap(new int[] {
                0, 1, 3, // first triangle
                1, 2, 3  // second triangle
        });
        
        vao = new GLVertexArray().bind()
                                 .buffer(buffer, GL.STATIC_DRAW, GL.FLOAT, 3, false, GL.FLOAT, 3, false, GL.FLOAT, 2, false)
                                 .indexBuffer(indices, GL.STATIC_DRAW).unbind();
        
        texture1 = GLTexture.loadImage("textures/awesomeface.png");
        texture2 = GLTexture.loadImage("textures/container.jpg");
        
        shader.setUniform("texture1", 0);
        shader.setUniform("texture2", 1);
    }
    
    static void draw(double time, double deltaT)
    {
        glViewport(0, 0, window.framebufferWidth(), window.framebufferHeight());
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        texture1.bind(0);
        texture2.bind(1);
        
        shader.bind();
        
        vao.bind().draw(GL.TRIANGLES).unbind();
        
        shader.unbind();
        
        window.swap();
    }
    
    public static void main(String[] args)
    {
        try
        {
            GLFW.init();
            
            GLFW.EVENT_BUS.register(LearnOpenGL104.class);
            
            window = new Window.Builder()
                    .name("First")
                    .contextVersionMajor(3)
                    .contextVersionMinor(3)
                    .openglProfile(GLFW_OPENGL_CORE_PROFILE)
                    .openglForwardCompat(true)
                    .size(800, 600)
                    .build();
            window.onWindowInit(LearnOpenGL104::init);
            window.onWindowDraw(LearnOpenGL104::draw);
            window.open();
            
            GLFW.eventLoop();
        }
        finally
        {
            GLFW.EVENT_BUS.unregister(LearnOpenGL104.class);
            
            GLFW.destroy();
        }
    }
}

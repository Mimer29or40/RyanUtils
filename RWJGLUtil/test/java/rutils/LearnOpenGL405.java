package rutils;

import org.joml.Matrix4d;
import rutils.gl.*;
import rutils.glfw.GLFW;
import rutils.glfw.Window;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.opengl.GL11.*;

public class LearnOpenGL405
{
    private static final Logger LOGGER = new Logger();
    
    static Window window;
    
    static GLShader shader;
    static GLShader screenShader;
    
    static GLVertexArray cubeVAO;
    static GLVertexArray planeVAO;
    static GLVertexArray quadVAO;
    
    static GLTexture cubeTexture;
    static GLTexture floorTexture;
    
    static GLFrameBuffer frameBuffer;
    
    static final Matrix4d model = new Matrix4d();
    static final Matrix4d view  = new Matrix4d();
    static final Matrix4d proj  = new Matrix4d();
    
    static void init()
    {
        glClearColor(0.1F, 0.1F, 0.1F, 1F);
        
        glEnable(GL_DEPTH_TEST);
        
        shader = new GLShader().load(GL.VERTEX_SHADER, "#version 330 core\n" +
                                                       "layout (location = 0) in vec3 aPos;\n" +
                                                       "layout (location = 1) in vec2 aTexCoords;\n" +
                                                       "\n" +
                                                       "out vec2 TexCoords;\n" +
                                                       "\n" +
                                                       "uniform mat4 model;\n" +
                                                       "uniform mat4 view;\n" +
                                                       "uniform mat4 projection;\n" +
                                                       "\n" +
                                                       "void main()\n" +
                                                       "{\n" +
                                                       "    TexCoords = aTexCoords;    \n" +
                                                       "    gl_Position = projection * view * model * vec4(aPos, 1.0);\n" +
                                                       "}\n")
                               .load(GL.FRAGMENT_SHADER, "#version 330 core\n" +
                                                         "out vec4 FragColor;\n" +
                                                         "\n" +
                                                         "in vec2 TexCoords;\n" +
                                                         "\n" +
                                                         "uniform sampler2D texture1;\n" +
                                                         "\n" +
                                                         "void main()\n" +
                                                         "{\n" +
                                                         "    FragColor = texture(texture1, TexCoords);\n" +
                                                         "}\n")
                               .validate();
        
        screenShader = new GLShader().load(GL.VERTEX_SHADER, "#version 330 core\n" +
                                                             "layout (location = 0) in vec2 aPos;\n" +
                                                             "layout (location = 1) in vec2 aTexCoords;\n" +
                                                             "\n" +
                                                             "out vec2 TexCoords;\n" +
                                                             "\n" +
                                                             "void main()\n" +
                                                             "{\n" +
                                                             "    TexCoords = aTexCoords;\n" +
                                                             "    gl_Position = vec4(aPos.x, aPos.y, 0.0, 1.0);\n" +
                                                             "}\n")
                                     .load(GL.FRAGMENT_SHADER, "#version 330 core\n" +
                                                               "out vec4 FragColor;\n" +
                                                               "\n" +
                                                               "in vec2 TexCoords;\n" +
                                                               "\n" +
                                                               "uniform sampler2D screenTexture;\n" +
                                                               "\n" +
                                                               "void main()\n" +
                                                               "{\n" +
                                                               "    vec3 col = texture(screenTexture, TexCoords).rgb;\n" +
                                                               "    FragColor = vec4(col, 1.0);\n" +
                                                               "}\n")
                                     .validate();
        
        FloatBuffer buffer = FloatBuffer.wrap(new float[] {
                // positions         // texture Coords
                -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,
                +0.5f, -0.5f, -0.5f, 1.0f, 0.0f,
                +0.5f, +0.5f, -0.5f, 1.0f, 1.0f,
                +0.5f, +0.5f, -0.5f, 1.0f, 1.0f,
                -0.5f, +0.5f, -0.5f, 0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 0.0f,
                
                -0.5f, -0.5f, +0.5f, 0.0f, 0.0f,
                +0.5f, -0.5f, +0.5f, 1.0f, 0.0f,
                +0.5f, +0.5f, +0.5f, 1.0f, 1.0f,
                +0.5f, +0.5f, +0.5f, 1.0f, 1.0f,
                -0.5f, +0.5f, +0.5f, 0.0f, 1.0f,
                -0.5f, -0.5f, +0.5f, 0.0f, 0.0f,
                
                -0.5f, +0.5f, +0.5f, 1.0f, 0.0f,
                -0.5f, +0.5f, -0.5f, 1.0f, 1.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
                -0.5f, -0.5f, +0.5f, 0.0f, 0.0f,
                -0.5f, +0.5f, +0.5f, 1.0f, 0.0f,
                
                +0.5f, +0.5f, +0.5f, 1.0f, 0.0f,
                +0.5f, +0.5f, -0.5f, 1.0f, 1.0f,
                +0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
                +0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
                +0.5f, -0.5f, +0.5f, 0.0f, 0.0f,
                +0.5f, +0.5f, +0.5f, 1.0f, 0.0f,
                
                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
                +0.5f, -0.5f, -0.5f, 1.0f, 1.0f,
                +0.5f, -0.5f, +0.5f, 1.0f, 0.0f,
                +0.5f, -0.5f, +0.5f, 1.0f, 0.0f,
                -0.5f, -0.5f, +0.5f, 0.0f, 0.0f,
                -0.5f, -0.5f, -0.5f, 0.0f, 1.0f,
                
                -0.5f, +0.5f, -0.5f, 0.0f, 1.0f,
                +0.5f, +0.5f, -0.5f, 1.0f, 1.0f,
                +0.5f, +0.5f, +0.5f, 1.0f, 0.0f,
                +0.5f, +0.5f, +0.5f, 1.0f, 0.0f,
                -0.5f, +0.5f, +0.5f, 0.0f, 0.0f,
                -0.5f, +0.5f, -0.5f, 0.0f, 1.0f
        });
        cubeVAO = new GLVertexArray().bind().buffer(buffer, GL.STATIC_DRAW, GL.FLOAT, 3, false, GL.FLOAT, 2, false).unbind();
        
        buffer   = FloatBuffer.wrap(new float[] {
                // positions         // texture Coords
                +5.0f, -0.5f, +5.0f, 2.0f, 0.0f,
                -5.0f, -0.5f, +5.0f, 0.0f, 0.0f,
                -5.0f, -0.5f, -5.0f, 0.0f, 2.0f,
                
                +5.0f, -0.5f, +5.0f, 2.0f, 0.0f,
                -5.0f, -0.5f, -5.0f, 0.0f, 2.0f,
                +5.0f, -0.5f, -5.0f, 2.0f, 2.0f
        });
        planeVAO = new GLVertexArray().bind().buffer(buffer, GL.STATIC_DRAW, GL.FLOAT, 3, false, GL.FLOAT, 2, false).unbind();
        
        // vertex attributes for a quad that fills the entire screen in Normalized Device Coordinates.
        buffer  = FloatBuffer.wrap(new float[] {
                // positions   // texCoords
                -1.0f, +1.0f, 0.0f, 1.0f,
                -1.0f, -1.0f, 0.0f, 0.0f,
                +1.0f, -1.0f, 1.0f, 0.0f,
                
                -1.0f, 1.0f, 0.0f, 1.0f,
                +1.0f, -1.0f, 1.0f, 0.0f,
                +1.0f, +1.0f, 1.0f, 1.0f
        });
        quadVAO = new GLVertexArray().bind().buffer(buffer, GL.STATIC_DRAW, GL.FLOAT, 2, false, GL.FLOAT, 2, false).unbind();
        
        cubeTexture  = GLTexture.loadImage("textures/container.jpg");
        floorTexture = GLTexture.loadImage("textures/metal.png");
        
        shader.bind();
        shader.setUniform("texture1", 0);
        
        screenShader.bind();
        screenShader.setUniform("screenTexture", 0);
        
        frameBuffer = new GLFrameBuffer(200, 150).bind().attach().validate().unbind();
        
        // draw as wireframe
        // glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
    }
    
    static void draw(double time, double deltaT)
    {
        // render
        // ------
        // bind to framebuffer and draw scene as we normally would to color texture
        frameBuffer.bind();
        glViewport(0, 0, frameBuffer.width(), frameBuffer.height());
        glEnable(GL_DEPTH_TEST); // enable depth testing (is disabled for rendering screen-space quad)
        
        // make sure we clear the framebuffer's content
        glClearColor(0.1f, 0.1f, 0.1f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        shader.bind();
        model.identity();
        // glm::mat4 view = camera.GetViewMatrix();
        // glm::mat4 proj = glm::perspective (glm::radians (camera.Zoom), window.aspectRatio(), 0.1, 100.0)
        view.identity().lookAt(5 * Math.cos(time), 1, 5 * Math.sin(time), 0, 0, 0, 0, 1, 0);
        proj.identity().perspective(Math.toRadians(90), window.aspectRatio(), 0.1, 100.0);
        shader.setUniform("view", view);
        shader.setUniform("projection", proj);
        
        // cubes
        cubeVAO.bind();
        cubeTexture.bind(0);
        
        model.identity().translate(-1.0, 0.0, -1.0);
        shader.setUniform("model", model);
        cubeVAO.draw(GL.TRIANGLES);
        
        model.identity().translate(2.0, 0.0, 0.0);
        shader.setUniform("model", model);
        cubeVAO.draw(GL.TRIANGLES);
        
        // floor
        planeVAO.bind();
        floorTexture.bind(0);
        model.identity();
        shader.setUniform("model", model);
        planeVAO.draw(GL.TRIANGLES);
        planeVAO.unbind();
        
        // now bind back to default framebuffer and draw a quad plane with the attached framebuffer color texture
        // glBindFramebuffer(GL_FRAMEBUFFER, 0);
        frameBuffer.unbind();
        glViewport(0, 0, window.framebufferWidth(), window.framebufferHeight());
        glDisable(GL_DEPTH_TEST); // disable depth test so screen-space quad isn't discarded due to depth test.
        // clear all relevant buffers
        glClearColor(1.0f, 1.0f, 1.0f, 1.0f); // set clear color to white (not really necessary actually, since we won't be able to see behind the quad anyways)
        glClear(GL_COLOR_BUFFER_BIT);
        
        screenShader.bind();
        quadVAO.bind();
        frameBuffer.color().bind();    // use the color attachment texture as the texture of the quad plane
        // frameBuffer.depthStencil().bind();
        quadVAO.draw(GL.TRIANGLES);
        
        
        // glfw: swap buffers and poll IO events (keys pressed/released, mouse moved etc.)
        // -------------------------------------------------------------------------------
        window.swap();
    }
    
    public static void main(String[] args)
    {
        try
        {
            GLFW.init();
            
            GLFW.EVENT_BUS.register(LearnOpenGL405.class);
            
            window = new Window.Builder()
                    .name("First")
                    .contextVersionMajor(3)
                    .contextVersionMinor(3)
                    .openglProfile(GLFW_OPENGL_CORE_PROFILE)
                    .openglForwardCompat(true)
                    .size(800, 600)
                    .build();
            window.onWindowInit(LearnOpenGL405::init);
            window.onWindowDraw(LearnOpenGL405::draw);
            window.open();
            
            GLFW.eventLoop();
        }
        finally
        {
            GLFW.EVENT_BUS.unregister(LearnOpenGL405.class);
            
            GLFW.destroy();
        }
    }
}

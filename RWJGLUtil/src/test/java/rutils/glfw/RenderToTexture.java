package rutils.glfw;

import org.joml.Matrix3d;
import org.joml.Matrix4d;
import org.lwjgl.BufferUtils;
import rutils.gl.GL;
import rutils.gl.GLShader;
import rutils.gl.GLTexture;
import rutils.gl.GLVertexArray;

import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

public class RenderToTexture extends GLFWApplicationTest
{
    static final String vertexShader =
            "#version 120\n" +
            "\n" +
            "attribute vec3 vertexPosition;\n" +
            "attribute vec3 vertexNormal;\n" +
            "attribute vec3 vertexColor;\n" +
            "attribute vec2 texCoord2d;\n" +
            "\n" +
            "uniform mat4 modelViewMatrix;\n" +
            "uniform mat3 normalMatrix;\n" +
            "uniform mat4 projectionMatrix;\n" +
            "\n" +
            "struct LightSource\n" +
            "{\n" +
            "    vec3 ambient;\n" +
            "    vec3 diffuse;\n" +
            "    vec3 specular;\n" +
            "    vec3 position;\n" +
            "};\n" +
            "uniform LightSource lightSource;\n" +
            "\n" +
            "struct LightModel\n" +
            "{\n" +
            "    vec3 ambient;\n" +
            "};\n" +
            "uniform LightModel lightModel;\n" +
            "\n" +
            "struct Material {\n" +
            "    vec3  emission;\n" +
            "    vec3  specular;\n" +
            "    highp float shininess;\n" +
            "};\n" +
            "uniform Material material;\n" +
            "\n" +
            "varying vec3 v_color;\n" +
            "varying vec2 v_texCoord2d;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    vec3 normal     = normalize(normalMatrix * vertexNormal);                     // normal vector              \n" +
            "    vec3 position   = vec3(modelViewMatrix * vec4(vertexPosition, 1));            // vertex pos in eye coords   \n" +
            "    vec3 halfVector = normalize(lightSource.position + vec3(0,0,1));              // light half vector          \n" +
            "    float nDotVP    = dot(normal, normalize(lightSource.position));               // normal . light direction   \n" +
            "    float nDotHV    = max(0., dot(normal,  halfVector));                          // normal . light half vector \n" +
            "    float pf        = mix(0., pow(nDotHV, material.shininess), step(0., nDotVP)); // power factor               \n" +
            "\n" +
            "    vec3 ambient    = lightSource.ambient;\n" +
            "    vec3 diffuse    = lightSource.diffuse * nDotVP;\n" +
            "    vec3 specular   = lightSource.specular * pf;\n" +
            "    vec3 sceneColor = material.emission + vertexColor * lightModel.ambient;\n" +
            "\n" +
            "    v_color = clamp(sceneColor +                           \n" +
            "                    ambient  * vertexColor +               \n" +
            "                    diffuse  * vertexColor +               \n" +
            "                    specular * material.specular, 0., 1. );\n" +
            "\n" +
            "    v_texCoord2d = texCoord2d;\n" +
            "\n" +
            "    gl_Position = projectionMatrix * modelViewMatrix * vec4(vertexPosition, 1);\n" +
            "}\n";
    
    static final String fragmentShader =
            "#version 120\n" +
            "\n" +
            "uniform sampler2D texUnit;\n" +
            "\n" +
            "varying vec3 v_color;\n" +
            "varying vec2 v_texCoord2d;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "    gl_FragColor = vec4(v_color, 1) * texture2D(texUnit, v_texCoord2d);\n" +
            "}\n";
    
    @SuppressWarnings("SpellCheckingInspection")
    static final String[] circles = new String[] {
            "................",
            "................",
            "......xxxx......",
            "....xxxxxxxx....",
            "...xxxxxxxxxx...",
            "...xxx....xxx...",
            "..xxx......xxx..",
            "..xxx......xxx..",
            "..xxx......xxx..",
            "..xxx......xxx..",
            "...xxx....xxx...",
            "...xxxxxxxxxx...",
            "....xxxxxxxx....",
            "......xxxx......",
            "................",
            "................"
    };
    
    GLShader      shader;
    GLVertexArray vertexArray;
    
    GLTexture texture;
    
    final Matrix4d view = new Matrix4d();
    final Matrix4d proj = new Matrix4d();
    
    @Override
    protected Window createWindow()
    {
        return new Window.Builder()
                .openglDebugContext(true)
                .resizable(true)
                .build();
    }
    
    @Override
    protected void init()
    {
        this.shader = new GLShader().load(GL.VERTEX_SHADER, vertexShader).load(GL.FRAGMENT_SHADER, fragmentShader).validate().bind();
        this.shader.setUniform("lightSource.ambient", 0.0f, 0.0f, 0.0f);
        this.shader.setUniform("lightSource.diffuse", 1.0f, 1.0f, 1.0f);
        this.shader.setUniform("lightSource.specular", 1.0f, 1.0f, 1.0f);
        this.shader.setUniform("lightSource.position", 0.0f, 1.0f, 1.0f);
        this.shader.setUniform("lightModel.ambient", 0.2f, 0.2f, 0.2f);
        this.shader.setUniform("material.emission", 0.0f, 0.0f, 0.0f);
        this.shader.setUniform("material.specular", 1.0f, 1.0f, 1.0f);
        this.shader.setUniform("material.shininess", 10.0f);
        
        this.vertexArray = new GLVertexArray().add(new float[] {
                1.0f, 1.0f, -1.0f,  // Green
                -1.0f, 1.0f, -1.0f, // Green
                -1.0f, 1.0f, 1.0f,  // Green
                1.0f, 1.0f, 1.0f,   // Green
                1.0f, -1.0f, 1.0f,  // Orange
                -1.0f, -1.0f, 1.0f, // Orange
                -1.0f, -1.0f, -1.0f,// Orange
                1.0f, -1.0f, -1.0f, // Orange
                1.0f, 1.0f, 1.0f,   // Red
                -1.0f, 1.0f, 1.0f,  // Red
                -1.0f, -1.0f, 1.0f, // Red
                1.0f, -1.0f, 1.0f,  // Red
                1.0f, -1.0f, -1.0f, // Yellow
                -1.0f, -1.0f, -1.0f,// Yellow
                -1.0f, 1.0f, -1.0f, // Yellow
                1.0f, 1.0f, -1.0f,  // Yellow
                -1.0f, 1.0f, 1.0f,  // Blue
                -1.0f, 1.0f, -1.0f, // Blue
                -1.0f, -1.0f, -1.0f,// Blue
                -1.0f, -1.0f, 1.0f, // Blue
                1.0f, 1.0f, -1.0f,  // Magenta
                1.0f, 1.0f, 1.0f,   // Magenta
                1.0f, -1.0f, 1.0f,  // Magenta
                1.0f, -1.0f, -1.0f  // Magenta
        }, GL.STATIC_DRAW, 3).add(new float[] {
                0.0f, 1.0f, 0.0f,  // Green
                0.0f, 1.0f, 0.0f,  // Green
                0.0f, 1.0f, 0.0f,  // Green
                0.0f, 1.0f, 0.0f,  // Green
                0.0f, -1.0f, 0.0f, // Orange
                0.0f, -1.0f, 0.0f, // Orange
                0.0f, -1.0f, 0.0f, // Orange
                0.0f, -1.0f, 0.0f, // Orange
                0.0f, 0.0f, 1.0f,  // Red
                0.0f, 0.0f, 1.0f,  // Red
                0.0f, 0.0f, 1.0f,  // Red
                0.0f, 0.0f, 1.0f,  // Red
                0.0f, 0.0f, -1.0f, // Yellow
                0.0f, 0.0f, -1.0f, // Yellow
                0.0f, 0.0f, -1.0f, // Yellow
                0.0f, 0.0f, -1.0f, // Yellow
                -1.0f, 0.0f, 0.0f, // Blue
                -1.0f, 0.0f, 0.0f, // Blue
                -1.0f, 0.0f, 0.0f, // Blue
                -1.0f, 0.0f, 0.0f, // Blue
                1.0f, 0.0f, 0.0f,  // Magenta
                1.0f, 0.0f, 0.0f,  // Magenta
                1.0f, 0.0f, 0.0f,  // Magenta
                1.0f, 0.0f, 0.0f,  // Magenta
        }, GL.STATIC_DRAW, 3).add(new float[] {
                0.0f, 1.0f, 0.0f, // Green
                0.0f, 1.0f, 0.0f, // Green
                0.0f, 1.0f, 0.0f, // Green
                0.0f, 1.0f, 0.0f, // Green
                1.0f, 0.5f, 0.0f, // Orange
                1.0f, 0.5f, 0.0f, // Orange
                1.0f, 0.5f, 0.0f, // Orange
                1.0f, 0.5f, 0.0f, // Orange
                1.0f, 0.0f, 0.0f, // Red
                1.0f, 0.0f, 0.0f, // Red
                1.0f, 0.0f, 0.0f, // Red
                1.0f, 0.0f, 0.0f, // Red
                1.0f, 1.0f, 0.0f, // Yellow
                1.0f, 1.0f, 0.0f, // Yellow
                1.0f, 1.0f, 0.0f, // Yellow
                1.0f, 1.0f, 0.0f, // Yellow
                0.0f, 0.0f, 1.0f, // Blue
                0.0f, 0.0f, 1.0f, // Blue
                0.0f, 0.0f, 1.0f, // Blue
                0.0f, 0.0f, 1.0f, // Blue
                1.0f, 0.0f, 1.0f, // Magenta
                1.0f, 0.0f, 1.0f, // Magenta
                1.0f, 0.0f, 1.0f, // Magenta
                1.0f, 0.0f, 1.0f, // Magenta
        }, GL.STATIC_DRAW, 3).add(new float[] {
                0.0f, 0.0f, // Green
                0.0f, 1.0f, // Green
                1.0f, 1.0f, // Green
                1.0f, 0.0f, // Green
                0.0f, 0.0f, // Orange
                0.0f, 1.0f, // Orange
                1.0f, 1.0f, // Orange
                1.0f, 0.0f, // Orange
                0.0f, 0.0f, // Red
                0.0f, 1.0f, // Red
                1.0f, 1.0f, // Red
                1.0f, 0.0f, // Red
                0.0f, 0.0f, // Yellow
                0.0f, 1.0f, // Yellow
                1.0f, 1.0f, // Yellow
                1.0f, 0.0f, // Yellow
                0.0f, 0.0f, // Blue
                0.0f, 1.0f, // Blue
                1.0f, 1.0f, // Blue
                1.0f, 0.0f, // Blue
                0.0f, 0.0f, // Magenta
                0.0f, 1.0f, // Magenta
                1.0f, 1.0f, // Magenta
                1.0f, 0.0f, // Magenta
        }, GL.STATIC_DRAW, 3);
        
        int[] indices = new int[36];
        for (int i = 0, v = 0, n = 6 * 4; v < n; v += 4)
        {
            indices[i++] = v;
            indices[i++] = v + 1;
            indices[i++] = v + 2;
            
            // second triangle (ccw winding)
            indices[i++] = v;
            indices[i++] = v + 2;
            indices[i++] = v + 3;
        }
        this.vertexArray.addEBO(indices, GL.STATIC_DRAW);
        
        this.view.identity();
        
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_TEXTURE_2D);
        
        glActiveTexture(GL_TEXTURE0);
        this.shader.setUniform("texUnit", 0);
        
        this.texture = new GLTexture(16, 16, GL.RGB);
        ByteBuffer data = BufferUtils.createByteBuffer(this.texture.width() * this.texture.height() * this.texture.channels());
        for (int j = 0; j < this.texture.width(); j++)
        {
            for (int i = 0; i < this.texture.height(); i++)
            {
                if (circles[j].charAt(i) == 'x')
                {
                    data.put((byte) 0x00);
                    data.put((byte) 0xFF);
                    data.put((byte) 0x00);
                }
                else
                {
                    data.put((byte) 0x77);
                    data.put((byte) 0x77);
                    data.put((byte) 0x77);
                }
            }
        }
        this.texture.bind().set(data.clear()).applyTextureSettings();
        
        glClearColor(.5f, .5f, .5f, 1.f);
    }
    
    @Override
    protected void draw(double time, double deltaT)
    {
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glViewport(0, 0, window.framebufferWidth(), window.framebufferHeight());
    
        proj.identity().perspective(Math.toRadians(90), window.aspectRatio(), 0.1, 100.0);
        view.identity().lookAt(5 * Math.cos(time), 1, 5 * Math.sin(time), 0, 0, 0, 0, 1, 0);
    
        shader.bind();
        shader.setUniform("projectionMatrix", proj);
        shader.setUniform("modelViewMatrix", view);
        shader.setUniform("normalMatrix", view.normal(new Matrix3d()));
        
        texture.bind(0);
    
        vertexArray.bind().draw(GL.TRIANGLES);
        
        window.swap();
    }
    
    @Override
    protected void beforeEventLoop()
    {
    
    }
    
    public static void main(String[] args)
    {
        new RenderToTexture().run();
    }
}

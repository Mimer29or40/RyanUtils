package rutils;

import org.joml.Matrix4d;
import org.joml.Vector2d;
import org.joml.Vector2dc;
import org.joml.Vector4d;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;
import rutils.gl.GL;
import rutils.gl.GLShader;
import rutils.gl.GLTexture;
import rutils.gl.GLVertexArray;
import rutils.glfw.*;
import rutils.glfw.event.EventKeyboardKeyPressed;
import rutils.glfw.event.EventMouseButtonDragged;
import rutils.glfw.event.EventMouseScrolled;
import rutils.noise.Noise;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.logging.Level;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL14.GL_FUNC_ADD;
import static org.lwjgl.opengl.GL14.glBlendEquation;
import static org.lwjgl.stb.STBImageWrite.stbi_write_png;

public class NoiseTest
{
    private static final Logger LOGGER = new Logger();
    
    public FastNoise fastNoise = new FastNoise(1337);
    public Noise     noise     = new Noise(1337);
    
    public int width  = 512;
    public int height = 512;
    
    public ByteBuffer data;
    
    protected Window window;
    
    // -------------------- Rending -------------------- //
    protected GLShader      shader;
    protected GLVertexArray vao;
    protected GLTexture     texture;
    
    // -------------------- Zooming and Panning -------------------- //
    protected       double   zoom = 0.5;
    protected final Vector2d pos  = new Vector2d(0.5, 0.5);
    protected final Matrix4d view = new Matrix4d();
    
    protected final Matrix4d viewInv = new Matrix4d();
    
    private static final Vector2d TEMP_V2 = new Vector2d();
    private static final Vector4d TEMP_V4 = new Vector4d();
    
    protected Window createWindow()
    {
        return new Window.Builder()
                .name("Noise")
                .size(this.width, this.height)
                .resizable(false)
                .build();
    }
    
    protected void init()
    {
        glClearColor(0.1F, 0.1F, 0.1F, 1F);
        
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glDisable(GL_DEPTH_TEST);
        
        glEnable(GL_CULL_FACE);
        glCullFace(GL_BACK);
        
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glBlendEquation(GL_FUNC_ADD);
        
        this.shader = new GLShader().bind()
                                    .load(GL.VERTEX_SHADER, "#version 460\n" +
                                                            "layout (location = 0) in vec2 aPos;\n" +
                                                            "uniform mat4 view;\n" +
                                                            "out vec2 texCord;\n" +
                                                            "void main()\n" +
                                                            "{\n" +
                                                            "    texCord = aPos;\n" +
                                                            "    gl_Position = view * vec4(aPos.x, aPos.y, 0.0, 1.0);\n" +
                                                            "}\n")
                                    .load(GL.FRAGMENT_SHADER, "#version 460\n" +
                                                              "uniform sampler2D tex;\n" +
                                                              "in vec2 texCord;\n" +
                                                              "out vec4 FragColor;\n" +
                                                              "void main()\n" +
                                                              "{\n" +
                                                              "    float color = texture(tex, texCord).r;\n" +
                                                              "    FragColor = texture(tex, texCord);\n" +
                                                              "    //FragColor = vec4(color, color, color, 1.0);\n" +
                                                              "    //FragColor = vec4(1.0, 0.5, 0.2, 1.0);\n" +
                                                              "}\n")
                                    .validate()
                                    .unbind();
        
        try (MemoryStack stack = MemoryStack.stackPush())
        {
            FloatBuffer buffer = stack.floats(+0F, +0F, // Bottom Left
                                              +0F, +1F, // Top Left
                                              +1F, +1F, // Top Right
                                              +1F, +0F  // Bottom Right
                                             );
            
            this.vao = new GLVertexArray().bind().buffer(buffer, GL.STATIC_DRAW, GL.FLOAT, 2, false).unbind();
        }
        
        this.fastNoise.setNoiseType(FastNoise.NoiseType.Value);
        this.noise.type(Noise.Type.VALUE);
        
        this.texture = new GLTexture(this.width, this.height, GL.RGB).bind().applyTextureSettings().unbind();
        
        this.data = BufferUtils.createByteBuffer(this.width * this.height * this.texture.channels());
    }
    
    protected void draw(double time, double deltaT)
    {
        glViewport(0, 0, this.window.framebufferWidth(), this.window.framebufferHeight());
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        
        this.shader.bind();
        this.shader.setUniform("view", this.view);
        this.shader.setUniform("tex", 0);
        
        this.texture.bind(0);
        for (int i = 0, n = this.width * this.height, index = 0; i < n; i++)
        {
            int x = i % this.width;
            int y = i / this.width;
            
            int value = noiseToInt(this.noise.get(x, y, time * 100.0));
            this.data.put(index++, (byte) value);
            this.data.put(index++, (byte) value);
            this.data.put(index++, (byte) value);
        }
        this.texture.set(this.data);
        
        this.vao.bind().draw(GL.QUADS).unbind();
        
        this.texture.unbind();
        
        this.shader.unbind();
        
        this.window.swap();
    }
    
    @SubscribeEvent
    public void onDrag(EventMouseButtonDragged event)
    {
        if (event.button() == Mouse.Button.LEFT)
        {
            Vector2dc rel = event.rel();
            
            Vector2dc beforePos = screenToWorldCoord(GLFW.mouse().x() - rel.x(), GLFW.mouse().y() - rel.y());
            double    beforeX   = beforePos.x();
            double    beforeY   = beforePos.y();
            Vector2dc afterPos  = screenToWorldCoord(GLFW.mouse().x(), GLFW.mouse().y());
            double    afterX    = afterPos.x();
            double    afterY    = afterPos.y();
            
            this.pos.add(beforeX - afterX, beforeY - afterY);
            
            calculateView();
        }
    }
    
    @SubscribeEvent
    public void onScroll(EventMouseScrolled event)
    {
        Vector2dc dir = event.scroll();
        if (dir.lengthSquared() != 0)
        {
            double val = 1.1;
            
            Vector2dc beforePos = screenToWorldCoord(GLFW.mouse().x(), GLFW.mouse().y());
            double    beforeX   = beforePos.x();
            double    beforeY   = beforePos.y();
            this.zoom *= dir.y() >= 0 ? 1.0 / (dir.y() * val) : -dir.y() * val;
            calculateView();
            
            Vector2dc afterPos = screenToWorldCoord(GLFW.mouse().x(), GLFW.mouse().y());
            double    afterX   = afterPos.x();
            double    afterY   = afterPos.y();
            this.pos.add(beforeX - afterX, beforeY - afterY);
            calculateView();
        }
    }
    
    @SubscribeEvent
    public void onKeyPress(EventKeyboardKeyPressed event)
    {
        if (event.key() == Keyboard.Key.SPACE)
        {
            this.zoom = 0.5;
            this.pos.set(0.5, 0.5);
            calculateView();
        }
    }
    
    public void calculateView()
    {
        double zoom = this.zoom;
        
        double aspect = this.window.aspectRatio() * zoom;
        
        double zNear = -1.0;
        double zFar  = 1.0;
        
        double x = this.pos.x();
        double y = this.pos.y();
        
        this.view.setOrtho(-aspect, aspect, zoom, -zoom, zNear, zFar).translate(-x, -y, 0);
        this.viewInv.set(this.view).invert();
    }
    
    public Vector2dc screenToWorldCoord(double screenX, double screenY)
    {
        double viewX = screenX * 2.0 / this.window.width() + -1.0;
        double viewY = screenY * -2.0 / this.window.height() + 1.0;
        
        NoiseTest.TEMP_V4.set(viewX, viewY, 0, 1);
        
        this.viewInv.transform(NoiseTest.TEMP_V4);
        
        double worldX = NoiseTest.TEMP_V4.x;
        double worldY = NoiseTest.TEMP_V4.y;
        
        return NoiseTest.TEMP_V2.set(worldX, worldY);
    }
    
    protected void beforeEventLoop()
    {
        calculateView();
    }
    
    public void run()
    {
        try
        {
            NoiseTest.LOGGER.info("Test Starting");
            
            GLFW.init();
            
            GLFW.EVENT_BUS.register(this);
            
            this.window = createWindow();
            this.window.onWindowInit(this::init);
            this.window.onWindowDraw(this::draw);
            this.window.open();
            
            beforeEventLoop();
            
            GLFW.eventLoop();
        }
        finally
        {
            NoiseTest.LOGGER.info("Test Stopping");
            
            GLFW.EVENT_BUS.unregister(this);
            
            GLFW.destroy();
        }
    }
    
    public static int noiseToInt(double noise)
    {
        return (int) (noise * 127.5 + 127.5);
    }
    
    public static void main(String[] args)
    {
        FastNoise fastNoise = new FastNoise(1337);
        fastNoise.setNoiseType(FastNoise.NoiseType.Value);
        
        Noise noise = new Noise(1337);
        noise.type(Noise.Type.VALUE);
        
        int width    = 512;
        int height   = 512;
        int channels = 1;
        
        ByteBuffer data = BufferUtils.createByteBuffer(width * height * channels);
        for (int i = 0, n = data.remaining(); i < n; i++)
        {
            int x = i % width;
            int y = i / width;
            
            int value     = noiseToInt(noise.get(x, y));
            int fastValue = noiseToInt(fastNoise.getNoise(x, y));
            data.put(i, (byte) value);
        }
        
        String filePath = "out/noise1.png";
        
        if (!stbi_write_png(filePath, width, height, channels, data, width * channels))
        {
            System.err.println("Image could not be saved: " + filePath);
        }
        
        Logger.setLevel(Level.FINER);
        
        new NoiseTest().run();
    }
}

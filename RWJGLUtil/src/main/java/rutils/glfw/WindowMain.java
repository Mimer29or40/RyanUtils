package rutils.glfw;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

class WindowMain extends Window
{
    private static final Builder BUILDER = new Builder().name("Main").visible(false).focused(false).focusOnShow(false);
    
    WindowMain()
    {
        super(WindowMain.BUILDER);
        
        glfwMakeContextCurrent(this.handle);
        
        org.lwjgl.opengl.GL.createCapabilities();
    }
    
    @Override
    public void destroy()
    {
        org.lwjgl.opengl.GL.destroy();
        
        super.destroy();
    }
}

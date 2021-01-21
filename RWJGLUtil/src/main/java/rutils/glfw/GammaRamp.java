package rutils.glfw;

import org.lwjgl.glfw.GLFWGammaRamp;
import rutils.MemUtil;

import java.util.Arrays;
import java.util.Objects;

public class GammaRamp
{
    public final int size;
    
    public final short[] red, green, blue;
    
    GammaRamp(GLFWGammaRamp glfwGammaRamp)
    {
        this.size = glfwGammaRamp.size();
        
        this.red = new short[this.size];
        this.green = new short[this.size];
        this.blue = new short[this.size];
    
        MemUtil.memCopy(glfwGammaRamp.red(), this.red);
        MemUtil.memCopy(glfwGammaRamp.green(), this.green);
        MemUtil.memCopy(glfwGammaRamp.blue(), this.blue);
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GammaRamp gammaRamp = (GammaRamp) o;
        return this.size == gammaRamp.size && Arrays.equals(this.red, gammaRamp.red) && Arrays.equals(this.green, gammaRamp.green) && Arrays.equals(this.blue, gammaRamp.blue);
    }
    
    @Override
    public int hashCode()
    {
        int result = Objects.hash(this.size);
        result = 31 * result + Arrays.hashCode(this.red);
        result = 31 * result + Arrays.hashCode(this.green);
        result = 31 * result + Arrays.hashCode(this.blue);
        return result;
    }
    
    @Override
    public String toString()
    {
        return "GammaRamp{" + "size=" + this.size + '}';
    }
}

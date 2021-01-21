package rutils.glfw.second;

import static org.lwjgl.glfw.GLFW.*;

public enum Modifier
{
    NONE(0x00000000),
    SHIFT(GLFW_MOD_SHIFT),
    CONTROL(GLFW_MOD_CONTROL),
    ALT(GLFW_MOD_ALT),
    SUPER(GLFW_MOD_SUPER),
    CAPS_LOCK(GLFW_MOD_CAPS_LOCK),
    NUM_LOCK(GLFW_MOD_NUM_LOCK),
    ANY(0xFFFFFFFF),
    ;
    
    private final int value;
    
    Modifier(int value)
    {
        this.value = value;
    }
    
    public int value()
    {
        return this.value;
    }
    
    /**
     * Checks if this modifier is active in the provided bitmap.
     *
     * @param mods The modifier bitmap.
     * @return True if this modifier is active in the bitmap.
     */
    public boolean active(int mods)
    {
        return (mods == 0 && this.value == 0) || (mods & this.value) != 0;
    }
}

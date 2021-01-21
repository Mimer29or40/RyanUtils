package rutils.glfw;

import java.util.function.Predicate;

import static org.lwjgl.glfw.GLFW.*;

@SuppressWarnings("unused")
public enum Modifier implements Predicate<Integer>
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
     * @return {@code true} if this modifier is active in the bitmap, otherwise
     * {@code false}
     */
    @Override
    public boolean test(Integer mods)
    {
        return (mods == 0 && this.value == 0) || (mods & this.value) != 0;
    }
}

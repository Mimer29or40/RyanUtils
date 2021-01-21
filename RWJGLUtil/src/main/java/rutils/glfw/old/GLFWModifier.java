package rutils.glfw.old;

import rutils.Logger;

import static org.lwjgl.glfw.GLFW.*;

@SuppressWarnings({"unused"})
public enum GLFWModifier
{
    NONE(GLFW_FALSE),
    SHIFT(GLFW_MOD_SHIFT),
    CONTROL(GLFW_MOD_CONTROL),
    ALT(GLFW_MOD_ALT),
    SUPER(GLFW_MOD_SUPER),
    CAPS_LOCK(GLFW_MOD_CAPS_LOCK),
    NUM_LOCK(GLFW_MOD_NUM_LOCK),
    ANY(0xFFFFFFFF),
    ;
    
    private static final Logger LOGGER = new Logger();
    
    private static boolean lockMods;
    
    /**
     * @return If locking modifier keys should lock.
     */
    public static boolean lockMods()
    {
        return GLFWModifier.lockMods;
    }
    
    /**
     * Sets if locking modifier keys should lock.
     *
     * @param lockMods The new state.
     */
    public static void lockMods(boolean lockMods)
    {
        GLFWModifier.LOGGER.finest("GLFWModifier 'lockMods' state changed: ", lockMods);
    
        GLFWModifier.lockMods = lockMods;
    }
    
    /**
     * Toggles if locking modifier keys should lock.
     */
    public static void toggleLockMods()
    {
        lockMods(!GLFWModifier.lockMods);
    }
    
    private final int value;
    
    GLFWModifier(int value)
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
        return (this.value & mods) != 0;
    }
}

package rutils.glfw;

import static org.lwjgl.glfw.GLFW.*;

public enum Modifier
{
    SHIFT(GLFW_MOD_SHIFT),
    CONTROL(GLFW_MOD_CONTROL),
    ALT(GLFW_MOD_ALT),
    SUPER(GLFW_MOD_SUPER),
    CAPS_LOCK(GLFW_MOD_CAPS_LOCK),
    NUM_LOCK(GLFW_MOD_NUM_LOCK),
    
    ANY(0xFFFFFFFF),
    
    ;
    
    private static int activeMods = 0;
    
    private static boolean lockMods = false;
    
    public static int activeMods()
    {
        return Modifier.activeMods;
    }
    
    /**
     * Internal methods to update the active modifier bitmap.
     *
     * @param mods The updated modifier bitmap.
     */
    static void updateMods(int mods)
    {
        Modifier.activeMods = mods;
    }
    
    /**
     * Sets the lock mods flag. Set {@code true} to enable lock key modifier
     * bits, or {@code false} to disable them. If enabled, callbacks that
     * receive modifier bits will also have the {@link Modifier#CAPS_LOCK}
     * set when the event was generated with Caps Lock on, and the
     * {@link Modifier#NUM_LOCK} set when Num Lock was on.
     *
     * @param lockMods {@code true} to enable lockMods mode, otherwise {@code false}.
     */
    public static void lockMods(boolean lockMods)
    {
        Modifier.lockMods = lockMods;
        GLFW.TASK_DELEGATOR.runTask(() -> GLFW.WINDOWS.keySet().forEach(handle -> glfwSetInputMode(handle, GLFW_LOCK_KEY_MODS, lockMods ? GLFW_TRUE : GLFW_FALSE)));
    }
    
    /**
     * @return Retrieves the lock mods flag.
     */
    public static boolean lockMods()
    {
        return Modifier.lockMods;
    }
    
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
     * Checks if this modifier is active.
     *
     * @return {@code true} if this modifier is active, otherwise {@code false}
     */
    public boolean test()
    {
        return (Modifier.activeMods & this.value) > 0;
    }
    
    /**
     * Checks to see if the provided modifiers are set.
     * <p>
     * If {@link Modifier#ANY ANY} is present among any combination of
     * Modifiers, then it will take precedent over the other Modifiers, i.e.
     * returning {@code true} if any modifiers are currently active.
     * <p>
     * If no modifiers are provided, then it will return {@code true} if and
     * only if no modifiers are currently active.
     *
     * @param modifiers The modifiers to query.
     * @return {@code true} if and only if the provided modifiers are active.
     */
    public static boolean test(Modifier... modifiers)
    {
        if (modifiers.length == 0) return Modifier.activeMods == 0;
        int query = 0;
        for (Modifier modifier : modifiers)
        {
            if (modifier == Modifier.ANY) return Modifier.activeMods > 0;
            query |= modifier.value;
        }
        return (Modifier.activeMods & query) == query;
    }
    
    /**
     * Checks to see if and only if the provided modifiers are set.
     * <p>
     * If {@link Modifier#ANY ANY} is present among any combination of
     * Modifiers, then it will take precedent over the other Modifiers, i.e.
     * returning {@code true} if any modifiers are currently active.
     * <p>
     * If no modifiers are provided, then it will return {@code true} if and
     * only if no modifiers are currently active.
     *
     * @param modifiers The modifiers to query.
     * @return {@code true} if and only if the provided modifiers are active.
     */
    public static boolean testExclusive(Modifier... modifiers)
    {
        if (modifiers.length == 0) return Modifier.activeMods == 0;
        int query = 0;
        for (Modifier modifier : modifiers)
        {
            if (modifier == Modifier.ANY) return Modifier.activeMods > 0;
            query |= modifier.value;
        }
        return Modifier.activeMods == query;
    }
}

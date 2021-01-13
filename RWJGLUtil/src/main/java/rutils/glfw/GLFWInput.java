package rutils.glfw;

public interface GLFWInput
{
    /**
     * @return If the input is in the down state.
     */
    boolean down();
    
    /**
     * @return If the input is in the up state.
     */
    boolean up();
    
    /**
     * @return If the input is in the held state.
     */
    boolean held();
    
    /**
     * @return If the input is in the repeat state.
     */
    boolean repeat();
    
    /**
     * @return The bit map of mods currently affecting this input.
     */
    int mods();
    
    /**
     * @return If the GLFWInput is in the down state with optional modifiers. This will only be true for one frame.
     */
    default boolean down(GLFWModifier... modifiers)
    {
        return down() && checkModifiers(modifiers);
    }
    
    /**
     * @return If the GLFWInput was released with optional modifiers. This will only be true for one frame.
     */
    default boolean up(GLFWModifier... modifiers)
    {
        return up() && checkModifiers(modifiers);
    }
    
    /**
     * @return If the GLFWInput is being held down with optional modifiers.
     */
    default boolean held(GLFWModifier... modifiers)
    {
        return held() && checkModifiers(modifiers);
    }
    
    /**
     * @return If the GLFWInput is being repeated with optional modifiers. This will be true for one frame at a time.
     */
    default boolean repeat(GLFWModifier... modifiers)
    {
        return repeat() && checkModifiers(modifiers);
    }
    
    /**
     * Checks if the supplied modifiers match which modifiers are pressed.
     *
     * @param modifiers The array of modifiers.
     * @return True if the supplied modifiers matches the actual modifiers.
     */
    default boolean checkModifiers(GLFWModifier[] modifiers)
    {
        if (modifiers.length == 0) return true;
        int mods = 0;
        for (GLFWModifier modifier : modifiers) mods |= modifier.value();
        return (mods == 0 && mods() == 0) || (mods() & mods) != 0;
    }
}

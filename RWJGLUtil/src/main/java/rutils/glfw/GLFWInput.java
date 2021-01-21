package rutils.glfw;


import java.util.function.Predicate;

public class GLFWInput
{
    protected boolean down, up, held, repeat;
    
    protected int _action, action;
    
    protected int _mods, mods;
    
    protected long downTime;
    
    /**
     * @return If the GLFWInput is in the down state with optional modifiers. This will only be true for one frame.
     */
    public boolean down(Modifier... modifiers)
    {
        return this.down && checkModifiers(modifiers);
    }
    
    /**
     * @return If the GLFWInput was released with optional modifiers. This will only be true for one frame.
     */
    public boolean up(Modifier... modifiers)
    {
        return this.up && checkModifiers(modifiers);
    }
    
    /**
     * @return If the GLFWInput is being held down with optional modifiers.
     */
    public boolean held(Modifier... modifiers)
    {
        return this.held && checkModifiers(modifiers);
    }
    
    /**
     * @return If the GLFWInput is being repeated with optional modifiers. This will be true for one frame at a time.
     */
    public boolean repeat(Modifier... modifiers)
    {
        return this.repeat && checkModifiers(modifiers);
    }
    
    /**
     * Checks if the supplied modifiers match which modifiers are pressed.
     *
     * @param modifiers The array of modifiers.
     * @return True if the supplied modifiers matches the actual modifiers.
     */
    protected boolean checkModifiers(Modifier[] modifiers)
    {
        if (modifiers.length == 0) return true;
        Predicate<Integer> predicate = modifiers[0];
        for (int i = 1; i < modifiers.length; i++) predicate = predicate.and(modifiers[i]);
        return predicate.test(this.mods);
    }
}

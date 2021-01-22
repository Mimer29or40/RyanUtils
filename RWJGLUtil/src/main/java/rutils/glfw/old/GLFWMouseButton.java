package rutils.glfw.old;

import org.joml.Vector2d;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;

public enum GLFWMouseButton implements GLFWInput
{
    NONE(-1),
    
    LEFT(GLFW_MOUSE_BUTTON_LEFT),
    RIGHT(GLFW_MOUSE_BUTTON_RIGHT),
    MIDDLE(GLFW_MOUSE_BUTTON_MIDDLE),
    
    FOUR(GLFW_MOUSE_BUTTON_4),
    FIVE(GLFW_MOUSE_BUTTON_5),
    SIX(GLFW_MOUSE_BUTTON_6),
    SEVEN(GLFW_MOUSE_BUTTON_7),
    EIGHT(GLFW_MOUSE_BUTTON_8),
    
    ;
    
    private static final HashMap<Integer, GLFWMouseButton> buttons = new HashMap<>();
    
    final int reference;
    
    boolean down, up, held, repeat;
    int newState, state, newMods, mods;
    long downTime, pressTime;
    
    boolean dragged;
    
    final Vector2d click  = new Vector2d();
    final Vector2d dClick = new Vector2d();
    
    GLFWMouseButton(int reference)
    {
        this.reference = reference;
    }
    
    /**
     * @return If the input is in the down state.
     */
    @Override
    public boolean down()
    {
        return this.down;
    }
    
    /**
     * @return If the input is in the up state.
     */
    @Override
    public boolean up()
    {
        return this.up;
    }
    
    /**
     * @return If the input is in the held state.
     */
    @Override
    public boolean held()
    {
        return this.held;
    }
    
    /**
     * @return If the input is in the repeat state.
     */
    @Override
    public boolean repeat()
    {
        return this.repeat;
    }
    
    /**
     * @return If the GLFWMouseButton is being dragged.
     */
    public boolean dragged()
    {
        return this.dragged;
    }
    
    /**
     * @return The bit map of mods currently affecting this input.
     */
    @Override
    public int mods()
    {
        return this.mods;
    }
    
    /**
     * @return If the GLFWMouseButton is being dragged with modifiers.
     */
    public boolean dragged(GLFWModifier... modifiers)
    {
        return dragged() && checkModifiers(modifiers);
    }
    
    /**
     * @return Gets the Input that corresponds to the GLFW constant.
     */
    public static GLFWMouseButton get(int reference)
    {
        return GLFWMouseButton.buttons.getOrDefault(reference, GLFWMouseButton.NONE);
    }
    
    static
    {
        for (GLFWMouseButton button : values())
        {
            GLFWMouseButton.buttons.put(button.reference, button);
        }
    }
}

package rutils.glfw.second;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_8;

public class Mouse
{
    public enum Button
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
    
        private static final HashMap<Integer, Button> BUTTON_MAP = new HashMap<>();
    
        final int reference;
    
        Button(int reference)
        {
            this.reference = reference;
        }
    
        /**
         * @return Gets the GLFWInput that corresponds to the GLFW constant.
         */
        public static Button get(int reference)
        {
            return Button.BUTTON_MAP.getOrDefault(reference, Button.NONE);
        }
    
        static
        {
            for (Button button : values())
            {
                Button.BUTTON_MAP.put(button.reference, button);
            }
        }
    }
}

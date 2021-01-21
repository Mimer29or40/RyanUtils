package rutils.glfw.old;

import org.lwjgl.glfw.GLFW;
import rutils.Logger;

@SuppressWarnings("unused")
public class GLFWKeyboard extends GLFWInputDevice
{
    private static final Logger LOGGER = new Logger();
    
    private final StringBuilder capturedText = new StringBuilder();
    
    GLFWKeyboard() {}
    
    /**
     * This is called by the Engine to generate the events and state changes for the Device.
     *  @param time  The time in nano seconds that it happened.
     * @param delta The time in nano seconds since the last frame.
     */
    public void generateGLFWEvents(/*Profiler profiler, */long time, long delta)
    {
        GLFWKeyboard.LOGGER.finest("Handling GLFWKeyboard EngineEvents");
        
        // try (Section capturedText = profiler.startSection("Captured Text"))
        {
            for (int i = 0, n = this.capturedText.length(); i < n; i++)
            {
                // EngineEvents.post(EngineEvent.KEYBOARD_KEY_TYPED, this.capturedText.charAt(i)); // TODO
            }
            this.capturedText.setLength(0);
        }
        
        // try (Section keys = profiler.startSection("Keys"))
        {
            for (GLFWKeyboardKey key : GLFWKeyboardKey.values())
            {
                key.down   = false;
                key.up     = false;
                key.repeat = false;
                key.mods   = 0;
                
                if (key.newState != key.state)
                {
                    if (key.newState == GLFW.GLFW_PRESS)
                    {
                        key.down     = true;
                        key.held     = true;
                        key.repeat   = true;
                        key.downTime = time;
                    }
                    else if (key.newState == GLFW.GLFW_RELEASE)
                    {
                        key.up       = true;
                        key.held     = false;
                        key.downTime = Long.MAX_VALUE;
                    }
                    key.state = key.newState;
                    key.mods  = key.newMods;
                }
                if (key.state == GLFW.GLFW_REPEAT && key.held && time - key.downTime > GLFWInputDevice.holdDelay)
                {
                    key.downTime += GLFWInputDevice.repeatDelay;
                    key.repeat = true;
                    key.mods   = key.newMods;
                }
                
                postEvents(key, time, delta);
            }
        }
    }
    
    /**
     * This is called by the Device to post any events that it may have generated this frame.
     *
     * @param key   The GLFWInput
     * @param time  The time in nano seconds that it happened.
     * @param delta The time in nano seconds since the last frame.
     */
    protected void postEvents(GLFWKeyboardKey key, long time, long delta)
    {
        // if (key.down) EngineEvents.post(EngineEvent.KEYBOARD_KEY_DOWN, key); // TODO
        if (key.up)
        {
            // EngineEvents.post(EngineEvent.KEYBOARD_KEY_UP, key); // TODO
            
            if (time - key.pressTime < GLFWInputDevice.doubleDelay)
            {
                // EngineEvents.post(EngineEvent.KEYBOARD_KEY_PRESSED, key, true); // TODO
            }
            else
            {
                // EngineEvents.post(EngineEvent.KEYBOARD_KEY_PRESSED, key, false); // TODO
                key.pressTime = time;
            }
        }
        // if (key.held) EngineEvents.post(EngineEvent.KEYBOARD_KEY_HELD, key); // TODO
        // if (key.repeat) EngineEvents.post(EngineEvent.KEYBOARD_KEY_REPEAT, key); // TODO
    }
    
    /**
     * This is the callback used by the window whenever an input is pressed, released, or repeated
     *
     * @param reference The GLFWInput
     * @param state     The action that took place
     * @param mods      The modifier info
     */
    public void stateCallback(int reference, int state, int mods)
    {
        GLFWKeyboard.LOGGER.finest("GLFWKeyboard State Callback: %s %s %s", reference, state, mods);
        
        GLFWKeyboardKey input = GLFWKeyboardKey.get(reference);
        input.newState = state;
        input.newMods  = mods;
    }
    
    /**
     * This is a callback for when {@link GLFW#glfwSetCharCallback} is called.
     *
     * @param codePoint the {@code codePoint} to be converted
     */
    public void charCallback(int codePoint)
    {
        GLFWKeyboard.LOGGER.finest("GLFWKeyboard Char Callback:", codePoint);
        
        this.capturedText.append(Character.toString(codePoint));
    }
}

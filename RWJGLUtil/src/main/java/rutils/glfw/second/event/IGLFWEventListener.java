package rutils.glfw.second.event;

/**
 * GLFWEvent listeners are wrapped with implementations of this interface
 */
public interface IGLFWEventListener
{
    void invoke(GLFWEvent event);
}

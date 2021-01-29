package rutils.glfw.event;

import rutils.glfw.event.events.GLFWEvent;

/**
 * GLFWEvent listeners are wrapped with implementations of this interface
 */
public interface IGLFWEventListener
{
    void invoke(GLFWEvent event);
}

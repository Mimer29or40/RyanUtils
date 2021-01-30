package rutils.glfw;

import rutils.glfw.event.Event;

/**
 * Event listeners are wrapped with implementations of this interface
 */
public interface IEventListener
{
    void invoke(Event event);
}

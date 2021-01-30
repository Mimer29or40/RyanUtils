package rutils.glfw.event;

import rutils.glfw.event.events.Event;

/**
 * Event listeners are wrapped with implementations of this interface
 */
public interface IEventListener
{
    void invoke(Event event);
}

package rutils.glfw;

import rutils.glfw.event.Event;

import java.util.function.Consumer;

/**
 * Event listeners are wrapped with implementations of this interface
 */
public interface IEventListener extends Consumer<Event>
{

}

package rutils.glfw.second.event;

/**
 * Event listeners are wrapped with implementations of this interface
 */
public interface IEventListener
{
    void invoke(Event event);
}

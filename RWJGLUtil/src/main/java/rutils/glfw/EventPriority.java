package rutils.glfw;

import rutils.glfw.event.Event;

public enum EventPriority implements IEventListener
{
    /**
     * Priority of event listeners, listeners will be sorted with respect to this priority level.
     * <p>
     * Note:
     * Due to using a ArrayList in the ListenerList,
     * these need to stay in a contiguous index starting at 0. {Default ordinal}
     */
    HIGHEST, //First to execute
    HIGH,
    NORMAL,
    LOW,
    LOWEST; //Last to execute
    
    /**
     * Performs this operation on the given argument.
     *
     * @param event the input argument
     */
    @Override
    public void accept(Event event)
    {
        event.setPhase(this);
    }
}

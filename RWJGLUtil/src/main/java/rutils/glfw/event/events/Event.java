package rutils.glfw.event.events;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rutils.glfw.event.EventProperty;
import rutils.glfw.event.EventPriority;

public interface Event
{
    @EventProperty
    double time();
    
    @Nullable
    EventPriority getPhase();
    
    void setPhase(@NotNull EventPriority value);
    
    final class _Event extends AbstractEvent implements Event
    {
        private _Event() {}
    }
    
    static Event create()
    {
        return new _Event();
    }
}

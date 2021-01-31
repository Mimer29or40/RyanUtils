package rutils.glfw.event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rutils.glfw.EventProperty;
import rutils.glfw.EventPriority;

public interface Event
{
    @EventProperty
    double time();
    
    @Nullable
    EventPriority getPhase();
    
    void setPhase(@NotNull EventPriority value);
}

package rutils.glfw.event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rutils.glfw.EventPriority;
import rutils.glfw.EventProperty;

public interface Event
{
    @EventProperty(format = "%.3f")
    double time();
    
    @Nullable
    EventPriority getPhase();
    
    void setPhase(@NotNull EventPriority value);
}

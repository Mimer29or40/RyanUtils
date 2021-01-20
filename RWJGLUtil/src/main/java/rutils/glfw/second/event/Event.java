package rutils.glfw.second.event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Objects;

public class Event
{
    private EventPriority phase = null;
    
    @Override
    public String toString()
    {
        Field[] fields = getClass().getFields();
        
        StringBuilder s = new StringBuilder(getClass().getSimpleName()).append("{");
        for (int i = 0, n = fields.length; i < n; i++)
        {
            Field field = fields[i];
            s.append(field.getName()).append('=');
            try
            {
                s.append(field.get(this));
            }
            catch (IllegalAccessException e)
            {
                s.append(field.getType());
            }
            if (i + 1 < n) s.append(' ');
        }
        return s.append("}").toString();
    }
    
    @Nullable
    public EventPriority getPhase()
    {
        return this.phase;
    }
    
    public void setPhase(@NotNull EventPriority value)
    {
        Objects.requireNonNull(value, "setPhase argument must not be null");
        int prev = phase == null ? -1 : phase.ordinal();
        if (prev >= value.ordinal()) throw new IllegalArgumentException("Attempted to set event phase to " + value + " when already " + phase);
        phase = value;
    }
}

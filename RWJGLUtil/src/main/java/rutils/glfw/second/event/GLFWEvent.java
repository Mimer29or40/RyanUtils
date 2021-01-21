package rutils.glfw.second.event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rutils.ClassUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public class GLFWEvent
{
    private static final Map<Class<? extends GLFWEvent>, Set<Method>> METHOD_CACHE = new HashMap<>();
    
    private GLFWEventPriority phase = null;
    
    @Override
    public String toString()
    {
        Set<Method> methods = GLFWEvent.METHOD_CACHE.computeIfAbsent(getClass(), c -> ClassUtil.getMethods(c, m -> m.isAnnotationPresent(Property.class)));
        
        StringBuilder s = new StringBuilder(getClass().getSimpleName()).append("{");
        
        Iterator<Method> iterator = methods.iterator();
        while (true)
        {
            Method method = iterator.next();
            s.append(method.getName()).append('=');
            try
            {
                s.append(method.invoke(this));
            }
            catch (IllegalAccessException | InvocationTargetException e)
            {
                s.append(method.getReturnType());
            }
            
            if (iterator.hasNext())
            {
                s.append(' ');
            }
            else
            {
                break;
            }
        }
        return s.append("}").toString();
    }
    
    @Nullable
    public GLFWEventPriority getPhase()
    {
        return this.phase;
    }
    
    public void setPhase(@NotNull GLFWEventPriority value)
    {
        Objects.requireNonNull(value, "setPhase argument must not be null");
        int prev = phase == null ? -1 : phase.ordinal();
        if (prev >= value.ordinal()) throw new IllegalArgumentException("Attempted to set event phase to " + value + " when already " + phase);
        phase = value;
    }
    
    @Retention(value = RUNTIME)
    @Target(value = METHOD)
    protected @interface Property {}
}

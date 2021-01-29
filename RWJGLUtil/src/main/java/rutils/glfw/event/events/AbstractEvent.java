package rutils.glfw.event.events;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rutils.ClassUtil;
import rutils.StringUtil;
import rutils.glfw.event.GLFWEventPriority;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class AbstractEvent implements Event
{
    private static final Map<Class<? extends Event>, Set<Method>> METHOD_CACHE = new ConcurrentHashMap<>();
    
    private final double time;
    
    private GLFWEventPriority phase = null;
    
    public AbstractEvent()
    {
        this.time = glfwGetTime();
    }
    
    @Override
    public String toString()
    {
        Set<Method> methods;
        synchronized (AbstractEvent.METHOD_CACHE)
        {
            methods = AbstractEvent.METHOD_CACHE.computeIfAbsent(getClass(), c -> ClassUtil.getMethods(c, m -> m.isAnnotationPresent(Property.class)));
        }
        
        StringBuilder s = new StringBuilder(getClass().getSimpleName()).append("{");
        
        Iterator<Method> iterator = methods.iterator();
        while (true)
        {
            Method   method   = iterator.next();
            Property property = method.getAnnotation(Property.class);
            
            if (property.printName()) s.append(method.getName()).append('=');
            try
            {
                Class<?> declaringClass = method.getDeclaringClass();
                Method toString = declaringClass.getDeclaredMethod(method.getName() + "Transform");
                s.append(toString.invoke(this));
            }
            catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored)
            {
                try
                {
                    s.append(StringUtil.toString(method.invoke(this)));
                }
                catch (IllegalAccessException | InvocationTargetException ignored1)
                {
                    s.append(method.getReturnType());
                }
            }
            
            if (iterator.hasNext())
            {
                s.append(", ");
            }
            else
            {
                break;
            }
        }
        return s.append("}").toString();
    }
    
    @Override
    public double time()
    {
        return this.time;
    }
    
    public String timeTransform()
    {
        return String.format("%.3f", time());
    }
    
    @Override
    public @Nullable GLFWEventPriority getPhase()
    {
        return this.phase;
    }
    
    @Override
    public void setPhase(@NotNull GLFWEventPriority value)
    {
    
    }
}

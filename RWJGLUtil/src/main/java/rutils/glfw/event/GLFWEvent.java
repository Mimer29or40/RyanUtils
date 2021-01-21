package rutils.glfw.event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rutils.ClassUtil;
import rutils.StringUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class GLFWEvent
{
    private static final Map<Class<? extends GLFWEvent>, Set<Method>> METHOD_CACHE = new ConcurrentHashMap<>();
    
    private final double time;
    
    private GLFWEventPriority phase = null;
    
    public GLFWEvent()
    {
        // this.time = System.nanoTime() / 1_000_000_000D;
        this.time = glfwGetTime();
    }
    
    @Override
    public String toString()
    {
        Set<Method> methods;
        synchronized (GLFWEvent.METHOD_CACHE)
        {
            methods = GLFWEvent.METHOD_CACHE.computeIfAbsent(getClass(), c -> ClassUtil.getMethods(c, m -> m.isAnnotationPresent(Property.class)));
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
    
    @Property
    public double time()
    {
        return this.time;
    }
    
    protected String timeTransform()
    {
        return String.format("%.3f", this.time);
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
    protected @interface Property
    {
        boolean printName() default true;
    }
}

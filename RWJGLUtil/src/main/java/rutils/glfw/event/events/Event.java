package rutils.glfw.event.events;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import rutils.ClassUtil;
import rutils.StringUtil;
import rutils.glfw.event.GLFWEventPriority;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.lwjgl.glfw.GLFW.glfwGetTime;

public interface Event
{
    @Property
    double time();
    
    @Nullable
    GLFWEventPriority getPhase();
    
    void setPhase(@NotNull GLFWEventPriority value);
    
    @Retention(value = RUNTIME)
    @Target(value = METHOD)
    @interface Property
    {
        boolean printName() default true;
    }
}

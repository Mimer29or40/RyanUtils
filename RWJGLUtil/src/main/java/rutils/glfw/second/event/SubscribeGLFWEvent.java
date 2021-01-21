package rutils.glfw.second.event;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(value = RUNTIME)
@Target(value = METHOD)
public @interface SubscribeGLFWEvent
{
    GLFWEventPriority priority() default GLFWEventPriority.NORMAL;
}

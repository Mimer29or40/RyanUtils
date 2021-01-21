package rutils.glfw.second.event;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to subscribe a method to an {@link GLFWEvent}
 *
 * This annotation can only be applied to single parameter methods, where the single parameter is a subclass of
 * {@link GLFWEvent}.
 *
 * Use {@link EventBus#register(Object)} to submit either an Object instance or a {@link Class} to the event bus
 * for scanning to generate callback {@link IGLFWEventListener} wrappers.
 *
 * The GLFWEvent Bus system generates an ASM wrapper that dispatches to the marked method.
 */
@Retention(value = RUNTIME)
@Target(value = METHOD)
public @interface SubscribeGLFWEvent
{
    GLFWEventPriority priority() default GLFWEventPriority.NORMAL;
    boolean receiveCanceled() default false;
}

package rutils.glfw.old;

public abstract class GLFWDevice
{
    /**
     * Generates the events that are consumed in that frame.
     * @param time The time since the engine was started in nanoseconds.
     * @param delta The time since the last frame in nanoseconds.
     */
    abstract void generateGLFWEvents(long time, long delta);
}

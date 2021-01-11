package rutils.glfw.event;

public interface IGLFWEventHandler
{
    /**
     * Method is called once per event every frame
     *
     * @param t     The total time since the engine has started in seconds.
     * @param dt    The time since last frame in seconds.
     * @param event The engine event.
     * @return If the handler consumed the event.
     */
    boolean handleEvent(double t, double dt, GLFWEvent event);
}

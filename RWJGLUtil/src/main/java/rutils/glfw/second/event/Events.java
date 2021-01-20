package rutils.glfw.second.event;

import rutils.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

public class Events
{
    private static final Logger LOGGER = new Logger();
    
    static final EventBus EVENT_BUS = new EventBus();
    
    @SubscribeEvent
    public static void doThing(EventWindowFocused event)
    {
        LOGGER.info("doThing", "static", event);
    }
    
    static class Thing
    {
        Thing()
        {
            EVENT_BUS.register(this);
        }
        
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public void doThing(EventWindowFocused event)
        {
            LOGGER.info("doThing", getClass().getSimpleName(), event);
        }
    }
    
    static class SecondThing extends Thing
    {
        SecondThing()
        {
            super();
        }
        
        // @Override
        // @SubscribeEvent
        // public void doThing(EventWindowFocused event)
        // {
        //     LOGGER.info("@Override doThing", getClass().getSimpleName(), event);
        // }
        
        @SubscribeEvent
        public void doOtherThing(EventWindow event)
        {
            LOGGER.info("doOtherThing", getClass().getSimpleName(), event);
        }
    }
    
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException
    {
        // // Set<Method> methods = new Reflections(new MethodAnnotationsScanner()).getMethodsAnnotatedWith(SubscribeEvent.class);
        // Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()).setScanners(new MethodAnnotationsScanner()));
        // Set<Method> methods     = reflections.getMethodsAnnotatedWith(SubscribeEvent.class);
        // for (Method method : methods)
        // {
        //     System.out.println(Arrays.toString(method.getParameters()));
        //     method.invoke(null, new EventWindowFocused(null, true));
        // }
        Logger.setLevel(Level.ALL);
        EVENT_BUS.register(Events.class);
        new Thing();
        new SecondThing();
        EVENT_BUS.post(new EventWindowFocused(null, false));
        EVENT_BUS.post(new EventWindow(null));
    }
}

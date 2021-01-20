package rutils.glfw.second.event;

import rutils.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class EventBus
{
    private static final Logger LOGGER = new Logger();
    
    private static final AtomicInteger maxID = new AtomicInteger(0);
    
    private final Map<Integer, IEventListener>                            wrappedCache       = new HashMap<>();
    private final Map<Object, List<IEventListener>>                       objectListeners    = new ConcurrentHashMap<>();
    private final Map<Class<?>, Set<IEventListener>>                      eventListeners     = new ConcurrentHashMap<>();
    private final Map<EventPriority, Map<Class<?>, List<IEventListener>>> classListenersMaps = new ConcurrentHashMap<>();
    
    private final boolean trackPhases;
    
    private final    int     busID    = EventBus.maxID.getAndIncrement();
    private volatile boolean shutdown = false;
    
    public EventBus(boolean trackPhases)
    {
        this.trackPhases = trackPhases;
        
        for (EventPriority priority : EventPriority.values()) this.classListenersMaps.put(priority, new HashMap<>());
    }
    
    public EventBus()
    {
        this(false);
    }
    
    public void start()
    {
        this.shutdown = false;
    }
    
    public void shutdown()
    {
        EventBus.LOGGER.severe("EventBus {} shutting down - future events will not be posted.", this.busID, new Exception("stacktrace"));
        this.shutdown = true;
    }
    
    public void register(final Object target)
    {
        if (this.objectListeners.containsKey(target))
        {
            return;
        }
        
        if (target.getClass() == Class.class)
        {
            registerClass((Class<?>) target);
        }
        else
        {
            registerObject(target);
        }
    }
    
    public void unregister(final Object target)
    {
        List<IEventListener> toRemove = this.objectListeners.remove(target);
        
        if (toRemove == null) return;
        for (Map<Class<?>, List<IEventListener>> classListenersMap : this.classListenersMaps.values())
        {
            for (List<IEventListener> classListeners : classListenersMap.values())
            {
                classListeners.removeAll(toRemove);
            }
        }
    }
    
    public void post(Event event)
    {
        if (this.shutdown) return;
        
        EventBus.LOGGER.finer("Posting", event);
        
        Set<IEventListener> listeners = getListeners(event.getClass());
        
        int index = 0;
        try
        {
            for (IEventListener listener : listeners)
            {
                if (!this.trackPhases && Objects.equals(listener.getClass(), EventPriority.class)) continue;
                listener.invoke(event);
                index++;
            }
        }
        catch (Throwable throwable)
        {
            StringBuilder builder = new StringBuilder();
            builder.append("Exception caught during firing event: ").append(throwable.getMessage()).append('\n');
            builder.append("\tIndex: ").append(index).append('\n');
            builder.append("\tListeners:\n");
            index = 0;
            for (IEventListener listener : listeners) builder.append("\t\t").append(index++).append(": ").append(listener).append('\n');
            final StringWriter sw = new StringWriter();
            throwable.printStackTrace(new PrintWriter(sw));
            builder.append(sw.getBuffer());
            EventBus.LOGGER.severe(builder.toString());
            throw throwable;
        }
    }
    
    private void registerClass(final Class<?> clazz)
    {
        for (Method m : clazz.getMethods())
        {
            if (Modifier.isStatic(m.getModifiers()))
            {
                if (m.isAnnotationPresent(SubscribeEvent.class))
                {
                    registerListener(clazz, m, m);
                }
            }
        }
    }
    
    private void registerObject(final Object obj)
    {
        final HashSet<Class<?>> classes = new HashSet<>();
        typesFor(obj.getClass(), classes);
        for (Method m : obj.getClass().getMethods())
        {
            if (!Modifier.isStatic(m.getModifiers()))
            {
                for (Class<?> c : classes)
                {
                    Optional<Method> declMethod = getDeclaredMethod(c, m);
                    if (declMethod.isPresent() && declMethod.get().isAnnotationPresent(SubscribeEvent.class))
                    {
                        registerListener(obj, m, declMethod.get());
                        break;
                    }
                }
            }
        }
    }
    
    private void registerListener(final Object target, final Method method, final Method real)
    {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length != 1)
        {
            throw new IllegalArgumentException("Method " + method + " has @SubscribeEvent annotation. " +
                                               "It has " + parameterTypes.length + " arguments, " +
                                               "but event handler methods require a single argument only."
            );
        }
        
        Class<?> eventType = parameterTypes[0];
        
        if (!Event.class.isAssignableFrom(eventType))
        {
            throw new IllegalArgumentException("Method " + method + " has @SubscribeEvent annotation, " +
                                               "but takes an argument that is not an Event subtype : " + eventType);
        }
        
        addToListeners(target, eventType, wrapMethod(target, real), method.getAnnotation(SubscribeEvent.class).priority());
    }
    
    private void addToListeners(final Object target, final Class<?> eventType, final IEventListener listener, final EventPriority priority)
    {
        EventBus.LOGGER.finer("Adding listener '%s' of '%s' to target '%s' with priority=%s", listener, eventType.getSimpleName(), target, priority);
        
        List<IEventListener> objectListeners = this.objectListeners.computeIfAbsent(target, c -> Collections.synchronizedList(new ArrayList<>()));
        objectListeners.add(listener);
        
        Map<Class<?>, List<IEventListener>> classListenersMap = this.classListenersMaps.get(priority);
        
        this.eventListeners.remove(eventType);
        
        List<IEventListener> classListeners = classListenersMap.computeIfAbsent(eventType, c -> Collections.synchronizedList(new ArrayList<>()));
        classListeners.add(listener);
    }
    
    private void typesFor(final Class<?> clz, final Set<Class<?>> visited)
    {
        if (clz.getSuperclass() == null) return;
        typesFor(clz.getSuperclass(), visited);
        for (Class<?> i : clz.getInterfaces()) typesFor(i, visited);
        visited.add(clz);
    }
    
    
    private Optional<Method> getDeclaredMethod(final Class<?> clz, final Method in)
    {
        try
        {
            return Optional.of(clz.getDeclaredMethod(in.getName(), in.getParameterTypes()));
        }
        catch (NoSuchMethodException nse)
        {
            return Optional.empty();
        }
    }
    
    public IEventListener wrapMethod(final Object target, final Method method)
    {
        int hash = Objects.hash(target, method);
        return this.wrappedCache.computeIfAbsent(hash, h -> event -> {
            try
            {
                method.invoke(target.getClass() == Class.class ? null : target, event);
            }
            catch (IllegalAccessException | InvocationTargetException e)
            {
                EventBus.LOGGER.severe("Could not access listener method.");
                EventBus.LOGGER.severe(e);
            }
        });
    }
    
    private Set<IEventListener> getListeners(final Class<?> eventClass)
    {
        return this.eventListeners.computeIfAbsent(eventClass, c -> {
            Set<IEventListener> listeners = new LinkedHashSet<>();
            for (EventPriority priority : EventPriority.values())
            {
                Map<Class<?>, List<IEventListener>> classListenersMap = this.classListenersMaps.get(priority);
                
                Class<?> clazz = eventClass;
                boolean  toAdd = true;
                while (clazz != Object.class)
                {
                    List<IEventListener> eventListeners = classListenersMap.get(clazz);
                    
                    if (eventListeners != null)
                    {
                        if (toAdd)
                        {
                            listeners.add(priority);
                            toAdd = false;
                        }
                        listeners.addAll(eventListeners);
                    }
                    clazz = clazz.getSuperclass();
                }
            }
            return listeners;
        });
    }
}

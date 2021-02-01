package rutils.glfw;

import rutils.ClassUtil;
import rutils.Logger;
import rutils.glfw.event.Event;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
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
    
    private final Queue<Event> eventQueue = new ConcurrentLinkedQueue<>();
    
    private final Thread thread;
    
    public EventBus(boolean trackPhases)
    {
        this.trackPhases = trackPhases;
        
        for (EventPriority priority : EventPriority.values()) this.classListenersMaps.put(priority, new HashMap<>());
        
        this.thread = new Thread(() -> {
            while (!this.shutdown)
            {
                while (!this.eventQueue.isEmpty())
                {
                    Event event = this.eventQueue.poll();
                    
                    EventBus.LOGGER.finest("Posting", event);
                    
                    Set<IEventListener> listeners = this.eventListeners.computeIfAbsent(event.getClass(), this::computeListeners);
                    
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
                Thread.yield();
            }
        }, "EventBus");
    }
    
    public void start()
    {
        this.shutdown = false;
        this.thread.start();
    }
    
    public void shutdown()
    {
        EventBus.LOGGER.fine("EventBus %s shutting down - future events will not be posted.", this.busID);
        this.shutdown = true;
        try
        {
            this.thread.join();
        }
        catch (InterruptedException ignored) { }
    }
    
    public void register(final Object target)
    {
        if (this.objectListeners.containsKey(target)) return;
        
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
        
        this.eventQueue.offer(event);
    }
    
    private void registerClass(final Class<?> clazz)
    {
        for (Method m : ClassUtil.getMethods(clazz, m -> m.getDeclaringClass() != Object.class && Modifier.isStatic(m.getModifiers())))
        {
            if (m.isAnnotationPresent(SubscribeEvent.class))
            {
                registerListener(clazz, m);
            }
        }
    }
    
    private void registerObject(final Object obj)
    {
        for (Method m : ClassUtil.getMethods(obj.getClass(), m -> m.getDeclaringClass() != Object.class && !Modifier.isStatic(m.getModifiers()) && m.canAccess(obj)))
        {
            if (m.isAnnotationPresent(SubscribeEvent.class))
            {
                registerListener(obj, m);
            }
        }
    }
    
    private void registerListener(final Object target, final Method method)
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
        
        addToListeners(target, eventType, wrapMethod(target, method), method.getAnnotation(SubscribeEvent.class).priority());
    }
    
    private void addToListeners(final Object target, final Class<?> eventType, final IEventListener listener, final EventPriority priority)
    {
        EventBus.LOGGER.finer("Adding listener '%s' of '%s' to target '%s' with priority=%s", listener, eventType.getSimpleName(), target, priority);
        
        List<IEventListener> objectListeners = this.objectListeners.computeIfAbsent(target, c -> Collections.synchronizedList(new ArrayList<>()));
        objectListeners.add(listener);
        
        Map<Class<?>, List<IEventListener>> classListenersMap = this.classListenersMaps.get(priority);
        
        this.eventListeners.clear();
        
        List<IEventListener> classListeners = classListenersMap.computeIfAbsent(eventType, c -> Collections.synchronizedList(new ArrayList<>()));
        classListeners.add(listener);
    }
    
    public IEventListener wrapMethod(final Object target, final Method method)
    {
        int hash = Objects.hash(target, method.getName(), Arrays.hashCode(method.getParameterTypes()));
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
    
    private Set<IEventListener> computeListeners(final Class<?> eventClass)
    {
        Set<Class<?>> classes  = ClassUtil.getTypes(eventClass, clz -> Modifier.isInterface(clz.getModifiers()));
        Class<?>[]    classArr = classes.toArray(new Class<?>[0]);
        
        Set<IEventListener> listeners = new LinkedHashSet<>();
        for (EventPriority priority : EventPriority.values())
        {
            Map<Class<?>, List<IEventListener>> classListenersMap = this.classListenersMaps.get(priority);
            
            boolean toAdd = true;
            for (int i = classArr.length - 1; i >= 0; i--)
            {
                List<IEventListener> eventListeners = classListenersMap.get(classArr[i]);
                
                if (eventListeners != null)
                {
                    if (toAdd)
                    {
                        listeners.add(priority);
                        toAdd = false;
                    }
                    listeners.addAll(eventListeners);
                }
            }
        }
        return listeners;
    }
}

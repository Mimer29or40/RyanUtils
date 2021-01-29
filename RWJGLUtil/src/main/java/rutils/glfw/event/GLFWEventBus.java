package rutils.glfw.event;

import rutils.ClassUtil;
import rutils.Logger;
import rutils.glfw.event.events.GLFWEvent;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class GLFWEventBus
{
    private static final Logger LOGGER = new Logger();
    
    private static final AtomicInteger maxID = new AtomicInteger(0);
    
    private final Map<Integer, IGLFWEventListener>                                wrappedCache       = new HashMap<>();
    private final Map<Object, List<IGLFWEventListener>>                           objectListeners    = new ConcurrentHashMap<>();
    private final Map<Class<?>, Set<IGLFWEventListener>>                          eventListeners     = new ConcurrentHashMap<>();
    private final Map<GLFWEventPriority, Map<Class<?>, List<IGLFWEventListener>>> classListenersMaps = new ConcurrentHashMap<>();
    
    private final boolean trackPhases;
    
    private final    int     busID    = GLFWEventBus.maxID.getAndIncrement();
    private volatile boolean shutdown = false;
    
    private final Queue<GLFWEvent> eventQueue = new ConcurrentLinkedQueue<>();
    
    private final Thread thread;
    
    public GLFWEventBus(boolean trackPhases)
    {
        this.trackPhases = trackPhases;
        
        for (GLFWEventPriority priority : GLFWEventPriority.values()) this.classListenersMaps.put(priority, new HashMap<>());
        
        this.thread = new Thread(() -> {
            while (!this.shutdown)
            {
                while (!this.eventQueue.isEmpty())
                {
                    GLFWEvent event = this.eventQueue.poll();
    
                    GLFWEventBus.LOGGER.finest("Posting", event);
                    
                    Set<IGLFWEventListener> listeners = getListeners(event.getClass());
    
                    int index = 0;
                    try
                    {
                        for (IGLFWEventListener listener : listeners)
                        {
                            if (!this.trackPhases && Objects.equals(listener.getClass(), GLFWEventPriority.class)) continue;
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
                        for (IGLFWEventListener listener : listeners) builder.append("\t\t").append(index++).append(": ").append(listener).append('\n');
                        final StringWriter sw = new StringWriter();
                        throwable.printStackTrace(new PrintWriter(sw));
                        builder.append(sw.getBuffer());
                        GLFWEventBus.LOGGER.severe(builder.toString());
                        throw throwable;
                    }
                }
                Thread.yield();
            }
        }, "EventBus");
    }
    
    public GLFWEventBus()
    {
        this(false);
    }
    
    public void start()
    {
        this.shutdown = false;
        this.thread.start();
    }
    
    public void shutdown()
    {
        // GLFWEventBus.LOGGER.warning("GLFWEventBus %s shutting down - future events will not be posted.\n%s", this.busID, new Exception("stacktrace"));
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
        List<IGLFWEventListener> toRemove = this.objectListeners.remove(target);
        
        if (toRemove == null) return;
        for (Map<Class<?>, List<IGLFWEventListener>> classListenersMap : this.classListenersMaps.values())
        {
            for (List<IGLFWEventListener> classListeners : classListenersMap.values())
            {
                classListeners.removeAll(toRemove);
            }
        }
    }
    
    public void post(GLFWEvent event)
    {
        if (this.shutdown) return;
        
        this.eventQueue.offer(event);
    }
    
    private void registerClass(final Class<?> clazz)
    {
        for (Method m : ClassUtil.getMethods(clazz, m -> m.getDeclaringClass() != Object.class && Modifier.isStatic(m.getModifiers())))
        {
            if (m.isAnnotationPresent(SubscribeGLFWEvent.class))
            {
                registerListener(clazz, m);
            }
        }
    }
    
    private void registerObject(final Object obj)
    {
        for (Method m : ClassUtil.getMethods(obj.getClass(), m -> m.getDeclaringClass() != Object.class && !Modifier.isStatic(m.getModifiers()) && m.canAccess(obj)))
        {
            if (m.isAnnotationPresent(SubscribeGLFWEvent.class))
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
            throw new IllegalArgumentException("Method " + method + " has @SubscribeGLFWEvent annotation. " +
                                               "It has " + parameterTypes.length + " arguments, " +
                                               "but event handler methods require a single argument only."
            );
        }
        
        Class<?> eventType = parameterTypes[0];
        
        if (!GLFWEvent.class.isAssignableFrom(eventType))
        {
            throw new IllegalArgumentException("Method " + method + " has @SubscribeGLFWEvent annotation, " +
                                               "but takes an argument that is not an GLFWEvent subtype : " + eventType);
        }
        
        addToListeners(target, eventType, wrapMethod(target, method), method.getAnnotation(SubscribeGLFWEvent.class).priority());
    }
    
    private void addToListeners(final Object target, final Class<?> eventType, final IGLFWEventListener listener, final GLFWEventPriority priority)
    {
        GLFWEventBus.LOGGER.finer("Adding listener '%s' of '%s' to target '%s' with priority=%s", listener, eventType.getSimpleName(), target, priority);
        
        List<IGLFWEventListener> objectListeners = this.objectListeners.computeIfAbsent(target, c -> Collections.synchronizedList(new ArrayList<>()));
        objectListeners.add(listener);
        
        Map<Class<?>, List<IGLFWEventListener>> classListenersMap = this.classListenersMaps.get(priority);
        
        this.eventListeners.clear();
        
        List<IGLFWEventListener> classListeners = classListenersMap.computeIfAbsent(eventType, c -> Collections.synchronizedList(new ArrayList<>()));
        classListeners.add(listener);
    }
    
    public IGLFWEventListener wrapMethod(final Object target, final Method method)
    {
        int hash = Objects.hash(target, method.getName(), Arrays.hashCode(method.getParameterTypes()));
        return this.wrappedCache.computeIfAbsent(hash, h -> event -> {
            try
            {
                method.invoke(target.getClass() == Class.class ? null : target, event);
            }
            catch (IllegalAccessException | InvocationTargetException e)
            {
                GLFWEventBus.LOGGER.severe("Could not access listener method.");
                GLFWEventBus.LOGGER.severe(e);
            }
        });
    }
    
    private Set<IGLFWEventListener> getListeners(final Class<?> eventClass)
    {
        return this.eventListeners.computeIfAbsent(eventClass, c -> {
            Set<IGLFWEventListener> listeners = new LinkedHashSet<>();
            for (GLFWEventPriority priority : GLFWEventPriority.values())
            {
                Map<Class<?>, List<IGLFWEventListener>> classListenersMap = this.classListenersMaps.get(priority);
                
                Class<?> clazz = eventClass;
                boolean  toAdd = true;
                while (clazz != Object.class)
                {
                    List<IGLFWEventListener> eventListeners = classListenersMap.get(clazz);
                    
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

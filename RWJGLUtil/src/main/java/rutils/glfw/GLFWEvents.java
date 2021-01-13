package rutils.glfw;

import rutils.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.function.Consumer;

import static rutils.StrUtil.join;

public class GLFWEvents
{
    private static final Logger LOGGER = new Logger();
    
    private static final HashMap<String, String[]>                     REGISTRY      = new HashMap<>();
    private static final HashMap<String, ArrayList<GLFWEvent>>         EVENTS        = new HashMap<>();
    private static final HashMap<String, HashSet<Consumer<GLFWEvent>>> SUBSCRIPTIONS = new HashMap<>();
    
    /**
     * Registers an GLFWEvent with parameters that can be posted.
     * @param eventType The event type
     * @param parameters The event parameters
     */
    public static void register(String eventType, String[] parameters)
    {
        GLFWEvents.LOGGER.fine("Registered GLFWEvent '%s' with parameters %s", eventType, Arrays.toString(parameters));
        
        GLFWEvents.REGISTRY.put(eventType, parameters);
    }
    
    /**
     * @return All events posted during the current frame.
     */
    public static Iterable<GLFWEvent> get()
    {
        GLFWEvents.LOGGER.finest("Getting all GLFWEvents");
        
        ArrayList<GLFWEvent> events = new ArrayList<>();
        GLFWEvents.EVENTS.values().forEach(events::addAll);
        return events;
    }
    
    /**
     * @param eventTypes GLFWEvent type filter
     * @return All events posted during the current frame that are of the types provided.
     */
    public static Iterable<GLFWEvent> get(String... eventTypes)
    {
        GLFWEvents.LOGGER.finest("Getting GLFWEvents for types", join(eventTypes, ", ", "[", "]"));
        
        ArrayList<GLFWEvent> events = new ArrayList<>();
        for (String eventType : eventTypes)
        {
            GLFWEvents.EVENTS.computeIfAbsent(eventType, e -> new ArrayList<>());
            events.addAll(GLFWEvents.EVENTS.get(eventType));
        }
        return events;
    }
    
    /**
     * @param eventGroups GLFWEventGroup filters
     * @return All events posted during the current frame that are apart of the GLFWEventGroup's provided.
     */
    public static Iterable<GLFWEvent> get(GLFWEventGroup... eventGroups)
    {
        GLFWEvents.LOGGER.finest("Getting GLFWEvents for groups", join(eventGroups, ", ", "[", "]"));
        
        ArrayList<GLFWEvent> events = new ArrayList<>();
        for (GLFWEventGroup eventGroup : eventGroups)
        {
            for (String eventType : eventGroup.events())
            {
                GLFWEvents.EVENTS.computeIfAbsent(eventType, e -> new ArrayList<>());
                events.addAll(GLFWEvents.EVENTS.get(eventType));
            }
        }
        return events;
    }
    
    /**
     * Posts an event that can be consumed and subscribed to.
     *
     * @param eventType The event class to post.
     * @param arguments The parameters that are passed to the GLFWEvent class
     */
    public static void post(String eventType, Object... arguments)
    {
        if (!GLFWEvents.REGISTRY.containsKey(eventType)) throw new RuntimeException(String.format("GLFWEvent '%s' not registered", eventType));
        
        GLFWEvents.EVENTS.computeIfAbsent(eventType, e -> new ArrayList<>());
        GLFWEvent event = new GLFWEvent(eventType, GLFWEvents.REGISTRY.get(eventType), arguments);
        GLFWEvents.EVENTS.get(eventType).add(event);
        
        GLFWEvents.LOGGER.finer("GLFWEvent Posted:", event);
        
        GLFWEvents.SUBSCRIPTIONS.computeIfAbsent(eventType, e -> new HashSet<>());
        for (Consumer<GLFWEvent> function : GLFWEvents.SUBSCRIPTIONS.get(eventType))
        {
            GLFWEvents.LOGGER.finest("Calling Subscribed Function:", event);
            
            function.accept(event);
        }
    }
    
    /**
     * Subscribe to a specific event so that a function is called as soon as its posted.
     *
     * @param eventType The event type.
     * @param function  The function that will be called.
     */
    public static void subscribe(String eventType, Consumer<GLFWEvent> function)
    {
        GLFWEvents.LOGGER.finer("GLFWEvent Subscription for type:", eventType);
        
        GLFWEvents.SUBSCRIPTIONS.computeIfAbsent(eventType, e -> new HashSet<>());
        GLFWEvents.SUBSCRIPTIONS.get(eventType).add(function);
    }
    
    /**
     * Subscribe to an GLFWEventGroup so that a function is called as soon as any event in the group is posted.
     *
     * @param eventGroup The event group.
     * @param function   The function that will be called.
     */
    public static void subscribe(GLFWEventGroup eventGroup, Consumer<GLFWEvent> function)
    {
        for (String eventType : eventGroup.events()) subscribe(eventType, function);
    }
    
    public static void unsubscribe(String eventType, Consumer<GLFWEvent> function)
    {
        GLFWEvents.LOGGER.finer("GLFWEvent Un-subscription for type:", eventType);
    
        GLFWEvents.SUBSCRIPTIONS.computeIfAbsent(eventType, e -> new HashSet<>());
        GLFWEvents.SUBSCRIPTIONS.get(eventType).remove(function);
    }
    
    /**
     * Clears all events that were posted.
     */
    public static void clear()
    {
        GLFWEvents.LOGGER.finest("GLFWEvents Cleared");
        
        GLFWEvents.EVENTS.clear();
    }
    
    static
    {
        register(GLFWEvent.WINDOW_FOCUSED, new String[] {"focused"});
        register(GLFWEvent.WINDOW_FULLSCREEN, new String[] {"fullscreen"});
        register(GLFWEvent.WINDOW_MOVED, new String[] {"pos"});
        register(GLFWEvent.WINDOW_RESIZED, new String[] {"size"});
        register(GLFWEvent.WINDOW_VSYNC, new String[] {"vsync"});
        
        register(GLFWEvent.MOUSE_BUTTON_CLICKED, new String[] {"button", "pos", "doubleClicked"});
        register(GLFWEvent.MOUSE_BUTTON_DOWN, new String[] {"button", "pos"});
        register(GLFWEvent.MOUSE_BUTTON_DRAGGED, new String[] {"button", "dragPos", "pos", "rel"});
        register(GLFWEvent.MOUSE_BUTTON_HELD, new String[] {"button", "pos"});
        register(GLFWEvent.MOUSE_BUTTON_REPEAT, new String[] {"button", "pos"});
        register(GLFWEvent.MOUSE_BUTTON_UP, new String[] {"button", "pos"});
        
        register(GLFWEvent.MOUSE_CAPTURED, new String[] {"captured"});
        register(GLFWEvent.MOUSE_ENTERED, new String[] {"entered"});
        register(GLFWEvent.MOUSE_MOVED, new String[] {"pos", "rel"});
        register(GLFWEvent.MOUSE_SCROLLED, new String[] {"dir"});
        
        register(GLFWEvent.KEYBOARD_KEY_PRESSED, new String[] {"key", "doublePressed"});
        register(GLFWEvent.KEYBOARD_KEY_DOWN, new String[] {"key"});
        register(GLFWEvent.KEYBOARD_KEY_HELD, new String[] {"key"});
        register(GLFWEvent.KEYBOARD_KEY_REPEAT, new String[] {"key"});
        register(GLFWEvent.KEYBOARD_KEY_UP, new String[] {"key"});
        register(GLFWEvent.KEYBOARD_KEY_TYPED, new String[] {"char"});
    
        GLFWEventGroup.WINDOW.add(GLFWEvent.WINDOW_FOCUSED);
        GLFWEventGroup.WINDOW.add(GLFWEvent.WINDOW_FULLSCREEN);
        GLFWEventGroup.WINDOW.add(GLFWEvent.WINDOW_MOVED);
        GLFWEventGroup.WINDOW.add(GLFWEvent.WINDOW_RESIZED);
        GLFWEventGroup.WINDOW.add(GLFWEvent.WINDOW_VSYNC);
        
        GLFWEventGroup.MOUSE_BUTTON.add(GLFWEvent.MOUSE_BUTTON_CLICKED);
        GLFWEventGroup.MOUSE_BUTTON.add(GLFWEvent.MOUSE_BUTTON_DOWN);
        GLFWEventGroup.MOUSE_BUTTON.add(GLFWEvent.MOUSE_BUTTON_DRAGGED);
        GLFWEventGroup.MOUSE_BUTTON.add(GLFWEvent.MOUSE_BUTTON_HELD);
        GLFWEventGroup.MOUSE_BUTTON.add(GLFWEvent.MOUSE_BUTTON_REPEAT);
        GLFWEventGroup.MOUSE_BUTTON.add(GLFWEvent.MOUSE_BUTTON_UP);
        
        GLFWEventGroup.MOUSE.add(GLFWEvent.MOUSE_CAPTURED);
        GLFWEventGroup.MOUSE.add(GLFWEvent.MOUSE_ENTERED);
        GLFWEventGroup.MOUSE.add(GLFWEvent.MOUSE_MOVED);
        GLFWEventGroup.MOUSE.add(GLFWEvent.MOUSE_SCROLLED);
        
        GLFWEventGroup.KEYBOARD_KEY.add(GLFWEvent.KEYBOARD_KEY_PRESSED);
        GLFWEventGroup.KEYBOARD_KEY.add(GLFWEvent.KEYBOARD_KEY_DOWN);
        GLFWEventGroup.KEYBOARD_KEY.add(GLFWEvent.KEYBOARD_KEY_HELD);
        GLFWEventGroup.KEYBOARD_KEY.add(GLFWEvent.KEYBOARD_KEY_REPEAT);
        GLFWEventGroup.KEYBOARD_KEY.add(GLFWEvent.KEYBOARD_KEY_UP);
        GLFWEventGroup.KEYBOARD_KEY.add(GLFWEvent.KEYBOARD_KEY_TYPED);
        
        GLFWEventGroup.MOUSE.add(GLFWEventGroup.MOUSE_BUTTON);
        
        GLFWEventGroup.KEYBOARD.add(GLFWEventGroup.KEYBOARD_KEY);
        
        GLFWEventGroup.INPUT.add(GLFWEventGroup.MOUSE);
        GLFWEventGroup.INPUT.add(GLFWEventGroup.KEYBOARD);
    }
}

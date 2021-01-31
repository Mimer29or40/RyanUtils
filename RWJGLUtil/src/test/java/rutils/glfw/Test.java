package rutils.glfw;

import rutils.Logger;
import rutils.glfw.event.*;

import java.util.logging.Level;

public class Test
{
    private static final Logger LOGGER = new Logger();
    
    static Window window;
    static Window window1;
    
    @SubscribeEvent
    public static void handleEvent(Event event)
    {
        // LOGGER.info("allEvents", event);
    }
    
    @SubscribeEvent
    public static void handleEvent(EventJoystickConnected event)
    {
        // LOGGER.info("EventJoystickConnected", event);
    }
    
    @SubscribeEvent
    public static void handleEvent(EventJoystickDisconnected event)
    {
        // LOGGER.info("EventJoystickDisconnected", event);
    }
    
    @SubscribeEvent
    public static void handleEvent(EventInputDevice event)
    {
        // LOGGER.info("EventInputDevice", event);
    }
    
    @SubscribeEvent
    public static void handleEvent(EventInputDeviceInputDown event)
    {
        LOGGER.info("EventInputDeviceInputDown", event);
    }
    
    @SubscribeEvent
    public static void handleEvent(EventJoystickHat event)
    {
        // LOGGER.info("EventJoystickHat", event);
    }
    
    @SubscribeEvent
    public static void handleEvent(EventGamepadHat event)
    {
        // LOGGER.info("EventGamepadHat", event);
    }
    
    @SubscribeEvent
    public static void handleEvent(EventKeyboardKey event)
    {
        // if (event instanceof EventKeyboardKeyDown)
        // {
        //     switch (event.key())
        //     {
        //         case S -> GLFW.mouse().show(window);
        //         case H -> GLFW.mouse().hide(window);
        //         case C -> GLFW.mouse().capture(window);
        //     }
        // }
    }
    
    public static void main(String[] args)
    {
        Logger.setLevel(Level.ALL);
        Logger.setLevel(Level.FINER);
        
        // TODO - Make an input manager to listen for input events and transform them into game inputs. This is out of scope for this library
        
        try
        {
            Test.LOGGER.fine("Application Starting");
            
            GLFW.init();
            
            GLFW.EVENT_BUS.register(Test.class);
            
            window  = new Window.Builder().name("First").build();
            // window1 = new Window.Builder().name("Second").build();

            GLFW.eventLoop();
        }
        catch (Throwable e)
        {
            Test.LOGGER.severe(e);
        }
        finally
        {
            Test.LOGGER.fine("Application Stopping");
            
            GLFW.destroy();
        }
    }
}

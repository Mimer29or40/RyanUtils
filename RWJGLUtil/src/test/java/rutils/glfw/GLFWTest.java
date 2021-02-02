package rutils.glfw;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
import static org.lwjgl.glfw.GLFW.*;

class GLFWTest
{
    @SuppressWarnings("ResultOfMethodCallIgnored")
    void notInitialized()
    {
        assertThrows(RuntimeException.class, GLFW::monitors);
        assertThrows(RuntimeException.class, () -> GLFW.getMonitor(0));
        assertThrows(RuntimeException.class, GLFW::primaryMonitor);
        assertThrows(RuntimeException.class, GLFW::getTime);
        assertThrows(RuntimeException.class, GLFW::supportRawMouseInput);
        assertThrows(RuntimeException.class, () -> GLFW.loadControllerMapping("path/to/mapping.txt"));
        assertThrows(RuntimeException.class, GLFW::mouse);
        assertThrows(RuntimeException.class, GLFW::keyboard);
        assertThrows(RuntimeException.class, () -> GLFW.getJoystick(0));
        assertThrows(RuntimeException.class, GLFW::getClipboardString);
        assertThrows(RuntimeException.class, () -> GLFW.setClipboardString("String"));
    }
    
    void initialized()
    {
        Collection<Monitor> monitors;
        Monitor             monitor;
        Joystick            joystick;
    
        monitors = GLFW.monitors();
        assertTrue(monitors.size() > 0);
    
        assertThrows(RuntimeException.class, () -> GLFW.getMonitor(-1));
        for (int index = 0; index < monitors.size(); index++)
        {
            monitor = GLFW.getMonitor(index);
            assertNotNull(monitor, "Monitor at index=" + index + " is null");
        }
    
        monitor = GLFW.primaryMonitor();
        assertNotNull(monitor, "Primary Monitor is null");
        assertEquals(monitor, GLFW.getMonitor(0), "Primary Monitor is not first");
    
        assertTrue(GLFW.getTime() > 0);
        
        double newTime = 32.1235;
        GLFW.setTime(newTime);
        assertTrue(GLFW.getTime() > newTime && GLFW.getTime() < 33.1235);
    
        assertDoesNotThrow(GLFW::supportRawMouseInput);
    
        assertTrue(GLFW.loadControllerMapping("game_controller_db.txt"));
    
        assertNotNull(GLFW.mouse());
        assertNotNull(GLFW.keyboard());
    
        assertThrows(RuntimeException.class, () -> GLFW.getJoystick(-1));
        assertThrows(RuntimeException.class, () -> GLFW.getJoystick(GLFW_JOYSTICK_LAST + 1));
        for (int jid = GLFW_JOYSTICK_1; jid < GLFW_JOYSTICK_LAST; jid++)
        {
            joystick = GLFW.getJoystick(jid);
            if (glfwJoystickPresent(jid))
            {
                assertNotNull(joystick, "Joystick at jid=" + jid + " is null");
            }
            else
            {
                assertNull(joystick, "Joystick at jid=" + jid + " is not null");
            }
        }
    
        String clipboardString = "Clipboard String";
        GLFW.setClipboardString(clipboardString);
        assertEquals(clipboardString, GLFW.getClipboardString());
    }
    
    @Test
    void init()
    {
        notInitialized();
        
        GLFW.init();
    
        initialized();
        
        GLFW.destroy();
    
        notInitialized();
    }
}

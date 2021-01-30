package rutils.glfw;

import rutils.Logger;
import rutils.glfw.event.events.*;
import rutils.group.IPair;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard extends InputDevice
{
    private static final Logger LOGGER = new Logger();
    
    // -------------------- Callback Objects -------------------- //
    
    protected final Queue<IPair<Window, String>> _charChanges = new ConcurrentLinkedQueue<>();
    
    // -------------------- Internal Objects -------------------- //
    
    final Map<Key, KeyInput> keyMap;
    
    Keyboard()
    {
        super("Keyboard");
    
        synchronized (this.keyMap = new LinkedHashMap<>())
        {
            for (Keyboard.Key key : Key.values()) this.keyMap.put(key, new KeyInput(GLFW_RELEASE));
        }
    }
    
    @Override
    public String toString()
    {
        return "Mouse{" + '}';
    }
    
    /**
     * Sets the sticky keys flag. If sticky mouse buttons are enabled, a mouse
     * button press will ensure that {@link EventKeyboardKeyPressed} is posted
     * even if the mouse button had been released before the call. This is
     * useful when you are only interested in whether mouse buttons have been
     * pressed but not when or in which order.
     *
     * @param sticky {@code true} to enable sticky mode, otherwise {@code false}.
     */
    public void sticky(Window window, boolean sticky)
    {
        GLFW.TASK_DELEGATOR.runTask(() -> glfwSetInputMode(window.handle, GLFW_STICKY_KEYS, sticky ? GLFW_TRUE : GLFW_FALSE));
    }
    
    /**
     * @return Retrieves the sticky keys flag.
     */
    @SuppressWarnings("ConstantConditions")
    public boolean stickyEnabled(Window window)
    {
        return GLFW.TASK_DELEGATOR.waitReturnTask(() -> glfwGetInputMode(window.handle, GLFW_STICKY_KEYS) == GLFW_TRUE);
    }
    
    /**
     * This method is called by the window it is attached to. This is where
     * events should be posted to when something has changed.
     *
     * @param time  The system time in nanoseconds.
     * @param delta The time in nanoseconds since the last time this method was called.
     */
    @Override
    protected void postEvents(long time, long delta)
    {
        IPair<Window, String> charChange;
        while ((charChange = this._charChanges.poll()) != null)
        {
            GLFW.EVENT_BUS.post(EventKeyboardTyped.create(charChange.getA(), charChange.getB()));
        }
    
        synchronized (this.keyMap)
        {
            for (Key key : this.keyMap.keySet())
            {
                KeyInput keyObj = this.keyMap.get(key);
            
                if (keyObj._state != keyObj.state)
                {
                    if (keyObj._state == GLFW_PRESS)
                    {
                        keyObj.held       = true;
                        keyObj.holdTime   = time + InputDevice.holdFrequency;
                        keyObj.repeatTime = time + InputDevice.repeatDelay;
                        GLFW.EVENT_BUS.post(EventKeyboardKeyDown.create(keyObj._window, key));
                    }
                    else if (keyObj._state == GLFW_RELEASE)
                    {
                        keyObj.held       = false;
                        keyObj.holdTime   = Long.MAX_VALUE;
                        keyObj.repeatTime = Long.MAX_VALUE;
                        GLFW.EVENT_BUS.post(EventKeyboardKeyUp.create(keyObj._window, key));
                    
                        if (time - keyObj.pressTime < InputDevice.doublePressedDelay)
                        {
                            keyObj.pressTime = 0;
                            GLFW.EVENT_BUS.post(EventKeyboardKeyPressed.create(keyObj._window, key, true));
                        }
                        else
                        {
                            keyObj.pressTime = time;
                            GLFW.EVENT_BUS.post(EventKeyboardKeyPressed.create(keyObj._window, key, false));
                        }
                    }
                    keyObj.state = keyObj._state;
                }
                if (keyObj.held && time - keyObj.holdTime >= InputDevice.holdFrequency)
                {
                    keyObj.holdTime += InputDevice.holdFrequency;
                    GLFW.EVENT_BUS.post(EventKeyboardKeyHeld.create(keyObj._window, key));
                }
                if (time - keyObj.repeatTime > InputDevice.repeatFrequency)
                {
                    keyObj.repeatTime += InputDevice.repeatFrequency;
                    GLFW.EVENT_BUS.post(EventKeyboardKeyRepeated.create(keyObj._window, key));
                }
            }
        }
    }
    
    static final class KeyInput extends Input
    {
        private KeyInput(int initial)
        {
            super(initial);
        }
    }
    
    public enum Key
    {
        NONE(GLFW_KEY_UNKNOWN, -1, -1),
        
        A(GLFW_KEY_A, 'a', 'A'),
        B(GLFW_KEY_B, 'b', 'B'),
        C(GLFW_KEY_C, 'c', 'C'),
        D(GLFW_KEY_D, 'd', 'D'),
        E(GLFW_KEY_E, 'e', 'E'),
        F(GLFW_KEY_F, 'f', 'F'),
        G(GLFW_KEY_G, 'g', 'G'),
        H(GLFW_KEY_H, 'h', 'H'),
        I(GLFW_KEY_I, 'i', 'I'),
        J(GLFW_KEY_J, 'j', 'J'),
        K(GLFW_KEY_K, 'k', 'K'),
        L(GLFW_KEY_L, 'l', 'L'),
        M(GLFW_KEY_M, 'm', 'M'),
        N(GLFW_KEY_N, 'n', 'N'),
        O(GLFW_KEY_O, 'o', 'O'),
        P(GLFW_KEY_P, 'p', 'P'),
        Q(GLFW_KEY_Q, 'q', 'Q'),
        R(GLFW_KEY_R, 'r', 'R'),
        S(GLFW_KEY_S, 's', 'S'),
        T(GLFW_KEY_T, 't', 'T'),
        U(GLFW_KEY_U, 'u', 'U'),
        V(GLFW_KEY_V, 'v', 'V'),
        W(GLFW_KEY_W, 'w', 'W'),
        X(GLFW_KEY_X, 'x', 'X'),
        Y(GLFW_KEY_Y, 'y', 'Y'),
        Z(GLFW_KEY_Z, 'z', 'Z'),
        
        K1(GLFW_KEY_1, '1', '!'),
        K2(GLFW_KEY_2, '2', '@'),
        K3(GLFW_KEY_3, '3', '#'),
        K4(GLFW_KEY_4, '4', '$'),
        K5(GLFW_KEY_5, '5', '%'),
        K6(GLFW_KEY_6, '6', '^'),
        K7(GLFW_KEY_7, '7', '&'),
        K8(GLFW_KEY_8, '8', '*'),
        K9(GLFW_KEY_9, '9', '('),
        K0(GLFW_KEY_0, '0', ')'),
        
        GRAVE(GLFW_KEY_GRAVE_ACCENT, '`', '~'),
        MINUS(GLFW_KEY_MINUS, '-', '_'),
        EQUALS(GLFW_KEY_EQUAL, '=', '+'),
        L_BRACKET(GLFW_KEY_LEFT_BRACKET, '[', '{'),
        R_BRACKET(GLFW_KEY_RIGHT_BRACKET, ']', '}'),
        BACKSLASH(GLFW_KEY_BACKSLASH, '\\', '|'),
        SEMICOLON(GLFW_KEY_SEMICOLON, ';', ':'),
        APOSTROPHE(GLFW_KEY_APOSTROPHE, '\'', '"'),
        COMMA(GLFW_KEY_COMMA, ',', '<'),
        PERIOD(GLFW_KEY_PERIOD, '.', '>'),
        SLASH(GLFW_KEY_SLASH, '/', '?'),
        
        F1(GLFW_KEY_F1, -1, -1),
        F2(GLFW_KEY_F2, -1, -1),
        F3(GLFW_KEY_F3, -1, -1),
        F4(GLFW_KEY_F4, -1, -1),
        F5(GLFW_KEY_F5, -1, -1),
        F6(GLFW_KEY_F6, -1, -1),
        F7(GLFW_KEY_F7, -1, -1),
        F8(GLFW_KEY_F8, -1, -1),
        F9(GLFW_KEY_F9, -1, -1),
        F10(GLFW_KEY_F10, -1, -1),
        F11(GLFW_KEY_F11, -1, -1),
        F12(GLFW_KEY_F12, -1, -1),
        
        UP(GLFW_KEY_UP, -1, -1),
        DOWN(GLFW_KEY_DOWN, -1, -1),
        LEFT(GLFW_KEY_LEFT, -1, -1),
        RIGHT(GLFW_KEY_RIGHT, -1, -1),
        
        TAB(GLFW_KEY_TAB, '\t', '\t'),
        CAPS_LOCK(GLFW_KEY_CAPS_LOCK, -1, -1),
        ENTER(GLFW_KEY_ENTER, '\n', '\n'),
        BACK(GLFW_KEY_BACKSPACE, '\b', '\b'),
        SPACE(GLFW_KEY_SPACE, ' ', ' '),
        
        L_SHIFT(GLFW_KEY_LEFT_SHIFT, -1, -1),
        R_SHIFT(GLFW_KEY_RIGHT_SHIFT, -1, -1),
        L_CTRL(GLFW_KEY_LEFT_CONTROL, -1, -1),
        R_CTRL(GLFW_KEY_RIGHT_CONTROL, -1, -1),
        L_ALT(GLFW_KEY_LEFT_ALT, -1, -1),
        R_ALT(GLFW_KEY_RIGHT_ALT, -1, -1),
        L_SUPER(GLFW_KEY_LEFT_SUPER, -1, -1),
        R_SUPER(GLFW_KEY_RIGHT_SUPER, -1, -1),
        
        MENU(GLFW_KEY_MENU, -1, -1),
        ESCAPE(GLFW_KEY_ESCAPE, -1, -1),
        PRINT_SCREEN(GLFW_KEY_PRINT_SCREEN, -1, -1),
        SCROLL_LOCK(GLFW_KEY_SCROLL_LOCK, -1, -1),
        PAUSE(GLFW_KEY_PAUSE, -1, -1),
        INS(GLFW_KEY_INSERT, -1, -1),
        DEL(GLFW_KEY_DELETE, -1, -1),
        HOME(GLFW_KEY_HOME, -1, -1),
        END(GLFW_KEY_END, -1, -1),
        PAGE_UP(GLFW_KEY_PAGE_UP, -1, -1),
        PAGE_DN(GLFW_KEY_PAGE_DOWN, -1, -1),
        
        NP0(GLFW_KEY_KP_0, '0', '0'),
        NP1(GLFW_KEY_KP_1, '1', '1'),
        NP2(GLFW_KEY_KP_2, '2', '2'),
        NP3(GLFW_KEY_KP_3, '3', '3'),
        NP4(GLFW_KEY_KP_4, '4', '4'),
        NP5(GLFW_KEY_KP_5, '5', '5'),
        NP6(GLFW_KEY_KP_6, '6', '6'),
        NP7(GLFW_KEY_KP_7, '7', '7'),
        NP8(GLFW_KEY_KP_8, '8', '8'),
        NP9(GLFW_KEY_KP_9, '9', '9'),
        
        NUM_LOCK(GLFW_KEY_NUM_LOCK, -1, -1),
        NP_DIV(GLFW_KEY_KP_DIVIDE, '/', '/'),
        NP_MUL(GLFW_KEY_KP_MULTIPLY, '*', '*'),
        NP_SUB(GLFW_KEY_KP_SUBTRACT, '-', '-'),
        NP_ADD(GLFW_KEY_KP_ADD, '+', '+'),
        NP_DECIMAL(GLFW_KEY_KP_DECIMAL, '.', '.'),
        NP_EQUALS(GLFW_KEY_KP_EQUAL, '=', '='),
        NP_ENTER(GLFW_KEY_KP_ENTER, '\n', '\n'),
        
        KEY_WORLD_1(GLFW_KEY_WORLD_1, -1, -1),
        KEY_WORLD_2(GLFW_KEY_WORLD_2, -1, -1),
        
        ;
        
        private static final HashMap<Integer, Key> KEY_MAP = new HashMap<>();
        
        final int ref;
        
        final int  scancode;
        final char baseChar, shiftChar;
        
        Key(int ref, int baseChar, int shiftChar)
        {
            this.ref       = ref;
            this.scancode  = ref >= 0 ? glfwGetKeyScancode(ref) : -1;
            this.baseChar  = (char) baseChar;
            this.shiftChar = (char) shiftChar;
        }
        
        /**
         * @return Gets the ButtonInput that corresponds to the GLFW constant.
         */
        public static Key get(int ref)
        {
            return Key.KEY_MAP.getOrDefault(ref, Key.NONE);
        }
        
        static
        {
            for (Key key : values())
            {
                Key.KEY_MAP.put(key.ref, key);
            }
        }
    }
}

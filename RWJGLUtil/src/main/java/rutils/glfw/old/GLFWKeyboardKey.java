package rutils.glfw.old;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;

public enum GLFWKeyboardKey implements GLFWInput
{
    NONE(GLFW_KEY_UNKNOWN, 0, 0),
    
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
    
    F1(GLFW_KEY_F1, 0, 0),
    F2(GLFW_KEY_F2, 0, 0),
    F3(GLFW_KEY_F3, 0, 0),
    F4(GLFW_KEY_F4, 0, 0),
    F5(GLFW_KEY_F5, 0, 0),
    F6(GLFW_KEY_F6, 0, 0),
    F7(GLFW_KEY_F7, 0, 0),
    F8(GLFW_KEY_F8, 0, 0),
    F9(GLFW_KEY_F9, 0, 0),
    F10(GLFW_KEY_F10, 0, 0),
    F11(GLFW_KEY_F11, 0, 0),
    F12(GLFW_KEY_F12, 0, 0),
    
    UP(GLFW_KEY_UP, 0, 0),
    DOWN(GLFW_KEY_DOWN, 0, 0),
    LEFT(GLFW_KEY_LEFT, 0, 0),
    RIGHT(GLFW_KEY_RIGHT, 0, 0),
    
    TAB(GLFW_KEY_TAB, '\t', '\t'),
    CAPS_LOCK(GLFW_KEY_CAPS_LOCK, 0, 0),
    ENTER(GLFW_KEY_ENTER, '\n', '\n'),
    BACK(GLFW_KEY_BACKSPACE, '\b', '\b'),
    SPACE(GLFW_KEY_SPACE, ' ', ' '),
    
    L_SHIFT(GLFW_KEY_LEFT_SHIFT, 0, 0),
    R_SHIFT(GLFW_KEY_RIGHT_SHIFT, 0, 0),
    L_CTRL(GLFW_KEY_LEFT_CONTROL, 0, 0),
    R_CTRL(GLFW_KEY_RIGHT_CONTROL, 0, 0),
    L_ALT(GLFW_KEY_LEFT_ALT, 0, 0),
    R_ALT(GLFW_KEY_RIGHT_ALT, 0, 0),
    L_SUPER(GLFW_KEY_LEFT_SUPER, 0, 0),
    R_SUPER(GLFW_KEY_RIGHT_SUPER, 0, 0),
    
    MENU(GLFW_KEY_MENU, 0, 0),
    ESCAPE(GLFW_KEY_ESCAPE, 0, 0),
    PRINT_SCREEN(GLFW_KEY_PRINT_SCREEN, 0, 0),
    SCROLL_LOCK(GLFW_KEY_SCROLL_LOCK, 0, 0),
    PAUSE(GLFW_KEY_PAUSE, 0, 0),
    INS(GLFW_KEY_INSERT, 0, 0),
    DEL(GLFW_KEY_DELETE, 0, 0),
    HOME(GLFW_KEY_HOME, 0, 0),
    END(GLFW_KEY_END, 0, 0),
    PAGE_UP(GLFW_KEY_PAGE_UP, 0, 0),
    PAGE_DN(GLFW_KEY_PAGE_DOWN, 0, 0),
    
    NP0(GLFW_KEY_KP_0, '0', 0),
    NP1(GLFW_KEY_KP_1, '1', 0),
    NP2(GLFW_KEY_KP_2, '2', 0),
    NP3(GLFW_KEY_KP_3, '3', 0),
    NP4(GLFW_KEY_KP_4, '4', 0),
    NP5(GLFW_KEY_KP_5, '5', 0),
    NP6(GLFW_KEY_KP_6, '6', 0),
    NP7(GLFW_KEY_KP_7, '7', 0),
    NP8(GLFW_KEY_KP_8, '8', 0),
    NP9(GLFW_KEY_KP_9, '9', 0),
    
    NUM_LOCK(GLFW_KEY_NUM_LOCK, 0, 0),
    NP_DIV(GLFW_KEY_KP_DIVIDE, '/', '/'),
    NP_MUL(GLFW_KEY_KP_MULTIPLY, '*', '*'),
    NP_SUB(GLFW_KEY_KP_SUBTRACT, '-', '-'),
    NP_ADD(GLFW_KEY_KP_ADD, '+', '+'),
    NP_DECIMAL(GLFW_KEY_KP_DECIMAL, '.', '.'),
    NP_EQUALS(GLFW_KEY_KP_EQUAL, '=', '='),
    NP_ENTER(GLFW_KEY_KP_ENTER, '\n', '\n'),
    
    ;
    
    private static final HashMap<Integer, GLFWKeyboardKey> keys = new HashMap<>();
    
    final int reference;
    
    boolean down, up, held, repeat;
    int newState, state, newMods, mods;
    long downTime, pressTime;
    
    final int  scancode;
    final char baseChar, shiftChar;
    
    GLFWKeyboardKey(int reference, int baseChar, int shiftChar)
    {
        this.reference = reference;
        this.scancode  = reference > 0 ? glfwGetKeyScancode(reference) : 0;
        this.baseChar  = (char) baseChar;
        this.shiftChar = (char) shiftChar;
    }
    
    /**
     * @return If the input is in the down state.
     */
    @Override
    public boolean down()
    {
        return this.down;
    }
    
    /**
     * @return If the input is in the up state.
     */
    @Override
    public boolean up()
    {
        return this.up;
    }
    
    /**
     * @return If the input is in the held state.
     */
    @Override
    public boolean held()
    {
        return this.held;
    }
    
    /**
     * @return If the input is in the repeat state.
     */
    @Override
    public boolean repeat()
    {
        return this.repeat;
    }
    
    /**
     * @return The bit map of mods currently affecting this input.
     */
    @Override
    public int mods()
    {
        return this.mods;
    }
    
    /**
     * @return Gets the GLFWInput that corresponds to the GLFW constant.
     */
    public static GLFWKeyboardKey get(int reference)
    {
        return GLFWKeyboardKey.keys.getOrDefault(reference, GLFWKeyboardKey.NONE);
    }
    
    static
    {
        for (GLFWKeyboardKey key : values())
        {
            GLFWKeyboardKey.keys.put(key.reference, key);
        }
    }
}

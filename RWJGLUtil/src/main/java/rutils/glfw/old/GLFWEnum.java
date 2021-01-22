package rutils.glfw.old;

import java.util.HashMap;

import static org.lwjgl.glfw.GLFW.*;

/**
 * All GLFWEnum constants so I can treat them differently than int in methods.
 */
// TODO - need to rethink this as values overlap
@SuppressWarnings({"JavadocReference", "unused"})
public enum GLFWEnum
{
    /**
     * Default value for this enum. Only use
     */
    NULL(-1L),
    
    /**
     * The major version number of the GLFWEnum library. This is incremented when the API is changed in non-compatible ways.
     */
    VERSION_MAJOR(GLFW_VERSION_MAJOR),
    
    /**
     * The minor version number of the GLFWEnum library. This is incremented when features are added to the API but it remains backward-compatible.
     */
    VERSION_MINOR(GLFW_VERSION_MINOR),
    
    /**
     * The revision number of the GLFWEnum library. This is incremented when a bug fix release is made that does not contain any API changes.
     */
    VERSION_REVISION(GLFW_VERSION_REVISION),
    
    /**
     * Boolean values.
     */
    TRUE(GLFW_TRUE),
    FALSE(GLFW_FALSE),
    
    /**
     * The key or button was released.
     */
    RELEASE(GLFW_RELEASE),
    
    /**
     * The key or button was pressed.
     */
    PRESS(GLFW_PRESS),
    
    /**
     * The key was held down until it repeated.
     */
    REPEAT(GLFW_REPEAT),
    
    /**
     * Joystick hat states.
     */
    HAT_CENTERED(GLFW_HAT_CENTERED),
    HAT_UP(GLFW_HAT_UP),
    HAT_RIGHT(GLFW_HAT_RIGHT),
    HAT_DOWN(GLFW_HAT_DOWN),
    HAT_LEFT(GLFW_HAT_LEFT),
    HAT_RIGHT_UP(GLFW_HAT_RIGHT_UP),
    HAT_RIGHT_DOWN(GLFW_HAT_RIGHT_DOWN),
    HAT_LEFT_UP(GLFW_HAT_LEFT_UP),
    HAT_LEFT_DOWN(GLFW_HAT_LEFT_DOWN),
    
    /**
     * The unknown key.
     */
    KEY_UNKNOWN(GLFW_KEY_UNKNOWN),
    
    /**
     * Printable keys.
     */
    KEY_SPACE(GLFW_KEY_SPACE),
    KEY_APOSTROPHE(GLFW_KEY_APOSTROPHE),
    KEY_COMMA(GLFW_KEY_COMMA),
    KEY_MINUS(GLFW_KEY_MINUS),
    KEY_PERIOD(GLFW_KEY_PERIOD),
    KEY_SLASH(GLFW_KEY_SLASH),
    KEY_0(GLFW_KEY_0),
    KEY_1(GLFW_KEY_1),
    KEY_2(GLFW_KEY_2),
    KEY_3(GLFW_KEY_3),
    KEY_4(GLFW_KEY_4),
    KEY_5(GLFW_KEY_5),
    KEY_6(GLFW_KEY_6),
    KEY_7(GLFW_KEY_7),
    KEY_8(GLFW_KEY_8),
    KEY_9(GLFW_KEY_9),
    KEY_SEMICOLON(GLFW_KEY_SEMICOLON),
    KEY_EQUAL(GLFW_KEY_EQUAL),
    KEY_A(GLFW_KEY_A),
    KEY_B(GLFW_KEY_B),
    KEY_C(GLFW_KEY_C),
    KEY_D(GLFW_KEY_D),
    KEY_E(GLFW_KEY_E),
    KEY_F(GLFW_KEY_F),
    KEY_G(GLFW_KEY_G),
    KEY_H(GLFW_KEY_H),
    KEY_I(GLFW_KEY_I),
    KEY_J(GLFW_KEY_J),
    KEY_K(GLFW_KEY_K),
    KEY_L(GLFW_KEY_L),
    KEY_M(GLFW_KEY_M),
    KEY_N(GLFW_KEY_N),
    KEY_O(GLFW_KEY_O),
    KEY_P(GLFW_KEY_P),
    KEY_Q(GLFW_KEY_Q),
    KEY_R(GLFW_KEY_R),
    KEY_S(GLFW_KEY_S),
    KEY_T(GLFW_KEY_T),
    KEY_U(GLFW_KEY_U),
    KEY_V(GLFW_KEY_V),
    KEY_W(GLFW_KEY_W),
    KEY_X(GLFW_KEY_X),
    KEY_Y(GLFW_KEY_Y),
    KEY_Z(GLFW_KEY_Z),
    KEY_LEFT_BRACKET(GLFW_KEY_LEFT_BRACKET),
    KEY_BACKSLASH(GLFW_KEY_BACKSLASH),
    KEY_RIGHT_BRACKET(GLFW_KEY_RIGHT_BRACKET),
    KEY_GRAVE_ACCENT(GLFW_KEY_GRAVE_ACCENT),
    KEY_WORLD_1(GLFW_KEY_WORLD_1),
    KEY_WORLD_2(GLFW_KEY_WORLD_2),
    
    /**
     * Function keys.
     */
    KEY_ESCAPE(GLFW_KEY_ESCAPE),
    KEY_ENTER(GLFW_KEY_ENTER),
    KEY_TAB(GLFW_KEY_TAB),
    KEY_BACKSPACE(GLFW_KEY_BACKSPACE),
    KEY_INSERT(GLFW_KEY_INSERT),
    KEY_DELETE(GLFW_KEY_DELETE),
    KEY_RIGHT(GLFW_KEY_RIGHT),
    KEY_LEFT(GLFW_KEY_LEFT),
    KEY_DOWN(GLFW_KEY_DOWN),
    KEY_UP(GLFW_KEY_UP),
    KEY_PAGE_UP(GLFW_KEY_PAGE_UP),
    KEY_PAGE_DOWN(GLFW_KEY_PAGE_DOWN),
    KEY_HOME(GLFW_KEY_HOME),
    KEY_END(GLFW_KEY_END),
    KEY_CAPS_LOCK(GLFW_KEY_CAPS_LOCK),
    KEY_SCROLL_LOCK(GLFW_KEY_SCROLL_LOCK),
    KEY_NUM_LOCK(GLFW_KEY_NUM_LOCK),
    KEY_PRINT_SCREEN(GLFW_KEY_PRINT_SCREEN),
    KEY_PAUSE(GLFW_KEY_PAUSE),
    KEY_F1(GLFW_KEY_F1),
    KEY_F2(GLFW_KEY_F2),
    KEY_F3(GLFW_KEY_F3),
    KEY_F4(GLFW_KEY_F4),
    KEY_F5(GLFW_KEY_F5),
    KEY_F6(GLFW_KEY_F6),
    KEY_F7(GLFW_KEY_F7),
    KEY_F8(GLFW_KEY_F8),
    KEY_F9(GLFW_KEY_F9),
    KEY_F10(GLFW_KEY_F10),
    KEY_F11(GLFW_KEY_F11),
    KEY_F12(GLFW_KEY_F12),
    KEY_F13(GLFW_KEY_F13),
    KEY_F14(GLFW_KEY_F14),
    KEY_F15(GLFW_KEY_F15),
    KEY_F16(GLFW_KEY_F16),
    KEY_F17(GLFW_KEY_F17),
    KEY_F18(GLFW_KEY_F18),
    KEY_F19(GLFW_KEY_F19),
    KEY_F20(GLFW_KEY_F20),
    KEY_F21(GLFW_KEY_F21),
    KEY_F22(GLFW_KEY_F22),
    KEY_F23(GLFW_KEY_F23),
    KEY_F24(GLFW_KEY_F24),
    KEY_F25(GLFW_KEY_F25),
    KEY_KP_0(GLFW_KEY_KP_0),
    KEY_KP_1(GLFW_KEY_KP_1),
    KEY_KP_2(GLFW_KEY_KP_2),
    KEY_KP_3(GLFW_KEY_KP_3),
    KEY_KP_4(GLFW_KEY_KP_4),
    KEY_KP_5(GLFW_KEY_KP_5),
    KEY_KP_6(GLFW_KEY_KP_6),
    KEY_KP_7(GLFW_KEY_KP_7),
    KEY_KP_8(GLFW_KEY_KP_8),
    KEY_KP_9(GLFW_KEY_KP_9),
    KEY_KP_DECIMAL(GLFW_KEY_KP_DECIMAL),
    KEY_KP_DIVIDE(GLFW_KEY_KP_DIVIDE),
    KEY_KP_MULTIPLY(GLFW_KEY_KP_MULTIPLY),
    KEY_KP_SUBTRACT(GLFW_KEY_KP_SUBTRACT),
    KEY_KP_ADD(GLFW_KEY_KP_ADD),
    KEY_KP_ENTER(GLFW_KEY_KP_ENTER),
    KEY_KP_EQUAL(GLFW_KEY_KP_EQUAL),
    KEY_LEFT_SHIFT(GLFW_KEY_LEFT_SHIFT),
    KEY_LEFT_CONTROL(GLFW_KEY_LEFT_CONTROL),
    KEY_LEFT_ALT(GLFW_KEY_LEFT_ALT),
    KEY_LEFT_SUPER(GLFW_KEY_LEFT_SUPER),
    KEY_RIGHT_SHIFT(GLFW_KEY_RIGHT_SHIFT),
    KEY_RIGHT_CONTROL(GLFW_KEY_RIGHT_CONTROL),
    KEY_RIGHT_ALT(GLFW_KEY_RIGHT_ALT),
    KEY_RIGHT_SUPER(GLFW_KEY_RIGHT_SUPER),
    KEY_MENU(GLFW_KEY_MENU),
    KEY_LAST(GLFW_KEY_LAST),
    
    /**
     * If this bit is set one or more Shift keys were held down.
     */
    MOD_SHIFT(GLFW_MOD_SHIFT),
    
    /**
     * If this bit is set one or more Control keys were held down.
     */
    MOD_CONTROL(GLFW_MOD_CONTROL),
    
    /**
     * If this bit is set one or more Alt keys were held down.
     */
    MOD_ALT(GLFW_MOD_ALT),
    
    /**
     * If this bit is set one or more Super keys were held down.
     */
    MOD_SUPER(GLFW_MOD_SUPER),
    
    /**
     * If this bit is set the Caps Lock key is enabled and the {@link #GLFW_LOCK_KEY_MODS LOCK_KEY_MODS} input mode is set.
     */
    MOD_CAPS_LOCK(GLFW_MOD_CAPS_LOCK),
    
    /**
     * If this bit is set the Num Lock key is enabled and the {@link #GLFW_LOCK_KEY_MODS LOCK_KEY_MODS} input mode is set.
     */
    MOD_NUM_LOCK(GLFW_MOD_NUM_LOCK),
    
    /**
     * GLFWMouse buttons. See <a target="_blank" href="http://www.glfw.org/docs/latest/input.html#input_mouse_button">mouse button input</a> for how these are used.
     */
    MOUSE_BUTTON_1(GLFW_MOUSE_BUTTON_1),
    MOUSE_BUTTON_2(GLFW_MOUSE_BUTTON_2),
    MOUSE_BUTTON_3(GLFW_MOUSE_BUTTON_3),
    MOUSE_BUTTON_4(GLFW_MOUSE_BUTTON_4),
    MOUSE_BUTTON_5(GLFW_MOUSE_BUTTON_5),
    MOUSE_BUTTON_6(GLFW_MOUSE_BUTTON_6),
    MOUSE_BUTTON_7(GLFW_MOUSE_BUTTON_7),
    MOUSE_BUTTON_8(GLFW_MOUSE_BUTTON_8),
    MOUSE_BUTTON_LAST(GLFW_MOUSE_BUTTON_LAST),
    MOUSE_BUTTON_LEFT(GLFW_MOUSE_BUTTON_LEFT),
    MOUSE_BUTTON_RIGHT(GLFW_MOUSE_BUTTON_RIGHT),
    MOUSE_BUTTON_MIDDLE(GLFW_MOUSE_BUTTON_MIDDLE),
    
    /**
     * Joysticks. See <a target="_blank" href="http://www.glfw.org/docs/latest/input.html#joystick">joystick input</a> for how these are used.
     */
    JOYSTICK_1(GLFW_JOYSTICK_1),
    JOYSTICK_2(GLFW_JOYSTICK_2),
    JOYSTICK_3(GLFW_JOYSTICK_3),
    JOYSTICK_4(GLFW_JOYSTICK_4),
    JOYSTICK_5(GLFW_JOYSTICK_5),
    JOYSTICK_6(GLFW_JOYSTICK_6),
    JOYSTICK_7(GLFW_JOYSTICK_7),
    JOYSTICK_8(GLFW_JOYSTICK_8),
    JOYSTICK_9(GLFW_JOYSTICK_9),
    JOYSTICK_10(GLFW_JOYSTICK_10),
    JOYSTICK_11(GLFW_JOYSTICK_11),
    JOYSTICK_12(GLFW_JOYSTICK_12),
    JOYSTICK_13(GLFW_JOYSTICK_13),
    JOYSTICK_14(GLFW_JOYSTICK_14),
    JOYSTICK_15(GLFW_JOYSTICK_15),
    JOYSTICK_16(GLFW_JOYSTICK_16),
    JOYSTICK_LAST(GLFW_JOYSTICK_LAST),
    
    /**
     * Gamepad buttons. See <a target="_blank" href="http://www.glfw.org/docs/latest/input.html#gamepad">gamepad</a> for how these are used.
     */
    GAMEPAD_BUTTON_A(GLFW_GAMEPAD_BUTTON_A),
    GAMEPAD_BUTTON_B(GLFW_GAMEPAD_BUTTON_B),
    GAMEPAD_BUTTON_X(GLFW_GAMEPAD_BUTTON_X),
    GAMEPAD_BUTTON_Y(GLFW_GAMEPAD_BUTTON_Y),
    GAMEPAD_BUTTON_LEFT_BUMPER(GLFW_GAMEPAD_BUTTON_LEFT_BUMPER),
    GAMEPAD_BUTTON_RIGHT_BUMPER(GLFW_GAMEPAD_BUTTON_RIGHT_BUMPER),
    GAMEPAD_BUTTON_BACK(GLFW_GAMEPAD_BUTTON_BACK),
    GAMEPAD_BUTTON_START(GLFW_GAMEPAD_BUTTON_START),
    GAMEPAD_BUTTON_GUIDE(GLFW_GAMEPAD_BUTTON_GUIDE),
    GAMEPAD_BUTTON_LEFT_THUMB(GLFW_GAMEPAD_BUTTON_LEFT_THUMB),
    GAMEPAD_BUTTON_RIGHT_THUMB(GLFW_GAMEPAD_BUTTON_RIGHT_THUMB),
    GAMEPAD_BUTTON_DPAD_UP(GLFW_GAMEPAD_BUTTON_DPAD_UP),
    GAMEPAD_BUTTON_DPAD_RIGHT(GLFW_GAMEPAD_BUTTON_DPAD_RIGHT),
    GAMEPAD_BUTTON_DPAD_DOWN(GLFW_GAMEPAD_BUTTON_DPAD_DOWN),
    GAMEPAD_BUTTON_DPAD_LEFT(GLFW_GAMEPAD_BUTTON_DPAD_LEFT),
    GAMEPAD_BUTTON_LAST(GLFW_GAMEPAD_BUTTON_LAST),
    GAMEPAD_BUTTON_CROSS(GLFW_GAMEPAD_BUTTON_CROSS),
    GAMEPAD_BUTTON_CIRCLE(GLFW_GAMEPAD_BUTTON_CIRCLE),
    GAMEPAD_BUTTON_SQUARE(GLFW_GAMEPAD_BUTTON_SQUARE),
    GAMEPAD_BUTTON_TRIANGLE(GLFW_GAMEPAD_BUTTON_TRIANGLE),
    
    /**
     * Gamepad axes. See <a target="_blank" href="http://www.glfw.org/docs/latest/input.html#gamepad">gamepad</a> for how these are used.
     */
    GAMEPAD_AXIS_LEFT_X(GLFW_GAMEPAD_AXIS_LEFT_X),
    GAMEPAD_AXIS_LEFT_Y(GLFW_GAMEPAD_AXIS_LEFT_Y),
    GAMEPAD_AXIS_RIGHT_X(GLFW_GAMEPAD_AXIS_RIGHT_X),
    GAMEPAD_AXIS_RIGHT_Y(GLFW_GAMEPAD_AXIS_RIGHT_Y),
    GAMEPAD_AXIS_LEFT_TRIGGER(GLFW_GAMEPAD_AXIS_LEFT_TRIGGER),
    GAMEPAD_AXIS_RIGHT_TRIGGER(GLFW_GAMEPAD_AXIS_RIGHT_TRIGGER),
    GAMEPAD_AXIS_LAST(GLFW_GAMEPAD_AXIS_LAST),
    
    /**
     * Error codes.
     *
     * <h5>Enum values:</h5>
     *
     * <ul>
     * <li>{@link #GLFW_NO_ERROR NO_ERROR} - No error has occurred.</li>
     * <li>{@link #GLFW_NOT_INITIALIZED NOT_INITIALIZED} -
     * GLFWEnum has not been initialized.
     *
     * <p>This occurs if a GLFWEnum function was called that may not be called unless the library is initialized.</p>
     * </li>
     * <li>{@link #GLFW_NO_CURRENT_CONTEXT NO_CURRENT_CONTEXT} -
     * No context is current for this thread.
     *
     * <p>This occurs if a GLFWEnum function was called that needs and operates on the current OpenGL or OpenGL ES context but no context is current on the
     * calling thread. One such function is {@link #glfwSwapInterval SwapInterval}.</p>
     * </li>
     * <li>{@link #GLFW_INVALID_ENUM INVALID_ENUM} -
     * One of the arguments to the function was an invalid enum value.
     *
     * <p>One of the arguments to the function was an invalid enum value, for example requesting {@link #GLFW_RED_BITS RED_BITS} with {@link #glfwGetWindowAttrib GetWindowAttrib}.</p>
     * </li>
     * <li>{@link #GLFW_INVALID_VALUE INVALID_VALUE} -
     * One of the arguments to the function was an invalid value.
     *
     * <p>One of the arguments to the function was an invalid value, for example requesting a non-existent OpenGL or OpenGL ES version like 2.7.</p>
     *
     * <p>Requesting a valid but unavailable OpenGL or OpenGL ES version will instead result in a {@link #GLFW_VERSION_UNAVAILABLE VERSION_UNAVAILABLE} error.</p>
     * </li>
     * <li>{@link #GLFW_OUT_OF_MEMORY OUT_OF_MEMORY} -
     * A memory allocation failed.
     *
     * <p>A bug in GLFWEnum or the underlying operating system. Report the bug to our <a target="_blank" href="https://github.com/glfw/glfw/issues">issue tracker</a>.</p>
     * </li>
     * <li>{@link #GLFW_API_UNAVAILABLE API_UNAVAILABLE} -
     * GLFWEnum could not find support for the requested API on the system.
     *
     * <p>The installed graphics driver does not support the requested API, or does not support it via the chosen context creation backend. Below are a few
     * examples:</p>
     *
     * <p>Some pre-installed Windows graphics drivers do not support OpenGL. AMD only supports OpenGL ES via EGL, while Nvidia and Intel only support it via
     * a WGL or GLX extension. macOS does not provide OpenGL ES at all. The Mesa EGL, OpenGL and OpenGL ES libraries do not interface with the Nvidia
     * binary driver. Older graphics drivers do not support Vulkan.</p>
     * </li>
     * <li>{@link #GLFW_VERSION_UNAVAILABLE VERSION_UNAVAILABLE} -
     * The requested OpenGL or OpenGL ES version (including any requested context or framebuffer hints) is not available on this machine.
     *
     * <p>The machine does not support your requirements. If your application is sufficiently flexible, downgrade your requirements and try again. Otherwise,
     * inform the user that their machine does not match your requirements.</p>
     *
     * <p>Future invalid OpenGL and OpenGL ES versions, for example OpenGL 4.8 if 5.0 comes out before the 4.x series gets that far, also fail with this
     * error and not {@link #GLFW_INVALID_VALUE INVALID_VALUE}, because GLFWEnum cannot know what future versions will exist.</p>
     * </li>
     * <li>{@link #GLFW_PLATFORM_ERROR PLATFORM_ERROR} -
     * A platform-specific error occurred that does not match any of the more specific categories.
     *
     * <p>A bug or configuration error in GLFWEnum, the underlying operating system or its drivers, or a lack of required resources. Report the issue to our
     * <a target="_blank" href="https://github.com/glfw/glfw/issues">issue tracker</a>.</p>
     * </li>
     * <li>{@link #GLFW_FORMAT_UNAVAILABLE FORMAT_UNAVAILABLE} -
     * The requested format is not supported or available.
     *
     * <p>If emitted during window creation, one or more hard constraints did not match any of the available pixel formats. If your application is
     * sufficiently flexible, downgrade your requirements and try again. Otherwise, inform the user that their machine does not match your requirements.</p>
     *
     * <p>If emitted when querying the clipboard, ignore the error or report it to the user, as appropriate.</p>
     * </li>
     * <li>{@link #GLFW_NO_WINDOW_CONTEXT NO_WINDOW_CONTEXT} -
     * The specified window does not have an OpenGL or OpenGL ES context.
     *
     * <p>A window that does not have an OpenGL or OpenGL ES context was passed to a function that requires it to have one.</p>
     *
     * <p>Application programmer error. Fix the offending call.</p>
     * </li>
     * </ul>
     */
    NO_ERROR(GLFW_NO_ERROR),
    NOT_INITIALIZED(GLFW_NOT_INITIALIZED),
    NO_CURRENT_CONTEXT(GLFW_NO_CURRENT_CONTEXT),
    INVALID_ENUM(GLFW_INVALID_ENUM),
    INVALID_VALUE(GLFW_INVALID_VALUE),
    OUT_OF_MEMORY(GLFW_OUT_OF_MEMORY),
    API_UNAVAILABLE(GLFW_API_UNAVAILABLE),
    VERSION_UNAVAILABLE(GLFW_VERSION_UNAVAILABLE),
    PLATFORM_ERROR(GLFW_PLATFORM_ERROR),
    FORMAT_UNAVAILABLE(GLFW_FORMAT_UNAVAILABLE),
    NO_WINDOW_CONTEXT(GLFW_NO_WINDOW_CONTEXT),
    
    /**
     * GLFWWindow attributes.
     *
     * <h5>Enum values:</h5>
     *
     * <ul>
     * <li>{@link #GLFW_FOCUSED FOCUSED} -
     * {@code WindowHint}: Specifies whether the windowed mode window will be given input focus when created. This hint is ignored for full screen and
     * initially hidden windows.
     *
     * <p>{@code GetWindowAttrib}: Indicates whether the specified window has input focus.</p>
     * </li>
     * <li>{@link #GLFW_ICONIFIED ICONIFIED} - {@code GetWindowAttrib}: Indicates whether the specified window is iconified, whether by the user or with {@link #glfwIconifyWindow IconifyWindow}.</li>
     * <li>{@link #GLFW_RESIZABLE RESIZABLE} -
     * {@code WindowHint}: Specifies whether the windowed mode window will be resizable <i>by the user</i>. The window will still be resizable using the
     * {@link #glfwSetWindowSize SetWindowSize} function. This hint is ignored for full screen windows.
     *
     * <p>{@code GetWindowAttrib}: Indicates whether the specified window is resizable <i>by the user</i>.</p>
     * </li>
     * <li>{@link #GLFW_VISIBLE VISIBLE} -
     * {@code WindowHint}: Specifies whether the windowed mode window will be initially visible. This hint is ignored for full screen windows. Windows created
     * hidden are completely invisible to the user until shown. This can be useful if you need to set up your window further before showing it, for
     * example moving it to a specific location.
     *
     * <p>{@code GetWindowAttrib}: Indicates whether the specified window is visible. GLFWWindow visibility can be controlled with {@link #glfwShowWindow ShowWindow} and {@link #glfwHideWindow HideWindow}.</p>
     * </li>
     * <li>{@link #GLFW_DECORATED DECORATED} -
     * {@code WindowHint}: Specifies whether the windowed mode window will have window decorations such as a border, a close widget, etc. An undecorated window
     * may still allow the user to generate close events on some platforms. This hint is ignored for full screen windows.
     *
     * <p>{@code GetWindowAttrib}: Indicates whether the specified window has decorations such as a border, a close widget, etc.</p>
     * </li>
     * <li>{@link #GLFW_AUTO_ICONIFY AUTO_ICONIFY} -
     * {@code WindowHint}: Specifies whether the full screen window will automatically iconify and restore the previous video mode on input focus loss. This
     * hint is ignored for windowed mode windows.
     * </li>
     * <li>{@link #GLFW_FLOATING FLOATING} -
     * {@code WindowHint}: Specifies whether the windowed mode window will be floating above other regular windows, also called topmost or always-on-top. This
     * is intended primarily for debugging purposes and cannot be used to implement proper full screen windows. This hint is ignored for full screen
     * windows.
     *
     * <p>{@code GetWindowAttrib}: Indicates whether the specified window is floating, also called topmost or always-on-top.</p>
     * </li>
     * <li>{@link #GLFW_MAXIMIZED MAXIMIZED} -
     * {@code WindowHint}: Specifies whether the windowed mode window will be maximized when created. This hint is ignored for full screen windows.
     *
     * <p>{@code GetWindowAttrib}: Indicates whether the specified window is maximized, whether by the user or {@link #glfwMaximizeWindow MaximizeWindow}.</p>
     * </li>
     * <li>{@link #GLFW_CENTER_CURSOR CENTER_CURSOR} -
     * {@code WindowHint}: Specifies whether the cursor should be centered over newly created full screen windows. This hint is ignored for windowed mode
     * windows.
     * </li>
     * <li>{@link #GLFW_TRANSPARENT_FRAMEBUFFER TRANSPARENT_FRAMEBUFFER} -
     * {@code WindowHint}: specifies whether the window framebuffer will be transparent. If enabled and supported by the system, the window framebuffer
     * alpha channel will be used to combine the framebuffer with the background. This does not affect window decorations.
     * </li>
     * <li>{@link #GLFW_HOVERED HOVERED} - {@code GetWindowAttrib}: indicates whether the cursor is currently directly over the content area of the window, with no other windows between.</li>
     * <li>{@link #GLFW_FOCUS_ON_SHOW FOCUS_ON_SHOW} -
     * {@code WindowHint}: specifies whether input focuses on calling show window.
     *
     * <p>{@code GetWindowAttrib}: Indicates whether input focuses on calling show window.</p>
     * </li>
     * </ul>
     */
    FOCUSED(GLFW_FOCUSED),
    ICONIFIED(GLFW_ICONIFIED),
    RESIZABLE(GLFW_RESIZABLE),
    VISIBLE(GLFW_VISIBLE),
    DECORATED(GLFW_DECORATED),
    AUTO_ICONIFY(GLFW_AUTO_ICONIFY),
    FLOATING(GLFW_FLOATING),
    MAXIMIZED(GLFW_MAXIMIZED),
    CENTER_CURSOR(GLFW_CENTER_CURSOR),
    TRANSPARENT_FRAMEBUFFER(GLFW_TRANSPARENT_FRAMEBUFFER),
    HOVERED(GLFW_HOVERED),
    FOCUS_ON_SHOW(GLFW_FOCUS_ON_SHOW),
    
    /**
     * Input options.
     */
    CURSOR(GLFW_CURSOR),
    STICKY_KEYS(GLFW_STICKY_KEYS),
    STICKY_MOUSE_BUTTONS(GLFW_STICKY_MOUSE_BUTTONS),
    LOCK_KEY_MODS(GLFW_LOCK_KEY_MODS),
    RAW_MOUSE_MOTION(GLFW_RAW_MOUSE_MOTION),
    
    /**
     * Cursor state.
     */
    CURSOR_NORMAL(GLFW_CURSOR_NORMAL),
    CURSOR_HIDDEN(GLFW_CURSOR_HIDDEN),
    CURSOR_DISABLED(GLFW_CURSOR_DISABLED),
    
    /**
     * Standard cursor shapes. See <a target="_blank" href="http://www.glfw.org/docs/latest/input.html#cursor_standard">standard cursor creation</a> for how these are used.
     */
    ARROW_CURSOR(GLFW_ARROW_CURSOR),
    IBEAM_CURSOR(GLFW_IBEAM_CURSOR),
    CROSSHAIR_CURSOR(GLFW_CROSSHAIR_CURSOR),
    HAND_CURSOR(GLFW_HAND_CURSOR),
    HRESIZE_CURSOR(GLFW_HRESIZE_CURSOR),
    VRESIZE_CURSOR(GLFW_VRESIZE_CURSOR),
    
    /**
     * GLFWMonitor events.
     */
    CONNECTED(GLFW_CONNECTED),
    DISCONNECTED(GLFW_DISCONNECTED),
    
    /**
     * Init hints.
     */
    JOYSTICK_HAT_BUTTONS(GLFW_JOYSTICK_HAT_BUTTONS),
    COCOA_CHDIR_RESOURCES(GLFW_COCOA_CHDIR_RESOURCES),
    COCOA_MENUBAR(GLFW_COCOA_MENUBAR),
    
    /**
     * Don't care value.
     */
    DONT_CARE(GLFW_DONT_CARE),
    
    /**
     * PixelFormat hints.
     */
    RED_BITS(GLFW_RED_BITS),
    GREEN_BITS(GLFW_GREEN_BITS),
    BLUE_BITS(GLFW_BLUE_BITS),
    ALPHA_BITS(GLFW_ALPHA_BITS),
    DEPTH_BITS(GLFW_DEPTH_BITS),
    STENCIL_BITS(GLFW_STENCIL_BITS),
    ACCUM_RED_BITS(GLFW_ACCUM_RED_BITS),
    ACCUM_GREEN_BITS(GLFW_ACCUM_GREEN_BITS),
    ACCUM_BLUE_BITS(GLFW_ACCUM_BLUE_BITS),
    ACCUM_ALPHA_BITS(GLFW_ACCUM_ALPHA_BITS),
    AUX_BUFFERS(GLFW_AUX_BUFFERS),
    STEREO(GLFW_STEREO),
    SAMPLES(GLFW_SAMPLES),
    SRGB_CAPABLE(GLFW_SRGB_CAPABLE),
    REFRESH_RATE(GLFW_REFRESH_RATE),
    DOUBLEBUFFER(GLFW_DOUBLEBUFFER),
    
    /**
     * Client API hints.
     *
     * <h5>Enum values:</h5>
     *
     * <ul>
     * <li>{@link #GLFW_CLIENT_API CLIENT_API} -
     * {@code WindowHint}: Specifies which client API to create the context for. Possible values are {@link #GLFW_OPENGL_API OPENGL_API}, {@link #GLFW_OPENGL_ES_API OPENGL_ES_API} and {@link #GLFW_NO_API NO_API}. This is a hard
     * constraint.
     *
     * <p>{@code GetWindowAttrib}: Indicates the client API provided by the window's context; either {@link #GLFW_OPENGL_API OPENGL_API}, {@link #GLFW_OPENGL_ES_API OPENGL_ES_API} or {@link #GLFW_NO_API NO_API}.</p>
     * </li>
     * <li>{@link #GLFW_CONTEXT_VERSION_MAJOR CONTEXT_VERSION_MAJOR} -
     * {@code WindowHint}: Specifies the client API major version that the created context must be compatible with. The exact behavior of this hint depends on
     * the requested client API.
     *
     * <div style="margin-left: 26px; border-left: 1px solid gray; padding-left: 14px;"><h5>Note</h5>
     *
     * <ul>
     * <li>While there is no way to ask the driver for a context of the highest supported version, GLFWEnum will attempt to provide this when you ask for a
     * version 1.0 context, which is the default for these hints.</li>
     * <li><b>OpenGL</b>: {@link #GLFW_CONTEXT_VERSION_MAJOR CONTEXT_VERSION_MAJOR} and {@link #GLFW_CONTEXT_VERSION_MINOR CONTEXT_VERSION_MINOR} are not hard constraints, but creation will fail if the OpenGL version of the
     * created context is less than the one requested. It is therefore perfectly safe to use the default of version 1.0 for legacy code and you will
     * still get backwards-compatible contexts of version 3.0 and above when available.</li>
     * <li><b>OpenGL ES</b>: {@link #GLFW_CONTEXT_VERSION_MAJOR CONTEXT_VERSION_MAJOR} and {@link #GLFW_CONTEXT_VERSION_MINOR CONTEXT_VERSION_MINOR} are not hard constraints, but creation will fail if the OpenGL ES version
     * of the created context is less than the one requested. Additionally, OpenGL ES 1.x cannot be returned if 2.0 or later was requested, and vice
     * versa. This is because OpenGL ES 3.x is backward compatible with 2.0, but OpenGL ES 2.0 is not backward compatible with 1.x.</li>
     * </ul></div>
     *
     * <p>{@code GetWindowAttrib}: Indicate the client API major version of the window's context.</p>
     * </li>
     * <li>{@link #GLFW_CONTEXT_VERSION_MINOR CONTEXT_VERSION_MINOR} -
     * {@code WindowHint}: Specifies the client API minor version that the created context must be compatible with. The exact behavior of this hint depends on
     * the requested client API.
     *
     * <p>{@code GetWindowAttrib}: Indicate the client API minor version of the window's context.</p>
     * </li>
     * <li>{@link #GLFW_CONTEXT_REVISION CONTEXT_REVISION} - {@code GetWindowAttrib}: Indicates the client API version of the window's context.</li>
     * <li>{@link #GLFW_CONTEXT_ROBUSTNESS CONTEXT_ROBUSTNESS} -
     * {@code WindowHint}: Specifies the robustness strategy to be used by the context. This can be one of {@link #GLFW_NO_RESET_NOTIFICATION NO_RESET_NOTIFICATION} or {@link #GLFW_LOSE_CONTEXT_ON_RESET LOSE_CONTEXT_ON_RESET}, or
     * {@link #GLFW_NO_ROBUSTNESS NO_ROBUSTNESS} to not request a robustness strategy.
     *
     * <p>{@code GetWindowAttrib}: Indicates the robustness strategy used by the context. This is {@link #GLFW_LOSE_CONTEXT_ON_RESET LOSE_CONTEXT_ON_RESET} or {@link #GLFW_NO_RESET_NOTIFICATION NO_RESET_NOTIFICATION} if the window's
     * context supports robustness, or {@link #GLFW_NO_ROBUSTNESS NO_ROBUSTNESS} otherwise.</p>
     * </li>
     * <li>{@link #GLFW_OPENGL_FORWARD_COMPAT OPENGL_FORWARD_COMPAT} -
     * {@code WindowHint}: Specifies whether the OpenGL context should be forward-compatible, i.e. one where all functionality deprecated in the requested
     * version of OpenGL is removed. This must only be used if the requested OpenGL version is 3.0 or above. If OpenGL ES is requested, this hint is
     * ignored.
     *
     * <p>{@code GetWindowAttrib}: Indicates if the window's context is an OpenGL forward-compatible one.</p>
     * </li>
     * <li>{@link #GLFW_OPENGL_DEBUG_CONTEXT OPENGL_DEBUG_CONTEXT} -
     * {@code WindowHint}: Specifies whether to create a debug OpenGL context, which may have additional error and performance issue reporting functionality.
     * If OpenGL ES is requested, this hint is ignored.
     *
     * <p>{@code GetWindowAttrib}: Indicates if the window's context is an OpenGL debug context.</p>
     * </li>
     * <li>{@link #GLFW_OPENGL_PROFILE OPENGL_PROFILE} -
     * {@code WindowHint}: Specifies which OpenGL profile to create the context for. Possible values are one of {@link #GLFW_OPENGL_CORE_PROFILE OPENGL_CORE_PROFILE} or {@link #GLFW_OPENGL_COMPAT_PROFILE OPENGL_COMPAT_PROFILE},
     * or {@link #GLFW_OPENGL_ANY_PROFILE OPENGL_ANY_PROFILE} to not request a specific profile. If requesting an OpenGL version below 3.2, {@link #GLFW_OPENGL_ANY_PROFILE OPENGL_ANY_PROFILE} must be used. If OpenGL ES
     * is requested, this hint is ignored.
     *
     * <p>{@code GetWindowAttrib}: Indicates the OpenGL profile used by the context. This is {@link #GLFW_OPENGL_CORE_PROFILE OPENGL_CORE_PROFILE} or {@link #GLFW_OPENGL_COMPAT_PROFILE OPENGL_COMPAT_PROFILE} if the context uses a
     * known profile, or {@link #GLFW_OPENGL_ANY_PROFILE OPENGL_ANY_PROFILE} if the OpenGL profile is unknown or the context is an OpenGL ES context. Note that the returned profile may
     * not match the profile bits of the context flags, as GLFWEnum will try other means of detecting the profile when no bits are set.</p>
     * </li>
     * <li>{@link #GLFW_CONTEXT_RELEASE_BEHAVIOR CONTEXT_RELEASE_BEHAVIOR} -
     * {@code WindowHint}: Specifies the release behavior to be used by the context. If the behavior is {@link #GLFW_ANY_RELEASE_BEHAVIOR ANY_RELEASE_BEHAVIOR}, the default behavior of the
     * context creation API will be used. If the behavior is {@link #GLFW_RELEASE_BEHAVIOR_FLUSH RELEASE_BEHAVIOR_FLUSH}, the pipeline will be flushed whenever the context is released from
     * being the current one. If the behavior is {@link #GLFW_RELEASE_BEHAVIOR_NONE RELEASE_BEHAVIOR_NONE}, the pipeline will not be flushed on release.
     * </li>
     * <li>{@link #GLFW_CONTEXT_NO_ERROR CONTEXT_NO_ERROR} -
     * {@code WindowHint}: Specifies whether errors should be generated by the context. If enabled, situations that would have generated errors instead cause
     * undefined behavior.
     * </li>
     * <li>{@link #GLFW_CONTEXT_CREATION_API CONTEXT_CREATION_API} -
     * {@code WindowHint}: Specifies which context creation API to use to create the context. Possible values are {@link #GLFW_NATIVE_CONTEXT_API NATIVE_CONTEXT_API} and {@link #GLFW_EGL_CONTEXT_API EGL_CONTEXT_API}.
     * This is a hard constraint. If no client API is requested, this hint is ignored.
     *
     * <div style="margin-left: 26px; border-left: 1px solid gray; padding-left: 14px;"><h5>Note</h5>
     *
     * <ul>
     * <li><b>macOS</b>: The EGL API is not available on this platform and requests to use it will fail.</li>
     * <li><b>Wayland, Mir</b>: The EGL API <i>is</i> the native context creation API, so this hint will have no effect.</li>
     * <li>An OpenGL extension loader library that assumes it knows which context creation API is used on a given platform may fail if you change this
     * hint. This can be resolved by having it load via {@link #glfwGetProcAddress GetProcAddress}, which always uses the selected API.</li>
     * </ul></div>
     *
     * <p>{@code GetWindowAttrib}: Indicates the context creation API used to create the window's context; either {@link #GLFW_NATIVE_CONTEXT_API NATIVE_CONTEXT_API} or {@link #GLFW_EGL_CONTEXT_API EGL_CONTEXT_API}.</p>
     * </li>
     * <li>{@link #GLFW_SCALE_TO_MONITOR SCALE_TO_MONITOR} -
     * {@code WindowHint}: Specifies whether the window content area should be resized based on the monitor content scale of any monitor it is placed on.
     * This includes the initial placement when the window is created. Possible values are {@link #GLFW_TRUE TRUE} and {@link #GLFW_FALSE FALSE}.
     *
     * <p>This hint only has an effect on platforms where screen coordinates and pixels always map 1:1 such as Windows and X11. On platforms like macOS the
     * resolution of the framebuffer is changed independently of the window size.</p>
     * </li>
     * </ul>
     */
    CLIENT_API(GLFW_CLIENT_API),
    CONTEXT_VERSION_MAJOR(GLFW_CONTEXT_VERSION_MAJOR),
    CONTEXT_VERSION_MINOR(GLFW_CONTEXT_VERSION_MINOR),
    CONTEXT_REVISION(GLFW_CONTEXT_REVISION),
    CONTEXT_ROBUSTNESS(GLFW_CONTEXT_ROBUSTNESS),
    OPENGL_FORWARD_COMPAT(GLFW_OPENGL_FORWARD_COMPAT),
    OPENGL_DEBUG_CONTEXT(GLFW_OPENGL_DEBUG_CONTEXT),
    OPENGL_PROFILE(GLFW_OPENGL_PROFILE),
    CONTEXT_RELEASE_BEHAVIOR(GLFW_CONTEXT_RELEASE_BEHAVIOR),
    CONTEXT_NO_ERROR(GLFW_CONTEXT_NO_ERROR),
    CONTEXT_CREATION_API(GLFW_CONTEXT_CREATION_API),
    SCALE_TO_MONITOR(GLFW_SCALE_TO_MONITOR),
    
    /**
     * Specifies whether to use full resolution framebuffers on Retina displays. This is ignored on other platforms.
     */
    COCOA_RETINA_FRAMEBUFFER(GLFW_COCOA_RETINA_FRAMEBUFFER),
    
    /**
     * Specifies the UTF-8 encoded name to use for autosaving the window frame, or if empty disables frame autosaving for the window. This is ignored on other
     * platforms. This is set with {@link #glfwWindowHintString WindowHintString}.
     */
    COCOA_FRAME_NAME(GLFW_COCOA_FRAME_NAME),
    
    /**
     * Specifies whether to enable Automatic Graphics Switching, i.e. to allow the system to choose the integrated GPU for the OpenGL context and move it
     * between GPUs if necessary or whether to force it to always run on the discrete GPU. This only affects systems with both integrated and discrete GPUs.
     * This is ignored on other platforms.
     */
    COCOA_GRAPHICS_SWITCHING(GLFW_COCOA_GRAPHICS_SWITCHING),
    
    /**
     * The desired ASCII encoded class and instance parts of the ICCCM {@code WM_CLASS} window property. These are set with {@link #glfwWindowHintString WindowHintString}.
     */
    X11_CLASS_NAME(GLFW_X11_CLASS_NAME),
    X11_INSTANCE_NAME(GLFW_X11_INSTANCE_NAME),
    
    /**
     * Values for the {@link #GLFW_CLIENT_API CLIENT_API} hint.
     */
    NO_API(GLFW_NO_API),
    OPENGL_API(GLFW_OPENGL_API),
    OPENGL_ES_API(GLFW_OPENGL_ES_API),
    
    /**
     * Values for the {@link #GLFW_CONTEXT_ROBUSTNESS CONTEXT_ROBUSTNESS} hint.
     */
    NO_ROBUSTNESS(GLFW_NO_ROBUSTNESS),
    NO_RESET_NOTIFICATION(GLFW_NO_RESET_NOTIFICATION),
    LOSE_CONTEXT_ON_RESET(GLFW_LOSE_CONTEXT_ON_RESET),
    
    /**
     * Values for the {@link #GLFW_OPENGL_PROFILE OPENGL_PROFILE} hint.
     */
    OPENGL_ANY_PROFILE(GLFW_OPENGL_ANY_PROFILE),
    OPENGL_CORE_PROFILE(GLFW_OPENGL_CORE_PROFILE),
    OPENGL_COMPAT_PROFILE(GLFW_OPENGL_COMPAT_PROFILE),
    
    /**
     * Values for the {@link #GLFW_CONTEXT_RELEASE_BEHAVIOR CONTEXT_RELEASE_BEHAVIOR} hint.
     */
    ANY_RELEASE_BEHAVIOR(GLFW_ANY_RELEASE_BEHAVIOR),
    RELEASE_BEHAVIOR_FLUSH(GLFW_RELEASE_BEHAVIOR_FLUSH),
    RELEASE_BEHAVIOR_NONE(GLFW_RELEASE_BEHAVIOR_NONE),
    
    /**
     * Values for the {@link #GLFW.GLFW_CONTEXT_CREATION_API CONTEXT_CREATION_API} hint.
     */
    NATIVE_CONTEXT_API(GLFW_NATIVE_CONTEXT_API),
    EGL_CONTEXT_API(GLFW_EGL_CONTEXT_API),
    OSMESA_CONTEXT_API(GLFW_OSMESA_CONTEXT_API),
    
    ;
    
    private static final HashMap<Long, GLFWEnum> LOOKUP_MAP = new HashMap<>();
    
    static
    {
        for (GLFWEnum gl : GLFWEnum.values()) GLFWEnum.LOOKUP_MAP.put(gl.ref, gl);
    }
    
    private final long ref;
    private final String glName;
    
    GLFWEnum(long ref)
    {
        this.ref = ref;
        
        StringBuilder name = new StringBuilder(name());
        if (name().startsWith("_")) name.deleteCharAt(0);
        this.glName = name.insert(0, "GL_").toString();
    }
    
    public int ref()
    {
        return (int) this.ref;
    }
    
    public long refL()
    {
        return this.ref;
    }
    
    @Override
    public String toString()
    {
        return this.glName;
    }
    
    public static GLFWEnum get(long gl)
    {
        return GLFWEnum.LOOKUP_MAP.getOrDefault(gl, GLFWEnum.NULL);
    }
    }

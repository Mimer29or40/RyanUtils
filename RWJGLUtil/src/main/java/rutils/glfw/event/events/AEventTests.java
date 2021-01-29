package rutils.glfw.event.events;

import rutils.ClassUtil;

public class AEventTests
{
    public static void main(String[] args)
    {
        Class<?> clazz = EventGamepadButtonDown.class;
        System.out.println(ClassUtil.getTypes(clazz));
    }
}

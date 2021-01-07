package rutils;

public class ClassUtil
{
    public static String getCallingClassName()
    {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
    
        return elements.length > 2 ? elements[2].getClassName() : "";
    }
}

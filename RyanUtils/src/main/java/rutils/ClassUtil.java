package rutils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public class ClassUtil
{
    private static final Predicate<Class<?>> DEFAULT_CLASS_FILTER  = clazz -> clazz != Object.class;
    private static final Predicate<Method>   DEFAULT_METHOD_FILTER = method -> method.getDeclaringClass() != Object.class;
    private static final Predicate<Field>    DEFAULT_FIELD_FILTER  = field -> field.getDeclaringClass() != Object.class;
    
    public static String getCallingClassName()
    {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        
        return elements.length > 2 ? elements[2].getClassName() : "";
    }
    
    public static Set<Class<?>> getTypes(final Class<?> clazz, Predicate<Class<?>> filter, final Set<Class<?>> found)
    {
        if (clazz.getSuperclass() != null) getTypes(clazz.getSuperclass(), filter, found);
        for (Class<?> i : clazz.getInterfaces()) getTypes(i, filter, found);
        if (filter.test(clazz)) found.add(clazz);
        return found;
    }
    
    public static Set<Class<?>> getTypes(final Class<?> clazz, Predicate<Class<?>> filter)
    {
        return getTypes(clazz, filter, new LinkedHashSet<>());
    }
    
    public static Set<Class<?>> getTypes(final Class<?> clazz, final Set<Class<?>> found)
    {
        return getTypes(clazz, DEFAULT_CLASS_FILTER, found);
    }
    
    public static Set<Class<?>> getTypes(final Class<?> clazz)
    {
        return getTypes(clazz, DEFAULT_CLASS_FILTER, new LinkedHashSet<>());
    }
    
    public static Set<Method> getMethods(final Class<?> clazz, final Predicate<Method> filter, final Set<Method> found)
    {
        final Set<Class<?>> foundClasses = getTypes(clazz);
        
        for (Method m : methodsFor(clazz, new LinkedHashSet<>()))
        {
            if (filter.test(m))
            {
                for (Class<?> c : foundClasses)
                {
                    try
                    {
                        found.add(c.getDeclaredMethod(m.getName(), m.getParameterTypes()));
                        break;
                    }
                    catch (NoSuchMethodException ignored) { }
                }
            }
        }
        return found;
    }
    
    public static Set<Method> getMethods(final Class<?> clazz, Predicate<Method> filter)
    {
        return getMethods(clazz, filter, new LinkedHashSet<>());
    }
    
    public static Set<Method> getMethods(final Class<?> clazz, final Set<Method> found)
    {
        return getMethods(clazz, DEFAULT_METHOD_FILTER, found);
    }
    
    public static Set<Method> getMethods(final Class<?> clazz)
    {
        return getMethods(clazz, DEFAULT_METHOD_FILTER, new LinkedHashSet<>());
    }
    
    public static Set<Field> getFields(final Class<?> clazz, Predicate<Field> filter, final Set<Field> found)
    {
        final Set<Class<?>> foundClasses = getTypes(clazz);
        
        for (Field f : fieldsFor(clazz, new LinkedHashSet<>()))
        {
            if (filter.test(f))
            {
                for (Class<?> c : foundClasses)
                {
                    try
                    {
                        found.add(c.getDeclaredField(f.getName()));
                        break;
                    }
                    catch (NoSuchFieldException ignored) { }
                }
            }
        }
        return found;
    }
    
    public static Set<Field> getFields(final Class<?> clazz, Predicate<Field> filter)
    {
        return getFields(clazz, filter, new LinkedHashSet<>());
    }
    
    public static Set<Field> getFields(final Class<?> clazz, final Set<Field> found)
    {
        return getFields(clazz, DEFAULT_FIELD_FILTER, found);
    }
    
    public static Set<Field> getFields(final Class<?> clazz)
    {
        return getFields(clazz, DEFAULT_FIELD_FILTER, new LinkedHashSet<>());
    }
    
    private static Set<Method> methodsFor(final Class<?> clazz, final Set<Method> found)
    {
        if (clazz.getSuperclass() != null) getMethods(clazz.getSuperclass(), found);
        for (Class<?> c : clazz.getInterfaces()) getMethods(c, found);
        Collections.addAll(found, clazz.getDeclaredMethods());
        return found;
    }
    
    private static Set<Field> fieldsFor(final Class<?> clazz, final Set<Field> found)
    {
        if (clazz.getSuperclass() != null) fieldsFor(clazz.getSuperclass(), found);
        for (Class<?> c : clazz.getInterfaces()) fieldsFor(c, found);
        Collections.addAll(found, clazz.getDeclaredFields());
        return found;
    }
}

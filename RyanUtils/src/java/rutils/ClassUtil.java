package rutils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;

public class ClassUtil
{
    private static final Predicate<Class<?>> DEFAULT_CLASS_FILTER  = clazz -> clazz != Object.class;
    private static final Predicate<Method>   DEFAULT_METHOD_FILTER = method -> method.getDeclaringClass() != Object.class;
    private static final Predicate<Field>    DEFAULT_FIELD_FILTER  = field -> field.getDeclaringClass() != Object.class;
    
    public static @NotNull String getCallingClassName()
    {
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        
        return elements.length > 2 ? elements[2].getClassName() : "";
    }
    
    /**
     * Generates a set containing all ancestor classes and interfaces of the
     * specified sorted in a depth first arrangement and filtered according
     * to the {@link Predicate} provided.
     *
     * @param clazz  The class to search.
     * @param filter The filter for the classes.
     * @return A set containing all ancestor classes and interfaces of the specified class
     */
    public static @NotNull Set<Class<?>> getTypes(@NotNull final Class<?> clazz, @NotNull Predicate<Class<?>> filter)
    {
        return getTypesImpl(clazz, filter, new LinkedHashSet<>());
    }
    
    /**
     * Generates a set containing all ancestor classes and interfaces of the
     * specified sorted in a depth first arrangement and filtered according
     * to the {@link Predicate} provided. Excluding
     * {@link Object Object.class}.
     *
     * @param clazz The class to search.
     * @return A set containing all ancestor classes and interfaces of the specified class
     */
    public static @NotNull Set<Class<?>> getTypes(@NotNull final Class<?> clazz)
    {
        return getTypesImpl(clazz, ClassUtil.DEFAULT_CLASS_FILTER, new LinkedHashSet<>());
    }
    
    /**
     * Generates a set containing all methods for all ancestor classes and
     * interfaces of the specified sorted in a depth first arrangement and
     * filtered according to the {@link Predicate} provided. The methods will
     * be the method reference of the original declaring ancestor sorted
     * alphabetically.
     * <p>
     * This will not determine if the specified class can access the found
     * method.
     *
     * @param clazz  The class to search.
     * @param filter he filter for the methods.
     * @return A set containing all methods of all ancestor classes and interfaces of the specified class
     */
    public static @NotNull Set<Method> getMethods(@NotNull final Class<?> clazz, @NotNull final Predicate<Method> filter)
    {
        return getMethodsImpl(clazz, filter, new LinkedHashSet<>());
    }
    
    /**
     * Generates a set containing all methods for all ancestor classes and
     * interfaces of the specified sorted in a depth first arrangement and
     * filtered according to the {@link Predicate} provided. The methods will
     * be the method reference of the original declaring ancestor sorted
     * alphabetically. Excluding {@link Object Object.class}.
     * <p>
     * This will not determine if the specified class can access the found
     * method.
     *
     * @param clazz The class to search.
     * @return A set containing all methods of all ancestor classes and interfaces of the specified class
     */
    public static @NotNull Set<Method> getMethods(@NotNull final Class<?> clazz)
    {
        return getMethodsImpl(clazz, ClassUtil.DEFAULT_METHOD_FILTER, new LinkedHashSet<>());
    }
    
    /**
     * Generates a set containing all fields for all ancestor classes and
     * interfaces of the specified sorted in a depth first arrangement and
     * filtered according to the {@link Predicate} provided. The fields will be
     * the field reference of the original declaring ancestor sorted
     * alphabetically.
     * <p>
     * This will not determine if the specified class can access the found
     * method.
     *
     * @param clazz  The class to search.
     * @param filter he filter for the fields.
     * @return A set containing all methods of all ancestor classes and interfaces of the specified class
     */
    public static @NotNull Set<Field> getFields(@NotNull final Class<?> clazz, @NotNull Predicate<Field> filter)
    {
        return getFieldsImpl(clazz, filter, new LinkedHashSet<>());
    }
    
    /**
     * Generates a set containing all fields for all ancestor classes and
     * interfaces of the specified sorted in a depth first arrangement and
     * filtered according to the {@link Predicate} provided. The fields will be
     * the field reference of the original declaring ancestor sorted
     * alphabetically. Excluding {@link Object Object.class}.
     * <p>
     * This will not determine if the specified class can access the found
     * method.
     *
     * @param clazz The class to search.
     * @return A set containing all methods of all ancestor classes and interfaces of the specified class
     */
    public static @NotNull Set<Field> getFields(@NotNull final Class<?> clazz)
    {
        return getFieldsImpl(clazz, ClassUtil.DEFAULT_FIELD_FILTER, new LinkedHashSet<>());
    }
    
    private static Set<Class<?>> getTypesImpl(final Class<?> clazz, Predicate<Class<?>> filter, final Set<Class<?>> found)
    {
        if (clazz.getSuperclass() != null) getTypesImpl(clazz.getSuperclass(), filter, found);
        for (Class<?> i : clazz.getInterfaces()) getTypesImpl(i, filter, found);
        if (filter.test(clazz)) found.add(clazz);
        return found;
    }
    
    private static Set<Method> getMethodsImpl(final Class<?> clazz, final Predicate<Method> filter, final Set<Method> found)
    {
        final Set<Class<?>> foundClasses = getTypesImpl(clazz, c -> true, new LinkedHashSet<>());
        
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
    
    private static Set<Field> getFieldsImpl(final Class<?> clazz, Predicate<Field> filter, final Set<Field> found)
    {
        final Set<Class<?>> foundClasses = getTypesImpl(clazz, c -> true, new LinkedHashSet<>());
        
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
    
    @SuppressWarnings({"UseBulkOperation", "ManualArrayToCollectionCopy"})
    private static Set<Method> methodsFor(final Class<?> clazz, final Set<Method> found)
    {
        if (clazz.getSuperclass() != null) methodsFor(clazz.getSuperclass(), found);
        for (Class<?> c : clazz.getInterfaces()) methodsFor(c, found);
        Method[] methods = clazz.getDeclaredMethods();
        Arrays.sort(methods, ClassUtil.SORT_METHODS);
        for (Method method : methods) found.add(method);
        return found;
    }
    
    @SuppressWarnings({"UseBulkOperation", "ManualArrayToCollectionCopy"})
    private static Set<Field> fieldsFor(final Class<?> clazz, final Set<Field> found)
    {
        if (clazz.getSuperclass() != null) fieldsFor(clazz.getSuperclass(), found);
        for (Class<?> c : clazz.getInterfaces()) fieldsFor(c, found);
        Field[] fields = clazz.getDeclaredFields();
        Arrays.sort(fields, ClassUtil.SORT_FIELDS);
        for (Field field : fields) found.add(field);
        return found;
    }
    
    private static final Comparator<Parameter> SORT_PARAMETERS = Comparator.comparing(p -> p.getType().getName());
    
    private static final Comparator<Field> SORT_FIELDS = Comparator.comparing(Field::getName);
    
    private static final Comparator<Method> SORT_METHODS = (m1, m2) -> {
        int nameCompare = m1.getName().compareTo(m2.getName());
        if (nameCompare != 0) return nameCompare;
        int parameterCount = Integer.compare(m1.getParameterCount(), m2.getParameterCount());
        if (parameterCount != 0) return parameterCount;
        return Arrays.compare(m1.getParameters(), m2.getParameters(), SORT_PARAMETERS);
    };
    
    private ClassUtil() { }
}

package rutils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class ClassUtilTest
{
    private static final Logger LOGGER = new Logger();
    
    @Test
    void getCallingClassName()
    {
        assertSame("rutils.ClassUtilTest", ClassUtil.getCallingClassName());
    }
    
    @Test
    void getTypes()
    {
        Set<Class<?>> classes;
        
        classes = new LinkedHashSet<>();
        classes.add(IThing.class);
        classes.add(Thing.class);
        classes.add(ISubThing.class);
        classes.add(SubThing.class);
        assertIterableEquals(classes, ClassUtil.getTypes(SubThing.class));
        
        classes = new LinkedHashSet<>();
        classes.add(Object.class);
        classes.add(IThing.class);
        classes.add(Thing.class);
        classes.add(ISubThing.class);
        classes.add(SubThing.class);
        assertIterableEquals(classes, ClassUtil.getTypes(SubThing.class, c -> true));
        
        classes = new LinkedHashSet<>();
        classes.add(IThing.class);
        classes.add(ISubThing.class);
        assertIterableEquals(classes, ClassUtil.getTypes(SubThing.class, Class::isInterface));
    }
    
    @Test
    void getMethods() throws NoSuchMethodException
    {
        Set<Method> methods;
        
        methods = new LinkedHashSet<>();
        methods.add(IThing.class.getDeclaredMethod("doIThing"));
        methods.add(Thing.class.getDeclaredMethod("doAbstractThing"));
        methods.add(Thing.class.getDeclaredMethod("doStaticThing"));
        methods.add(Thing.class.getDeclaredMethod("doThing"));
        methods.add(ISubThing.class.getDeclaredMethod("doISubThing"));
        methods.add(SubThing.class.getDeclaredMethod("doStaticSubThing"));
        methods.add(SubThing.class.getDeclaredMethod("doSubThing"));
        assertIterableEquals(methods, ClassUtil.getMethods(SubThing.class));
        
        methods = new LinkedHashSet<>();
        methods.add(Object.class.getDeclaredMethod("clone"));
        methods.add(Object.class.getDeclaredMethod("equals", Object.class));
        methods.add(Object.class.getDeclaredMethod("finalize"));
        methods.add(Object.class.getDeclaredMethod("getClass"));
        methods.add(Object.class.getDeclaredMethod("hashCode"));
        methods.add(Object.class.getDeclaredMethod("notify"));
        methods.add(Object.class.getDeclaredMethod("notifyAll"));
        methods.add(Object.class.getDeclaredMethod("toString"));
        methods.add(Object.class.getDeclaredMethod("wait"));
        methods.add(Object.class.getDeclaredMethod("wait", long.class));
        methods.add(Object.class.getDeclaredMethod("wait", long.class, int.class));
        methods.add(IThing.class.getDeclaredMethod("doIThing"));
        methods.add(Thing.class.getDeclaredMethod("doAbstractThing"));
        methods.add(Thing.class.getDeclaredMethod("doStaticThing"));
        methods.add(Thing.class.getDeclaredMethod("doThing"));
        methods.add(ISubThing.class.getDeclaredMethod("doISubThing"));
        methods.add(SubThing.class.getDeclaredMethod("doStaticSubThing"));
        methods.add(SubThing.class.getDeclaredMethod("doSubThing"));
        assertIterableEquals(methods, ClassUtil.getMethods(SubThing.class, m -> true));
        
        methods = new LinkedHashSet<>();
        methods.add(Object.class.getDeclaredMethod("equals", Object.class));
        methods.add(Object.class.getDeclaredMethod("wait", long.class));
        assertIterableEquals(methods, ClassUtil.getMethods(SubThing.class, m -> m.getParameterCount() == 1));
        
        methods = new LinkedHashSet<>();
        methods.add(Thing.class.getDeclaredMethod("doStaticThing"));
        methods.add(SubThing.class.getDeclaredMethod("doStaticSubThing"));
        assertIterableEquals(methods, ClassUtil.getMethods(SubThing.class, m -> Modifier.isStatic(m.getModifiers())));
    }
    
    @Test
    void getFields() throws NoSuchFieldException
    {
        Set<Field> fields;
        
        fields = new LinkedHashSet<>();
        fields.add(Thing.class.getDeclaredField("staticThing"));
        fields.add(Thing.class.getDeclaredField("thing"));
        fields.add(SubThing.class.getDeclaredField("staticSubThing"));
        fields.add(SubThing.class.getDeclaredField("subThing"));
        assertIterableEquals(fields, ClassUtil.getFields(SubThing.class));
        
        fields = new LinkedHashSet<>();
        fields.add(Thing.class.getDeclaredField("staticThing"));
        fields.add(SubThing.class.getDeclaredField("staticSubThing"));
        assertIterableEquals(fields, ClassUtil.getFields(SubThing.class, f -> Modifier.isStatic(f.getModifiers())));
    }
    
    interface IThing
    {
        void doIThing();
    }
    
    @SuppressWarnings("unused")
    static abstract class Thing implements IThing
    {
        static int staticThing;
        
        static void doStaticThing() {LOGGER.info("doStaticThing");}
        
        int thing;
        
        @Override
        public void doIThing() {LOGGER.info("doIThing");}
        
        void doThing() {LOGGER.info("doThing");}
        
        abstract void doAbstractThing();
    }
    
    interface ISubThing
    {
        void doISubThing();
    }
    
    @SuppressWarnings("unused")
    static class SubThing extends Thing implements ISubThing
    {
        static int staticSubThing;
        
        static void doStaticSubThing() {LOGGER.info("doStaticSubThing");}
        
        int subThing;
        
        @Override
        void doThing() {LOGGER.info("@Override doThing");}
        
        @Override
        void doAbstractThing() {LOGGER.info("doAbstractThing");}
        
        @Override
        public void doISubThing() {LOGGER.info("doISubThing");}
        
        public void doSubThing() {LOGGER.info("doSubThing");}
    }
}

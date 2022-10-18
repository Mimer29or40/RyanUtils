package rutils;

import org.joml.Vector2d;
import org.joml.Vector2dc;
import rutils.property.IntProperty;
import rutils.property.ObjectProperty;
import rutils.property.Property;

public class PropertyTest
{
    // @BeforeAll
    // static void beforeAll()
    // {
    //     profiler = Profiler.get("Test Profiler");
    // }
    //
    // @Test
    // void get()
    // {
    //     assertSame(profiler, Profiler.get("Test Profiler"));
    // }
    //
    // @Test
    // void name()
    // {
    //     assertEquals(profiler.name(), Profiler.get("Test Profiler").name());
    // }
    
    public static void main(String[] args)
    {
        Property<Integer> intProp = new IntProperty(9);
        intProp.get();
        
        Vector2d vec = new Vector2d();
        Property<Vector2dc> vecProp = new ObjectProperty<>(vec);
    }
}

package rutils.property;

import org.joml.Vector2dc;

import java.util.Objects;

public class Vector2dcProperty extends ObjectProperty<Vector2dc>
{
    
    public Vector2dcProperty(Vector2dc initial)
    {
        super(initial);
    }
    
    
    protected boolean checkObj(Vector2dc newValue)
    {
        return !Objects.deepEquals(this.value, newValue);
    }
    
    protected Vector2dc setObj(Vector2dc newValue)
    {
        return this.value = newValue;
    }
}

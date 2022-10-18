package rutils.property;

public interface ReadOnlyProperty<T>
{
    T get();
    
    boolean changed();
}

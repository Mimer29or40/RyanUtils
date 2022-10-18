package rutils.property;

public interface Property<T> extends ReadOnlyProperty<T>
{
    void set(T newValue);
}

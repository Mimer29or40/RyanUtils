package rutils.property;

abstract class AbstractProperty<T> implements Property<T>
{
    @Override
    public String toString()
    {
        return getClass().getName() + '{' + "value=" + get() + '}';
    }
}

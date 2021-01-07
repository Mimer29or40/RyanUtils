package rutils.profiler;

import java.util.Objects;

public class Section implements AutoCloseable
{
    private final Profiler profiler;
    private final String name;
    
    Section(Profiler profiler, String name)
    {
        this.profiler = profiler;
        this.name = name;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return this.profiler.equals(section.profiler) && this.name.equals(section.name);
    }
    
    @Override
    public int hashCode()
    {
        return Objects.hash(this.profiler, this.name);
    }
    
    @Override
    public String toString()
    {
        return "Section{" + '\'' + this.name + '\'' + ", profiler=" + this.profiler + '}';
    }
    
    
    /**
     * @return The name of the section.
     */
    public String name()
    {
        return this.name;
    }
    
    @Override
    public void close()
    {
        this.profiler.endSection();
    }
}

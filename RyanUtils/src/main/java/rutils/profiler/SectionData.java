package rutils.profiler;

public abstract class SectionData implements Comparable<SectionData>
{
    public final String name;
    
    private SectionData(String name)
    {
        this.name = name;
    }
    
    public abstract long value();
    
    public abstract String valueString();
    
    public int compareTo(SectionData o)
    {
        return value() < o.value() ? -1 : value() > o.value() ? 1 : this.name.compareTo(o.name);
    }
    
    public static class Average extends SectionData
    {
        public final long avgTime, minTime, maxTime;
    
        Average(String name, long avgTime, long minTime, long maxTime)
        {
            super(name);
            this.avgTime = avgTime / 1000;
            this.minTime = minTime / 1000;
            this.maxTime = maxTime / 1000;
        }
        
        @Override
        public long value()
        {
            return this.avgTime;
        }
        
        @Override
        public String valueString()
        {
            return String.format("Avg: %6d us Min: %6d us Max: %6d us", this.avgTime, this.minTime, this.maxTime);
        }
    }
    
    public static class Percent extends SectionData
    {
        public final long   time;
        public final double percentage, globalPercentage;
    
        Percent(String name, long time, double percentage, double globalPercentage)
        {
            super(name);
            this.time             = time / 1000;
            this.percentage       = percentage;
            this.globalPercentage = globalPercentage;
        }
        
        @Override
        public long value()
        {
            return this.time;
        }
        
        @Override
        public String valueString()
        {
            return String.format("%6d us (Sec: %7.3f%% / Gbl: %7.3f%%)", this.time, this.percentage, this.globalPercentage);
        }
    }
}

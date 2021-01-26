package rutils;

import rutils.profiler.Profiler;
import rutils.profiler.Section;

public class ProfilerTest
{
    private static final Logger LOGGER = new Logger();
    
    public static void main(String[] args) throws InterruptedException
    {
        Profiler profiler = Profiler.get("Test Profiler").enabled(true).startFrame();
    
        try(Section s1 = profiler.startSection("Section 1"))
        {
            Thread.sleep(1000);
        }
    
        try(Section s2 = profiler.startSection("Section 2"))
        {
            try(Section s3 = profiler.startSection("Section 3"))
            {
                Thread.sleep(1000);
            }
            
            try(Section s4 = profiler.startSection("Section 4"))
            {
                Thread.sleep(1000);
            }
        }
    
        profiler.endFrame();
    
        LOGGER.info(profiler.getAvgDataString(null));
        LOGGER.info(profiler.getMaxDataString(null));
        LOGGER.info(profiler.getMinDataString(null));
    }
}

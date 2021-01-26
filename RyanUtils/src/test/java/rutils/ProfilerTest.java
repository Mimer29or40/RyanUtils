package rutils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import rutils.profiler.Profiler;
import rutils.profiler.Section;

import static org.junit.jupiter.api.Assertions.*;

public class ProfilerTest
{
    private static final Logger LOGGER = new Logger();
    
    static Profiler profiler;
    
    @BeforeAll
    static void beforeAll()
    {
        profiler = Profiler.get("Test Profiler");
    }
    
    @Test
    void get()
    {
        assertSame(profiler, Profiler.get("Test Profiler"));
    }
    
    @Test
    void name()
    {
        assertEquals(profiler.name(), Profiler.get("Test Profiler").name());
    }
    
    @Test
    void enabled()
    {
        profiler.enabled(true);
        assertTrue(profiler.enabled());
        
        profiler.toggleEnabled();
        assertFalse(profiler.enabled());
        
        profiler.enable();
        assertTrue(profiler.enabled());
        
        profiler.disable();
        assertFalse(profiler.enabled());
    }
    
    @Test
    void warningThreshold()
    {
        profiler.warningThreshold(0.05);
        assertEquals(0.05, profiler.warningThreshold(), 0.001);
    }
    
    @Test
    void frame() throws InterruptedException
    {
        profiler.disable();
        profiler.startFrame();
        try (Section section = profiler.startSection("Section 1"))
        {
            assertNull(section);
            Thread.sleep(1);
        }
        profiler.endFrame();
        
        profiler.enable();
        profiler.startFrame();
        try (Section section = profiler.startSection("Section 1"))
        {
            assertNotNull(section);
            Thread.sleep(1);
        }
        profiler.endFrame();
        
        
        profiler.disable();
        profiler.startFrame();
        profiler.startSection("Section 1");
        assertNull(profiler.currentSection());
        Thread.sleep(1);
        profiler.endSection();
        profiler.endFrame();
        
        profiler.enable();
        profiler.startFrame();
        profiler.startSection("Section 1");
        assertNotNull(profiler.currentSection());
        Thread.sleep(1);
        profiler.endSection();
        profiler.endFrame();
    }
    
    @Test
    void frameData() throws InterruptedException
    {
        profiler.enable();
        
        profiler.clear();
        profiler.startFrame();
        
        try (Section s1 = profiler.startSection("Section 1"))
        {
            LOGGER.info(s1);
            Thread.sleep(1000);
        }
        
        try (Section s2 = profiler.startSection("Section 2"))
        {
            LOGGER.info(s2);
            
            try (Section s3 = profiler.startSection("Section 3"))
            {
                LOGGER.info(s3);
                Thread.sleep(1000);
            }
            
            try (Section s4 = profiler.startSection("Section 4"))
            {
                LOGGER.info(s4);
                Thread.sleep(1000);
            }
        }
        
        profiler.endFrame();
        
        LOGGER.info(profiler.getAvgDataString(null));
        LOGGER.info(profiler.getMaxDataString(null));
        LOGGER.info(profiler.getMinDataString(null));
    }
}

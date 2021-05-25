package rutils.glfw;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestWindowBuilder
{
    @Test
    void testWindowName()
    {
        new GLFWApplicationTest()
        {
            @Override
            protected Window createWindow()
            {
                return new Window.Builder()
                        .name("Name")
                        .build();
            }
            
            @Override
            protected void init()
            {
            
            }
            
            @Override
            protected void draw(double time, double deltaT)
            {
            
            }
            
            @Override
            protected void beforeEventLoop()
            {
                assertEquals("Name", window.name());
            }
        }.run();
        
        new GLFWApplicationTest()
        {
            @Override
            protected Window createWindow()
            {
                return new Window.Builder()
                        .name(null)
                        .build();
            }
            
            @Override
            protected void init()
            {
            
            }
            
            @Override
            protected void draw(double time, double deltaT)
            {
            
            }
            
            @Override
            protected void beforeEventLoop()
            {
                assertEquals("Window-" + window.handle, window.name());
            }
        }.run();
    }
}

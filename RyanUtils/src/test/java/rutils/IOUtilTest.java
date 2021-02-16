package rutils;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;

class IOUtilTest
{
    @Test
    void getPath()
    {
        assertTrue(IOUtil.getPath("rutils/TestResource.txt").toFile().exists());
        assertFalse(IOUtil.getPath("path/to/non/existent/file.txt").toFile().exists());
    }
    
    @Test
    void resourceToByteBuffer()
    {
        byte[]     actual = new byte[] {84, 104, 105, 115, 32, 105, 115, 32, 97, 32, 116, 101, 115, 116, 32, 102, 105, 108, 101, 46, 13, 10, 13, 10, 84, 104, 97, 116, 32, 105, 115, 32, 117, 115, 101, 100, 32, 98, 121, 32, 73, 79, 85, 116, 105, 108, 84, 101, 115, 116, 13, 10};
        ByteBuffer loaded = IOUtil.resourceToByteBuffer("rutils/TestResource.txt");
        for (byte data : actual) assertEquals(data, loaded.get());
        assertFalse(loaded.hasRemaining());
    }
}

package rutils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class IOUtil
{
    /**
     * Gets the path to the file. First it tries to load a resources, then if it fails then tries to load from disk.
     *
     * @param resource The string path to the file
     * @return The path to the file
     */
    public static @NotNull Path getPath(@NotNull String resource)
    {
        try
        {
            return Path.of(Objects.requireNonNull(IOUtil.class.getClassLoader().getResource(resource)).toURI());
        }
        catch (URISyntaxException | NullPointerException ignored) { }
        return Path.of(resource);
    }
    
    /**
     * Loads a file as a ByteBuffer.
     *
     * @param resource The path to the file.
     * @return The data as a ByteBuffer.
     */
    @SuppressWarnings("StatementWithEmptyBody")
    public static ByteBuffer resourceToByteBuffer(@NotNull String resource)
    {
        try (SeekableByteChannel fc = Files.newByteChannel(IOUtil.getPath(resource)))
        {
            ByteBuffer buffer = ByteBuffer.allocateDirect((int) fc.size() + 1).order(ByteOrder.nativeOrder());
            while (fc.read(buffer) != -1) ;
            return buffer.clear();
        }
        catch (IOException e)
        {
            throw new RuntimeException(String.format("Could not load resource: \"%s\"", resource), e);
        }
    }
    
    private IOUtil() { }
}

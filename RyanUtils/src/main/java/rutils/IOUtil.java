package rutils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    private static final Logger LOGGER = new Logger();
    
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
     * @param resource  The path to the file.
     * @param bytesRead The number of bytes read from the file.
     * @return The data as a ByteBuffer.
     */
    public static @Nullable ByteBuffer readFromFile(@NotNull String resource, int[] bytesRead)
    {
        ByteBuffer buffer = null;
        try (SeekableByteChannel fc = Files.newByteChannel(IOUtil.getPath(resource)))
        {
            buffer = ByteBuffer.allocateDirect((int) fc.size() + 1).order(ByteOrder.nativeOrder());
            bytesRead[0] = 0;
            for (int read; (read = fc.read(buffer)) != -1; ) bytesRead[0] += read;
        }
        catch (IOException e)
        {
            IOUtil.LOGGER.warning("Could not load resource: \"%s\"\n%s", resource, e);
        }
        return buffer != null ? buffer.clear() : null;
    }
    
    /**
     * Loads a file as a ByteBuffer.
     *
     * @param resource The path to the file.
     * @return The data as a ByteBuffer.
     */
    public static @Nullable ByteBuffer readFromFile(@NotNull String resource)
    {
        return readFromFile(resource, new int[1]);
    }
    
    /**
     * Saves a ByteBuffer to file.
     *
     * @param resource     The path to the file.
     * @param buffer       The data to save.
     * @param bytesWritten The number of bytes written to the file.
     * @return {@code true} if write as successful
     */
    public static boolean writeToFile(@NotNull String resource, @NotNull ByteBuffer buffer, int[] bytesWritten)
    {
        try (SeekableByteChannel fc = Files.newByteChannel(IOUtil.getPath(resource)))
        {
            bytesWritten[0] = 0;
            for (int read; (read = fc.write(buffer)) != -1; ) bytesWritten[0] += read;
            return true;
        }
        catch (IOException e)
        {
            IOUtil.LOGGER.warning("Could not save resource: \"%s\"\n%s", resource, e);
        }
        return false;
    }
    
    /**
     * Saves a ByteBuffer to file.
     *
     * @param resource The path to the file.
     * @param buffer   The data to save.
     * @return {@code true} if write as successful
     */
    public static boolean writeToFile(@NotNull String resource, @NotNull ByteBuffer buffer)
    {
        return writeToFile(resource, buffer, new int[1]);
    }
    
    /**
     * Gets the extension string for a file name string.
     * <p>
     * If the file name string is a directory or a file with no extension, then
     * an empty string is returned.
     * <p>
     * NOTE: The extension string includes a period ({@code .}).
     *
     * @param fileName The file name string.
     * @return The file extension with a period.
     */
    public static @NotNull String getExtension(@NotNull String fileName)
    {
        int  len = fileName.length();
        char ch  = fileName.charAt(len - 1);
        // in the case of a directory or in the case of . or ..
        if (ch == '/' || ch == '\\' || ch == '.') return "";
        int dotInd = fileName.lastIndexOf('.');
        int sepInd = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        if (dotInd <= sepInd) return "";
        return '.' + fileName.substring(dotInd + 1).toLowerCase();
    }
    
    private IOUtil() { }
}

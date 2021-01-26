package rutils.group;

import org.jetbrains.annotations.Nullable;

/**
 * A simple {@code String} tuple.
 */
public interface IQuadS extends IQuad<String, String, String, String>
{
    /**
     * @return The first String value.
     */
    @Nullable String a();
    
    /**
     * @return The second String value.
     */
    @Nullable String b();
    
    /**
     * @return The third String value.
     */
    @Nullable String c();
    
    /**
     * @return The fourth String value.
     */
    @Nullable String d();
}

package rutils.group;

import org.jetbrains.annotations.Nullable;

/**
 * A simple {@code String} pair.
 */
public interface IPairS extends IPair<String, String>
{
    /**
     * @return The first String value.
     */
    @Nullable String a();
    
    /**
     * @return The second String value.
     */
    @Nullable String b();
}

package rutils.group;

import org.jetbrains.annotations.Nullable;

/**
 * A simple {@code String} pair.
 */
public class PairS extends Pair<String, String> implements IPairS
{
    /**
     * Creates a new pair with two Strings.
     *
     * @param a The first String.
     * @param b The second String.
     */
    public PairS(@Nullable String a, @Nullable String b)
    {
        super(a, b);
    }
    
    /**
     * @return The first String value.
     */
    @Override
    public @Nullable String a()
    {
        return this.a;
    }
    
    /**
     * @return The second String value.
     */
    @Override
    public @Nullable String b()
    {
        return this.b;
    }
}

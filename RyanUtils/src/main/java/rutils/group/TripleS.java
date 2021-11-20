package rutils.group;

import org.jetbrains.annotations.Nullable;

/**
 * A simple {@code String} triple.
 */
public class TripleS extends Triple<String, String, String> implements ITripleS
{
    /**
     * Creates a new triple with three Strings.
     *
     * @param a The first String.
     * @param b The second String.
     * @param c The third String.
     */
    public TripleS(@Nullable String a, @Nullable String b, @Nullable String c)
    {
        super(a, b, c);
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
    
    /**
     * @return The third String value.
     */
    @Override
    public @Nullable String c()
    {
        return this.c;
    }
}

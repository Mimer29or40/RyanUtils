package rutils.group;

import org.jetbrains.annotations.Nullable;

/**
 * A simple {@code String} quad.
 */
public class QuadS extends Quad<String, String, String, String> implements IQuadS
{
    /**
     * Creates a new quad with four Strings.
     *
     * @param a The first String.
     * @param b The second String.
     * @param c The third String.
     * @param d The fourth String.
     */
    public QuadS(@Nullable String a, @Nullable String b, @Nullable String c, @Nullable String d)
    {
        super(a, b, c, d);
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
    
    /**
     * @return The fourth String value.
     */
    @Override
    public @Nullable String d()
    {
        return this.d;
    }
}

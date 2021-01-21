package rutils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;

public class StringUtil
{
    public static String toString(Object obj)
    {
        if (obj == null) return "null";
        
        if (obj instanceof Throwable)
        {
            final StringWriter sw = new StringWriter();
            ((Throwable) obj).printStackTrace(new PrintWriter(sw));
            return sw.getBuffer().toString();
        }
        if (obj.getClass().isArray())
        {
            if (obj instanceof boolean[]) return Arrays.toString((boolean[]) obj);
            if (obj instanceof byte[]) return Arrays.toString((byte[]) obj);
            if (obj instanceof short[]) return Arrays.toString((short[]) obj);
            if (obj instanceof char[]) return Arrays.toString((char[]) obj);
            if (obj instanceof int[]) return Arrays.toString((int[]) obj);
            if (obj instanceof long[]) return Arrays.toString((long[]) obj);
            if (obj instanceof float[]) return Arrays.toString((float[]) obj);
            if (obj instanceof double[]) return Arrays.toString((double[]) obj);
            return Arrays.deepToString((Object[]) obj);
        }
        return obj.toString();
    }
}

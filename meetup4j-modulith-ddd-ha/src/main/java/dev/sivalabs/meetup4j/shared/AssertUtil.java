package dev.sivalabs.meetup4j.shared;

public class AssertUtil {
    private AssertUtil() {}

    public static <T> T requireNotNull(T obj, String message) {
        if (obj == null)
            throw new IllegalArgumentException(message);
        return obj;
    }

    public static int requireMin(int value, int min, String message) {
        if (value < min)
            throw new IllegalArgumentException(message);
        return value;
    }
}

package dev.sivalabs.meetup4j.shared;

public class AssertUtil {
    private AssertUtil() {}

    public static <T> T requireNotNull(T obj, String message) {
        if (obj == null)
            throw new IllegalArgumentException(message);
        return obj;
    }
}

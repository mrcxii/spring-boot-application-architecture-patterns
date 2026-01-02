package dev.sivalabs.meetup4j.shared;

import io.hypersistence.tsid.TSID;

public class TSIDUtil {
    private TSIDUtil() {}

    public static String generateTsidString() {
        return TSID.Factory.getTsid().toString();
    }

    public static Long generateTsidLong() {
        return TSID.Factory.getTsid().toLong();
    }
}

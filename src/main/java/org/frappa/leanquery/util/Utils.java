package org.frappa.leanquery.util;

import java.time.Instant;
import java.time.LocalDateTime;

public class Utils {

    public static Instant toInstant(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant();
    }

}

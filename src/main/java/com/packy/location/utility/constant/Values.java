package com.packy.location.utility.constant;

import java.math.BigDecimal;

public enum Values {
    ;
    public static class common {
        public static final String ERROR_MESSAGE_SEPARATOR = ";";
    }

    public static class Location {
        public static final BigDecimal MAX_LATITUDE = new BigDecimal(90);
        public static final BigDecimal MIN_LATITUDE = new BigDecimal(-90);
        public static final BigDecimal MAX_LONGITUDE = new BigDecimal(180);
        public static final BigDecimal MIN_LONGITUDE = new BigDecimal(-180);
    }
}

package com.packy.location.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

public class FunctionalException extends RuntimeException {

    @Getter
    private FunctionalCategory functionalCategory;

    public FunctionalException(@NonNull FunctionalCategory errorCategory, @NonNull FunctionalMessage errorMessage, Object... messageParameters) {
        super(String.format(errorMessage.getValue(), messageParameters));
        functionalCategory = errorCategory;
    }

    @Builder
    public FunctionalException(@NonNull FunctionalCategory errorCategory, @NonNull FunctionalMessage errorMessage, Throwable cause, Object... messageParameters) {
        super(String.format(errorMessage.getValue(), messageParameters), cause);
        functionalCategory = errorCategory;
    }

    @AllArgsConstructor
    @Getter
    public enum FunctionalMessage {
        SET("%s"),
        INVALID_PARAMETERS("Invalid parameters [%s]"),
        LOCATION_ID_DOES_NOT_EXIT("No record for location id: %s"),
        MANDATORY_PARAMETER("Parameter %s should be provided"),
        NOT_POSITIVE_INTEGER_PARAMETER("Parameter %s should be an integer whose value is greater or equal than 1"),
        INVALID_LATITUDE_PARAMETER("Parameter %s should be between -90 and 90"),
        INVALID_LONGITUDE_PARAMETER("Parameter %s should be between -180 and 180"),
        BLANK_PARAMETER("Parameter %s should have a not empty or blank value");
        private String value;

        public String buildMessage(final Object... parameters) {
            return String.format(value, parameters);
        }
    }

    @AllArgsConstructor
    @Getter
    public enum FunctionalCategory {
        PARAMETER_VALIDATION,
        LOGIC_VALIDATION,
        ENTITY_NOT_FOUND,
        OPERATION_NOT_SUPPORTED
    }
}

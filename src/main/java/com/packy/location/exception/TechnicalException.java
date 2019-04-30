package com.packy.location.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

public class TechnicalException extends RuntimeException {
    
    public TechnicalException(@NonNull TechnicalMessage errorMessage, Object... messageParameters) {
        super(String.format(errorMessage.getValue(), messageParameters));
    }

    @Builder
    public TechnicalException(@NonNull TechnicalMessage errorMessage, Throwable cause, Object... messageParameters) {
        super(String.format(errorMessage.getValue(), messageParameters), cause);
    }

    @AllArgsConstructor
    @Getter
    public enum TechnicalMessage {
        SET("%s"),
        DATABASE_ERROR("Error occurred with Database, root cause: %s"),
        METHOD_ARG_TYPE_MISMATCH_ERROR("Please make sure the submitted parameters are in the correct format. Value %s is invalid for '%s'"),
        METHOD_NOT_SUPPORTED_ERROR("Method invoked %s should be of type : %s"),
        MISSING_PARAMETER_ERROR("Parameter %s is missing. Please make sure to submit all required parameters");
        private String value;

    }
}

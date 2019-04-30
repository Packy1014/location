package com.packy.location.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.packy.location.utility.constant.Values;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

import static com.packy.location.exception.FunctionalException.FunctionalMessage.*;
import static org.apache.commons.lang3.StringUtils.isBlank;

@AllArgsConstructor
@Getter
public enum ParameterValidator {

    MANDATORY_VALUE_VALIDATION((isMandatory, name, value) -> {
        if (value == null) {
            return MANDATORY_PARAMETER.buildMessage(name);
        }
        return null;
    }),
    STRING_VALUE_VALIDATION((isMandatory, name, value) -> {
        if (isMandatory && value == null) {
            return MANDATORY_PARAMETER.buildMessage(name);
        }
        return (value != null && isBlank((String) value)) ?
                BLANK_PARAMETER.buildMessage(name) : null;
    }),
    POSITIVE_INTEGER_VALIDATION((isMandatory, name, value) -> {
        if (isMandatory && value == null) {
            return MANDATORY_PARAMETER.buildMessage(name);
        }
        return (value != null && (Integer) value < 1) ?
                NOT_POSITIVE_INTEGER_PARAMETER.buildMessage(name) : null;
    }),
    LATITUDE_VALIDATION((isMandatory, name, value) -> {
        if (isMandatory && value == null) {
            return MANDATORY_PARAMETER.buildMessage(name);
        }
        return (value != null && (((BigDecimal) value).compareTo(Values.Location.MAX_LATITUDE) > 0
                || ((BigDecimal) value).compareTo(Values.Location.MIN_LATITUDE) < 0)) ?
                INVALID_LATITUDE_PARAMETER.buildMessage(name) : null;
    }),
    LONGITUDE_VALIDATION((isMandatory, name, value) -> {
        if (isMandatory && value == null) {
            return MANDATORY_PARAMETER.buildMessage(name);
        }
        return (value != null && (((BigDecimal) value).compareTo(Values.Location.MAX_LONGITUDE) > 0
                || ((BigDecimal) value).compareTo(Values.Location.MIN_LONGITUDE) < 0)) ?
                INVALID_LONGITUDE_PARAMETER.buildMessage(name) : null;
    });

    private Validation<Object, String> function;
    private static final ObjectMapper mapper = new ObjectMapper();

    @FunctionalInterface
    interface Validation<V, R> {
        R validate(boolean isMandatory, String parameterName, V value);
    }

}

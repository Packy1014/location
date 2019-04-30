package com.packy.location.validator;

import com.packy.location.exception.FunctionalException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashSet;
import java.util.Set;

import static com.packy.location.exception.FunctionalException.FunctionalCategory.PARAMETER_VALIDATION;
import static com.packy.location.exception.FunctionalException.FunctionalMessage.INVALID_PARAMETERS;
import static com.packy.location.utility.constant.Values.common.ERROR_MESSAGE_SEPARATOR;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParaValidationUtil {

    private Set<String> validationErrorMessages = new LinkedHashSet<>();

    public static ParaValidationUtil getInstance() {
        return new ParaValidationUtil();
    }

    public <T> ParaValidationUtil addMandatoryParamValidator(final String parameterName,
                                                             final T parameterValue,
                                                             final ParameterValidator parameterValidator) {
        return addParamValidator(true, parameterName, parameterValue, parameterValidator);
    }

    public <T> ParaValidationUtil addOptionalParamValidator(final String parameterName,
                                                            final T parameterValue,
                                                            final ParameterValidator parameterValidator) {
        return addParamValidator(false, parameterName, parameterValue, parameterValidator);
    }

    private <T> ParaValidationUtil addParamValidator(final boolean isMandatoryParameter,
                                                     final String parameterName,
                                                     final T parameterValue,
                                                     final ParameterValidator parameterValidator) {
        String errorMessage = parameterValidator.getFunction().validate(isMandatoryParameter, parameterName, parameterValue);
        if (errorMessage != null) {
            validationErrorMessages.add(errorMessage);
        }
        return this;
    }

    public void validate() {
        if (!validationErrorMessages.isEmpty()) {
            throw FunctionalException.builder()
                    .errorCategory(PARAMETER_VALIDATION)
                    .errorMessage(INVALID_PARAMETERS)
                    .messageParameters(new Object[]{StringUtils.join(validationErrorMessages, ERROR_MESSAGE_SEPARATOR)})
                    .build();
        }
    }

}

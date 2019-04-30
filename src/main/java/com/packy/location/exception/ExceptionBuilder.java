package com.packy.location.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.packy.location.exception.FunctionalException.FunctionalCategory.ENTITY_NOT_FOUND;
import static com.packy.location.exception.TechnicalException.TechnicalMessage.DATABASE_ERROR;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionBuilder {

    public static RuntimeException buildTechnicalException(TechnicalException.TechnicalMessage technicalMessage) {
        return buildTechnicalException(technicalMessage);
    }

    public static RuntimeException buildTechnicalException(TechnicalException.TechnicalMessage technicalMessage, Object... params) {
        return buildTechnicalException(technicalMessage, null, params);
    }

    public static RuntimeException buildTechnicalException(TechnicalException.TechnicalMessage technicalMessage, Throwable rootException, Object... params) {
        return TechnicalException.builder()
                .errorMessage(technicalMessage)
                .cause(rootException)
                .messageParameters(params)
                .build();
    }

    public static RuntimeException buildFunctionalException(FunctionalException.FunctionalCategory functionalCategory, FunctionalException.FunctionalMessage functionalMessage, Object... params) {
        return FunctionalException.builder()
                .errorCategory(functionalCategory)
                .errorMessage(functionalMessage)
                .messageParameters(params)
                .build();
    }

    public static TechnicalException buildDatabaseTechnicalException(Throwable rootException) {
        return TechnicalException.builder()
                .errorMessage(DATABASE_ERROR)
                .messageParameters(new Object[]{rootException.getMessage()})
                .cause(rootException)
                .build();
    }

    public static FunctionalException buildEntityNotFoundFunctionalException(FunctionalException.FunctionalMessage functionalMessage, String id) {
        return FunctionalException.builder()
                .errorCategory(ENTITY_NOT_FOUND)
                .errorMessage(functionalMessage)
                .messageParameters(new Object[]{id})
                .build();
    }

}

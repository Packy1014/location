package com.packy.location.controller.exceptionHandler;

import com.packy.location.exception.ExceptionBuilder;
import com.packy.location.exception.FunctionalException;
import com.packy.location.exception.TechnicalException;
import com.packy.location.model.inner.ExceptionInner;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.packy.location.exception.TechnicalException.TechnicalMessage.*;
import static com.packy.location.utility.constant.Values.common.ERROR_MESSAGE_SEPARATOR;
import static java.util.Collections.singletonList;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    public ExceptionHandlerController() {
        super();
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException exception,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        return buildExceptionResponse(
                METHOD_NOT_ALLOWED,
                singletonList(ExceptionBuilder
                        .buildTechnicalException(METHOD_NOT_SUPPORTED_ERROR, exception.getMethod(), exception.getSupportedHttpMethods())
                        .getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return buildExceptionResponse(
                BAD_REQUEST,
                singletonList(ExceptionBuilder
                        .buildTechnicalException(MISSING_PARAMETER_ERROR, exception.getParameterName())
                        .getMessage()));
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException exception) {
        return buildExceptionResponse(
                BAD_REQUEST,
                singletonList(ExceptionBuilder
                        .buildTechnicalException(METHOD_ARG_TYPE_MISMATCH_ERROR, exception.getValue(), exception.getName())
                        .getMessage()));
    }

    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<Object> handleTechnicalException(final TechnicalException technicalException) {
        return buildExceptionResponse(
                INTERNAL_SERVER_ERROR,
                parseErrorMessages(technicalException));
    }

    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<Object> handleFunctionalException(final FunctionalException functionalException) {
        ResponseEntityBuilder responseEntityBuilder = Try.of(() -> ResponseEntityBuilder.valueOf(functionalException.getFunctionalCategory().name()))
                .getOrElse(() -> ResponseEntityBuilder.DEFAULT);
        return responseEntityBuilder.buildFromFunctionalException.apply(functionalException);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleOtherException(final RuntimeException runtimeException) {
        return buildExceptionResponse(
                INTERNAL_SERVER_ERROR,
                parseErrorMessages(runtimeException));
    }

    @AllArgsConstructor
    @Getter
    private enum ResponseEntityBuilder {
        PARAMETER_VALIDATION(functionalException -> buildExceptionResponse(BAD_REQUEST, parseErrorMessages(functionalException))),
        ENTITY_NOT_FOUND(functionalException -> buildExceptionResponse(NOT_FOUND, parseErrorMessages(functionalException))),
        OPERATION_NOT_SUPPORTED(functionalException -> buildExceptionResponse(FORBIDDEN, parseErrorMessages(functionalException))),
        DEFAULT(functionalException -> buildExceptionResponse(INTERNAL_SERVER_ERROR, parseErrorMessages(functionalException)));
        private Function<FunctionalException, ResponseEntity<Object>> buildFromFunctionalException;
    }

    private static ResponseEntity<Object> buildExceptionResponse(final HttpStatus httpStatus, List<String> errorMessages) {
        ExceptionInner exceptionInner = ExceptionInner.builder()
                .httpStatus(httpStatus)
                .errors(errorMessages).build();
        return new ResponseEntity<>(exceptionInner, httpStatus);
    }

    private static List<String> parseErrorMessages(Exception exception) {
        return exception.getLocalizedMessage() != null ? Arrays.asList(exception.getLocalizedMessage().split(ERROR_MESSAGE_SEPARATOR)) : null;
    }
}

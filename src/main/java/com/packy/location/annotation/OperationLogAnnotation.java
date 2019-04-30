package com.packy.location.annotation;


import com.packy.location.model.inner.OperationLogInner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OperationLogAnnotation {
    /**
     * Module name
     */
    OperationLogInner.MODULE module();

    /**
     * Operation type
     */
    OperationLogInner.OPERATION_TYPE operationType();

    /**
     * Ignored Parameter class types, default includes HttpServletRequest and HttpServletResponse
     */
    Class[] ignoredParameterTypes() default {
            HttpServletRequest.class,
            HttpServletResponse.class
    };
}

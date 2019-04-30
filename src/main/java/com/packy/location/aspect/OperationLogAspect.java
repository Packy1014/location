package com.packy.location.aspect;

import com.packy.location.annotation.OperationLogAnnotation;
import com.packy.location.model.inner.OperationLogInner;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Component
@Aspect
@Log4j2
public class OperationLogAspect {

    @Pointcut("execution(* com.packy.location.controller..*.*(..))")
    public void pointcutMethod() {
    }

    @Around("pointcutMethod() && @annotation(operationLogAnnotation)")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint, OperationLogAnnotation operationLogAnnotation) throws Throwable {
        String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String methodName = proceedingJoinPoint.getSignature().getName();
        OperationLogInner operationLogInnerInput = OperationLogInner.builder()
                .operationTime(LocalDateTime.now())
                .operation(getOperationTypeAsString(operationLogAnnotation))
                .className(className)
                .methodName(methodName)
                .parameters(getParamDetailAsString(proceedingJoinPoint, operationLogAnnotation))
                .build();
        log.debug("[Method Start] " + operationLogInnerInput);
        Object result = proceedingJoinPoint.proceed();
        OperationLogInner operationLogInnerOutput = OperationLogInner.builder()
                .operationTime(LocalDateTime.now())
                .className(className)
                .methodName(methodName)
                .result(result)
                .build();
        log.debug("[Method End] " + operationLogInnerOutput);
        return result;
    }
    
    @SuppressWarnings("unchecked")
    private String getParamDetailAsString(ProceedingJoinPoint proceedingJoinPoint, OperationLogAnnotation operationLogAnnotation) {

        final Class[] ignoredParameterTypes = operationLogAnnotation.ignoredParameterTypes();
        final Object[] receivedParameters = proceedingJoinPoint.getArgs();

        final StringBuilder result = new StringBuilder();

        Arrays.stream(receivedParameters).forEach(receivedParameter ->
                Optional.ofNullable(receivedParameter).ifPresent(
                        receivedParameterNotNull -> {
                            for (Class ignoredParameterType : ignoredParameterTypes) {
                                if (ignoredParameterType.isAssignableFrom(receivedParameterNotNull.getClass())) {
                                    return;
                                }
                            }
                            result.append(receivedParameterNotNull.getClass().getSimpleName()).append(":").append(receivedParameterNotNull).append("| ");
                        }
                )
        );

        return result.length() == 0 ? null : result.toString();
    }

    private String getOperationTypeAsString(OperationLogAnnotation operationLogAnnotation) {
        return operationLogAnnotation.module() + "-" + operationLogAnnotation.operationType();
    }

}


package com.erocha.freeman.config.interceptor;

import com.erocha.freeman.app.domains.Response;
import com.erocha.freeman.app.domains.ResponseBuilder;
import com.erocha.freeman.commons.exceptions.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import static net.logstash.logback.argument.StructuredArguments.value;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ControllerAdvice
@Slf4j
public class RestErrorHandler extends DefaultHandlerExceptionResolver {

    private static final String STATUS = "status";
    private static final String PATH = "path";
    private static final String METHOD = "method";
    private static final String ERROR = "error";
    private static final String SPRING_MVC_EXCEPTION_HANDLER_CAPTURED = "Spring MVC Exception handler captured: {}";

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseBody
    public Response badCredentials(final BadCredentialsException ex, final HttpServletRequest request) {
        log.error(SPRING_MVC_EXCEPTION_HANDLER_CAPTURED, ex.getMessage(), ex, value(STATUS, UNAUTHORIZED.value()), value(ERROR, UNAUTHORIZED.getReasonPhrase()),
                value(PATH, request.getRequestURI()), value(METHOD, request.getMethod()));
        return ResponseBuilder.as(UNAUTHORIZED.value()).withMessage(ex.getMessage()).build();
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Response handleBusinessException(BusinessException ex, final HttpServletRequest request) {
        log.error(SPRING_MVC_EXCEPTION_HANDLER_CAPTURED, ex.getMessage(), ex, value(STATUS, UNPROCESSABLE_ENTITY.value()), value(PATH, request.getRequestURI()),
                value(METHOD, request.getMethod()));
        return ResponseBuilder.as(UNPROCESSABLE_ENTITY.value()).withMessage(ex.getMessage()).build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response handleException(Exception ex, final HttpServletRequest request) {
        log.error(SPRING_MVC_EXCEPTION_HANDLER_CAPTURED, ex.getMessage(), ex, value(STATUS, INTERNAL_SERVER_ERROR.value()),
                value(PATH, request.getRequestURI()), value(METHOD, request.getMethod()));
        log.error(ERROR, ex.fillInStackTrace());
        return ResponseBuilder.as(UNPROCESSABLE_ENTITY.value()).withMessage(ex.getMessage()).build();
    }
}

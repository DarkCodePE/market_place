package com.example.multisecurityspring.infrastructure.exception;

import com.example.multisecurityspring.infrastructure.exception.payload.WebErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    public static final String TRACE = "trace";
    @Autowired
    private MessageSource messageSource;

    @Value("${config.trace:false}")
    private boolean printStackTrace;

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        WebErrorResponse webErrorResponse = new WebErrorResponse(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Validation error. Check 'errors' field for details.");
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            webErrorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.unprocessableEntity().body(webErrorResponse);
    }

    @ExceptionHandler(UserLoginException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public WebErrorResponse handleUserLoginException(UserLoginException e){
        log.error("Authentication failed incorrect credentials", e);
        String message = messageSource.getMessage(e.getMessage(), e.getParams(), LocaleContextHolder.getLocale());
        return new WebErrorResponse(HttpStatus.EXPECTATION_FAILED.value(), message);
    }

    @ExceptionHandler(UserRegistrationException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public WebErrorResponse handleUserLoginException(UserRegistrationException e){
        log.error("Authentication failed incorrect credentials", e);
        String message = messageSource.getMessage(e.getMessage(), e.getParams(), LocaleContextHolder.getLocale());
        return new WebErrorResponse(HttpStatus.EXPECTATION_FAILED.value(), message);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public WebErrorResponse handleAccessDeniedException(
            Exception e, WebRequest request) {
        log.error("Access is denied", e);
        return new WebErrorResponse(HttpStatus.FORBIDDEN.value(), e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public WebErrorResponse handleNotFoundException(EntityNotFoundException e){
        log.error("Bad request wrong items", e);
        String message = messageSource.getMessage(e.getMessage(), e.getParams(), LocaleContextHolder.getLocale());
        return new WebErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
    }

    @ExceptionHandler(NoSuchElementFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public WebErrorResponse handleNoSuchElementFoundException(NoSuchElementFoundException itemNotFoundException, WebRequest request) {
        log.error("Failed to find the requested element", itemNotFoundException);
        return new WebErrorResponse(HttpStatus.NOT_FOUND.value(), itemNotFoundException.getMessage());
    }
    @ExceptionHandler(InvalidTokenRequestException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public WebErrorResponse handleInvalidTokenRequestException(InvalidTokenRequestException e){
        log.error("Expired token", e);
        String message = messageSource.getMessage(e.getMessage(), e.getParams(), LocaleContextHolder.getLocale());
        return new WebErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), message);
    }

    @ExceptionHandler(value = MailSendException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public WebErrorResponse handleMailSendException(MailSendException e, WebRequest request) {
        log.error("Error sending mail verification", e);
        String message = messageSource.getMessage(e.getMessage(), e.getParams(), LocaleContextHolder.getLocale());
        return new WebErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), message);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public WebErrorResponse handleAllUncaughtException(Exception exception, WebRequest request) {
        log.error("Unknown error occurred", exception);
        WebErrorResponse webErrorResponse = new WebErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Unknown error occurred");
        if (printStackTrace && isTraceOn(request)) {
            webErrorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }
        return webErrorResponse;
    }

    private boolean isTraceOn(WebRequest request) {
        String[] value = request.getParameterValues(TRACE);
        return Objects.nonNull(value)
                && value.length > 0
                && value[0].contentEquals("true");
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception e,
                                                             Object body,
                                                             HttpHeaders headers,
                                                             HttpStatus status,
                                                             WebRequest request) {
        WebErrorResponse webErrorResponse = new WebErrorResponse(status.value(), e.getMessage());
        if (printStackTrace && isTraceOn(request)) {
            webErrorResponse.setStackTrace(ExceptionUtils.getStackTrace(e));
        }
        return ResponseEntity.status(status).body(webErrorResponse);
    }
}

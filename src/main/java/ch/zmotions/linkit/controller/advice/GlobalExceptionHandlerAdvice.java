package ch.zmotions.linkit.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandlerAdvice {

    private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandlerAdvice.class);

    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "An error occured. If it persists, please contact your Administrator.";
        return handleExceptionInternal(ex, bodyOfResponse, HttpStatus.CONFLICT,
                new HttpHeaders(), request);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDenied(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Access Denied. You have no access to this page.";
        return handleExceptionInternal(ex, bodyOfResponse, HttpStatus.UNAUTHORIZED,
                new HttpHeaders(), request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception ex, String bodyOfResponse, HttpStatus status,
                                                           HttpHeaders httpHeaders, WebRequest request) {
        StringBuilder stackTrace = new StringBuilder();
        for (StackTraceElement elem : ex.getStackTrace()) {
            stackTrace.append(elem.toString()).append("\n");
        }
        LOGGER.warn("Caught by global Handler: " + "\n" +
                "Exception:" + stackTrace.toString() + "\n" +
                "Body of Response:" + bodyOfResponse + "\n" +
                "Http Headers: " + httpHeaders.toString() + "\n" +
                "HttpStatus: " + status + "\n" +
                "Request: " + request.toString());
        return ResponseEntity.status(status).body(bodyOfResponse);
    }

}
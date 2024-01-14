package sbankpaymentms.exception.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import sbankpaymentms.exception.base.InvalidStateException;
import sbankpaymentms.exception.base.NotFoundException;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends DefaultErrorAttributes {

    public static final String STATUS = "status";
    public static final String MESSAGE = "message";
    public static final String PATH = "path";
    public static final String ERROR = "error";
    public static final String TIMESTAMP = "timestamp";

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handle(NotFoundException ex, WebRequest request) {
        log.error("Resource not found {}", ex.getMessage(), ex);
        return handleError(request, HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidStateException.class)
    public ResponseEntity<Map<String, Object>> handle(InvalidStateException ex, WebRequest request) {
        log.error("Request is invalid state {}", ex.getMessage(), ex);
        return handleError(request, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handle(HttpMessageNotReadableException ex, WebRequest request) {
        log.error("Request is invalid format {}", ex.getMessage(), ex);
        return handleError(request, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handle(ConstraintViolationException ex, WebRequest request) {
        log.error("Constraints violated {}", ex.getMessage(), ex);
        return handleError(request, HttpStatus.BAD_REQUEST, ex.getMessage());

    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handle(MethodArgumentTypeMismatchException ex, WebRequest request) {
        log.error("Method arguments are not valid {}", ex.getMessage(), ex);
        return handleError(request, HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<Map<String, Object>> handle(MissingRequestHeaderException ex,
                                                      WebRequest request) {
        log.error("Missing request header {}", ex.getMessage(), ex);
        return handleError(request, HttpStatus.BAD_REQUEST, ex.getMessage());

    }

    private ResponseEntity<Map<String, Object>> handleError(WebRequest request, HttpStatus status, String message) {
        Map<String, Object> attributes = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        attributes.put(STATUS, status.value());
        attributes.put(ERROR, status.getReasonPhrase());
        attributes.put(MESSAGE, message);
        attributes.put(PATH, ((ServletWebRequest) request).getRequest().getRequestURI());
        attributes.put(TIMESTAMP, LocalDateTime.now());
        return new ResponseEntity<>(attributes, status);
    }

}



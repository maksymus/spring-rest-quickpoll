package org.homenet.rest.quickpoll.controller.v1.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

// extends to handle
// @ExceptionHandler({NoSuchRequestHandlingMethodException.class, HttpRequestMethodNotSupportedException.class,
// HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class, MissingPathVariableException.class,
// MissingServletRequestParameterException.class, ServletRequestBindingException.class, ConversionNotSupportedException.class,
// TypeMismatchException.class, HttpMessageNotReadableException.class, HttpMessageNotWritableException.class,
// MethodArgumentNotValidException.class, MissingServletRequestPartException.class, BindException.class,
// NoHandlerFoundException.class, AsyncRequestTimeoutException.class})
@ControllerAdvice(basePackages = "org.homenet.rest.quickpoll.controller")
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException rnfe, HttpServletRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(HttpStatus.NOT_FOUND.value());
        errorDetail.setTitle("Resource Not Found");
        errorDetail.setDetails(rnfe.getMessage());
        errorDetail.setDeveloperMessage(rnfe.getClass().getName());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetail);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().getTime());
        errorDetail.setStatus(status.value());
        errorDetail.setTitle(status.getReasonPhrase());
        errorDetail.setDetails(ex.getMessage());
        errorDetail.setDeveloperMessage(ex.getClass().getName());

        if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException manvEx = (MethodArgumentNotValidException) ex;
            List<FieldError> fieldErrors = manvEx.getBindingResult().getFieldErrors();
            fieldErrors.forEach(err -> {
                Map<String, List<ValidationError>> errors = errorDetail.getErrors();
                errors.putIfAbsent(err.getField(), new ArrayList<>());
                errors.get(err.getField()).add(new ValidationError(err.getCode(), err.getDefaultMessage()));
            });
        }

        return super.handleExceptionInternal(ex, errorDetail, headers, status, request);
    }
}

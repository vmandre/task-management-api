package nz.co.solnet.api.exceptionhandler;

import lombok.AllArgsConstructor;
import nz.co.solnet.domain.exception.TaskNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ExceptionMessage.Field> fields = new ArrayList<>();
        ex.getBindingResult()
                .getAllErrors()
                .forEach(objectError -> {
                    String name = ((FieldError)objectError).getField();
                    String message = "Invalid field";

                    fields.add(new ExceptionMessage.Field(name, message));
                });

        ExceptionMessage exceptionMessage = buildExceptionMessage(status.value(), "Bad request");
        exceptionMessage.setFields(fields);

        return super.handleExceptionInternal(ex, exceptionMessage, headers, status, request);
    }

    @ExceptionHandler(HttpClientErrorException.BadRequest.class)
    public ResponseEntity<Object> handleBadRequestException(RuntimeException ex, WebRequest request) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ExceptionMessage exceptionMessage = buildExceptionMessage(badRequest.value(), ex.getMessage());

        return super.handleExceptionInternal(ex, exceptionMessage, new HttpHeaders(), badRequest, request);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Object> handleTaskNotFoundException(RuntimeException ex, WebRequest request) {
        HttpStatus notFound = HttpStatus.NOT_FOUND;

        ExceptionMessage exceptionMessage = buildExceptionMessage(notFound.value(), ex.getMessage());

        return super.handleExceptionInternal(ex, exceptionMessage, new HttpHeaders(), notFound, request);
    }

    private ExceptionMessage buildExceptionMessage(Integer statusCode, String message) {
        ExceptionMessage exMessage = new ExceptionMessage();
        exMessage.setError(message);
        exMessage.setStatus(statusCode);
        exMessage.setTimestamp(OffsetDateTime.now());

        return exMessage;
    }
}

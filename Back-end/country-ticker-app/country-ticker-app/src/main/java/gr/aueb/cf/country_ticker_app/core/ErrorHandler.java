package gr.aueb.cf.country_ticker_app.core;

import gr.aueb.cf.country_ticker_app.core.exceptions.*;
import gr.aueb.cf.country_ticker_app.dto.ResponseMessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Global error handler for managing exceptions across the application.
 * <p>
 * This class centralizes exception handling logic and provides meaningful responses
 * for different types of exceptions encountered in controllers.
 * </p>
 *
 * <b>Note:</b> This class leverages Spring's {@link ControllerAdvice} annotation.
 *
 * @author ilias
 */
@ControllerAdvice
public class ErrorHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles {@link ValidationException} by returning validation errors as key-value pairs.
     *
     * @param ex the exception containing validation errors
     * @return a {@link ResponseEntity} with the validation errors and a BAD_REQUEST status
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String , String>> handleValidationException(ValidationException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        Map<String , String> errors = new HashMap<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link AppObjectNotFoundException} by returning a NOT_FOUND response.
     *
     * @param e the exception indicating the object was not found
     * @return a {@link ResponseEntity} containing the error code and message
     */
    @ExceptionHandler({AppObjectNotFoundException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppObjectNotFoundException e) {
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Handles {@link AppObjectAlreadyExists} by returning a CONFLICT response.
     *
     * @param e the exception indicating the object already exists
     * @return a {@link ResponseEntity} containing the error code and message
     */
    @ExceptionHandler({AppObjectAlreadyExists.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppObjectAlreadyExists e) {
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.CONFLICT);
    }

    /**
     * Handles {@link AppObjectInvalidArgumentException} by returning a BAD_REQUEST response.
     *
     * @param e the exception indicating invalid arguments
     * @return a {@link ResponseEntity} containing the error code and message
     */
    @ExceptionHandler({AppObjectInvalidArgumentException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppObjectInvalidArgumentException e) {
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles {@link AppObjectNotAuthorizedException} by returning a FORBIDDEN response.
     *
     * @param e the exception indicating unauthorized access
     * @return a {@link ResponseEntity} containing the error code and message
     */
    @ExceptionHandler({AppObjectNotAuthorizedException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppObjectNotAuthorizedException e) {
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.FORBIDDEN);
    }

    /**
     * Handles {@link AppServerException} by returning an INTERNAL_SERVER_ERROR response.
     *
     * @param e the exception indicating a server-side error
     * @return a {@link ResponseEntity} containing the error code and message
     */
    @ExceptionHandler({AppServerException.class})
    public ResponseEntity<ResponseMessageDTO> handleConstraintViolationException(AppServerException e) {
        return new ResponseEntity<>(new ResponseMessageDTO(e.getCode(), e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

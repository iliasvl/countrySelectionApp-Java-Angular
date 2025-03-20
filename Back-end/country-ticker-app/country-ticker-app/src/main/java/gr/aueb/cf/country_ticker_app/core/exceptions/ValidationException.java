package gr.aueb.cf.country_ticker_app.core.exceptions;

import lombok.Getter;
import org.springframework.validation.BindingResult;

/**
 * Exception to indicate validation errors.
 * <p>
 * This exception is thrown when validation on input data fails. It contains details about the failed validation.
 * </p>
 *
 * @author ilias
 */
@Getter
public class ValidationException extends Exception {
    private final BindingResult bindingResult;

    /**
     * Constructs a new {@code ValidationException} with the specified {@code BindingResult}.
     *
     * @param bindingResult the result of the failed validation
     */
    public ValidationException(BindingResult bindingResult) {
        super("Validation failed");
        this.bindingResult = bindingResult;
    }

}

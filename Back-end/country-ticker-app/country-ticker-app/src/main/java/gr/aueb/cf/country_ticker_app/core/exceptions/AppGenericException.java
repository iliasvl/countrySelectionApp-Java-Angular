package gr.aueb.cf.country_ticker_app.core.exceptions;

import lombok.Getter;


/**
 * A generic exception for the application.
 * <p>
 * This exception serves as a base class for other custom exceptions and allows the use of custom error codes
 * alongside standard error messages.
 * </p>
 *
 * @author ilias
 */
@Getter
public class AppGenericException extends Exception {
    private final String code;

    /**
     * Constructs a new {@code AppGenericException} with the specified code and message.
     *
     * @param code    the custom error code
     * @param message the detailed error message
     */
    public AppGenericException(String code, String message) {
        super(message);
        this.code = code;
    }
}

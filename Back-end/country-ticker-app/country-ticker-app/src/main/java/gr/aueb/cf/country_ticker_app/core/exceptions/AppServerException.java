package gr.aueb.cf.country_ticker_app.core.exceptions;

import lombok.Getter;

/**
 * Exception to indicate server-side errors.
 * <p>
 * This exception is thrown when an internal server error occurs during application execution.
 * </p>
 *
 * @author ilias
 */
@Getter
public class AppServerException extends Exception {
    private final String code;

    /**
     * Constructs a new {@code AppServerException} with the specified code and message.
     *
     * @param code    the custom error code
     * @param message the detailed error message
     */
    public AppServerException(String code, String message) {
        super(message);
        this.code = code;
    }
}

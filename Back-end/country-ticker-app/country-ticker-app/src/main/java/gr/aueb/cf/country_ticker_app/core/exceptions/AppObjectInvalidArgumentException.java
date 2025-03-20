package gr.aueb.cf.country_ticker_app.core.exceptions;

/**
 * Exception to indicate invalid arguments.
 * <p>
 * This exception is thrown when invalid arguments are provided for an operation or method.
 * </p>
 *
 * @author ilias
 */
public class AppObjectInvalidArgumentException extends AppGenericException {
    private static final String DEFAULT_CODE = "InvalidArgument";

    /**
     * Constructs a new {@code AppObjectInvalidArgumentException} exception with the specified code and message.
     * The provided code is prefixed with the default code "InvalidArgument".
     *
     * @param code    the custom error code
     * @param message the detailed error message
     */
    public AppObjectInvalidArgumentException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}


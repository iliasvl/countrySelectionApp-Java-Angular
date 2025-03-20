package gr.aueb.cf.country_ticker_app.core.exceptions;

/**
 * Exception to indicate that an object was not found.
 * <p>
 * This exception is thrown when a requested object cannot be located within the system.
 * </p>
 *
 * @author ilias
 */
public class AppObjectNotFoundException extends AppGenericException {
    private static final String DEFAULT_CODE = "NotFound";

    /**
     * Constructs a new {@code AppObjectNotFoundException} with the specified code and message.
     * The provided code is prefixed with the default code "NotFound".
     *
     * @param code    the custom error code
     * @param message the detailed error message
     */
    public AppObjectNotFoundException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}

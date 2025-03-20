package gr.aueb.cf.country_ticker_app.core.exceptions;

/**
 * Exception to indicate that an object already exists.
 * <p>
 * This exception is thrown when an attempt is made to create or add an object that already exists in the system.
 * </p>
 *
 * @author ilias
 */
public class AppObjectAlreadyExists extends AppGenericException {
    private static final String DEFAULT_CODE = "AlreadyExists";

    /**
     * Constructs a new {@code AppObjectAlreadyExists} exception with the specified code and message.
     * The provided code is prefixed with the default code "AlreadyExists".
     *
     * @param code    the custom error code
     * @param message the detailed error message
     */
    public AppObjectAlreadyExists(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}

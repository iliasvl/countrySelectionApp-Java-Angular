package gr.aueb.cf.country_ticker_app.core.exceptions;

/**
 * Exception to indicate unauthorized access.
 * <p>
 * This exception is thrown when an operation or action is attempted without proper authorization.
 * </p>
 *
 * @author ilias
 */
public class AppObjectNotAuthorizedException extends AppGenericException {

    private static final String DEFAULT_CODE = "NotAuthorized";

    /**
     * Constructs a new {@code AppObjectNotAuthorizedException} with the specified code and message.
     * The provided code is prefixed with the default code "NotAuthorized".
     *
     * @param code    the custom error code
     * @param message the detailed error message
     */
    public AppObjectNotAuthorizedException(String code, String message) {
        super(code + DEFAULT_CODE, message);
    }
}

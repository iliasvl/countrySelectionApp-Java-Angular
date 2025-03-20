package gr.aueb.cf.country_ticker_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for encapsulating response messages.
 * <p>
 * This class is used to transfer response-related data, typically error or success messages, in a structured manner.
 * </p>
 *
 * <b>Note:</b> The description field is optional and can be left empty.
 *
 * @author ilias
 */
@Data
@AllArgsConstructor
public class ResponseMessageDTO {

    private String code;
    private String description;

    /**
     * Constructs a new {@code ResponseMessageDTO} with the specified code and an empty description.
     *
     * @param code the response code
     */
    public ResponseMessageDTO(String code) {
        this.code = code;
        this.description = "";
    }
}

package gr.aueb.cf.country_ticker_app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for authentication responses.
 * <p>
 * This class is used to transfer authentication-related information such as the
 * user's name, token, and ID upon successful authentication.
 * </p>
 *
 * @author ilias
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDTO {
    private String firstname;
    private String lastname;
    private String token;
    private Long travellerId;
}

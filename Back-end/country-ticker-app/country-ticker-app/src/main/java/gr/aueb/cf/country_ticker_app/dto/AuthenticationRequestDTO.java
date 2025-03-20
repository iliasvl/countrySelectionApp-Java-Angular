package gr.aueb.cf.country_ticker_app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for authentication requests.
 * <p>
 * This class is used to transfer authentication data such as the username and password
 * during a login request.
 * </p>
 *
 * @author ilias
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestDTO {

    @NotNull
    private String username;

    @NotNull
    private String password;
}

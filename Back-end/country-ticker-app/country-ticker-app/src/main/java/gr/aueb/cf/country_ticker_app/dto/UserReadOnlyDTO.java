package gr.aueb.cf.country_ticker_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for reading user data in a read-only manner.
 * <p>
 * This class is used to transfer limited user details such as first name, last name, and username for display purposes.
 * </p>
 *
 * @author ilias
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserReadOnlyDTO {
    private String firstname;
    private String lastname;
    private String username;
}

package gr.aueb.cf.country_ticker_app.dto;

import gr.aueb.cf.country_ticker_app.core.enums.GenderType;
import gr.aueb.cf.country_ticker_app.core.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * DTO for inserting user data.
 * <p>
 * This class is used to transfer user-related details during creation operations, including authentication and profile data.
 * </p>
 *
 * <b>Note:</b> Validation constraints are applied to ensure the correctness of the input data.
 *
 * @author ilias
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserInsertDTO {

    @NotNull(message = "Is active must not be null")
    private Boolean isActive;

    @NotEmpty(message = "Firstname is required")
    private String firstname;

    @NotEmpty(message = "Lastname is required")
    private String lastname;

    @NotEmpty(message = "Username/Email is required")
    @Email(message = "Email is not valid")
    private String username;

    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[!@#$%^&*]).{8,}$")
    private String password;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotNull(message = "Gender is required")
    private GenderType gender;

    @NotNull(message = "Role is required")
    private Role role;
}

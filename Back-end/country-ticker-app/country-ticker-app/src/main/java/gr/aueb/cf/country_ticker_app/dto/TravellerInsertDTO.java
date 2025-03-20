package gr.aueb.cf.country_ticker_app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * DTO for inserting traveller data.
 * <p>
 * This class is used to transfer traveller-related data required during creation operations.
 * </p>
 *
 * <b>Note:</b> All fields are mandatory and validated using constraints.
 *
 * @author ilias
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TravellerInsertDTO {

    @NotNull(message = "Is active must not be null")
    private Boolean isActive;

    @NotNull(message = "User details must not be null")
    private UserInsertDTO user;

    @NotNull(message = "Personal information must not be null")
    private PersonalInfoInsertDTO personalInfo;
}

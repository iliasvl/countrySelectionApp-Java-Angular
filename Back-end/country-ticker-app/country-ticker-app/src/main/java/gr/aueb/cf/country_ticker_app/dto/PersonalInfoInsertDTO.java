package gr.aueb.cf.country_ticker_app.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for inserting personal information.
 * <p>
 * This class is used to transfer the personal details such as phone, address, city, and country
 * required during data insertion or creation operations.
 * </p>
 *
 * <b>Note:</b> All fields are mandatory, and validation constraints ensure non-empty values.
 *
 * @author ilias
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonalInfoInsertDTO {

    @NotEmpty(message = "Phone is required")
    private String phone;

    @NotEmpty(message = "Address is required")
    private String address;

    @NotEmpty(message = "City is required")
    private String city;

    @NotEmpty(message = "Country is required")
    private String country;
}

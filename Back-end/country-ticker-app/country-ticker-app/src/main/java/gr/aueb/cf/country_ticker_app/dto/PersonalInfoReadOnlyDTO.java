package gr.aueb.cf.country_ticker_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for reading personal information in a read-only manner.
 * <p>
 * This class is used to transfer personal details such as phone, address, city, and country
 * during read operations.
 * </p>
 *
 * <b>Note:</b> All fields are optional and primarily intended for display purposes.
 *
 * @author ilias
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PersonalInfoReadOnlyDTO {

    private String phone;

    private String address;

    private String city;

    private String country;
}

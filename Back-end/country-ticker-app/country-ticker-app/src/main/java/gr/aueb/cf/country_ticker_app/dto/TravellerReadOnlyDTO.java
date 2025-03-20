package gr.aueb.cf.country_ticker_app.dto;

import gr.aueb.cf.country_ticker_app.model.static_data.Country;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

/**
 * DTO for reading traveller data in a read-only manner.
 * <p>
 * This class is used to transfer traveller-related data, including associated user and country information,
 * for display purposes or read operations.
 * </p>
 *
 * <b>Note:</b> This is a comprehensive data object including nested DTOs and collections.
 *
 * @author ilias
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TravellerReadOnlyDTO {

    private Long id;

    private String uuid;

    private Boolean isActive;

    private UserReadOnlyDTO user;

    private PersonalInfoReadOnlyDTO personalInfo;

    private Set<Country> countries;
}

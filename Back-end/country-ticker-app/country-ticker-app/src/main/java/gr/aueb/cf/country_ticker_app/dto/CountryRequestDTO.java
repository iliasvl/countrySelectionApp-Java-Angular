package gr.aueb.cf.country_ticker_app.dto;

import lombok.*;

import java.util.List;

/**
 * DTO for handling requests related to multiple countries.
 * <p>
 * This class is used to transfer a list of country IDs, typically for batch operations.
 * </p>
 *
 * @author ilias
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CountryRequestDTO {

    private List<Long> countryIds;
}

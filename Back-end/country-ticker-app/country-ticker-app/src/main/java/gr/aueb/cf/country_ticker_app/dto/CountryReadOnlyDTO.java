package gr.aueb.cf.country_ticker_app.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO for reading country data in a read-only manner.
 * <p>
 * This class is used to transfer data for displaying or reading country-related information.
 * </p>
 *
 * @author ilias
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CountryReadOnlyDTO {
    private Long id;
    private String name;
    private String code;
    private String flagUrl;
}

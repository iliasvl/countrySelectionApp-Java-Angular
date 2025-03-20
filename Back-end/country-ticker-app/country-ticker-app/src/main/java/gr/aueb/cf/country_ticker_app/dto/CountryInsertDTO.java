package gr.aueb.cf.country_ticker_app.dto;

import lombok.*;

/**
 * DTO for inserting country data.
 * <p>
 * This class is used to transfer data required for creating or updating a country entity.
 * </p>
 *
 * @author ilias
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CountryInsertDTO {
    private Long id;
    private String name;
    private String code;
    private String flagUrl;
}


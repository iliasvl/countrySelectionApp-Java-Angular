package gr.aueb.cf.country_ticker_app.core.filters;

import lombok.*;
import org.springframework.lang.Nullable;

/**
 * Filter class for traveller-related queries.
 * <p>
 * Extends {@link GenericFilters} to include traveller-specific filtering options.
 * </p>
 *
 * @author ilias
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TravellerFilters extends GenericFilters {

    @Nullable
    private String uuid;

    @Nullable
    private Boolean active;



}

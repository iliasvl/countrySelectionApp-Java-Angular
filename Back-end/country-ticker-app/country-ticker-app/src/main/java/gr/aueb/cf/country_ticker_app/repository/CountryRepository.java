package gr.aueb.cf.country_ticker_app.repository;

import gr.aueb.cf.country_ticker_app.model.static_data.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for managing {@link Country} entities.
 * <p>
 * This interface provides CRUD operations and custom queries for the "countries" table.
 * </p>
 *
 * @author ilias
 */
public interface CountryRepository extends JpaRepository<Country, Long> {
    List<Country> findAllById(Iterable<Long> ids);
}

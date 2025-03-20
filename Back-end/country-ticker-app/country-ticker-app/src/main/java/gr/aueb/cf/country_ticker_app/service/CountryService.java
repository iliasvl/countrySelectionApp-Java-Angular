package gr.aueb.cf.country_ticker_app.service;

import gr.aueb.cf.country_ticker_app.model.static_data.Country;
import gr.aueb.cf.country_ticker_app.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Service class for managing country-related operations.
 * <p>
 * This class provides methods to retrieve all countries and find specific countries by their IDs.
 * </p>
 *
 * <b>Features:</b>
 * - Fetching all countries.
 * - Fetching a set of countries by their IDs.
 *
 * @see CountryRepository
 * @see Country
 *
 * @author ilias
 */
@Service
@RequiredArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;

    /**
     * Retrieves all countries from the database.
     *
     * @return a {@link List} of all {@link Country} objects
     */
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    /**
     * Finds all countries by their IDs.
     *
     * @param ids a {@link Set} of country IDs to search for
     * @return a {@link Set} of matching {@link Country} objects
     */
    public Set<Country> findAllById(Set<Long> ids) {
        return new HashSet<>(countryRepository.findAllById(ids));
    }
}

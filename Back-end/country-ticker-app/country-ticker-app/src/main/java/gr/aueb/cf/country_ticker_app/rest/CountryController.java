package gr.aueb.cf.country_ticker_app.rest;


import gr.aueb.cf.country_ticker_app.model.static_data.Country;
import gr.aueb.cf.country_ticker_app.service.CountryService;
import gr.aueb.cf.country_ticker_app.service.TravellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * REST controller for country-related operations.
 * <p>
 * This class handles API requests related to retrieving countries.
 * </p>
 *
 * <b>Endpoints:</b>
 * - GET /api/countries
 *
 * @author ilias
 */
@RestController
@RequestMapping("/api/countries")
@RequiredArgsConstructor
@Tag(name = "Countries", description = "Endpoints for managing countries")
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);
    private final CountryService countryService;
    private final TravellerService travellerService;

    /**
     * Retrieves a list of all countries.
     *
     * @return a {@link ResponseEntity} containing the list of countries
     */
    @GetMapping
    @Operation(summary = "Get all countries", description = "Returns a list of all available countries")
    public ResponseEntity<List<Country>> getAllCountries() {
        List<Country> countries = countryService.getAllCountries();
        return ResponseEntity.ok(countries);
    }

}

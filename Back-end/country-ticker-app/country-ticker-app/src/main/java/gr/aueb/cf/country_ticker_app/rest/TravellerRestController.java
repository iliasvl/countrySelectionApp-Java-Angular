package gr.aueb.cf.country_ticker_app.rest;

import gr.aueb.cf.country_ticker_app.core.exceptions.*;
import gr.aueb.cf.country_ticker_app.core.filters.Paginated;
import gr.aueb.cf.country_ticker_app.core.filters.TravellerFilters;
import gr.aueb.cf.country_ticker_app.dto.CountryRequestDTO;
import gr.aueb.cf.country_ticker_app.dto.TravellerInsertDTO;
import gr.aueb.cf.country_ticker_app.dto.TravellerReadOnlyDTO;
import gr.aueb.cf.country_ticker_app.model.User;
import gr.aueb.cf.country_ticker_app.repository.UserRepository;
import gr.aueb.cf.country_ticker_app.service.TravellerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.List;

/**
 * REST controller for traveller-related operations.
 * <p>
 * This class handles API requests related to traveller management, such as creating, updating, and retrieving travellers.
 * </p>
 *
 * <b>Endpoints:</b>
 * - GET /api/travellers
 * - POST /api/travellers/save
 * - GET /api/travellers/{travellerId}
 * - POST /api/travellers/{travellerId}/add
 * - PATCH /api/travellers/{travellerId}/remove-country/{countryId}
 * - POST /api/travellers/all/paginated
 * - GET /api/travellers/{travellerId}/total-travelled-countries
 * - GET /api/travellers/{travellerId}/total-untravelled-countries
 * - PATCH /api/travellers/{id}/deactivate
 * <b>Pre-authorization:</b>
 * Certain endpoints require the user to have specific roles or authorities.
 *
 * @author ilias
 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Travellers", description = "Endpoints for managing travellers")
public class TravellerRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TravellerRestController.class);
    private final TravellerService travellerService;
    private final UserRepository userRepository;

// ------ Not used because /travellers/all/paginated covers both filtering and pagination ------
//    @GetMapping("/travellers/paginated")
//    public ResponseEntity<Page<TravellerReadOnlyDTO>> getPaginatedTravellers(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "2") int size) {
//
//        Page<TravellerReadOnlyDTO> travellersPage = travellerService.getPaginatedTravellers(page, size);
//        return new ResponseEntity<>(travellersPage, HttpStatus.OK);
//    }


    /**
     * Retrieves a list of all travellers.
     *
     * @return a {@link ResponseEntity} containing the list of traveller DTOs
     */
    @Operation(
            summary = "Retrieve all travellers",
            description = "Returns a list of all travellers in the system",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of travellers returned"),
                    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
            }
    )
    @GetMapping("/travellers")
    public ResponseEntity<List<TravellerReadOnlyDTO>> getAllTravellers() {
        List<TravellerReadOnlyDTO> allTravellers = travellerService.getAllTravellers();
        return ResponseEntity.ok(allTravellers);
    }


    /**
     * Saves a new traveller to the system.
     *
     * @param travellerInsertDTO the DTO containing the traveller's data
     * @param bindingResult      validation results for the traveller data
     * @return a {@link ResponseEntity} containing the saved traveller DTO
     * @throws AppObjectInvalidArgumentException if the request contains invalid arguments
     * @throws ValidationException              if validation fails
     * @throws AppObjectAlreadyExists           if the traveller already exists
     * @throws AppServerException               if an internal server error occurs
     */
    @Operation(summary = "Register a new traveler", description = "Creates a new traveler account and returns the saved traveler details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Traveler created successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TravellerReadOnlyDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid traveler data provided"),
            @ApiResponse(responseCode = "409", description = "Traveler already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/travellers/save")
    public ResponseEntity<TravellerReadOnlyDTO> saveTraveller(
            @Valid @RequestBody TravellerInsertDTO travellerInsertDTO,
            BindingResult bindingResult) throws AppObjectInvalidArgumentException, ValidationException, AppObjectAlreadyExists, AppServerException {


        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }

        try {
            TravellerReadOnlyDTO travellerReadOnlyDTO = travellerService.saveTraveller(travellerInsertDTO);
            return new ResponseEntity<>(travellerReadOnlyDTO, HttpStatus.OK);
        } catch (Exception e) {
            throw new AppServerException("An error occurred while saving the traveller", e.getMessage());
        }
    }

    /**
     * Retrieves a traveller by their ID.
     *
     * @param travellerId the ID of the traveller to retrieve
     * @return a {@link ResponseEntity} containing the traveller DTO
     */
    @Operation(summary = "Get traveler by ID", description = "Retrieves a traveler based on their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Traveler found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TravellerReadOnlyDTO.class))),
            @ApiResponse(responseCode = "404", description = "Traveler not found")
    })
    @GetMapping("/travellers/{travellerId}")
    public ResponseEntity<TravellerReadOnlyDTO> getTraveller(@PathVariable Long travellerId) {
        TravellerReadOnlyDTO travellerDTO = travellerService.getTraveller(travellerId);
        return ResponseEntity.ok(travellerDTO);
    }

    /**
     * Adds a list of countries to a traveller's profile.
     * <p>
     * This endpoint allows authenticated users with the role "TRAVELLER" or "ADMIN" to associate a list
     * of countries with a specified traveller. Non-admin users can only modify their own profile.
     * </p>
     *
     * @param travellerId       the ID of the traveller to add countries to
     * @param countryRequestDTO the DTO containing the list of country IDs to be added
     * @param principal         the currently authenticated user's details
     * @return a {@link ResponseEntity} containing the updated {@link TravellerReadOnlyDTO} or an error response
     * @throws AppObjectNotFoundException if the user or traveller is not found
     */
    @Operation(summary = "Add countries to traveler", description = "Allows a traveler to add countries they have visited.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Countries added successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TravellerReadOnlyDTO.class))),
            @ApiResponse(responseCode = "403", description = "Unauthorized to modify another traveler's list"),
            @ApiResponse(responseCode = "404", description = "Traveler not found")
    })
    @PreAuthorize("hasRole('TRAVELLER') or hasRole('ADMIN')")
    @PostMapping("travellers/{travellerId}/add")
    public ResponseEntity<?> addCountriesToTraveller(
            @PathVariable Long travellerId,
            @RequestBody CountryRequestDTO countryRequestDTO, Principal principal) throws AppObjectNotFoundException
    {
        // To ensure that the traveller is trying to add countries only for themselves (for non-admins)
        String authenticatedUsername = principal.getName();
        User authenticatedUser = userRepository.findByUsername(authenticatedUsername)
                .orElseThrow(() -> new AppObjectNotFoundException("User", "User not found"));

        if (!authenticatedUser.getTraveller().getId().equals(travellerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("You are not authorized to modify another traveller's countries.");

        }

        // Service call to add countries
        try {
            TravellerReadOnlyDTO updatedTraveller = travellerService.addCountriesToTraveller(travellerId, countryRequestDTO.getCountryIds());
            LOGGER.info("Countries added to traveller with ID: {}", travellerId);
            return ResponseEntity.ok(updatedTraveller);
        } catch (Exception e) {
            LOGGER.error("ERROR: Could not add countries to traveller.", e);
            return ResponseEntity.status(500).build();  // Returning internal server error in case of failure
        }
    }


    /**
     * Removes a country from a traveller's profile.
     * <p>
     * This endpoint allows authenticated users with the role "TRAVELLER" or "ADMIN" to remove a specific
     * country from a traveller's list of associated countries.
     * </p>
     *
     * @param travellerId the ID of the traveller from whom the country will be removed
     * @param countryId   the ID of the country to remove
     * @return a {@link ResponseEntity} containing a success message
     * @throws AppObjectNotFoundException if the traveller or country is not found
     */
    @Operation(summary = "Remove a country from traveler", description = "Removes a specific country from a traveler's visited list.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Country removed successfully"),
            @ApiResponse(responseCode = "404", description = "Traveler or country not found")
    })
    @PreAuthorize("hasRole('TRAVELLER') or hasRole('ADMIN')")
    @PatchMapping("/travellers/{travellerId}/remove-country/{countryId}")
    public ResponseEntity<String> removeCountryFromTraveller(
            @PathVariable Long travellerId, @PathVariable Long countryId) throws AppObjectNotFoundException {

        travellerService.removeCountryFromTraveller(travellerId, countryId);
        return ResponseEntity.ok("Country removed from traveller's list.");
    }

// ------ Not used because /travellers/all/paginated covers both filtering and pagination ------
//    @PostMapping("/travellers/all")
//    public ResponseEntity<List<TravellerReadOnlyDTO>> getTravellers(@Nullable @RequestBody TravellerFilters filters,
//                                                                Principal principal)
//            throws AppObjectNotFoundException, AppObjectNotAuthorizedException {
//        try {
//            if (filters == null) filters = TravellerFilters.builder().build();
//            return ResponseEntity.ok(travellerService.getTravellersFiltered(filters));
//        } catch (Exception e) {
//            LOGGER.error("ERROR: Could not get travellers.", e);
//            throw e;
//        }
//    }

    /**
     * Retrieves a paginated and filtered list of travellers.
     * <p>
     * This endpoint allows users to apply dynamic filters to retrieve a paginated list of travellers.
     * It includes authorization and is useful for admins or users managing a large list of travellers.
     * </p>
     *
     * @param filters   the filters to apply for retrieving travellers (nullable)
     * @param principal the currently authenticated user's details
     * @return a {@link ResponseEntity} containing the paginated list of filtered {@link TravellerReadOnlyDTO}
     * @throws AppObjectNotFoundException    if any relevant objects are not found
     * @throws AppObjectNotAuthorizedException if the user is not authorized to perform this action
     */
    @Operation(summary = "Get paginated travelers", description = "Retrieves a list of travelers with pagination and filtering options.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved paginated list",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Paginated.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/travellers/all/paginated")
    public ResponseEntity<Paginated<TravellerReadOnlyDTO>> getTravellersFilteredPaginated(@Nullable @RequestBody TravellerFilters filters,
                                                                                        Principal principal)
            throws AppObjectNotFoundException, AppObjectNotAuthorizedException {
        try {
            if (filters == null) filters = TravellerFilters.builder().build();
            return ResponseEntity.ok(travellerService.getTravellersFilteredPaginated(filters));
        } catch (Exception e) {
            LOGGER.error("ERROR: Could not get travellers.", e);
            throw e;
        }
    }

    /**
     * Retrieves the total number of countries travelled by a specific traveller.
     *
     * @param travellerId the ID of the traveller
     * @return a {@link ResponseEntity} containing the total number of travelled countries
     */
    @Operation(summary = "Get total traveled countries", description = "Returns the number of countries a traveler has visited.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total traveled countries retrieved successfully")
    })
    @GetMapping("/travellers/{travellerId}/total-travelled-countries")
    public ResponseEntity<Long> getTotalTraveledCountries(@PathVariable Long travellerId) {
        long totalTraveledCountries = travellerService.getTotalTraveledCountries(travellerId);
        return ResponseEntity.ok(totalTraveledCountries);
    }


    /**
     * Retrieves the total number of untravelled countries for a specific traveller.
     *
     * @param travellerId the ID of the traveller
     * @return a {@link ResponseEntity} containing the total number of untravelled countries
     */
    @Operation(summary = "Get total untraveled countries", description = "Returns the number of countries a traveler has not visited yet.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total untraveled countries retrieved successfully")
    })
    @GetMapping("/travellers/{travellerId}/total-untravelled-countries")
    public ResponseEntity<Long> getTotalUntraveledCountries(@PathVariable Long travellerId) {
        long totalUntraveledCountries = travellerService.getTotalUntraveledCountries(travellerId);
        return ResponseEntity.ok(totalUntraveledCountries);
    }


    /**
     * Soft deletes a traveller by deactivating their account.
     * <p>
     * This endpoint can only be accessed by users with the "ADMIN" authority.
     * Deactivating a traveller prevents their further participation while retaining their data.
     * </p>
     *
     * @param id the ID of the traveller to deactivate
     * @return a {@link ResponseEntity} containing a success message
     * @throws AppObjectNotFoundException if the traveller is not found
     */
    @Operation(summary = "Deactivate traveler", description = "Soft deletes a traveler (deactivates them) - Admin only.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Traveler deactivated successfully"),
            @ApiResponse(responseCode = "404", description = "Traveler not found")
    })
    @PatchMapping("/travellers/{id}/deactivate")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> softDeleteTraveller(@PathVariable Long id) throws AppObjectNotFoundException {
        travellerService.softDeleteTraveller(id);
        return ResponseEntity.ok("Traveller was deactivated successfully.");
    }


    /**
     * Restores a previously soft-deleted traveller by reactivating their account.
     * <p>
     * This endpoint can only be accessed by users with the "ADMIN" authority.
     * Reactivating a traveller allows them to resume participation in the system.
     * </p>
     *
     * @param id the ID of the traveller to restore
     * @return a {@link ResponseEntity} containing a success message
     * @throws AppObjectNotFoundException if the traveller is not found
     */
    @Operation(summary = "Restore traveler", description = "Restores a soft-deleted traveler - Admin only.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Traveler restored successfully"),
            @ApiResponse(responseCode = "404", description = "Traveler not found")
    })
    @PatchMapping("/travellers/{id}/restore")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> restoreTraveller(@PathVariable Long id) throws AppObjectNotFoundException{
        travellerService.restoreTraveller(id);
        return ResponseEntity.ok("Traveller was restored successfully.");
    }


    /**
     * Permanently deletes a traveller's record from the system.
     * <p>
     * This endpoint can only be accessed by users with the "ADMIN" role. Soft deletion is preferred;
     * however, this method is available if permanent deletion is necessary.
     * </p>
     *
     * @param travellerId the ID of the traveller to delete
     * @return a {@link ResponseEntity} with the appropriate status based on the outcome
     */
    @Operation(
            summary = "Delete a traveller",
            description = "Deletes a traveller based on their ID. This method is implemented for a hard delete but is not used as soft delete is preferred."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Traveller deleted successfully (No Content)"),
            @ApiResponse(responseCode = "404", description = "Traveller not found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/travellers/{travellerId}/delete")   // No need as we have implemented the soft delete but the method is there in case it is needed.
    @PreAuthorize("hasRole('ADMIN')") // Ensure only ADMIN can delete travelers
    public ResponseEntity<Void> deleteTraveller(@PathVariable Long travellerId) {
        try {
            travellerService.deleteTraveller(travellerId);
            LOGGER.info("Traveller with ID {} deleted successfully", travellerId);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (AppObjectNotFoundException e) {
            LOGGER.error("Traveller with ID {} not found", travellerId);
            return ResponseEntity.notFound().build(); // 404 Not Found
        } catch (Exception e) {
            LOGGER.error("Error deleting traveller with ID {}", travellerId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500 Internal Server Error
        }
    }

}

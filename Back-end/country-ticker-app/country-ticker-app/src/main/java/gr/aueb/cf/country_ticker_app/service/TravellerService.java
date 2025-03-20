package gr.aueb.cf.country_ticker_app.service;

import gr.aueb.cf.country_ticker_app.core.exceptions.AppObjectAlreadyExists;
import gr.aueb.cf.country_ticker_app.core.exceptions.AppObjectNotFoundException;
import gr.aueb.cf.country_ticker_app.core.filters.Paginated;
import gr.aueb.cf.country_ticker_app.core.filters.TravellerFilters;
import gr.aueb.cf.country_ticker_app.core.specifications.TravellerSpecification;
import gr.aueb.cf.country_ticker_app.dto.TravellerInsertDTO;
import gr.aueb.cf.country_ticker_app.dto.TravellerReadOnlyDTO;
import gr.aueb.cf.country_ticker_app.mapper.Mapper;
import gr.aueb.cf.country_ticker_app.model.Traveller;
import gr.aueb.cf.country_ticker_app.model.static_data.Country;
import gr.aueb.cf.country_ticker_app.repository.CountryRepository;
import gr.aueb.cf.country_ticker_app.repository.PersonalInfoRepository;
import gr.aueb.cf.country_ticker_app.repository.TravellerRepository;
import gr.aueb.cf.country_ticker_app.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service class for managing traveller-related operations.
 * <p>
 * This class provides methods for creating, updating, retrieving, and managing travellers and their associated data.
 * </p>
 *
 * <b>Features:</b>
 * - CRUD operations for travellers.
 * - Managing associated countries and traveller status.
 * - Filtering and pagination for travellers.
 *
 * @see TravellerRepository
 * @see Traveller
 * @see CountryRepository
 *
 * @author ilias
 */
@Service
@RequiredArgsConstructor
public class TravellerService {

    private final TravellerRepository travellerRepository;
    private final UserRepository userRepository;
    private final PersonalInfoRepository personalInfoRepository;
    private final CountryRepository countryRepository;
    private final Mapper mapper;

    /**
     * Saves a new traveller to the database.
     *
     * @param travellerInsertDTO the DTO containing the traveller's data
     * @return the saved traveller as a {@link TravellerReadOnlyDTO}
     * @throws AppObjectAlreadyExists if the traveller or related user/personal info already exists
     */
    @Transactional(rollbackOn = Exception.class)
    public TravellerReadOnlyDTO saveTraveller(TravellerInsertDTO travellerInsertDTO) throws AppObjectAlreadyExists {


        if (userRepository.findByFirstnameAndLastname(travellerInsertDTO.getUser().getFirstname(), travellerInsertDTO.getUser().getLastname()).isPresent()) {
            throw new AppObjectAlreadyExists("User", "User with firstname: " + travellerInsertDTO.getUser().getFirstname() + " and lastname: " + travellerInsertDTO.getUser().getLastname() + " already exists.");
        }

        if (personalInfoRepository.findByPhone(travellerInsertDTO.getPersonalInfo().getPhone()).isPresent()) {
            throw new AppObjectAlreadyExists("PersonalInfo", "PersonalInfo with phone: " + travellerInsertDTO.getPersonalInfo().getPhone() + " already exists.");
        }

        Traveller traveller = mapper.mapToTravellerEntity(travellerInsertDTO);
        traveller.setIsActive(travellerInsertDTO.getIsActive());

        Traveller savedTraveller = travellerRepository.save(traveller);
        return mapper.mapToTravellerReadOnlyDto(savedTraveller);


    }

    /**
     * Retrieves a paginated list of travellers.
     *
     * @param page the page number to retrieve
     * @param size the number of items per page
     * @return a paginated list of {@link TravellerReadOnlyDTO}
     */
    public Page<TravellerReadOnlyDTO> getPaginatedTravellers (int page, int size) {
        String defaultSort = "id";
        Pageable pageable = PageRequest.of(page, size, Sort.by(defaultSort).ascending());
        return travellerRepository.findAll(pageable).map(mapper::mapToTravellerReadOnlyDto);
    }


    /**
     * Retrieves a paginated and sorted list of travellers.
     * <p>
     * This method allows sorting by a specified field and direction, alongside pagination.
     * Useful for efficiently handling large datasets of travellers.
     * </p>
     *
     * @param page the page number to retrieve (zero-based)
     * @param size the number of items per page
     * @param sortBy the field to sort by (e.g., "id", "name")
     * @param sortDirection the direction of sorting ("ASC" for ascending, "DESC" for descending)
     * @return a paginated list of {@link TravellerReadOnlyDTO} objects
     */
    public Page<TravellerReadOnlyDTO> getPaginatedSortedTravellers(int page, int size, String sortBy, String sortDirection) {
       Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
       Pageable pageable = PageRequest.of(page, size, sort);
       return travellerRepository.findAll(pageable).map(mapper::mapToTravellerReadOnlyDto);
    }

    /**
     * Adds a list of countries to a traveller.
     *
     * @param travellerId the ID of the traveller
     * @param countryIds the IDs of the countries to add
     * @return the updated traveller as a {@link TravellerReadOnlyDTO}
     */
    @Transactional(rollbackOn = Exception.class)
    public TravellerReadOnlyDTO addCountriesToTraveller(Long travellerId, List<Long> countryIds) {
        Traveller traveller = travellerRepository.findById(travellerId)
                .orElseThrow(() -> new RuntimeException("Traveller not found with id " + travellerId));

        List<Country> countries = countryRepository.findAllById(countryIds);
        traveller.getCountries().addAll(countries);



        Traveller updatedTraveller = travellerRepository.save(traveller);
        return mapper.mapToTravellerReadOnlyDto(updatedTraveller);
    }

    /**
     * Removes a country from a traveller's list of associated countries.
     * <p>
     * This method ensures the specified country is removed from the traveller's list,
     * and throws an exception if the traveller or country does not exist or is not associated.
     * </p>
     *
     * @param travellerId the ID of the traveller
     * @param countryId the ID of the country to remove
     * @throws AppObjectNotFoundException if the traveller or country is not found, or the country is not associated with the traveller
     */
    public void removeCountryFromTraveller(Long travellerId, Long countryId) throws AppObjectNotFoundException {
        Traveller traveller = travellerRepository.findById(travellerId)
                .orElseThrow(() -> new AppObjectNotFoundException("Traveller", "Traveller not found"));

        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new AppObjectNotFoundException("Country", "Country not found"));

        if (traveller.getCountries().contains(country)) {
            traveller.getCountries().remove(country);
            travellerRepository.save(traveller); // Save the updated traveller
        } else {
            throw new AppObjectNotFoundException("Country", "Country not found in traveller's list");
        }
    }


    /**
     * Updates the active status of a traveller.
     * <p>
     * This method cascades the status update to the associated user entity as well.
     * </p>
     *
     * @param travellerId the ID of the traveller to update
     * @param isActive the new active status to set
     * @throws EntityNotFoundException if the traveller is not found
     */
    public void updateTravellerStatus(Long travellerId, Boolean isActive) {
        Traveller traveller = travellerRepository.findById(travellerId)
                .orElseThrow(() -> new EntityNotFoundException("Traveller not found"));

        // Updates the Traveller and cascades the status to User
        traveller.setIsActive(isActive);
        travellerRepository.save(traveller);
    }

    /**
     * Retrieves a traveller by their ID.
     * <p>
     * Converts the traveller entity into a read-only DTO for safe data transfer.
     * </p>
     *
     * @param travellerId the ID of the traveller to retrieve
     * @return the retrieved traveller as a {@link TravellerReadOnlyDTO}
     * @throws EntityNotFoundException if the traveller is not found
     */
    public TravellerReadOnlyDTO getTraveller(Long travellerId) {
        Traveller traveller = travellerRepository.findById(travellerId)
                .orElseThrow(() -> new EntityNotFoundException("Traveller not found with id " + travellerId));
        return mapper.mapToTravellerReadOnlyDto(traveller);
    }


    /**
     * Retrieves a list of all travellers.
     * <p>
     * Converts the traveller entities into read-only DTOs for data transfer.
     * </p>
     *
     * @return a {@link List} of {@link TravellerReadOnlyDTO} objects
     */
    public List<TravellerReadOnlyDTO> getAllTravellers() {
        List<Traveller> travellers = travellerRepository.findAll();
        return travellers.stream()
                .map(mapper::mapToTravellerReadOnlyDto)
                .collect(Collectors.toList());
    }


    /**
     * Soft deletes (deactivates) a traveller.
     *
     * @param travellerId the ID of the traveller
     * @throws AppObjectNotFoundException if the traveller is not found
     */
    @Transactional
    public void softDeleteTraveller(Long travellerId) throws AppObjectNotFoundException {
        Traveller traveller = travellerRepository.findById(travellerId)
                .orElseThrow(() -> new AppObjectNotFoundException("Traveller", "Traveller with ID " + travellerId + " not found"));

        traveller.setIsActive(false); // Soft delete
        travellerRepository.save(traveller);
    }

    /**
     * Restores a soft-deleted traveller by setting their active status to true.
     *
     * @param travellerId the ID of the traveller to restore
     * @throws AppObjectNotFoundException if the traveller is not found
     */
    @Transactional
    public void restoreTraveller(Long travellerId) throws AppObjectNotFoundException {
        Traveller traveller = travellerRepository.findById(travellerId)
                .orElseThrow(() -> new AppObjectNotFoundException("Traveller", "Traveller with ID " + travellerId + " not found"));

        traveller.setIsActive(true); // Reactivate soft-deleted traveller
        travellerRepository.save(traveller);
    }



    /**
     * Permanently deletes a traveller from the database.
     *
     * @param travellerId the ID of the traveller to delete
     * @throws AppObjectNotFoundException if the traveller is not found
     */
    @Transactional
    public void deleteTraveller(Long travellerId) throws AppObjectNotFoundException {
        Traveller traveller = travellerRepository.findById(travellerId)
                .orElseThrow(() -> new AppObjectNotFoundException("Traveller", "Traveller with ID " + travellerId + " not found"));

        travellerRepository.delete(traveller);
    }


    /**
     * Retrieves a paginated and filtered list of travellers.
     * <p>
     * Applies dynamic filters and pagination to the traveller data for more efficient queries.
     * </p>
     *
     * @param filters the filters to apply for querying travellers
     * @return a paginated list of filtered travellers as {@link Paginated} objects
     */
    @Transactional
    public Paginated<TravellerReadOnlyDTO> getTravellersFilteredPaginated(TravellerFilters filters) {
        Pageable pageable = filters.getPageable();
        Page<Traveller> filtered = travellerRepository.findAll(getSpecsFromFilters(filters), pageable);
        return new Paginated<>(filtered.map(mapper::mapToTravellerReadOnlyDto));
    }


    /**
     * Retrieves a filtered list of travellers based on the provided filters.
     *
     * @param filters the filters to apply for querying travellers
     * @return a {@link List} of filtered {@link TravellerReadOnlyDTO} objects
     */
    @Transactional
    public List<TravellerReadOnlyDTO> getTravellersFiltered(TravellerFilters filters) {
        return travellerRepository.findAll(getSpecsFromFilters(filters))
                .stream().map(mapper::mapToTravellerReadOnlyDto).toList();
    }


    /**
     * Builds a specification for querying travellers based on the provided filters.
     *
     * @param filters the traveller filters to apply
     * @return a {@link Specification} object for querying travellers
     */
    private Specification<Traveller> getSpecsFromFilters(TravellerFilters filters) {
        return Specification
                .where(TravellerSpecification.trStringFieldLike("uuid", filters.getUuid()))
                .and(TravellerSpecification.trUserIsActive(filters.getActive()));
    }


    /**
     * Retrieves the total number of countries a traveller has visited.
     *
     * @param travellerId the ID of the traveller
     * @return the number of countries the traveller has travelled to
     * @throws RuntimeException if the traveller is not found
     */
    public long getTotalTraveledCountries(Long travellerId) {
        Traveller traveller = travellerRepository.findById(travellerId)
                .orElseThrow(() -> new RuntimeException("Traveller not found with id " + travellerId));

        return traveller.getCountries().size();
    }

    /**
     * Retrieves the total number of untravelled countries for a traveller.
     *
     * @param travellerId the ID of the traveller
     * @return the number of countries the traveller has not travelled to
     * @throws RuntimeException if the traveller is not found
     */
    public long getTotalUntraveledCountries(Long travellerId) {
        Traveller traveller = travellerRepository.findById(travellerId)
                .orElseThrow(() -> new RuntimeException("Traveller not found with id " + travellerId));

        Set<Long> traveledCountryIds = traveller.getCountries().stream()
                .map(Country::getId)
                .collect(Collectors.toSet());

        List<Country> allCountries = countryRepository.findAll();
        long untraveledCount = allCountries.stream()
                .filter(country -> !traveledCountryIds.contains(country.getId()))
                .count();

        return untraveledCount;
    }


}

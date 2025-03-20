package gr.aueb.cf.country_ticker_app.mapper;

import gr.aueb.cf.country_ticker_app.dto.*;
import gr.aueb.cf.country_ticker_app.model.PersonalInfo;
import gr.aueb.cf.country_ticker_app.model.Traveller;
import gr.aueb.cf.country_ticker_app.model.User;
import gr.aueb.cf.country_ticker_app.model.static_data.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting between entities and DTOs.
 * <p>
 * This class provides methods to map data from entity objects to DTOs, and vice versa, for data transfer and processing purposes.
 * </p>
 *
 * <b>Note:</b> Uses {@link PasswordEncoder} to securely encode passwords.
 *
 * @author ilias
 */
@Component
@RequiredArgsConstructor
public class Mapper {

    private final PasswordEncoder passwordEncoder;

    /**
     * Maps a {@link Traveller} entity to a {@link TravellerReadOnlyDTO}.
     *
     * @param traveller the traveller entity to map
     * @return the mapped {@link TravellerReadOnlyDTO}
     */
    public TravellerReadOnlyDTO mapToTravellerReadOnlyDto(Traveller traveller) {
        var dto = new TravellerReadOnlyDTO();
        dto.setId(traveller.getId());
        dto.setUuid(traveller.getUuid());
        dto.setIsActive(traveller.getIsActive());
        dto.setCountries(traveller.getCountries());

        // map to final
        var userDTO = new UserReadOnlyDTO();
        userDTO.setFirstname(traveller.getUser().getFirstname());
        userDTO.setLastname(traveller.getUser().getLastname());
        userDTO.setUsername(traveller.getUser().getUsername());
        dto.setUser(userDTO);

        //map to personalInfoDTO
        var personalInfoDTO = new PersonalInfoReadOnlyDTO();
        personalInfoDTO.setPhone(traveller.getPersonalInfo().getPhone());
        personalInfoDTO.setAddress(traveller.getPersonalInfo().getAddress());
        personalInfoDTO.setCity(traveller.getPersonalInfo().getCity());
        personalInfoDTO.setCountry(traveller.getPersonalInfo().getCountry());
        dto.setPersonalInfo(personalInfoDTO);

        return dto;
    }


    /**
     * Maps a {@link TravellerInsertDTO} to a {@link Traveller} entity.
     *
     * @param travellerInsertDto the DTO containing traveller data for insertion
     * @return the mapped {@link Traveller} entity
     */
    public Traveller mapToTravellerEntity(TravellerInsertDTO travellerInsertDto) {
        Traveller traveller = new Traveller();
        traveller.setIsActive(travellerInsertDto.getIsActive());



        User user = new User();
        UserInsertDTO userInsertDto = travellerInsertDto.getUser();
        String encodedPassword = passwordEncoder.encode(userInsertDto.getPassword());

        user.setFirstname(userInsertDto.getFirstname());
        user.setLastname((userInsertDto.getLastname()));
        user.setUsername(userInsertDto.getUsername());
        user.setPassword(encodedPassword);      //todo
        user.setDateOfBirth(userInsertDto.getDateOfBirth());
        user.setGender(userInsertDto.getGender());
        user.setRole(userInsertDto.getRole());
        //user.setIsActive(userInsertDto.getIsActive());
        user.setIsActive(travellerInsertDto.getIsActive());
        traveller.setUser(user);



        PersonalInfo personalInfo = new PersonalInfo();
        PersonalInfoInsertDTO personalInfoInsertDto = travellerInsertDto.getPersonalInfo();
        personalInfo.setPhone(personalInfoInsertDto.getPhone());
        personalInfo.setAddress(personalInfoInsertDto.getAddress());
        personalInfo.setCity(personalInfoInsertDto.getCity());
        personalInfo.setCountry(personalInfoInsertDto.getCountry());
        traveller.setPersonalInfo(personalInfo);

        return traveller;
    }

    /**
     * Maps a {@link CountryInsertDTO} to a {@link Country} entity.
     *
     * @param dto the DTO containing country data for insertion
     * @return the mapped {@link Country} entity
     */
    public Country mapToCountryEntity(CountryInsertDTO dto) {
        Country country = new Country();
        country.setId(dto.getId());
        country.setName(dto.getName());
        country.setCode(dto.getCode());
        country.setFlagUrl(dto.getFlagUrl());
        return country;
    }

    /**
     * Maps a {@link Country} entity to a {@link CountryReadOnlyDTO}.
     *
     * @param entity the country entity to map
     * @return the mapped {@link CountryReadOnlyDTO}
     */
    public CountryReadOnlyDTO mapToCountryReadOnlyDto(Country entity) {
        return new CountryReadOnlyDTO(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                entity.getFlagUrl()
        );
    }
}

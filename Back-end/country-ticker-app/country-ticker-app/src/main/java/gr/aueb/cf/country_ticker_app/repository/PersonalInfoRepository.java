package gr.aueb.cf.country_ticker_app.repository;

import gr.aueb.cf.country_ticker_app.model.PersonalInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * Repository interface for managing {@link PersonalInfo} entities.
 * <p>
 * This interface provides CRUD operations, custom queries, and support for specifications
 * on the "personal_information" table.
 * </p>
 *
 * @author ilias
 */
public interface PersonalInfoRepository extends JpaRepository<PersonalInfo, Long>, JpaSpecificationExecutor<PersonalInfo> {

    Optional<PersonalInfo> findByCountry(String country);
    Optional<PersonalInfo> findByAddress(String address);
    Optional<PersonalInfo> findByPhone(String phone);
    Optional<PersonalInfo> findByCity(String city);
}

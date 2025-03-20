package gr.aueb.cf.country_ticker_app.repository;

import gr.aueb.cf.country_ticker_app.model.Traveller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link Traveller} entities.
 * <p>
 * This interface provides CRUD operations, custom queries, and support for specifications
 * on the "travellers" table.
 * </p>
 *
 * @author ilias
 */
public interface TravellerRepository extends JpaRepository<Traveller, Long>, JpaSpecificationExecutor<Traveller> {
    Optional<Traveller> findTravellerByUserId(Long id);
    Optional<Traveller> findByUuid(String uuid);
    @Query("SELECT t FROM Traveller t WHERE t.isActive = true")
    List<Traveller> findAllActiveTravellers();

}

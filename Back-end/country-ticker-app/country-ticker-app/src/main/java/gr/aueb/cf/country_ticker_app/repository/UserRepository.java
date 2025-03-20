package gr.aueb.cf.country_ticker_app.repository;

import gr.aueb.cf.country_ticker_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * Repository interface for managing {@link User} entities.
 * <p>
 * This interface provides CRUD operations, custom queries, and support for specifications
 * on the "users" table.
 * </p>
 *
 * @author ilias
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);
    Optional<User> findByLastname(String lastname);
    Optional<User> findByFirstnameAndLastname(String firstname, String lastname);
}

package gr.aueb.cf.country_ticker_app.core.specifications;

import gr.aueb.cf.country_ticker_app.model.Traveller;
import gr.aueb.cf.country_ticker_app.model.User;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specifications for querying {@link Traveller} entities.
 * <p>
 * This class provides reusable specifications for filtering travellers based on dynamic conditions.
 * </p>
 *
 * <b>Note:</b> This class is designed with static methods to allow usage without instantiation.
 *
 * @author ilias
 */
public class TravellerSpecification {

    /**
     * Private constructor to prevent instantiation.
     */
    private TravellerSpecification() {

    }

    /**
     * Creates a specification to filter travellers based on the "isActive" status of the associated user.
     *
     * @param isActive the active status to filter by (nullable, includes all if null)
     * @return a {@link Specification} to filter travellers by user activity status
     */
    public static Specification<Traveller> trUserIsActive(Boolean isActive) {
        return ((root, query, criteriaBuilder) -> {
            if (isActive == null) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            Join<Traveller, User> user = root.join("user");
            return criteriaBuilder.equal(user.get("isActive"), isActive);
        });

    }

    /**
     * Creates a specification to filter travellers by matching a string field using "LIKE" (case insensitive).
     *
     * @param field the name of the string field to match
     * @param value the value to look for (nullable, includes all if null or empty)
     * @return a {@link Specification} to filter travellers by the specified field and value
     */
    public static Specification<Traveller> trStringFieldLike(String field, String value) {
        return ((root, query, criteriaBuilder) -> {
            if (value == null || value.trim().isEmpty()) {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }
            return criteriaBuilder.like(criteriaBuilder.upper(root.get(field)), "%" + value.toUpperCase() + "%");  // case insensitive
        });

    }
}

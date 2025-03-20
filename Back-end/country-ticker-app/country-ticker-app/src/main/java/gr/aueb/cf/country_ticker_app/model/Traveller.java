package gr.aueb.cf.country_ticker_app.model;

import gr.aueb.cf.country_ticker_app.model.static_data.Country;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Entity representing a traveller.
 * <p>
 * This class maps to the "travellers" table in the database and includes details
 * about the traveller's user information, personal information, and associated countries.
 * </p>
 *
 * <b>Note:</b> Extends {@link AbstractEntity} to inherit auditing fields.
 *
 * @author ilias
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "travellers")
public class Traveller extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String uuid;

    @Column(name = "is_active")
    private Boolean isActive;



//    save simultaneously to Traveller and User
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id"
    )
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_info_id", referencedColumnName = "id"
    )
    private PersonalInfo personalInfo;


    @ManyToMany
    @JoinTable(
            name = "traveller_country", // name of the join table
            joinColumns = @JoinColumn(name = "traveller_id"), // foreign key to the traveller
            inverseJoinColumns = @JoinColumn(name = "country_id") // foreign key to the country
    )
    private Set<Country> countries = new HashSet<>();

    /**
     * Adds a country to the traveller's list of associated countries.
     *
     * @param country the country to add
     */
    public void addCountry(Country country) {
        if (countries == null) countries = new HashSet<>();
        countries.add(country);
    }

    /**
     * Checks if the traveller is associated with any countries.
     *
     * @param country the country to check
     * @return true if the traveller has the country, false otherwise
     */
    public boolean hasCountry(Country country) {
        return countries != null && !countries.isEmpty();
    }

    /**
     * Initializes the UUID of the traveller if not already set.
     */
    @PrePersist
    public void initializeUUID() {
        if (uuid == null) uuid = UUID.randomUUID().toString();
    }

    /**
     * Sets the active status for the traveller and cascades the status to the associated user.
     *
     * @param isActive the active status to set
     */
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;

        // Cascade the change to the associated User if exists
        if (this.user != null) {
            this.user.setIsActive(isActive);
        }
    }
}

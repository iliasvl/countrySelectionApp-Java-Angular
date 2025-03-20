package gr.aueb.cf.country_ticker_app.model.static_data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import gr.aueb.cf.country_ticker_app.model.Traveller;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Entity representing a country.
 * <p>
 * This class maps to the "countries" table in the database and includes details
 * about the country's name, code, and flag URL. It also maintains a many-to-many
 * relationship with {@link Traveller}.
 * </p>
 *
 * @author ilias
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "code", nullable = false, unique = true, length = 2)
    private String code;

    @Column(name = "flag_url", nullable = false, length = 255)
    private String flagUrl;


    @ManyToMany(mappedBy = "countries")
    @JsonIgnore
    private Set<Traveller> travellers = new HashSet<>();
}


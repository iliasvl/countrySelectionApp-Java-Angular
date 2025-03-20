package gr.aueb.cf.country_ticker_app.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity representing personal information.
 * <p>
 * This class maps to the "personal_information" table and includes details such as
 * phone number, address, city, and country.
 * </p>
 *
 * <b>Note:</b> Extends {@link AbstractEntity} to inherit auditing fields.
 *
 * @author ilias
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "personal_information")
public class PersonalInfo extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;

    private String address;

    private String city;

    private String country;


    public PersonalInfo(LocalDateTime createdAt, LocalDateTime updatedAt, Long id, String phone, String address, String city,
                        String country) {
        this.id = id;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.country = country;
    }


}

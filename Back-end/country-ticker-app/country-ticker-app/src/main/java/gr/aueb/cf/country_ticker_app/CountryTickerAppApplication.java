package gr.aueb.cf.country_ticker_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Main application class for the Country Ticker App.
 * <p>
 * This class serves as the entry point for the Spring Boot application and is responsible
 * for bootstrapping the application context and initializing the configuration.
 * </p>
 *
 * <b>Features:</b>
 * - Annotation-driven Spring Boot setup using {@link SpringBootApplication}.
 * - Enables JPA auditing to automatically populate auditing fields in entity classes.
 *
 * @author ilias
 */
@SpringBootApplication
@EnableJpaAuditing
public class CountryTickerAppApplication {

	/**
	 * The main method, used to launch the application.
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(CountryTickerAppApplication.class, args);
	}

}

package gr.aueb.cf.country_ticker_app.core.filters;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Wrapper class to represent a paginated response.
 * <p>
 * This class encapsulates the data and metadata of a paginated query result.
 * </p>
 *
 * @param <T> the type of data contained in the paginated response
 *
 * @author ilias
 */
@Getter
@Setter
public class Paginated<T>{

    List<T> data;
    long totalElements;
    int totalPages;
    int numberOfElements;
    int currentPage;
    int pageSize;

    /**
     * Constructs a paginated response based on a Spring Data {@link Page} object.
     *
     * @param page the page object containing data and metadata
     */
    public Paginated(Page<T> page) {
        this.data = page.getContent();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.numberOfElements = page.getNumberOfElements();
        this.currentPage = page.getNumber();
        this.pageSize = page.getSize();
    }
}

package gr.aueb.cf.country_ticker_app.core.filters;

import io.micrometer.common.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


/**
 * Abstract class to represent generic filters for data retrieval operations.
 * <p>
 * Provides support for pagination, sorting, and default configurations.
 * </p>
 *
 * @author ilias
 */
@Getter
@Setter
public abstract class GenericFilters {
    private final static int DEFAULT_PAGE_SIZE = 10;
    private static final String DEFAULT_SORT_COLUMN = "id";
    private static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;

    private int page;
    private int pageSize;
    private Sort.Direction sortDirection;
    private String sortBy;


    /**
     * Gets the page size with a default fallback.
     *
     * @return the page size, or default if not set or invalid
     */
    public int getPageSize() {
        return pageSize <= 0 ? DEFAULT_PAGE_SIZE : pageSize;
    }

    /**
     * Gets the page number ensuring it is non-negative.
     *
     * @return the page number, or 0 if negative
     */
    public int getPage() {
        return Math.max(page, 0);
    }

    /**
     * Gets the sorting direction with a default fallback.
     *
     * @return the sorting direction, or default if not set
     */
    public Sort.Direction getSortDirection(){
        if (this.sortDirection == null) return DEFAULT_SORT_DIRECTION;
        return this.sortDirection;
    }

    /**
     * Gets the sorting column with a default fallback.
     *
     * @return the column to sort by, or default if not set
     */
    public String getSortBy(){
        if (this.sortBy == null || StringUtils.isBlank(this.sortBy)) return DEFAULT_SORT_COLUMN;
        return this.sortBy;
    }

    /**
     * Creates a {@link Pageable} object for pagination and sorting.
     *
     * @return the pageable configuration
     */
    public Pageable getPageable(){
        return PageRequest.of(getPage(), getPageSize(), getSort());
    }

    /**
     * Creates a {@link Sort} object based on direction and column.
     *
     * @return the sorting configuration
     */
    public Sort getSort(){
        return Sort.by(this.getSortDirection(), this.getSortBy());
    }
}

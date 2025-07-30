package ch.zmotions.linkit.service.util.sort;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class SortHelper {

    private final String DEFAULT_SORT_PROPERTY = "id";
    private final String DEFAULT_SORT_DIRECTION = "ASC";

    public Sort getSort(String sort) {
        String[] sortProps = sort.split(":");
        if (sortProps.length == 2) {
            String sortProperty = sortProps[0];
            String sortDirection = sortProps[1].trim();
            return sortDirection.equals(DEFAULT_SORT_DIRECTION) ? Sort.by(Sort.Order.asc(sortProperty))
                    : Sort.by(Sort.Order.desc(sortProperty));
        } else {
            return getDefaultSort();
        }
    }

    public Sort getDefaultSort() {
        return Sort.by(Sort.Order.asc(DEFAULT_SORT_PROPERTY));
    }

    public String defaultPropertyAndDirection() {
        return DEFAULT_SORT_PROPERTY + ":" + DEFAULT_SORT_DIRECTION;
    }
}

package ch.zmotions.linkit.service.util.sort;

import org.junit.Test;
import org.springframework.data.domain.Sort;

import java.util.Objects;

import static org.junit.Assert.*;

public class SortHelperTest {

    @Test
    public void getSort() {
        SortHelper sortHelper = new SortHelper();
        Sort sort = sortHelper.getSort("id:ASC");
        assertEquals("ASC",
                Objects.requireNonNull(sort.getOrderFor("id")).getDirection().name());
        Sort sort2 = sortHelper.getSort("id:DESC");
        assertEquals("DESC",
                Objects.requireNonNull(sort2.getOrderFor("id")).getDirection().name());
        Sort sort3 = sortHelper.getSort("username:ASC");
        assertEquals("ASC",
                Objects.requireNonNull(sort3.getOrderFor("username")).getDirection().name());
        assertTrue(sort3.get().findFirst().isPresent());
        assertEquals("username", sort3.get().findFirst().get().getProperty());
    }

    @Test
    public void getDefaultSort() {
        SortHelper sortHelper = new SortHelper();
        Sort defaultSort = sortHelper.getDefaultSort();
        assertTrue(defaultSort.get().findFirst().isPresent());
        assertEquals("id", defaultSort.get().findFirst().get().getProperty());
        assertEquals("ASC", defaultSort.get().findFirst().get().getDirection().name());
    }

    @Test
    public void defaultPropertyAndDirection() {
        SortHelper sortHelper = new SortHelper();
        String defaultPropertyAndDirection = sortHelper.defaultPropertyAndDirection();
        assertEquals("id:ASC", defaultPropertyAndDirection);
    }
}
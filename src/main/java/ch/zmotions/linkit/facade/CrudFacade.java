package ch.zmotions.linkit.facade;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CrudFacade<T> {
    List<T> findAll();
    Optional<T> findById(UUID id);
    Optional<T> create(T entity);
    Optional<T> update(T entity);
    void removeById(UUID id);
    void removeAll();
}

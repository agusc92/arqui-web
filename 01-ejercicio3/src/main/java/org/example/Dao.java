package org.example;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {

    Optional<T> get(int id);

    List<T> getAll();

    void add(T t);

    void delete(int id);

    void update(T t);
}

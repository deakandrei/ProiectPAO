package repositories;

import models.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryRepository {
    void save(Category category);

    Optional<Category> find(String id);

    List<Category> readAll();

    static ICategoryRepository build(RepositoryType type) {
        return new FileCategoryRepository();
    }
}

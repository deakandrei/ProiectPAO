package repositories;

import models.Category;

import java.util.Optional;

public interface ICategoryRepository {
    void save(Category category);

    Optional<Category> find(int id);

    static ICategoryRepository build(RepositoryType type) {
        return new FileCategoryRepository();
    }
}

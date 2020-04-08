package repositories;

import models.Category;

import java.util.Optional;

public interface ICategoryRepository {
    void Save(Category category);

    Optional<Category> Find(int id);

    static ICategoryRepository build(RepositoryType type) {
        return new FileCategoryRepository();
    }
}

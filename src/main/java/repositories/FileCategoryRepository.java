package repositories;

import models.Category;

import java.util.Optional;

public class FileCategoryRepository implements ICategoryRepository {
    @Override
    public void Save(Category category) {

    }

    @Override
    public Optional<Category> Find(int id) {
        return Optional.empty();
    }
}

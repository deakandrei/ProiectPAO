package repositories;

import models.Category;

import java.util.Optional;

public class FileCategoryRepository implements ICategoryRepository {
    @Override
    public void save(Category category) {

    }

    @Override
    public Optional<Category> find(int id) {
        return Optional.empty();
    }
}

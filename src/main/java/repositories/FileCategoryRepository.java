package repositories;

import managers.CSVFilesLocationManager;
import managers.CSVFileManager;
import managers.csvlayout.CategoryLayout;
import models.Category;

import java.util.List;
import java.util.Optional;

public class FileCategoryRepository implements ICategoryRepository {
    private CSVFileManager file = CSVFileManager.getInstance(
            CSVFilesLocationManager.CATEGORIES.getPath()
    );

    @Override
    public void save(Category category) {
        Optional<Integer> index = file.findFirstMatch(x -> x.equals(category.getName()));
        if(!index.isPresent()) {
            file.addLine(CategoryLayout.serialize(category));
        }
    }

    @Override
    public Optional<Category> find(String id) {
        /* in the csv file, the name is on the first column */
        Optional<Integer> index = file.findFirstMatch(line -> id.equals(line.get(0)));
        if(index.isPresent()) {
            List<String> line = file.getLine(index.get());
            return Optional.of(CategoryLayout.deserialize(line));
        }
        return Optional.empty();
    }
}

package repositories;

import managers.CSVFileLocation;
import managers.CSVFileManager;
import managers.csvlayout.CategoryLayout;
import models.Category;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileCategoryRepository implements ICategoryRepository {
    private CSVFileManager file = CSVFileManager.getInstance(
            CSVFileLocation.CATEGORIES.getPath()
    );

    @Override
    public void save(Category category) {
        Optional<Integer> index = file.findFirstMatch(
                x -> x.get(CategoryLayout.NAME.ordinal()).equals(category.getName()));
        if(index.isEmpty()) {
            file.addLine(CategoryLayout.serialize(category));
        } else {
            file.replaceLine(index.get(), CategoryLayout.serialize(category));
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

    @Override
    public List<Category> readAll() {
        return file.findAllMatches(x -> true).stream()
                .map(CategoryLayout::deserialize)
                .collect(Collectors.toList());
    }
}

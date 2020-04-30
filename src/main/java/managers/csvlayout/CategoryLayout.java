package managers.csvlayout;

import models.Category;

import java.util.List;

public enum CategoryLayout {
    NAME, DESCRIPTION;

    public static List<String> serialize(Category category) {
        return List.of(category.getName(), category.getDescription());
    }

    public static Category deserialize(List<String> line) {
        return new Category(line.get(NAME.ordinal()),
                            line.get(DESCRIPTION.ordinal()));
    }
}

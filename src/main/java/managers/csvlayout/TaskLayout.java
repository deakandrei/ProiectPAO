package managers.csvlayout;

import models.Task;

import java.util.List;
import java.util.Optional;

public enum  TaskLayout {
   ID, TITLE, DESCRIPTION, CATEGORY, STATUS,
    /* The following columns are not stored in the task object
     * but are used to make connections with other objects */
    BOARD_ID; // id of the board; this is an optional field.

    public static List<String> serialize(Task task) {
        /* Make sure to save the fields in the same order as
         *  in the FieldColumn enum */
        Integer id = task.getId().get();
        return List.of(id.toString(), task.getTitle(), task.getDescription(),
                task.getStatus().toString(), task.getCategory());
    }

    public static Task deserialize(List<String> line) {
        Task T = new Task(
                line.get(TITLE.ordinal()),
                line.get(DESCRIPTION.ordinal()),
                line.get(CATEGORY.ordinal())
        );
        T.setStatus(Task.Status.valueOf(line.get(STATUS.ordinal())));
        T.setId(Optional.of(Integer.getInteger(line.get(ID.ordinal()))));
        return T;
    }
}

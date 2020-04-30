package managers.csvlayout;

import managers.DateFormatManager;
import models.Task;
import models.TimedTask;

import java.util.List;
import java.util.Optional;

public enum TimedTaskLayout {
    ID, TITLE, DESCRIPTION, CATEGORY, STATUS, DATE,

    BOARD_ID;

    public static List<String> serialize(TimedTask task) {
        /* Make sure to save the fields in the same order as
         *  in the FieldColumn enum */
        Integer id = task.getId().get();
        return List.of(id.toString(), task.getTitle(),
                task.getDescription(), task.getCategory(),
                task.getStatus().toString(),
                DateFormatManager.format(task.getDueDate()));
    }

    public static TimedTask deserialize(List<String> line) {
        TimedTask t = new TimedTask(
                line.get(TITLE.ordinal()),
                line.get(DESCRIPTION.ordinal()),
                line.get(CATEGORY.ordinal()),
                DateFormatManager.parse(line.get(DATE.ordinal()))
        );
        t.setStatus(Task.Status.valueOf(line.get(STATUS.ordinal())));
        t.setId(Optional.of(Integer.getInteger(line.get(ID.ordinal()))));
        return t;
    }
}

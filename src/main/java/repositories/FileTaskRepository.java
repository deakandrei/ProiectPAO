package repositories;

import Utils.CSV;
import Utils.CSVFindIdCriteria;
import managers.CSVFileManager;
import models.Task;

import java.util.List;
import java.util.Optional;

public class FileTaskRepository implements ITaskRepository {
    private CSVFileManager file = CSVFileManager.getInstance("csvdata/tasks.csv");

    @Override
    public void save(Task task) {
        if(task.getId().isEmpty()) {
            /* this is a new task then */
            int unusedId = CSV.findUnusedId(file, FieldColumn.ID.ordinal());
            task.setId(Optional.of(unusedId));
            file.addLine(serialize(task));
            return;
        }
        Integer id = task.getId().get();
        Optional<Integer> index = file.findFirstMatch(
                new CSVFindIdCriteria(FieldColumn.ID.ordinal(), id));
        if(index.isPresent()) {
            file.replaceLine(index.get(), serialize(task));
        } else {
            System.out.println("The id " + id.toString() +
                    " was expected to be present in the file.");
        }
    }

    @Override
    public Optional<Task> find(int id) {
        Optional<Integer> index = file.findFirstMatch(
                new CSVFindIdCriteria(FieldColumn.ID.ordinal(), id));
        if(index.isPresent()) {
            List<String> line = file.getLine(index.get());
            String title = line.get(FieldColumn.TITLE.ordinal());
            String description = line.get(FieldColumn.DESCRIPTION.ordinal());
            String category = line.get(FieldColumn.CATEGORY.ordinal());
            Task t = new Task(title, description, category);
            t.setId(Optional.of(id));
            return Optional.of(t);
        }
        return Optional.empty();
    }

    public enum FieldColumn {
        ID, TITLE, DESCRIPTION, CATEGORY, STATUS,
        /* The following columns are not stored in the task object
        * but are used to make connections with other objects */
        BOARD_ID // id of the board
    }

    private List<String> serialize(Task task) {
        /* Make sure to save the fields in the same order as
        *  in the FieldColumn enum */
        Integer id = task.getId().get();
        return List.of(id.toString(), task.getTitle(), task.getDescription(),
                task.getStatus().toString(), task.getCategory());
    }
}

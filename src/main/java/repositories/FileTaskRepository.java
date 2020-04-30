package repositories;

import Utils.CSVFindIdCriteria;
import managers.CSVFilesLocationManager;
import managers.CSVFileManager;
import managers.csvlayout.TaskLayout;
import models.Task;

import java.util.List;
import java.util.Optional;

public class FileTaskRepository implements ITaskRepository {
    private CSVFileManager file = CSVFileManager.getInstance(
            CSVFilesLocationManager.TASKS.getPath()
    );

    @Override
    public void save(Task task) {
        if(task.getId().isEmpty()) {
            /* this is a new task then */
            int unusedId = CSVFileManager.findUnusedId(file, TaskLayout.ID.ordinal());
            task.setId(Optional.of(unusedId));
            file.addLine(TaskLayout.serialize(task));
            return;
        }
        Integer id = task.getId().get();
        Optional<Integer> index = file.findFirstMatch(
                new CSVFindIdCriteria(TaskLayout.ID.ordinal(), id));
        if(index.isPresent()) {
            file.updateFirstColumns(index.get(), TaskLayout.serialize(task));
        } else {
            System.out.println("The id " + id.toString() +
                    " was expected to be present in the file.");
        }
    }

    @Override
    public Optional<Task> find(int id) {
        Optional<Integer> index = file.findFirstMatch(
                new CSVFindIdCriteria(TaskLayout.ID.ordinal(), id));
        if(index.isPresent()) {
            List<String> line = file.getLine(index.get());
            return Optional.of(TaskLayout.deserialize(line));
        }
        return Optional.empty();
    }
}

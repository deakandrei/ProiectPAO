package repositories;

import Utils.CSVFindIdCriteria;
import managers.CSVFilesLocationManager;
import managers.CSVFileManager;
import managers.csvlayout.TimedTaskLayout;
import models.TimedTask;

import java.util.List;
import java.util.Optional;

public class FileTimedTaskRepository implements ITimedTaskRepository {
    private CSVFileManager file = CSVFileManager.getInstance(
            CSVFilesLocationManager.TIMED_TASKS.getPath()
    );

    @Override
    public void save(TimedTask task) {
        if(task.getId().isEmpty()) {
            /* this is a new task then */
            int unusedId = CSVFileManager.findUnusedId(file, TimedTaskLayout.ID.ordinal());
            task.setId(Optional.of(unusedId));
            file.addLine(TimedTaskLayout.serialize(task));
            return;
        }
        Integer id = task.getId().get();
        Optional<Integer> index = file.findFirstMatch(
                new CSVFindIdCriteria(TimedTaskLayout.ID.ordinal(), id));
        if(index.isPresent()) {
            file.updateFirstColumns(index.get(), TimedTaskLayout.serialize(task));
        } else {
            System.out.println("The id " + id.toString() +
                    " was expected to be present in the file "
                    + file.getFilename());
        }
    }

    @Override
    public Optional<TimedTask> find(int id) {
        Optional<Integer> index = file.findFirstMatch(
                new CSVFindIdCriteria(TimedTaskLayout.ID.ordinal(), id));
        if(index.isPresent()) {
            List<String> line = file.getLine(index.get());
            return Optional.of(TimedTaskLayout.deserialize(line));
        }
        return Optional.empty();
    }

}

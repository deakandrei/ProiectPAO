package repositories;

import Utils.CSVFindIdCriteria;
import managers.CSVFileLocation;
import managers.CSVFileManager;
import managers.DateFormatManager;
import managers.csvlayout.TaskLayout;
import managers.csvlayout.TimedTaskLayout;
import models.Task;
import models.TimedTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileTimedTaskRepository implements ITimedTaskRepository {
    private final CSVFileManager file = CSVFileManager.getInstance(
            CSVFileLocation.TIMED_TASKS.getPath()
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

    @Override
    public List<TimedTask> getDeadlinesAfter(Date date) {
        return file.findAllMatches(
                (line -> DateFormatManager.parse(
                        line.get(TimedTaskLayout.DATE.ordinal()))
                        .after(date)))
                .stream()
                .map(TimedTaskLayout::deserialize)
                .collect(Collectors.toList());

    }

    @Override
    public List<TimedTask> readAll() {
        return file.findAllMatches(x->true).stream()
                .map(TimedTaskLayout::deserialize)
                .collect(Collectors.toList());
    }

    @Override
    public void updateBoardId(TimedTask task, Integer boardId) {
        Optional<Integer> taskId = task.getId();
        if(taskId.isEmpty()) {
            int unusedId = CSVFileManager.findUnusedId(file, TimedTaskLayout.ID.ordinal());
            task.setId(Optional.of(unusedId));
        }
        List<String> line = new ArrayList<>(TimedTaskLayout.serialize(task));
        line.add(boardId.toString());
        if(taskId.isEmpty()) {
            file.addLine(line);
        } else {
            Optional<Integer> index = file.findFirstMatch(
                    new CSVFindIdCriteria(TimedTaskLayout.ID.ordinal(), taskId.get())
            );
            if(index.isEmpty()) {
                System.out.println("Error updating task with id " +
                        taskId.get().toString());
            } else {
                file.replaceLine(index.get(), line);
            }
        }
    }

}

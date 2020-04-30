package repositories;

import Utils.CSVFindIdCriteria;
import managers.CSVFileLocation;
import managers.CSVFileManager;
import managers.csvlayout.TaskLayout;
import models.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileTaskRepository implements ITaskRepository {
    private CSVFileManager file = CSVFileManager.getInstance(
            CSVFileLocation.TASKS.getPath()
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

    @Override
    public List<Task> readAll() {
        return file.findAllMatches(x->true).stream()
                .map(TaskLayout::deserialize)
                .collect(Collectors.toList());
    }

    @Override
    public void updateBoardId(Task task, Integer boardId) {
        Optional<Integer> taskId = task.getId();
        if(taskId.isEmpty()) {
            int unusedId = CSVFileManager.findUnusedId(file, TaskLayout.ID.ordinal());
            task.setId(Optional.of(unusedId));
        }
        List<String> line = new ArrayList<>(TaskLayout.serialize(task));
        line.add(boardId.toString());
        if(taskId.isEmpty()) {
            file.addLine(line);
        } else {
            Optional<Integer> index = file.findFirstMatch(
                    new CSVFindIdCriteria(TaskLayout.ID.ordinal(), taskId.get())
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

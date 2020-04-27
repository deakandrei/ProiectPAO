package repositories;

import models.Task;

import java.util.Optional;

public class FileTaskRepository implements ITaskRepository {
    @Override
    public void save(Task task) {

    }

    @Override
    public Optional<Task> find(int id) {
        return Optional.empty();
    }
}

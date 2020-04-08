package repositories;

import models.Task;

import java.util.Optional;

public class FileTaskRepository implements ITaskRepository {
    @Override
    public void Save(Task task) {

    }

    @Override
    public Optional<Task> Find(int id) {
        return Optional.empty();
    }
}

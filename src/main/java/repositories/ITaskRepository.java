package repositories;

import models.Task;

import java.util.Optional;

public interface ITaskRepository {
    void save(Task task);

    Optional<Task> find(int id);

    static ITaskRepository build(RepositoryType type) {
        return new FileTaskRepository();
    }
}

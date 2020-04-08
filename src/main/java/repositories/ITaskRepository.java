package repositories;

import models.Task;

import java.util.Optional;

public interface ITaskRepository {
    void Save(Task task);

    Optional<Task> Find(int id);

    static ITaskRepository build(RepositoryType type) {
        return new FileTaskRepository();
    }
}

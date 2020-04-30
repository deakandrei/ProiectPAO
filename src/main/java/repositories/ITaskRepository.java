package repositories;

import models.Task;

import java.util.List;
import java.util.Optional;

public interface ITaskRepository {
    void save(Task task);

    Optional<Task> find(int id);

    List<Task> readAll();

    void updateBoardId(Task task, Integer boardId);

    static ITaskRepository build(RepositoryType type) {
        return new FileTaskRepository();
    }
}

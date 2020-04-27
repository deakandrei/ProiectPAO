package repositories;

import models.TimedTask;

import java.util.Optional;

public interface ITimedTaskRepository {
    void save(TimedTask task);

    Optional<TimedTask> find(int id);

    static ITimedTaskRepository build(RepositoryType type) {
        return new FileTimedTaskRepository();
    }
}

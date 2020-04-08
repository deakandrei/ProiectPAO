package repositories;

import models.TimedTask;

import java.util.Optional;

public interface ITimedTaskRepository {
    void Save(TimedTask task);

    Optional<TimedTask> Find(int id);

    static ITimedTaskRepository build(RepositoryType type) {
        return new FileTimedTaskRepository();
    }
}

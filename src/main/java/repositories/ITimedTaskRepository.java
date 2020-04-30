package repositories;

import models.TimedTask;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface ITimedTaskRepository {
    void save(TimedTask task);

    Optional<TimedTask> find(int id);

    List<TimedTask> getDeadlinesAfter(Date date);

    List<TimedTask> readAll();

    void updateBoardId(TimedTask task, Integer boardId);

    static ITimedTaskRepository build(RepositoryType type) {
        return new FileTimedTaskRepository();
    }
}

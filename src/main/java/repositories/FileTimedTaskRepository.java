package repositories;

import models.TimedTask;

import java.util.Optional;

public class FileTimedTaskRepository implements ITimedTaskRepository {
    @Override
    public void save(TimedTask task) {

    }

    @Override
    public Optional<TimedTask> find(int id) {
        return Optional.empty();
    }
}

package repositories;

import models.TimedTask;

import java.util.Optional;

public class FileTimedTaskRepository implements ITimedTaskRepository {
    @Override
    public void Save(TimedTask task) {

    }

    @Override
    public Optional<TimedTask> Find(int id) {
        return Optional.empty();
    }
}

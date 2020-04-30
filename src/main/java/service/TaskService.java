package service;

import models.Appointment;
import models.Board;
import models.Task;
import models.TimedTask;
import repositories.IBoardRepository;
import repositories.ICategoryRepository;
import repositories.ITaskRepository;
import repositories.ITimedTaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

public class TaskService {
    /* This service deals with Boards and Tasks/TimedTasks */
    private ITaskRepository taskRepository;
    private ITimedTaskRepository timedTaskRepository;
    private IBoardRepository boardRepository;

    private ICategoryRepository categoryRepository;

    private TaskService() {
        FileReadService service = FileReadService.getInstance();
        taskRepository = service.taskRepository;
        timedTaskRepository = service.timedTaskRepository;
        boardRepository = service.boardRepository;
        categoryRepository = service.categoryRepository;
    }

    private static final class SingletonHolder {
        private static TaskService INSTANCE = new TaskService();
    }

    public SortedSet<Appointment> getAllAppointments() {
        return null; //new TreeSet<Appointment>();
    }

    public static TaskService getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void addTaskToBoard(Board board, Task task) {
        if(board.getId().isEmpty()) {
            boardRepository.save(board);
        }
        taskRepository.updateBoardId(task, board.getId().get());
        AuditService.log("Add task to board");
    }

    public void addTimedTaskToBoard(Board board, TimedTask task) {
        if(board.getId().isEmpty()) {
            boardRepository.save(board);
        }
        taskRepository.updateBoardId(task, board.getId().get());
        AuditService.log("Add task with deadline to board");
    }
}

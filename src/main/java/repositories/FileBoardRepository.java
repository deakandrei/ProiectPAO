package repositories;

import Utils.CSVFindIdCriteria;
import managers.CSVFileManager;
import managers.CSVFilesLocationManager;
import managers.csvlayout.BoardLayout;
import managers.csvlayout.TaskLayout;
import managers.csvlayout.TimedTaskLayout;
import models.Board;
import models.Task;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FileBoardRepository implements IBoardRepository {
    private CSVFileManager boardFile = CSVFileManager.getInstance(
            CSVFilesLocationManager.BOARDS.getPath()
    );
    private CSVFileManager taskFile = CSVFileManager.getInstance(
            CSVFilesLocationManager.TASKS.getPath()
    );
    private CSVFileManager timedTaskFile = CSVFileManager.getInstance(
            CSVFilesLocationManager.TIMED_TASKS.getPath()
    );

    @Override
    public void save(Board board) {
        Optional<Integer> id = board.getId();
        if(id.isEmpty()) {
            int freeId = CSVFileManager.findUnusedId(boardFile, BoardLayout.ID.ordinal());
            board.setId(Optional.of(freeId));
            boardFile.addLine(BoardLayout.serialize(board));
        } else {
            Optional<Integer> index = boardFile.findFirstMatch(
                    new CSVFindIdCriteria(BoardLayout.ID.ordinal(),id.get()));
            if(index.isPresent()) {
                boardFile.updateFirstColumns(index.get(), BoardLayout.serialize(board));
            } else {
                System.out.println("ID " + id.get() + " was not found in "
                                    + boardFile.getFilename());
            }
        }
    }

    @Override
    public Optional<Board> find(int id) {
        Optional<Integer> index = boardFile.findFirstMatch(
                new CSVFindIdCriteria(BoardLayout.ID.ordinal(), id));
        if(index.isPresent()) {
            Board B = BoardLayout.deserialize(boardFile.getLine(index.get()));
            /* To populate the 3 lists with tasks, we look into
            * the tasks file and also TimedTasks file */
            List<Task> tasksInfo =
                taskFile.findAllMatches(
                        line -> taskLineFilter(line, TaskLayout.BOARD_ID.ordinal(),
                                                String.valueOf(id)))
                    .stream()
                    .map(t -> TaskLayout.deserialize(t))
                    .collect(Collectors.toList());
            tasksInfo.addAll(
                    timedTaskFile.findAllMatches(
                            line -> taskLineFilter(line,
                                    TimedTaskLayout.BOARD_ID.ordinal(),
                                    String.valueOf(id)))
                            .stream()
                            .map(t -> TimedTaskLayout.deserialize(t))
                            .collect(Collectors.toList())
                    );
            /* Now sort the taks according to their status */
            for(Task task : tasksInfo) {
                switch (task.getStatus()) {
                    case TODO:
                        B.addTodo(task);
                        break;
                    case IN_PROGRESS:
                        B.addInProgress(task);
                        break;
                    case DONE:
                        B.addDone(task);
                        break;
                }
            }
            return Optional.of(B);
        }
        return Optional.empty();
    }

    /* Return true if the line has at least columnNr+1 elements and
    * the value at columnNr equals the provided value */
    private boolean taskLineFilter(List<String> line, int columnNr, String value) {
        return line.size() > columnNr && line.get(columnNr).equals(value);
    }

}

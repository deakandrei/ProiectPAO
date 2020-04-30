package repositories;

import Utils.CSVFindIdCriteria;
import managers.CSVFileManager;
import managers.CSVFileLocation;
import managers.csvlayout.BoardLayout;
import managers.csvlayout.TaskLayout;
import managers.csvlayout.TimedTaskLayout;
import models.Board;
import models.Task;
import models.TimedTask;

import java.util.*;
import java.util.stream.Collectors;

public class FileBoardRepository implements IBoardRepository {
    private final CSVFileManager boardFile = CSVFileManager.getInstance(
            CSVFileLocation.BOARDS.getPath()
    );
    private final CSVFileManager taskFile = CSVFileManager.getInstance(
            CSVFileLocation.TASKS.getPath()
    );
    private final CSVFileManager timedTaskFile = CSVFileManager.getInstance(
            CSVFileLocation.TIMED_TASKS.getPath()
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
                    .map(TaskLayout::deserialize)
                    .collect(Collectors.toList());
            tasksInfo.addAll(
                    timedTaskFile.findAllMatches(
                            line -> taskLineFilter(line,
                                    TimedTaskLayout.BOARD_ID.ordinal(),
                                    String.valueOf(id)))
                            .stream()
                            .map(TimedTaskLayout::deserialize)
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

    @Override
    public List<Board> readAll() {
        Map<String, Board> boards = boardFile.findAllMatches(x->true).stream()
                .map(BoardLayout::deserialize)
                .collect(Collectors.toMap(
                        B -> B.getId().get().toString(),
                        B -> B
                ));

        taskFile.findAllMatches(
                line -> fieldIsOneOf(line, TaskLayout.BOARD_ID.ordinal(), boards.keySet()))
                .forEach(line -> {
            Task task = TaskLayout.deserialize(line);
            String boardId = line.get(TaskLayout.BOARD_ID.ordinal());
            Board B = boards.get(boardId);
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
        });

        timedTaskFile.findAllMatches(
                line -> fieldIsOneOf(line, TimedTaskLayout.BOARD_ID.ordinal(), boards.keySet()))
                .forEach(line -> {
                    TimedTask task = TimedTaskLayout.deserialize(line);
                    String boardId = line.get(TaskLayout.BOARD_ID.ordinal());
                    Board B = boards.get(boardId);
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
                });
        return new ArrayList<>(boards.values());
    }

    private boolean fieldIsOneOf(List<String> line, int column, Set<String> values) {
        return line.size() > column && values.contains(line.get(column));
    }

    /* Return true if the line has at least columnNr+1 elements and
    * the value at columnNr equals the provided value */
    private boolean taskLineFilter(List<String> line, int columnNr, String value) {
        return line.size() > columnNr && line.get(columnNr).equals(value);
    }

}

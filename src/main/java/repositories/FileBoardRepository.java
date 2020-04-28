package repositories;

import Utils.CSV;
import Utils.CSVFindIdCriteria;
import managers.CSVFileManager;
import models.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileBoardRepository implements IBoardRepository {
    private CSVFileManager file = CSVFileManager.getInstance("csvdata/boards.csv");

    @Override
    public void save(Board board) {
        Optional<Integer> id = board.getId();
        if(id.isEmpty()) {
            int freeId = CSV.findUnusedId(file, FieldColumn.ID.ordinal());
            board.setId(Optional.of(freeId));
            file.addLine(serialize(board));
        } else {
            Optional<Integer> index = file.findFirstMatch(
                    new CSVFindIdCriteria(FieldColumn.ID.ordinal(),id.get()));
            if(index.isPresent()) {
                file.replaceLine(index.get(), serialize(board));
            } else {
                System.out.println("ID " + id.get() + " was not found in "
                                    + file.getFilename());
            }
        }
    }

    @Override
    public Optional<Board> find(int id) {
        Optional<Integer> index = file.findFirstMatch(
                new CSVFindIdCriteria(FieldColumn.ID.ordinal(), id));
        if(index.isPresent()) {
            List<String> line = file.getLine(index.get());
            Board B = new Board(line.get(FieldColumn.TITLE.ordinal()));
            B.setId(Optional.of(id));
            return Optional.of(B);
        }
        return Optional.empty();
    }

    public enum FieldColumn {
        ID, TITLE
    }

    private List<String> serialize(Board board) {
        return new ArrayList<>();
    }
}

package managers.csvlayout;

import models.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum BoardLayout {
    ID, TITLE;

    public static List<String> serialize(Board board) {
        List<String> line = List.of(
                board.getId().get().toString(),
                board.getTitle()
        );
        return line;
    }

    public static Board deserialize(List<String> line) {
        Board board = new Board(line.get(TITLE.ordinal()));
        board.setId(Optional.of(Integer.getInteger(line.get(ID.ordinal()))));
        return board;
    }
}

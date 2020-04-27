package repositories;

import models.Board;

import java.util.Optional;

public class FileBoardRepository implements IBoardRepository {
    @Override
    public void save(Board board) {

    }

    @Override
    public Optional<Board> find(int id) {
        return Optional.empty();
    }
}

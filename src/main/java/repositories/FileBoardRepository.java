package repositories;

import models.Board;

import java.util.Optional;

public class FileBoardRepository implements IBoardRepository {
    @Override
    public void Save(Board board) {

    }

    @Override
    public Optional<Board> Find(int id) {
        return Optional.empty();
    }
}

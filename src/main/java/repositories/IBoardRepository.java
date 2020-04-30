package repositories;

import models.Board;

import java.util.List;
import java.util.Optional;

public interface IBoardRepository {
    void save(Board board);

    Optional<Board> find(int id);

    List<Board> readAll();

    static IBoardRepository build(RepositoryType type) {
        return new FileBoardRepository();
    }
}

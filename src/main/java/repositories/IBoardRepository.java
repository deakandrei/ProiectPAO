package repositories;

import models.Board;

import java.util.Optional;

public interface IBoardRepository {
    void save(Board board);

    Optional<Board> find(int id);

    static IBoardRepository build(RepositoryType type) {
        return new FileBoardRepository();
    }
}

package repositories;

import models.Board;

import java.util.Optional;

public interface IBoardRepository {
    void Save(Board board);

    Optional<Board> Find(int id);

    static IBoardRepository build(RepositoryType type) {
        return new FileBoardRepository();
    }
}

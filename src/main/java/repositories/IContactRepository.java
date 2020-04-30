package repositories;

import models.Contact;

import java.util.List;
import java.util.Optional;

public interface IContactRepository {
    void save(Contact contact);

    Optional<Contact> find(int id);

    List<Contact> readAll();

    static IContactRepository build(RepositoryType type) {
        return new FileContactRepository();
    }
}

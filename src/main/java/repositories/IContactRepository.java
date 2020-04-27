package repositories;

import models.Contact;

import java.util.Optional;

public interface IContactRepository {
    void save(Contact contact);

    Optional<Contact> find(int id);

    static IContactRepository build(RepositoryType type) {
        return new FileContactRepository();
    }
}

package repositories;

import models.Contact;

import java.util.Optional;

public interface IContactRepository {
    void Save(Contact contact);

    Optional<Contact> Find(int id);

    static IContactRepository build(RepositoryType type) {
        return new FileContactRepository();
    }
}

package repositories;

import models.Contact;

import java.util.Optional;

public class FileContactRepository implements IContactRepository {
    @Override
    public void Save(Contact contact) {

    }

    @Override
    public Optional<Contact> Find(int id) {
        return Optional.empty();
    }
}

package repositories;

import models.Contact;

import java.util.Optional;

public class FileContactRepository implements IContactRepository {
    @Override
    public void save(Contact contact) {

    }

    @Override
    public Optional<Contact> find(int id) {
        return Optional.empty();
    }
}

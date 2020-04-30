package repositories;

import Utils.CSVFindIdCriteria;
import managers.CSVFileManager;
import managers.CSVFilesLocationManager;
import managers.csvlayout.ContactLayout;
import models.Contact;

import java.util.List;
import java.util.Optional;

import static managers.csvlayout.ContactLayout.serialize;

public class FileContactRepository implements IContactRepository {
    private CSVFileManager file = CSVFileManager.getInstance(
            CSVFilesLocationManager.CONTACTS.getPath()
    );

    @Override
    public void save(Contact contact) {
        Optional<Integer> id = contact.getId();
        if(id.isEmpty()) {
            int unusedId = CSVFileManager.findUnusedId(file, ContactLayout.ID.ordinal());
            contact.setId(Optional.of(unusedId));
            file.addLine(ContactLayout.serialize(contact));
        }
        Optional<Integer> index = file.findFirstMatch(new
                        CSVFindIdCriteria(ContactLayout.ID.ordinal(), id.get()));
        if(index.isPresent()) {
            file.updateFirstColumns(index.get(), serialize(contact));
        } else {
            System.out.println("Expected id " + id.get().toString() +
                    " in file" + file.getFilename());
        }
    }

    @Override
    public Optional<Contact> find(int id) {
        Optional<Integer> index = file.findFirstMatch(new
                        CSVFindIdCriteria(ContactLayout.ID.ordinal(), id));
        if(index.isPresent()) {
            List<String> line = file.getLine(index.get());
            return Optional.of(ContactLayout.deserialize(line));
        }
        return Optional.empty();
    }
}

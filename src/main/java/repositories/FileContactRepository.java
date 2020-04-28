package repositories;

import Utils.CSV;
import Utils.CSVFindIdCriteria;
import managers.CSVFileManager;
import models.Contact;

import java.util.List;
import java.util.Optional;

public class FileContactRepository implements IContactRepository {
    private CSVFileManager file = CSVFileManager.getInstance("csvdata/contacts.csv");

    @Override
    public void save(Contact contact) {
        Optional<Integer> id = contact.getId();
        if(id.isEmpty()) {
            int unusedId = CSV.findUnusedId(file, FieldColumn.ID.ordinal());
            contact.setId(Optional.of(unusedId));
            file.addLine(serialize(contact));
        }
        Optional<Integer> index = file.findFirstMatch(new
                        CSVFindIdCriteria(FieldColumn.ID.ordinal(), id.get()));
        if(index.isPresent()) {
            file.replaceLine(index.get(), serialize(contact));
        } else {
            System.out.println("Expected id " + id.get().toString() +
                    " in file" + file.getFilename());
        }
    }

    @Override
    public Optional<Contact> find(int id) {
        Optional<Integer> index = file.findFirstMatch(new
                        CSVFindIdCriteria(FieldColumn.ID.ordinal(), id));
        if(index.isPresent()) {
            List<String> line = file.getLine(index.get());
            String name = line.get(FieldColumn.NAME.ordinal());
            Contact c = new Contact(name);
            c.setId(Optional.of(id));
            return Optional.of(c);
        }
        return Optional.empty();
    }

    /* Enum with the columns where each field is in the csv file. */
    public enum FieldColumn {
        ID, NAME,


    }

    private List<String> serialize(Contact contact) {
        Integer id = contact.getId().get();
        return List.of(id.toString(), contact.getName());
    }
}

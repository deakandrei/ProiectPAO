package managers.csvlayout;

import models.Contact;

import java.util.List;
import java.util.Optional;

public enum ContactLayout {
    ID, NAME,;

    public static List<String> serialize(Contact contact) {
        Integer id = contact.getId().get();
        return List.of(id.toString(), contact.getName());
    }

    public static Contact deserialize(List<String> line) {
        Contact contact = new Contact(line.get(NAME.ordinal()));
        Integer id = Integer.getInteger(line.get(ID.ordinal()));
        contact.setId(Optional.ofNullable(id));
        return contact;
    }
}

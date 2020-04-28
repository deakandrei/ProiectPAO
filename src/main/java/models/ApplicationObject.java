package models;

import java.util.Optional;

/*
* Functionality common to all objects stored in the database
* and need a separate id field.
* */
public class ApplicationObject {
    protected Optional<Integer> id = Optional.empty();

    public Optional<Integer> getId() {
        return id;
    }

    public void setId(Optional<Integer> id) {
        this.id = id;
    }

}

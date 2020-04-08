package models;

import java.util.Optional;

/*
* Functionality common to all objects stored in the database
* */
public class ApplicationObject {
    private Optional<Integer> id = Optional.empty();

    public Optional<Integer> getId() {
        return id;
    }

    public void setId(Optional<Integer> id) {
        this.id = id;
    }

}

package models;

public class Contact extends ApplicationObject {
    private String name;

    public Contact(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        if(id.isPresent()) {
            return id.get();
        }
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  Contact) {
            Contact other = (Contact) obj;
            if(this.id.isEmpty() || other.id.isEmpty()) {
                return false;
            }
            return this.id.get().equals(other.id.get());
        }
        return false;
    }
}

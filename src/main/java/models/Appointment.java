package models;

import java.util.*;

public class Appointment extends ApplicationObject {
    private String title;
    private String description;
    private Date date;
    private Set<Contact> participants;


    public Appointment(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.participants = new HashSet<>();
        this.date = date;
    }

    public Set<Contact> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<Contact> participants) {
        this.participants = participants;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        if(id.isEmpty())
            return title.hashCode();
        return id.get();
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Appointment) {
            Appointment other = (Appointment) obj;
            if(this.id.isEmpty() || other.id.isEmpty()) {
                return false;
            }
            return this.id.get().equals(other.id.get());
        }
        return false;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", participants=" + participants +
                '}';
    }
}

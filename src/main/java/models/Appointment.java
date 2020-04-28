package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Appointment extends ApplicationObject {
    private String title;
    private String description;
    private Date date;
    private List<Contact> participants;


    public Appointment(String title, String description, Date date) {
        this.title = title;
        this.description = description;
        this.participants = new ArrayList<>();
        this.date = date;
    }

    public List<Contact> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Contact> participants) {
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
}

package models;

import java.util.Date;
import java.util.List;

public class Appointment extends ApplicationObject {
    private List<Contact> participants;
    private Date date;
    private String title;
    private String description;


    public Appointment(List<Contact> participants, Date date, String title, String description) {
        this.participants = participants;
        this.date = date;
        this.title = title;
        this.description = description;
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

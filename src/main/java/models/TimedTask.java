package models;

import java.util.Date;

public class TimedTask extends Task {
    private Date dueDate;

    public TimedTask(String title, String description, Date deadline) {
        super(title, description);
        this.dueDate = deadline;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
}

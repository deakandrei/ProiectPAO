package models;

import java.util.Date;
import java.util.Objects;

public class TimedTask extends Task {
    private Date dueDate;

    public TimedTask(String title, String description, String category, Date deadline) {
        super(title, description, category);
        this.dueDate = deadline;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "TimedTask{" +
                "dueDate=" + dueDate +
                '}';
    }
    @Override
    public int hashCode() {
        if(id.isEmpty())
            return super.getTitle().hashCode();
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
}

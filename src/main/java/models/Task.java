package models;

public class Task extends ApplicationObject {

    public enum Status {
        TODO, IN_PROGRESS, DONE
    }

    private String title;
    private String description;
    private String category;
    private Status status;

    public Task(String title, String description, String category) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.status = Status.TODO;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

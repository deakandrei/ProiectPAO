package models;

import java.util.List;

/* A simple Kanban board to organize the tasks */
public class Board extends ApplicationObject {
    private String title;
    private List<Task> todo;
    private List<Task> inProgress;
    private List<Task> done;

    public Board(String title, List<Task> todo, List<Task> inProgress, List<Task> done) {
        this.title = title;
        this.todo = todo;
        this.inProgress = inProgress;
        this.done = done;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Task> getTodo() {
        return todo;
    }

    public void setTodo(List<Task> todo) {
        this.todo = todo;
    }

    public List<Task> getInProgress() {
        return inProgress;
    }

    public void setInProgress(List<Task> inProgress) {
        this.inProgress = inProgress;
    }

    public List<Task> getDone() {
        return done;
    }

    public void setDone(List<Task> done) {
        this.done = done;
    }
}

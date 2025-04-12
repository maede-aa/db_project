package todo.entity;

import db.Entity;

import java.util.Date;

public class Step extends Entity {
    public static final int STEP_ENTITY_CODE = 22;

    public enum Status {
        NotStarted,
        Completed
    }

    private String title;
    private Status status;
    private int taskRef;
    private Date creationDate;
    private Date lastModificationDate;

    public Step() {
        this.status = Status.NotStarted;
        this.creationDate = new Date();
        this.lastModificationDate = new Date();
    }

    public void setStatus(Status status) {
        this.status = status;
        this.lastModificationDate = new Date();
    }

    public Status getStatus() {
        return status;
    }

    public void setTaskRef(int taskRef) {
        this.taskRef = taskRef;
        this.lastModificationDate = new Date();
    }

    public int getTaskRef() {
        return taskRef;
    }

    public void setTitle(String title) {
        this.title = title;
        this.lastModificationDate = new Date();
    }

    public String getTitle() {
        return title;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastModificationDate() {
        return lastModificationDate;
    }

    @Override
    public Entity copy() {
        Step copy = new Step();
        copy.id = this.id;
        copy.status = this.status;
        copy.taskRef = this.taskRef;
        copy.title = this.title;
        copy.creationDate = this.creationDate;
        copy.lastModificationDate = this.lastModificationDate;
        return copy;
    }

    @Override
    public int getEntityCode() {
        return STEP_ENTITY_CODE;
    }
}
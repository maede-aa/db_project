package todo.entity;

import db.*;

import java.util.Date;

public class Task extends Entity implements Trackable {
    public static final int TASK_ENTITY_CODE = 11;

    public enum Status {
        NotStarted,
        InProgress,
        Completed
    }

    public enum Category {
        Personal,
        University,
        Work,
        Other
    }

    public enum Priority {
        Low,
        Medium,
        High,
        NoPriority;
    }

    private String title;
    private String description;
    private Date dueDate;
    private Status status;
    private Date creationDate;
    private Date lastModificationDate;
    private Category category;
    private Priority priority;

    public Task() {
        this.status = Status.NotStarted;
        this.category = Category.Other;
        this.priority = Priority.NoPriority;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Priority getPriority() {
        return priority;
    }

    @Override
    public Entity copy() {
        Task copy = new Task();
        copy.id = this.id;
        copy.title = this.title;
        copy.description = this.description;
        copy.creationDate = this.creationDate;
        copy.status = this.status;
        copy.priority = this.priority;
        copy.category = this.category;
        copy.dueDate = this.dueDate;
        copy.lastModificationDate = this.lastModificationDate;
        return copy;
    }

    @Override
    public int getEntityCode() {
        return TASK_ENTITY_CODE;
    }

    @Override
    public void setCreationDate(Date date) {
        this.creationDate = date;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setLastModificationDate(Date date) {
        this.lastModificationDate = date;
    }

    @Override
    public Date getLastModificationDate() {
        return lastModificationDate;
    }
}

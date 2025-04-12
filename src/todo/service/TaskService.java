package todo.service;

import db.Database;
import db.exception.EntityNotFoundException;
import todo.entity.*;
import todo.entity.Task.Status;

import java.util.Date;
import java.util.List;

public class TaskService {
    public static int createTask(String title, String description, Date dueDate) throws Exception {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setStatus(Status.NotStarted);
        Database.add(task);
        return task.id;
    }

    public static void removeTask(int taskId) throws EntityNotFoundException {
        List<Step> steps = Database.getStepsByTask(taskId);
        for (Step step : steps) {
            Database.delete(step.id);
        }
        Database.delete(taskId);
    }

    public static Task getTask(int taskId) throws EntityNotFoundException {
        return (Task) Database.get(taskId);
    }

    public static void updateTask(int taskId, String field, String value) throws Exception {
        Task task = getTask(taskId);

        switch (field.toLowerCase()) {
            case "title":
                task.setTitle(value);
                break;
            case "description":
                task.setDescription(value);
                break;
            case "duedate":
                break;
            case "status":
                Status newStatus = Status.valueOf(value);
                task.setStatus(newStatus);
                if (newStatus == Status.Completed) {
                    completeAllSteps(taskId);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid field: " + field);
        }
        Database.update(task);
    }

    private static void completeAllSteps(int taskId) throws EntityNotFoundException {
        List<Step> steps = Database.getStepsByTask(taskId);
        for (Step step : steps) {
            step.setStatus(Step.Status.Completed);
            Database.update(step);
        }
    }

    public static void printTaskDetails(Task task) {
        System.out.println("ID: " + task.id);
        System.out.println("Title: " + task.getTitle());
        System.out.println("Description: " + task.getDescription());
        System.out.println("Due Date: " + task.getDueDate());
        System.out.println("Status: " + task.getStatus());
    }

    public static void printTaskWithSteps(Task task) {
        printTaskDetails(task);

        List<Step> steps = Database.getStepsByTask(task.id);
        if (!steps.isEmpty()) {
            System.out.println("Steps:");
            for (Step step : steps) {
                System.out.println("    + " + step.getTitle() + ":");
                System.out.println("        ID: " + step.id);
                System.out.println("        Status: " + step.getStatus());
            }
        }
    }

    public static void printAllTasks() {
        List<Task> tasks = Database.getAllTasks();
        for (Task task : tasks) {
            printTaskWithSteps(task);
            System.out.println();
        }
    }

    public static void printIncompleteTasks() {
        List<Task> tasks = Database.getIncompleteTasks();
        for (Task task : tasks) {
            printTaskWithSteps(task);
            System.out.println();
        }
    }
}
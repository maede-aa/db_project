package todo.service;

import db.Database;
import db.exception.EntityNotFoundException;
import todo.entity.*;
import todo.entity.Task.Status;

import java.text.SimpleDateFormat;
import java.util.Arrays;
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
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date dueDate = dateFormat.parse(value);
                task.setDueDate(dueDate);
                break;
            case "status":
                Status newStatus = Status.valueOf(value);
                task.setStatus(newStatus);
                if (newStatus == Status.Completed) {
                    completeAllSteps(taskId);
                }
                break;
            case "category":
                try {
                    Task.Category newCategory = Task.Category.valueOf(value);
                    task.setCategory(newCategory);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid category.");
                }
                break;
            case "priority":
                try {
                    Task.Priority newPriority = Task.Priority.valueOf(value);
                    task.setPriority(newPriority);
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid priority.");
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

    public static void printTasksByCategory(Task.Category category) {
        List<Task> allTasks = Database.getAllTasks();
        System.out.println("\n===== Tasks in Category: " + category + " =====");

        boolean found = false;
        for (Task task : allTasks) {
            if (task.getCategory() == category) {
                printTaskDetails(task);
                System.out.println("------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No tasks found in this category.");
        }
    }
}
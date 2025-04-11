package todo.service;

import db.Database;
import db.exception.EntityNotFoundException;
import todo.entity.*;
import java.util.*;

public class TaskService {
    private static final Map<Integer, String> tasks = new HashMap<>();
    private static int idCounter = 1;

    public static int createTask(String title, String description, Date dueDate, Task.Category category, Task.Priority priority) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setCategory(category);
        task.setPriority(priority);
        Database.add(task);
        return task.id;
    }

    public static void removeTask(int taskId) {
        List<Step> steps = Database.getStepsByTask(taskId);
        for (Step step : steps) {
            Database.delete(step.id);
        }
        Database.delete(taskId);
    }

    public static Task getTask(int taskId) {
        try {
            return (Task) Database.get(taskId);
        } catch (Exception e) {
            throw new EntityNotFoundException("Task not found with ID: " + taskId);
        }
    }

    public static List<Task> getAllTasks() {
        return Database.getAllTasks();
    }

    public static void changeTaskTitle(int taskId, String newTitle) {
        Task task = getTask(taskId);
        task.setTitle(newTitle);
        Database.update(task);
    }

    public static void changeTaskStatus(int taskId, Task.Status newStatus) {
        Task task = getTask(taskId);
        task.setStatus(newStatus);
        Database.update(task);
    }

    public static void changeTaskPriority(int taskId, Task.Priority newPriority) {
        Task task = getTask(taskId);
        task.setPriority(newPriority);
        Database.update(task);
    }



    public static void showTaskDetails(int taskId) {
        Task task = getTask(taskId);
        System.out.println("Task Details:");
        System.out.println("ID: " + task.id);
        System.out.println("Title: " + task.getTitle());
        System.out.println("Description: " + task.getDescription());
        System.out.println("Due Date: " + (task.getDueDate()));
        System.out.println("Status: " + task.getStatus());
        System.out.println("Priority: " + task.getPriority());
        System.out.println("Category: " + task.getCategory());
    }

public static void addTask(String title, String description, String dueDate) {
    TaskService.tasks.put(idCounter++, title + " | " + description + " | " + dueDate);
    System.out.println("Task added successfully.");
}

public static boolean taskExists(int id) {
    return TaskService.tasks.containsKey(id);
}

public static void deleteTask(int id) {
    TaskService.tasks.remove(id);
}

public static void updateTask(int id, String field, String value) {
    if (!TaskService.tasks.containsKey(id)) {
        System.out.println("Task not found.");
        return;
    }

    String[] parts = TaskService.tasks.get(id).split(" \\| ");
    switch (field.toLowerCase()) {
        case "title":
            parts[0] = value;
            break;
        case "description":
            parts[1] = value;
            break;
        case "duedate":
            parts[2] = value;
            break;
        default:
            System.out.println("Invalid field.");
            return;
    }

    TaskService.tasks.put(id, String.join(" | ", parts));
    System.out.println("Task updated.");
}

public static void listAllTasks() {
    for (Map.Entry<Integer, String> entry : TaskService.tasks.entrySet()) {
        System.out.println("ID: " + entry.getKey() + " - " + entry.getValue());
    }
}

public static void showTaskDetails(int id) {
    if (!TaskService.tasks.containsKey(id)) {
        System.out.println("Task not found.");
        return;
    }

    System.out.println("Task Details:");
    System.out.println("ID: " + id + " - " + TaskService.tasks.get(id));
    }
    }
package todo;

import todo.service.*;
import db.exception.EntityNotFoundException;
import todo.entity.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class CommandProcessor {
    private final Scanner scn;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public CommandProcessor(Scanner scn) {
        this.scn = scn;
    }

    public void start() {
        System.out.println("=== Task Management System ===");
        printHelp();

        while (true) {
            System.out.print("\n> ");
            String input = scn.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting program...");
                break;
            }

            try {
                processCommand(input);
            } catch (Exception e) {
                System.out.println("Error : " + e.getMessage());
            }
        }
    }

    private void processCommand(String input) throws Exception {
        String[] parts = input.split(" ");
        String mainCommand = parts[0].toLowerCase();

        switch (mainCommand) {
            case "add":
                handleAddCommand(parts);
                break;
            case "delete":
                handleDeleteCommand();
                break;
            case "update":
                handleUpdateCommand(parts);
                break;
            case "get":
                handleGetCommand(parts);
                break;
            case "help":
                printHelp();
                break;
            default:
                System.out.println("Invalid command. Type 'help' for available commands.");
        }
    }

    private void handleAddCommand(String[] parts) throws Exception {
        if (parts.length < 2) {
            throw new Exception("Missing object type (task / step)");
        }

        String objectType = parts[1].toLowerCase();

        if (objectType.equals("task")) {
            System.out.print("Title: ");
            String title = scn.nextLine();

            System.out.print("Description: ");
            String description = scn.nextLine();

            System.out.print("Due date :");
            Date dueDate = parseDate(scn.nextLine());

            int taskId = TaskService.createTask(title, description, dueDate);
            System.out.println("Task saved successfully.");
            System.out.println("ID : " + taskId);
        }
        else if (objectType.equals("step")) {
            System.out.print("TaskID : ");
            int taskId = Integer.parseInt(scn.nextLine());

            System.out.print("Title : ");
            String title = scn.nextLine();

            StepService.addStep(taskId, title);
        }
        else {
            throw new Exception("Invalid object type. Use 'task' or 'step'.");
        }
    }

    private void handleDeleteCommand() throws Exception {
        System.out.print("ID: ");
        int id = Integer.parseInt(scn.nextLine());

        try {
            TaskService.removeTask(id);
        } catch (EntityNotFoundException e) {
            StepService.deleteStep(id);
        }
    }

    private void handleUpdateCommand(String[] parts) throws Exception {
        if (parts.length < 2) {
            throw new Exception("Missing object type (task / step)");
        }

        String objectType = parts[1].toLowerCase();

        System.out.print("ID: ");
        int id = Integer.parseInt(scn.nextLine());

        System.out.print("Field: ");
        String field = scn.nextLine();

        System.out.print("New Value: ");
        String value = scn.nextLine();

        if (objectType.equals("task")) {
            TaskService.updateTask(id, field, value);
        }
        else if (objectType.equals("step")) {
            StepService.updateStep(id, field, value);
        }
        else {
            throw new Exception("Invalid object type. Use 'task' or 'step'");
        }
    }

    private void handleGetCommand(String[] parts) throws Exception {
        if (parts.length < 2) {
            throw new Exception("Missing query type (all-tasks / incomplete-tasks / task-by-id)");
        }

        String queryType = parts[1].toLowerCase();

        if (queryType.equals("all-tasks")) {
            TaskService.printAllTasks();
        }
        else if (queryType.equals("incomplete-tasks")) {
            TaskService.printIncompleteTasks();
        }
        else if (queryType.equals("task-by-id")) {
            System.out.print("ID: ");
            int id = Integer.parseInt(scn.nextLine());
            Task task = TaskService.getTask(id);
            TaskService.printTaskWithSteps(task);
        }
        else {
            throw new Exception("Invalid query type");
        }
    }

    private Date parseDate(String dateStr) throws ParseException {
        return dateFormat.parse(dateStr);
    }

    private void printHelp() {
        System.out.println("\nAvailable Commands:");
        System.out.println("  add task       - Create a new task");
        System.out.println("  add step       - Create a new step");
        System.out.println("  delete         - Delete an item by ID");
        System.out.println("  update task    - Update a task");
        System.out.println("  update step    - Update a step");
        System.out.println("  get all-tasks      - List all tasks");
        System.out.println("  get incomplete-tasks - List incomplete tasks");
        System.out.println("  get task-by-id     - Show task details");
        System.out.println("  help           - Show this help");
        System.out.println("  exit           - Exit the program");
    }
}
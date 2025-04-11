package todo;

import todo.service.StepService;
import todo.service.TaskService;

import java.util.Scanner;

public class CommandProcessor {
    private final Scanner scanner;

    public CommandProcessor(Scanner scanner) {
        this.scanner = scanner;
    }

    public void start() {
        System.out.println("=== Task Management System ===");
        printHelp();

        while (true) {
            System.out.print("\n> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Exiting program...");
                break;
            }

            processCommand(input);
        }
    }

    private void processCommand(String input) {
        try {
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
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleAddCommand(String[] parts) throws Exception {
        if (parts.length < 2) {
            throw new Exception("Missing object type (task/step)");
        }

        String objectType = parts[1].toLowerCase();

        if (objectType.equals("task")) {
            System.out.print("Task title: ");
            String title = scanner.nextLine();

            System.out.print("Description: ");
            String description = scanner.nextLine();

            System.out.print("Due date (yyyy-MM-dd): ");
            String dueDate = scanner.nextLine();

            TaskService.addTask(title, description, dueDate);
        }
        else if (objectType.equals("step")) {
            System.out.print("For Task ID: ");
            int taskId = Integer.parseInt(scanner.nextLine());

            System.out.print("Step title: ");
            String title = scanner.nextLine();

            StepService.addStep(taskId, title);
        }
        else {
            throw new Exception("Invalid object type. Use 'task' or 'step'");
        }
    }

    private void handleDeleteCommand() throws Exception {
        System.out.print("Enter ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());

        if (TaskService.taskExists(id)) {
            TaskService.deleteTask(id);
            System.out.println("Task deleted successfully.");
        }
        else if (StepService.stepExists(id)) {
            StepService.deleteStep(id);
            System.out.println("Step deleted successfully.");
        }
        else {
            throw new Exception("No entity found with ID: " + id);
        }
    }

    private void handleUpdateCommand(String[] parts) throws Exception {
        if (parts.length < 2) {
            throw new Exception("Missing object type (task/step)");
        }

        String objectType = parts[1].toLowerCase();

        System.out.print("Enter ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        System.out.print("Field to update: ");
        String field = scanner.nextLine();

        System.out.print("New value: ");
        String value = scanner.nextLine();

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
            throw new Exception("Missing query type (tasks/steps/task)");
        }

        String queryType = parts[1].toLowerCase();

        if (queryType.equals("tasks")) {
            TaskService.listAllTasks();
        }
        else if (queryType.equals("steps")) {
            StepService.listAllSteps();
        }
        else if (queryType.equals("task")) {
            System.out.print("Enter Task ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            TaskService.showTaskDetails(id);
        }
        else {
            throw new Exception("Invalid query type. Use 'tasks', 'steps' or 'task'");
        }
    }

    private void printHelp() {
        System.out.println("\nAvailable Commands:");
        System.out.println("  add task       - Create a new task");
        System.out.println("  add step       - Create a new step");
        System.out.println("  delete         - Delete an item by ID");
        System.out.println("  update task    - Update a task");
        System.out.println("  update step    - Update a step");
        System.out.println("  get tasks      - List all tasks");
        System.out.println("  get steps      - List all steps");
        System.out.println("  get task       - Show task details");
        System.out.println("  help           - Show this help");
        System.out.println("  exit           - Exit the program");
    }
}
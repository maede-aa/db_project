package todo.service;

import java.util.HashMap;
import java.util.Map;

public class StepService {
    private static final Map<Integer, String> steps = new HashMap<>();
    private static int idCounter = 1;

    public static void addStep(int taskId, String title) {
        steps.put(idCounter++, "TaskID: " + taskId + " | " + title);
        System.out.println("Step added successfully.");
    }

    public static boolean stepExists(int id) {
        return steps.containsKey(id);
    }

    public static void deleteStep(int id) {
        steps.remove(id);
    }

    public static void updateStep(int id, String field, String value) {
        if (!steps.containsKey(id)) {
            System.out.println("Step not found.");
            return;
        }

        String old = steps.get(id);
        String[] parts = old.split(" \\| ");
        switch (field.toLowerCase()) {
            case "title":
                parts[1] = " " + value;
                break;
            default:
                System.out.println("Invalid field.");
                return;
        }

        steps.put(id, String.join(" |", parts));
        System.out.println("Step updated.");
    }

    public static void listAllSteps() {
        for (Map.Entry<Integer, String> entry : steps.entrySet()) {
            System.out.println("ID: " + entry.getKey() + " - " + entry.getValue());
        }
    }

    public static void addStep(int taskId, String title) {
        steps.put(idCounter++, "TaskID: " + taskId + " | " + title);
        System.out.println("Step added successfully.");
    }

    public static boolean stepExists(int id) {
        return steps.containsKey(id);
    }

    ublic static void deleteStep(int id) {
        steps.remove(id);
    }

    public static void updateStep(int id, String field, String value) {
        if (!steps.containsKey(id)) {
            System.out.println("Step not found.");
            return;
        }
        String old = steps.get(id);
        String[] parts = old.split(" \\| ");
        switch (field.toLowerCase()) {
            case "title":
                parts[1] = " " + value;
                break;
            default:
                System.out.println("Invalid field.");
                return;
        }
        steps.put(id, String.join(" |", parts));
        System.out.println("Step updated.");
    }

    public static void listAllSteps() {
        for (Map.Entry<Integer, String> entry : steps.entrySet()) {
            System.out.println("ID: " + entry.getKey() + " - " + entry.getValue());
        }
    }
}

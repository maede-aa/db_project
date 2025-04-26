package todo.service;

import db.Database;
import db.exception.EntityNotFoundException;
import todo.entity.*;
import java.util.List;

public class StepService {

    public static void addStep(int taskId, String title) throws EntityNotFoundException {

        Task task = (Task) Database.get(taskId);
        Step step = new Step();
        step.setTitle(title);
        step.setTaskRef(taskId);
        Database.add(step);

        System.out.println("Step saved successfully.");
        System.out.println("ID : " + step.id);
        System.out.println("Creation Date : " + step.getCreationDate());
    }

    public static void deleteStep(int id) throws EntityNotFoundException {
        Database.delete(id);
        System.out.println("Entity with ID = " + id + " successfully deleted.");
    }

    public static void updateStep(int id, String field, String value) throws Exception {
        Step step = (Step) Database.get(id);
        String oldValue = "";

        switch (field.toLowerCase()) {
            case "title":
                oldValue = step.getTitle();
                step.setTitle(value);
                Database.update(step);
                break;
            case "status":
                oldValue = step.getStatus().toString();
                Step.Status newStatus = Step.Status.valueOf(value);
                step.setStatus(newStatus);
                Database.update(step);
                updateParentTaskStatus(step.getTaskRef());
                break;
            default:
                throw new IllegalArgumentException("Invalid field: " + field);
        }

        System.out.println("Successfully updated the step.");
        System.out.println("Field: " + field);
        System.out.println("Old Value: " + oldValue);
        System.out.println("New Value: " + value);
        System.out.println("Modification Date: " + step.getLastModificationDate());
    }

    private static void updateParentTaskStatus(int taskId) throws EntityNotFoundException {
        Task task = (Task) Database.get(taskId);
        List<Step> steps = Database.getStepsByTask(taskId);

        boolean allCompleted = true;
        boolean anyCompleted = false;

        for (Step step : steps) {
            if (step.getStatus() != Step.Status.Completed) {
                allCompleted = false;
            } else {
                anyCompleted = true;
            }
        }

        if (allCompleted) {
            task.setStatus(Task.Status.Completed);
        } else if (anyCompleted && task.getStatus() == Task.Status.NotStarted) {
            task.setStatus(Task.Status.InProgress);
        }

        Database.update(task);
    }
}
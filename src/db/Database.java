package db;

import db.exception.*;
import todo.entity.*;

import java.util.*;

public final class Database {
    private static final ArrayList<Entity> entities = new ArrayList<Entity>();
    private static HashMap<Integer, Validator> validators = new HashMap<Integer, Validator>();

    private Database() {}

    public static void add(Entity e) {
        if (e instanceof Trackable) {
            Trackable trackable = (Trackable) e;
            Date now = new Date();
            trackable.setCreationDate(now);
            trackable.setLastModificationDate(now);
        }
        validateEntityIfPossible(e);
        Entity copy = e.copy();
        copy.id = entities.isEmpty() ? 1 : getMaxId() + 1;
        entities.add(copy);
        e.id = copy.id ;
    }

    private static int getMaxId() {
        int max = 0;
        for(Entity entity : entities)
            if(entity.id > max)
                max = entity.id;
        return max;
    }

    public static Entity get(int id) {
        Entity found = findById(id);
        if (found == null) {
            throw new EntityNotFoundException(id);
        }
        return found.copy();
    }

    private static Entity findById(int id) {
        for (Entity entity : entities)
            if (entity.id == id)
                return entity;
        return null;
    }

    public static void delete(int id) {
        Entity e = findById(id);
        if (e == null)
            throw new EntityNotFoundException(id);
        entities.remove(e);
    }

    public static void update(Entity e) {
        if (e instanceof Trackable) {
            Trackable trackable = (Trackable) e;
            trackable.setLastModificationDate(new Date());
        }

        validateEntityIfPossible(e);
        Entity copy = e.copy();

        for (int i = 0 ; i < entities.size() ; ++i) {
            if (entities.get(i).id == e.id) {
                entities.set(i ,copy);
                return;
            }
        }
        throw new EntityNotFoundException(e.id);
    }

    public static void registerValidator(int entityCode, Validator validator) {
        if(validators.containsKey(entityCode))
            throw new IllegalArgumentException("Validator already exists for entity code: " + entityCode);
        validators.put(entityCode ,validator);
    }

    private static void validateEntityIfPossible(Entity e) {
        Validator validator = validators.get(e.getEntityCode());
        if (validator != null) {
            try {
                validator.validate(e);
            } catch (InvalidEntityException e1) {
                throw new IllegalArgumentException("Invalid entity: " + e1.getMessage());
            }
        }
    }

    public static List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getEntityCode() == Task.TASK_ENTITY_CODE) {
                tasks.add((Task) entity.copy());
            }
        }
        return tasks;
    }

    public static List<Task> getIncompleteTasks() {
        List<Task> tasks = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getEntityCode() == Task.TASK_ENTITY_CODE) {
                Task task = (Task) entity;
                if (task.getStatus() != Task.Status.Completed) {
                    tasks.add((Task) task.copy());
                }
            }
        }
        return tasks;
    }

    public static List<Step> getStepsByTask(int taskId) {
        List<Step> steps = new ArrayList<>();
        for (Entity entity : entities) {
            if (entity.getEntityCode() == Step.STEP_ENTITY_CODE) {
                Step step = (Step) entity;
                if (step.getTaskRef() == taskId) {
                    steps.add((Step) step.copy());
                }
            }
        }
        return steps;
    }
}
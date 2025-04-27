package todo.serializer;

import db.Entity;
import db.Serializer;
import todo.entity.Task;

import java.text.SimpleDateFormat;

public class TaskSerializer implements Serializer {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public String serialize(Entity e) {
        Task task = (Task) e;
        return task.id + "," + task.getTitle() + "," + task.getDescription();
    }

    @Override
    public Entity deserialize(String s) {
        String[] parts = s.split(",");
        Task task = new Task();
        task.id = Integer.parseInt(parts[0]);
        task.setTitle(parts[1]);
        task.setDescription(parts[2]);
        return task;
    }
}

package todo.serializer;

import db.Entity;
import db.Serializer;
import todo.entity.Step;

public class StepSerializer implements Serializer {
    @Override
    public String serialize(Entity e) {
        Step step = (Step) e;
        return step.id + "," + step.getTaskRef() + "," + step.getTitle() + "," + step.getStatus();
    }

    @Override
    public Entity deserialize(String s) {
        String[] parts = s.split(",");
        Step step = new Step();
        step.id = Integer.parseInt(parts[0]);
        step.setTaskRef(Integer.parseInt(parts[1]));
        step.setTitle(parts[2]);
        step.setStatus(Step.Status.valueOf(parts[3]));
        return step;
    }
}

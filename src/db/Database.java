package db;

import db.exception.EntityNotFoundException;
import java.util.ArrayList;

public final class Database {
    private static final ArrayList<Entity> entities = new ArrayList<Entity>();

    private Database() {}

    public static void add(Entity e) {
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
        Entity copy = e.copy();
        for (int i = 0; i < entities.size(); ++i) {
            if (entities.get(i).id == e.id) {
                entities.set(i ,copy);
                return;
            }
        }
        throw new EntityNotFoundException(e.id);
    }
}

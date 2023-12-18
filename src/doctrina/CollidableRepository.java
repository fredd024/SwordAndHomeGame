package doctrina;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CollidableRepository implements Iterable<StaticEntity> {

    private static CollidableRepository instance;
    private final List<StaticEntity> registeredEntity;

    public static CollidableRepository getInstance() {
        if (instance == null){
            instance = new CollidableRepository();
        }
        return instance;
    }

    public void registerEntity(StaticEntity entity) {
        registeredEntity.add(entity);
    }

    public void unregisterEntity(StaticEntity entity){
        registeredEntity.remove(entity);
    }

    public void registerEntities(Collection<StaticEntity> entities) {
        registeredEntity.addAll(entities);
    }

    public void unregisterEntities(Collection<StaticEntity> entities) {
        registeredEntity.removeAll(entities);
    }

    public int count() {
        return registeredEntity.size();
    }

    public void clear() {
        registeredEntity.clear();
    }

    private CollidableRepository() {
        registeredEntity = new ArrayList<>();
    }

    @Override
    public Iterator<StaticEntity> iterator() {
        return registeredEntity.iterator();
    }
}

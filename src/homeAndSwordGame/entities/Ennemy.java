package homeAndSwordGame.entities;

import doctrina.Canvas;
import doctrina.MovableEntity;
import doctrina.StaticEntity;

public abstract class Ennemy extends MovableEntity implements LivingEntity {
    @Override
    public abstract void draw(Canvas canvas);

    @Override
    public abstract boolean isAlive() ;

    @Override
    public abstract void takeDamage(int damage);

    public abstract void changeTarget(StaticEntity newTarget);
}

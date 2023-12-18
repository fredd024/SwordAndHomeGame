package doctrina;

import java.awt.*;

public class Collision {

    private final MovableEntity entity;

    public Collision(MovableEntity entity) {
        this.entity = entity;
    }

    public int getAllowSpeed(Direction direction) {
        switch (direction) {
            case UP : return getAllowUpSpeed();
            case DOWN :  return getAllowDownSpeed();
            case LEFT : return getAllowLeftSpeed();
            case RIGHT : return getAllowRightSpeed();
        }
        return 0;
    }

    private int getAllowUpSpeed() {
        return distance( other -> entity.y - (other.y + other.height));
    }

    private int getAllowDownSpeed() {
        return distance(other -> other.y - (entity.y + entity.height));
    }

    private int getAllowLeftSpeed() {
        return distance(other -> entity.x - (other.x + other.width));
    }

    private int getAllowRightSpeed(){
        return distance(other -> other.x - (entity.x + entity.width));
    }

    private int distance(DistanceCalculator calculator) {
        Rectangle collisionBound = entity.getHitBoxe();
        int allowedDistance = entity.getSpeed();

        // attention c'est pas optimiser ce truc la
        for (StaticEntity other : CollidableRepository.getInstance()){
            if (collisionBound.intersects(other.getBounds())) {
                allowedDistance = Math.min(allowedDistance, calculator.calculatewitdh(other));
            }
        }
        return allowedDistance;
    }

    private interface DistanceCalculator {
        int calculatewitdh (StaticEntity other);
    }
}

package homeAndSwordGame.entities;

import doctrina.Canvas;
import doctrina.*;

import java.awt.*;

public class Dog extends MovableEntity {
    private int speed = 175;
    private AnimationDirectional animation ;
    private Rectangle interactionZone;
    private Player player;
    private StaticEntity target;

    public Dog(Player player) {
        super();
        setDiemsion(32,32);
        setSpeed(0);

        interactionZone = new Rectangle(width,height);

        animation = new AnimationDirectional("images/Animal/whiteDog.png",this,3);
        animation.idle(Direction.DOWN);
        this.player = player;
        this.target = null;
    }

    public void update(){
        setSpeed((int) (speed * GameTime.getDeltaFrame()));
        interactionZone.setLocation((int) (x - interactionZone.getWidth()/2 + this.getWidth()/2 ), (int) (y - interactionZone.getHeight() /2 + this.getHeight()/2));
        super.update();

        if (target == null){
            return;
        }
        
        if (target instanceof LivingEntity){
            if (!((LivingEntity) target).isAlive()){
                target = player;
            }
        }

        int targetXdistance = Math.abs((x - target.getX()));
        int targetYdistance = Math.abs((y - target.getY()));

        if ((targetXdistance + targetYdistance) > 60 || !( target instanceof Player)) {
            if (targetXdistance >= targetYdistance) {
                if (x - target.getX() < 0) {
                    moveRight();
                    if (!hasMoved()) {
                        if (y - target.getY() < 0) {
                            moveDown();
                        } else {
                            moveUp();
                        }
                    }
                } else {
                    moveLeft();
                    if (!hasMoved()) {
                        if (y - target.getY() < 0) {
                            moveDown();
                        } else {
                            moveUp();
                        }
                    }
                }
            } else {
                if (y - target.getY() < 0) {
                    moveDown();
                    if (!hasMoved()) {
                        if (x - target.getX() < 0) {
                            moveRight();
                        } else {
                            moveLeft();
                        }
                    }
                } else {
                    moveUp();
                    if (!hasMoved()) {
                        if (x - target.getX() < 0) {
                            moveRight();
                        } else {
                            moveLeft();
                        }
                    }
                }
            }
        }


        if (hasMoved()){
            animation.nextFrame(this.getDirection());
        } else {
            animation.idle(this.getDirection());
        }
    }
    @Override
    public void draw(Canvas canvas) {
        canvas.drawImage(animation.getImage(), x,y);

        if (GameConfig.isDebugEnabled()){
            drawHitBox(canvas);
            canvas.drawRectangle(interactionZone,Color.blue);
        }
        if (actionTriggerRange(player)){
            canvas.drawString("(e) interact" , x - 20 , y , Color.WHITE);
        }
    }

    public void setTarget(StaticEntity entity) {
        target = entity;
    }

    public int distanceWith(StaticEntity entity){
        int entityXdistance = Math.abs((x - entity.getX()));
        int entityYdistance = Math.abs((y -entity.getY()));

        return entityXdistance + entityYdistance;
    }

    public boolean actionTriggerRange(Player player) {
        return player.intersectwith(interactionZone);
    }

    public void action(){
        if (target == null){
            target = player;
        } else {
            target = null;
        }
    }
}

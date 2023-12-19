package homeAndSwordGame.entities;

import doctrina.Canvas;
import doctrina.*;
import homeAndSwordGame.SoundEffect;

import java.awt.*;

public class Dog extends MovableEntity implements Pet {
    private int speed = 175;
    private AnimationDirectional animation ;
    private Rectangle interactionZone;
    private Player player;
    private boolean isFollowing;
    private float healCooldown = 0;
    private float actionCooldwn;
    private Direction actionDirection;

    public Dog(Player player) {
        super();
        setDiemsion(32,32);
        setSpeed(0);

        interactionZone = new Rectangle(width,height);

        animation = new AnimationDirectional("images/Animal/whiteDog.png",this,3);
        animation.idle(Direction.DOWN);
        this.player = player;
    }

    public void update(){
        setSpeed((int) (speed * GameTime.getDeltaFrameSecond()));
        interactionZone.setLocation((int) (x - interactionZone.getWidth()/2 + this.getWidth()/2 ), (int) (y - interactionZone.getHeight() /2 + this.getHeight()/2));
        super.update();


        int targetXdistance = Math.abs((x - player.getX()));
        int targetYdistance = Math.abs((y - player.getY()));

        if ((targetXdistance + targetYdistance) > 200 && isFollowing ) {
            if (targetXdistance >= targetYdistance) {
                if (x - player.getX() < 0) {
                    moveRight();
                    if (!hasMoved()) {
                        if (y - player.getY() < 0) {
                            moveDown();
                        } else {
                            moveUp();
                        }
                    }
                } else {
                    moveLeft();
                    if (!hasMoved()) {
                        if (y - player.getY() < 0) {
                            moveDown();
                        } else {
                            moveUp();
                        }
                    }
                }
            } else {
                if (y - player.getY() < 0) {
                    moveDown();
                    if (!hasMoved()) {
                        if (x - player.getX() < 0) {
                            moveRight();
                        } else {
                            moveLeft();
                        }
                    }
                } else {
                    moveUp();
                    if (!hasMoved()) {
                        if (x - player.getX() < 0) {
                            moveRight();
                        } else {
                            moveLeft();
                        }
                    }
                }
            }
        } else if (isFollowing) {
            if (healCooldown > 0){
                healCooldown -= GameTime.getDeltaFrameSecond();
                randomMove();
            }else {
                heal();
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
    }

    public boolean actionTriggerRange(Player player) {
        return player.intersectwith(interactionZone);
    }

    @Override
    public boolean isFollowing() {
        return isFollowing;
    }

    @Override
    public void follow() {
        SoundEffect.SQUICK_SQUICK.play();
        healCooldown = 5f;
        isFollowing = true;
    }

    @Override
    public void unfollow() {
        isFollowing = false;
    }

    private void heal(){
        SoundEffect.MURLOC.play();
        player.heal(20);
        healCooldown =  (float) (Math.random() * 55) + 5;
    }

    private void randomMove(){
        if (actionCooldwn < 0) {
            actionCooldwn = 0.5f;
            int rnd = (int) (Math.random() * 10);
            System.out.println(rnd);

            if (rnd >= 5){
                actionDirection = null;
            }
            if (rnd == 6){
                actionDirection = Direction.UP;
            }
            if (rnd == 7){
                actionDirection = Direction.DOWN;
            }
            if (rnd == 8){
                actionDirection = Direction.RIGHT;
            }
            if (rnd == 9){
                actionDirection = Direction.LEFT;
            }

        }
        if (actionDirection != null) {
            move(actionDirection);
            int targetXdistance = Math.abs((x - player.getX()));
            int targetYdistance = Math.abs((y - player.getY()));
            if ((targetXdistance + targetYdistance) > 200 ){
                actionDirection = null;
            }
        }
        actionCooldwn -= GameTime.getDeltaFrameSecond();
    }

}

package homeAndSwordGame.entities;

import doctrina.Canvas;
import doctrina.*;
import homeAndSwordGame.SoundEffect;

import java.awt.*;

public class Skeleton extends MovableEntity  implements LivingEntity {

    private int speed = 150;
    private int maxHealthPoint = 20;
    private int healtPoint;
    private AnimationDirectional animation ;
    private AnimationSimple deathAnimation;
    private StaticEntity target;
    private boolean attackAction = false;
    private Point attackPosition;
    private Rectangle attackZone;
    private AnimationSimple attackAnimation;
    private Player player;

    public Skeleton(Player player) {
        super();
        setDiemsion(32,32);
        setSpeed(0);
        healtPoint = maxHealthPoint;
        attackPosition= new Point();
        animation = new AnimationDirectional("images/Ennemies/Skeleton/main.png",this,3);
        animation.idle(Direction.DOWN);
        deathAnimation = new AnimationSimple("images/Ennemies/Skeleton/death.png",this,4);
        attackAnimation = new AnimationSimple("images/longAttack.png",32,32,8);
        this.target = player;
        this.player = player;
    }

    public void update(){
        if (!isAlive()){
            if(!deathAnimation.isAnimationEnd()) {
                deathAnimation.nextFrame();
                attackAction = false;
            }
            return;
        }

        setSpeed((int) (speed * GameTime.getDeltaFrame()));
        super.update();

        if (target == null){
            moveRight();
            animation.nextFrame(this.getDirection());
            int distanceX = Math.abs((x - player.getX()));
            int distanceY = Math.abs((y - player.getY()));
            if (distanceX < 50 || distanceY < 50){
                target = player;
            }
            return;
        }

        int distanceX = Math.abs((x - target.getX()));
        int distanceY = Math.abs((y - target.getY()));

        if (distanceX > 300 || distanceY > 300){
            return;
        }

        if (attackAction){
            if (attackAnimation.isAnimationEnd()){
                attackAction = false;
                if (target.intersectwith(attackZone) && target instanceof LivingEntity){
                    ((LivingEntity)target).takeDamage(10);
                }
            }
            attackAnimation.nextFrame();
            return;
        }

        if (distanceY < 32 && distanceX < 32){
            attack();
        }

        if ( distanceX >= distanceY || distanceX < 32){
            if (y - target.getY() < 0){
                moveDown();
                if (!hasMoved()){
                    if (x - target.getX() < 0){
                        moveRight();
                    }else {
                        moveLeft();
                    }
                }
            }else {
                moveUp();
                if (!hasMoved()){
                    if (x - target.getX() < 0){
                        moveRight();
                    }else {
                        moveLeft();
                    }
                }
            }
        }
        if (!hasMoved() && distanceY >= distanceX || distanceY < 32){
            if (x - target.getX() < 0){
                moveRight();
                if (!hasMoved()){
                    if (y - target.getY() < 0){
                        moveDown();
                    }else {
                        moveUp();
                    }
                }
            }else {
                moveLeft();
                if (!hasMoved()){
                    if (y - target.getY() < 0){
                        moveDown();
                    }else {
                        moveUp();
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
        if (healtPoint > 0) {
            canvas.drawImage(animation.getImage(), x,y);
            canvas.drawRectangle(x, y - 20, width, 2, Color.RED);
            canvas.drawRectangle(x, y - 20, ((int) ((width * 1.0 / maxHealthPoint) * healtPoint)), 2, Color.green);
        } else {
            canvas.drawImage(deathAnimation.getImage(), x,y);
        }

        if (attackAction){
            canvas.drawImage(attackAnimation.getImage(), attackPosition.x, attackPosition.y);
        }

        if (GameConfig.isDebugEnabled()){
            drawHitBox(canvas);
        }
    }

    @Override
    public boolean isAlive() {
        return healtPoint > 0;
    }

    @Override
    public void takeDamage(int damage) {
        if (isAlive()) {
            healtPoint -= damage;
            SoundEffect.ENEMY_HURT.play();
            if (!isAlive()){
                SoundEffect.SKELETON_DEATH.play();
            }
        }
    }

    public void attack(){
        SoundEffect.BASIC_ATTACK.play();

        attackAnimation.resetAnimation();

        attackPosition.setLocation(target.getX(),target.getY());
        attackZone = new Rectangle(target.getX(),target.getY(),32,32);

        attackAction = true;
    }

    public void changeTarget(StaticEntity newTarget) {
        target = newTarget;
    }
}

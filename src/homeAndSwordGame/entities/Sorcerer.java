package homeAndSwordGame.entities;

import doctrina.*;
import doctrina.Canvas;
import homeAndSwordGame.SoundEffect;

import java.awt.*;

public class Sorcerer extends Ennemy {

    private int speed = 100;
    private int maxHealthPoint = 10;
    private int healtPoint;
    private AnimationDirectional animation ;
    private AnimationSimple deathAnimation;
    private StaticEntity target;
    private boolean attackAction = false;
    private Point attackPosition;
    private Direction attackDirection;
    private Rectangle attackZone;
    private AnimationDirectional attackAnimation;
    private Player player;
    private float cooldown = 0;
    private int animationBooster = 3;

    public Sorcerer(Player player) {
        super();
        setDiemsion(32,32);
        setSpeed(0);
        healtPoint = maxHealthPoint;
        attackPosition= new Point();
        animation = new AnimationDirectional("images/Ennemies/sorcerer/main.png",this,3);
        animation.idle(Direction.DOWN);
        deathAnimation = new AnimationSimple("images/Ennemies/sorcerer/death.png",this,10);
        attackAnimation = new AnimationDirectional("images/longAttack64x64Purple.png",64,64,6);
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

        setSpeed((int) (speed * GameTime.getDeltaFrameSecond()));
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
            if (attackAnimation.isAnimationEnd() && animationBooster <= 0) {
                attackAction = false;
                animationBooster = 3;
            }else if (attackAnimation.isAnimationEnd()){
                animationBooster--;
            }
            if (cooldown <= 0  && target.intersectwith(attackZone) && target instanceof LivingEntity){
                ((LivingEntity)target).takeDamage(1);
                cooldown = 0.1f;
            }

            cooldown -= GameTime.getDeltaFrameSecond();
            attackAnimation.nextFrame(attackDirection);
            return;
        }

        if (distanceY < 32 && distanceX < 32){
            boolean vertical = Math.abs(distanceX) < Math.abs(distanceY);
            if (vertical && target.getY() - this.getY() < 0){
                attack(Direction.UP);
            }
            if (vertical && target.getY() - this.getY() > 0){
                attack(Direction.DOWN);
            }
            boolean horizontal = Math.abs(distanceY) < Math.abs(distanceX);
            if (horizontal && target.getX() - this.getX() > 0){
                attack(Direction.LEFT);
            }
            if (horizontal && target.getX() - this.getX() < 0){
                attack(Direction.RIGHT);
            }
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
            if (GameConfig.isDebugEnabled()) {
                canvas.drawRectangle(attackZone, Color.red);
            }
        }

        if (GameConfig.isDebugEnabled() && isAlive()){
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
                SoundEffect.SORCERERDEATH.play();
            }
        }
    }

    public void attack(Direction direction){
        SoundEffect.LAZER.play();

        attackDirection = direction;

        attackAnimation.resetAnimation();

        int positionX = this.x + this.width/2 - 32;
        int positionY = this.y + this.width/2 - 32;

        if (direction == Direction.LEFT) {
            attackZone = new Rectangle(positionX +32 , positionY +28, 64, 8);
            attackPosition.setLocation(positionX +32,positionY);
        } else  if (direction == Direction.RIGHT) {
            attackDirection = Direction.UP;
            attackZone = new Rectangle(positionX -32, positionY +28, 64, 8);
            attackPosition.setLocation(positionX -32,positionY);
        }else  if (direction == Direction.UP) {
            attackDirection = Direction.RIGHT;
            attackZone = new Rectangle(positionX + 28, positionY - 32, 8, 64);
            attackPosition.setLocation(positionX ,positionY - 32);
        }else  if (direction == Direction.DOWN) {
            attackZone = new Rectangle(positionX + 28, positionY + 32, 8, 64);
            attackPosition.setLocation(positionX,positionY + 32);
        }

        attackAction = true;
    }

    public void changeTarget(StaticEntity newTarget) {
        target = newTarget;
    }
}

package homeAndSwordGame.entities;

import doctrina.Canvas;
import doctrina.*;
import homeAndSwordGame.SoundEffect;

import javax.imageio.ImageIO;
import java.awt.*;

public class Player extends ControllableEntity implements LivingEntity {

    private int speed = 250;
    private int maxHealtPoint = 100;
    private int healtPoint;
    private Image ressourceHud;
    private boolean attackAction  = false;
    private Direction attackDirection;
    private Point attackPosition;
    private Rectangle attackZone;
    private AnimationDirectional playerAnimation;
    private AnimationDirectional attackAnimation;

    public Player(MovementController controller) {
        super(controller);
        setDiemsion(32,32);
        setSpeed(0);
        playerAnimation = new AnimationDirectional("images/charachter/Male 01-1.png",this,3);
        attackAnimation = new AnimationDirectional("images/attack-0001.png",32,32,4);
        attackPosition =  new Point();
        healtPoint = maxHealtPoint;

        try {
            ressourceHud = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("images/HUD/ressourceHud.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(){


        setSpeed((int) (speed * GameTime.getDeltaFrame()));
        super.update();
        if (!attackAction) {
            moveWithController();
        }
        if (hasMoved()){
            playerAnimation.nextFrame(this.getDirection());
        } else {
            playerAnimation.idle(this.getDirection());
        }

        if (attackAction){
            if (attackAnimation.isAnimationEnd()){
                attackAction = false;
            }
            attackAnimation.nextFrame(attackDirection);
        }

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawImage(playerAnimation.getImage(),x,y);

        if (attackAction){
            canvas.drawImage(attackAnimation.getImage(),(int) attackPosition.getX(),(int) attackPosition.getY());
        }

        if (GameConfig.isDebugEnabled()){
            drawHitBox(canvas);
        }
    }

    public void attack(){
        SoundEffect.BASIC_ATTACK.play();

        attackAnimation.resetAnimation();
        attackDirection = this.getDirection();
        if (attackDirection == Direction.DOWN){
            attackPosition.setLocation(x,y + height);
            attackZone = new Rectangle(x,y + height,32,32);
        }
        if (attackDirection == Direction.UP){
            attackPosition.setLocation(x,y - height);
            attackZone = new Rectangle(x,y - height,32,32);
        }
        if (attackDirection == Direction.RIGHT){
            attackPosition.setLocation(x + width,y);
            attackZone = new Rectangle(x + width,y,32,32);
        }
        if (attackDirection == Direction.LEFT){
            attackPosition.setLocation(x - width,y);
            attackZone = new Rectangle(x - width,y,32,32);
        }

        attackAction = true;
    }

    public boolean canAttack(){
        return !attackAction;
    }

    public Rectangle getAttackZone() {
        return attackZone;
    }

    public void drawHud(Canvas canvas,Camera camera){
        canvas.drawRectangle(camera.getPositionX() + 64, camera.getPositionY() + 28 , healtPoint * 161 / 100, 24,Color.green);
        canvas.drawRectangle(camera.getPositionX() + 52, camera.getPositionY() + 108 , 173, 24,Color.blue);
        canvas.drawImage(ressourceHud, camera.getPositionX(), camera.getPositionY());
    }

    @Override
    public boolean isAlive() {
        return healtPoint > 0;
    }

    @Override
    public void takeDamage(int damage) {
        healtPoint -= damage;
    }
}

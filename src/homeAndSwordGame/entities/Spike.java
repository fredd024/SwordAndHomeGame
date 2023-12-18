package homeAndSwordGame.entities;

import doctrina.AnimationSimple;
import doctrina.Canvas;
import doctrina.GameTime;
import doctrina.StaticEntity;

import java.awt.*;

public class Spike extends StaticEntity {

    private final float COOLDOWN_TIME;
    private float cooldown;
    private boolean hasAttack= false;
    private Rectangle zone;
    private Player player;

    private AnimationSimple animation;

    public Spike(int x , int y, float cooldown, Player player){
        this.player = player;
        setDiemsion(16,16);
        teleport(x,y);
        COOLDOWN_TIME = cooldown;
        this.cooldown = cooldown;
        animation = new AnimationSimple("images/spike.png",this,9);
        animation.idle();
        zone = new Rectangle(x,y,width,height);
    }

    public void update(){
        cooldown -= GameTime.getDeltaFrame();
        if (cooldown <= 0){
            animation.nextFrame();
            if (animation.isAnimationEnd()){
                animation.nextFrame();
                cooldown = COOLDOWN_TIME;
            }
        }

        if (player.intersectwith(zone)){
            player.takeDamage(attack());
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawImage(animation.getImage(),x,y);
    }

    public Rectangle getBounds(){
        return zone;
    }

    public int attack(){
        if (cooldown <= 0 && !hasAttack){
            hasAttack = true;
            return 20;
        }
        if (cooldown > 0 ){
            hasAttack = false;
        }
        return 0;
    }
}

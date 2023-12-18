package homeAndSwordGame.entities.HUD;

import doctrina.AnimationSimple;
import doctrina.Canvas;
import doctrina.StaticEntity;

public class Hand extends StaticEntity {

    private AnimationSimple animation;

    public Hand (int x,int y){
        setDiemsion(32,32);
        animation = new AnimationSimple("images/HUD/selectionHand.png",this,5);
        teleport(x,y);
    }

    @Override
    public void teleport(int x, int y){
        super.teleport(x,y);
        animation.resetAnimation();
    }

    public void update(){
        animation.nextFrame();
    }

    public void draw(Canvas canvas){
        canvas.drawImage(animation.getImage(),x,y);
    }


}

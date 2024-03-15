package homeAndSwordGame.Wave;

import doctrina.*;
import doctrina.Canvas;
import homeAndSwordGame.entities.Ennemy;
import homeAndSwordGame.entities.LivingEntity;
import homeAndSwordGame.entities.Player;

import java.awt.*;
import java.util.ArrayList;

public abstract class Wave {


    private ArrayList<Ennemy> ennemies = new ArrayList<>();
    private float time = 60;

    protected abstract void generateEnnemies(Player player);
    public ArrayList<Ennemy> getEnnemies(){ return  ennemies;}

    public void load(ArrayList<Ennemy> ennemies){
        this.ennemies = ennemies;
        for (Ennemy ennemy: ennemies) {
            ImageDrawer.getInstance().addEntity(ennemy);
        }
    }

    public void update() {
        for (Ennemy ennemy: ennemies) {
            ennemy.update();
        }

        time -= GameTime.getDeltaFrameSecond();
    }

    public boolean isWaveEnded(){
        for (Ennemy ennemy: ennemies) {
            if (((LivingEntity) ennemy).isAlive()){
                return false;
            }
        }
        return true;
    }

    public boolean runOutOfTime() {
        return time <= 0f;
    }

    public void drawTime(Canvas canvas, Camera camera){
        Rectangle stringDimension = canvas.getStringDimension("temp pour tuer tout les ennemies");
        canvas.drawString("temp pour tuer tout les ennemies", camera.getPositionX() + (800 /2) - (stringDimension.width / 2 ),camera.getPositionY() + 20, Color.WHITE);

        stringDimension = canvas.getStringDimension(((int) time) + " S");
        canvas.drawString( ((int) time) + " S",camera.getPositionX() + (800 /2) ,camera.getPositionY() + 40, Color.WHITE);
    }
}

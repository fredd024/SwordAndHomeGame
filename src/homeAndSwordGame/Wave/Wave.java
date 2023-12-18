package homeAndSwordGame.Wave;

import doctrina.ImageDrawer;
import doctrina.MovableEntity;
import homeAndSwordGame.entities.Player;
import homeAndSwordGame.entities.Skeleton;

import java.util.ArrayList;

public abstract class Wave {

    protected abstract void generateEnnemies(Player player);

    private ArrayList<Skeleton> ennemies = new ArrayList<>();

    public ArrayList<Skeleton> getEnnemies(){ return  ennemies;}

    public void load(ArrayList<Skeleton> ennemies){
        this.ennemies = ennemies;
        for (Skeleton ennemy: ennemies) {
            ImageDrawer.getInstance().addEntity(ennemy);
        }
    }

    public void update() {
        for (MovableEntity ennemy: ennemies) {
            ennemy.update();
        }
    }

    public boolean isWaveEnd(){
        for (Skeleton ennemy: ennemies) {
            if (ennemy.isAlive()){
                return false;
            }
        }
        return true;
    }
}

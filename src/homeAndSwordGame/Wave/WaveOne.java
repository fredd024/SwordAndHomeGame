package homeAndSwordGame.Wave;

import homeAndSwordGame.entities.Player;
import homeAndSwordGame.entities.Skeleton;

import java.util.ArrayList;

public class WaveOne extends Wave{

    private ArrayList<Skeleton> ennemies = new ArrayList<>();

    public WaveOne(Player player){
        generateEnnemies(player);
        load(ennemies);
    }

    @Override
    protected void generateEnnemies(Player player) {
        Skeleton skeleton;
        for (int i = 0; i < 5; i++) {
            skeleton = new Skeleton(player);
            skeleton.teleport((int) (Math.random() * 72 + 10),(int) (Math.random() * 324 + 460));
            skeleton.changeTarget(null);
            ennemies.add(skeleton);
        }

    }
}

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
        skeleton = new Skeleton(player);
        skeleton.teleport(10,500);
        skeleton.changeTarget(null);
        ennemies.add(skeleton);

        skeleton = new Skeleton(player);
        skeleton.teleport(100,540);
        skeleton.changeTarget(null);
        ennemies.add(skeleton);

        skeleton = new Skeleton(player);
        skeleton.teleport(30,520);
        skeleton.changeTarget(null);
        ennemies.add(skeleton);

        skeleton = new Skeleton(player);
        skeleton.teleport(50,600);
        skeleton.changeTarget(null);
        ennemies.add(skeleton);

        skeleton = new Skeleton(player);
        skeleton.teleport(10,780);
        skeleton.changeTarget(null);
        ennemies.add(skeleton);

        skeleton = new Skeleton(player);
        skeleton.teleport(10,500);
        skeleton.changeTarget(null);
        ennemies.add(skeleton);
    }
}

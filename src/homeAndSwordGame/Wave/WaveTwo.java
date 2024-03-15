package homeAndSwordGame.Wave;

import doctrina.MovableEntity;
import homeAndSwordGame.entities.Ennemy;
import homeAndSwordGame.entities.Player;
import homeAndSwordGame.entities.Skeleton;
import homeAndSwordGame.entities.Sorcerer;

import java.util.ArrayList;

public class WaveTwo extends Wave{

    private ArrayList<Ennemy> ennemies = new ArrayList<>();

    public WaveTwo(Player player){
        generateEnnemies(player);
        load(ennemies);
    }

    @Override
    protected void generateEnnemies(Player player) {
        Ennemy ennemie;
        for (int i = 0; i < 3; i++) {
            ennemie = new Skeleton(player);
            ennemie.teleport((int) (Math.random() * 72 + 10),(int) (Math.random() * 324 + 460));
            ennemie.changeTarget(null);
            ennemies.add(ennemie);
        }

        for (int i = 0; i < 4; i++) {
            ennemie = new Sorcerer(player);
            ennemie.teleport((int) (Math.random() * 72 + 10),(int) (Math.random() * 324 + 460));
            ennemie.changeTarget(null);
            ennemies.add(ennemie);
        }

    }
}

package homeAndSwordGame;

import doctrina.Blockade;
import doctrina.Canvas;
import doctrina.CollidableRepository;
import homeAndSwordGame.entities.Player;
import homeAndSwordGame.entities.Spike;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class WorldContent {


    private ArrayList<Blockade> blocakdeArray = new ArrayList<>();

    private ArrayList<Spike> spikeArray = new ArrayList<>();

    public WorldContent(String filePath, Player player){
        loadMapContent(filePath, player);
    }

    public void loadMapContent(String filePath, Player player) {
        String column;
        int y = 0;
        URL ressource = this.getClass().getClassLoader().getResource(filePath);
        Scanner fileReader;

        try {
            File file = new File(ressource.toURI());
            fileReader = new Scanner(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        while (fileReader.hasNextLine()){
            column = fileReader.nextLine();
            for (int i = 0; i < column.length(); i++) {
                if (column.charAt(i) == '1'){
                    Blockade blockade = new Blockade();
                    blockade.setDiemsion(16,16);
                    blockade.teleport(i * 8, y * 16);
                    CollidableRepository.getInstance().registerEntity(blockade);
                    blocakdeArray.add(blockade);
                }
                if (column.charAt(i) == '2'){
                    spikeArray.add(new Spike(i * 8, y * 16 , 2.5f, player));

                }
            }

            y++;
        }
    }

    public void update() {
        for (Spike spike : spikeArray) {
            spike.update();
        }
    }

    public void draw(Canvas canvas){
        for (Spike spike : spikeArray) {
            spike.draw(canvas);
        }
    }

    public void drawHitBox(Canvas canvas){
        for (int i = 0; i < blocakdeArray.size(); i++) {
            blocakdeArray.get(i).draw(canvas);
        }
    }
}

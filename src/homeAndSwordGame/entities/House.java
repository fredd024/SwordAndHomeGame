package homeAndSwordGame.entities;

import doctrina.Blockade;
import doctrina.Canvas;
import doctrina.GameConfig;
import doctrina.StaticEntity;
import homeAndSwordGame.PersonalColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class House extends StaticEntity {
    private static final String SPRITE_PATH = "images/maison.png";
    private Image image;
    private Blockade blockade1;
    private Blockade blockade2;
    private Rectangle doorZone;
    private int width = 507;
    private int height = 288;

    public House(int x, int y) {
        teleport(x,y);
        blockade1 = new Blockade();
        blockade1.setDiemsion(480,120);
        blockade2 = new Blockade();
        blockade2.setDiemsion(130,72);
        doorZone = new Rectangle(x + 72,  y + height - 60 - 52,50,60);
        teleportBlockade();
        load();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawImage(image,x,y);
        if (GameConfig.isDebugEnabled()) {
            blockade1.draw(canvas);
            blockade2.draw(canvas);
            canvas.drawRectangle(doorZone, PersonalColor.getTransprentBlue());
        }
    }

    public boolean inDoorActionZone(Player player) {
        return player.intersectwith(doorZone);
    }

    private void teleportBlockade() {
        blockade1.teleport(x + 13,y + 80);
        blockade2.teleport(x + (width/2) - blockade2.getWidth()/2,y + height - blockade2.getHeight() - 16);
    }

    private void load() {
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(SPRITE_PATH));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}

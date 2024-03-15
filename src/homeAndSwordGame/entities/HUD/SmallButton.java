package homeAndSwordGame.entities.HUD;

import doctrina.Canvas;
import doctrina.StaticEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class SmallButton extends Button{

    public SmallButton(String text, int x, int y) {
        super(text, x, y);
        setImage("images/HUD/activeButton.png","images/HUD/unactiveButton.png");
        super.initialize();
    }
}

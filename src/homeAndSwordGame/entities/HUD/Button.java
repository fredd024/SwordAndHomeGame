package homeAndSwordGame.entities.HUD;

import doctrina.Canvas;
import doctrina.StaticEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class Button extends StaticEntity {

    private boolean active = false;
    private String text;
    private Rectangle textDimension;
    private final String ACTIVE_BUTTON_PATH = "images/HUD/activeButton.png";
    private final String UNACTIVE_BUTTON_PATH = "images/HUD/unactiveButton.png";
    private Image activeButtonImage;
    private Image unactiveButtonImage;
    private LevelHud levelHud;

    public Button(String text, int x, int y , LevelHud levelHud) {
        this.text = text;
        loadImage();
        detecDimension();
        teleport(x,y);
        this.levelHud = levelHud;
    }

    public void setActiveButton() {
        active = true;
    }

    public void unsetActiveButton() {
        active = false;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.setFont("/fonts/vinquerg.ttf",10f);
        if (textDimension == null) {
            textDimension = canvas.getStringDimension(text);
        }
        canvas.drawImage(getButtonImage(), this.getX() + levelHud.getX(), this.getY() + levelHud.getY());
        canvas.drawString(text
                , getCenterTextPositionX() + levelHud.getX()
                , getCenterTextPositionY() + levelHud.getY()
                , Color.BLACK);

    }

    private int getCenterTextPositionX() {
        return (int) (x + (width / 2) - (textDimension.getWidth() / 2));
    }

    private int getCenterTextPositionY() {

        int modifier = active ? 0 : 2;
        return (int) (y + (textDimension.getHeight()) + (height - textDimension.getHeight()) / 2) - modifier;
    }

    private void loadImage() {
        try {
            activeButtonImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(ACTIVE_BUTTON_PATH));
            unactiveButtonImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(UNACTIVE_BUTTON_PATH));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private Image getButtonImage() {
        if (active) {
            return activeButtonImage;
        }
        return unactiveButtonImage;
    }

    private void detecDimension() {
        ImageObserver imageObserver = (img, infoflags, x, y, width, height) -> false;
        this.width = activeButtonImage.getWidth(imageObserver);
        this.height = activeButtonImage.getHeight(imageObserver);
    }

}

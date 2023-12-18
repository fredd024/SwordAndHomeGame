package homeAndSwordGame;

import doctrina.Canvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class World {

    private String BACKGROUND_PATH;
    private String FOREGROUND_PATH;
    private int width;
    private int height;
    private Image background;
    private Image foreground;

    public World(String backgroundPath) {
        this.BACKGROUND_PATH = backgroundPath;
    }

    public World(String backgroundPath, String foregroundPath) {
        this.BACKGROUND_PATH = backgroundPath;
        this.FOREGROUND_PATH = foregroundPath;
    }

    public void load() {
        try {
            background = ImageIO.read(
                    this.getClass().getClassLoader().getResourceAsStream(BACKGROUND_PATH));
            if (FOREGROUND_PATH != null){
                foreground = ImageIO.read(
                        this.getClass().getClassLoader().getResourceAsStream(FOREGROUND_PATH));
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

        setDimension();
    }

    public void drawBackground(Canvas canvas) {
        canvas.drawImage(background,0,0);
    }

    public void drawForeground(Canvas canvas) {
        if (foreground != null) {
            canvas.drawImage(foreground,0,0);
        }
    }

    private void setDimension() {
        ImageObserver imageObserver = (img, infoflags, x, y, width, height) -> false;

        width = background.getWidth(imageObserver);
        height = background.getHeight(imageObserver);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}

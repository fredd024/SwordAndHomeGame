package doctrina;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AnimationSimple {

    private BufferedImage spriteSheet;
    private static final int ANIMATION_SPEED = 8;
    protected int currentAnimationFrame; //idle frame
    protected int nextFrame = 2;

    private Image[] frames;
    protected Image currentImage;


    protected boolean animationEnd;
    protected int numberFrame;
    protected int width;
    protected int height;

    public AnimationSimple(String spritePath, StaticEntity animatedEntity, int numberframe) {
        this.numberFrame = numberframe;
        width = animatedEntity.getHeight();
        height = animatedEntity.getWidth();

        loadSpriteSheet(spritePath);
        loadAnimation();
    }

    public AnimationSimple(String spritePath, int width, int height, int numberframe) {
        this.numberFrame = numberframe;
        this.width = width;
        this.height = height;

        loadSpriteSheet(spritePath);
        loadAnimation();
    }

    public void nextFrame(){
        updateFrame();
        currentImage = frames[currentAnimationFrame];
    }

    public void idle(){
        currentAnimationFrame = 0;
        currentImage = frames[0];

    }

    public void resetAnimation(){
        animationEnd = false;
        currentAnimationFrame = 0;
    }

    public Image getImage() {
        return currentImage;
    }

    public boolean isAnimationEnd(){
        return animationEnd;
    }

    protected void loadSpriteSheet(String spritePath) {
        try {
            spriteSheet = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(spritePath));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void loadAnimation() {
        frames = loadAnimationFrame(0);

    }

    protected Image[] loadAnimationFrame(int y) {
        Image[] frameSet = new Image[numberFrame];
        for (int i = 0; i < numberFrame; i++) {
            frameSet[i] = spriteSheet.getSubimage( (i * width),y,width,height);
        }
        return frameSet;
    }

    protected void updateFrame(){
        if (nextFrame > 0){
            nextFrame--;
            if (currentAnimationFrame == numberFrame - 1 && nextFrame == 1){
                animationEnd = true;
            } else {
                animationEnd = false;
            }
            return;
        }
        nextFrame = ANIMATION_SPEED;
        currentAnimationFrame++;
        if (currentAnimationFrame >= numberFrame){
            currentAnimationFrame = 0;
        }
    }
}

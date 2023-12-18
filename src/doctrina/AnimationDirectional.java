package doctrina;

import java.awt.*;

public class AnimationDirectional extends AnimationSimple {

    private Image[] rightFrames;
    private Image[] leftFrames;
    private Image[] upFrames;
    private Image[] downFrames;
    private Direction lastDirection;

    public AnimationDirectional(String spritePath, StaticEntity animatedEntity, int numberframe) {
        super(spritePath,animatedEntity,numberframe);

        loadSpriteSheet(spritePath);
        loadAnimation();
    }

    public AnimationDirectional(String spritePath, int width, int height, int numberframe) {
        super(spritePath,width,height,numberframe);

        loadSpriteSheet(spritePath);
        loadAnimation();
    }

    public void nextFrame(Direction direction){
        updateFrame();
        if (lastDirection == Direction.RIGHT || lastDirection == Direction.LEFT){
            currentImage = getFrameSet(lastDirection)[currentAnimationFrame];
        } else {
            currentImage = getFrameSet(direction)[currentAnimationFrame];
        }
        lastDirection = direction;
    }

    public void idle(Direction direction){
        currentAnimationFrame = 1;
        currentImage = getFrameSet(direction)[currentAnimationFrame];

    }



    @Override
    protected void loadAnimation() {
        downFrames = loadAnimationFrame(0);
        leftFrames =loadAnimationFrame(height);
        rightFrames = loadAnimationFrame(height * 2);
        upFrames = loadAnimationFrame(height * 3);

    }



    private Image[]getFrameSet(Direction direction){
        if (direction == Direction.UP){
            return upFrames;
        }
        if (direction == Direction.LEFT){
            return leftFrames;
        }
        if (direction == Direction.RIGHT){
            return rightFrames;
        }
        if (direction == Direction.DOWN){
            return downFrames;
        }
        return null;
    }

}

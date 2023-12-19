package homeAndSwordGame.scenes;

import doctrina.Canvas;
import doctrina.GameConfig;
import doctrina.ImageDrawer;
import doctrina.MovableEntity;
import homeAndSwordGame.*;
import homeAndSwordGame.entities.Dog;
import homeAndSwordGame.entities.Skeleton;

import java.awt.*;

public class HouseBasement extends Scene {
    private World world;
    private Rectangle doorZone;
    private Dog dog;
    private WorldContent worldContent;


    @Override
    public void initialize() {
        world = new World("images/World/BackgroundSSV1.png", "images/World/ForegroundSSV1.png");
        world.load();
        doorZone = new Rectangle(288, 432, 64, 32);
        dog = new Dog(player);
        dog.teleport(281,244);
        worldContent = new WorldContent("map /Basement.txt",player);
        ImageDrawer.getInstance().addEntity(player);
        ImageDrawer.getInstance().addEntity(dog);

    }

    @Override
    public void update() {
        for (MovableEntity entity: entities) {
            entity.update();
        }

        player.update();
        camera.setRelatedPosition(player);

        boolean interactPressed = gamePad.isInteractJustPressed();


        if (dog.actionTriggerRange(player) && interactPressed){
            if (dog.isFollowing()){
                player.removeFollower();
            } else {
                player.setFollower(dog);
            }
        }

        if (interactPressed) {
            if (player.intersectwith(doorZone)) {
                SoundEffect.DOOR_OPENING.play();
                player.teleport(142,70);
                SHGame.getInstance().changeScene(new HouseScene());
            }
        }

        dog.update();
        if (interactPressed && dog.actionTriggerRange(player)){
            SoundEffect.SQUICK_SQUICK.play();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        world.drawBackground(canvas);
        ImageDrawer.getInstance().draw(canvas);
        world.drawForeground(canvas);
        if (GameConfig.isDebugEnabled()) {
            worldContent.drawHitBox(canvas);
            canvas.drawRectangle(doorZone, PersonalColor.getTransprentBlue());
        }

        if (dog.actionTriggerRange(player)){
            canvas.drawString("(e) interact" , dog.getX() - 20 , dog.getY() , Color.WHITE);
        }

        if (player.intersectwith(doorZone)){
            canvas.drawString("(e) interact", player.getX() - 10, player.getY(), Color.white);
        }

    }

}

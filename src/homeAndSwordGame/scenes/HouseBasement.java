package homeAndSwordGame.scenes;

import doctrina.Canvas;
import doctrina.GameConfig;
import doctrina.ImageDrawer;
import homeAndSwordGame.*;
import homeAndSwordGame.entities.Dog;

import java.awt.*;

public class HouseBasement extends Scene {
    private World world;
    private Rectangle doorZone;
    private Dog dog;


    @Override
    public void initialize() {
        world = new World("images/World/BackgroundSSV1.png", "images/World/ForegroundSSV1.png");
        world.load();
        doorZone = new Rectangle(288, 432, 64, 32);
        dog = new Dog(player);
        dog.teleport(281,244);
        WorldHitbox3.loadMapHitbox();
        ImageDrawer.getInstance().addEntity(player);
        ImageDrawer.getInstance().addEntity(dog);

    }

    @Override
    public void update() {
        player.update();
        camera.setRelatedPosition(player);

        boolean interactPressed = gamePad.isInteractJustPressed();

        if (interactPressed) {
            if (player.intersectwith(doorZone)) {
                SoundEffect.DOOR_OPENING.play();
                SHGame.getInstance().changeScene(new HouseScene());
                player.teleport(142,70);
            }
        }

        dog.update();
        if (interactPressed && dog.actionTriggerRange(player)){
            SoundEffect.SQUICK_SQUICK.play();
            dog.action();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        world.drawBackground(canvas);
        ImageDrawer.getInstance().draw(canvas);
        world.drawForeground(canvas);
        if (GameConfig.isDebugEnabled()) {
            WorldHitbox3.draw(canvas);
            canvas.drawRectangle(doorZone, PersonalColor.getTransprentBlue());
        }

    }

}

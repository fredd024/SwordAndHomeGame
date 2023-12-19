package homeAndSwordGame.scenes;

import doctrina.Canvas;
import doctrina.GameConfig;
import doctrina.ImageDrawer;
import doctrina.MovableEntity;
import homeAndSwordGame.*;

import java.awt.*;

public class HouseScene extends Scene {

    private Rectangle outsideDoorZone;
    private Rectangle basementDoorZone;
    private World world;
    private WorldContent worldContent;

    @Override
    public void initialize() {
        outsideDoorZone = new Rectangle(96,480,64,32);
        basementDoorZone = new Rectangle(130,62,64,64);

        ImageDrawer.getInstance().addEntity(player);

        world = new World("images/World/BackgroundV1.png","images/World/ForegroundV1.png");
        world.load();
        camera.setBordes(world.getWidth(), world.getHeight());

        worldContent = new WorldContent("map /House.txt",player);
    }

    @Override
    public void update() {
        for (MovableEntity entity: entities) {
            entity.update();
        }
        player.update();
        camera.setRelatedPosition(player);

        if (gamePad.isInteractJustPressed() ){
            if (player.intersectwith(outsideDoorZone)){
                SoundEffect.DOOR_OPENING.play();
                player.teleport(800,600);
                SHGame.getInstance().changeScene(new OustideHouseScene());
            }
            if (player.intersectwith(basementDoorZone)){
                SoundEffect.DOOR_OPENING.play();
                player.teleport(300, 430);
                SHGame.getInstance().changeScene(new HouseBasement());
            }

        }

    }

    @Override
    public void draw(Canvas canvas) {
        world.drawBackground(canvas);

        player.draw(canvas);

        ImageDrawer.getInstance().draw(canvas);

        world.drawForeground(canvas);

        if (GameConfig.isDebugEnabled()){
            worldContent.drawHitBox(canvas);
            canvas.drawRectangle(outsideDoorZone,PersonalColor.getTransprentBlue());
            canvas.drawRectangle(basementDoorZone,PersonalColor.getTransprentBlue());
        }

        if (player.intersectwith(basementDoorZone)){
            canvas.drawString("(e) interact", player.getX() - 10, player.getY(), Color.white);
        }
        if (player.intersectwith(outsideDoorZone)){
            canvas.drawString("(e) interact", player.getX() - 10, player.getY(), Color.white);
        }

    }
}

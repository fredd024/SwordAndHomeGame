package homeAndSwordGame.scenes;

import doctrina.Canvas;
import doctrina.GameConfig;
import doctrina.ImageDrawer;
import doctrina.MovableEntity;
import homeAndSwordGame.*;
import homeAndSwordGame.entities.HUD.LevelHud;
import homeAndSwordGame.entities.House;

import java.awt.*;

public class OustideHouseScene extends Scene {

    private World world;
    private House house;
    private TriggerZone levelZone;
    private LevelHud  levelHud;
    private WorldContent worldContent;

    @Override
    public void initialize() {
        world = new World("images/World/outsideBackground.png","images/World/outsideForeground.png");
        world.load();
        house = new House(722,400);
        levelZone = new TriggerZone(896,0,64,48);
        camera.setBordes(world.getWidth(),world.getHeight());
        worldContent = new WorldContent("map /OutsideWorld.txt",player);
        levelHud = new LevelHud(gamePad,camera);

        ImageDrawer.getInstance().addEntity(player);
        ImageDrawer.getInstance().addEntity(house,100);

        SoundEffect.MAIN_MUSIC.loop();
    }

    @Override
    public void update() {
        for (MovableEntity entity: entities) {
            entity.update();
        }

        if (levelHud.isActive()) {
            levelHud.update();
            return;
        }

        boolean lol = gamePad.isInteractJustPressed();
        player.update();

        if (gamePad.isCameraAnimationPressed() && !camera.animation().isRunning()){

        }

        if (camera.animation().isRunning()){
            camera.animation().update();
        } else {
            camera.setRelatedPosition(player);
        }

        if (lol && house.inDoorActionZone(player) && !SHGame.getInstance().isInWave()){
            SoundEffect.DOOR_OPENING.play();
            SoundEffect.MAIN_MUSIC.stopLoop();
            player.teleport(122,460);
            SHGame.getInstance().changeScene(new HouseScene());
        }

        if (levelZone.isTriggeredBy(player) && !SHGame.getInstance().isInWave()){
            levelHud.show();
        }


    }

    @Override
    public void draw(Canvas canvas) {
        world.drawBackground(canvas);

        ImageDrawer.getInstance().draw(canvas);

        if (levelHud.isActive()) {
            levelHud.draw(canvas);
        }

        world.drawForeground(canvas);

        if (GameConfig.isDebugEnabled()){
            worldContent.drawHitBox(canvas);
            levelZone.draw(canvas);
        }

        if (house.inDoorActionZone(player)){
            canvas.drawString("(e) interact", player.getX() - 10, player.getY(), Color.white);
        }

    }
}

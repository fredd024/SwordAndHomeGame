package homeAndSwordGame.scenes;


import doctrina.Canvas;
import doctrina.GameConfig;
import doctrina.ImageDrawer;
import doctrina.MovableEntity;
import homeAndSwordGame.PersonalColor;
import homeAndSwordGame.SHGame;
import homeAndSwordGame.Wave.WaveOne;
import homeAndSwordGame.World;
import homeAndSwordGame.WorldContent;
import homeAndSwordGame.entities.Skeleton;

import java.awt.*;
import java.util.ArrayList;

public class LevelOne extends Scene {

    private World world;
    private ArrayList<Skeleton> skeletons = new ArrayList<>();
    private Rectangle exitZone;
    private WorldContent worldContent;

    @Override
    public void initialize() {
        world = new World("images/World/level1Background.png","images/World/level1Foreground.png");
        world.load();
        worldContent = new WorldContent("map /LevelOne.txt",player);
        player . teleport(1089,800);
        exitZone = new Rectangle(464,224,48,48);
        if (player.getFollower() != null){
            ((MovableEntity) player.getFollower()).teleport(player.getX(),player.getY());
        }

        generateSkeleton();

        ImageDrawer.getInstance().addEntity(player);
    }

    @Override
    public void update() {
        for (MovableEntity entity: entities) {
            entity.update();
        }
        player.update();
        for (Skeleton skeleton: skeletons) {
            skeleton.update();
        }
        if (player.intersectwith(exitZone) && areAllSkeletonKilled()){
            player.teleport(917,90);
            camera.animation().slideTo(0,300,1000,2000);
            SHGame.getInstance().changeScene(new OustideHouseScene());
            SHGame.getInstance().startWave(new WaveOne(player));
        }

        worldContent.update();
        camera.setRelatedPosition(player);

        if (gamePad.isFirePressed() && player.canAttack()){
            player.attack();
            for (Skeleton skeleton: skeletons) {
                if (skeleton.intersectwith(player.getAttackZone())){
                    skeleton.takeDamage(5);
                }
            }
        }

        if (gamePad.isCameraAnimationPressed()){
            for (Skeleton skeleton: skeletons) {
                skeleton.takeDamage(9999);
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        world.drawBackground(canvas);
        worldContent.draw(canvas);
        ImageDrawer.getInstance().draw(canvas);

        world.drawForeground(canvas);

        Rectangle stringDimension = canvas.getStringDimension("Ennemies restants :");
        canvas.drawString("Ennemies restants :", camera.getPositionX() + (800 /2) - (stringDimension.width / 2 ),camera.getPositionY() + 20, Color.WHITE);
        canvas.drawString( getEnnemiesLeft() +"",camera.getPositionX() + (800 /2) ,camera.getPositionY() + 40, Color.WHITE);

        if (GameConfig.isDebugEnabled()){
            worldContent.drawHitBox(canvas);
            canvas.drawRectangle(exitZone.x, exitZone.y, exitZone.width,exitZone.height, PersonalColor.getTransprentBlue());
        }

        if (player.intersectwith(exitZone)){
            canvas.drawString("Need to kill all Ennemy", player.getX() - 20, player.getY(), Color.RED);
        }
    }

    private void generateSkeleton(){
        Point[] positions = {
                new Point(1089,1054),
                new Point(556,1048),
                new Point(762,870),
                new Point(474,790),
                new Point(564,387),
                new Point(334,407)
        };

        for (Point point: positions) {
            Skeleton skeleton = new Skeleton(player);
            skeleton.teleport(point.x, point.y);
            skeletons.add(skeleton);
            ImageDrawer.getInstance().addEntity(skeleton);
        }

    }

    private boolean areAllSkeletonKilled(){
        return getEnnemiesLeft() == 0;
    }

    private int getEnnemiesLeft() {
        int ennemies = 0;

        for (Skeleton skeleton: skeletons) {
            if (skeleton.isAlive()){
                ennemies++;
            }
        }
        return ennemies;
    }
}

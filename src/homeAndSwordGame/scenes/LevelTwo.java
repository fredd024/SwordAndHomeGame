package homeAndSwordGame.scenes;

import doctrina.Canvas;
import doctrina.GameConfig;
import doctrina.ImageDrawer;
import doctrina.MovableEntity;
import homeAndSwordGame.PersonalColor;
import homeAndSwordGame.SHGame;
import homeAndSwordGame.Wave.WaveOne;
import homeAndSwordGame.Wave.WaveTwo;
import homeAndSwordGame.World;
import homeAndSwordGame.WorldContent;
import homeAndSwordGame.entities.LivingEntity;
import homeAndSwordGame.entities.Skeleton;
import homeAndSwordGame.entities.Sorcerer;

import java.awt.*;
import java.util.ArrayList;

public class LevelTwo extends Scene{

    private World world;
    private ArrayList<MovableEntity> ennemies = new ArrayList<>();
    private Rectangle exitZone;
    private WorldContent worldContent;

    @Override
    public void initialize() {
        world = new World("images/World/level2Background.png","images/World/level2Foreground.png");
        world.load();
        worldContent = new WorldContent("map /LevelTwo.txt",player);
        player . teleport(1210,164);
        exitZone = new Rectangle(256,336,72,24);
        if (player.getFollower() != null){
            ((MovableEntity) player.getFollower()).teleport(player.getX(),player.getY());
        }

        generateEnemmies();

        ImageDrawer.getInstance().addEntity(player);
    }

    @Override
    public void update() {
        for (MovableEntity entity: entities) {
            entity.update();
        }
        player.update();
        for (MovableEntity skeleton: ennemies) {
            skeleton.update();
        }
        if (player.intersectwith(exitZone) && areAllSkeletonKilled()){
            player.teleport(917,90);
            camera.animation().slideTo(0,300,1000,2000);
            SHGame.getInstance().changeScene(new OustideHouseScene());
            SHGame.getInstance().startWave(new WaveTwo(player));
        }

        worldContent.update();
        camera.setRelatedPosition(player);

        if (gamePad.isFirePressed() && player.canAttack()){
            player.attack();
            for (MovableEntity ennemie:  ennemies) {
                if (ennemie.intersectwith(player.getAttackZone())){
                    ((LivingEntity) ennemie).takeDamage(5);
                }
            }
        }

        if (gamePad.isCameraAnimationPressed()){
            for (MovableEntity ennemie: ennemies) {
                ((LivingEntity) ennemie).takeDamage(9999);
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

    private void generateEnemmies(){
        Point[] positions = {
                new Point(1068,440),
                new Point(922,248),
                new Point(504,354),
                new Point(384,456),
                new Point(698,298)
        };


        for (Point point: positions) {
            MovableEntity ennemie;
            if ((int) (Math.random() * 2) == 0) {
                ennemie = new Skeleton(player);
            } else {

                ennemie = new Sorcerer(player);
            }
            ennemie.teleport(point.x, point.y);
            ennemies.add(ennemie);
            ImageDrawer.getInstance().addEntity(ennemie);
        }

    }

    private boolean areAllSkeletonKilled(){
        return getEnnemiesLeft() == 0;
    }

    private int getEnnemiesLeft() {
        int ennemiesLeft = 0;

        for (MovableEntity ennemie: ennemies) {
            if (((LivingEntity) ennemie).isAlive()){
                ennemiesLeft++;
            }
        }
        return ennemiesLeft;
    }
}

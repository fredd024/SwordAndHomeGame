package homeAndSwordGame.scenes;


import doctrina.Canvas;
import doctrina.GameConfig;
import doctrina.ImageDrawer;
import homeAndSwordGame.PersonalColor;
import homeAndSwordGame.SHGame;
import homeAndSwordGame.Wave.WaveOne;
import homeAndSwordGame.World;
import homeAndSwordGame.WorldHitbox4;
import homeAndSwordGame.entities.Skeleton;

import java.awt.*;
import java.util.ArrayList;

public class LevelOne extends Scene {

    private World world;
    private ArrayList<Skeleton> skeletons = new ArrayList<>();
    private Rectangle exitZone;

    @Override
    public void initialize() {
        world = new World("images/World/level1Background.png","images/World/level1Foreground.png");
        world.load();
        WorldHitbox4.loadMapContent(player);
        player . teleport(1089,800);
        exitZone = new Rectangle(464,224,48,48);

        generateSkeleton();

        ImageDrawer.getInstance().addEntity(player);
    }

    @Override
    public void update() {
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

        WorldHitbox4.update();
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
        WorldHitbox4.draw(canvas);
        ImageDrawer.getInstance().draw(canvas);

        world.drawForeground(canvas);
        if (GameConfig.isDebugEnabled()){
            WorldHitbox4.drawHitBox(canvas);
            canvas.drawRectangle(exitZone.x, exitZone.y, exitZone.width,exitZone.height, PersonalColor.getTransprentBlue());
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
        for (Skeleton skeleton: skeletons) {
            if (skeleton.isAlive()){
                return false;
            }
        }
        return  true;
    }
}

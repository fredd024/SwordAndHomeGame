package homeAndSwordGame;

import doctrina.Canvas;
import doctrina.*;
import homeAndSwordGame.Wave.Wave;
import homeAndSwordGame.Wave.WaveOne;
import homeAndSwordGame.entities.Player;
import homeAndSwordGame.entities.Skeleton;
import homeAndSwordGame.scenes.OustideHouseScene;
import homeAndSwordGame.scenes.Scene;

import java.awt.*;
import java.util.ArrayList;

public class SHGame extends Game {

    private static SHGame instance;

    private GamePad gamePad;
    private Camera  camera;
    private Player player;
    private Scene actualScene;
    private Wave wave;

    public static SHGame getInstance() {
        instance = instance == null ? new SHGame() : instance;
        return instance;
    }

    @Override
    protected void initialize() {
        RenderingEngine.getInstance().getScreen().windowed();
        gamePad = new GamePad();
        gamePad.useWasdKey();
        camera = new Camera();
        player = new Player(gamePad);
        changeScene(new OustideHouseScene());
        player.teleport(500,500);
        actualScene.initialize();
        GameConfig.enableDebug();
        if (GameConfig.isFullScreen()){
            RenderingEngine.getInstance().getScreen().fullscreen();
        }
    }

    @Override
    protected void update() {

        if (GameTime.getElapsedTime() < 500 && GameTime.getElapsedTime() != 0){
            GameConfig.disableDebug();
        }

        if (!player.isAlive()){
            if (gamePad.isInteractPressed()){
                SHGame.getInstance().restart();
            }
            if(gamePad.isQuitJustPressed()){
                instance.stop();
            }
            return;
        }

        actualScene.update();

        if(gamePad.isQuitJustPressed()){
            instance.stop();
        }

        if (gamePad.isFullScreenJustPressed()){
            RenderingEngine.getInstance().getScreen().stop();
            if (RenderingEngine.getInstance().getScreen().isFullScreenMode()){
                RenderingEngine.getInstance().getScreen().windowed();
                GameConfig.fullScreenDisable();
            } else {
                RenderingEngine.getInstance().getScreen().fullscreen();
                GameConfig.fullScreenEnable();
            }
            RenderingEngine.getInstance().getScreen().start();
        }

        if (gamePad.isSettingJustPressed()){
            GameConfig.toggleDebug();
        }

        if (wave != null && !camera.animation().isRunning()){
            wave.update();
        }

        if (gamePad.isFirePressed() && player.canAttack()){
            player.attack();
            if (wave != null){
                for (Skeleton skeleton: wave.getEnnemies()) {
                    if (skeleton.intersectwith(player.getAttackZone())) {
                        skeleton.takeDamage(5);
                    }
                }
            }
        }

    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.drawRectangle(0, 0, RenderingEngine.getInstance().getScreen().getWidth(), RenderingEngine.getInstance().getScreen().getHeigth(), Color.BLACK);
        camera.update(canvas);

        actualScene.draw(canvas);

        if (GameConfig.isDebugEnabled()) {
            canvas.drawString("poisition: x=" + player.getX() + " y=" + player.getY(), 10 + camera.getPositionX() + 640, 20 + camera.getPositionY(), Color.WHITE);
            canvas.drawString("fps :" + GameTime.getCurrentFps(), 10 + camera.getPositionX() + 740, 40 + camera.getPositionY(), Color.WHITE);
        }

        player.drawHud(canvas,camera);

        if (isInWave()){
            if (wave.runOutOfTime()){
                player.takeDamage(9999999);
            }
            wave.drawTime(canvas,camera);
        }

        if (!player.isAlive()){
            canvas.drawRectangle(camera.getPositionX(), camera.getPositionY(), 800,RenderingEngine.getInstance().getScreen().getHeigth(), new Color(0,0,0,80));
            canvas.setFont("/fonts/vinquerg.ttf",32f);
            Rectangle gameOverDimension = canvas.getStringDimension("Game Over");
            canvas.drawString("Game Over",camera.getPositionX() + (800 / 2) - (int) (gameOverDimension.getWidth() + 10 ) , camera.getPositionY() + (600 / 2) - (int) (gameOverDimension.getHeight() / 2), Color.RED);
            canvas.setFont("/fonts/vinquerg.ttf",16f);
            Rectangle restartDimension = canvas.getStringDimension("Press [e] to restart");
            canvas.drawString("Press [e] to restart",camera.getPositionX() + (800 / 2) - (int) (restartDimension.getWidth() /4) , camera.getPositionY() + (600 / 2) + (int) gameOverDimension.getHeight(), Color.WHITE);
            Rectangle quitDimension = canvas.getStringDimension("Press [q] to quit");
            canvas.drawString("Press [q] to quit",camera.getPositionX() + (800 / 2) - (int) (quitDimension.getWidth() /2) , camera.getPositionY() + (600 / 2) + (int) gameOverDimension.getHeight() +(int) restartDimension.getHeight() / 2, Color.WHITE);
        }

    }

    public void changeScene(Scene newScene) {
        camera.setPosition(0,0);
        CollidableRepository.getInstance().clear();
        ImageDrawer.getInstance().clear();
        actualScene = newScene;
        actualScene.setBasic(player,gamePad,camera);
        actualScene.initialize();
    }

    public void restart() {
        instance.stop();
        instance = new SHGame();
        instance.start();
    }

    public void startWave(Wave wave){
        this.wave = wave;
    }

    public boolean isInWave() {
        if (wave == null){
            return false;
        }
        return !wave.isWaveEnded();
    }
}

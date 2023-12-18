package homeAndSwordGame;

import doctrina.Canvas;
import doctrina.*;
import homeAndSwordGame.Wave.Wave;
import homeAndSwordGame.entities.Player;
import homeAndSwordGame.entities.Skeleton;
import homeAndSwordGame.scenes.OustideHouseScene;
import homeAndSwordGame.scenes.Scene;

import java.awt.*;

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
        if (wave != null && wave.isWaveEnd()){
            wave = null;
        }

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

    }

    @Override
    protected void draw(Canvas canvas) {
        canvas.drawRectangle(0, 0, RenderingEngine.getInstance().getScreen().getWidth(), RenderingEngine.getInstance().getScreen().getHeigth(), Color.BLACK);
        camera.update(canvas);

        actualScene.draw(canvas);

        if (GameConfig.isDebugEnabled()) {
            canvas.drawString("poisition: x=" + player.getX() + " y=" + player.getY(), 10 + camera.getPositionX(), 20 + camera.getPositionY(), Color.WHITE);
            canvas.drawString("fps :" + GameTime.getCurrentFps(), 10 + camera.getPositionX(), 40 + camera.getPositionY(), Color.WHITE);
        }

        player.drawHud(canvas,camera);

        if (!player.isAlive()){
            canvas.drawRectangle(camera.getPositionX(), camera.getPositionY(), RenderingEngine.getInstance().getScreen().getWidth(),RenderingEngine.getInstance().getScreen().getHeigth(), new Color(0,0,0,70));
            canvas.drawFontString("Game Over",camera.getPositionX() + (RenderingEngine.getInstance().getScreen().getWidth() / 2) , camera.getPositionY() + (RenderingEngine.getInstance().getScreen().getWidth() / 2), Color.RED);
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
        return wave == null;
    }
}

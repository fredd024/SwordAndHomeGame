package homeAndSwordGame.scenes;

import doctrina.Canvas;
import doctrina.GameConfig;
import homeAndSwordGame.GamePad;
import homeAndSwordGame.SHGame;
import homeAndSwordGame.SoundEffect;
import homeAndSwordGame.entities.HUD.BigButton;
import homeAndSwordGame.entities.HUD.Hand;
import homeAndSwordGame.entities.HUD.SmallButton;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;

public class ScreenMenu extends Scene {

    private Image background;
    private Image title;
    private boolean isActive = false;
    private ArrayList<BigButton> buttons = new ArrayList<>();
    private int activeButton = 0;
    private GamePad gamepad;
    private Hand hand;

    public ScreenMenu(GamePad gamepad) {
        this.gamepad = gamepad;
        hand = new Hand(0,0);
        loadImage();
        loadButtons();
        changeButton(0);
    }

    @Override
    public void initialize() {

    }

    public void update() {
        hand.update();
        if (gamepad.isDownJustPressed()){
            changeButton(1);
        }

        if (gamepad.isUpJustPressed()){
            changeButton(-1);
        }

        if (gamepad.isQuitJustPressed()){
            SHGame.getInstance().stop();
        }

        if (gamepad.isInteractJustPressed()){
            if (activeButton == 0){
                isActive= false;
                GameConfig.disableDebug();
            }
            if (activeButton == 1){

            }
            if (activeButton == 2){
                SHGame.getInstance().stop();
            }
        }
    }

    public void draw(Canvas canvas){
        canvas.drawImage(background,0,0);
        canvas.drawRectangle(0,0,800,600, new Color(30,30,30,50));
        ImageObserver imageObserver = (img, infoflags, x, y, width, height) -> false;
        canvas.drawImage(title, 400 - title.getWidth(imageObserver)/2,0);
        for (int i = 0; i < buttons.size(); i++) {
            buttons.get(i).draw(canvas);
        }
        hand.draw(canvas);
    }

    public void activateScreen(){
        isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }

    private void loadImage(){
        try {
            background = ImageIO.read(
                    this.getClass().getClassLoader().getResourceAsStream("images/SHMenu.png"));

            title = ImageIO.read(
                    this.getClass().getClassLoader().getResourceAsStream("images/Sword&Home.png"));

        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }

    }

    private void loadButtons() {
        BigButton startBtn = new BigButton("Play",400,200);
        startBtn.teleport(400 - startBtn.getWidth()/2,startBtn.getY());
        buttons.add(startBtn);

        BigButton optionBtn = new BigButton("option",400,325);
        optionBtn.teleport(400 - optionBtn.getWidth()/2,optionBtn.getY());
        buttons.add(optionBtn);

        BigButton quitButton = new BigButton("quit",400,450);
        quitButton.teleport(400 - quitButton.getWidth()/2,quitButton.getY());
        buttons.add(quitButton);

    }

    private void changeButton(int modifier){
        buttons.get(activeButton).unsetActiveButton();
        activeButton += modifier;
        activeButton = activeButton <0 ? buttons.size()-1 : activeButton;

        activeButton = activeButton > buttons.size()-1 ? 0 : activeButton;
        buttons.get(activeButton).setActiveButton();

        hand.teleport(buttons.get(activeButton).getX() + buttons.get(activeButton).getWidth()/2 - hand.getWidth()/2  ,buttons.get(activeButton).getY() - hand.getHeight()/2);
    }

}

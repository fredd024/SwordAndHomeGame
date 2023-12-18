package homeAndSwordGame.entities.HUD;

import doctrina.Camera;
import doctrina.Canvas;
import doctrina.StaticEntity;
import homeAndSwordGame.GamePad;
import homeAndSwordGame.SHGame;
import homeAndSwordGame.scenes.LevelOne;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;

public class LevelHud extends StaticEntity {

    private Camera camera;
    private GamePad gamePad;
    private ArrayList<Button> buttons = new ArrayList<>();
    private Image backgroundImage;
    private final String BACKGROUND_PATH = "images/HUD/levelBackground.png";
    private final int COLUMN = 3;
    private final int ROW = 5;
    private int activeButton = 0;
    private boolean isActive = false;
    private Hand hand;

    public LevelHud(GamePad gamepad, Camera camera){
        loadImage();
        detectDimension();
        loadButton();
        this.camera = camera;
        this.gamePad = gamepad;
        buttons.get(activeButton).setActiveButton();
        hand = new Hand(buttons.get(activeButton).getX(),buttons.get(activeButton).getY());


    }

    public void update() {
        hand.update();

        if (gamePad.isRightJustPressed()){
            changeButton(1);
            return;
        }
        if (gamePad.isLeftJustPressed()){
            changeButton(-1);
            return;
        }
        if (gamePad.isUpJustPressed()){
            changeButton(-COLUMN);
            return;
        }
        if (gamePad.isDownJustPressed()){
            changeButton(COLUMN);
            return;
        }
        if (gamePad.isQuitJustPressed()){

            isActive = false;
            return;
        }
        if (gamePad.isInteractJustPressed()){
            if (activeButton == 0){
                SHGame.getInstance().changeScene(new LevelOne());
                System.out.println("changement");
            }
            isActive = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {

        teleport(camera.getPositionX() + 300,camera.getPositionY() + 300);
        canvas.drawImage(backgroundImage,x,y);
        for (Button btn: buttons) {
            btn.draw(canvas);
        }
        hand.draw(canvas);

        Rectangle StringOneDimension = canvas.getStringDimension("[echap] - quitter");
        Rectangle StringTwoDimension = canvas.getStringDimension("[Enter] - selectioner");
        canvas.drawString("q - quitter",this.x, this.y + this.height + (int) StringOneDimension.getHeight(),Color.WHITE);
        canvas.drawString("e - selectioner",this.x , this.y + this.height + (int) StringOneDimension.getHeight() + (int) StringTwoDimension.getHeight(),Color.WHITE);
    }

    public boolean isActive(){
        return isActive;
    }

    public void show() {
        isActive = true;
    }
    private void loadImage() {
        try {
            backgroundImage = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(BACKGROUND_PATH));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void detectDimension() {
        ImageObserver imageObserver = (img, infoflags, x, y, width, height) -> false;
        this.width = backgroundImage.getWidth(imageObserver);
        this.height = backgroundImage.getHeight(imageObserver);
    }

    private void loadButton(){
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                buttons.add(new Button("" + (j + (COLUMN * i) + 1),(j * (this.width / COLUMN)) + 6 , (i * (this.height / ROW) ) + 6,this));
            }
        }
    }

    private void changeButton(int modifier){
        buttons.get(activeButton).unsetActiveButton();
        activeButton += modifier;
        activeButton = activeButton > (ROW * COLUMN) -1 ? activeButton - (ROW * COLUMN): activeButton;
        activeButton = activeButton < 0 ? (ROW * COLUMN) - 1 : activeButton;
        buttons.get(activeButton).setActiveButton();

        hand.teleport(buttons.get(activeButton).getX() + this.getX(),buttons.get(activeButton).getY() + this.getY() - hand.getHeight()/2);
    }
}

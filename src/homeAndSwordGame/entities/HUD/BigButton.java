package homeAndSwordGame.entities.HUD;

import doctrina.Canvas;
import doctrina.StaticEntity;

public class BigButton extends Button {
    public BigButton(String text, int x, int y) {
        super(text, x, y);
        setImage("images/HUD/activeBigButton.png","images/HUD/unactiveBigButton.png");
        super.initialize();
    }


}

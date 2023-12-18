package homeAndSwordGame;

import doctrina.MovementController;

import java.awt.event.KeyEvent;

public class GamePad extends MovementController {

    private int quitKey = KeyEvent.VK_Q;
    private int fireKey = KeyEvent.VK_SPACE;
    private int cameraAnimationKey = KeyEvent.VK_P;
    private int debugKey = KeyEvent.VK_O;
    private int fullScreenKey = KeyEvent.VK_L;
    private int interactKey = KeyEvent.VK_E;



    public GamePad() {
        bindKey(quitKey);
        bindKey(fireKey);
        bindKey(cameraAnimationKey);
        bindKey(debugKey);
        bindKey(fullScreenKey);
        bindKey(interactKey);
    }

    public boolean isFirePressed() {
        return isKeyPressed(fireKey);
    }

    public boolean isInteractPressed() { return  isKeyPressed(interactKey);}

    public boolean isSettingJustPressed() {
        return isKeyJustPressed(debugKey);
    }

    public boolean isFullScreenJustPressed() {return  isKeyJustPressed(fullScreenKey);}

    public boolean isInteractJustPressed() {return  isKeyJustPressed(interactKey);}

    public boolean isCameraAnimationPressed() {
        return isKeyPressed(cameraAnimationKey);
    }

    public boolean isQuitJustPressed(){ return isKeyJustPressed(quitKey);}

}

package doctrina;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;

public abstract class Controller implements KeyListener {

    private final HashMap<Integer, Boolean> pressedKeys;
    private final HashMap<Integer, Boolean> lastFramePressedKeys;

    public Controller() {
        pressedKeys = new HashMap<>();
        lastFramePressedKeys = new HashMap<>();
        RenderingEngine.getInstance().addKeyListener(this);
    }

    protected void bindKey(int keyCode) {
        pressedKeys.put(keyCode, false);
        lastFramePressedKeys.put(keyCode, false);
    }

    protected void clearKeys() {
        pressedKeys.clear();
    }

    protected void unbindKey(int keyCode) {pressedKeys.remove(keyCode);}

    protected void removeKey(int keyCode) {
        pressedKeys.remove(keyCode);
    }

    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.containsKey(keyCode)
                && pressedKeys.get(keyCode);
    }

    protected boolean isKeyJustPressed(int keyCode){
        if ( lastFramePressedKeys.get(keyCode) != isKeyPressed(keyCode) && isKeyPressed(keyCode)) {
            lastFramePressedKeys.put(keyCode,isKeyPressed(keyCode));
            return true;
        }
        lastFramePressedKeys.put(keyCode,isKeyPressed(keyCode));
        return false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (pressedKeys.containsKey(keyCode)){
            pressedKeys.put(keyCode, true);
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (pressedKeys.containsKey(keyCode)){
            pressedKeys.put(keyCode, false);
        }
    }
}

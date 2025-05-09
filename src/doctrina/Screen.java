package doctrina;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Screen {
    private JFrame frame;
    private Cursor invisibleCursor;
    private GraphicsDevice device;
    private DisplayMode fullscreenDisplayMode;
    private boolean isFullScreenMode;

    public Screen() {
        initializeFrame();
        initializeInivisbleCursor();
        initializeDevice();
    }

    public boolean isFullScreenMode() {
        return isFullScreenMode;
    }

    public void start() {
        frame.setVisible(true);
    }

    public void stop() {
        frame.setVisible(false);
        frame.dispose();
    }

    public void hideCursor() {
        frame.setCursor(invisibleCursor);
    }

    public void showCursor() {
        frame.setCursor(Cursor.getDefaultCursor());
    }

    public void setSize(int width, int height){
        boolean frameIsVisible = frame.isVisible();
        if (frameIsVisible){
            frame.setVisible(false);
        }
        frame.setSize(width,height);
        frame.setLocationRelativeTo(null);
        if (frameIsVisible){
            frame.setVisible(true);
        }
        //fullscreenDisplayMode =  findClosestDisplayMode(width,height);
        System.out.println("Fullscreen Mode: " + fullscreenDisplayMode.getWidth() + "x " +fullscreenDisplayMode.getHeight()+ "y");
    }

    public int getWidth() {
        return isFullScreenMode ? fullscreenDisplayMode.getWidth() : frame.getWidth();
    }

    public int getHeigth() {
        return isFullScreenMode ? fullscreenDisplayMode.getHeight() : frame.getHeight();
    }

    public void fullscreen() {
        if (device.isFullScreenSupported()) {
            device.setFullScreenWindow(frame);
        }
        // device.setDisplayMode(fullscreenDisplayMode); || C'est l'ancienne facons de faire
        frame.setLocationRelativeTo(null);
        isFullScreenMode = true;
    }

    public void windowed() {
        if (device.isFullScreenSupported()) {
            device.setFullScreenWindow(null);
        }
        // device.setDisplayMode(windowedDisplayMode); || C'est l'ancienne facons de faire
        frame.setLocationRelativeTo(null);
        isFullScreenMode = false;
    }

    public void setPanel(JPanel panel) {
        frame.add(panel);
    }

    private DisplayMode findClosestDisplayMode(int width, int height) {
        DisplayMode[] displayModes = device.getDisplayModes();
        int desiredResolution = width * height;
        int[] availiableResolutions = new int[displayModes.length];
        for (int i = 0; i < displayModes.length; i++) {
            availiableResolutions[i] = displayModes[i].getWidth() * displayModes[i].getHeight();
        }
        return displayModes[closestIndexOfValue(desiredResolution, availiableResolutions)];
    }

    private  int closestIndexOfValue (int value, int[] list) {
        int closestIndex = -1;
        for (int i = 0, min = Integer.MAX_VALUE; i < list.length; i++) {
            final int difference = Math.abs (list[i] - value);
            if (difference < min) {
                min = difference;
                closestIndex = i;
            }
        }
        return closestIndex;
    }

    private void initializeFrame() {
        frame = new JFrame();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setTitle("Doctrine Game");
        frame.setIgnoreRepaint(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setState(JFrame.NORMAL);
        frame.setUndecorated(true);
    }

    private void initializeInivisbleCursor() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Point hotSpot = new Point(0,0);
        BufferedImage cursorImage = new BufferedImage(1,1,BufferedImage.TRANSLUCENT);
        invisibleCursor = toolkit.createCustomCursor(cursorImage,hotSpot,"invisibleCursor");
    }

    private void initializeDevice() {
        device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        fullscreenDisplayMode = device.getDisplayMode();
        // windowedDisplayMode = device.getDisplayMode();
    }
}

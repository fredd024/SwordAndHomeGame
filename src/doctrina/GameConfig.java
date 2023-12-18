package doctrina;

public class GameConfig {

    private static boolean debug;
    private static boolean fullScreen;

    public static boolean isDebugEnabled() {
        return debug;
    }

    public static void enableDebug(){
        debug = true;
    }

    public static void disableDebug() {
        debug = false;
    }

    public static void toggleDebug() {
        debug = !debug;
    }


    public static boolean isFullScreen() { return fullScreen;}

    public static void fullScreenEnable() {  fullScreen = true;}

    public static void fullScreenDisable() {  fullScreen = false;}
}

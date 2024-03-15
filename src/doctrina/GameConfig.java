package doctrina;

public class GameConfig {

    private static boolean debug;
    private static boolean fullScreen;
    private static boolean sound = true;

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

    public static void toggleSoubd() {
        sound = !sound;
    }

    public static boolean isSound(){ return sound;}
}

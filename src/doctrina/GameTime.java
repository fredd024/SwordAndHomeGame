package doctrina;

import java.util.concurrent.TimeUnit;

public class GameTime {


    private static final int FPS_TARGET = 60;

    private static int currentFps;
    private static int fpsCount;
    private static long fpsTimeDelta;
    private static long gameStartTime;
    private long lastFrameTime;
    private static float deltaFrameSecond;
    private long syncTime;

    public static float getDeltaFrameSecond() {
        return deltaFrameSecond;
    }

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public static int getCurrentFps(){
        return (currentFps > 0) ? currentFps : fpsCount;
    }

    public static String getElapsedFormatedTime() {
        long time = getElapsedTime();
        long hours = TimeUnit.MILLISECONDS.toHours(time);
        time -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(time);
        time -= TimeUnit.MINUTES.toMillis(minutes);
        long secondes = TimeUnit.MILLISECONDS.toSeconds(time);
        return String.format("%02d:%02d:%02d",hours,minutes,secondes);
    }

    public static long getElapsedTime() {
        return System.currentTimeMillis() - gameStartTime;
    }

    protected GameTime() {
        updateSyncTime();
        gameStartTime = System.currentTimeMillis();
        fpsTimeDelta = 0;
        currentFps = 0;
    }

    protected void synchronize() {
        update();
        try {
            Thread.sleep(getSleepTime());
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        updateSyncTime();
    }

    private void update() {
        fpsCount++;
        long currentSecond = TimeUnit.MILLISECONDS.toSeconds(getElapsedTime());
        if (fpsTimeDelta != currentSecond){
            currentFps = fpsCount;
            fpsCount = 0;
        }
        fpsTimeDelta = currentSecond;
        deltaFrameSecond = (System.currentTimeMillis() - lastFrameTime) / 1000.0f;
        lastFrameTime = System.currentTimeMillis();
    }

    private long getSleepTime() {
        long targetTime = 1000L / FPS_TARGET;
        long sleep = targetTime - (System.currentTimeMillis() - syncTime);
        if (sleep < 0) {
            sleep = 4;
        }
        return sleep;
    }

    private void updateSyncTime() {
        syncTime = System.currentTimeMillis();
    }
}

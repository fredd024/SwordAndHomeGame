package doctrina;

public class CameraAnimation{

    private Camera camera;
    private int cameraDestinationX;
    private int cameraDestinationY;
    private float animationDuration;
    private float animationStop;
    private boolean isRunning;

    public CameraAnimation(Camera camera){
        this.camera = camera;
    }

    public void slideTo(int x, int y, int timeMillis, int timeStopMillis) {
        isRunning = true;
        cameraDestinationX = x;
        cameraDestinationY = y;
        animationDuration = timeMillis / 1000;
        animationStop = timeStopMillis / 1000;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void update() {
        if (animationDuration > 0) {
            updateAnimation();
            return;
        }
        if (animationStop > 0) {
            updateTimeStop();
            return;
        }

        isRunning = false;
    }

    private void updateAnimation() {
        int distanceX = ((int) ((( cameraDestinationX - camera.getPositionX() ) * GameTime.getDeltaFrame() ) / animationDuration) );
        int distanceY = ((int) ((( cameraDestinationY - camera.getPositionY() ) * GameTime.getDeltaFrame() ) / animationDuration) );
        camera.setPosition(camera.getPositionX() + distanceX, camera.getPositionY() + distanceY);
        animationDuration -= GameTime.getDeltaFrame();

    }

    private void updateTimeStop(){
        animationStop -= GameTime.getDeltaFrame();
    }
}

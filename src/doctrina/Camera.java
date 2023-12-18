package doctrina;

public class Camera{

    private int positionX;
    private int positionY;
    private int mapWidth;
    private int mapHeight;
    private int camWidth = RenderingEngine.getInstance().getScreen().getWidth();
    private int camHeight = RenderingEngine.getInstance().getScreen().getHeigth();
    private CameraAnimation animation;

    public Camera () {
        positionX = 0;
        positionY = 0;
        this.mapWidth = 500;
        this.mapHeight = 600;
        animation = new CameraAnimation(this);
    }

    public void setBordes(int borderX, int borderY) {
        mapWidth = borderX;
        mapHeight = borderY;
    }

    public void setRelatedPosition(MovableEntity centeredEntity) {
        positionX = centeredEntity.getX() - camWidth / 2 - centeredEntity.getWidth() /2;
        positionY = centeredEntity.getY() - camHeight / 2 - centeredEntity.getHeight() /2;
    }

    public void setPosition(int x, int y) {
        positionX = x;
        positionY = y;
    }

    public void update(Canvas canvas) {
        calculatePosition();
        canvas.translate(-positionX,-positionY);
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public CameraAnimation animation() {
        return animation;
    }

    private void calculatePosition() {
        if (positionY < 0) {
            positionY = 0;
        }
        if (positionX < 0) {
            positionX = 0;
        }
        if (positionY > mapHeight - camHeight) {
            positionY = mapHeight - camHeight ;
        }
        if (positionX > mapWidth - camWidth) {
            positionX = mapWidth - camWidth;
        }
    }


}

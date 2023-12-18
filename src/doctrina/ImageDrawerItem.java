package doctrina;

public class ImageDrawerItem {

    private StaticEntity drawEntity;
    private int positionModifer;

    public ImageDrawerItem(StaticEntity entity, int modifer) {
        drawEntity= entity;
        positionModifer = modifer;
    }

    public StaticEntity getDrawEntity() {
        return drawEntity;
    }

    public int getPositionModifer() {
        return positionModifer;
    }
}

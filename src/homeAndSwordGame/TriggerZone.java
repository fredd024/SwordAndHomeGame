package homeAndSwordGame;

import doctrina.Canvas;
import doctrina.MovableEntity;

import java.awt.*;

public class TriggerZone {

    private boolean canTriggered = true;
    private boolean triggered;
    private Rectangle zone;

    public TriggerZone(int width,int height){
        zone = new Rectangle(width,height);
    }

    public TriggerZone(int x, int y,int width,int height){
        zone = new Rectangle(x,y,width,height);
    }

    public void teleport(int x, int y) {
        zone.setLocation(x,y);
    }

    public boolean isTriggeredBy(MovableEntity entity) {
        intersectWith(entity);
        return triggered;
    }

    public void draw (Canvas canvas){
        canvas.drawRectangle(zone,new Color(255,255,0,80));
    }

    private void intersectWith(MovableEntity entity){
        if (entity.intersectwith(zone) && canTriggered){
            triggered = true;
            canTriggered = false;
            return;
        }
        if (!entity.intersectwith(zone) && !canTriggered){
            canTriggered = true;
        }
        triggered = false;
    }

}

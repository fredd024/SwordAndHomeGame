package doctrina;

import java.util.ArrayList;
import java.util.Iterator;

public class ImageDrawer implements Iterable<ImageDrawerItem>{

    private static ImageDrawer instance;
    private static ArrayList<ImageDrawerItem> entityList;

    public ImageDrawer() {
        entityList = new ArrayList<>();
    }

    public static ImageDrawer getInstance() {
        if ( instance == null){
            instance =  new ImageDrawer();
        }
        return instance;
    }

    public void addEntity(StaticEntity entity,int positionModifer) {
        entityList.add(new ImageDrawerItem(entity,positionModifer));
    }

    public void addEntity(StaticEntity entity) {
        entityList.add(new ImageDrawerItem(entity,0));
    }

    public void draw(Canvas canvas){
        sortArray();
        for (int i = 0; i < entityList.size(); i++) {
            entityList.get(i).getDrawEntity().draw(canvas);
        }
    }

    public void clear() {
        entityList.clear();
    }

    private void sortArray(){
        for (int i = 0; i < entityList.size(); i++) {
            int positionY = Integer.MAX_VALUE;
            int index = 0;
            ImageDrawerItem item = null;
            for (int j = i; j < entityList.size(); j++) {
                ImageDrawerItem comparedItem = entityList.get(j);
                if (comparedItem.getDrawEntity().getY() + comparedItem.getPositionModifer() < positionY){
                    positionY =  comparedItem.getDrawEntity().getY() + comparedItem.getPositionModifer();
                    item = comparedItem;
                    index = j;
                }
            }
            entityList.set(index, entityList.get(i));
            entityList.set(i,item);


        }
    }

    @Override
    public Iterator<ImageDrawerItem> iterator() {
        return ImageDrawer.getInstance().iterator();
    }
}

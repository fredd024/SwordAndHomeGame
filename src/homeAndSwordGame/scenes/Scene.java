package homeAndSwordGame.scenes;

import doctrina.Camera;
import doctrina.Canvas;
import doctrina.ImageDrawer;
import doctrina.MovableEntity;
import homeAndSwordGame.GamePad;
import homeAndSwordGame.entities.Player;

import java.util.ArrayList;

public abstract class Scene {

   protected ArrayList<MovableEntity> entities = new ArrayList<>();
   protected Player player;
   protected GamePad gamePad;
   protected Camera camera;

   abstract public void initialize();
   abstract public void update();
   abstract public void draw(Canvas canvas);

   public void setBasic(Player player, GamePad gamePad, Camera camera){
      this.player = player;
      if (player.getFollower() != null){
         addPlayerFollower();
      }
      this.gamePad = gamePad;
      this.camera = camera;
   }

   private void addPlayerFollower(){
      ((MovableEntity) player.getFollower()).teleport(player.getX(),player.getY());
      entities.add((MovableEntity) player.getFollower());
      ImageDrawer.getInstance().addEntity((MovableEntity) player.getFollower());
   }
}

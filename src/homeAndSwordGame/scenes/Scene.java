package homeAndSwordGame.scenes;

import doctrina.Camera;
import doctrina.Canvas;
import homeAndSwordGame.GamePad;
import homeAndSwordGame.entities.Player;

public abstract class Scene {

   protected Player player;
   protected GamePad gamePad;
   protected Camera camera;

   abstract public void initialize();
   abstract public void update();
   abstract public void draw(Canvas canvas);

   public void setBasic(Player player,GamePad gamePad, Camera camera){
      this.player = player;
      this.gamePad = gamePad;
      this.camera = camera;
   }
}

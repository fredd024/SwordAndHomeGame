package doctrina;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.InputStream;

public class Canvas {

    private final Graphics2D graphics;
    private final Font font = getFont();

    public Canvas(Graphics2D graphics) {
        this.graphics = graphics;
    }

    public void drawRectangle(int x, int y, int width, int height, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillRect(x,y,width,height);
    }

    public void drawRectangle (StaticEntity entity, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillRect(entity.x, entity.y, entity.width, entity.height);
    }

    public void drawRectangle (Rectangle rectangle, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void drawCircle(int x, int y, int radius, Paint paint){
        graphics.setPaint(paint);
        graphics.fillOval(x,y,radius*2,radius*2);
    }

    public void drawString(String text, int x, int y, Paint paint){
        graphics.setPaint(paint);
        graphics.drawString(text,x,y);
    }

    public void drawFontString(String text, int x, int y, Paint paint){
        graphics.setPaint(paint);
        graphics.setFont(font);
        graphics.drawString(text,x,y);
    }

    public void drawImage(Image image , int x , int y) {
        graphics.drawImage(image, x , y , null);
    }

    public void translate(int x, int y) {
        graphics.translate(x,y);
    }

    public Rectangle getStringDimension(String text){

        FontMetrics fm = graphics.getFontMetrics(this.font);
        Rectangle2D bounds = fm.getStringBounds(text, graphics);
        int length = (int)bounds.getWidth();
        int height = (int)bounds.getHeight();

        return new Rectangle(length,height);
    }

    private Font getFont(){
        Font font = null;
        try (InputStream stream = Canvas.class.getResourceAsStream("/fonts/vinquerg.ttf")) {
            font = Font.createFont(Font.TRUETYPE_FONT, stream);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        font = font.deriveFont(10f);

        return font;
    }
}

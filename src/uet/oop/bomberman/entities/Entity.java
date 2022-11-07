package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.util.Constant;

public abstract class Entity implements Constant {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected double x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected double y;

    protected Image img;
    protected boolean remove = false;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(double xUnit, double yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public Entity() {

    }

    public boolean isRemove() {
        return remove;
    }

    public void setRemove(boolean remove) {
        this.remove = remove;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();

    public boolean intersect(Entity object) {
        return this.x < object.getX() + 32 && this.x + 32 > object.getX()
            && this.y < object.getY() + 32
            && this.y + 32 > object.getY();
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    protected void setImg(Image img) {
        this.img = img;
    }

    public int getXCanvas() {
        return (int)  (x + x + 32) / (Sprite.SCALED_SIZE * 2);
    }
    public int getYCanvas() {
        return (int) (y + y + 32) / (Sprite.SCALED_SIZE * 2);
    }

    public void setXCanvas(int xUnit) {
        this.x = xUnit * Sprite.SCALED_SIZE;
    }
    public void setYCanvas(int yUnit) {
        this.y = yUnit * Sprite.SCALED_SIZE;
    }
}

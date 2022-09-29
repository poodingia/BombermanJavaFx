package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Entity {

    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;
    protected int velocityX = 5;
    protected int velocityY = 5;
    protected Image img;
    protected int state;
    protected int previousState;
    protected int foot;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    public abstract void update();

    public void moveLeft() {
        changeFoot();
        this.previousState = state;
        this.state = states.LEFT.ordinal();
        this.x -= velocityX;
    }

    public void moveRight() {
        changeFoot();
        this.previousState = state;
        this.state = states.RIGHT.ordinal();
        this.x += velocityX;
    }

    public void moveUp() {
        changeFoot();
        this.previousState = state;
        this.state = states.UP.ordinal();
        this.y -= velocityY;
    }

    public void moveDown() {
        changeFoot();
        this.previousState = state;
        this.state = states.DOWN.ordinal();
        this.y += velocityY;
    }

    public void changeFoot() {

        foot = 1 - foot;

    }

    public enum states {
        RIGHT,
        LEFT,
        UP,
        DOWN
    }
}

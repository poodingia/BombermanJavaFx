package uet.oop.bomberman.entities.character;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.AnimatedEntity;

public abstract class Character extends AnimatedEntity {

    protected Board board;
    protected boolean alive = true;
    protected double speed;
    protected boolean moving = false;
    double velocityX = 0;
    double velocityY = 0;
    protected int direction = -1; // Đứng yên


    public Character(int xUnit, int yUnit, Image img, Board b) {
        super(xUnit, yUnit, img);
        board = b;
    }

    protected abstract void calculateMove();

    public abstract void kill();

    protected abstract void afterKill();

    protected abstract boolean canMove();

    void move() {
        this.x += velocityX;
        this.y += velocityY;
    }

    void resetVelocity() {
        this.velocityY = 0;
        this.velocityX = 0;
    }

    void moveBack() {
        this.x -= velocityX;
        this.y -= velocityY;
    }

}

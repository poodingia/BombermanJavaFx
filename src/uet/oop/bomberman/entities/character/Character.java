package uet.oop.bomberman.entities.character;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;

public abstract class Character extends AnimatedEntity {

    protected boolean alive = true;
    protected double speed = 1;
    protected boolean moving = false;
    protected double velocityX = 0;
    protected double velocityY = 0;
    protected int direction = -1; // Đứng yên


    public Character(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    protected abstract void calculateMove();

    public abstract void kill();

    protected abstract void afterKill();

    protected abstract boolean canMove();

    public void move() {
        this.x += velocityX;
        this.y += velocityY;
    }

    public void resetVelocity() {
        this.velocityY = 0;
        this.velocityX = 0;
    }

    public void moveBack() {
        this.x -= velocityX;
        this.y -= velocityY;
    }

}

package uet.oop.bomberman.entities.character;

import static uet.oop.bomberman.BombermanGame.mapObjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntitiy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Wall;


public abstract class Character extends AnimatedEntitiy {

    protected boolean alive = true;
    protected int hitBoxSize = 32;
    protected boolean moving = false;
    protected int velocityX = 0;
    protected int velocityY = 0;
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

    public boolean isCollide() {
        int column = this.x / 32;
        int row = this.y / 32;
        if (row <= 0) {
            row = 1;
        }
        if (column <= 0) {
            column = 1;
        }
        Entity object = null;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                object = mapObjects.get(i).get(j);
                if ( object instanceof Brick || object instanceof Wall) {
                    if (this.intersect(object)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void moveBack() {
        this.x -= velocityX;
        this.y -= velocityY;
    }

}

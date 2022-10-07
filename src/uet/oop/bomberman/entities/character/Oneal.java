package uet.oop.bomberman.entities.character;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class Oneal extends Character {

    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    protected void calculateMove() {

    }

    @Override
    public void kill() {

    }

    @Override
    protected void afterKill() {

    }

    @Override
    protected boolean canMove() {
        return false;
    }

    @Override
    public void update() {

    }

    @Override
    public boolean collide(Entity other) {
        return false;

    }
}

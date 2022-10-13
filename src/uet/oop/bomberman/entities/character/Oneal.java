package uet.oop.bomberman.entities.character;

import javafx.scene.image.Image;

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

}

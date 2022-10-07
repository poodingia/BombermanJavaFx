package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class Brick extends Entity {

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void update() {
    }

    @Override
    public boolean collide(Entity other) {
        return false;

    }
}

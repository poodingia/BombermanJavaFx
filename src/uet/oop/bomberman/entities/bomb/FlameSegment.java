package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntitiy;
import uet.oop.bomberman.entities.Entity;

public class FlameSegment extends AnimatedEntitiy {

    public FlameSegment(int xUnit, int yUnit, Image img) {
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

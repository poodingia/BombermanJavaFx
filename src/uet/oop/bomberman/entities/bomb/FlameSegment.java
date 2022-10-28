package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;

public class FlameSegment extends AnimatedEntity {
    private int direction;

    public FlameSegment(double xUnit, double yUnit, Image img, int direction) {
        super(xUnit, yUnit, img);
        this.direction = direction;
    }

    @Override
    public void update() {

    }

    public int getDirection() {
        return direction;
    }
}

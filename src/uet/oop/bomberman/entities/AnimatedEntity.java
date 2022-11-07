package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public abstract class AnimatedEntity extends Entity {

    private final int MAX_ANIMATE = 7500;
    protected int animate = 0;

    public AnimatedEntity(double xUnit, double yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    protected void animate() {
        if (animate < MAX_ANIMATE) {
            animate++;
        } else {
            animate = 0;
        }
    }

}

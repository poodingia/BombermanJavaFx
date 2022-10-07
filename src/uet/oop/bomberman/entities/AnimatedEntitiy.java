package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public abstract class AnimatedEntitiy extends Entity {

    protected final int MAX_ANIMATE = 7500;
    protected int animate = 0;

    public AnimatedEntitiy(int xUnit, int yUnit, Image img) {
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

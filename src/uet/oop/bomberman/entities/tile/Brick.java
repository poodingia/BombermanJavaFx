package uet.oop.bomberman.entities.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends AnimatedEntity {

    private int timeLeft = 0;
    private boolean collapsed = false;

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    @Override
    public void render(GraphicsContext gc) {
        chooseSprite();
        super.render(gc);
    }

    @Override
    public void update() {

    }

    public void collapse() {
        collapsed = true;
    }

    private void afterCollapse() {
        if (collapsed) {
            timeLeft--;
        }

        if (timeLeft == - 80) {
            remove = true;
        }
    }

    private void chooseSprite() {
        afterCollapse();
        if (collapsed) {
            if (timeLeft < 0) {
                img = Sprite.movingSprite(Sprite.brick_exploded1,
                    Sprite.brick_exploded2, animate, 40).getFxImage();
            }
        }

    }

}

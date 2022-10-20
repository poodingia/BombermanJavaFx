package uet.oop.bomberman.entities.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class BombBuff extends Entity {
    private boolean isConsumed = false;
    private Brick brick;

    public void setBrick(Brick brick) {
        this.brick = brick;
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
        brick.render(gc);
    }

    public BombBuff(int x, int y, Image img, Brick brick) {
        super(x, y, img);
        this.brick = brick;
    }

    @Override
    public void update() {

    }

}

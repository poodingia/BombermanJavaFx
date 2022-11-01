package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class FlameSegment extends AnimatedEntity {

    private int timeLeft = 0;
    private final int direction;

    public FlameSegment(double xUnit, double yUnit, Image img, int direction) {
        super(xUnit, yUnit, img);
        this.direction = direction;
    }

    @Override
    public void update() {
        chooseSprite();
    }

    public void chooseSprite() {
        timeLeft--;
        if (timeLeft == -24) {
            switch (direction) {
                case LEFT:
                case RIGHT:
                    setImg(Sprite.explosion_horizontal1.getFxImage());
                    break;
                case UP:
                case DOWN:
                    setImg(Sprite.explosion_vertical1.getFxImage());
                    break;
            }
        } else if (timeLeft == -48) {
            switch (direction) {
                case LEFT:
                case RIGHT:
                    setImg(Sprite.explosion_horizontal2.getFxImage());
                    break;
                case UP:
                case DOWN:
                    setImg(Sprite.explosion_vertical2.getFxImage());
                    break;
            }
        }
    }

    @Override
    public boolean intersect(Entity object) {
        return this.x < object.getX() + 30 && this.x + 30 > object.getX()
            && this.y < object.getY() + 30 && this.y + 30 > object.getY();
    }
}

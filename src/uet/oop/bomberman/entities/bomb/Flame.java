package uet.oop.bomberman.entities.bomb;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Bomb {


    List<FlameSegment> flameSegmentList = new ArrayList<>();

    public Flame(int xUnit, int yUnit, Image img, Bomber bomber) {
        super(xUnit, yUnit, img, bomber);
        flameSegmentList.add(new FlameSegment(x / Sprite.SCALED_SIZE - 1 , y / Sprite.SCALED_SIZE,
            Sprite.explosion_horizontal.getFxImage()));
        flameSegmentList.add(new FlameSegment(x / Sprite.SCALED_SIZE + 1, y / Sprite.SCALED_SIZE,
            Sprite.explosion_horizontal.getFxImage()));
        flameSegmentList.add(new FlameSegment(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE - 1,
            Sprite.explosion_vertical.getFxImage()));
        flameSegmentList.add(new FlameSegment(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE + 1,
            Sprite.explosion_vertical.getFxImage()));

    }

    @Override
    public void update() {
        explode();
        chooseSprite();
        animate();
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        for (FlameSegment flameSegment: flameSegmentList) {
            flameSegment.render(gc);
        }
    }

    @Override
    public void chooseSprite() {
        for (int i = 0; i < flameSegmentList.size(); i++) {
            if(timeLeft == 240) {
                switch (i) {
                    case LEFT:
                    case RIGHT:
                        flameSegmentList.get(i).setImg(Sprite.explosion_horizontal1.getFxImage());
                        break;
                    case UP:
                    case DOWN:
                        flameSegmentList.get(i).setImg(Sprite.explosion_vertical1.getFxImage());
                        break;
                }
            }
            else if(timeLeft == 0) {
                switch (i) {
                    case LEFT:
                    case RIGHT:
                        flameSegmentList.get(i).setImg(Sprite.explosion_horizontal2.getFxImage());
                        break;
                    case UP:
                    case DOWN:
                        flameSegmentList.get(i).setImg(Sprite.explosion_vertical2.getFxImage());
                        break;
                }
            }
        }
    }

    public void explode() {
        timeLeft--;
    }

}
package uet.oop.bomberman.entities.bomb;



import static uet.oop.bomberman.BombermanGame.entities;
import static uet.oop.bomberman.BombermanGame.mapObjects;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Bomb {


    private List<FlameSegment> flameSegmentList = new ArrayList<>();

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
        kill();
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
            if(timeLeft == -24) {
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
            else if(timeLeft == -48) {
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

    @Override
    public void kill() {
        for(Character character: entities) {
            for(FlameSegment flameSegment: flameSegmentList) {
                if(character.intersect(flameSegment)) {
                    character.kill();
                }
            }
        }
        for (FlameSegment flameSegment: flameSegmentList) {
            Entity entity = mapObjects.get(flameSegment.getYCanvas()).get(flameSegment.getXCanvas());
            if(entity instanceof Brick) {
                ((Brick) entity).collapse();
            }
        }
    }
}
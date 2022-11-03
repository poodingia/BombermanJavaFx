package uet.oop.bomberman.entities.bomb;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Buff;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.*;

public class Flame extends Bomb {

    private List<FlameSegment> flameSegmentList = new ArrayList<>();

    public Flame(double xUnit, double yUnit, Image img, Bomber bomber) {
        super(xUnit, yUnit, img, bomber);
        addFlameSegment();
    }

    @Override
    public void update() {
        kill();
        animate();
        chooseSprite();
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        for (FlameSegment flameSegment : flameSegmentList) {
            flameSegment.render(gc);
        }
    }

    @Override
    public void chooseSprite() {
        flameSegmentList.forEach(FlameSegment::chooseSprite);
    }

    @Override
    public void kill() {
        for (Character character : characters) {
            for (FlameSegment flameSegment : flameSegmentList) {
                if (character.intersect(flameSegment) || character.intersect(this)) {
                    character.kill();
                }
            }
        }
        for (FlameSegment flameSegment : flameSegmentList) {
            Entity entity = mapObjects.get(flameSegment.getYCanvas())
                .get(flameSegment.getXCanvas());
            if (entity instanceof Brick) {
                ((Brick) entity).collapse();
            }
            for(Entity e : ground) {
                if (e instanceof Buff
                        && e.intersect(flameSegment)
                        && mapObjects.get(e.getYCanvas())
                        .get(e.getXCanvas()) instanceof Grass){
                    ((Buff) e).spawnEnemy();
                    e.setRemove(true);
                }
            }
        }

        for(Bomb bomb : bombs) {
            if(!bomb.equals(this) && bomb.getTimeLeft() > 0) {
                for(FlameSegment flameSegment: flameSegmentList) {
                    if(flameSegment.intersect(bomb)) {
                        bomb.setTimeLeft(1);
                    }
                }
            }
        }
    }

    public void addFlameSegment() {
        for (int i = 1; i < bomber.getFlameLength(); i++) {
            flameSegmentList.add(
                new FlameSegment(x / Sprite.SCALED_SIZE - i, y / Sprite.SCALED_SIZE,
                    Sprite.explosion_horizontal.getFxImage(), LEFT));
            if (!(mapObjects.get(getYCanvas()).get(getXCanvas() - i) instanceof Grass)) {
                break;
            }
        }

        for (int i = 1; i < bomber.getFlameLength(); i++) {
            flameSegmentList.add(
                new FlameSegment(x / Sprite.SCALED_SIZE + i, y / Sprite.SCALED_SIZE,
                    Sprite.explosion_horizontal.getFxImage(), RIGHT));
            if (!(mapObjects.get(getYCanvas()).get(getXCanvas() + i) instanceof Grass)) {
                break;
            }
        }

        for (int i = 1; i < bomber.getFlameLength(); i++) {
            flameSegmentList.add(
                new FlameSegment(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE - i,
                    Sprite.explosion_vertical.getFxImage(), UP));
            if (!(mapObjects.get(getYCanvas() - i).get(getXCanvas()) instanceof Grass)) {
                break;
            }
        }

        for (int i = 1; i < bomber.getFlameLength(); i++) {
            flameSegmentList.add(
                new FlameSegment(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE + i,
                    Sprite.explosion_vertical.getFxImage(), DOWN));
            if (!(mapObjects.get(getYCanvas() + i).get(getXCanvas()) instanceof Grass)) {
                break;
            }
        }
    }

}
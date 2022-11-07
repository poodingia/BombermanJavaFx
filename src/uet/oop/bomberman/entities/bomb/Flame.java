package uet.oop.bomberman.entities.bomb;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Buff;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Bomb {

    private List<FlameSegment> flameSegmentList = new ArrayList<>();

    public Flame(double xUnit, double yUnit, Image img, Bomber bomber, Board b) {
        super(xUnit, yUnit, img, bomber, b);
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
        for (Character character : board.characters) {
            for (FlameSegment flameSegment : flameSegmentList) {
                if (flameSegment.intersect(character) || character.intersect(this)) {
                    character.kill();
                }
            }
        }
        for (FlameSegment flameSegment : flameSegmentList) {
            Entity entity = board.mapObjects.get(flameSegment.getYCanvas())
                .get(flameSegment.getXCanvas());
            if (entity instanceof Brick) {
                ((Brick) entity).collapse();
            }
            for(Entity e : board.ground) {
                if (e instanceof Buff
                        && e.intersect(flameSegment)
                        && board.mapObjects.get(e.getYCanvas())
                        .get(e.getXCanvas()) instanceof Grass){
                    ((Buff) e).spawnEnemy();
                    e.setRemove(true);
                }
            }
        }

        for(Bomb bomb : board.bombs) {
            if(!bomb.equals(this) && bomb.getTimeLeft() > 0) {
                for(FlameSegment flameSegment: flameSegmentList) {
                    if(flameSegment.intersect(bomb)) {
                        bomb.setTimeLeft(1);
                    }
                }
            }
        }
    }

    private void addFlameSegment() {
        for (int i = 1; i < bomber.getFlameLength(); i++) {
            flameSegmentList.add(
                new FlameSegment(x / Sprite.SCALED_SIZE - i, y / Sprite.SCALED_SIZE,
                    Sprite.explosion_horizontal.getFxImage(), LEFT));
            if (!(board.mapObjects.get(getYCanvas()).get(getXCanvas() - i) instanceof Grass)) {
                break;
            }
        }

        for (int i = 1; i < bomber.getFlameLength(); i++) {
            flameSegmentList.add(
                new FlameSegment(x / Sprite.SCALED_SIZE + i, y / Sprite.SCALED_SIZE,
                    Sprite.explosion_horizontal.getFxImage(), RIGHT));
            if (!(board.mapObjects.get(getYCanvas()).get(getXCanvas() + i) instanceof Grass)) {
                break;
            }
        }

        for (int i = 1; i < bomber.getFlameLength(); i++) {
            flameSegmentList.add(
                new FlameSegment(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE - i,
                    Sprite.explosion_vertical.getFxImage(), UP));
            if (!(board.mapObjects.get(getYCanvas() - i).get(getXCanvas()) instanceof Grass)) {
                break;
            }
        }

        for (int i = 1; i < bomber.getFlameLength(); i++) {
            flameSegmentList.add(
                new FlameSegment(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE + i,
                    Sprite.explosion_vertical.getFxImage(), DOWN));
            if (!(board.mapObjects.get(getYCanvas() + i).get(getXCanvas()) instanceof Grass)) {
                break;
            }
        }
    }

}
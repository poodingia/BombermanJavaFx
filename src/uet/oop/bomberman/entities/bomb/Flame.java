package uet.oop.bomberman.entities.bomb;


import static uet.oop.bomberman.BombermanGame.characters;
import static uet.oop.bomberman.BombermanGame.mapObjects;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.graphics.Sprite;

public class Flame extends Bomb {


    private int timeLeft = 0;
    private List<FlameSegment> flameSegmentList = new ArrayList<>();

    public Flame(int xUnit, int yUnit, Image img, Bomber bomber) {
        super(xUnit, yUnit, img, bomber);
        addFlameSegment();
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
        for (FlameSegment flameSegment : flameSegmentList) {
            flameSegment.render(gc);
        }
    }

    @Override
    public void chooseSprite() {
        for (FlameSegment flameSegment : flameSegmentList) {
            if (timeLeft == -24) {
                switch (flameSegment.getDirection()) {
                    case LEFT:
                    case RIGHT:
                        flameSegment.setImg(Sprite.explosion_horizontal1.getFxImage());
                        break;
                    case UP:
                    case DOWN:
                        flameSegment.setImg(Sprite.explosion_vertical1.getFxImage());
                        break;
                }
            } else if (timeLeft == -48) {
                switch (flameSegment.getDirection()) {
                    case LEFT:
                    case RIGHT:
                        flameSegment.setImg(Sprite.explosion_horizontal2.getFxImage());
                        break;
                    case UP:
                    case DOWN:
                        flameSegment.setImg(Sprite.explosion_vertical2.getFxImage());
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
        for (Character character : characters) {
            for (FlameSegment flameSegment : flameSegmentList) {
                if (character.intersect(flameSegment)) {
                    character.kill();
                }
            }
        }
        for (FlameSegment flameSegment : flameSegmentList) {
            Entity entity = mapObjects.get(flameSegment.getY() / 32)
                .get(flameSegment.getX() / 32);
            if (entity instanceof Brick) {
                ((Brick) entity).collapse();
            }
        }
    }

    public void addFlameSegment() {
        for (int i = 1; i < bomber.getFlameLength(); i++) {
            if (!(mapObjects.get(getYCanvas()).get(getXCanvas() - i) instanceof Grass)) {
                flameSegmentList.add(
                    new FlameSegment(x / Sprite.SCALED_SIZE - i, y / Sprite.SCALED_SIZE,
                        Sprite.explosion_horizontal.getFxImage(), LEFT));
                break;
            }
            flameSegmentList.add(
                new FlameSegment(x / Sprite.SCALED_SIZE - i, y / Sprite.SCALED_SIZE,
                    Sprite.explosion_horizontal.getFxImage(), LEFT));
        }

        for (int i = 1; i < bomber.getFlameLength(); i++) {
            if (!(mapObjects.get(getYCanvas()).get(getXCanvas() + i) instanceof Grass)) {
                flameSegmentList.add(
                    new FlameSegment(x / Sprite.SCALED_SIZE + i, y / Sprite.SCALED_SIZE,
                        Sprite.explosion_horizontal.getFxImage(), RIGHT));
                break;
            }
            flameSegmentList.add(
                new FlameSegment(x / Sprite.SCALED_SIZE + i, y / Sprite.SCALED_SIZE,
                    Sprite.explosion_horizontal.getFxImage(), RIGHT));
        }

        for (int i = 1; i < bomber.getFlameLength(); i++) {
            if (!(mapObjects.get(getYCanvas() - i).get(getXCanvas()) instanceof Grass)) {
                flameSegmentList.add(
                    new FlameSegment(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE - i,
                        Sprite.explosion_vertical.getFxImage(), UP));
                break;
            }
            flameSegmentList.add(
                new FlameSegment(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE - i,
                    Sprite.explosion_vertical.getFxImage(), UP));
        }

        for (int i = 1; i < bomber.getFlameLength(); i++) {
            if (!(mapObjects.get(getYCanvas() + i).get(getXCanvas()) instanceof Grass)) {
                flameSegmentList.add(
                    new FlameSegment(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE + i,
                        Sprite.explosion_vertical.getFxImage(), DOWN));
                break;
            }
            flameSegmentList.add(
                new FlameSegment(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE + i,
                    Sprite.explosion_vertical.getFxImage(), DOWN));
        }
    }

}
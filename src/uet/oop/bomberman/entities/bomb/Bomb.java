package uet.oop.bomberman.entities.bomb;

import static uet.oop.bomberman.BombermanGame.mapObjects;

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.mediaPlayer;

public class Bomb extends AnimatedEntity {

    protected Bomber bomber;
    protected int timeLeft = 240;
    private Flame flame = null;
    private boolean harmful = false;
    mediaPlayer explodeSound = new mediaPlayer("res/sounds/explode.wav");
    mediaPlayer bombPlant = new mediaPlayer("res/sounds/plantingBomb.wav");

    public Bomb(double xUnit, double yUnit, Image img, Bomber bomber) {
        super(xUnit, yUnit, img);
        this.bomber = bomber;
    }


    @Override
    public void update() {
        isHarmful();
        chooseSprite();
        countDown();
        animate();
        if (flame != null) {
            flame.update();
        }
    }

    public Bomber getBomber() {
        return bomber;
    }

    public void setBomber(Bomber bomber) {
        this.bomber = bomber;
    }

    public void countDown() {
        bombPlant.play();
        timeLeft--;
        explode();
    }

    public void explode() {
        if (timeLeft == 0) {
            flame = new Flame((int)x / Sprite.SCALED_SIZE, (int)y / Sprite.SCALED_SIZE, null, bomber);
            img = Sprite.bomb_exploded.getFxImage();
            explodeSound.play();
        } else if (timeLeft == -24) {
            img = Sprite.bomb_exploded1.getFxImage();
        } else if (timeLeft == -48) {
            img = Sprite.bomb_exploded2.getFxImage();
        } else if (timeLeft == -60) {
            remove = true;
            flame.remove = true;
        }
    }

    public void chooseSprite() {
        if (timeLeft > 0) {
            img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 60)
                .getFxImage();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        if (timeLeft <= 0 && flame != null) {
            flame.render(gc);
        }
    }

    public void kill() {
        flame.kill();
    }

    public boolean isHarmful() {
        if (!bomber.intersect(this)) {
            harmful = true;
        }
        return harmful;
    }

    public void printMap() {
        for (ArrayList<Entity> arrayList : mapObjects) {
            for (Entity entity : arrayList) {
                if (entity instanceof Brick) {
                    System.out.print('x');
                } else if (entity instanceof Wall) {
                    System.out.print('#');
                } else if (entity instanceof Grass) {
                    System.out.print(' ');
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getTimeLeft() {
        return timeLeft;
    }
}

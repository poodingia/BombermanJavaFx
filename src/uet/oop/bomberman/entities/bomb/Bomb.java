package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends AnimatedEntity {

    private Bomber bomber;
    private Flame flame = null;

    protected int timeLeft = 240;

    public Bomb(int xUnit, int yUnit, Image img, Bomber bomber) {
        super(xUnit, yUnit, img);
        this.bomber = bomber;
    }


    @Override
    public void update() {
        chooseSprite();
        countDown();
        animate();
        if(flame != null) {
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
        timeLeft--;
        explode();
    }

    public void explode() {
        if (timeLeft == 0) {
            flame = new Flame(x / Sprite.SCALED_SIZE, y / Sprite.SCALED_SIZE, null, bomber);
            img = Sprite.bomb_exploded.getFxImage();
        } else if (timeLeft == -240) {
            img = Sprite.bomb_exploded1.getFxImage();
        } else if (timeLeft == -480) {
            img = Sprite.bomb_exploded2.getFxImage();
        } else if (timeLeft == -600) {
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
        if (timeLeft <= 0) {
            flame.render(gc);
        }
    }
}

package uet.oop.bomberman.entities.bomb;

import java.util.ArrayList;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends AnimatedEntity {
    private Bomber bomber;
    private Flame flame;

    private int timeLeft = 240;
    public Bomb(int xUnit, int yUnit, Image img, Bomber bomber) {
        super(xUnit, yUnit, img);
        this.bomber = bomber;
    }


    @Override
    public void update() {
        chooseSprite();
        countDown();
        animate();
    }

    public Bomber getBomber() {
        return bomber;
    }

    public void setBomber(Bomber bomber) {
        this.bomber = bomber;
    }

    public void countDown() {
        timeLeft --;
        explode();
    }

    public void explode() {
        if(timeLeft <= 0) {
            img = Sprite.bomb_exploded1.getFxImage();

        }
    }

    public void chooseSprite() {
        img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 60)
            .getFxImage();
    }




}

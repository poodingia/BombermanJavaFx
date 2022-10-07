package uet.oop.bomberman.entities.bomb;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntitiy;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Bomb extends AnimatedEntitiy {
    Bomber bomber;

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

    @Override
    public boolean collide(Entity other) {
        return false;
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

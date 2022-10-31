package uet.oop.bomberman.entities.character.enemy;

import static uet.oop.bomberman.BombermanGame.characters;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.AI.AIHigh;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        this.points = 200;
        this.speed = 0.5;
        this.deadImage = Sprite.oneal_dead.getFxImage();
        ai = new AIHigh((Bomber) characters.get(0), this);
        this.direction = -1;
    }

    @Override
    protected void chooseSprite() {
        switch (direction) {
            case LEFT:
                img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2,
                    Sprite.oneal_left3, animate, 40).getFxImage();
                break;
            case RIGHT:
                img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2,
                    Sprite.oneal_right3, animate, 40).getFxImage();
                break;
            default:
                break;
        }
    }
}
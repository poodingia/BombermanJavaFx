package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.character.enemy.AI.AILow;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Enemy {
    public Balloom (int x, int y, Image img, Board b) {
        super(x, y, img , b);
        this.points = 100;
        this.speed = 0.5;
        this.deadImage = Sprite.balloom_dead.getFxImage();
        ai = new AILow();
        direction = ai.calculateDirection();
    }

    protected void chooseSprite() {
        switch (direction) {
            case LEFT:
                img = Sprite.movingSprite(
                    Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3,
                    animate, 40).getFxImage();
                break;
            case RIGHT:
                img = Sprite.movingSprite(
                    Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3,
                    animate, 40).getFxImage();
                break;
            default:
                break;
        }
    }
}

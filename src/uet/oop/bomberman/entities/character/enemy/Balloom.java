package uet.oop.bomberman.entities.character.enemy;

import uet.oop.bomberman.entities.character.enemy.AI.AILow;
import uet.oop.bomberman.graphics.Sprite;

public class Balloom extends Enemy {
    public Balloom (int x, int y) {
        super(x, y, Sprite.balloom_left1.getFxImage(), Sprite.balloom_dead.getFxImage(), 100, 0.5);

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

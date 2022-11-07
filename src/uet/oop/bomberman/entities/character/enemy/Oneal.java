package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.AI.AIHigh;
import uet.oop.bomberman.graphics.Sprite;

public class Oneal extends Enemy {
    public Oneal(int x, int y, Image img, Board b) {
        super(x, y, img, b);
        this.points = 200;
        this.speed = 0.5;
        this.deadImage = Sprite.oneal_dead.getFxImage();
        ai = new AIHigh((Bomber) board.characters.get(0), this, board);
        this.direction = 1;
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

    @Override
    public void update() {
        if (this.getX() == this.getXCanvas() * Sprite.SCALED_SIZE
                && this.getY() == this.getYCanvas() * Sprite.SCALED_SIZE) {
            if (((AIHigh)ai).isChasing()) speed = 1;
            else speed = 0.5;
        }
        super.update();
    }
}
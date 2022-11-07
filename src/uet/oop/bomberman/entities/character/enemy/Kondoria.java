package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.AI.AIHigher;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class Kondoria extends Enemy {

    public Kondoria(int x, int y, Image img, Board b) {
        super(x, y, img, b);
        this.points = 1000;
        this.speed = 0.25;
        this.deadImage = Sprite.kondoria_dead.getFxImage();
        ai = new AIHigher((Bomber) board.characters.get(0), this, board);
        direction = ai.calculateDirection();
    }

    protected void chooseSprite() {
        switch (direction) {
            case LEFT:
                img = Sprite.movingSprite(
                    Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3,
                    animate, 40).getFxImage();
                break;
            case RIGHT:
                img = Sprite.movingSprite(
                    Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3,
                    animate, 40).getFxImage();
                break;
            default:
                break;
        }
    }
    @Override
    public int collide(Entity entity) {
        if(entity instanceof Wall) {
            return FREEZE;
        }
        return MOVE;
    }
}
package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.AI.AIHigher;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

//Spawn when timer runs out
public class Pontan extends Enemy {
    public Pontan (int x, int y, Image img, Board b) {
        super(x, y, img, b);
        this.points = 10000;
        this.speed = 2;
        Random rand = new Random();
        if (rand.nextInt(2) == 1) {
            this.deadImage = Sprite.pontan_dead_1.getFxImage();
        } else  this.deadImage = Sprite.pontan_dead_2.getFxImage();
        
        ai = new AIHigher((Bomber) board.characters.get(0), this, board);
        direction = ai.calculateDirection();
    }

    protected void chooseSprite() {
        switch (direction) {
            case RIGHT:

            case UP:
                img = Sprite.movingSprite(
                        Sprite.pontan_right1, Sprite.pontan_right2, Sprite.pontan_right3,
                        Sprite.pontan_right4, Sprite.pontan_right5, Sprite.pontan_right6,
                        animate, 40).getFxImage();
                break;

            default:
                img = Sprite.movingSprite(
                    Sprite.pontan_left1, Sprite.pontan_left2, Sprite.pontan_left3,
                    Sprite.pontan_left4, Sprite.pontan_left5, Sprite.pontan_left6,
                    animate, 40).getFxImage();
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

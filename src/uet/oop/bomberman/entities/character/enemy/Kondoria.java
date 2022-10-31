package uet.oop.bomberman.entities.character.enemy;

import static uet.oop.bomberman.BombermanGame.bombs;
import static uet.oop.bomberman.BombermanGame.mapObjects;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.enemy.AI.AILow;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class Kondoria extends Enemy {

    public Kondoria (int x, int y, Image img) {
        super(x, y, img);
        this.points = 100;
        this.speed = 0.5;
        this.deadImage = Sprite.kondoria_dead.getFxImage();
        ai = new AILow();
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
    protected boolean collide(Entity e) {
        return !(e instanceof Wall) && !(e instanceof Bomb);
    }

    @Override
        protected boolean canMove() {
            int column = this.getXCanvas();
            int row = this.getYCanvas();

            Entity object = null;
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = column - 1; j <= column + 1; j++) {
                    object = mapObjects.get(i).get(j);
                    if (object instanceof Wall) {
                        if (this.intersect(object)) {
                            return false;
                        }
                    }
                }
            }

        for(Bomb bomb : bombs) {
            if (this.intersect(bomb)) {
                return false;
            }
        }
            return true;
        }
    }
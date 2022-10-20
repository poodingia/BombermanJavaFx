package uet.oop.bomberman.entities.character;

import static uet.oop.bomberman.BombermanGame.bombs;
import static uet.oop.bomberman.BombermanGame.keyCodeList;
import static uet.oop.bomberman.BombermanGame.mapObjects;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;


public class Bomber extends Character {

    private int bombLeft = 100;
    private int timeAfter = 100;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    protected void calculateMove() {
        if (keyCodeList.size() >= 1) {
            resetVelocity();
            KeyCode keyCode = keyCodeList.lastElement();
            if (keyCode == KeyCode.LEFT) {
                direction = LEFT;
                velocityX = -1;
            } else if (keyCode == KeyCode.RIGHT) {
                direction = RIGHT;
                velocityX = 1;
            } else if (keyCode == KeyCode.UP) {
                direction = UP;
                velocityY = -1;
            } else if (keyCode == KeyCode.DOWN) {
                direction = DOWN;
                velocityY = 1;
            }
            moving = velocityY != 0 || velocityX != 0;
            move();
            if (canMove()) {
                moveBack();
            }
        }
    }

    public void handle() {
        if (keyCodeList.size() > 0) {
            if (keyCodeList.lastElement().isArrowKey()) {
                calculateMove();
            } else if (keyCodeList.lastElement() == KeyCode.SPACE) {
                placeBomb();
            }
        }
    }

    @Override
    public void kill() {
        alive = false;
    }

    @Override
    protected void afterKill() {
        if (!alive) {
            timeAfter--;
        }
        if (timeAfter <= 0) {
            remove = true;
        }
    }

    @Override
    public boolean canMove() {
        int column = (this.x + this.x + 32) / 64;
        int row = (this.y + this.y + 32) / 64;
        if (row <= 0) {
            row = 1;
        }
        if (column <= 0) {
            column = 1;
        }
        Entity object = null;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                object = mapObjects.get(i).get(j);
                if (object instanceof Brick || object instanceof Wall) {
                    if (this.intersect(object)) {
                        slide(object);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void update() {
        handle();
        chooseSprite();
        animate();
        stay();
        afterKill();
    }

    private void chooseSprite() {
        if (!alive) {
            img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3,
                animate, 40).getFxImage();
            return;
        }
        switch (direction) {
            case UP:
                img = Sprite.player_up.getFxImage();
                if (moving) {
                    img = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 40)
                        .getFxImage();

                }
                break;
            case DOWN:
                img = Sprite.player_down.getFxImage();
                if (moving) {
                    img = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate,
                            40)
                        .getFxImage();
                }
                break;
            case LEFT:
                img = Sprite.player_left.getFxImage();
                if (moving) {
                    img = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate,
                            40)
                        .getFxImage();
                }
                break;
            default:
                img = Sprite.player_right.getFxImage();
                if (moving) {
                    img = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2,
                        animate, 40).getFxImage();
                }
                break;
        }
    }

    public void stay() {
        moving = false;
    }

    public void placeBomb() {
        if (keyCodeList.size() >= 1 && bombLeft > 0) {
            if (keyCodeList.lastElement() == KeyCode.SPACE) {
                Bomb bomb = new Bomb((x + 14) / 32, (y + 16) / 32, Sprite.bomb.getFxImage(), this);
                bombs.add(bomb);
                bombLeft--;
                keyCodeList.pop();
            }
        }
    }

    public void slide(Entity obstacle) {
        Bomber e = new Bomber(0, 0, Sprite.oneal_right1.getFxImage());
        e.setX(this.x);
        e.setY(this.y);
        switch (direction) {
            case LEFT:
            case RIGHT:
                for (int i = 0; i < 16; i++) {
                    e.setY(this.y + i);
                    if (!e.intersect(obstacle)) {

                        this.setY(e.getY());
                        return;
                    }
                    e.setY(this.y - i);
                    if (!e.intersect(obstacle)) {

                        this.setY(e.getY());
                        return;
                    }
                }
            case UP:
            case DOWN:
                for (int i = 0; i < 16; i++) {
                    e.setX(this.x + i);
                    if (!e.intersect(obstacle)) {

                        this.setX(e.getX());
                        return;
                    }
                    e.setX(this.x - i);
                    if (!e.intersect(obstacle)) {

                        this.setX(e.getX());
                        return;
                    }
                }
        }
    }

}

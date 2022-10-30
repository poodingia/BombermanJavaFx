package uet.oop.bomberman.entities.character;

import static uet.oop.bomberman.BombermanGame.bombs;
import static uet.oop.bomberman.BombermanGame.characters;
import static uet.oop.bomberman.BombermanGame.ground;
import static uet.oop.bomberman.BombermanGame.keyCodeList;
import static uet.oop.bomberman.BombermanGame.mapObjects;
import static uet.oop.bomberman.BombermanGame.result;
import static uet.oop.bomberman.BombermanGame.state;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.tile.BombBuff;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Buff;
import uet.oop.bomberman.entities.tile.FlameBuff;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.SpeedBuff;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;


public class Bomber extends Character {

    private int bombLeft = 1;
    private int timeAfter = 100;

    private int flameLength = 2;

    public Bomber(int x, int y, Image img) {
        super(x, y, img);
        speed = 1;
    }

    @Override
    protected void calculateMove() {
        if (!alive) {
            return;
        }
        if (keyCodeList.size() >= 1) {
            resetVelocity();
            KeyCode keyCode = keyCodeList.lastElement();
            if (keyCode == KeyCode.LEFT) {
                direction = LEFT;
                velocityX = -speed;
            } else if (keyCode == KeyCode.RIGHT) {
                direction = RIGHT;
                velocityX = speed;
            } else if (keyCode == KeyCode.UP) {
                direction = UP;
                velocityY = -speed;
            } else if (keyCode == KeyCode.DOWN) {
                direction = DOWN;
                velocityY = speed;
            }
            moving = velocityY != 0 || velocityX != 0;
            move();
            if (!canMove()) {
                moveBack();
            }
        }
    }

    @Override
    public void kill() {
        alive = true;
    }

    @Override
    protected void afterKill() {
        if (!alive) {
            timeAfter--;
        }
        if (timeAfter <= 0) {
            remove = true;
            state = GAME_OVER;
            result = LOST;
        }
    }

    @Override
    public boolean canMove() {
        int column = getXCanvas();
        int row = getYCanvas();
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
                        return false;
                    }
                }
            }
        }
        for (Bomb bomb : bombs) {
            if (this.intersect(bomb) && bomb.isHarmful()) {
                slide(object);
                return false;
            }
        }
        return true;
    }

    @Override
    public void update() {
        calculateMove();
        placeBomb();
        powerUp();
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
        if (keyCodeList.size() >= 1 && bombs.size() < 1) {
            if (keyCodeList.lastElement() == KeyCode.SPACE) {
                Bomb bomb = new Bomb(this.getXCanvas(), this.getYCanvas(), Sprite.bomb.getFxImage(),
                    this);
                bombs.add(bomb);
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
                for (int i = 0; i < 10; i++) {
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
                for (int i = 0; i < 10; i++) {
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

    public void powerUp() {
        for (Entity entity : ground) {
            if (entity instanceof Buff || entity instanceof Portal) {
                if (this.intersect(entity)) {
                    if (entity instanceof BombBuff) {
                        bombLeft++;
                    } else if (entity instanceof FlameBuff) {
                        flameLength++;
                    } else if (entity instanceof SpeedBuff) {
                        speed += 0.25;
                    } else if (entity instanceof Portal) {
                        if (characters.size() == 1) {
                            state = GAME_OVER;
                            result = WON;
                            return;
                        }
                        moveBack();
                        return;
                    }
                    entity.setRemove(true);
                }
            }
        }
    }

    public int getFlameLength() {
        return flameLength;
    }

    public boolean intersect(Entity object) {
        return this.x < object.getX() + 32 && this.x + 32 - 8 > object.getX()
            && this.y < object.getY() + 32
            && this.y + 32 > object.getY();
    }
}

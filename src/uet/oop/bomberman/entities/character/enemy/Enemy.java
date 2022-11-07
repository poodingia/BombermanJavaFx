package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.AI.AI;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends Character {

    private int invulnerableFrame = 120;
    int points;
    AI ai;


    private int timeAfter = 30;
    private int deathAnimation = 30;
    Image deadImage;

    Enemy(int x, int y, Image img, Board b) {
        super(x, y, img, b);
    }

    public void update() {
        if (invulnerableFrame > 0) invulnerableFrame--;
        chooseSprite();
        animate();

        if (!alive) {
            afterKill();
        } else {
            calculateMove();
            checkCollisionWithBomber();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (alive) {
            chooseSprite();
        } else {
            if (timeAfter > 0) {
                img = deadImage;
                animate = 0;
            } else {
                img = Sprite.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3,
                    animate, 40).getFxImage();
            }
        }

        gc.drawImage(img, x, y);
    }

    protected abstract void chooseSprite();

    protected void calculateMove() {
        if (this.getX() == this.getXCanvas() * Sprite.SCALED_SIZE
                && this.getY() == this.getYCanvas() * Sprite.SCALED_SIZE) {
            direction = ai.calculateDirection();
        }
        if (!alive) {
            return;
        }
        int xa = 0, ya = 0;

        if (direction == UP) {
            ya--;
        } else if (direction == DOWN) {
            ya++;
        } else if (direction == LEFT) {
            xa--;
        } else if (direction == RIGHT) {
            xa++;
        }
        moving = xa != 0 || ya != 0;
        move(xa * speed, ya * speed);
        if (!canMove()) {
            direction = ai.calculateDirection();
            moveBack(xa * speed, ya * speed);
        }
    }

    @Override
    protected boolean canMove() {
        int column = this.getXCanvas();
        int row = this.getYCanvas();

        Entity object;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                object = board.mapObjects.get(i).get(j);
                if(this.intersect(object)) {
                    if(this.collide(object) == FREEZE) {
                        return false;
                    }
                }
            }
        }

        for (Bomb bomb : board.bombs) {
            if (this.intersect(bomb)) {
                return false;
            }
        }
        return true;
    }


    private void move(double xa, double ya) {
        y += ya;
        x += xa;
    }

    private void moveBack(double xa, double ya) {
        y -= ya;
        x -= xa;
    }

    public void kill() {
        if (invulnerableFrame <= 0) alive = false;
        // add point to score
        // play music
    }

    @Override
    protected void afterKill() {
        if (timeAfter > 0) {
            --timeAfter;
        } else {
            if (deathAnimation > 0) {
                --deathAnimation;
            } else {
                remove = true;
                board.setScore(board.getScore() + points);
            }
        }
    }

    private void checkCollisionWithBomber() {
        for (Character c : board.characters) {
            if (c instanceof Bomber && c.intersect(this)) {
                c.kill();
            }
        }
    }

    int collide(Entity entity) {
        if(entity instanceof Wall) {
            return FREEZE;
        } else if (entity instanceof Brick) {
           return FREEZE;
        }
        return MOVE;
    }

}

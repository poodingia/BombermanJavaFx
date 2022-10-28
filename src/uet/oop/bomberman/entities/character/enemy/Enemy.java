package uet.oop.bomberman.entities.character.enemy;

import static uet.oop.bomberman.BombermanGame.characters;
import static uet.oop.bomberman.BombermanGame.getBomb;
import static uet.oop.bomberman.BombermanGame.mapObjects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.AI.AI;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Enemy extends Character {
    protected int points;
    protected AI ai;
    protected final double MAX_STEPS;
    protected final double rest;
    protected double steps;

    protected int timeAfter = 30;
    protected int deathAnimation = 30;
    protected Image deadImage;

    public Enemy(int x, int y, Image img) {
        super(x, y, img);
        MAX_STEPS = Sprite.SCALED_SIZE / speed;
        steps = MAX_STEPS;
        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
    }

    public void update() {
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
                img = Sprite.movingSprite(Sprite.mob_dead1,
                    Sprite.mob_dead2, Sprite.mob_dead3, animate, 40).getFxImage();
            }
        }

        gc.drawImage(img, x, y);
    }

    protected abstract void chooseSprite();

    @Override
    protected void calculateMove() {
        int xa = 0, ya = 0;
        if (steps <= 0) {
            direction = ai.calculateDirection();
            steps = MAX_STEPS;
        }

        if (direction == UP) ya--;
        if (direction == DOWN) ya++;
        if (direction == LEFT) xa--;
        if (direction == RIGHT) xa++;

        if (canMove()) {
            steps -= 1 + rest;
            move(xa * speed, ya * speed);
            moving = true;
        } else {
            steps = 0;
            moving = false;
        }
    }

    @Override
    protected boolean canMove() {
        int column = this.getXCanvas();
        int row = this.getYCanvas();
        if (row <= 0) {
            row = 1;
        }
        if (column <= 0) {
            column = 1;
        }

        switch (direction) {
            case UP:
                row--;
                break;
            case DOWN:
                row++;
                break;
            case LEFT:
                column--;
                break;
            case RIGHT:
                column++;
                break;
        }
        Entity object;
        object = mapObjects.get(row).get(column);
        if (this.intersect(object)) return this.collide(object);

        object = getBomb(row, column);
        if (object != null) {
            if (this.intersect(object)) {
                return this.collide(object);
            }
        }

        return true;
    }


    public void move(double xa, double ya) {
        if (!alive) return;
        y += ya;
        x += xa;
    }

    public void kill() {
        alive = false;
        // add point to score
        // play music
    }

    @Override
    protected void afterKill() {
        if (timeAfter > 0) --timeAfter;
        else {
            if (deathAnimation > 0) --deathAnimation;
            else {
                remove = true;
            }
        }
    }

    protected boolean collide(Entity e) {
        return !(e instanceof Wall) && !(e instanceof Brick) && !(e instanceof Bomb);
    }

    protected void checkCollisionWithBomber() {
        for (Character c: characters) {
            if (c instanceof Bomber && c.intersect(this)) {
                c.kill();
            }
        }
    }

    @Override
    public boolean intersect(Entity object) {
        return (int) this.x < (int) object.getX() + 32 && (int) this.x + 32 > (int) object.getX()
            && (int) this.y < (int) object.getY() + 32
            && (int) this.y + 32 > (int) object.getY();
    }
}

package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.AI.AI;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.*;

public abstract class Enemy extends Character {
    protected int points;
    protected AI ai;
    protected final double MAX_STEPS;
//    protected final double rest;
    protected double steps;

    protected int timeAfter = 30;
    protected int deathAnimation = 30;
    protected Image deadImage;

    public Enemy(int x, int y, Image img, Image dead, int points, double speed) {
        super(x, y, img);

        this.points = points;
        this.speed = speed;
        this.deadImage = dead;

        MAX_STEPS = Sprite.SCALED_SIZE / speed;
//        rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        steps = MAX_STEPS;
    }

    public void update() {
        chooseSprite();
        animate();

        if (!alive) {
            afterKill();
        } else {
            calculateMove();
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
            steps -= 1;
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
        if (!(object instanceof Grass) && this.intersect(object)) return false;

        if (getBomb(row, column) != null) {
            object = getBomb(row, column);
            if (this.intersect(object)) {
                return false;
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
}

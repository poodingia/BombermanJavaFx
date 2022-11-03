package uet.oop.bomberman.entities.character.enemy;

import static uet.oop.bomberman.BombermanGame.bombs;
import static uet.oop.bomberman.BombermanGame.characters;
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

    protected int invulnerableFrame = 48;
    protected final double MAX_STEPS;
    protected double steps;
    protected int points;
    protected AI ai;


    protected int timeAfter = 30;
    protected int deathAnimation = 30;
    protected Image deadImage;

    public Enemy(int x, int y, Image img) {
        super(x, y, img);
        MAX_STEPS = Sprite.SCALED_SIZE / speed;
        steps = MAX_STEPS;
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

    @Override
    protected void calculateMove() {
        if (!alive) {
            return;
        }
        int xa = 0, ya = 0;
        if (steps <= 0 || direction == -1) {
            direction = ai.calculateDirection();
            steps = MAX_STEPS;
        }

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
        steps -= 1;
        if (!canMove()) {
            steps = 0;
            moveBack(xa * speed, ya * speed);
        }
    }

    @Override
    protected boolean canMove() {
        int column = this.getXCanvas();
        int row = this.getYCanvas();

        Entity object = null;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                object = mapObjects.get(i).get(j);
                if (object instanceof Brick || object instanceof Wall) {
                    if (this.intersect(object)) {
                        return false;
                    }
                }
            }
        }

        for (Bomb bomb : bombs) {
            if (this.intersect(bomb)) {
                return false;
            }
        }

        for(Character character: characters) {
            if(this.intersect(character) && character instanceof Enemy && !this.equals(character)) {
                return false;
            }
        }
        return true;
    }


    public void move(double xa, double ya) {
        y += ya;
        x += xa;
    }

    public void moveBack(double xa, double ya) {
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
            }
        }
    }

    protected void checkCollisionWithBomber() {
        for (Character c : characters) {
            if (c instanceof Bomber && c.intersect(this)) {
                c.kill();
            }
        }
    }

}

package uet.oop.bomberman.entities.character.enemy.AI;

import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

import static uet.oop.bomberman.BombermanGame.bombs;
import static uet.oop.bomberman.BombermanGame.characters;

public class AIMedium extends AI {

    Bomber bomber;
    Enemy enemy;

    public AIMedium(Bomber bomber, Enemy e) {
        this.bomber = bomber;
        enemy = e;
    }


    @Override
    public int calculateDirection() {
        if (bomber == null || Math.abs(characters.get(0).getXCanvas() - enemy.getXCanvas()) > 5
                || Math.abs(characters.get(0).getYCanvas() - enemy.getYCanvas()) > 5) {
            return random.nextInt(4);
        }

        int dirToAvoidBombs = avoidBombs();
        if (dirToAvoidBombs != -1) {
            return dirToAvoidBombs;
        } else {
            int vertical = random.nextInt(2);
            if (vertical == 1) {
                int v = calculateRowDirection();
                if (v != -1) {
                    return v;
                } else {
                    return calculateColDirection();
                }

            } else {
                int h = calculateColDirection();

                if (h != -1) {
                    return h;
                } else {
                    return calculateRowDirection();
                }
            }
        }
    }

    protected int avoidBombs() {
        for (Bomb b : bombs) {
            if (b.getYCanvas() == enemy.getYCanvas()) {
                if (b.getXCanvas() - enemy.getXCanvas() <= 2
                        && b.getXCanvas() - enemy.getXCanvas() >= 0 ) {
                    return LEFT;
                }
                if (b.getXCanvas() - enemy.getXCanvas() >= -2
                        && b.getXCanvas() - enemy.getXCanvas() < 0 ) {
                    return RIGHT;
                }
            }
            if (b.getXCanvas() == enemy.getXCanvas()) {
                if (b.getYCanvas() - enemy.getYCanvas() <= 2
                        && b.getYCanvas() - enemy.getYCanvas() >= 0 ) {
                    return UP;
                }
                if (b.getXCanvas() - enemy.getXCanvas() >= -2
                        && b.getXCanvas() - enemy.getXCanvas() < 0 ) {
                    return DOWN;
                }
            }
        }
        return -1;
    }

    protected int calculateColDirection() {
        if (bomber.getXCanvas() < enemy.getXCanvas()) {
            return LEFT;
        } else if (bomber.getXCanvas() > enemy.getXCanvas()) {
            return RIGHT;
        }

        return -1;
    }

    protected int calculateRowDirection() {
        if (bomber.getYCanvas() < enemy.getYCanvas()) {
            return UP;
        } else if (bomber.getYCanvas() > enemy.getYCanvas()) {
            return DOWN;
        }
        return -1;
    }
}

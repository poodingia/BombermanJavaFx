package uet.oop.bomberman.entities.character.enemy.AI;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AIMedium extends AI {

    Bomber bomber;
    Enemy enemy;

    public AIMedium(Bomber bomber, Enemy e) {
        this.bomber = bomber;
        enemy = e;
    }


    @Override
    public int calculateDirection() {
        if (bomber == null) {
            return random.nextInt(4);
        }

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

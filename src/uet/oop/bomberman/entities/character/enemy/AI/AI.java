package uet.oop.bomberman.entities.character.enemy.AI;

import java.util.Random;
import uet.oop.bomberman.util.Constant;

public abstract class AI implements Constant {
    protected Random random = new Random();

    public abstract int calculateDirection();
}

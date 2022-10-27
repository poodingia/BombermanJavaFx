package uet.oop.bomberman.entities.character.enemy.AI;

public class AILow extends AI {

    public int calculateDirection() {
        return random.nextInt(4);
    }
}

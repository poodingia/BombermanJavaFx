package uet.oop.bomberman.entities.character.enemy.AI;

import static uet.oop.bomberman.BombermanGame.mapObjects;

import java.util.List;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Grass;

public class AIHigh extends AI {

    Bomber bomber;
    Enemy enemy;

    public AIHigh(Bomber bomber, Enemy e) {
        this.bomber = bomber;
        enemy = e;
    }

    @Override
    public int calculateDirection() {
        Node initial_node = new Node(enemy.getYCanvas(), enemy.getXCanvas());
        Node final_node = new Node(bomber.getYCanvas(), bomber.getXCanvas());

        AStar a_star = new AStar(HEIGHT, WIDTH, initial_node, final_node);

        int[][] blocks_in_array = new int[WIDTH * HEIGHT][2];
        int count_block = 0;

        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (!(mapObjects.get(i).get(j) instanceof Grass)) {
                    blocks_in_array[count_block][0] = i;
                    blocks_in_array[count_block][1] = j;
                    count_block++;
                }
            }
        }
        a_star.setBlocks(blocks_in_array, count_block);
        List<Node> path = a_star.findPath();
        if (path.size() > 1) {
            int nextX = path.get(1).getCol();
            int nextY = path.get(1).getRow();

            if (enemy.getYCanvas()  > nextY) {
                return UP;
            }
            if (enemy.getYCanvas()  < nextY) {
                return DOWN;
            }
            if (enemy.getXCanvas()  > nextX) {
                return LEFT;
            }
            if (enemy.getXCanvas()  < nextX) {
                return RIGHT;
            }
        }
        return random.nextInt(4);
    }
}

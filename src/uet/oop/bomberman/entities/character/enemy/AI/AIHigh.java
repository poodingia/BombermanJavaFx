package uet.oop.bomberman.entities.character.enemy.AI;

import java.util.List;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Grass;

//Use for Oneal and other tracking enemies with collision with Brick
public class AIHigh extends AI {

    private Board board;
    private Bomber bomber;
    private final Enemy enemy;

    private AStar a_star;

    private boolean isChasing;

    public AIHigh(Bomber bomber, Enemy e, Board b) {
        this.bomber = bomber;
        enemy = e;
        a_star = new AStar(HEIGHT, WIDTH);
        isChasing = false;
        board = b;
    }

    public boolean isChasing() {
        return isChasing;
    }

    @Override
    public int calculateDirection() {
        if (Math.abs(enemy.getXCanvas() - bomber.getXCanvas()) > 4
                || Math.abs(enemy.getYCanvas() - bomber.getYCanvas()) > 4) {
            return random.nextInt(4);
        } else {
            Node initial_node = new Node(enemy.getYCanvas(), enemy.getXCanvas());
            Node final_node = new Node(bomber.getYCanvas(), bomber.getXCanvas());

            a_star.setInitial_node(initial_node);
            a_star.setFinal_node(final_node);
            a_star.setNodes();
            //mark all block on the map
            int[][] blocks_in_array = new int[WIDTH * HEIGHT][2];
            int count_block = 0;

            for (int i = 0; i < HEIGHT; i++) {
                for (int j = 0; j < WIDTH; j++) {
                    if (!(board.mapObjects.get(i).get(j) instanceof Grass)) {
                        blocks_in_array[count_block][0] = i;
                        blocks_in_array[count_block][1] = j;
                        count_block++;
                    }
                }
            }

            a_star.setBlocks(blocks_in_array, count_block);

            //start finding the path
            List<Node> path = a_star.findPath();
            if (path.size() > 1) {
                int nextX = path.get(1).getCol();
                int nextY = path.get(1).getRow();
                isChasing = true;

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
            isChasing = false;
            return random.nextInt(4);
        }

    }
}

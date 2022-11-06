package uet.oop.bomberman.entities.character.enemy.AI;

import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Grass;

import java.util.List;

import static uet.oop.bomberman.BombermanGame.bombs;
import static uet.oop.bomberman.BombermanGame.mapObjects;

//Use for Kondoria and other tracking enemies without collision with Brick
//Add bomb dodging later
public class AIHigher extends AI {

    private Bomber bomber;
    private Enemy enemy;

    private AStar a_star;

    public AIHigher(Bomber bomber, Enemy e) {
        this.bomber = bomber;
        enemy = e;
        a_star = new AStar(HEIGHT, WIDTH);
    }

    @Override
    public int calculateDirection() {
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
                if (!(mapObjects.get(i).get(j) instanceof Grass)
                        && !(mapObjects.get(i).get(j) instanceof Brick)) {
                    blocks_in_array[count_block][0] = i;
                    blocks_in_array[count_block][1] = j;
                    count_block++;
                }
            }
        }

        for (Bomb b : bombs) {
            blocks_in_array[count_block][0] = b.getYCanvas();
            blocks_in_array[count_block][1] = b.getXCanvas();
            count_block++;
        }
        a_star.setBlocks(blocks_in_array, count_block);

        //start finding the path
        List<Node> path = a_star.findPath();
        if (path.size() > 1) {
            int nextX = path.get(1).getCol();
            int nextY = path.get(1).getRow();
//            System.out.println("Path:");
//            for (Node n : path) {
//                System.out.println(n.getCol() + ", " + n.getRow());
//            }
//            System.out.println("\n");

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

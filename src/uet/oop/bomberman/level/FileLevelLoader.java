package uet.oop.bomberman.level;

import static uet.oop.bomberman.BombermanGame.mapObjects;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class FileLevelLoader {
    public static void loadMapFile(int mode) throws FileNotFoundException {
        File mapFile = new File("res/levels/level1.txt");
        Scanner mapReader = new Scanner(mapFile);
        int level = mapReader.nextInt();
        int rows = mapReader.nextInt();
        int column = mapReader.nextInt();
        String rowLone = mapReader.nextLine();
        for (int i = 0; i < rows; i++) {
            mapObjects.add(new ArrayList<Entity>());
            String row = mapReader.nextLine();
            for (int j = 0; j < column; j++) {
                Entity object;
                switch (row.charAt(j)) {
                    case '#': {
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                        break;
                    }
                    case '*': {
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        break;
                    }
                    default: {
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        break;
                    }
                }
                mapObjects.get(i).add(object);
            }
        }
    }

}

package uet.oop.bomberman.level;
import static uet.oop.bomberman.BombermanGame.mapObjects;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.util.List;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class FileLevelLoader {

    public FileLevelLoader() {

    }
    private char[][] map;
    private int height;
    private int width;
    private int level;


    public char getMapAt(int i, int j) {
        return map[i][j];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void loadLevel(int _level) {
        List<String> list = new ArrayList<>();
        try {
            FileReader fr = new FileReader("res/levels/Level" + _level + ".txt");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (!line.equals("")) {
                list.add(line);
                line = br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] arrays = list.get(0).trim().split(" ");
        _level = Integer.parseInt(arrays[0]);
        height = Integer.parseInt(arrays[1]);
        width = Integer.parseInt(arrays[2]);
        map = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                map[i][j] = list.get(i + 1).charAt(j);
            }
        }
    }

    public void creatEntities() {
        for (int y = 0; y < getHeight(); y++) {
            mapObjects.add(new ArrayList<Entity>());
            for (int x = 0; x < getWidth(); x++) {
                Entity object;
                int pos = x + y * getWidth();
                char c = map[y][x];
                switch (c) {
                    // Thêm grass
                    case ' ':
                        object = new Grass(x, y, Sprite.grass.getFxImage());
                        break;
                    // Thêm Wall
                    case '#':
                        object = new Wall(x, y, Sprite.wall.getFxImage());
                        break;
                    // Thêm Portal
//                    case 'x':
//                        break;
                    // Thêm brick
                    case '*':
                        object = new Brick(x, y, Sprite.brick.getFxImage());
                        break;
                    default: {
                        object = new Grass(x, y, Sprite.grass.getFxImage());
                        break;
                    }
                }
                mapObjects.get(y).add(object);
            }
        }
    }
}
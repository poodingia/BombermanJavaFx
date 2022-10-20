package uet.oop.bomberman.level;
import static uet.oop.bomberman.BombermanGame.grasses;
import static uet.oop.bomberman.BombermanGame.mapObjects;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tile.*;
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
                FlameBuff flameB;
                BombBuff bombB;
                SpeedBuff speedB;
                Portal portal;
                int pos = x + y * getWidth();
                char c = map[y][x];
                Grass grass = new Grass(x, y, Sprite.grass.getFxImage());
                grasses.add(grass);
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
                    case 'x':
                        object = new Portal(x, y, Sprite.portal.getFxImage());
                        break;
                    // Thêm brick
                    case '*':
                        object = new Brick(x, y, Sprite.brick.getFxImage());
                        break;
                    case 'b':
                        object = new BombBuff(x, y, Sprite.powerup_bombs.getFxImage());
                        break;
                    case 'f':
                        object = new FlameBuff(x, y, Sprite.powerup_flames.getFxImage());
                        break;
                    case 's':
                        object = new SpeedBuff(x, y, Sprite.powerup_speed.getFxImage());
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
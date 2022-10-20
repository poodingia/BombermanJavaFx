package uet.oop.bomberman.level;
import static uet.oop.bomberman.BombermanGame.grasses;
import static uet.oop.bomberman.BombermanGame.mapObjects;
import static uet.oop.bomberman.BombermanGame.items;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.tile.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.util.List;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;


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
                Brick objectBrick;
                FlameBuff flameB;
                BombBuff bombB;
                SpeedBuff speedB;
                Portal portal;
                int pos = x + y * getWidth();
                char c = map[y][x];

                Grass grass = new Grass(x, y, Sprite.grass.getFxImage());
                grasses.add(grass);
                switch (c) {
                    // Thêm Wall
                    case '#':
                        object = new Wall(x, y, Sprite.wall.getFxImage());
                        mapObjects.get(y).add(object);
                        break;
                    // Thêm Portal
                    case 'x':
                        object = new Portal(x, y, Sprite.portal.getFxImage());
                        break;
                    // Thêm brick
                    case '*':
                        objectBrick = new Brick(x, y, Sprite.brick.getFxImage());
                        mapObjects.get(y).add(objectBrick);
                        break;
                    case 'b':
                        objectBrick = new Brick(x, y, Sprite.brick.getFxImage());
                        bombB = new BombBuff(x, y, Sprite.powerup_bombs.getFxImage(), objectBrick);
                        mapObjects.get(y).add(objectBrick);
                        items.add(bombB);
                        bombB.setBrick(objectBrick);
                        break;
                    case 'f':
                        objectBrick = new Brick(x, y, Sprite.brick.getFxImage());
                        flameB = new FlameBuff(x, y, Sprite.powerup_flames.getFxImage(), objectBrick);
                        mapObjects.get(y).add(objectBrick);
                        items.add(flameB);
                        flameB.setBrick(objectBrick);
                        break;
                    case 's':
                        objectBrick = new Brick(x, y, Sprite.brick.getFxImage());
                        speedB = new SpeedBuff(x, y, Sprite.powerup_speed.getFxImage(), objectBrick);
                        mapObjects.get(y).add(objectBrick);
                        items.add(speedB);
                        speedB.setBrick(objectBrick);
                        break;

                    default: {
                        object = new Grass(x, y, Sprite.grass.getFxImage());
                        mapObjects.get(y).add(object);
                        break;
                    }
                }
            }
        }
    }
}
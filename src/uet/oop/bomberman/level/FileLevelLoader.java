package uet.oop.bomberman.level;
import static uet.oop.bomberman.BombermanGame.characters;
import static uet.oop.bomberman.BombermanGame.ground;
import static uet.oop.bomberman.BombermanGame.mapObjects;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.enemy.Balloom;
import uet.oop.bomberman.entities.character.enemy.Kondoria;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.BombBuff;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.FlameBuff;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.SpeedBuff;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;

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
                Grass grass = new Grass(x, y, Sprite.grass.getFxImage());
                ground.add(grass);
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
                        object = new Brick(x, y, Sprite.brick.getFxImage());
                        ground.add(new Portal(x, y, Sprite.portal.getFxImage()));
                        break;
                    // Thêm brick
                    case '*':
                        object = new Brick(x, y, Sprite.brick.getFxImage());
                        break;
                    case 'b':
                        ground.add(new BombBuff(x, y, Sprite.powerup_bombs.getFxImage()));
                        object = new Brick(x, y, Sprite.brick.getFxImage());
                        break;
                    case 'f':
                        ground.add(new FlameBuff(x, y, Sprite.powerup_flames.getFxImage()));
                        object = new Brick(x, y, Sprite.brick.getFxImage());
                        break;
                    case 's':
                        ground.add(new SpeedBuff(x, y, Sprite.powerup_speed.getFxImage()));
                        object = new Brick(x, y, Sprite.brick.getFxImage());
                        break;
                    case '1':
                        characters.add(new Balloom(x, y, Sprite.balloom_left1.getFxImage()));
                        object = new Grass(x, y, Sprite.grass.getFxImage());
                        break;
                    case '2':
                        characters.add(new Oneal(x, y, Sprite.oneal_left1.getFxImage()));
                        object = new Grass(x, y, Sprite.grass.getFxImage());
                        break;
                    case '3':
                        characters.add(new Kondoria(x, y, Sprite.kondoria_left1.getFxImage()));
                        object = new Grass(x, y, Sprite.grass.getFxImage());
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
package uet.oop.bomberman;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.util.Constant;

public class Board implements Constant {

    public ArrayList<ArrayList<Entity>> mapObjects = new ArrayList<>();
    public Stack<KeyCode> keyCodeList = new Stack<>();
    public List<Bomb> bombs = new ArrayList<>();
    public List<Entity> ground = new ArrayList<>();
    public List<Character> characters = new ArrayList<>();
    protected Scene GameScene;
    private int result = 0;
    private int state = 0;
    private int score = 0;
    private int timer = TIME * 120;
    private int level = 1;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public boolean getBombAt(int x, int y) {
        for (Bomb bomb : this.bombs) {
            if (bomb.getXCanvas() == x && bomb.getYCanvas() == y) {
                return true;
            }
        }
        return false;
    }

    public Portal getPortal() {
        for (Entity p : this.ground) {
            if (p instanceof Portal) {
                return (Portal) p;
            }
        }
        return null;
    }
}
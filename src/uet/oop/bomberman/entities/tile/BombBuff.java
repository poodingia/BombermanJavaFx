package uet.oop.bomberman.entities.tile;

import static uet.oop.bomberman.BombermanGame.ground;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class BombBuff extends Entity implements Buff{

    public BombBuff(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }
}
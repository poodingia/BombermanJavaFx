package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;

public class SpeedBuff extends Entity implements Buff {

    public SpeedBuff(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }
}

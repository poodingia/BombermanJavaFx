package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;

public class FlameBuff extends Entity implements Buff {

    public FlameBuff(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }
}

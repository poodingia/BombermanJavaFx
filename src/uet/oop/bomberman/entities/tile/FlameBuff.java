package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.sound.mediaPlayer;

public class FlameBuff extends Entity implements Buff {
    mediaPlayer item = new mediaPlayer("res/sounds/item.wav");

    public FlameBuff(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (this.isRemove()) item.play();
    }
}

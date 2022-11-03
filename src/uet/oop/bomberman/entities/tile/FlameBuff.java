package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.mediaPlayer;

import static uet.oop.bomberman.BombermanGame.characters;

public class FlameBuff extends Entity implements Buff {
    mediaPlayer item = new mediaPlayer("res/sounds/item.wav");

    public FlameBuff(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (this.isRemove()) item.play();
    }

    public void spawnEnemy() {
        for (int i = 0; i < 2; i++) {
            characters.add(new Oneal(this.getXCanvas(), this.getYCanvas(), Sprite.balloom_left1.getFxImage()));
        }
    }
}

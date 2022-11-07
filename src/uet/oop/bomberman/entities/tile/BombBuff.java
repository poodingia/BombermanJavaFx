package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.enemy.Balloom;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.mediaPlayer;

public class BombBuff extends Entity implements Buff{
    private final Board board;
    private final mediaPlayer item = new mediaPlayer("res/sounds/item.wav");

    public BombBuff(int x, int y, Image img, Board b) {
        super(x, y, img);
        board = b;
    }

    @Override
    public void update() {
        if (this.isRemove()) item.play();
    }

    @Override
    public void spawnEnemy() {
        for (int i = 0; i < 2; i++) {
            board.characters.add(new Balloom(this.getXCanvas(), this.getYCanvas(), Sprite.balloom_left1.getFxImage(), board));
        }
    }
}
package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import uet.oop.bomberman.graphics.Sprite;

public class Bomber extends Entity {

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        foot = 0;
    }

    @Override
    public void update() {
        if(this.foot == states.LEFT.ordinal()) {
            if (this.state == states.LEFT.ordinal()){
                this.img = Sprite.player_left_1.getFxImage();
            }
            else if (this.state == states.RIGHT.ordinal()) {
                this.img = Sprite.player_right_1.getFxImage();
            }
            else if(this.state == states.UP.ordinal()) {
                this.img = Sprite.player_up_1.getFxImage();
            }
            else if(this.state == states.DOWN.ordinal()) {
                this.img = Sprite.player_down_1.getFxImage();
            }}
        else {
            if (this.state == states.LEFT.ordinal()){
                this.img = Sprite.player_left_2.getFxImage();
            }
            else if (this.state == states.RIGHT.ordinal()) {
                this.img = Sprite.player_right_2.getFxImage();
            }
            else if(this.state == states.UP.ordinal()) {
                this.img = Sprite.player_up_2.getFxImage();
            }
            else if(this.state == states.DOWN.ordinal()) {
                this.img = Sprite.player_down_2.getFxImage();
            }
        }
    }

}

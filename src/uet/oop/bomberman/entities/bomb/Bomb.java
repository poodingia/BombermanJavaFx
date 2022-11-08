package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.sound.mediaPlayer;;

public class Bomb extends AnimatedEntity {

    protected Board board;

    protected Bomber bomber;
    private int timeLeft = 240;
    private Flame flame = null;
    private boolean harmful = false;
    private mediaPlayer explodeSound = new mediaPlayer("res/sounds/explode.wav");
    private mediaPlayer bombPlant = new mediaPlayer("res/sounds/plantingBomb.wav");

    public Bomb(double xUnit, double yUnit, Image img, Bomber bomber, Board b) {
        super(xUnit, yUnit, img);
        this.bomber = bomber;
        board = b;
    }


    @Override
    public void update() {
        isHarmful();
        chooseSprite();
        countDown();
        animate();
        if (flame != null) {
            flame.update();
        }
    }

    public Bomber getBomber() {
        return bomber;
    }

    public void setBomber(Bomber bomber) {
        this.bomber = bomber;
    }

    private void countDown() {
        bombPlant.play();
        if (!bomber.isRemote()){
            timeLeft--;
            explode();
        }
        else {
            timeLeft--;
            if(board.keyCodeList.size() > 0 && board.keyCodeList.lastElement() == KeyCode.X){
                timeLeft = 1;
                board.keyCodeList.pop();
            }
            explode();
        }

    }

    private void explode() {
        if (timeLeft == 0) {
            flame = new Flame(getXCanvas(), getYCanvas(), null, bomber, board);
            img = Sprite.bomb_exploded.getFxImage();
            explodeSound.play();
        } else if (timeLeft == -24) {
            img = Sprite.bomb_exploded1.getFxImage();
        } else if (timeLeft == -48) {
            img = Sprite.bomb_exploded2.getFxImage();
        } else if (timeLeft == -60) {
            remove = true;
            flame.remove = true;
        }
    }

    void chooseSprite() {
        if (timeLeft > 0) {
            img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, animate, 60)
                .getFxImage();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        super.render(gc);
        if (timeLeft <= 0 && flame != null) {
            flame.render(gc);
        }
    }

    public void kill() {
        flame.kill();
    }

    public boolean isHarmful() {
        if (!bomber.intersect(this)) {
            harmful = true;
        }
        return harmful;
    }

    void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    int getTimeLeft() {
        return timeLeft;
    }
}

package uet.oop.bomberman;

import static uet.oop.bomberman.level.FileLevelLoader.loadMapFile;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static ArrayList<ArrayList<Entity>> mapObjects = new ArrayList<>();
    public static Stack<KeyCode> keyCodeList = new Stack<>();

    public static List<Bomb> bombs = new ArrayList<>();

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();
        stage.setTitle("Bomberman 2022.1.0");

        loadMapFile(1);

        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);

        scene.setOnKeyPressed(event -> {
            keyCodeList.add(event.getCode());
        });
        scene.setOnKeyReleased(event -> {
            keyCodeList.clear();
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();
    }


    public void update() {
        entities.forEach(Entity::update);
        bombs.forEach(Bomb::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        mapObjects.forEach(g -> g.forEach(e -> e.render(gc)));
        bombs.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

}

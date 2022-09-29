package uet.oop.bomberman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();

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

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        createMapFile();

        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);


        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            switch (code) {
                case LEFT: {
                    bomberman.moveLeft();
                    break;
                }
                case RIGHT: {
                    bomberman.moveRight();
                    break;
                }
                case UP: {
                    bomberman.moveUp();
                    break;
                }
                case DOWN: {
                    bomberman.moveDown();
                    break;
                }
            }
        });
    }

    public void createMap() throws FileNotFoundException {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                if (j == 0 || j == HEIGHT - 1 || i == 0 || i == WIDTH - 1) {
                    object = new Wall(i, j, Sprite.wall.getFxImage());
                } else {
                    object = new Grass(i, j, Sprite.grass.getFxImage());
                }
                stillObjects.add(object);
            }
        }


    }

    public void createMapFile() throws FileNotFoundException {
        File mapFile = new File("res/levels/level1.txt");
        Scanner mapReader = new Scanner(mapFile);
        int level = mapReader.nextInt();
        int rows = mapReader.nextInt();
        int column = mapReader.nextInt();
        String rowLone = mapReader.nextLine();
        for (int i = 0; i < rows; i++) {
            String row = mapReader.nextLine();
            for (int j = 0; j < column; j++) {
                Entity object;
                switch (row.charAt(j)) {
                    case '#': {
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                        break;
                    }
                    case '*': {
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        break;
                    }
                    default: {
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        break;
                    }
                }
                stillObjects.add(object);
            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}

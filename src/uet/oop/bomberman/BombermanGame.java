package uet.oop.bomberman;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.FileLevelLoader;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static ArrayList<ArrayList<Entity>> mapObjects = new ArrayList<>();
    public static Stack<KeyCode> keyCodeList = new Stack<>();

    public static List<Bomb> bombs = new ArrayList<>();

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private FileLevelLoader levelLoader = new FileLevelLoader();
    private Scene GameScene;
    private Scene MenuScene;
    VBox root = new VBox();
    VBox menu = new VBox();
    Text Stat = new Text("Level 1");
    private boolean paused = true;

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

        //START MENU===========================================
        menu.setStyle("-fx-background-image: url('start_menu.png')");
        MenuScene = new Scene(menu, WIDTH * 32, HEIGHT * 32 + 15, Color.BLACK);
        stage.setScene(MenuScene);

        Button start = new Button("START");
        Button exit = new Button("EXIT");

        Text title = new Text("BOMBERMAN");
        title.setStyle("-fx-font: 80px Algerian; -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, #008cff 0%, #00e1ff 50%); -fx-stroke: #1a7422; -fx-stroke-width: 1");
        start.setStyle("-fx-font: 40px Algerian; -fx-background-color: #0d4056; -fx-text-fill: #00ffd0; -fx-base: #b6e7c9;");
        exit.setStyle("-fx-font: 40px Algerian; -fx-background-color: #0d4056; -fx-text-fill: #00ffd0; -fx-base: #b6e7c9;");
        Font font = Font.font("Verdana", FontWeight.BOLD, 30);
        Font font1 = Font.font("Algerian", FontWeight.BOLD, 30);
        start.setFont(font);
        exit.setFont(font);
        title.setFont(font1);

        menu.setAlignment(Pos.TOP_LEFT);
        menu.setSpacing(20);
        menu.getChildren().addAll(title, start, exit);
        start.setOnAction(event -> {
            paused = false;
            // Them scene vao stage
            stage.setScene(scene);
        });

        exit.setOnAction(event -> {
            stage.close();
        });

        stage.show();
        stage.setTitle("Bomberman 2022.1.0");
        //==========================================================

        //loadMapFile(1);
        createMap();

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

    public void createMap(){
        levelLoader.loadLevel(1);
        levelLoader.creatEntities();
    }

    public void StartMenu(Stage stage) {
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        root.getChildren().add(Stat);
        root.getChildren().add(canvas);

        root.setStyle("-fx-background-color: white; -fx-text-fill: yellow;");
        menu.setStyle("-fx-background-image: url('start_menu.png')");
        // Tao scene
        GameScene = new Scene(root);
        MenuScene = new Scene(menu, WIDTH * 32, HEIGHT * 32 + 15, Color.BLACK);
        stage.setScene(MenuScene);

        Button start = new Button("START");
        Button exit = new Button("EXIT");

        Text title = new Text("BOMBERMAN");
        title.setStyle("-fx-font: 80px Algerian; -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, #008cff 0%, #00e1ff 50%); -fx-stroke: #1a7422; -fx-stroke-width: 1");
        start.setStyle("-fx-font: 40px Algerian; -fx-background-color: #0d4056; -fx-text-fill: #00ffd0; -fx-base: #b6e7c9;");
        exit.setStyle("-fx-font: 40px Algerian; -fx-background-color: #0d4056; -fx-text-fill: #00ffd0; -fx-base: #b6e7c9;");
        Font font = Font.font("Verdana", FontWeight.BOLD, 30);
        Font font1 = Font.font("Algerian", FontWeight.BOLD, 30);
        start.setFont(font);
        exit.setFont(font);
        title.setFont(font1);

        menu.setAlignment(Pos.TOP_LEFT);
        menu.setSpacing(20);
        menu.getChildren().addAll(title, start, exit);
        start.setOnAction(event -> {
            paused = false;
            stage.setScene(GameScene);
        });

        exit.setOnAction(event -> {
            stage.close();
        });

//        stage.setTitle("Bomberman");
//        stage.show();
    }

    public void update() {
        bombs.removeIf(Bomb::isRemove);
        entities.removeIf(Entity::isRemove);
        mapObjects.forEach(row -> row.removeIf(Entity::isRemove));
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
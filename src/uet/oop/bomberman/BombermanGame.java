package uet.oop.bomberman;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.tile.Brick;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.FileLevelLoader;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static ArrayList<ArrayList<Entity>> mapObjects = new ArrayList<>();
    public static Stack<KeyCode> keyCodeList = new Stack<>();

    public static List<Bomb> bombs = new ArrayList<>();

    public static List<Entity> ground = new ArrayList<>();
    public static List<Character> characters = new ArrayList<>();
    VBox root = new VBox();
    VBox menu = new VBox();
    VBox pausedMenu = new VBox();
    Text Stat = new Text("Level 1");
    private GraphicsContext gc;
    private Canvas canvas;
    private FileLevelLoader levelLoader = new FileLevelLoader();
    private Scene GameScene;
    private Scene MenuScene;
    private Scene pausedMenuScene;
    private boolean paused = true;


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        StartMenu(stage);
        pauseMenu(stage);
        //==========================================================

        //loadMapFile(1);
        createMap();
        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        characters.add(bomberman);

        GameScene.setOnKeyPressed(event -> {
            keyCodeList.add(event.getCode());
        });
        GameScene.setOnKeyReleased(event -> {
            keyCodeList.clear();
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                handlePause(stage);
                render();
                update();
            }
        };
        timer.start();
    }

    public void createMap() {
        levelLoader.loadLevel(1);
        levelLoader.createEntities();
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
        title.setStyle(
            "-fx-font: 80px Algerian; -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, #008cff 0%, #00e1ff 50%); -fx-stroke: #1a7422; -fx-stroke-width: 1");
        start.setStyle(
            "-fx-font: 40px Algerian; -fx-background-color: #0d4056; -fx-text-fill: #00ffd0; -fx-base: #b6e7c9;");
        exit.setStyle(
            "-fx-font: 40px Algerian; -fx-background-color: #0d4056; -fx-text-fill: #00ffd0; -fx-base: #b6e7c9;");
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

        stage.setTitle("Bomberman");
        stage.show();
    }

    public void pauseMenu(Stage stage) {
        Button quit = new Button("QUIT");
        Button resume = new Button("CONTINUE");
        pausedMenuScene = new Scene(pausedMenu, WIDTH * 32, HEIGHT * 32 + 15, Color.BLACK);

        pausedMenu.getChildren().addAll(resume, quit);

        pausedMenu.setAlignment(Pos.CENTER);
        pausedMenu.setSpacing(20);
        pausedMenu.setBackground(new Background(
            new BackgroundFill(Color.rgb(241, 222, 68), CornerRadii.EMPTY, Insets.EMPTY)));
        //pausedMenu.setStyle("-fx-background-image: url('start_menu.png')");
        quit.setStyle("-fx-background-color: #e7e0b6; -fx-text-fill: #0d4056; -fx-base: #b6e7c9;");
        resume.setStyle(
            "-fx-background-color: #e7e0b6; -fx-text-fill: #0d4056; -fx-base: #b6e7c9;");
        quit.setPadding(new Insets(20, 109, 20, 109));
        resume.setPadding(new Insets(20, 80, 20, 80));
        Font font = Font.font("Tahoma", FontWeight.BOLD, 30);
        resume.setFont(font);
        quit.setFont(font);

        resume.setOnAction(event -> {
            paused = false;
            GameSceneTrans(stage);
        });

        quit.setOnAction(event -> {
            stage.close();
        });
    }

    public void handlePause(Stage stage) {
        EventHandler<KeyEvent> eventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent e) {
                switch (e.getCode()) {
                    case ESCAPE:
                        if (!paused) {
                            paused = true;
                            PausedSceneTrans(stage);
                        }
                    default:
                        break;
                }
            }
        };
        GameScene.addEventFilter(KeyEvent.KEY_PRESSED, eventHandler);
    }

    public void PausedSceneTrans(Stage stage) {
        stage.setScene(pausedMenuScene);
    }

    public void GameSceneTrans(Stage stage) {
        stage.setScene(GameScene);
    }

    public void update() {
        bombs.removeIf(Bomb::isRemove);
        characters.removeIf(Entity::isRemove);
        ground.forEach(Entity::update);
        ground.removeIf(Entity::isRemove);
//        for (ArrayList<Entity> arrayList : mapObjects) {
//            for (int i = 0; i < arrayList.size(); i++) {
//                if (arrayList.get(i).isRemove()) {
//                    arrayList.set(i,
//                        new Grass(arrayList.get(i).getXCanvas(), arrayList.get(i).getXCanvas(),
//                            Sprite.grass.getFxImage()));
//                }
//            }
//        }
        for (int i = 1; i < mapObjects.size() - 1; i++) {
            for (int j = 1; j < mapObjects.get(i).size() - 1; j++) {
                if (mapObjects.get(i).get(j).isRemove()) {
                    mapObjects.get(i).set(j, new Grass(j, i, Sprite.grass.getFxImage()));
                }
            }
        }
        mapObjects.forEach(a -> a.forEach(Entity::update));
        characters.forEach(Entity::update);
        bombs.forEach(Bomb::update);

    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        ground.forEach(g -> g.render(gc));
        bombs.forEach(g -> g.render(gc));
        mapObjects.forEach(g -> g.forEach(e -> {
            if (!(e instanceof Grass)) {
                e.render(gc);
            }
        }));
        characters.forEach(g -> g.render(gc));
    }

    public static Bomb getBomb(int row, int column) {
        for (Bomb b : bombs) {
            if (b.getXCanvas() == row && b.getYCanvas() == column) {
                return b;
            }
        }
        return null;
    }

//    public static Character getCharacterExcluding(int row, int column, Character main) {
//        for (Character c :characters) {
//            if (c == main) continue;
//            if (c.intersect(main)) {
//                return c;
//            }
//        }
//        return null;
//    }
}
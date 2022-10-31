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
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.FileLevelLoader;
import uet.oop.bomberman.sound.mediaPlayer;
import uet.oop.bomberman.util.Constant;
import uet.oop.bomberman.util.Style;

public class BombermanGame extends Application implements Constant, Style {

    public static int result = 0;
    public static int state = 0;
    public static ArrayList<ArrayList<Entity>> mapObjects = new ArrayList<>();
    public static Stack<KeyCode> keyCodeList = new Stack<>();
    public static List<Bomb> bombs = new ArrayList<>();
    public static List<Entity> ground = new ArrayList<>();
    public static List<Character> characters = new ArrayList<>();
    VBox root = new VBox();
    VBox menu = new VBox();
    VBox pausedMenu = new VBox();
    VBox waitMenu = new VBox();
    VBox Win = new VBox();
    private int win = 0;
    VBox Lose = new VBox();
    private int lose = 0;
    mediaPlayer soundTrack = new mediaPlayer("res/music/gunny_background.mp3");
    mediaPlayer pauseMusic = new mediaPlayer("res/music/pause.mp3");
    mediaPlayer winMusic = new mediaPlayer("res/music/win.mp3");
    mediaPlayer loseMusic = new mediaPlayer("res/music/lose.mp3");
    mediaPlayer clicking = new mediaPlayer("res/sounds/click.mp3");
    private int level = 2;
    private Text Stat = new Text(String.format("Level %d", level));
    private GraphicsContext gc;
    private Canvas canvas;
    private FileLevelLoader levelLoader = new FileLevelLoader();
    private Scene GameScene;
    private Scene pausedMenuScene;
    private Scene Victory;
    private Scene Defeated;
    private Scene waitScene;
    private boolean paused = true;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws FileNotFoundException {
        stage.setResizable(false);
        StartMenu(stage);
        pauseMenu(stage);
        winMenu(stage);
        loseMenu(stage);
        nextLevelMenu(stage);
        soundTrack.play();

        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        characters.add(bomberman);
        createMap();

        GameScene.setOnKeyPressed(event -> {
            keyCodeList.add(event.getCode());
        });
        GameScene.setOnKeyReleased(event -> {
            keyCodeList.clear();
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                handlePause(stage);
                render();
                update();
                handleLose(stage);
                handleWin(stage);
                //handleTransition(stage);
            }
        };
        timer.start();
    }

    public void createMap() {
        levelLoader.loadLevel(level);
        levelLoader.createEntities();
        //System.out.println(level);
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
        Scene menuScene = new Scene(menu, WIDTH * 32, HEIGHT * 32 + 15, Color.BLACK);
        stage.setScene(menuScene);

        Button start = new Button("START");
        Button exit = new Button("EXIT");
        Text title = new Text("BOMBERMAN");

        title.setStyle(
            "-fx-font: 80px Algerian; -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, #008cff 0%, #00e1ff 50%); -fx-stroke: #1a7422; -fx-stroke-width: 1");
        start.setStyle(BUTTON_NORMAL);
        exit.setStyle(BUTTON_NORMAL);
        Font font = Font.font("Verdana", FontWeight.BOLD, 30);
        Font font1 = Font.font("Algerian", FontWeight.BOLD, 30);
        start.setFont(font);
        exit.setFont(font);
        title.setFont(font1);

        start.setOnMouseEntered(e -> start.setStyle(BUTTON_HOVER));
        start.setOnMouseExited(e -> start.setStyle(BUTTON_NORMAL));
        exit.setOnMouseEntered(e -> exit.setStyle(BUTTON_HOVER));
        exit.setOnMouseExited(e -> exit.setStyle(BUTTON_NORMAL));

        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(20);
        menu.getChildren().addAll(title, start, exit);
        start.setOnAction(event -> {
            clicking.play();
            paused = false;
            stage.setScene(GameScene);
            soundTrack.stop();
        });

        exit.setOnAction(event -> {
            clicking.play();
            stage.close();
        });

        stage.setTitle("Bomberman");
        stage.show();
    }

    public void winMenu(Stage stage) {
        Text title = new Text("VICTORY");
        title.setStyle(
                "-fx-font: 80px Algerian; -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, #008cff 0%, #00e1ff 50%); -fx-stroke: #1a7422; -fx-stroke-width: 1");
        Font font = Font.font("Tahoma", FontWeight.BOLD, 30);
        Button exit = new Button("EXIT");
        Button button = new Button("REPLAY");
        button.setStyle(BUTTON_NORMAL);
        button.setFont(font);

        Victory = new Scene(Win, WIDTH * 32, HEIGHT * 32 + 15, Color.BLACK);

        Win.getChildren().addAll(title, exit, button);

        exit.setStyle(BUTTON_NORMAL);
        exit.setFont(font);

        button.setOnMouseEntered(e -> button.setStyle(BUTTON_HOVER));
        button.setOnMouseExited(e -> button.setStyle(BUTTON_NORMAL));
        exit.setOnMouseEntered(e -> exit.setStyle(BUTTON_HOVER));
        exit.setOnMouseExited(e -> exit.setStyle(BUTTON_NORMAL));
        Win.setAlignment(Pos.CENTER);
        Win.setSpacing(20);
        Win.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        exit.setOnAction(event -> {
            clicking.stop();
            clicking.play();
            stage.close();
        });
        button.setOnAction(event -> {
            clicking.stop();
            clicking.play();
            GameSceneTrans(stage);
        });
    }

    public void loseMenu(Stage stage) {
        Text title = new Text("DEFEATED");
        title.setStyle(
                "-fx-font: 80px Algerian; -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, #008cff 0%, #00e1ff 50%); -fx-stroke: #1a7422; -fx-stroke-width: 1");

        Button exit = new Button("EXIT");
        Defeated = new Scene(Lose, WIDTH * 32, HEIGHT * 32 + 15, Color.BLACK);

        Lose.getChildren().addAll(title, exit);

        exit.setStyle(BUTTON_NORMAL);
        Font font = Font.font("Tahoma", FontWeight.BOLD, 30);
        exit.setFont(font);
        exit.setOnMouseEntered(e -> exit.setStyle(BUTTON_HOVER));
        exit.setOnMouseExited(e -> exit.setStyle(BUTTON_NORMAL));
        Lose.setAlignment(Pos.CENTER);
        Lose.setSpacing(20);
        Lose.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        exit.setOnAction(event -> {
            clicking.stop();
            clicking.play();
            stage.close();
        });
    }

    public void nextLevelMenu(Stage stage) {
        Button button = new Button("NEXT LEVEL");
        Button exit = new Button("EXIT");
        waitScene = new Scene(waitMenu, WIDTH * 32, HEIGHT * 32 + 15, Color.BLACK);
        Font font = Font.font("Tahoma", FontWeight.BOLD, 30);
        waitMenu.setAlignment(Pos.CENTER);
        waitMenu.setSpacing(20);
        waitMenu.setStyle("-fx-background-image: url('start_menu.png')");
        button.setOnMouseEntered(e -> button.setStyle(BUTTON_HOVER));
        button.setOnMouseExited(e -> button.setStyle(BUTTON_NORMAL));
        exit.setOnMouseEntered(e -> exit.setStyle(BUTTON_HOVER));
        exit.setOnMouseExited(e -> exit.setStyle(BUTTON_NORMAL));
        button.setStyle(BUTTON_NORMAL);
        button.setFont(font);
        exit.setStyle(BUTTON_NORMAL);
        exit.setFont(font);
        waitMenu.getChildren().addAll(button, exit);
        button.setOnAction(event -> {
            clicking.stop();
            clicking.play();
            GameSceneTrans(stage);
        });
        exit.setOnAction(event -> {
            clicking.stop();
            clicking.play();
            stage.close();
        });
    }

    public void pauseMenu(Stage stage) {
        Button quit = new Button("QUIT");
        Button resume = new Button("CONTINUE");
        Font font = Font.font("Tahoma", FontWeight.BOLD, 30);

        pausedMenuScene = new Scene(pausedMenu, WIDTH * 32, HEIGHT * 32 + 15, Color.BLACK);
        pausedMenu.getChildren().addAll(resume, quit);
        pausedMenu.setAlignment(Pos.CENTER);
        pausedMenu.setSpacing(20);
        pausedMenu.setStyle("-fx-background-image: url('start_menu.png')");

        quit.setStyle(BUTTON_NORMAL);
        quit.setOnMouseEntered(e -> quit.setStyle(BUTTON_HOVER));
        quit.setOnMouseExited(e -> quit.setStyle(BUTTON_NORMAL));

        resume.setStyle(BUTTON_NORMAL);
        resume.setOnMouseEntered(e -> resume.setStyle(BUTTON_HOVER));
        resume.setOnMouseExited(e -> resume.setStyle(BUTTON_NORMAL));

        resume.setFont(font);
        quit.setFont(font);

        resume.setOnAction(event -> {
            paused = false;
            GameSceneTrans(stage);
            pauseMusic.stop();
            clicking.stop();
            clicking.play();
        });

        quit.setOnAction(event -> {
            clicking.stop();
            clicking.play();
            stage.close();
        });
    }

    public void winTrans(Stage stage) {
            stage.setScene(Victory);
            winMusic.play();
    }

    public void loseTrans(Stage stage) {
            stage.setScene(Defeated);
            loseMusic.play();
    }

    public void nextLevelTrans(Stage stage) {
        stage.setScene(waitScene);
        winMusic.play();
        //soundTrack.play();
    }

    public void handlePause(Stage stage) {
        EventHandler<KeyEvent> eventHandler = new EventHandler<>() {
            @Override
            public void handle(KeyEvent e) {
                if (e.getCode() == KeyCode.ESCAPE) {
                    if (!paused) {
                        paused = true;
                        PausedSceneTrans(stage);
                        pauseMusic.play();
                    }
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
        Stat.setText(String.format("Level: %d", level));
    }

    public void reset(Stage stage) {
        characters.clear();
        ground.clear();
        mapObjects.forEach(ArrayList::clear);
        keyCodeList.clear();
        bombs.clear();
        if (result == WON) {
            state = 0;
            level++;
            if (level <= 3){
                nextLevelTrans(stage);
            }
            else if(level > 3){
                level = 1;
                winTrans(stage);
            }
        }
        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        characters.add(bomberman);
        createMap();
    }

    public void handleLose(Stage stage) {
        if (state == GAME_OVER && result == LOST) {
            loseTrans(stage);
        }
    }

    public void handleWin(Stage stage) {
        if (state == GAME_OVER && result != LOST) {
            reset(stage);
        }
    }

    public static boolean getBombAt(int x, int y) {
        for(Bomb bomb: bombs) {
            if(bomb.getXCanvas() == x && bomb.getYCanvas() == y) {
                return true;
            }
        }
        return false;
    }
}
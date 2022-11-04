package uet.oop.bomberman;

import static uet.oop.bomberman.graphics.ButtonUtil.setUpButton;

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
import javafx.scene.text.TextAlignment;
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
    VBox Lose = new VBox();
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

    public static boolean getBombAt(int x, int y) {
        for (Bomb bomb : bombs) {
            if (bomb.getXCanvas() == x && bomb.getYCanvas() == y) {
                return true;
            }
        }
        return false;
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


        createMap();

        GameScene.setOnKeyPressed(event -> {
            if (event.getCode().isArrowKey() || event.getCode() == KeyCode.SPACE) {
                keyCodeList.add(event.getCode());
            }
        });
        GameScene.setOnKeyReleased(event -> {
            keyCodeList.removeIf(keyCode -> keyCode == event.getCode());
        });

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                handlePause(stage);
                if (!paused) {
                    render();
                    update();
                }
                handleEnd(stage);
            }
        };
        timer.start();
    }

    public void createMap() {
        levelLoader.updateSprite(level);
        levelLoader.loadLevel(level);
        Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        characters.add(bomberman);
        levelLoader.createEntities();
        levelLoader.createCharacter();
    }

    public void StartMenu(Stage stage) {
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        root.getChildren().add(Stat);
        root.getChildren().add(canvas);

        root.setStyle("-fx-background-color: #e7e0b6; -fx-text-fill: yellow;");
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
        Font font1 = Font.font("Algerian", FontWeight.BOLD, 30);
        title.setFont(font1);
        Stat.setStyle("-fx-font: 15px Algerian; -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, #e7e0b6 0%, #f1de44 50%); -fx-stroke: #1a7422; -fx-stroke-width: 1");
        setUpButton(start);
        setUpButton(exit);

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

        Button exit = new Button("EXIT");
        Button replay = new Button("REPLAY");

        Victory = new Scene(Win, WIDTH * 32, HEIGHT * 32 + 15, Color.BLACK);
        Win.getChildren().addAll(title, exit, replay);
        Win.setAlignment(Pos.CENTER);
        Win.setSpacing(20);
        Win.setStyle("-fx-background-image: url('win_menu.png')");
        setUpButton(replay);
        setUpButton(exit);

        exit.setOnAction(event -> {
            clicking.stop();
            clicking.play();
            stage.close();
        });
        replay.setOnAction(event -> {
            paused = false;
            winMusic.stop();
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
        Button replay = new Button("REPLAY");
        Defeated = new Scene(Lose, WIDTH * 32, HEIGHT * 32 + 15, Color.BLACK);

        Lose.getChildren().addAll(title, exit, replay);
        Lose.setAlignment(Pos.CENTER);
        Lose.setSpacing(20);
        Lose.setStyle("-fx-background-image: url('lose_menu.png')");
        setUpButton(exit);
        setUpButton(replay);
        exit.setOnAction(event -> {
            clicking.stop();
            clicking.play();
            stage.close();
        });
        replay.setOnAction(event -> {
            paused = false;
            loseMusic.stop();
            clicking.stop();
            clicking.play();
            GameSceneTrans(stage);
        });
    }

    public void nextLevelMenu(Stage stage) {
        Button nextLevel = new Button("NEXT LEVEL");
        Button exit = new Button("EXIT");
        waitScene = new Scene(waitMenu, WIDTH * 32, HEIGHT * 32 + 15, Color.BLACK);
        Font font = Font.font("Tahoma", FontWeight.BOLD, 30);
        waitMenu.setAlignment(Pos.CENTER);
        waitMenu.setSpacing(20);
        waitMenu.setStyle("-fx-background-image: url('nextLevel_menu.png')");
        setUpButton(nextLevel);
        setUpButton(exit);
        waitMenu.getChildren().addAll(nextLevel, exit);
        nextLevel.setOnAction(event -> {
            clicking.stop();
            clicking.play();
            winMusic.stop();
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
        pausedMenuScene = new Scene(pausedMenu, WIDTH * 32, HEIGHT * 32 + 15, Color.BLACK);
        pausedMenu.getChildren().addAll(resume, quit);
        pausedMenu.setAlignment(Pos.CENTER);
        pausedMenu.setSpacing(20);
        pausedMenu.setStyle("-fx-background-image: url('start_menu.png')");

        setUpButton(quit);
        setUpButton(resume);

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
        levelLoader.updateSprite(level);
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
        Stat.setText(/*"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t" + */String.format("Level: %d", level));
    }

    public void reset(Stage stage) {
        characters.clear();
        ground.clear();
        mapObjects.forEach(ArrayList::clear);
        keyCodeList.clear();
        bombs.clear();
        state = 0;
        levelLoader.updateSprite(level);
        if (result == WON) {
            level++;
            if (level <= 3) {
                nextLevelTrans(stage);
            } else if (level > 3) {
                level = 1;
                winTrans(stage);
            }
        } else if (result == LOST) {
            level = 1;
            loseTrans(stage);
        }
        createMap();
    }

    public void handleEnd(Stage stage) {
        if (state == GAME_OVER) {
            paused = true;
            reset(stage);
        }
    }
}
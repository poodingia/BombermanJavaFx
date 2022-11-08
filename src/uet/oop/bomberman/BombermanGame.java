package uet.oop.bomberman;

import static uet.oop.bomberman.graphics.ButtonUtil.setUpButton;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.character.enemy.Pontan;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.FileLevelLoader;
import uet.oop.bomberman.sound.mediaPlayer;
import uet.oop.bomberman.util.Constant;
import uet.oop.bomberman.util.Style;

public class BombermanGame extends Application implements Constant, Style {

    private final static Board board = new Board();
    private final VBox root = new VBox();
    private final VBox menu = new VBox();
    private final VBox pausedMenu = new VBox();
    private final VBox waitMenu = new VBox();
    private final VBox Win = new VBox();
    private final VBox Lose = new VBox();
    private final mediaPlayer soundTrack = new mediaPlayer("res/music/gunny_background.mp3");
    private final mediaPlayer pauseMusic = new mediaPlayer("res/music/pause.mp3");
    private final mediaPlayer winMusic = new mediaPlayer("res/music/win.mp3");
    private final mediaPlayer loseMusic = new mediaPlayer("res/music/lose.mp3");
    private final mediaPlayer clicking = new mediaPlayer("res/sounds/click.mp3");
    private final Text Stat = new Text(String.format("Level %d", board.getLevel())
        + String.format("\t\t\t\t\t\t\tTime: %d", board.getTimer() / 120)
        + String.format("\t\t\t\t\t\t\tScore: %d", board.getScore()));
    private final FileLevelLoader levelLoader = new FileLevelLoader(board);
    private GraphicsContext gc;
    private Canvas canvas;
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

        createMap();

        board.GameScene.setOnKeyPressed(event -> {
            if (event.getCode().isArrowKey() || event.getCode() == KeyCode.SPACE
                || event.getCode() == KeyCode.X) {
                board.keyCodeList.add(event.getCode());
            }
        });
        board.GameScene.setOnKeyReleased(
            event -> board.keyCodeList.removeIf(keyCode -> keyCode == event.getCode()));

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

    private void createMap() {
        levelLoader.updateSprite(board.getLevel());
        levelLoader.loadLevel(board.getLevel());
        if (board.characters.size() == 0) {
            Bomber bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage(), board);
            board.characters.add(bomberman);
        }

        levelLoader.createEntities();
        levelLoader.createCharacter();
    }

    private void StartMenu(Stage stage) {
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        root.getChildren().add(Stat);
        root.getChildren().add(canvas);

        root.setStyle("-fx-background-color: #e7e0b6; -fx-text-fill: yellow;");
        menu.setStyle("-fx-background-image: url('start_menu.png')");
        // Tao scene
        board.GameScene = new Scene(root);
        Scene menuScene = new Scene(menu, WIDTH * 32, HEIGHT * 32 + 15, Color.BLACK);
        stage.setScene(menuScene);

        Button start = new Button("START");
        Button exit = new Button("EXIT");
        Text title = new Text("BOMBERMAN");

        title.setStyle(
            "-fx-font: 80px Algerian; -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, #008cff 0%, #00e1ff 50%); -fx-stroke: #1a7422; -fx-stroke-width: 1");
        Font font1 = Font.font("Algerian", FontWeight.BOLD, 30);
        title.setFont(font1);
        Stat.setStyle(
            "-fx-font: 15px Algerian; -fx-fill: linear-gradient(from 0% 0% to 100% 200%, repeat, #e7e0b6 0%, #f1de44 50%); -fx-stroke: #1a7422; -fx-stroke-width: 1");
        setUpButton(start);
        setUpButton(exit);

        menu.setAlignment(Pos.CENTER);
        menu.setSpacing(20);
        menu.getChildren().addAll(title, start, exit);
        start.setOnAction(event -> {
            clicking.play();
            paused = false;
            stage.setScene(board.GameScene);
            soundTrack.stop();
        });

        exit.setOnAction(event -> {
            clicking.play();
            stage.close();
        });

        stage.setTitle("Bomberman");
        stage.show();
    }

    private void winMenu(Stage stage) {
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

    private void loseMenu(Stage stage) {
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

    private void nextLevelMenu(Stage stage) {
        Button nextLevel = new Button("NEXT LEVEL");
        Button exit = new Button("EXIT");
        waitScene = new Scene(waitMenu, WIDTH * 32, HEIGHT * 32 + 15, Color.BLACK);
        waitMenu.setAlignment(Pos.CENTER);
        waitMenu.setSpacing(20);
        waitMenu.setStyle("-fx-background-image: url('nextLevel_menu.png')");
        setUpButton(nextLevel);
        setUpButton(exit);
        waitMenu.getChildren().addAll(nextLevel, exit);
        nextLevel.setOnAction(event -> {
            paused = false;
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

    private void pauseMenu(Stage stage) {
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

    private void winTrans(Stage stage) {
        stage.setScene(Victory);
        winMusic.play();
    }

    private void loseTrans(Stage stage) {
        stage.setScene(Defeated);
        loseMusic.play();
    }

    private void nextLevelTrans(Stage stage) {
        stage.setScene(waitScene);
        winMusic.play();
        //soundTrack.play();
    }

    private void handlePause(Stage stage) {
        EventHandler<KeyEvent> eventHandler = e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                if (!paused) {
                    paused = true;
                    PausedSceneTrans(stage);
                    pauseMusic.play();
                }
            }
        };
        board.GameScene.addEventFilter(KeyEvent.KEY_PRESSED, eventHandler);
    }

    private void PausedSceneTrans(Stage stage) {
        stage.setScene(pausedMenuScene);
    }

    private void GameSceneTrans(Stage stage) {
        stage.setScene(board.GameScene);
    }

    private void update() {
        board.bombs.removeIf(Bomb::isRemove);
        board.characters.removeIf(Entity::isRemove);
        board.ground.forEach(Entity::update);
        board.ground.removeIf(Entity::isRemove);
        for (int i = 1; i < board.mapObjects.size() - 1; i++) {
            for (int j = 1; j < board.mapObjects.get(i).size() - 1; j++) {
                if (board.mapObjects.get(i).get(j).isRemove()) {
                    board.mapObjects.get(i).set(j, new Grass(j, i, Sprite.grass.getFxImage()));
                }
            }
        }
        board.mapObjects.forEach(a -> a.forEach(Entity::update));
        board.characters.forEach(Entity::update);
        board.bombs.forEach(Bomb::update);
        levelLoader.updateSprite(board.getLevel());
        if (board.getTimer() >= 0) {
            board.setTimer(board.getTimer() - 1);
            if (board.getTimer() == 0) {
                if (board.getPortal() != null) {
                    board.characters.add(
                        new Pontan(board.getPortal().getXCanvas(), board.getPortal().getYCanvas(),
                            Sprite.pontan_left1.getFxImage(), board));
                    board.characters.add(
                        new Pontan(board.getPortal().getXCanvas(), board.getPortal().getYCanvas(),
                            Sprite.pontan_right1.getFxImage(), board));
                }
            }
        } else {
            board.setTimer(-1);
        }
    }

    private void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        board.ground.forEach(g -> g.render(gc));
        board.bombs.forEach(g -> g.render(gc));
        board.mapObjects.forEach(g -> g.forEach(e -> {
            if (!(e instanceof Grass)) {
                e.render(gc);
            }
        }));
        board.characters.forEach(g -> g.render(gc));
        Stat.setText(String.format("Level %d ", board.getLevel())
            + String.format("\t\t\t\t\t\t\tTime: %d", board.getTimer() / 120)
            + String.format("\t\t\t\t\t\t\tScore: %d", board.getScore()));
    }

    private void reset(Stage stage) {
        board.characters.removeIf(c -> c instanceof Enemy);
        board.ground.clear();
        board.mapObjects.forEach(ArrayList::clear);
        board.keyCodeList.clear();
        board.bombs.clear();
        board.setState(0);
        board.setTimer(TIME * 120);
        board.setScore(0);
        levelLoader.updateSprite(board.getLevel());
        if (board.getResult() == WON) {
            board.setLevel(board.getLevel() + 1);
            if (board.getLevel() <= 3) {
                nextLevelTrans(stage);
            } else {
                board.characters.clear();
                board.setLevel(1);
                winTrans(stage);
            }

        } else if (board.getResult() == LOST) {
            board.characters.clear();
            board.setLevel(1);
            loseTrans(stage);
        }
        if (board.characters.size() > 0) {
            board.characters.get(0).setX(32);
            board.characters.get(0).setY(32);
        }
        createMap();
    }

    private void handleEnd(Stage stage) {
        if (board.getState() == GAME_OVER) {
            paused = true;
            reset(stage);
        }
    }
}
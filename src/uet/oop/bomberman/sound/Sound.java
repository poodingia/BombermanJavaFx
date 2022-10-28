package uet.oop.bomberman.sound;
import java.nio.file.Paths;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class Sound {

    public Sound() {

    }

    public static void play(String file) {
        Media media = new Media(Paths.get(file).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
    }
}

package uet.oop.bomberman.graphics;

import static uet.oop.bomberman.util.Style.BUTTON_HOVER;
import static uet.oop.bomberman.util.Style.BUTTON_NORMAL;

import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ButtonUtil {
    public static void setUpButton(Button button){
        Font font = Font.font("Tahoma", FontWeight.BOLD, 30);
        button.setStyle(BUTTON_NORMAL);
        button.setFont(font);
        button.setOnMouseEntered(e -> button.setStyle(BUTTON_HOVER));
        button.setOnMouseExited(e -> button.setStyle(BUTTON_NORMAL));
    }

}

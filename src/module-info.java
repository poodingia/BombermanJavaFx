module bomberman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    opens uet.oop.bomberman to javafx.fxml;
    exports uet.oop.bomberman;
    exports uet.oop.bomberman.entities;
    exports uet.oop.bomberman.entities.tile;
    exports uet.oop.bomberman.entities.character;
    exports uet.oop.bomberman.entities.bomb;
}
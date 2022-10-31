module bomberman {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires javafx.media;
    opens uet.oop.bomberman to javafx.fxml;
    //opens uet.oop.bomberman to javafx.media;
    exports uet.oop.bomberman;
}
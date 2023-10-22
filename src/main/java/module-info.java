module ru.vsu.cs.baklanova.besiercurvefxapp {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.vsu.cs.baklanova.besiercurvefxapp to javafx.fxml;
    exports ru.vsu.cs.baklanova.besiercurvefxapp;
}
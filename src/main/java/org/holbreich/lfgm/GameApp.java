package org.holbreich.lfgm;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.holbreich.lfgm.ui.GameView;

public class GameApp extends Application {

    private static final int CELL_SIZE = 8;

    @Override
    public void start(Stage stage) {
        GameView view = new GameView(CELL_SIZE);

        BorderPane root = new BorderPane();
        root.setTop(buildToolbar(view));
        root.setCenter(view);

        stage.setTitle("Life Game");
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
    }

    private HBox buildToolbar(GameView view) {
        Label genLabel = new Label("Gen: 0");
        view.setOnTick(() -> genLabel.setText("Gen: " + view.getTurns()));

        Button startStop = new Button();
        startStop.setMinWidth(60);
        startStop.textProperty().bind(
            Bindings.when(view.runningProperty()).then("Stop").otherwise("Start")
        );
        startStop.setOnAction(e -> view.setRunning(!view.isRunning()));

        Button step   = new Button("Step");
        step.setOnAction(e -> view.step());

        Button random = new Button("Random");
        random.setOnAction(e -> {
            view.setRunning(false);
            view.randomize();
        });

        Slider speedSlider = new Slider(1, 60, 10);
        speedSlider.setShowTickLabels(true);
        speedSlider.setMajorTickUnit(15);
        speedSlider.setPrefWidth(160);
        speedSlider.valueProperty().addListener((obs, old, val) -> view.setSpeed(val.doubleValue()));

        HBox toolbar = new HBox(10,
            startStop, step, random,
            new Label("Speed (gen/s):"), speedSlider,
            genLabel
        );
        toolbar.setPadding(new Insets(10));
        toolbar.setAlignment(Pos.CENTER_LEFT);
        return toolbar;
    }
}

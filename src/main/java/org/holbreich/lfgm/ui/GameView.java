package org.holbreich.lfgm.ui;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.holbreich.lfgm.model.GameField;

/**
 * Canvas-based renderer and input handler for the game grid.
 */
public class GameView extends Canvas {

    private static final Color BG_COLOR    = Color.rgb(18, 18, 18);
    private static final Color CELL_COLOR  = Color.rgb(80, 200, 120);
    private static final Color GRID_COLOR  = Color.rgb(35, 35, 35);

    private final GameField field;
    private final int cellSize;
    private final int cellStride; // cellSize + 1px gap

    private boolean running = false;
    private long intervalNanos = 100_000_000L; // 10 gen/s default
    private long lastTick = 0;
    private Runnable onTick;

    public GameView(GameField field, int cellSize) {
        super(
            (long) field.getWidth()  * (cellSize + 1),
            (long) field.getHeight() * (cellSize + 1)
        );
        this.field = field;
        this.cellSize = cellSize;
        this.cellStride = cellSize + 1;

        setOnMouseClicked(this::handleClick);
        setOnMouseDragged(this::handleClick);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (running && now - lastTick >= intervalNanos) {
                    field.nextTurn();
                    lastTick = now;
                    if (onTick != null) onTick.run();
                }
                render();
            }
        }.start();
    }

    public void setRunning(boolean running)    { this.running = running; }
    public boolean isRunning()                 { return running; }
    public void setOnTick(Runnable onTick)     { this.onTick = onTick; }

    public void setSpeed(double generationsPerSecond) {
        intervalNanos = (long) (1_000_000_000.0 / generationsPerSecond);
    }

    public void step() {
        if (!running) {
            field.nextTurn();
            if (onTick != null) onTick.run();
        }
    }

    public void reset() {
        field.reset();
        if (onTick != null) onTick.run();
    }

    private void handleClick(MouseEvent e) {
        int x = (int) (e.getX() / cellStride);
        int y = (int) (e.getY() / cellStride);
        field.toggle(x, y);
    }

    private void render() {
        GraphicsContext gc = getGraphicsContext2D();
        double w = getWidth();
        double h = getHeight();

        gc.setFill(BG_COLOR);
        gc.fillRect(0, 0, w, h);

        // subtle grid
        gc.setStroke(GRID_COLOR);
        gc.setLineWidth(0.5);
        for (int x = 0; x <= field.getWidth(); x++) {
            gc.strokeLine(x * cellStride, 0, x * cellStride, h);
        }
        for (int y = 0; y <= field.getHeight(); y++) {
            gc.strokeLine(0, y * cellStride, w, y * cellStride);
        }

        gc.setFill(CELL_COLOR);
        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                if (field.isAlive(x, y)) {
                    gc.fillRect(x * cellStride + 1, y * cellStride + 1, cellSize, cellSize);
                }
            }
        }
    }
}

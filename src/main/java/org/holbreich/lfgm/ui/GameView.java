package org.holbreich.lfgm.ui;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import org.holbreich.lfgm.model.GameField;

/**
 * Canvas-based renderer and input handler. Extends Region so the canvas
 * fills whatever space the layout gives it and the grid adapts on resize.
 */
public class GameView extends Region {

    private static final Color BG_COLOR   = Color.rgb(18, 18, 18);
    private static final Color CELL_COLOR = Color.rgb(80, 200, 120);
    private static final Color GRID_COLOR = Color.rgb(35, 35, 35);

    private final int cellSize;
    private final int cellStride;

    private final Canvas canvas = new Canvas();
    private GameField field;

    private boolean running = false;
    private long intervalNanos = 100_000_000L;
    private long lastTick = 0;
    private Runnable onTick;

    public GameView(int cellSize) {
        this.cellSize = cellSize;
        this.cellStride = cellSize + 1;
        getChildren().add(canvas);

        canvas.setOnMouseClicked(this::handleClick);
        canvas.setOnMouseDragged(this::handleClick);

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

    @Override
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        if (w <= 0 || h <= 0) return;

        canvas.setWidth(w);
        canvas.setHeight(h);

        int newCols = Math.max(1, (int) (w / cellStride));
        int newRows = Math.max(1, (int) (h / cellStride));

        if (field == null || newCols != field.getWidth() || newRows != field.getHeight()) {
            field = new GameField(newCols, newRows);
            field.randomize(0.3);
            if (onTick != null) onTick.run();
        }
    }

    // --- public API used by GameApp ---

    public void setRunning(boolean running) { this.running = running; }
    public boolean isRunning()              { return running; }
    public int getTurns()                   { return field != null ? field.getTurns() : 0; }
    public void setOnTick(Runnable onTick)  { this.onTick = onTick; }

    public void setSpeed(double generationsPerSecond) {
        intervalNanos = (long) (1_000_000_000.0 / generationsPerSecond);
    }

    public void step() {
        if (!running && field != null) {
            field.nextTurn();
            if (onTick != null) onTick.run();
        }
    }

    public void randomize() {
        if (field != null) field.randomize(0.3);
        if (onTick != null) onTick.run();
    }

    public void reset() {
        if (field != null) field.reset();
        if (onTick != null) onTick.run();
    }

    // --- input ---

    private void handleClick(MouseEvent e) {
        if (field == null) return;
        int x = (int) (e.getX() / cellStride);
        int y = (int) (e.getY() / cellStride);
        field.toggle(x, y);
    }

    // --- rendering ---

    private void render() {
        if (field == null) return;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        double w = canvas.getWidth();
        double h = canvas.getHeight();

        gc.setFill(BG_COLOR);
        gc.fillRect(0, 0, w, h);

        gc.setStroke(GRID_COLOR);
        gc.setLineWidth(0.5);
        for (int x = 0; x <= field.getWidth(); x++)
            gc.strokeLine(x * cellStride, 0, x * cellStride, h);
        for (int y = 0; y <= field.getHeight(); y++)
            gc.strokeLine(0, y * cellStride, w, y * cellStride);

        gc.setFill(CELL_COLOR);
        for (int y = 0; y < field.getHeight(); y++) {
            for (int x = 0; x < field.getWidth(); x++) {
                if (field.isAlive(x, y))
                    gc.fillRect(x * cellStride + 1, y * cellStride + 1, cellSize, cellSize);
            }
        }
    }
}

package org.holbreich.lfgm.ui;

import javafx.animation.AnimationTimer;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import org.holbreich.lfgm.model.GameField;

/**
 * Canvas-based renderer and input handler. Extends Region so the canvas
 * fills whatever space the layout assigns and the grid adapts on resize.
 */
public class GameView extends Region {

    private static final Color  BG_COLOR        = Color.rgb(18, 18, 18);
    private static final Color  CELL_COLOR       = Color.rgb(80, 200, 120);
    private static final Color  GRID_COLOR       = Color.rgb(35, 35, 35);
    private static final double RANDOM_DENSITY   = 0.3;

    private final int cellSize;
    private final int cellStride;

    private final Canvas canvas = new Canvas();
    private GameField field     = new GameField(1, 1);
    private boolean firstLayout = true;

    private final BooleanProperty running = new SimpleBooleanProperty(false);
    private long intervalNanos = 100_000_000L;
    private long lastTick      = 0;
    private Runnable onTick;

    public GameView(int cellSize) {
        this.cellSize   = cellSize;
        this.cellStride = cellSize + 1;
        getChildren().add(canvas);
        canvas.setOnMouseClicked(this::handleClick);
        canvas.setOnMouseDragged(this::handleClick);
        startAnimationLoop();
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

        if (firstLayout) {
            field = GameField.randomized(newCols, newRows, RANDOM_DENSITY);
            firstLayout = false;
        } else if (newCols != field.getWidth() || newRows != field.getHeight()) {
            field.resize(newCols, newRows);
        }
    }

    // --- public API ---

    public BooleanProperty runningProperty()    { return running; }
    public boolean isRunning()                  { return running.get(); }
    public void setRunning(boolean r)           { running.set(r); }
    public int getTurns()                       { return field.getTurns(); }
    public void setOnTick(Runnable onTick)      { this.onTick = onTick; }

    public void setSpeed(double generationsPerSecond) {
        intervalNanos = (long) (1_000_000_000.0 / generationsPerSecond);
    }

    public void step() {
        if (!isRunning()) advanceTurn();
    }

    public void randomize() {
        field.randomize(RANDOM_DENSITY);
        notifyTick();
    }

    // --- private ---

    private void startAnimationLoop() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (isRunning() && now - lastTick >= intervalNanos) {
                    advanceTurn();
                    lastTick = now;
                }
                render();
            }
        }.start();
    }

    private void advanceTurn() {
        field.nextTurn();
        notifyTick();
    }

    private void notifyTick() {
        if (onTick != null) onTick.run();
    }

    private void handleClick(MouseEvent e) {
        int x = (int) (e.getX() / cellStride);
        int y = (int) (e.getY() / cellStride);
        field.toggle(x, y);
    }

    private void render() {
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
        for (int y = 0; y < field.getHeight(); y++)
            for (int x = 0; x < field.getWidth(); x++)
                if (field.isAlive(x, y))
                    gc.fillRect(x * cellStride + 1, y * cellStride + 1, cellSize, cellSize);
    }
}

package org.holbreich.lfgm.model;

import java.util.Random;

/**
 * Conway's Game of Life grid — pure Java, no UI dependencies.
 */
public class GameField {

    private boolean[][] cells;
    private final int width;
    private final int height;
    private int turns;
    private Runnable onChange;

    public GameField(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new boolean[height][width];
    }

    public int getWidth()  { return width; }
    public int getHeight() { return height; }
    public int getTurns()  { return turns; }

    public boolean isAlive(int x, int y) {
        return cells[y][x];
    }

    public void toggle(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            cells[y][x] = !cells[y][x];
            notifyChange();
        }
    }

    public void nextTurn() {
        turns++;
        boolean[][] next = new boolean[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int n = countNeighbours(x, y);
                next[y][x] = n == 3 || (n == 2 && cells[y][x]);
            }
        }
        cells = next;
        notifyChange();
    }

    public void reset() {
        turns = 0;
        cells = new boolean[height][width];
        notifyChange();
    }

    public void randomize(double density) {
        Random rnd = new Random();
        turns = 0;
        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                cells[y][x] = rnd.nextDouble() < density;
        notifyChange();
    }

    public void setOnChange(Runnable onChange) {
        this.onChange = onChange;
    }

    private int countNeighbours(int x, int y) {
        int count = 0;
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;
                int nx = x + dx, ny = y + dy;
                if (nx >= 0 && nx < width && ny >= 0 && ny < height && cells[ny][nx]) count++;
            }
        }
        return count;
    }

    private void notifyChange() {
        if (onChange != null) onChange.run();
    }
}

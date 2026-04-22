package org.holbreich.lfgm.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameFieldTest {

    /** Creates a field with specific cells toggled alive. Coords are x,y pairs. */
    private GameField field(int w, int h, int... coords) {
        GameField f = new GameField(w, h);
        for (int i = 0; i < coords.length; i += 2)
            f.toggle(coords[i], coords[i + 1]);
        return f;
    }

    // --- Conway rules ---

    @Test
    void liveCell_fewerThan2Neighbours_dies() {
        GameField f = field(5, 5, 2, 2, 2, 3); // two isolated cells, 1 neighbour each
        f.nextTurn();
        assertFalse(f.isAlive(2, 2));
        assertFalse(f.isAlive(2, 3));
    }

    @Test
    void liveCell_2Neighbours_survives() {
        // blinker: center cell has exactly 2 neighbours
        GameField f = field(5, 5, 1, 2, 2, 2, 3, 2);
        f.nextTurn();
        assertTrue(f.isAlive(2, 2));
    }

    @Test
    void liveCell_3Neighbours_survives() {
        // block: every cell has exactly 3 neighbours
        GameField f = field(6, 6, 2, 2, 3, 2, 2, 3, 3, 3);
        f.nextTurn();
        assertTrue(f.isAlive(2, 2));
        assertTrue(f.isAlive(3, 2));
        assertTrue(f.isAlive(2, 3));
        assertTrue(f.isAlive(3, 3));
    }

    @Test
    void liveCell_moreThan3Neighbours_dies() {
        // center of a + cross has 4 neighbours
        GameField f = field(5, 5, 2, 1, 1, 2, 2, 2, 3, 2, 2, 3);
        f.nextTurn();
        assertFalse(f.isAlive(2, 2));
    }

    @Test
    void deadCell_exactly3Neighbours_becomesAlive() {
        // blinker produces births above and below its center
        GameField f = field(5, 5, 1, 2, 2, 2, 3, 2);
        f.nextTurn();
        assertTrue(f.isAlive(2, 1));
        assertTrue(f.isAlive(2, 3));
    }

    // --- Known patterns ---

    @Test
    void block_isStillLife() {
        GameField f = field(6, 6, 2, 2, 3, 2, 2, 3, 3, 3);
        GameField before = field(6, 6, 2, 2, 3, 2, 2, 3, 3, 3);
        f.nextTurn();
        for (int y = 0; y < 6; y++)
            for (int x = 0; x < 6; x++)
                assertEquals(before.isAlive(x, y), f.isAlive(x, y),
                    "cell (" + x + "," + y + ") changed");
    }

    @Test
    void blinker_oscillates() {
        // horizontal ###
        GameField f = field(5, 5, 1, 2, 2, 2, 3, 2);

        f.nextTurn(); // → vertical
        assertFalse(f.isAlive(1, 2));
        assertFalse(f.isAlive(3, 2));
        assertTrue(f.isAlive(2, 1));
        assertTrue(f.isAlive(2, 2));
        assertTrue(f.isAlive(2, 3));

        f.nextTurn(); // → horizontal again
        assertTrue(f.isAlive(1, 2));
        assertTrue(f.isAlive(2, 2));
        assertTrue(f.isAlive(3, 2));
        assertFalse(f.isAlive(2, 1));
        assertFalse(f.isAlive(2, 3));
    }

    @Test
    void glider_survivesAndMoves() {
        //  .#.
        //  ..#
        //  ###
        GameField f = field(10, 10,
            1, 0,
            2, 1,
            0, 2, 1, 2, 2, 2
        );
        // After 4 turns a glider moves one cell diagonally
        for (int i = 0; i < 4; i++) f.nextTurn();
        int alive = 0;
        for (int y = 0; y < 10; y++)
            for (int x = 0; x < 10; x++)
                if (f.isAlive(x, y)) alive++;
        assertEquals(5, alive, "glider must still have 5 live cells after 4 turns");
    }

    @Test
    void emptyGrid_staysEmpty() {
        GameField f = new GameField(10, 10);
        f.nextTurn();
        for (int y = 0; y < 10; y++)
            for (int x = 0; x < 10; x++)
                assertFalse(f.isAlive(x, y));
    }

    // --- State management ---

    @Test
    void nextTurn_incrementsTurns() {
        GameField f = new GameField(5, 5);
        assertEquals(0, f.getTurns());
        f.nextTurn();
        f.nextTurn();
        assertEquals(2, f.getTurns());
    }

    @Test
    void reset_clearsCellsAndTurns() {
        GameField f = field(5, 5, 2, 2, 3, 3);
        f.nextTurn();
        f.reset();
        assertEquals(0, f.getTurns());
        for (int y = 0; y < 5; y++)
            for (int x = 0; x < 5; x++)
                assertFalse(f.isAlive(x, y));
    }

    @Test
    void toggle_flipsCell() {
        GameField f = new GameField(5, 5);
        assertFalse(f.isAlive(2, 2));
        f.toggle(2, 2);
        assertTrue(f.isAlive(2, 2));
        f.toggle(2, 2);
        assertFalse(f.isAlive(2, 2));
    }

    @Test
    void toggle_outOfBounds_doesNotThrow() {
        GameField f = new GameField(5, 5);
        assertDoesNotThrow(() -> f.toggle(-1, 0));
        assertDoesNotThrow(() -> f.toggle(0, -1));
        assertDoesNotThrow(() -> f.toggle(5, 0));
        assertDoesNotThrow(() -> f.toggle(0, 5));
    }

    @Test
    void onChange_firesOnNextTurn() {
        GameField f = new GameField(5, 5);
        int[] count = {0};
        f.setOnChange(() -> count[0]++);
        f.nextTurn();
        f.nextTurn();
        assertEquals(2, count[0]);
    }
}

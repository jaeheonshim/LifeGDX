package com.mygdx.game;

public class Cell {
    private boolean populated;

    public Cell(boolean populated) {
        this.populated = populated;
    }

    public Cell(Cell cell) {
        this.populated = cell.populated;
    }

    public void kill() {
        populated = false;
    }

    public void populate() {
        populated = true;
    }

    public boolean isPopulated() {
        return populated;
    }
}
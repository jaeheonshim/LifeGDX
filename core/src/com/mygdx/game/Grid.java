package com.mygdx.game;

import java.io.IOException;
import java.util.Arrays;

public class Grid {
    private Cell[][] grid;

    public Grid(int width, int height) {
        grid = new Cell[height + 2][width + 2];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = new Cell(false);
            }
        }
    }

    public Grid(Grid grid) {
        Cell[][] newGrid = new Cell[grid.grid.length][grid.grid[0].length];

        for (int i = 0; i < grid.grid.length; i++) {
            for (int j = 0; j < grid.grid[i].length; j++) {
                newGrid[i][j] = new Cell(grid.grid[i][j]);
            }
        }

        this.grid = newGrid;
    }

    public int getNumberOfNeighbors(int x, int y) {
        int neighbors = 0;
        int[] xCheckOperations = new int[]{
                -1, 0, +1, +1, +1, 0, -1, -1

        };
        int[] yCheckOperations = new int[]{
                +1, +1, +1, 0, -1, -1, -1, 0
        };

        for (int i = 0; i < xCheckOperations.length; i++) {
            if (x + xCheckOperations[i] >= 0 && x + xCheckOperations[i] < grid[0].length && y + yCheckOperations[i] >= 0 && y + yCheckOperations[i] < grid.length) {
                if (grid[y + yCheckOperations[i]][x + xCheckOperations[i]].isPopulated()) {
                    neighbors++;
                }
            }
        }

        return neighbors;
    }

    public Cell getCell(int x, int y) {
        return getGrid()[y][x];
    }

    public void killAll() {
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                cell.kill();
            }
        }
    }

    public void execute() {

        Grid tempGrid = new Grid(this);

        for (int i = 0; i < tempGrid.grid.length; i++) {
            for (int j = 0; j < tempGrid.grid[0].length; j++) {
                if (tempGrid.grid[i][j].isPopulated()) {
                    if (tempGrid.getNumberOfNeighbors(j, i) < 2) {
                        grid[i][j].kill();
                    } else if (tempGrid.getNumberOfNeighbors(j, i) > 3) {
                        grid[i][j].kill();
                    } else if (tempGrid.getNumberOfNeighbors(j, i) == 2 || tempGrid.getNumberOfNeighbors(j, i) == 3) {
                        grid[i][j].populate();
                    }
                } else {
                    if (tempGrid.getNumberOfNeighbors(j, i) == 3) {
                        this.grid[i][j].populate();
                    }
                }
            }
        }
    }

    public Cell[][] getGrid() {
        Cell[][] returnGrid = new Cell[grid.length - 2][grid[0].length - 2];
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[i].length - 1; j++) {
                returnGrid[i - 1][j - 1] = grid[i][j];
            }
        }

        return returnGrid;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(grid.length * grid[0].length);
        for (int i = 0; i < grid[0].length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j].isPopulated()) {
                    stringBuilder.append("0");
                } else {
                    stringBuilder.append(" ");
                }
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public int getWidth() {
        return grid[0].length - 2;
    }

    public int getHeight() {
        return grid.length - 2;
    }
}

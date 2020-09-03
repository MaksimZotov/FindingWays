package model;

import java.util.ArrayList;

public class Field {
    private ArrayList<ArrayList<Cell>> field;
    private int height;
    private int width;

    Field(int height, int width, int maxNumberOfMoves) {
        this.height = height;
        this.width = width;

        field = new ArrayList(height);
        for (int i = 0; i < height; i++) {
            field.set(i, new ArrayList<Cell>(width));
            for (int j = 0; j < width; j++) {
                field.get(i).set(j, new Cell((int) Math.random() * maxNumberOfMoves + 1));
            }
        }
    }

    void setWhiteColorForAllCells() {
        for (ArrayList<Cell> row : field) {
            for (Cell cell : row) {
                cell.setColor(Color.WHITE);
            }
        }
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Cell getCell(int row, int column) {
        return field.get(row).get(column);
    }
}

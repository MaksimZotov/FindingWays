package model;

import java.util.ArrayList;

public class Field {
    private final ArrayList<ArrayList<Cell>> field;
    private final int height;
    private final int width;

    Field(int height, int width, int maxNumberOfMoves) {
        if (height < 1 || width < 1 || maxNumberOfMoves < 1) {
            throw new IllegalArgumentException();
        }
        this.height = height;
        this.width = width;

        field = new ArrayList(height);
        for (int i = 0; i < height; i++) {
            field.add(new ArrayList<>(width));
            for (int j = 0; j < width; j++) {
                field.get(i).add(new Cell((int) (Math.random() * maxNumberOfMoves) + 1));
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
        if (row >= 0 && row < height && column >= 0 && column < width)
            return field.get(row).get(column);
        return null;
    }

    void setThatAllCellsAreNotPartOfTheWay() {
        for (ArrayList<Cell> row : field) {
            for (Cell cell : row) {
                cell.setIsItPartOfTheWay(false);
            }
        }
    }
}

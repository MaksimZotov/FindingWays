package model;

public class Cell {
    private Color color;
    private int number;

    Cell(int number) {
        color = Color.WHITE;
        this.number = number;
    }

    public Color getColor() {
        return color;
    }

    public int getNumber() {
        return number;
    }

    void setColor (Color color) {
        this.color = color;
    }

    void setNumber (int number) {
        this.number = number;
    }
}

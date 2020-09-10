package model.state;

public class Cell {
    private int number;
    private boolean isItPartOfTheWay;

    Cell(int number) {
        isItPartOfTheWay = false;
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    boolean getIsItPartOfTheWay() {
        return isItPartOfTheWay;
    }

    void setIsItPartOfTheWay(boolean isItPartOfTheWay) {
        this.isItPartOfTheWay = isItPartOfTheWay;
    }

    void setNumber(int number) {
        this.number = Math.max(number, 1);
    }
}

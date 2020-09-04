package model.componentsofthemodel;

public class Cell {
    private int number;
    private boolean isItPartOfTheWay;

    Cell(int number) {
        isItPartOfTheWay = false;
        this.number = number;
    }

    boolean getIsItPartOfTheWay() {
        return isItPartOfTheWay;
    }

    public int getNumber() {
        return number;
    }

    void setIsItPartOfTheWay(boolean isItPartOfTheWay) {
        this.isItPartOfTheWay = isItPartOfTheWay;
    }

    void setNumber(int number) {
        this.number = number;
    }
}

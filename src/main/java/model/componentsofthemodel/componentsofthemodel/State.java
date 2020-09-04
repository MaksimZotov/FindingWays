package model.componentsofthemodel.componentsofthemodel;

import model.componentsofthemodel.componentsofthemodel.commitments.StateCommitments;

public class State implements StateCommitments {
    Field field;
    private int quantityOfWays;
    private int numberOfCurrentWay;

    State(Field field) {
        this.field = field;
    }

    void init(int quantityOfWays, int numberOfCurrentWay) {
        this.quantityOfWays = quantityOfWays;
        this.numberOfCurrentWay = numberOfCurrentWay;
    }

    @Override
    public int getQuantityOfWays() {
        return quantityOfWays;
    }

    @Override
    public int getNumberOfCurrentWay() {
        return numberOfCurrentWay;
    }

    @Override
    public int getNumberOfCell(int column, int row) {
        return field.getCell(column, row).getNumber();
    }

    @Override
    public boolean getCellIsItPartOfTheWay(int column, int row) {
        return field.getCell(column, row).getIsItPartOfTheWay();
    }
}

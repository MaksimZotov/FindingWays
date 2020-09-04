package model.componentsofthemodel;

import model.componentsofthemodel.commitments.StateCommitments;

public class State implements StateCommitments {
    private Field field;
    private Integer quantityOfWays;
    private Integer numberOfCurrentWay;

    State(Integer quantityOfWays, Integer numberOfCurrentWay) {
        this.quantityOfWays = quantityOfWays;
        this.numberOfCurrentWay = numberOfCurrentWay;
    }

    @Override
    public void setField(Field field) {
        this.field = field;
    }

    @Override
    public Field getField() {
        return field;
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

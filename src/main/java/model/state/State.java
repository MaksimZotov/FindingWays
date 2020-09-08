package model.state;

import model.state.commitments.StateCommitments;

import java.util.ArrayList;
import java.util.function.Consumer;

public class State implements StateCommitments {
    private ArrayList<ArrayList<Cell>> ways;
    private Field field;
    private int indexOfCurrentWay;

    @Override
    public int getQuantityOfWays() {
        return ways.size();
    }

    @Override
    public int getIndexOfCurrentWay() {
        return indexOfCurrentWay;
    }

    @Override
    public int getFieldHeight() {
        return field.getHeight();
    }

    @Override
    public int getFieldWeight() {
        return field.getWidth();
    }

    @Override
    public int getNumberOfCell(int row, int column) {
        return field.getCell(row, column).getNumber();
    }

    @Override
    public boolean getCellIsItPartOfTheWay(int row, int column) {
        return field.getCell(row, column).getIsItPartOfTheWay();
    }

    Field getField() {
        if (field != null)
            return field;
        return null;
    }

    void setNextCalculatedWay() {
        if (!ways.isEmpty())
            setCalculatedWay(prevIndex -> indexOfCurrentWay = (prevIndex == ways.size() - 1) ? prevIndex : prevIndex + 1);
    }

    void setPreviousCalculatedWay() {
        if (!ways.isEmpty())
            setCalculatedWay(prevIndex -> indexOfCurrentWay = (prevIndex == 0) ? prevIndex : prevIndex - 1);
    }

    void setCalculatedWay(Consumer<Integer> consumer) {
        field.setThatAllCellsAreNotPartOfTheWay();
        consumer.accept(indexOfCurrentWay);
        ArrayList<Cell> way = ways.get(indexOfCurrentWay);
        for (Cell item : way)
            item.setIsItPartOfTheWay(true);
    }

    void setWays(ArrayList<ArrayList<Cell>> ways) {
        this.ways = ways;
    }


    void setField(Field field) {
        this.field = field;
    }
}

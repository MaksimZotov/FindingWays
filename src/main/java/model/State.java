package model;

import controller.StateForModelCommitments;
import controller.StateForViewCommitments;

import java.util.ArrayList;
import java.util.function.Consumer;

public class State implements StateForViewCommitments, StateForModelCommitments {
    private ArrayList<ArrayList<Cell>> ways;
    private Field field;
    private int indexOfCurrentWay = -1;


    // StateForViewCommitments

    @Override
    public int getQuantityOfWays() {
        return (ways == null) ? 0 : ways.size();
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
    public int getFieldWidth() {
        return field.getWidth();
    }

    @Override
    public int getNumberOfCell(int row, int column) {
        return field.getCell(row, column).getNumber();
    }

    @Override
    public boolean getCellIsItPartOfTheWay(int row, int column) {
        Cell cell = field.getCell(row, column);
        if(cell == null)
            return false;
        return cell.getIsItPartOfTheWay();
    }


    // StateForModelCommitments

    @Override
    public Field getField() {
        if (field != null)
            return field;
        return null;
    }

    @Override
    public void setNextCalculatedWay() {
        if (ways != null && !ways.isEmpty())
            setCalculatedWay(prevIndex -> indexOfCurrentWay = (prevIndex == ways.size() - 1) ? prevIndex : prevIndex + 1);
    }

    @Override
    public void setPreviousCalculatedWay() {
        if (ways != null && !ways.isEmpty())
            setCalculatedWay(prevIndex -> indexOfCurrentWay = (prevIndex == 0) ? prevIndex : prevIndex - 1);
    }

    @Override
    public void setWays(ArrayList<ArrayList<Cell>> ways) {
        this.ways = ways;
        indexOfCurrentWay = -1;
        setNextCalculatedWay();
    }

    @Override
    public void setField(Field field) {
        this.field = field;
        indexOfCurrentWay = -1;
        ways = null;
    }

    private void setCalculatedWay(Consumer<Integer> consumer) {
        field.setThatAllCellsAreNotPartOfTheWay();
        consumer.accept(indexOfCurrentWay);
        ArrayList<Cell> way = ways.get(indexOfCurrentWay);
        for (Cell item : way)
            item.setIsItPartOfTheWay(true);
    }
}

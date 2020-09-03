package model;

import java.util.ArrayList;

class Counter {
    private ArrayList<ArrayList<Cell>> ways;
    private int indexOfCurrentWay;

    private Field field;

    void setField(Field field) {
        this.field = field;
    }

    void calculateWays() {
        //...
        indexOfCurrentWay = 0;
    }

    ArrayList<Cell> getNextWay() {
        indexOfCurrentWay = (indexOfCurrentWay == ways.size() - 1) ? indexOfCurrentWay : indexOfCurrentWay + 1;
        return ways.get(indexOfCurrentWay);
    }

    ArrayList<Cell> getPreviousWay() {
        indexOfCurrentWay = (indexOfCurrentWay == 0) ? indexOfCurrentWay : indexOfCurrentWay - 1;
        return ways.get(indexOfCurrentWay);
    }
}

package model.componentsofthemodel.componentsofthemodel;

import model.componentsofthemodel.algorithms.commitments.AlgorithmCommitments;
import model.componentsofthemodel.algorithms.implementations.BasicAlgorithm;

import java.util.ArrayList;

public class Counter {
    private AlgorithmCommitments algorithmCommitments;
    private Field field;

    private ArrayList<ArrayList<Cell>> ways;
    private int indexOfCurrentWay;

    Counter() {
        algorithmCommitments = new BasicAlgorithm();
    }

    public int getQuantityOfWays() {
        return ways.size();
    }

    public int getNumberOfCurrentWay() {
        return indexOfCurrentWay + 1;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void calculateWays() {
        indexOfCurrentWay = 0;
    }

    public ArrayList<Cell> getNextWay() {
        indexOfCurrentWay = (indexOfCurrentWay == ways.size() - 1) ? indexOfCurrentWay : indexOfCurrentWay + 1;
        return ways.get(indexOfCurrentWay);
    }

    public ArrayList<Cell> getPreviousWay() {
        indexOfCurrentWay = (indexOfCurrentWay == 0) ? indexOfCurrentWay : indexOfCurrentWay - 1;
        return ways.get(indexOfCurrentWay);
    }
}

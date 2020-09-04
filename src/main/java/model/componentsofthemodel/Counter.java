package model.componentsofthemodel;

import model.algorithms.commitments.AlgorithmCommitments;
import model.algorithms.implementations.BasicAlgorithm;

import java.util.ArrayList;

public class Counter {
    private AlgorithmCommitments algorithmCommitments;
    private State state;
    private Integer quantityOfWays;
    private Integer numberOfCurrentWay;

    private ArrayList<ArrayList<Cell>> ways;
    private int indexOfCurrentWay;

    Counter(State state) {
        algorithmCommitments = new BasicAlgorithm();
        this.state = state;
    }

    public void calculateWays() {
        ways = algorithmCommitments.getWays(state.getField());
        indexOfCurrentWay = 0;
    }

    public Integer getQuantityOfWays() {
        quantityOfWays = ways.size();
        return quantityOfWays;
    }

    public Integer getNumberOfCurrentWay() {
        numberOfCurrentWay = indexOfCurrentWay + 1;
        return numberOfCurrentWay;
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

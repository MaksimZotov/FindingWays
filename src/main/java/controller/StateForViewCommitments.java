package controller;

public interface StateForViewCommitments {
    int getQuantityOfWays();
    int getIndexOfCurrentWay();
    int getFieldHeight();
    int getFieldWidth();
    int getNumberOfCell(int row, int column);
    boolean getCellIsItPartOfTheWay(int row, int column);
}

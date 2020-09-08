package model.state.commitments;

public interface StateCommitments {
    int getQuantityOfWays();
    int getIndexOfCurrentWay();
    int getFieldHeight();
    int getFieldWeight();
    int getNumberOfCell(int column, int row);
    boolean getCellIsItPartOfTheWay(int column, int row);
}

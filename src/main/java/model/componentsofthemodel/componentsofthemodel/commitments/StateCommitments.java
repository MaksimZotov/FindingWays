package model.componentsofthemodel.componentsofthemodel.commitments;

public interface StateCommitments {
    int getQuantityOfWays();
    int getNumberOfCurrentWay();
    int getNumberOfCell(int column, int row);
    boolean getCellIsItPartOfTheWay(int column, int row);
}

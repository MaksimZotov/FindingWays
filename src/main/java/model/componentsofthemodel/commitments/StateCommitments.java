package model.componentsofthemodel.commitments;

import model.componentsofthemodel.Field;

public interface StateCommitments {
    void setField(Field field);
    Field getField();
    int getQuantityOfWays();
    int getNumberOfCurrentWay();
    int getNumberOfCell(int column, int row);
    boolean getCellIsItPartOfTheWay(int column, int row);
}

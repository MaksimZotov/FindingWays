package model.componentsofthemodel.commitments;

import model.componentsofthemodel.State;

import java.io.IOException;

public interface ModelOfFindingWaysCommitments {
    void createField(int height, int width, int maxNumberOfMoves) throws IOException;
    void calculateWays();
    void showNextCalculatedWay();
    void showPreviousCalculatedWay();
    void setNumberOfCell(int row, int column, int number);
    State getState();
}

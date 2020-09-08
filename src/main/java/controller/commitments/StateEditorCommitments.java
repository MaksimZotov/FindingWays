package controller.commitments;

public interface StateEditorCommitments {
    void createField(int height, int width, int maxNumberOfMoves);
    void setNumberOfCell(int row, int column, int number);
    void calculateWays();
    void showNextCalculatedWay();
    void showPreviousCalculatedWay();
}

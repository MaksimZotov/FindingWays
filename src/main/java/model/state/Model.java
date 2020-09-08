package model.state;

import controller.commitments.ViewCommitments;
import model.algorithms.commitments.AlgorithmCommitments;
import model.algorithms.BasicAlgorithm;
import controller.commitments.ModelCommitments;

public class Model implements ModelCommitments {
    private final State state;
    private final ViewCommitments view;
    private final AlgorithmCommitments algorithm;

    public Model(ViewCommitments view) {
        this.view = view;
        algorithm = new BasicAlgorithm();
        state = new State();
    }

    @Override
    public void createField(int height, int width, int maxNumberOfMoves) {
        state.setField(new Field(height, width, maxNumberOfMoves));
        view.getUpdatedDataAboutTheModel(state);
    }

    @Override
    public void setNumberOfCell(int row, int column, int number) {
        Field field = state.getField();
        if (field == null)
            return;
        Cell cell = field.getCell(row, column);
        if (cell == null)
            return;
        cell.setNumber(number);
        view.getUpdatedDataAboutTheModel(state);
    }

    @Override
    public void calculateWays() {
        if(state.getField() == null)
            return;
        state.setWays(algorithm.getWays(state.getField()));
        view.getUpdatedDataAboutTheModel(state);
    }

    @Override
    public void showNextCalculatedWay() {
        state.setNextCalculatedWay();
        view.getUpdatedDataAboutTheModel(state);
    }

    @Override
    public void showPreviousCalculatedWay() {
        state.setPreviousCalculatedWay();
        view.getUpdatedDataAboutTheModel(state);
    }
}

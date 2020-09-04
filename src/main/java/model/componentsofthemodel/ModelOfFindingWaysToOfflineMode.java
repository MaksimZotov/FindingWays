package model.componentsofthemodel;

import java.util.function.Supplier;

import controller.commitments.ViewCommitments;
import model.componentsofthemodel.commitments.ModelOfFindingWaysCommitments;

import java.util.ArrayList;

public class ModelOfFindingWaysToOfflineMode implements ModelOfFindingWaysCommitments {
    State state;
    ViewCommitments viewCommitments;
    Counter counter;
    Field field;

    public ModelOfFindingWaysToOfflineMode(ViewCommitments viewCommitments) {
        counter = new Counter(state);
        state = new State(counter.getQuantityOfWays(), counter.getNumberOfCurrentWay());
        this.viewCommitments = viewCommitments;
    }

    @Override
    public void createField(int height, int width, int maxNumberOfMoves) {
        field = new Field(height, width, maxNumberOfMoves);
        state.setField(field);
        sendUpdatedInfoToApplication();
    }

    @Override
    public void calculateWays() {
        counter.calculateWays();
        sendUpdatedInfoToApplication();
    }

    @Override
    public void showNextCalculatedWay() {
        showCalculatedWay(counter::getNextWay);
    }

    @Override
    public void showPreviousCalculatedWay() {
        showCalculatedWay(counter::getPreviousWay);
    }

    @Override
    public void setNumberOfCell(int row, int column, int number) {
        field.getCell(row, column).setNumber(number);
        sendUpdatedInfoToApplication();
    }

    @Override
    public State getState() {
        return state;
    }

    private void showCalculatedWay(Supplier function) {
        field.setThatAllCellsAreNotPartOfTheWay();
        ArrayList<Cell> way = (ArrayList<Cell>) function;
        for (Cell item : way) {
            item.setIsItPartOfTheWay(true);
        }
        sendUpdatedInfoToApplication();
    }

    private void sendUpdatedInfoToApplication() {
        viewCommitments.getUpdatedDataAboutTheModel(state);
    }
}

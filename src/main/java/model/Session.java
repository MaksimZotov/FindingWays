package model;

import java.util.function.Supplier;
import view.ApplicationCommitments;
import java.util.ArrayList;

public class Session {
    ApplicationCommitments application;
    Counter counter;
    Field field;

    public Session(ApplicationCommitments application) {
        counter = new Counter();
        this.application = application;
    }

    public void createField(int height, int width, int maxNumberOfMoves) {
        field = new Field(height, width, maxNumberOfMoves);
        counter.setField(field);
        sendUpdatedInfoToApplication();
    }

    public void calculateWays() {
        counter.calculateWays();
        sendUpdatedInfoToApplication();
    }

    public void showNextCalculatedWay() {
        showCalculatedWay(counter::getNextWay);
    }

    public void showPreviousCalculatedWay() {
        showCalculatedWay(counter::getPreviousWay);
    }

    public void setNumberOfCell(int row, int column, int number) {
        field.getCell(row, column).setNumber(number);
        sendUpdatedInfoToApplication();
    }

    private void showCalculatedWay(Supplier function) {
        field.setWhiteColorForAllCells();
        ArrayList<Cell> way = (ArrayList<Cell>) function;
        for (Cell item : way) {
            item.setColor(Color.YELLOW);
        }
        sendUpdatedInfoToApplication();
    }

    private void sendUpdatedInfoToApplication() {
        application.showUpdatedInfo(field);
    }
}

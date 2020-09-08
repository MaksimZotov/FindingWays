package view;

import model.state.commitments.StateCommitments;
import controller.commitments.ModelCommitments;
import controller.commitments.ViewCommitments;
import model.state.Model;
import model.state.State;

import javafx.application.Application;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application implements ViewCommitments {
    private ModelCommitments model;
    private StateVisualisation stateVisualisation;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        model = new Model(this);
        //...
    }

    @Override
    public void getUpdatedDataAboutTheModel(Object data) {
        if(data instanceof State) {
            StateCommitments state = ((State) data);
            int quantityOfWays = state.getQuantityOfWays();
            int indexOfCurrentWay = state.getIndexOfCurrentWay();
            for (int i = 0; i < state.getFieldHeight(); i++) {
                for (int j = 0; j < state.getFieldWeight(); j++) {
                    Rectangle rectangleOfCell = stateVisualisation.getRectanglesOfCells().get(i).get(j);
                    Text textWithNumberOfCell = stateVisualisation.getNumbersOfCells().get(i).get(j);
                    Paint paint = (state.getCellIsItPartOfTheWay(i, j)) ? Paint.valueOf("white") : Paint.valueOf("yellow");
                    rectangleOfCell.setFill(paint);
                    textWithNumberOfCell.setText(state.getNumberOfCell(i, j) + "");
                }
            }
        }
    }

    private void createField(int height, int width, int maxNumberOfMoves) {
        model.createField(height, width, maxNumberOfMoves);
        stateVisualisation = new StateVisualisation(height, width);
    }

    private void setNumberOfCell(int row, int column, int number) {
        model.setNumberOfCell(row, column, number);
    }

    private void calculateWays() {
        model.calculateWays();
    }

    public void showNextCalculatedWay() {
        model.showNextCalculatedWay();
    }

    public void showPreviousCalculatedWay() {
        model.showPreviousCalculatedWay();
    }
}
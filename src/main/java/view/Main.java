package view;

import model.state.commitments.StateCommitments;
import controller.commitments.StateEditorCommitments;
import controller.commitments.ViewCommitments;
import model.state.StateEditor;
import model.state.State;

import javafx.application.Application;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application implements ViewCommitments {
    private StateEditorCommitments stateEditor;
    private StateVisualisation stateVisualisation;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        stateEditor = new StateEditor(this);
        //...
    }

    @Override
    public void getUpdatedDataAboutTheModel(Object data) {
        if (data instanceof Error) {
            //...
        }
        StateCommitments state = ((State) data);

        int quantityOfWays = state.getQuantityOfWays();

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

    private void createField(int height, int width, int maxNumberOfMoves) {
        stateEditor.createField(height, width, maxNumberOfMoves);
        stateVisualisation = new StateVisualisation(height, width);
    }

    private void setNumberOfCell(int row, int column, int number) {
        stateEditor.setNumberOfCell(row, column, number);
    }

    private void calculateWays() {
        stateEditor.calculateWays();
    }

    public void showNextCalculatedWay() {
        stateEditor.showNextCalculatedWay();
    }

    public void showPreviousCalculatedWay() {
        stateEditor.showPreviousCalculatedWay();
    }
}
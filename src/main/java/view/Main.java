package view;

import model.componentsofthemodel.componentsofthemodel.ModelOfFindingWaysToOfflineMode;
import model.componentsofthemodel.componentsofthemodel.ModelOfFindingWaysToOnlineMode;
import model.componentsofthemodel.componentsofthemodel.commitments.StateCommitments;
import model.componentsofthemodel.componentsofthemodel.commitments.ModelOfFindingWaysCommitments;
import controller.commitments.ViewCommitments;

import javafx.application.Application;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application implements ViewCommitments {
    private boolean isOnlineMode;

    private ModelOfFindingWaysCommitments model;
    private FieldVisualisation fieldVisualisation;

    private int height;
    private int width;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        model = (isOnlineMode) ? new ModelOfFindingWaysToOnlineMode(this) : new ModelOfFindingWaysToOfflineMode(this);
        //...
    }

    @Override
    public void getUpdatedDataAboutTheModel(Object data) {
        StateCommitments state = ((ModelOfFindingWaysCommitments) data).getState();

        int numberOfCurrentWay = state.getNumberOfCurrentWay();
        int quantityOfWays = state.getQuantityOfWays();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Rectangle rectangleOfCell = fieldVisualisation.getRectanglesOfCells().get(i).get(j);
                Text textWithNumberOfCell = fieldVisualisation.getNumbersOfCells().get(i).get(j);

                Paint paint = (state.getCellIsItPartOfTheWay(i, j)) ? Paint.valueOf("white") : Paint.valueOf("yellow");

                rectangleOfCell.setFill(paint);
                textWithNumberOfCell.setText(state.getNumberOfCell(i, j) + "");
            }
        }
    }

    private void createField(int height, int width, int maxNumberOfMoves) throws IOException {
        model.createField(height, width, maxNumberOfMoves);
        fieldVisualisation = new FieldVisualisation(height, width);
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
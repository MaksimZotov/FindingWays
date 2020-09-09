package view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
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

import java.awt.*;

public class Main extends Application implements ViewCommitments {
    private ModelCommitments model;
    private StateVisualisation stateVisualisation;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        model = new Model(this);

        int n = 32;
        int m = 32;
        int width = 1000;
        int height = 1000;
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(n, m);
        gridPane.setMaxSize(n, m);

        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++) {
                int divider = Math.max(n, m) * 2;
                Paint paint = ((i + j) % 2 == 1) ? Paint.valueOf("#C0C0C0") : Paint.valueOf("#A9A9A9");
                gridPane.add(new Rectangle(width / divider, height / divider, paint), i, j);
            }

        StackPane root = new StackPane();
        root.getChildren().add(gridPane);
        StackPane.setAlignment(gridPane, Pos.TOP_CENTER);
        gridPane.setPadding(new Insets(120, 0, 0, 0));

        NumberBinding scale = Bindings.min(root.widthProperty().divide(750), root.heightProperty().divide(750));
        gridPane.scaleXProperty().bind(scale);
        gridPane.scaleYProperty().bind(scale);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setHeight(720);
        stage.setWidth(1280);
        stage.setTitle("Finding Ways");
        stage.show();
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
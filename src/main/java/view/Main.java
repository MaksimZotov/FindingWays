package view;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import model.state.commitments.StateCommitments;
import controller.commitments.ModelCommitments;
import controller.commitments.ViewCommitments;
import model.state.Model;
import model.state.State;

import javafx.application.Application;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application implements ViewCommitments {
    private ModelCommitments model;
    private AnchorPane anchorPane;
    private int maxHeightOfField = 64;
    private int maxWidthOfField = 64;
    private ArrayList<ArrayList<Rectangle>> rectangles;
    private ArrayList<ArrayList<Text>> numbers;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        model = new Model(this);
        anchorPane = new AnchorPane();

        ObservableList<Node> childrenOfAnchorPane = anchorPane.getChildren();

        rectangles = new ArrayList<>();
        numbers = new ArrayList<>();

        for(int i = 0; i < maxHeightOfField; i++) {
            rectangles.add(new ArrayList<Rectangle>(maxHeightOfField));
            numbers.add(new ArrayList<Text>(maxWidthOfField));

            for (int j = 0; j < maxWidthOfField; j++) {
                Rectangle rectangle = new Rectangle();
                rectangles.get(i).add(rectangle);

                Text number = new Text();
                numbers.get(i).add(number);

                childrenOfAnchorPane.addAll(rectangle, number);
            }
        }

        Scene scene = new Scene(anchorPane);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setTitle("Finding Ways");
        stage.show();

        createField(32, 32, 5);
    }

    @Override
    public void getUpdatedDataAboutTheModel(Object data) {
        if(data instanceof State) {
            StateCommitments state = ((State) data);

            int quantityOfWays = state.getQuantityOfWays();
            int indexOfCurrentWay = state.getIndexOfCurrentWay();

            int heightField = state.getFieldHeight();
            int widthField = state.getFieldWidth();

            int leftBorderOfField = 600;
            int topBorderOfField = 90;

            int size = 500;

            int max = Math.max(heightField, widthField);
            size /= max;

            for(int i = 0; i < heightField; i++)
                for (int j = 0; j < widthField; j++) {
                    Rectangle rectangle = rectangles.get(i).get(j);
                    rectangle.setFill((state.getCellIsItPartOfTheWay(i, j)) ? Color.GRAY : Color.LIGHTGRAY);
                    rectangle.setLayoutX(leftBorderOfField + i * size);
                    rectangle.setLayoutY(topBorderOfField + j * size);
                    rectangle.setHeight(size);
                    rectangle.setWidth(size);
                    rectangle.setStroke(Color.BLACK);

                    Text number = numbers.get(i).get(j);
                    number.setText(state.getNumberOfCell(i, j) + "");
                    number.setTextAlignment(TextAlignment.CENTER);
                    number.setLayoutX(leftBorderOfField + (i + 0.35) * size);
                    number.setLayoutY(topBorderOfField + (j + 0.7) * size);
                    number.setFont(Font.font(size >> 1));
                }
        }
    }

    private void createField(int height, int width, int maxNumberOfMoves) {
        model.createField(height, width, maxNumberOfMoves);
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
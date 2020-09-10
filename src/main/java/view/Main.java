package view;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Pair;
import model.state.commitments.StateCommitments;
import controller.commitments.ModelCommitments;
import controller.commitments.ViewCommitments;
import model.state.Model;
import model.state.State;

import javafx.application.Application;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application implements ViewCommitments {
    private ModelCommitments model;
    private AnchorPane anchorPane;
    private int maxHeightOfField = 64;
    private int maxWidthOfField = 64;
    private int leftBorderOfField = 600;
    private int topBorderOfField = 90;
    private int sizeCoefficient = 500;
    private ArrayList<ArrayList<Rectangle>> rectangles;
    private ArrayList<ArrayList<Text>> numbers;
    private Text textNumberOfCurrentWay;
    private Text textQuantityOfWays;
    private TextField textFieldHeight;
    private TextField textFieldWidth;
    private TextField textFieldMaxValue;
    private TextField textFieldIncrement;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        model = new Model(this);
        anchorPane = new AnchorPane();

        ObservableList<Node> childrenOfAnchorPane = anchorPane.getChildren();


        // Creating the texts for output
        textNumberOfCurrentWay = createText(
                "Number of current way : 0", leftBorderOfField - sizeCoefficient * 0.85, topBorderOfField + (sizeCoefficient >> 4)
        );
        textQuantityOfWays = createText(
                "Quantity of ways : 0", leftBorderOfField - sizeCoefficient * 0.85, topBorderOfField + (sizeCoefficient >> 3)
        );

        childrenOfAnchorPane.addAll(textNumberOfCurrentWay, textQuantityOfWays);


        // Creating the static texts
        Text textHeight = createText("Height :", leftBorderOfField - sizeCoefficient * 0.85, topBorderOfField + 150);
        Text textWidth = createText("Width :", leftBorderOfField - sizeCoefficient * 0.85, topBorderOfField + 180);
        Text textMaxValue = createText("Max value :", leftBorderOfField - sizeCoefficient * 0.85, topBorderOfField + 210);
        Text textIncrement = createText("Increment :", leftBorderOfField - sizeCoefficient * 0.85, topBorderOfField + 300);

        childrenOfAnchorPane.addAll(textHeight, textWidth, textMaxValue, textIncrement);


        // Creating the texts for input
        textFieldHeight = createTextField("6", leftBorderOfField - sizeCoefficient * 0.65, topBorderOfField + 130);
        textFieldWidth = createTextField("6", leftBorderOfField - sizeCoefficient * 0.65, topBorderOfField + 160);
        textFieldMaxValue = createTextField("6", leftBorderOfField - sizeCoefficient * 0.65, topBorderOfField + 190);
        textFieldIncrement = createTextField("1", leftBorderOfField - sizeCoefficient * 0.65, topBorderOfField + 280);

        int maxWidth = 40;
        textFieldHeight.setMaxWidth(maxWidth);
        textFieldWidth.setMaxWidth(maxWidth);
        textFieldMaxValue.setMaxWidth(maxWidth);
        textFieldIncrement.setMaxWidth(maxWidth);

        childrenOfAnchorPane.addAll(textFieldHeight, textFieldWidth, textFieldMaxValue, textFieldIncrement);


        // Creating the field
        rectangles = new ArrayList<>();
        numbers = new ArrayList<>();

        for(int i = 0; i < maxHeightOfField; i++) {
            rectangles.add(new ArrayList<Rectangle>(maxHeightOfField));
            numbers.add(new ArrayList<Text>(maxWidthOfField));

            for (int j = 0; j < maxWidthOfField; j++) {
                Rectangle rectangle = new Rectangle();
                rectangles.get(i).add(rectangle);

                Text number = new Text();
                number.setOnMouseClicked(e -> {
                    Pair<Integer, Integer> index = getIndexOfNumber(number);
                    if(index == null)
                        return;

                    int sign = 0;
                    MouseButton clickType = e.getButton();
                    if (clickType == MouseButton.PRIMARY)
                        sign = 1;
                    else if(clickType == MouseButton.SECONDARY)
                        sign = -1;

                    setNumberOfCell(
                            index.getKey(), index.getValue(),
                            Integer.parseInt(number.getText()) + sign * Integer.parseInt(textFieldIncrement.getText())
                    );
                });
                numbers.get(i).add(number);

                childrenOfAnchorPane.addAll(rectangle, number);
            }
        }


        // Creating the buttons
        Button buttonCreateField = createButton(
                "Create field", leftBorderOfField - sizeCoefficient * 0.85 , topBorderOfField + sizeCoefficient * 0.8
        );
        Button buttonCalculateWays = createButton(
                "Calculate ways", leftBorderOfField - sizeCoefficient * 0.6, topBorderOfField + sizeCoefficient * 0.8
        );
        Button buttonPreviousWay = createButton(
                "Previous way", leftBorderOfField - sizeCoefficient * 0.85, topBorderOfField + sizeCoefficient * 0.9
        );
        Button buttonNextWay = createButton(
                "Next way", leftBorderOfField - sizeCoefficient * 0.6, topBorderOfField + sizeCoefficient * 0.9
        );

        buttonCreateField.setOnAction(e -> createField());
        buttonCalculateWays.setOnAction(e -> calculateWays());
        buttonNextWay.setOnAction(e -> showNextCalculatedWay());
        buttonPreviousWay.setOnAction(e -> showPreviousCalculatedWay());

        childrenOfAnchorPane.addAll(buttonCreateField, buttonCalculateWays, buttonPreviousWay, buttonNextWay);


        // Creating the stage
        Scene scene = new Scene(anchorPane);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setWidth(1280);
        stage.setHeight(720);
        stage.setTitle("Finding Ways");
        stage.show();
    }

    private Button createButton(String name, double layoutX, double layoutY) {
        Button result = new Button(name);
        result.setLayoutX(layoutX);
        result.setLayoutY(layoutY);
        return result;
    }

    private Text createText(String text, double layoutX, double layoutY) {
        Text result = new Text(text);
        result.setLayoutX(layoutX);
        result.setLayoutY(layoutY);
        result.setFont(Font.font(15));
        return result;
    }

    private TextField createTextField(String text, double layoutX, double layoutY) {
        TextField result = new TextField(text);
        result.setLayoutX(layoutX);
        result.setLayoutY(layoutY);
        result.setFont(Font.font(15));
        return result;
    }

    @Override
    public void getUpdatedDataAboutTheModel(Object data) {
        if(data instanceof State) {
            StateCommitments state = ((State) data);

            textNumberOfCurrentWay.setText("Number of current way : " + (state.getIndexOfCurrentWay() + 1));
            textQuantityOfWays.setText("Quantity of ways : " + state.getQuantityOfWays());

            int heightField = state.getFieldHeight();
            int widthField = state.getFieldWidth();

            int max = Math.max(heightField, widthField);
            int localSizeCoefficient = sizeCoefficient / max;

            for(int i = 0; i < maxHeightOfField; i++)
                for (int j = 0; j < maxWidthOfField; j++) {
                    if (i < heightField && j < widthField) {
                        Rectangle rectangle = rectangles.get(i).get(j);
                        rectangle.setVisible(true);
                        rectangle.setFill((state.getCellIsItPartOfTheWay(i, j)) ? Color.GRAY : Color.LIGHTGRAY);
                        rectangle.setLayoutX(leftBorderOfField + i * localSizeCoefficient);
                        rectangle.setLayoutY(topBorderOfField + j * localSizeCoefficient);
                        rectangle.setHeight(localSizeCoefficient);
                        rectangle.setWidth(localSizeCoefficient);
                        rectangle.setStroke(Color.BLACK);

                        Text number = numbers.get(i).get(j);
                        number.setVisible(true);
                        number.setText(state.getNumberOfCell(i, j) + "");
                        number.setTextAlignment(TextAlignment.CENTER);
                        number.setWrappingWidth(localSizeCoefficient);
                        number.setLayoutX(leftBorderOfField + (i) * localSizeCoefficient);
                        number.setLayoutY(topBorderOfField + (j + 0.7) * localSizeCoefficient);
                        number.setFont(Font.font(localSizeCoefficient >> 1));
                    }
                    else {
                        rectangles.get(i).get(j).setVisible(false);
                        numbers.get(i).get(j).setVisible(false);
                    }
                }
        }
    }

    private void createField() {
        model.createField(
                Integer.parseInt(textFieldHeight.getText()),
                Integer.parseInt(textFieldWidth.getText()),
                Integer.parseInt(textFieldMaxValue.getText())
        );
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

    private Pair<Integer, Integer> getIndexOfNumber(Text number) {
        for (int i = 0; i < maxHeightOfField; i++)
            for (int j = 0; j < maxWidthOfField; j++) {
                Text numberFromArray = numbers.get(i).get(j);
                if (number.getLayoutX() == numberFromArray.getLayoutX() && number.getLayoutY() == numberFromArray.getLayoutY())
                    return new Pair<>(i, j);
            }
        return null;
    }
}
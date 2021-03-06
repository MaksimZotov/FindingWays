package view;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import controller.StateForViewCommitments;
import controller.ModelCommitments;
import controller.ViewCommitments;
import model.Model;

import javafx.application.Application;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class Main extends Application implements ViewCommitments {
    private ModelCommitments model;
    private StateForViewCommitments state;
    private int maxHeightOfField = 64;
    private int maxWidthOfField = 64;
    private double resolutionY = 720;
    private double resolutionX = 1280;
    private double scaleUnitHeight;
    private double scaleUnitWidth;

    private ArrayList<ArrayList<Rectangle>> rectangles;
    private ArrayList<ArrayList<Text>> numbers;
    private Text textNumberOfCurrentWay;
    private Text textQuantityOfWays;
    private TextField textFieldWidth;
    private TextField textFieldHeight;
    private TextField textFieldMaxValue;
    private TextField textFieldIncrement;
    private Text textHeight;
    private Text textWidth;
    private Text textMaxValue;
    private Text textIncrement;
    private Button buttonCreateField;
    private Button buttonCalculateWays;
    private Button buttonPreviousWay;
    private Button buttonNextWay;
    private Button buttonHelp;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        model = new Model(this);

        AnchorPane anchorPane = new AnchorPane();
        ObservableList<Node> childrenOfAnchorPane = anchorPane.getChildren();

        // Создание текстов, предназначенных исключительно для вывода информации
        textNumberOfCurrentWay = new Text("Number of current way : 0");
        textQuantityOfWays = new Text("Quantity of ways : 0");
        childrenOfAnchorPane.addAll(textNumberOfCurrentWay, textQuantityOfWays);

        // Создание неизменяющихся текстов
        textHeight = new Text("Height :");
        textWidth = new Text("Width :");
        textMaxValue = new Text("Max value :");
        textIncrement = new Text("Increment :");
        childrenOfAnchorPane.addAll(textHeight, textWidth, textMaxValue, textIncrement);

        // Создание полей для ввода данных
        textFieldHeight = new TextField();
        textFieldWidth = new TextField();
        textFieldMaxValue = new TextField();
        textFieldIncrement = new TextField();
        BiConsumer<TextField, Pair<String, Integer>> textFieldListener = (textField, newValueAndMax)  -> {
            try {
                String newValue = newValueAndMax.getKey();
                int newValueInt = Integer.parseInt(newValue);
                if(newValueInt <= 0) {
                    textField.setText("1");
                    textField.setStyle("-fx-text-fill: red;");
                    return;
                }
                int max = newValueAndMax.getValue();
                if(newValueInt > max) {
                    textField.setText("" + max);
                    textField.setStyle("-fx-text-fill: red;");
                }
                else {
                    textField.setText(newValue);
                    textField.setStyle("-fx-text-fill: black;");
                }
            } catch (NumberFormatException ex) {
                textField.setText("");
            }
        };
        textFieldHeight.textProperty().addListener((observable, oldValue, newValue) -> textFieldListener.accept(textFieldHeight, new Pair<>(newValue, 32)));
        textFieldWidth.textProperty().addListener((observable, oldValue, newValue) -> textFieldListener.accept(textFieldWidth, new Pair<>(newValue, 32)));
        textFieldMaxValue.textProperty().addListener((observable, oldValue, newValue) -> textFieldListener.accept(textFieldMaxValue, new Pair<>(newValue, 100)));
        textFieldIncrement.textProperty().addListener((observable, oldValue, newValue) -> textFieldListener.accept(textFieldIncrement, new Pair<>(newValue, 100)));
        childrenOfAnchorPane.addAll(textFieldHeight, textFieldWidth, textFieldMaxValue, textFieldIncrement);

        // Создание кнопок
        buttonCreateField = new Button("Create field");
        buttonCalculateWays = new Button("Calculate ways");
        buttonPreviousWay = new Button("Previous way");
        buttonNextWay = new Button("Next way");
        buttonHelp = new Button("Help");
        buttonCreateField.setOnAction(e -> createField());
        buttonCalculateWays.setOnAction(e -> calculateWays());
        buttonNextWay.setOnAction(e -> showNextCalculatedWay());
        buttonPreviousWay.setOnAction(e -> showPreviousCalculatedWay());
        buttonHelp.setOnAction(e -> showHelp());
        childrenOfAnchorPane.addAll(buttonCreateField, buttonCalculateWays, buttonPreviousWay, buttonNextWay, buttonHelp);

        // Создание поля
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
                    if (clickType == MouseButton.PRIMARY) sign = 1;
                    else if(clickType == MouseButton.SECONDARY) sign = -1;
                    int numberInt = Integer.parseInt(number.getText());
                    int newNumber = numberInt + sign * Integer.parseInt(textFieldIncrement.getText());
                    setNumberOfCell(index.getKey(), index.getValue(), Math.min(newNumber, 99));
                });
                numbers.get(i).add(number);
                childrenOfAnchorPane.addAll(rectangle, number);
            }
        }

        // Создание Stage
        updateStageForResolution(stage, resolutionY, resolutionX);
        Scene scene = new Scene(anchorPane);

        stage.widthProperty().addListener(observable -> updateStageForResolution(stage, stage.getHeight(), stage.getWidth()));
        stage.heightProperty().addListener(observable -> updateStageForResolution(stage, stage.getHeight(), stage.getWidth()));

        stage.setScene(scene);
        stage.setHeight(resolutionY);
        stage.setWidth(resolutionX);
        stage.setTitle("Finding Ways");
        stage.show();
    }

    @Override
    public void getUpdatedDataAboutTheModel(Object data) {
        if(data instanceof StateForViewCommitments) {
            state = ((StateForViewCommitments) data);

            textNumberOfCurrentWay.setText("Number of current way : " + (state.getIndexOfCurrentWay() + 1));
            textQuantityOfWays.setText("Quantity of ways : " + state.getQuantityOfWays());

            int heightField = state.getFieldHeight();
            int widthField = state.getFieldWidth();

            double coefficient = (720.0 / 1280) * resolutionX / resolutionY;
            if (coefficient > 1)
                coefficient = 1 / coefficient;
            int sizeOfCell = (int) (600 * coefficient * Math.max(scaleUnitHeight, scaleUnitWidth) / Math.max(heightField, widthField));

            double topBorderField = (resolutionY - 1.08 * (sizeOfCell * Math.max(heightField, widthField))) / 2;
            double leftBorderField = 575 * scaleUnitWidth;

            for(int i = 0; i < maxHeightOfField; i++)
                for (int j = 0; j < maxWidthOfField; j++) {
                    if (i < heightField && j < widthField) {
                        Rectangle rectangle = rectangles.get(i).get(j);
                        rectangle.setVisible(true);
                        rectangle.setFill((state.getCellIsItPartOfTheWay(i, j)) ? Color.GRAY : Color.LIGHTGRAY);
                        rectangle.setLayoutX(leftBorderField + i * sizeOfCell);
                        rectangle.setLayoutY(topBorderField + j * sizeOfCell);
                        rectangle.setHeight(sizeOfCell);
                        rectangle.setWidth(sizeOfCell);
                        rectangle.setStroke(Color.BLACK);

                        Text number = numbers.get(i).get(j);
                        number.setVisible(true);
                        number.setText(state.getNumberOfCell(i, j) + "");
                        number.setTextAlignment(TextAlignment.CENTER);
                        number.setWrappingWidth(sizeOfCell);
                        number.setLayoutX(leftBorderField + i * sizeOfCell);
                        number.setLayoutY(topBorderField + (j + 0.7) * sizeOfCell);
                        number.setFont(Font.font(sizeOfCell >> 1));
                    }
                    else {
                        rectangles.get(i).get(j).setVisible(false);
                        numbers.get(i).get(j).setVisible(false);
                    }
                }
        }
    }

    private void createField() {
        try {
            int height = Integer.parseInt(textFieldHeight.getText());
            int width = Integer.parseInt(textFieldWidth.getText());
            int maxValue = Integer.parseInt(textFieldMaxValue.getText());
            model.createField(height, width, maxValue);
        } catch (NumberFormatException ex) { }
    }

    private void setNumberOfCell(int row, int column, int number) {
        model.setNumberOfCell(row, column, number);
    }

    private void calculateWays() {
        model.calculateWays();
    }

    private void showNextCalculatedWay() {
        model.showNextCalculatedWay();
    }

    private void showPreviousCalculatedWay() {
        model.showPreviousCalculatedWay();
    }

    private void showHelp() {
        Stage dialog = new Stage();
        dialog.initStyle(StageStyle.UTILITY);
        Text text = new Text(0, 15,
            "Приложение позволяет находить все возможные пути из левой верхней ячейки в правую нижнюю\n\n" +
            "Current number of way - номер текущего пути\n" +
            "Quantity of ways - количество найденных путей\n" +
            "Height - высота поля, которое вы хотите сгенерировать\n" +
            "Width - ширина поля, которое вы хотите сгенерировать\n" +
            "Max value - максимальное значение ячейки поля при генерации\n" +
            "Increment - значение, на которое будет увеличиваться значение ячейки при клике по ней левой кнопкой мыши или уменьшаться - при клике правой\n" +
            "Create field - создать поле\n" +
            "Calculate ways - высчитать пути для текущего поля\n" +
            "Previous way - посмотреть предыдущий путь\n" +
            "Next way - посмотреть следующий путь\n\n" +
            "Ограничения:\nМаксимальное значение ячейки - 100\nМаксимальное значение у Increment - 100\nМаксимальное значение высоты или ширины - 32"
        );
        text.setFont(Font.font(14));
        AnchorPane anchorPane = new AnchorPane(text);
        Scene scene = new Scene(anchorPane);
        dialog.setScene(scene);
        dialog.show();
    }

    private void updateStageForResolution(Stage stage, double resolutionY, double resolutionX) {
        this.resolutionY = resolutionY;
        this.resolutionX = resolutionX;
        scaleUnitHeight = resolutionY / 720;
        scaleUnitWidth = resolutionX / 1280;

        stage.setHeight(resolutionY);
        stage.setWidth(resolutionX);

        double firstLeftBorder = 160 * scaleUnitWidth;
        double secondLeftBorder = 330 * scaleUnitWidth;

        // Обновление текстов, предназначенных исключительно для вывода информации
        int scaleTextsForOutput = (int)(scaleUnitHeight * 20);
        Text[] textsForOutput = new Text[] {textNumberOfCurrentWay, textQuantityOfWays };
        for(Text item : textsForOutput) {
            item.setFont(Font.font(scaleTextsForOutput));
            item.setLayoutX(firstLeftBorder);
        }
        textNumberOfCurrentWay.setLayoutY(122 * scaleUnitHeight);
        textQuantityOfWays.setLayoutY(152 * scaleUnitHeight);

        // Обновление неизменяющихся текстов
        int scaleStaticTexts = (int)(scaleUnitHeight * 18);
        Text[] staticTexts = new Text[] { textHeight, textWidth, textMaxValue, textIncrement };
        for(Text item : staticTexts) {
            item.setFont(Font.font(scaleStaticTexts));
            item.setLayoutX(firstLeftBorder);
        }
        textHeight.setLayoutY(240 * scaleUnitHeight);
        textWidth.setLayoutY(270 * scaleUnitHeight);
        textMaxValue.setLayoutY(300 * scaleUnitHeight);
        textIncrement.setLayoutY(390 * scaleUnitHeight);

        // Обновление полей для ввода данных
        double scaleWidthTextsForInput = 40 * scaleUnitHeight;
        double scaleHeightTextsForInput = 30 * scaleUnitHeight;
        double scaleFontTextsForInput = 15 * scaleUnitHeight;
        TextField[] textFields = new TextField[] { textFieldHeight, textFieldWidth, textFieldMaxValue, textFieldIncrement };
        for(TextField item: textFields) {
            item.setFont(Font.font(scaleFontTextsForInput));
            item.setMaxSize(scaleWidthTextsForInput, scaleHeightTextsForInput);
            item.setMinSize(scaleWidthTextsForInput, scaleHeightTextsForInput);
            item.setLayoutX(secondLeftBorder);
            item.setMinWidth(scaleWidthTextsForInput * 2);
            item.setMaxWidth(scaleWidthTextsForInput * 2);
        }
        textFieldWidth.setLayoutY(220 * scaleUnitHeight);
        textFieldHeight.setLayoutY(250 * scaleUnitHeight);
        textFieldMaxValue.setLayoutY(280 * scaleUnitHeight);
        textFieldIncrement.setLayoutY(370 * scaleUnitHeight);

        // Обновление кнопок
        int scaleFontButtons = (int)(15 * scaleUnitHeight);
        Button[] buttonsLeft = new Button[] { buttonCreateField, buttonPreviousWay };
        Button[] buttonsRight = new Button[] { buttonCalculateWays, buttonNextWay };
        for(int i = 0; i < 2; i++) {
            buttonsLeft[i].setFont(Font.font(scaleFontButtons));
            buttonsRight[i].setFont(Font.font(scaleFontButtons));
            buttonsLeft[i].setLayoutX(firstLeftBorder);
            buttonsRight[i].setLayoutX(350 * scaleUnitWidth);
        }
        buttonCreateField.setLayoutY(490 * scaleUnitHeight);
        buttonPreviousWay.setLayoutY(540 * scaleUnitHeight);
        buttonCalculateWays.setLayoutY(490 * scaleUnitHeight);
        buttonNextWay.setLayoutY(540 * scaleUnitHeight);
        buttonHelp.setFont(Font.font(scaleFontButtons));
        buttonHelp.setLayoutX(firstLeftBorder);
        buttonHelp.setLayoutY(50 * scaleUnitHeight);

        // Обновление поля
        getUpdatedDataAboutTheModel(state);
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
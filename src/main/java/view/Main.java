package view;

import javafx.application.Application;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import model.Session;
import model.Field;
import model.Color;
import model.Cell;


public class Main extends Application implements ApplicationCommitments {
    private Session session;
    private FieldVisualisation fieldVisualisation;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        session = new Session(this);
        //...
    }

    private void createField(int height, int width, int maxNumberOfMoves) {
        session.createField(height, width, maxNumberOfMoves);
        fieldVisualisation = new FieldVisualisation(height, width);
    }

    private void setNumberOfCell(int row, int column, int number) {
        session.setNumberOfCell(row, column, number);
    }

    private void calculateWays() {
        session.calculateWays();
    }

    public void showNextCalculatedWay() {
        session.showNextCalculatedWay();
    }

    public void showPreviousCalculatedWay() {
        session.showPreviousCalculatedWay();
    }

    @Override
    public void showUpdatedInfo(Field field) {
        //...
        for (int i = 0; i < field.getHeight(); i++) {
            for (int j = 0; j < field.getWidth(); j++) {
                Cell cell = field.getCell(i, j);

                Color colorOfCell = cell.getColor();
                int numberOfCell = cell.getNumber();

                Rectangle rectangleOfCell = fieldVisualisation.getRectanglesOfCells().get(i).get(j);
                Text textWithNumberOfCell = fieldVisualisation.getNumbersOfCells().get(i).get(j);

                Paint paint = (colorOfCell == Color.WHITE) ? Paint.valueOf("white") : Paint.valueOf("yellow");

                rectangleOfCell.setFill(paint);
                textWithNumberOfCell.setText(numberOfCell + "");
            }
        }
    }
}
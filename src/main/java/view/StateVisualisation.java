package view;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

class StateVisualisation {
    private ArrayList<ArrayList<Rectangle>> rectanglesOfCells;
    private ArrayList<ArrayList<Text>> numbersOfCells;

    StateVisualisation(int height, int width) {
        //...
    }

    ArrayList<ArrayList<Rectangle>> getRectanglesOfCells() {
        return rectanglesOfCells;
    }

    ArrayList<ArrayList<Text>> getNumbersOfCells() {
        return numbersOfCells;
    }
}

package model.algorithms.implementations;

import model.algorithms.commitments.AlgorithmCommitments;
import model.componentsofthemodel.Cell;
import model.componentsofthemodel.Field;

import java.util.ArrayList;

public class BasicAlgorithm implements AlgorithmCommitments {

    @Override
    public ArrayList<ArrayList<Cell>> getWays(Field field) {
        int height = field.getHeight();
        int width = field.getWidth();

        Cell startCell = field.getCell(0, 0);
        Cell finishCell = field.getCell(height, width);

        //...

        return null;
    }
}

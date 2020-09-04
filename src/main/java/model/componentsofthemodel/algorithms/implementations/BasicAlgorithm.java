package model.componentsofthemodel.algorithms.implementations;

import model.componentsofthemodel.algorithms.commitments.AlgorithmCommitments;
import model.componentsofthemodel.componentsofthemodel.Cell;
import model.componentsofthemodel.componentsofthemodel.Field;

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

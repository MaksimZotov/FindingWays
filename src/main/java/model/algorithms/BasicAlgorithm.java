package model.algorithms;

import model.algorithms.commitments.AlgorithmCommitments;
import model.state.Cell;
import model.state.Field;

import java.util.ArrayList;

public class BasicAlgorithm implements AlgorithmCommitments {

    @Override
    public ArrayList<ArrayList<Cell>> getWays(Field field) {
        int height = field.getHeight();
        int width = field.getWidth();

        Cell startCell = field.getCell(0, 0);
        Cell finishCell = field.getCell(height - 1, width - 1);

        ArrayList<ArrayList<Cell>> result = new ArrayList<>();
        result.add(new ArrayList<>());
        result.get(0).add(startCell);

        result.add(new ArrayList<>());
        result.get(1).add(finishCell);

        //...

        if (result.isEmpty())
            return null;

        return result;
    }
}

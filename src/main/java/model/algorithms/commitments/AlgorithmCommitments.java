package model.algorithms.commitments;

import model.componentsofthemodel.Cell;
import model.componentsofthemodel.Field;

import java.util.ArrayList;

public interface AlgorithmCommitments {
    ArrayList<ArrayList<Cell>> getWays(Field field);
}

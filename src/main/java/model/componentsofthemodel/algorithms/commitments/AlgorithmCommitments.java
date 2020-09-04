package model.componentsofthemodel.algorithms.commitments;

import model.componentsofthemodel.componentsofthemodel.Cell;
import model.componentsofthemodel.componentsofthemodel.Field;

import java.util.ArrayList;

public interface AlgorithmCommitments {
    ArrayList<ArrayList<Cell>> getWays(Field field);
}

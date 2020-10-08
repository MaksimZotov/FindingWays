package model.algorithms.commitments;

import model.Cell;
import model.Field;

import java.util.ArrayList;

public interface AlgorithmCommitments {
    ArrayList<ArrayList<Cell>> getWays(Field field);
}

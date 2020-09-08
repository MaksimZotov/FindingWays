package model.algorithms.commitments;

import model.state.Cell;
import model.state.Field;

import java.util.ArrayList;

public interface AlgorithmCommitments {
    ArrayList<ArrayList<Cell>> getWays(Field field);
}

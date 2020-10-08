package controller;

import model.Cell;
import model.Field;

import java.util.ArrayList;

public interface StateForModelCommitments {
    Field getField();
    void setField(Field field);
    void setNextCalculatedWay();
    void setPreviousCalculatedWay();
    void setWays(ArrayList<ArrayList<Cell>> ways);
}

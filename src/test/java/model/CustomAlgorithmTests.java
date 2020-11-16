package model;

import model.algorithms.CustomAlgorithm;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;


public class CustomAlgorithmTests {

    @Test
    public void test() {
        CustomAlgorithm algorithm = new CustomAlgorithm();
        ArrayList<ArrayList<Cell>> expected;
        ArrayList<ArrayList<Cell>> actual;
        Field field;

        // 1
        field = new Field(1, 1, 1);
        Assert.assertEquals(algorithm.getWays(field).size(), 1);

        // 1 1
        // 1 1
        field = new Field(2, 2, 1);
        Assert.assertEquals(algorithm.getWays(field).size(), 2);

        // 2 1 1
        // 1 1 1
        // 1 1 1
        field = new Field(3, 3, 1);
        field.getCell(0, 0).setNumber(2);

        expected = new ArrayList<>();
        expected.add(new ArrayList<>());
        expected.get(0).add(new Cell(2));
        expected.get(0).add(new Cell(1));
        expected.get(0).add(new Cell(1));
        expected.add(new ArrayList<>());
        expected.get(1).add(new Cell(2));
        expected.get(1).add(new Cell(1));
        expected.get(1).add(new Cell(1));

       actual = algorithm.getWays(field);

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                Assert.assertEquals(expected.get(i).get(j).getNumber(), actual.get(i).get(j).getNumber());
            }
        }

        // 3 1 1
        // 1 1 1
        // 1 1 1
        field.getCell(0, 0).setNumber(3);
        Assert.assertEquals(null, algorithm.getWays(field));


        // 2 1 3 1 1
        // 1 1 1 1 1
        // 3 1 1 1 1
        // 1 1 1 1 1
        field = new Field(4, 5, 1);
        field.getCell(0, 0).setNumber(2);
        field.getCell(0, 2).setNumber(3);
        field.getCell(2, 0).setNumber(3);

        expected = new ArrayList<>();
        expected.add(new ArrayList<>());
        expected.get(0).add(new Cell(2));
        expected.get(0).add(new Cell(3));
        expected.get(0).add(new Cell(1));
        expected.get(0).add(new Cell(1));
        expected.get(0).add(new Cell(1));
        expected.add(expected.get(0));
        expected.add(expected.get(0));

        actual = algorithm.getWays(field);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                Assert.assertEquals(expected.get(i).get(j).getNumber(), actual.get(i).get(j).getNumber());
            }
        }
    }
}
package model.algorithms;

import model.algorithms.commitments.AlgorithmCommitments;
import model.state.Cell;
import model.state.Field;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class BasicAlgorithm implements AlgorithmCommitments {
    private class Vertex {
        private final ArrayList<Vertex> children;
        private final int row;
        private final int column;
        private final int stepSize;

        public Vertex(int row, int column, int stepSize) {
            this.row = row;
            this.column = column;
            this.stepSize = stepSize;
            children = new ArrayList<>();
        }
    }

    private Field field;
    private ArrayList<ArrayList<Vertex>> vertices;
    private Vertex startVertex;
    private ArrayList<ArrayList<Cell>> ways;

    @Override
    public ArrayList<ArrayList<Cell>> getWays(Field field) {
        this.field = field;

        int height = field.getHeight();
        int width = field.getWidth();

        vertices = new ArrayList<>(height);
        for(int i = 0; i < height; i++) {
            vertices.add(new ArrayList<Vertex>(width));
            for(int j = 0; j < width; j++) {
                vertices.get(i).add(null);
            }
        }

        startVertex = new Vertex(0, 0, field.getCell(0, 0).getNumber());

        createVertices(startVertex);

        ways = new ArrayList<>();
        createWaysOnBaseOfVertices(startVertex, new ArrayList<Cell>());

        if (ways.isEmpty())
            return null;

        return ways;
    }

    private void createVertices(Vertex vertex) {
        int row = vertex.row;
        int column = vertex.column;
        int step = vertex.stepSize;

        if(row >= vertices.size() || column >= vertices.get(0).size())
            return;

        if(row == vertices.size() - 1 && column == vertices.get(0).size() - 1) {
            vertices.get(row).set(column, vertex);
            return;
        }

        BiConsumer<Integer, Integer> biConsumer = (rowOfNextVertex, columnOfNextVertex) -> {
            if(rowOfNextVertex >= vertices.size() || columnOfNextVertex >= vertices.get(0).size())
                return;

            Vertex nextVertex = vertices.get(rowOfNextVertex).get(columnOfNextVertex);
            if (nextVertex != null) {
                vertex.children.add(nextVertex);
                vertices.get(row).set(column, vertex);
            }
            else {
                nextVertex = new Vertex(
                        rowOfNextVertex,
                        columnOfNextVertex,
                        field.getCell(rowOfNextVertex, columnOfNextVertex).getNumber()
                );
                createVertices(nextVertex);
                if(vertices.get(rowOfNextVertex).get(columnOfNextVertex) != null) {
                    vertex.children.add(nextVertex);
                    vertices.get(row).set(column, vertex);
                }
            }
        };

        biConsumer.accept(row + step, column);
        biConsumer.accept(row, column + step);
    }

    private void createWaysOnBaseOfVertices(Vertex vertex, ArrayList<Cell> arrayList) {
        int row = vertex.row;
        int column = vertex.column;

        arrayList.add(field.getCell(row, column));

        if(row == vertices.size() - 1 && column == vertices.get(0).size() - 1) {
            ways.add(arrayList);
            return;
        }

        for(int i = 0; i < vertex.children.size(); i++)
            createWaysOnBaseOfVertices(vertex.children.get(i), (ArrayList<Cell>) arrayList.clone());
    }
}

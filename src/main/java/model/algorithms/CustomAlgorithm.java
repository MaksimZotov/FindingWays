package model.algorithms;

import model.algorithms.commitments.AlgorithmCommitments;
import model.Cell;
import model.Field;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class CustomAlgorithm implements AlgorithmCommitments {
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

    // Поле из вершин, соответствущее входному полю
    private ArrayList<ArrayList<Vertex>> vertices;

    private Vertex startVertex;
    private ArrayList<ArrayList<Cell>> ways;

    @Override
    public ArrayList<ArrayList<Cell>> getWays(Field field) {
        this.field = field;

        int height = field.getHeight();
        int width = field.getWidth();

        if(height == 0 || width == 0)
            return new ArrayList();

        // Создание поля из вершин, соответствущего входному полю
        vertices = new ArrayList<>(height);
        for(int i = 0; i < height; i++) {
            vertices.add(new ArrayList<Vertex>(width));
            for(int j = 0; j < width; j++) {
                // Изначально каждая вершина, соответствующая определенной ячейке поля, инициализируется null'ом
                vertices.get(i).add(null);
            }
        }

        // Вершина, из которой считаем пути
        startVertex = new Vertex(0, 0, field.getCell(0, 0).getNumber());

        // Рекурсивно ищем потомков, которые ведут к финальной вершине, для заданной в качестве аргумента вершины
        findChildren(startVertex);

        ways = new ArrayList<ArrayList<Cell>>();
        createWaysOnBaseOfVertices(startVertex, new ArrayList<Cell>());

        return ways.isEmpty() ? null : ways;
    }

    // Время - O(M)
    // Память - O(N^2)
    // M - суммарное число ячеек во всех путях
    // N - длина квадратного поля
    private void findChildren(Vertex vertex) {
        int row = vertex.row;
        int column = vertex.column;
        int step = vertex.stepSize;

        // Если текущая вершина является конечной
        if(row == vertices.size() - 1 && column == vertices.get(0).size() - 1) {
            vertices.get(row).set(column, vertex);
            return;
        }


        // Проверка правых и нижних вершин, которые мы можем посетить из текущей:

        BiConsumer<Integer, Integer> biConsumer = (rowOfNextVertex, columnOfNextVertex) -> {

            // Если новая вершина выходит за пределы поля
            if(rowOfNextVertex >= vertices.size() || columnOfNextVertex >= vertices.get(0).size())
                return;

            // Берём элемент из поля вершин в соответсвии с индексами новой вершины
            Vertex nextVertex = vertices.get(rowOfNextVertex).get(columnOfNextVertex);

            // Если данный элемент != null, то информация о возможных путях из него уже высчитана
            if (nextVertex != null) {

                // Добавляем к текущей вершине в качестве потомка новую вершину
                vertex.children.add(nextVertex);

                // В поле вершин указываем, что текущая вершина уже высчитана
                vertices.get(row).set(column, vertex);
            }
            else {
                nextVertex = new Vertex(
                        rowOfNextVertex,
                        columnOfNextVertex,
                        field.getCell(rowOfNextVertex, columnOfNextVertex).getNumber()
                );

                // Ищем потомков для новой вершины, ведущих к финальной вершине
                findChildren(nextVertex);

                // Если у новой вершины нашлись потомки, ведущие к финальной вершине
                if(vertices.get(rowOfNextVertex).get(columnOfNextVertex) != null) {

                    // Добавляем к текущей вершине в качестве потомка новую вершину
                    vertex.children.add(nextVertex);

                    // В поле вершин указываем, что текущая вершина уже высчитана
                    vertices.get(row).set(column, vertex);
                }
            }
        };

        // Проверка нижней вершины
        biConsumer.accept(row + step, column);

        // Проверка правой вершины
        biConsumer.accept(row, column + step);
    }

    // Время - O(M)
    // Память - O(M)
    // M - суммарное число ячеек во всех вычисленных путях
    private void createWaysOnBaseOfVertices(Vertex vertex, ArrayList<Cell> way) {
        int row = vertex.row;
        int column = vertex.column;

        // Добавляем в список ячеек для данного пути ячейку, соответствущую текущей вершине
        way.add(field.getCell(row, column));

        // Если текущая вершина является конечной
        if(row == vertices.size() - 1 && column == vertices.get(0).size() - 1) {
            ways.add(way);
            return;
        }

        for(int i = 0; i < vertex.children.size(); i++) {
            createWaysOnBaseOfVertices(vertex.children.get(i), (ArrayList<Cell>) way.clone());
            // Из-за way.clone() на каждой ячейке каждого пути в памяти создаётся ArrayList.
            // Таким образом, на промежуточных ячейках путей генерируется много мусора, но,
            // так как ссылки на эти ArrayList'ы пропадают из памяти после завершения
            // рекурсивного выполнения createWaysOnBaseOfVertices()
            // (исключения составляют лишь ссылки, которые соответствую финальной ячейке),
            // то можно этим мусором пренебречь.
            // Тогда можно считать, что в памяти остаются только такие ArrayList'ы, что
            // каждый из них представляет собой последовательность ячеек для конкретного пути.
            // В таком случае затраты по памяти можно оценить как O(M),
            // где M - суммарное число ячеек во всех вычисленных путях.
        }
    }
}
